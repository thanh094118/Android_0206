package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.data.entity.Student
import com.example.myapplication.databinding.ActivityAddEditStudentBinding
import com.example.myapplication.ui.viewmodel.StudentViewModel
import kotlinx.coroutines.launch

class AddEditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditStudentBinding
    private val studentViewModel: StudentViewModel by viewModels()
    private var studentId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkEditMode()
        setupClickListeners()
    }

    private fun checkEditMode() {
        studentId = intent.getIntExtra("student_id", -1)
        if (studentId != -1) {
            isEditMode = true
            binding.textViewTitle.text = "SỬA THÔNG TIN SINH VIÊN"
            binding.buttonDelete.visibility = android.view.View.VISIBLE

            // Load thông tin sinh viên
            val studentName = intent.getStringExtra("student_name")
            val studentMssv = intent.getStringExtra("student_mssv")

            binding.editTextName.setText(studentName)
            binding.editTextStudentId.setText(studentMssv)
        }
    }

    private fun setupClickListeners() {
        binding.buttonSave.setOnClickListener {
            saveStudent()
        }

        binding.buttonDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun saveStudent() {
        val name = binding.editTextName.text.toString().trim()
        val studentIdText = binding.editTextStudentId.text.toString().trim()

        if (name.isEmpty()) {
            binding.textInputLayoutName.error = "Vui lòng nhập họ tên"
            return
        } else {
            binding.textInputLayoutName.error = null
        }

        if (studentIdText.isEmpty()) {
            binding.textInputLayoutStudentId.error = "Vui lòng nhập MSSV"
            return
        } else {
            binding.textInputLayoutStudentId.error = null
        }

        if (isEditMode) {
            // Cập nhật sinh viên
            val updatedStudent = Student(studentId, name, studentIdText)
            studentViewModel.update(updatedStudent)
            Toast.makeText(this, "Đã cập nhật thông tin sinh viên", Toast.LENGTH_SHORT).show()
        } else {
            // Thêm sinh viên mới
            val newStudent = Student(fullName = name, studentId = studentIdText)
            studentViewModel.insert(newStudent)
            Toast.makeText(this, "Đã thêm sinh viên mới", Toast.LENGTH_SHORT).show()
        }

        finish()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                deleteStudent()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun deleteStudent() {
        lifecycleScope.launch {
            val student = studentViewModel.getStudentById(studentId)
            student?.let {
                studentViewModel.delete(it)
                Toast.makeText(this@AddEditStudentActivity, "Đã xóa sinh viên", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}