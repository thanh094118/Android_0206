package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private val context: Context,
    private val students: MutableList<Student>,
    private val listener: OnStudentActionListener
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    interface OnStudentActionListener {
        fun onUpdate(student: Student)
        fun onDelete(student: Student)
        fun onCall(phone: String)
        fun onEmail(email: String)
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvMSSV: TextView = itemView.findViewById(R.id.tvMSSV)
        val btnMenu: ImageButton = itemView.findViewById(R.id.btnMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvMSSV.text = student.mssv

        holder.btnMenu.setOnClickListener {
            val popup = PopupMenu(context, holder.btnMenu)
            popup.menuInflater.inflate(R.menu.student_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_update -> listener.onUpdate(student)
                    R.id.menu_delete -> listener.onDelete(student)
                    R.id.menu_call -> listener.onCall(student.phone)
                    R.id.menu_email -> listener.onEmail(student.email)
                }
                true
            }
            popup.show()
        }
    }

    fun updateData(newStudents: List<Student>) {
        students.clear()
        students.addAll(newStudents)
        notifyDataSetChanged()
    }
}
