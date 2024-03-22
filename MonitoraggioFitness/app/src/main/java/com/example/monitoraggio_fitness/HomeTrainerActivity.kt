package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import android.widget.LinearLayout

class HomeTrainerActivity : AppCompatActivity() {

    private lateinit var buongiornoText: TextView
    private lateinit var creaWorkout: LinearLayout
    private lateinit var atleti: LinearLayout
    private lateinit var statistiche: LinearLayout

    private lateinit var impostazioniButton: ImageButton
    private lateinit var profiloButton: ImageButton

    private lateinit var creaButton: ImageButton
    private lateinit var atletaButton: ImageButton
    private lateinit var statsButton: ImageButton

    private lateinit var nome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_trainer)

        impostazioniButton = findViewById(R.id.impostazioni)
        profiloButton = findViewById(R.id.profilo)

        buongiornoText = findViewById(R.id.buongiorno)
        creaWorkout = findViewById(R.id.creaWorkout)
        atleti = findViewById(R.id.atleti)
        statistiche = findViewById(R.id.statistiche)

        creaButton = findViewById(R.id.crea)
        atletaButton = findViewById(R.id.atleta)
        statsButton = findViewById(R.id.stats)

        impostazioniButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        profiloButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        creaWorkout.setOnClickListener {
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        atleti.setOnClickListener {
            val intent = Intent(this, MyAthletesActivity::class.java)
            startActivity(intent)
        }

        statistiche.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        creaButton.setOnClickListener {
            val intent = Intent(this, CreateWorkoutActivity::class.java)
            startActivity(intent)
        }

        atletaButton.setOnClickListener {
            val intent = Intent(this, MyAthletesActivity::class.java)
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
                nome = response.getString("nome")
                buongiorno(nome)
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