package com.example.estudiantesappsergio.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.estudiantesappsergio.JsonHandler
import com.example.estudiantesappsergio.R



class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val studentId = args.studentId
        val student = JsonHandler.getStudentById(studentId)

        view.findViewById<TextView>(R.id.tv_student_id).also { it.text = "Codigo ${student.id}" }
        view.findViewById<TextView>(R.id.tv_student_name).also { it.text = "${student.name} ${student.surname}" }
        view.findViewById<TextView>(R.id.tv_student_subjects).also { it.text = student.subjectsToString() }
    }
}