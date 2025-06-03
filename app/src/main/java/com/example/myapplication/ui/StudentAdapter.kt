package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.entity.Student

class StudentAdapter(private val onItemClickListener: (Student) -> Unit) :
    ListAdapter<Student, StudentAdapter.StudentViewHolder>(StudentsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = getItem(position)
        holder.bind(currentStudent)
        holder.itemView.setOnClickListener {
            onItemClickListener(currentStudent)
        }
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val studentNameTextView: TextView = itemView.findViewById(R.id.textViewStudentName)
        private val studentIdTextView: TextView = itemView.findViewById(R.id.textViewStudentId)

        fun bind(student: Student?) {
            studentNameTextView.text = student?.fullName
            studentIdTextView.text = "MSSV: ${student?.studentId}"
        }

        companion object {
            fun create(parent: ViewGroup): StudentViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_student, parent, false)
                return StudentViewHolder(view)
            }
        }
    }

    class StudentsComparator : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
            return oldItem.id == newItem.id
        }
    }
}