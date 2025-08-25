package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var dbHelper: StudentDatabaseHelper
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        dbHelper = StudentDatabaseHelper(this)
        student = intent.getSerializableExtra("student") as Student

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cập nhật sinh viên"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val edtName = findViewById<EditText>(R.id.editTextName)
        val edtMSSV = findViewById<EditText>(R.id.editTextmssv)
        val edtEmail = findViewById<EditText>(R.id.editTextEmail)
        val edtPhone = findViewById<EditText>(R.id.editTextPhone)
        val btnUpdate = findViewById<Button>(R.id.buttonUpdate)

        edtName.setText(student.name)
        edtMSSV.setText(student.mssv)
        edtEmail.setText(student.email)
        edtPhone.setText(student.phone)

        btnUpdate.setOnClickListener {
            student.name = edtName.text.toString()
            student.mssv = edtMSSV.text.toString()
            student.email = edtEmail.text.toString()
            student.phone = edtPhone.text.toString()

            if (student.name.isBlank() || student.mssv.isBlank()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dbHelper.updateStudent(student)
            finish()
        }
    }
}
