package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class HomeAthleteActivity : AppCompatActivity() {

    private lateinit var buongiornoText: TextView
    private lateinit var nuovoWorkout: LinearLayout
    private lateinit var schedaAtleta: LinearLayout
    private lateinit var statistiche: LinearLayout

    private lateinit var impostazioniButton: ImageButton
    private lateinit var profiloButton: ImageButton

    private lateinit var workoutButton: ImageButton
    private lateinit var schedaButton: ImageButton
    private lateinit var statsButton: ImageButton

    private lateinit var nome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_athlete)

        impostazioniButton = findViewById(R.id.impostazioni)
        profiloButton = findViewById(R.id.profilo)

        buongiornoText = findViewById(R.id.buongiorno)
        nuovoWorkout = findViewById(R.id.nuovoWorkout)
        schedaAtleta = findViewById(R.id.schedaAtleta)
        statistiche = findViewById(R.id.statistiche)

        workoutButton = findViewById(R.id.workout)
        schedaButton = findViewById(R.id.scheda)
        statsButton = findViewById(R.id.stats)

        impostazioniButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        profiloButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        nuovoWorkout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        schedaAtleta.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)
        }

        statistiche.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        workoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        schedaButton.setOnClickListener {
            val intent = Intent(this, CardActivity::class.java)
            startActivity(intent)
        }

        statsButton.setOnClickListener {
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
                    nome = response.getString("nome")
                    buongiorno(nome)
                } catch (e: Exception) {
                }
            },
            { error ->
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)

    }

    private fun buongiorno(nome: String) {
        buongiornoText.text = "Buongiorno, $nome"
    }
}