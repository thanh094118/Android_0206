package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.database.StudentDatabase
import com.example.myapplication.data.entity.Student
import com.example.myapplication.data.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository
    val allStudents: LiveData<List<Student>>

    init {
        val studentDao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(studentDao)
        allStudents = repository.allStudents
    }

    fun insert(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(student)
    }

    fun update(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(student)
    }

    fun delete(student: Student) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(student)
    }

    suspend fun getStudentById(id: Int): Student? {
        return repository.getStudentById(id)
    }
}