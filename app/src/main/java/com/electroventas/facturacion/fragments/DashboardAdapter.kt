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

/**
 * Data class representing an item in the dashboard.
 *
 * @property id Unique identifier for the item.
 * @property title The main text displayed for the item.
 * @property icon String representation of the icon (e.g., an emoji or a character).
 * @property subtitle Additional descriptive text for the item.
 * @property color Hex string representing the background color of the item's card.
 */
data class DashboardItem(
    val id: Int,
    val title: String,
    val icon: String,
    val subtitle: String,
    val color: String
)

/**
 * Adapter for the RecyclerView that displays dashboard items.
 *
 * @param onItemClick Lambda function to be invoked when an item is clicked.
 */
class DashboardAdapter(private val onItemClick: (DashboardItem) -> Unit) :
    ListAdapter<DashboardItem, DashboardAdapter.ViewHolder>(DashboardDiffCallback()) {

    /**
     * Creates new ViewHolders.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds the data to the ViewHolder at the given position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    /**
     * ViewHolder for dashboard items.
     *
     * @param itemView The view for a single dashboard item.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Views within the item layout
        private val card: MaterialCardView = itemView.findViewById(R.id.card_dashboard)
        private val icon: MaterialTextView = itemView.findViewById(R.id.tv_icon)
        private val title: MaterialTextView = itemView.findViewById(R.id.tv_title)
        private val subtitle: MaterialTextView = itemView.findViewById(R.id.tv_subtitle)

        /**
         * Binds a DashboardItem to the views in this ViewHolder.
         *
         * @param item The DashboardItem to display.
         * @param onItemClick Lambda function to handle item clicks.
         */
        fun bind(item: DashboardItem, onItemClick: (DashboardItem) -> Unit) {
            icon.text = item.icon
            title.text = item.title
            subtitle.text = item.subtitle

            // Set the background color of the card
            card.setCardBackgroundColor(Color.parseColor(item.color))
            // Set the click listener for the card
            card.setOnClickListener { onItemClick(item) }
        }
    }
}

/**
 * DiffUtil.ItemCallback for DashboardItem.
 * This helps the ListAdapter efficiently update the list when data changes.
 */
class DashboardDiffCallback : DiffUtil.ItemCallback<DashboardItem>() {
    /**
     * Called to check whether two items represent the same object.
     */
    override fun areItemsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Called to check whether two items have the same data.
     */
    override fun areContentsTheSame(oldItem: DashboardItem, newItem: DashboardItem): Boolean {
        return oldItem == newItem
    }
}