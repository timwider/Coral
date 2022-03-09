package com.example.coral.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coral.R
import com.example.coral.utils.Converters

class WorkoutsAdapter (
): ListAdapter<String, WorkoutsAdapter.ViewHolder>(WorkoutsCallback()) {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val date: TextView = item.findViewById(R.id.tv_workout_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_workouts, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentDate = getItem(position)

        if (!currentDate.isNullOrEmpty()) {
            val currentDay = currentDate.split(".")[0]
            val currentMonth = currentDate.split(".")[1]
            currentDate = Converters().datePickerDateToString(currentMonth.toInt() - 1, currentDay.toInt())
        }
        holder.date.text = currentDate
    }

    class WorkoutsCallback: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}