package com.example.myapplication.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.entity.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM students ORDER BY fullName ASC")
    fun getAllStudents(): LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(student: Student)

    @Update
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAll()

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): Student?
}