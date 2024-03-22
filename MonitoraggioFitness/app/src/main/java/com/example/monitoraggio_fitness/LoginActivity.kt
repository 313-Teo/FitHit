package com.example.monitoraggio_fitness
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private lateinit var resultText: TextView
    private lateinit var signupText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.login)
        signupText = findViewById(R.id.signup)
        resultText = findViewById(R.id.result)

        loginButton.setOnClickListener {
            loginUser()
        }

        signupText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val url = getString(R.string.url) + "login"

        val jsonParams = JSONObject()
        jsonParams.put("email", email)
        jsonParams.put("password", password)

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                val token = response.getString("token")
                val account = response.getString("account")

                if (token.isNotEmpty()) {
                    saveToken(token)

                    if (account == "Atleta") {
                        val intent = Intent(this, HomeAthleteActivity::class.java)
                        startActivity(intent)
                    } else if (account == "Trainer") {
                        val intent = Intent(this, HomeTrainerActivity::class.java)
                        startActivity(intent)
                    } else {
                        resultText.text = "Tipo di account non supportato"
                    }
                } else {
                    resultText.text = "Errore durante l'autenticazione"
                }
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

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    private fun logoutUser() {
        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("token")
        editor.apply()
    }


}
