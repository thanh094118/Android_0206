package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "students.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                mssv TEXT,
                email TEXT,
                phone TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun addStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.insert("students", null, values)
    }

    fun getAllStudents(): List<Student> {
        val students = mutableListOf<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM students", null)
        if (cursor.moveToFirst()) {
            do {
                val student = Student(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    mssv = cursor.getString(cursor.getColumnIndexOrThrow("mssv")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"))
                )
                students.add(student)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return students
    }

    fun updateStudent(student: Student): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.update("students", values, "id = ?", arrayOf(student.id.toString()))
    }

    fun deleteStudent(id: Int): Int {
        val db = writableDatabase
        return db.delete("students", "id = ?", arrayOf(id.toString()))
    }
}
