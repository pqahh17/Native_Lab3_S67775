package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Companion object for constants
    companion object {
        const val DATABASE_NAME = "UserDatabase.db" // Database file name
        const val DATABASE_VERSION = 1             // Database version
        const val TABLE_NAME = "Users"             // Table name
        const val COLUMN_ID = "id"                 // Column for ID (Primary Key)
        const val COLUMN_NAME = "name"             // Column for User Name
        const val COLUMN_AGE = "age"               // Column for User Age
    }

    // Create table SQL query
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_AGE INTEGER)"
        db?.execSQL(createTable)
    }

    // Upgrade database logic
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Function to add a user to the database
    fun addUser(name: String, age: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    // Function to update a user in the database
    fun updateUser(id: Int, name: String, age: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_AGE, age)
        }
        val result = db.update(TABLE_NAME, values, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // Function to delete a user from the database
    fun deleteUser(id: Int): Boolean {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result > 0
    }

    // Function to retrieve users sorted by name
    fun getUsersSortedByName(): List<String> {
        val userList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME ASC", null)
        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME))
                val age = c.getInt(c.getColumnIndexOrThrow(COLUMN_AGE))
                userList.add("ID: $id, Name: $name, Age: $age")
            }
        }
        db.close()
        return userList
    }

    // Function to retrieve all users from the database
    fun getAllUsers(): List<String> {
        val userList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.use { c ->
            while (c.moveToNext()) {
                val id = c.getInt(c.getColumnIndexOrThrow(COLUMN_ID))
                val name = c.getString(c.getColumnIndexOrThrow(COLUMN_NAME))
                val age = c.getInt(c.getColumnIndexOrThrow(COLUMN_AGE))
                userList.add("ID: $id, Name: $name, Age: $age")
            }
        }
        db.close()
        return userList
    }
}
