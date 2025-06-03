package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.dao.StudentDao
import com.example.myapplication.data.entity.Student

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents()

    suspend fun insert(student: Student) {
        studentDao.insert(student)
    }

    suspend fun update(student: Student) {
        studentDao.update(student)
    }

    suspend fun delete(student: Student) {
        studentDao.delete(student)
    }

    suspend fun getStudentById(id: Int): Student? {
        return studentDao.getStudentById(id)
    }
}