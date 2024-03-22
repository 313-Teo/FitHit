package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class WorkoutPreviewActivity : AppCompatActivity() {

    private lateinit var exitButton: ImageButton
    private lateinit var workoutText: TextView
    private lateinit var timeText: TextView
    private lateinit var workoutContainerLayout: LinearLayout
    private lateinit var startButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_preview)

        exitButton = findViewById(R.id.indietro)
        workoutText = findViewById(R.id.workout)
        timeText = findViewById(R.id.time)
        workoutContainerLayout = findViewById(R.id.list)
        startButton = findViewById(R.id.startButton)

        exitButton.setOnClickListener {
            val intent = Intent(this, NewWorkoutActivity::class.java)
            startActivity(intent)
        }

        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val intent = intent
        val workout = intent.getStringExtra("workout")
        val card = intent.getStringExtra("card")
        val emailAtleta = intent.getStringExtra("email")

        if (card == "yes") {
            startButton.visibility = View.INVISIBLE
            startButton.isEnabled = false

            if(emailAtleta != null){
                exitButton.setOnClickListener {
                    val intent = Intent(this, CardActivity::class.java)
                    intent.putExtra("email", emailAtleta)
                    startActivity(intent)
                }
            } else {
                exitButton.setOnClickListener {
                    val intent = Intent(this, CardActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        val url = getString(R.string.url) + "workout/exercises"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val jsonParams = JSONObject()
        jsonParams.put("workout", workout)

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                val workout = response.getString("workout")
                workoutText.text = workout

                val tempo = response.getString("tempo")
                val tempoFormattato = if (tempo.length == 5) {
                    "$tempo:00"
                } else {
                    tempo
                }

                timeText.text = "TEMPO TOTALE:   $tempoFormattato"

                val exerciseArray = response.getJSONArray("esercizi")
                val exerciseList = ArrayList<String>()
                for (i in 0 until exerciseArray.length()) {
                    val exercise = exerciseArray.getString(i)
                    exerciseList.add(exercise)
                }

                for (exercise in exerciseList) {
                    val exerciseTextView = TextView(this)
                    exerciseTextView.text = exercise

                    val params = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, 0, 0, resources.getDimensionPixelSize(R.dimen.margin_bottom))
                    exerciseTextView.layoutParams = params

                    exerciseTextView.setBackgroundResource(R.drawable.custom_edittext)
                    exerciseTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
                    exerciseTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                    exerciseTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                    exerciseTextView.setPadding(0, resources.getDimensionPixelSize(R.dimen.padding), 0, resources.getDimensionPixelSize(R.dimen.padding))

                    workoutContainerLayout.addView(exerciseTextView)
                }

            },
            { error ->
                val errorData = String(error.networkResponse.data)
                val jsonError = JSONObject(errorData)
                val errorMessage = jsonError.optString("error")
                workoutText.text = errorMessage
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }
        }

        Volley.newRequestQueue(this).add(request)

    }

}
