// ======= HistorialAdapter.kt =======
package com.electroventas.facturacion.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.electroventas.facturacion.data.DataManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.textview.MaterialTextView

/**
 * Adapter para mostrar la lista de documentos generados en el RecyclerView
 * Maneja la visualización de boletas, facturas y notas de venta
 */
class HistorialAdapter : ListAdapter<DataManager.DocumentoGenerado, HistorialAdapter.ViewHolder>(HistorialDiffCallback()) {

    /**
     * Crea una nueva vista para cada item del historial
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vincula los datos del documento con la vista
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder que contiene las vistas de cada item del historial
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referencias a las vistas del layout
        private val card: MaterialCardView = itemView.findViewById(R.id.card_historial)
        private val chipTipo: Chip = itemView.findViewById(R.id.chip_tipo_documento)
        private val tvId: MaterialTextView = itemView.findViewById(R.id.tv_id_documento)
        private val tvFecha: MaterialTextView = itemView.findViewById(R.id.tv_fecha_documento)
        private val tvCliente: MaterialTextView = itemView.findViewById(R.id.tv_cliente_documento)
        private val tvProducto: MaterialTextView = itemView.findViewById(R.id.tv_producto_documento)
        private val tvPrecio: MaterialTextView = itemView.findViewById(R.id.tv_precio_documento)
        private val tvVendedor: MaterialTextView = itemView.findViewById(R.id.tv_vendedor_documento)

        /**
         * Vincula un documento con las vistas del ViewHolder
         * @param documento Documento a mostrar
         */
        fun bind(documento: DataManager.DocumentoGenerado) {
            // Configurar tipo de documento con colores específicos
            chipTipo.text = documento.tipo
            when (documento.tipo) {
                "Boleta" -> {
                    chipTipo.setChipBackgroundColorResource(android.R.color.holo_green_light)
                    chipTipo.setTextColor(Color.WHITE)
                }
                "Factura" -> {
                    chipTipo.setChipBackgroundColorResource(android.R.color.holo_blue_light)
                    chipTipo.setTextColor(Color.WHITE)
                }
                "Nota de Venta" -> {
                    chipTipo.setChipBackgroundColorResource(android.R.color.holo_orange_light)
                    chipTipo.setTextColor(Color.WHITE)
                }
            }

            // Configurar información del documento
            tvId.text = "N° ${documento.id}"
            tvFecha.text = "${documento.fecha} - ${documento.hora}"
            tvCliente.text = "${documento.clienteNombre} (${documento.clienteDni})"
            tvProducto.text = "${documento.producto} - ${documento.marca} ${documento.modelo}"
            tvPrecio.text = "S/ ${String.format("%.2f", documento.precio)}"
            tvVendedor.text = "Vendedor: ${documento.vendedor}"
        }
    }
}

/**
 * DiffCallback para optimizar las actualizaciones del RecyclerView
 * Compara documentos para determinar si han cambiado
 */
class HistorialDiffCallback : DiffUtil.ItemCallback<DataManager.DocumentoGenerado>() {

    /**
     * Compara si dos documentos son el mismo item
     */
    override fun areItemsTheSame(oldItem: DataManager.DocumentoGenerado, newItem: DataManager.DocumentoGenerado): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Compara si el contenido de dos documentos es el mismo
     */
    override fun areContentsTheSame(oldItem: DataManager.DocumentoGenerado, newItem: DataManager.DocumentoGenerado): Boolean {
        return oldItem == newItem
    }
}