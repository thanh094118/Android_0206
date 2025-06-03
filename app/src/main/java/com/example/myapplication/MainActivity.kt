package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.adapter.StudentAdapter
import com.example.myapplication.ui.viewmodel.StudentViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val studentViewModel: StudentViewModel by viewModels()
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeStudents()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter { student ->
            // Click để sửa sinh viên
            val intent = Intent(this, AddEditStudentActivity::class.java)
            intent.putExtra("student_id", student.id)
            intent.putExtra("student_name", student.fullName)
            intent.putExtra("student_mssv", student.studentId)
            startActivity(intent)
        }

        binding.recyclerViewStudents.adapter = adapter
        binding.recyclerViewStudents.layoutManager = LinearLayoutManager(this)
    }

    private fun observeStudents() {
        studentViewModel.allStudents.observe(this) { students ->
            students?.let { adapter.submitList(it) }
        }
    }

    private fun setupClickListeners() {
        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, AddEditStudentActivity::class.java)
            startActivity(intent)
        }
    }
}