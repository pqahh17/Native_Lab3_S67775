package com.example.recyclerviewsqlitedemo

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnAddUser: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        btnAddUser = findViewById(R.id.btn_add_user)

        recyclerView.layoutManager = LinearLayoutManager(this)
        loadUsers()

        btnAddUser.setOnClickListener {
            showAddUserDialog()
        }
    }

    private fun loadUsers() {
        val users = databaseHelper.getAllUsers()
        userAdapter = UserAdapter(users)
        recyclerView.adapter = userAdapter
    }

    private fun showAddUserDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null)
        val etName = dialogView.findViewById<EditText>(R.id.et_dialog_name)
        val etAge = dialogView.findViewById<EditText>(R.id.et_dialog_age)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Add User")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = etName.text.toString()
                val age = etAge.text.toString().toIntOrNull()

                if (name.isNotBlank() && age != null) {
                    val result = databaseHelper.addUser(name, age)
                    if (result != -1L) {
                        Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show()
                        loadUsers()
                    } else {
                        Toast.makeText(this, "Error Adding User", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()
    }
}
