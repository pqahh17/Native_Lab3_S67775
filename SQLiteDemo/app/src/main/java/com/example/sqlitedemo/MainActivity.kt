package com.example.sqlitedemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etUserId: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnViewSorted: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)

        // Initialize UI components
        etName = findViewById(R.id.et_name)
        etAge = findViewById(R.id.et_age)
        etUserId = findViewById(R.id.et_user_id) // New field for user ID
        btnAdd = findViewById(R.id.btn_add)
        btnView = findViewById(R.id.btn_view)
        btnUpdate = findViewById(R.id.btn_update) // Button for update
        btnDelete = findViewById(R.id.btn_delete) // Button for delete
        btnViewSorted = findViewById(R.id.btn_view_sorted) // Button for sorted view
        tvResult = findViewById(R.id.tv_result)

        // Add user
        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val age = etAge.text.toString().toIntOrNull()

            if (name.isBlank() || age == null) {
                Toast.makeText(this, "Please enter a valid name and age", Toast.LENGTH_SHORT).show()
            } else {
                val success = databaseHelper.addUser(name, age)
                if (success) {
                    Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                    etName.text.clear()
                    etAge.text.clear()
                } else {
                    Toast.makeText(this, "Failed to add user", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // View all users
        btnView.setOnClickListener {
            val users = databaseHelper.getAllUsers()
            tvResult.text = if (users.isNotEmpty()) users.joinToString("\n") else "No users found"
        }

        // Update user
        btnUpdate.setOnClickListener {
            val userId = etUserId.text.toString().toIntOrNull()
            val name = etName.text.toString()
            val age = etAge.text.toString().toIntOrNull()

            if (userId == null || name.isBlank() || age == null) {
                Toast.makeText(this, "Please enter a valid user ID, name, and age", Toast.LENGTH_SHORT).show()
            } else {
                val success = databaseHelper.updateUser(userId, name, age)
                if (success) {
                    Toast.makeText(this, "User updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update user", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Delete user
        btnDelete.setOnClickListener {
            val userId = etUserId.text.toString().toIntOrNull()

            if (userId == null) {
                Toast.makeText(this, "Please enter a valid user ID", Toast.LENGTH_SHORT).show()
            } else {
                val success = databaseHelper.deleteUser(userId)
                if (success) {
                    Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // View users sorted by name
        btnViewSorted.setOnClickListener {
            val users = databaseHelper.getUsersSortedByName()
            tvResult.text = if (users.isNotEmpty()) users.joinToString("\n") else "No users found"
        }
    }
}
