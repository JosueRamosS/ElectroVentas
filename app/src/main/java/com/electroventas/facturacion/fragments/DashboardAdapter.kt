package com.electroventas.facturacion.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

data class DashboardItem(
    val id: Int,
    val title: String,
    val icon: String,
    val subtitle: String,
    val color: String
)

class DashboardAdapter(private val onItemClick: (DashboardItem) -> Unit) :
    ListAdapter<DashboardItem, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card: MaterialCardView = itemView.findViewById(R.id.card_dashboard)
        private val icon: MaterialTextView = itemView.findViewById(R.id.tv_icon)
        private val title: MaterialTextView = itemView.findViewById(R.id.tv_title)
        private val subtitle: MaterialTextView = itemView.findViewById(R.id.tv_subtitle)

        fun bind(item: DashboardItem, onItemClick: (DashboardItem) -> Unit) {
            icon.text = item.icon
            title.text = item.title
            subtitle.text = item.subtitle

            card.setCardBackgroundColor(Color.parseColor(item.color))
            card.setOnClickListener { onItemClick(item) }
        }
    }
}

class DashboardDiffCallback : DiffUtil.ItemCallback<DashboardItem>() {
    override fun areItemsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        return oldItem == newItem
    }
}