package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class ProfileActivity : AppCompatActivity() {

    private lateinit var nomeText: TextView
    private lateinit var cognomeText: TextView
    private lateinit var pesoText: TextView
    private lateinit var altezzaText: TextView
    private lateinit var accountText: TextView
    private lateinit var codiceText: TextView
    private lateinit var imageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nomeText = findViewById(R.id.nome)
        cognomeText = findViewById(R.id.cognome)
        pesoText = findViewById(R.id.peso)
        altezzaText = findViewById(R.id.altezza)
        accountText = findViewById(R.id.account)
        codiceText = findViewById(R.id.codice)
        imageButton = findViewById(R.id.indietro)

        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val url = getString(R.string.url) + "profile"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val nome = response.getString("nome")
                    val cognome = response.getString("cognome")
                    val peso = response.getDouble("peso")
                    val altezza = response.getDouble("altezza")
                    val account = response.getString("account")
                    val codice = response.getString("codice")

                    nomeText.text = nome
                    cognomeText.text = cognome
                    pesoText.text = "$peso Kg"
                    altezzaText.text = "$altezza cm"
                    accountText.text = account
                    codiceText.text = codice

                } catch (e: Exception) {
                    Error()
                }
            },
            { error ->
                Error()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)

    }

    private fun Error() {
        nomeText.text = "Errore"
        cognomeText.text = "Errore"
        pesoText.text = "Errore"
        altezzaText.text = "Errore"
        accountText.text = "Errore"
        codiceText.text = "Errore"
    }

}
