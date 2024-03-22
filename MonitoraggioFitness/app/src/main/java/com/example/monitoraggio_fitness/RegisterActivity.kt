package com.example.monitoraggio_fitness
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Handler
import android.os.Looper


class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nomeEditText: EditText
    private lateinit var cognomeEditText: EditText
    private lateinit var pesoEditText: EditText
    private lateinit var altezzaEditText: EditText

    private lateinit var atletaRadioButton: RadioButton
    private lateinit var trainerRadioButton: RadioButton
    private var selectedRadioButton: RadioButton? = null

    private lateinit var registerButton: Button
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        nomeEditText = findViewById(R.id.nome)
        cognomeEditText = findViewById(R.id.cognome)
        pesoEditText = findViewById(R.id.peso)
        altezzaEditText = findViewById(R.id.altezza)

        atletaRadioButton = findViewById(R.id.atleta)
        trainerRadioButton = findViewById(R.id.trainer)

        registerButton = findViewById(R.id.register)

        resultText = findViewById(R.id.result)

        atletaRadioButton.setOnClickListener {
            onRadioButtonClicked(atletaRadioButton)
        }

        trainerRadioButton.setOnClickListener {
            onRadioButtonClicked(trainerRadioButton)
        }

        registerButton.setOnClickListener {
            registerUser()
        }

        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            ),
            intArrayOf(
                Color.parseColor("#fc6c0c"),
                Color.parseColor("#808080")
            )
        )

        atletaRadioButton.buttonTintList = colorStateList
        trainerRadioButton.buttonTintList = colorStateList
    }

    private fun registerUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val nome = nomeEditText.text.toString()
        val cognome = cognomeEditText.text.toString()
        val peso = pesoEditText.text.toString()
        val altezza = altezzaEditText.text.toString()

        if (email.isEmpty() || password.isEmpty() || nome.isEmpty() || cognome.isEmpty() || peso.isEmpty() || altezza.isEmpty()) {
            resultText.text = "Inserisci tutti i campi richiesti"
            return
        }

        if (!atletaRadioButton.isChecked && !trainerRadioButton.isChecked) {
            resultText.text = "Seleziona il tipo di account"
            return
        }

        val account = if (atletaRadioButton.isChecked) "Atleta" else "Trainer"

        val codice = if (atletaRadioButton.isChecked) {
            "AT"
        } else {
            "TR"
        } + (100000..999999).random().toString()

        val url = getString(R.string.url) + "register"

        val jsonParams = JSONObject()
        jsonParams.put("codice", codice)
        jsonParams.put("email", email)
        jsonParams.put("password", password)
        jsonParams.put("nome", nome)
        jsonParams.put("cognome", cognome)
        jsonParams.put("peso", peso)
        jsonParams.put("altezza", altezza)
        jsonParams.put("account", account)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                val message = response.getString("message")
                resultText.text = message

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            },
            { error ->
                val errorData = String(error.networkResponse.data)
                val jsonError = JSONObject(errorData)
                val errorMessage = jsonError.getString("error")
                resultText.text = errorMessage
            }
        )

        Volley.newRequestQueue(this).add(request)
    }

    private fun onRadioButtonClicked(radioButton: RadioButton) {
        selectedRadioButton?.isChecked = false
        radioButton.isChecked = true
        selectedRadioButton = radioButton
    }

}
