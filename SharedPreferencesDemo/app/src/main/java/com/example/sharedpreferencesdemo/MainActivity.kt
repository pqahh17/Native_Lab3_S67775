package com.example.sharedpreferencesdemo

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var tvGreeting: TextView
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etCity: EditText
    private lateinit var btnSave: Button
    private lateinit var btnLoad: Button
    private lateinit var btnClear: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPreferences", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        // Initialize UI components
        tvGreeting = findViewById(R.id.tv_greeting)
        etName = findViewById(R.id.et_name)
        etAge = findViewById(R.id.et_age) // Additional field
        etCity = findViewById(R.id.et_city) // Additional field
        btnSave = findViewById(R.id.btn_save)
        btnLoad = findViewById(R.id.btn_load)
        btnClear = findViewById(R.id.btn_clear)

        // Set button listeners
        btnSave.setOnClickListener { saveData() }
        btnLoad.setOnClickListener { loadData() }
        btnClear.setOnClickListener { clearData() }
    }

    private fun saveData() {
        val name = etName.text.toString()
        val age = etAge.text.toString()
        val city = etCity.text.toString()

        if (name.isEmpty() || age.isEmpty() || city.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        editor.putString("userName", name)
        editor.putString("userAge", age)
        editor.putString("userCity", city)
        editor.apply()

        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadData() {
        val name = sharedPreferences.getString("userName", "No Name Found")
        val age = sharedPreferences.getString("userAge", "No Age Found")
        val city = sharedPreferences.getString("userCity", "No City Found")

        tvGreeting.text = "Hello, $name! You are $age years old from $city."
    }

    private fun clearData() {
        editor.clear()
        editor.apply()
        Toast.makeText(this, "Data Cleared", Toast.LENGTH_SHORT).show()
        tvGreeting.text = "Hello, User!"
    }
}
