package com.example.estudiantesappsergio.model

data class Student(val id: Int, val name: String, val surname: String, val subjects: MutableList<Subject>) {

    fun subjectsToString(): String {
        val stringBuilder = StringBuilder()

        for (subject in subjects)
            stringBuilder.append("${subject.name}; ")

        return stringBuilder.toString()
    }
}