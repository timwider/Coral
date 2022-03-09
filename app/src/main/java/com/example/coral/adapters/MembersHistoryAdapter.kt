package com.example.coral.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coral.R
import com.example.coral.utils.HistoryConverter

class MembersHistoryAdapter: ListAdapter<String, MembersHistoryAdapter.ViewHolder>(MembersHistoryCallback()) {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val tvDate: TextView = itemView.findViewById(R.id.member_details_card_payment_date)
        val tvSum: TextView = itemView.findViewById(R.id.member_details_card_payment_sum)
        val tvWorkouts: TextView = itemView.findViewById(R.id.member_details_card_workouts)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MembersHistoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_member_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembersHistoryAdapter.ViewHolder, position: Int) {
        val currentDataRow = getItem(position)
        val dataRowSize = currentDataRow.split(":").size
        if (dataRowSize > 1) {
            val currentDate = HistoryConverter().getDataFromDataRow(currentDataRow).first
            val currentSum = HistoryConverter().getDataFromDataRow(currentDataRow).second
            val currentWorkouts = HistoryConverter().getDataFromDataRow(currentDataRow).third

            holder.tvDate.text = "Дата оплаты: $currentDate"
            holder.tvSum.text = "Сумма: $currentSum"
            holder.tvWorkouts.text = currentWorkouts
        }
    }

    class MembersHistoryCallback(): DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    }
}