package com.example.estudiantesappsergio

import com.example.estudiantesappsergio.model.Student
import com.example.estudiantesappsergio.model.Subject
import com.example.estudiantesappsergio.model.Teacher
import org.json.JSONObject


class JsonHandler {
    companion object {
        var subjects = mutableListOf<Subject>()
        private var teachers = mutableListOf<Teacher>()
        private var students = mutableListOf<Student>()

        fun parseJsonData(jsonData: JSONObject) {
            val subjectData = jsonData.getJSONArray("asignaturas")
            val teacherData = jsonData.getJSONArray("profesores")
            val studentData = jsonData.getJSONArray("alumnos")

            if (subjects.isNotEmpty() && teachers.isNotEmpty() && students.isNotEmpty())
                return

            for (i in 0 until subjectData.length()) {
                val name = subjectData.getString(i)
                subjects.add(Subject(name))
            }

            for (i in 0 until teacherData.length()) {
                val teacherEntry = teacherData.getJSONObject(i)
                val id = teacherEntry.getInt("codigo")
                val name = teacherEntry.getString("nombre")
                val surname = teacherEntry.getString("apellido")
                val subjectString = teacherEntry.getString("asignatura")
                val subjectObject = subjects.first { it.name == subjectString }

                teachers.add(Teacher(id, name, surname, subjectObject))
            }


            for (i in 0 until studentData.length()) {
                val studentEntry = studentData.getJSONObject(i)

                val id = studentEntry.getInt("codigo")
                val name = studentEntry.getString("nombre")
                val surname = studentEntry.getString("apellido")
                val subjectStrings = studentEntry.getJSONArray("Asignaturas")
                val subjectObjects = mutableListOf<Subject>()

                for (j in 0 until subjectStrings.length())
                    subjectObjects.add(subjects.first { it.name == subjectStrings.getString(j) })

                students.add(Student(id, name, surname, subjectObjects))
            }
        }

        fun getTeacherBySubject(subject: Subject): Teacher = teachers.first {
            it.subject == subject
        }

        fun getStudentsBySubject(subject: Subject): List<Student> = students.filter {
            it.subjects.contains(subject)
        }

        fun getStudentById(id: Int): Student = students.first {
            it.id == id
        }
    }
}