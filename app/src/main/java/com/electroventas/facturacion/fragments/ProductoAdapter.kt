package com.electroventas.facturacion.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.textview.MaterialTextView
import com.google.android.material.chip.Chip

class ProductoAdapter : ListAdapter<Producto, ProductoAdapter.ViewHolder>(ProductoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: MaterialTextView = itemView.findViewById(R.id.tv_nombre_producto)
        private val tvMarca: MaterialTextView = itemView.findViewById(R.id.tv_marca_producto)
        private val tvModelo: MaterialTextView = itemView.findViewById(R.id.tv_modelo_producto)
        private val tvCategoria: Chip = itemView.findViewById(R.id.tv_categoria_producto)
        private val tvPrecio: MaterialTextView = itemView.findViewById(R.id.tv_precio_producto)
        private val tvStock: MaterialTextView = itemView.findViewById(R.id.tv_stock_producto)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvMarca.text = "Marca: ${producto.marca}"
            tvModelo.text = "Modelo: ${producto.modelo}"
            tvCategoria.text = producto.categoria
            tvPrecio.text = "S/ ${String.format("%.2f", producto.precio)}"
            tvStock.text = "Stock: ${producto.stock}"

            // Cambiar color del badge según el stock
            val stockColor = when {
                producto.stock <= 3 -> android.graphics.Color.RED
                producto.stock <= 7 -> android.graphics.Color.parseColor("#FF9800")
                else -> android.graphics.Color.parseColor("#4CAF50")
            }
            tvStock.setTextColor(stockColor)
        }
    }
}

class ProductoDiffCallback : DiffUtil.ItemCallback<Producto>() {
    override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem == newItem
    }
}