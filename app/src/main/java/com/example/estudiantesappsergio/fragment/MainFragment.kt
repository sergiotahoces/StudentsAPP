package com.example.estudiantesappsergio.fragment

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.estudiantesappsergio.JsonHandler
import com.example.estudiantesappsergio.R
import com.example.estudiantesappsergio.model.CustomAdapter
import com.example.estudiantesappsergio.model.Subject



import org.json.JSONObject

class MainFragment : Fragment(), AdapterView.OnItemSelectedListener, CustomAdapter.ItemClickListener {
    private lateinit var spinnerAdapter: ArrayAdapter<Subject>
    private lateinit var recyclerAdapter: CustomAdapter
    private lateinit var tvTeacherName: TextView
    private lateinit var tvTeacherId: TextView
    private lateinit var tvStudentName: TextView
    private lateinit var tvStudentId: TextView
    private lateinit var tvStudentSubjects: TextView
    private lateinit var rvStudents: RecyclerView

     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val plainText = resources.openRawResource(R.raw.data).bufferedReader().use { it.readText() }
        val jsonData = JSONObject(plainText)
        JsonHandler.parseJsonData(jsonData)

        val spinnerSubjects: Spinner

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            spinnerSubjects = view.findViewById(R.id.spinner_subjects)
            rvStudents = view.findViewById(R.id.rv_students)
            tvTeacherName = view.findViewById(R.id.tv_teacher_name)
            tvTeacherId = view.findViewById(R.id.tv_teacher_id)
        } else {
            spinnerSubjects = view.findViewById(R.id.land_spinner_subjects)
            rvStudents = view.findViewById(R.id.land_rv_students)
            tvTeacherName = view.findViewById(R.id.land_tv_teacher_name)
            tvTeacherId = view.findViewById(R.id.land_tv_teacher_id)
            tvStudentName = view.findViewById(R.id.land_tv_student_name)
            tvStudentId = view.findViewById(R.id.land_tv_student_id)
            tvStudentSubjects = view.findViewById(R.id.land_tv_student_subjects)
        }

        spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, JsonHandler.subjects)
        spinnerSubjects.adapter = spinnerAdapter
        spinnerSubjects.onItemSelectedListener = this

        rvStudents.setHasFixedSize(true)
        rvStudents.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("SetTextI18n")
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val teacher = JsonHandler.getTeacherBySubject(spinnerAdapter.getItem(position)!!)
        tvTeacherName.text = "${teacher.name} ${teacher.surname}"
        tvTeacherId.text = "Codigo ${teacher.id}"

        //En caso de estar en "land" reinicia datos del alumno si cambias asignatura
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvStudentName.text = ""
            tvStudentId.text = ""
            tvStudentSubjects.text = ""
        }

        recyclerAdapter = CustomAdapter(requireContext(), JsonHandler.getStudentsBySubject(spinnerAdapter.getItem(position)!!))
        recyclerAdapter.setClickListener(this)
        rvStudents.adapter = recyclerAdapter
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    @SuppressLint("SetTextI18n")
    override fun onItemClick(view: View?, position: Int) {
        val student = recyclerAdapter.getItem(position)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tvStudentName.text = "${student.name} ${student.surname}"
            tvStudentId.text = "Codigo ${student.id}"
            tvStudentSubjects.text = student.subjectsToString()
        } else {
            val action = MainFragmentDirections.mainFragmentToDetailFragment(student.id)
            findNavController().navigate(action)
        }
    }
}