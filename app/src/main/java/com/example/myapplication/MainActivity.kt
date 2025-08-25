package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), StudentAdapter.OnStudentActionListener {

    private lateinit var dbHelper: StudentDatabaseHelper
    private lateinit var adapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private val students = mutableListOf<Student>()
    private val PREFS_NAME = "settings"
    private val KEY_BG_COLOR = "background_color"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Tải màu nền đã lưu và áp dụng
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedColor = prefs.getInt(KEY_BG_COLOR, android.graphics.Color.WHITE)
        findViewById<CoordinatorLayout>(R.id.main_layout)?.setBackgroundColor(savedColor)

// Bắt sự kiện click nút màu
        findViewById<Button>(R.id.btnRed).setOnClickListener {
            findViewById<CoordinatorLayout>(R.id.main_layout)?.setBackgroundColor(android.graphics.Color.RED)
            prefs.edit().putInt(KEY_BG_COLOR, android.graphics.Color.RED).apply()
        }

        findViewById<Button>(R.id.btnGreen).setOnClickListener {
            findViewById<CoordinatorLayout>(R.id.main_layout)?.setBackgroundColor(android.graphics.Color.GREEN)
            prefs.edit().putInt(KEY_BG_COLOR, android.graphics.Color.GREEN).apply()
        }

        findViewById<Button>(R.id.btnDefault).setOnClickListener {
            findViewById<CoordinatorLayout>(R.id.main_layout)?.setBackgroundColor(android.graphics.Color.WHITE)
            prefs.edit().remove(KEY_BG_COLOR).apply()  // Xóa key để trở về mặc định
        }



        dbHelper = StudentDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = StudentAdapter(this, students, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun loadStudents() {
        students.clear()
        students.addAll(dbHelper.getAllStudents())
        adapter.notifyDataSetChanged()
    }

    override fun onUpdate(student: Student) {
        val intent = Intent(this, UpdateStudentActivity::class.java)
        intent.putExtra("student", student)
        startActivity(intent)
    }

    override fun onDelete(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                dbHelper.deleteStudent(student.id)
                loadStudents()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onCall(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
        startActivity(intent)
    }

    override fun onEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
        startActivity(intent)
    }
}
