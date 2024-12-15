package com.example.securednotepad

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import android.content.Context
import android.content.Intent

class PasswdCheck : AppCompatActivity() {
    private lateinit var btnLogin: Button // Botão para efetuar o login
    private lateinit var passwdText: EditText // Campo de texto para inserir o PIN
    private lateinit var sharedPreferences: SharedPreferences // Para acessar o SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwd_check)
        enableEdgeToEdge()

        setupViews() // Inicializa as visualizações (EditText e Button) e o SharedPreferences
        setupLoginButton() // Configura o evento do botão de login
    }

    private fun setupViews() {
        passwdText = findViewById(R.id.passwdLogin) // Campo de texto para inserir o PIN
        btnLogin = findViewById(R.id.btnLogin) // Botão para realizar o login
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE) // Acessa o SharedPreferences
    }

    private fun setupLoginButton() {
        btnLogin.setOnClickListener {
            val enteredPin = passwdText.text.toString() // Obtém o PIN inserido pelo usuário
            val savedPin = sharedPreferences.getString("pin", "") // Obtém o PIN salvo do SharedPreferences

            // Verifica se o PIN inserido não está vazio e se corresponde ao PIN salvo
            if (enteredPin.isNotEmpty() && enteredPin == savedPin) {
                // Se o login for bem-sucedido, exibe uma mensagem de sucesso e inicia a activity NotePadList
                Toast.makeText(this, "Login bem sucedido!", Toast.LENGTH_SHORT).show()
                startNotePadListActivity()
            } else {
                // Se o PIN estiver incorreto, exibe uma mensagem de erro
                Toast.makeText(this, "PIN incorreto. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Inicia a activity NotePadList
    private fun startNotePadListActivity() {
        val intent = Intent(this, NotePadList::class.java)
        startActivity(intent)
        finish() // Finaliza a activity de verificação do PIN
    }
}