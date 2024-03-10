package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class CardActivity : AppCompatActivity() {

    private lateinit var nomecognomeText: TextView
    private lateinit var workout1Text: TextView
    private lateinit var workout2Text: TextView
    private lateinit var workout3Text: TextView
    private lateinit var exitButton: ImageButton

    private lateinit var account: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        nomecognomeText = findViewById(R.id.nomecognome)
        workout1Text = findViewById(R.id.workout1)
        workout2Text = findViewById(R.id.workout2)
        workout3Text = findViewById(R.id.workout3)
        exitButton = findViewById(R.id.indietro)

        val url = getString(R.string.url) + "card"

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
                    val workout1 = response.getString("workout1")
                    val workout2 = response.getString("workout2")
                    val workout3 = response.getString("workout3")
                    account = response.getString("account")

                    nomecognomeText.text = nome + " " + cognome
                    workout1Text.text = workout1
                    workout2Text.text = workout2
                    workout3Text.text = workout3

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

        exitButton.setOnClickListener {
            if (account == "Atleta") {
                val intent = Intent(this, HomeAthleteActivity::class.java)
                startActivity(intent)
            } else if (account == "Trainer") {
                val intent = Intent(this, HomeTrainerActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun Error() {
        nomecognomeText.text = "Errore"
        workout1Text.text = "Errore"
        workout2Text.text = "Errore"
        workout3Text.text = "Errore"
    }

}
