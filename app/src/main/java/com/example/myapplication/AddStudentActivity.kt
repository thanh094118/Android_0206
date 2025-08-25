package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class AddStudentActivity : AppCompatActivity() {

    private lateinit var dbHelper: StudentDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        dbHelper = StudentDatabaseHelper(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Thêm sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val edtName = findViewById<EditText>(R.id.editTextName)
        val edtMSSV = findViewById<EditText>(R.id.editTextmssv)
        val edtEmail = findViewById<EditText>(R.id.editTextEmail)
        val edtPhone = findViewById<EditText>(R.id.editTextPhone)
        val btnSave = findViewById<Button>(R.id.buttonSave)

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val mssv = edtMSSV.text.toString()
            val email = edtEmail.text.toString()
            val phone = edtPhone.text.toString()

            if (name.isBlank() || mssv.isBlank()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ họ tên và MSSV", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val student = Student(name = name, mssv = mssv, email = email, phone = phone)
            dbHelper.addStudent(student)
            finish()
        }
    }
}