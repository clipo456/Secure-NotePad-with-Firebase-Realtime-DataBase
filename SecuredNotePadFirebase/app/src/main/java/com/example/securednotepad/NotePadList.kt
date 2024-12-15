package com.example.securednotepad

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class NotePadList : AppCompatActivity() {
    private lateinit var notesList: ListView // ListView para exibir a lista de notas
    private lateinit var notesAdapter: CustomArrayAdapter // Adaptador personalizado para a ListView, que faz a exibição das notas
    private lateinit var noteInput: EditText // EditText para inserir novas notas
    private lateinit var addNoteButton: Button // Botão para adicionar novas notas

    private val notes = mutableListOf<String>() // Lista mutável para armazenar as notas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_pad_list)

        notesList = findViewById(R.id.notesList)
        noteInput = findViewById(R.id.noteInput)
        addNoteButton = findViewById(R.id.btnAddNote)

        // Carregar as notas salvas do SharedPreferences ao iniciar a atividade
        loadNotes()

        // Inicializa o adaptador personalizado com a lista de notas e configura a ListView para usá-lo
        notesAdapter = CustomArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        notesList.adapter = notesAdapter

        // Configura o evento de clique do botão "Adicionar Nota"
        addNoteButton.setOnClickListener {
            val note = noteInput.text.toString().trim() // Obtém o texto inserido no EditText
            if (note.isNotEmpty()) { // Verifica se o texto da nota não está vazio
                notes.add(note) // Adiciona a nota à lista de notas
                saveNotes() // Salva as notas atualizadas no SharedPreferences
                notesAdapter.notifyDataSetChanged()
                noteInput.text.clear() // Limpa o EditText após adicionar a nota
            }
        }

        // Configura o evento de clique curto em um item da lista para editar a nota
        notesList.setOnItemClickListener { parent, view, position, id ->
            val clickedNote = notes[position] // Obtém a nota clicada
            editNoteDialog(clickedNote, position) // Abre um diálogo para editar a nota
        }

        // Configura um evento de clique longo em um item da lista para excluir a nota
        notesList.setOnItemLongClickListener { parent, view, position, id ->
            deleteNoteDialog(position) // Abre um diálogo de confirmação para excluir a nota
            true
        }
    }

    // Método para abrir um diálogo para editar a nota
    private fun editNoteDialog(note: String, position: Int) {
        val editText = EditText(this) // Cria um novo EditText para editar a nota
        editText.setText(note) // Define o texto do EditText como a nota existente

        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Editar Nota")
            .setView(editText)
            .setPositiveButton("Salvar") { _, _ ->
                val editedNote = editText.text.toString().trim() // Armazena a nota editada
                if (editedNote.isNotEmpty()) {
                    notes[position] = editedNote // Atualiza a nota na lista
                    saveNotes() // Salva as notas atualizadas no SharedPreferences
                    notesAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        dialog.show() // Exibe o diálogo de edição de nota
    }

    // Método para abrir um diálogo de confirmação para excluir a nota
    private fun deleteNoteDialog(position: Int) {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Excluir Nota")
            .setMessage("Tem certeza de que deseja excluir esta nota?")
            .setPositiveButton("Sim") { _, _ ->
                notes.removeAt(position) // Remove a nota da lista de notas
                saveNotes() // Salva as notas atualizadas no SharedPreferences
                notesAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Não", null)
            .create()

        dialog.show() // Exibe o diálogo de confirmação para excluir a nota
    }

    // Método para salvar as notas no SharedPreferences
    private fun saveNotes() {
        val sharedPreferences = getSharedPreferences("MyNotes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("notes", HashSet(notes)) // Salva a lista de notas como um conjunto de strings no SharedPreferences
        editor.apply() // Aplica as alterações no SharedPreferences
    }

    // Método para carregar as notas salvas do SharedPreferences
    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("MyNotes", Context.MODE_PRIVATE)
        val savedNotes = sharedPreferences.getStringSet("notes", HashSet<String>()) // Obtém o conjunto de notas salvas
        notes.clear() // Limpa a lista de notas atual
        savedNotes?.let { // Verifica se o conjunto de notas salvas não é nulo
            notes.addAll(it) // Adiciona as notas salvas à lista de notas atual
        }
    }
}