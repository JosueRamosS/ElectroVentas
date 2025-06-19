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

class EstadisticaAdapter : ListAdapter<EstadisticaItem, EstadisticaAdapter.ViewHolder>(EstadisticaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_estadistica, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card: MaterialCardView = itemView.findViewById(R.id.card_estadistica)
        private val tvIcono: MaterialTextView = itemView.findViewById(R.id.tv_icono_estadistica)
        private val tvTitulo: MaterialTextView = itemView.findViewById(R.id.tv_titulo_estadistica)
        private val tvValor: MaterialTextView = itemView.findViewById(R.id.tv_valor_estadistica)

        fun bind(estadistica: EstadisticaItem) {
            tvIcono.text = estadistica.icono
            tvTitulo.text = estadistica.titulo
            tvValor.text = estadistica.valor

            card.strokeColor = Color.parseColor(estadistica.color)
            tvIcono.setTextColor(Color.parseColor(estadistica.color))
        }
    }
}

class EstadisticaDiffCallback : DiffUtil.ItemCallback<EstadisticaItem>() {
    override fun areItemsTheSame(oldItem: EstadisticaItem, newItem: EstadisticaItem): Boolean {
        return oldItem.titulo == newItem.titulo
    }

    override fun areContentsTheSame(oldItem: EstadisticaItem, newItem: EstadisticaItem): Boolean {
        return oldItem == newItem
    }
}