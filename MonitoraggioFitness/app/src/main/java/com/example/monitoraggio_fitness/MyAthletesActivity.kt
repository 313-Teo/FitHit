package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MyAthletesActivity : AppCompatActivity() {

    private lateinit var exitButton: ImageButton
    private lateinit var addButton: Button
    private lateinit var athletesContainerLayout: LinearLayout
    private lateinit var codiceEditText: EditText
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_athletes)

        codiceEditText = findViewById(R.id.codice)
        exitButton = findViewById(R.id.indietro)
        addButton = findViewById(R.id.aggiungi)
        resultText = findViewById(R.id.result)
        athletesContainerLayout = findViewById(R.id.list)

        val url = getString(R.string.url) + "myathletes"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val athleteArray = response.getJSONArray("atleti")
                val emailArray = response.getJSONArray("emailAtleti")

                val athleteList = ArrayList<String>()
                val emailList = ArrayList<String>()

                for (i in 0 until athleteArray.length()) {
                    val athlete = athleteArray.getString(i)
                    athleteList.add(athlete)

                    val email = emailArray.getString(i)
                    emailList.add(email)
                }

                for (index in athleteList.indices) {
                    val athlete = athleteList[index]
                    val email = emailList[index]

                    val athleteTextView = TextView(this)
                    athleteTextView.text = athlete

                    val params = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.margin_bottom))
                    athleteTextView.layoutParams = params

                    athleteTextView.setBackgroundResource(R.drawable.custom_edittext)
                    athleteTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    athleteTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                    athleteTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    athleteTextView.setPadding(0, resources.getDimensionPixelSize(R.dimen.padding), 0, resources.getDimensionPixelSize(R.dimen.padding))

                    athleteTextView.setOnClickListener {
                        val intent = Intent(this, CardActivity::class.java)
                        intent.putExtra("email", email)
                        startActivity(intent)
                    }

                    athletesContainerLayout.addView(athleteTextView)
                }
            },
            { error ->
                val errorData = String(error.networkResponse.data)
                val jsonError = JSONObject(errorData)
                val errorMessage = jsonError.optString("error")
                resultText.text = errorMessage
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)

        exitButton.setOnClickListener {
            val intent = Intent(this, HomeTrainerActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            addAthlete()
        }

    }

    private fun addAthlete() {
        val codice = codiceEditText.text.toString()

        val url = getString(R.string.url) + "myathletes/add"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val jsonParams = JSONObject()
        jsonParams.put("codice", codice)

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                val message = response.optString("message")
                resultText.text = message

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MyAthletesActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 3000)
            },
            { error ->
                val errorData = String(error.networkResponse.data)
                val jsonError = JSONObject(errorData)
                val errorMessage = jsonError.getString("error")
                resultText.text = errorMessage
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)

    }
}
