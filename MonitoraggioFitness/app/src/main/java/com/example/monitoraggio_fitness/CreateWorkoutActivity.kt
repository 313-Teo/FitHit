package com.example.monitoraggio_fitness

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class CreateWorkoutActivity : AppCompatActivity() {

    private lateinit var exitButton: ImageButton
    private lateinit var createButton: Button
    private lateinit var addButton: Button
    private lateinit var exerciseContainerLayout: LinearLayout
    private lateinit var nomeEditText: EditText
    private lateinit var resultText: TextView
    private lateinit var spinner: Spinner

    private val selectedExercises = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_workout)

        nomeEditText = findViewById(R.id.nome)
        exitButton = findViewById(R.id.indietro)
        createButton = findViewById(R.id.crea)
        addButton = findViewById(R.id.aggiungi)
        resultText = findViewById(R.id.result)
        spinner = findViewById(R.id.spinner)
        exerciseContainerLayout = findViewById(R.id.list)

        val defaultExercise = "Seleziona esercizio"
        val exerciseList = mutableListOf(defaultExercise)

        val url = getString(R.string.url) + "workout/create/exercises"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val request = object : JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val exerciseArray = response.getJSONArray("esercizi")
                for (i in 0 until exerciseArray.length()) {
                    val exerciseName = exerciseArray.getString(i)
                    exerciseList.add(exerciseName)
                }
                adapter.notifyDataSetChanged()
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedExerciseName = exerciseList[position]

                if (selectedExerciseName != defaultExercise) {
                    selectedExercises.add(selectedExerciseName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        exitButton.setOnClickListener {
            val intent = Intent(this, HomeTrainerActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            addExercise()
        }

        createButton.setOnClickListener {
            createWorkout()
        }
    }

    private fun addExercise() {
        exerciseContainerLayout.removeAllViews()

        for (exercise in selectedExercises) {
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

            exerciseContainerLayout.addView(exerciseTextView)
        }

        spinner.setSelection(0)
    }

    private fun createWorkout() {
        val nome = nomeEditText.text.toString()
        val selectedExercisesList: List<String> = selectedExercises.toList()

        val codice = "WR" + (100..999).random().toString()

        val url = getString(R.string.url) + "workout/create"

        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val jsonParams = JSONObject()
        jsonParams.put("esercizi", JSONArray(selectedExercisesList))
        jsonParams.put("nome", nome)
        jsonParams.put("codice", codice)

        val request = object : JsonObjectRequest(
            Request.Method.POST, url, jsonParams,
            { response ->
                val message = response.getString("message")
                resultText.text = message

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, HomeTrainerActivity::class.java)
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
