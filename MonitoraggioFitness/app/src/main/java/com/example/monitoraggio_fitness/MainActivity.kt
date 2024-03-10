package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var athleteButton: Button
    private lateinit var trainerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginButton = findViewById(R.id.login)
        registerButton = findViewById(R.id.register)
        athleteButton = findViewById(R.id.atleta)
        trainerButton = findViewById(R.id.trainer)

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        athleteButton.setOnClickListener {
            val intent = Intent(this, HomeAthleteActivity::class.java)
            startActivity(intent)
        }

        trainerButton.setOnClickListener {
            val intent = Intent(this, HomeTrainerActivity::class.java)
            startActivity(intent)
        }

    }
}
