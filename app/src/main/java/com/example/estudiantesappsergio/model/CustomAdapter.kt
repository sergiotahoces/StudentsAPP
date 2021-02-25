package com.example.estudiantesappsergio.model

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.estudiantesappsergio.R

class CustomAdapter internal constructor(context: Context?, data: List<Student>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val items: List<Student> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvStudentName.text = "${item.name} ${item.surname}"
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): Student = items[position]

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var tvStudentName: TextView = itemView.findViewById(R.id.tv_item_student_name)

        override fun onClick(view: View?) {
            if (mClickListener != null)
                mClickListener!!.onItemClick(view, adapterPosition)
        }

        init { itemView.setOnClickListener(this) }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}