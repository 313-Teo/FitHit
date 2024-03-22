package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class CardActivity : AppCompatActivity() {

    private lateinit var nomecognomeText: TextView
    private lateinit var exitButton: ImageButton
    private lateinit var workoutContainerLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        nomecognomeText = findViewById(R.id.nomecognome)
        exitButton = findViewById(R.id.indietro)
        workoutContainerLayout = findViewById(R.id.list)

        val emailAtleta = intent.getStringExtra("email")

        if (emailAtleta != null) {

            val url = getString(R.string.url) + "myathletes/cards"

            val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", "")

            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"

            val jsonParams = JSONObject()
            jsonParams.put("email", emailAtleta)

            val request = object : JsonObjectRequest(
                Request.Method.POST, url, jsonParams,
                { response ->
                    val nome = response.getString("nome")
                    val cognome = response.getString("cognome")

                    val workoutArray = response.getJSONArray("workout")
                    val workoutList = ArrayList<String>()
                    for (i in 0 until workoutArray.length()) {
                        val workout = workoutArray.getString(i)
                        workoutList.add(workout)
                    }

                    nomecognomeText.text = "$nome $cognome"

                    for (workout in workoutList.reversed()) {
                        val workoutTextView = TextView(this)
                        workoutTextView.text = workout

                        val params = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.margin_bottom))
                        workoutTextView.layoutParams = params

                        workoutTextView.setBackgroundResource(R.drawable.custom_edittext)
                        workoutTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
                        workoutTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        workoutTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        workoutTextView.setPadding(0, resources.getDimensionPixelSize(R.dimen.padding), 0, resources.getDimensionPixelSize(R.dimen.padding))

                        workoutTextView.setOnClickListener {
                            val intent = Intent(this, WorkoutPreviewActivity::class.java)
                            intent.putExtra("workout", workout)
                            intent.putExtra("card", "yes")
                            intent.putExtra("email", emailAtleta)
                            startActivity(intent)
                        }

                        workoutContainerLayout.addView(workoutTextView)
                    }
                },
                { error ->
                    val errorData = String(error.networkResponse.data)
                    val jsonError = JSONObject(errorData)
                    val errorMessage = jsonError.getString("error")
                    nomecognomeText.text = errorMessage
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers
                }
            }

            Volley.newRequestQueue(this).add(request)

            exitButton.setOnClickListener {
                val intent = Intent(this, MyAthletesActivity::class.java)
                startActivity(intent)
            }

        } else {

            val url = getString(R.string.url) + "card"

            val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
            val token = sharedPreferences.getString("token", "")

            val headers = HashMap<String, String>()
            headers["Authorization"] = "Bearer $token"

            val request = object : JsonObjectRequest(
                Request.Method.GET, url, null,
                { response ->
                    val nome = response.getString("nome")
                    val cognome = response.getString("cognome")

                    val workoutArray = response.getJSONArray("workout")
                    val workoutList = ArrayList<String>()
                    for (i in 0 until workoutArray.length()) {
                        val workout = workoutArray.getString(i)
                        workoutList.add(workout)
                    }

                    nomecognomeText.text = "$nome $cognome"

                    for (workout in workoutList.reversed()) {
                        val workoutTextView = TextView(this)
                        workoutTextView.text = workout

                        val params = LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                        params.setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.margin_bottom))
                        workoutTextView.layoutParams = params

                        workoutTextView.setBackgroundResource(R.drawable.custom_edittext)
                        workoutTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
                        workoutTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        workoutTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                        workoutTextView.setPadding(0, resources.getDimensionPixelSize(R.dimen.padding), 0, resources.getDimensionPixelSize(R.dimen.padding))

                        workoutTextView.setOnClickListener {
                            val intent = Intent(this, WorkoutPreviewActivity::class.java)
                            intent.putExtra("workout", workout)
                            intent.putExtra("card", "yes")
                            startActivity(intent)
                        }

                        workoutContainerLayout.addView(workoutTextView)
                    }
                },
                { error ->
                    val errorData = String(error.networkResponse.data)
                    val jsonError = JSONObject(errorData)
                    val errorMessage = jsonError.getString("error")
                    nomecognomeText.text = errorMessage
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    return headers
                }
            }

            Volley.newRequestQueue(this).add(request)

            exitButton.setOnClickListener {
                val intent = Intent(this, HomeAthleteActivity::class.java)
                startActivity(intent)
            }

        }

    }

}
