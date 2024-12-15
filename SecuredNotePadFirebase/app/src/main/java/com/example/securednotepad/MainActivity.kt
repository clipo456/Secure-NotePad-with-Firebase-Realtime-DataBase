package com.example.securednotepad

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var passwdText: EditText // Campo de texto para inserir o PIN
    private lateinit var passwdConfirm: EditText // Campo de texto para confirmar o PIN
    private lateinit var btnEnter: Button // Botão para confirmar e salvar o PIN
    private lateinit var sharedPreferences: SharedPreferences // Para armazenar o PIN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        enableEdgeToEdge()

        // Inicializa o SharedPreferences com o nome "loginPrefs" e modo privado
        sharedPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        // Verifica se o SharedPreferences contém uma entrada para a chave "pin"
        if (sharedPreferences.contains("pin")) {
            // Se existe, chama a activity PasswdCheck para verificar o PIN
            launchPasswdCheck(sharedPreferences.getString("pin", ""))
        } else {
            // Se não existe, configura as visualizações e o evento do botão
            setupViews()
            setupEnterButton()
        }
    }

    private fun setupViews() {
        passwdText = findViewById(R.id.passwdText)
        passwdConfirm = findViewById(R.id.passwdConfirm)
        btnEnter = findViewById(R.id.btnEnter)
    }

    // Configura botão de login
    private fun setupEnterButton() {
        btnEnter.setOnClickListener {
            val pin = passwdText.text.toString()
            val confirmPin = passwdConfirm.text.toString()

            // Verifica se os campos do PIN e de confirmação não estão vazios e se os PINs correspondem
            if (pin.isNotEmpty() && confirmPin.isNotEmpty() && pin == confirmPin) {
                // Se sim, salva o PIN e envia para a verificação
                savePin(pin)
                launchPasswdCheck(pin)
            } else {
                // Se não, exibe uma mensagem de erro através de um Toast
                Toast.makeText(this, "Os PINs não correspondem. Tente novamente.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Salva o PIN no SharedPreferences e exibe uma mensagem de sucesso através de um Toast
    private fun savePin(pin: String) {
        sharedPreferences.edit().putString("pin", pin).apply()
        Toast.makeText(this, "PIN criado com sucesso!", Toast.LENGTH_SHORT).show()
    }

    // Chama a activity PasswdCheck para verificar o PIN
    private fun launchPasswdCheck(pin: String?) {
        val intent = Intent(this, PasswdCheck::class.java)
        intent.putExtra("pin", pin)
        startActivity(intent)
        finish()
    }
}