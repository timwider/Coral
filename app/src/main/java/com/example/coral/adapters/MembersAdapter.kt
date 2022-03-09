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
import com.example.coral.data.Member

class MembersAdapter(
    private val listener: (Member) -> Unit
): ListAdapter<Member, MembersAdapter.ViewHolder>(MembersCallback()) {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val tvName: TextView = item.findViewById(R.id.tv_name)
        val tvDaysLeft: TextView = item.findViewById(R.id.tv_days_left)
        val card: CardView = item.findViewById(R.id.member_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_member, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMember = getItem(position)
        holder.tvName.text = currentMember.name
        holder.tvDaysLeft.text = currentMember.daysLeft.toString()
        holder.card.setOnClickListener { listener(currentMember) }
    }

    class MembersCallback: DiffUtil.ItemCallback<Member>() {
        override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean =
            oldItem == newItem
    }
}