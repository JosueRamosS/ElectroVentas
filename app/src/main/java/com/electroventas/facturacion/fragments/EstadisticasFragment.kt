package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.textview.MaterialTextView

data class EstadisticaItem(
    val titulo: String,
    val valor: String,
    val icono: String,
    val color: String
)

class EstadisticasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EstadisticaAdapter
    private lateinit var tvVentasHoy: MaterialTextView
    private lateinit var tvVentasMes: MaterialTextView
    private lateinit var tvProductosVendidos: MaterialTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_estadisticas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupRecyclerView()
        cargarEstadisticas()
    }

    private fun initViews(view: View) {
        tvVentasHoy = view.findViewById(R.id.tv_ventas_hoy)
        tvVentasMes = view.findViewById(R.id.tv_ventas_mes)
        tvProductosVendidos = view.findViewById(R.id.tv_productos_vendidos)
        recyclerView = view.findViewById(R.id.recycler_estadisticas)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EstadisticaAdapter()
        recyclerView.adapter = adapter
    }

    private fun cargarEstadisticas() {
        // Datos del dashboard principal
        tvVentasHoy.text = "S/ 8,450.00"
        tvVentasMes.text = "S/ 125,300.00"
        tvProductosVendidos.text = "47 productos"

        // Estadísticas detalladas
        val estadisticas = listOf(
            EstadisticaItem("Productos más vendidos", "TV Smart Sony (8 unidades)", "📺", "#4CAF50"),
            EstadisticaItem("Categoría top", "Refrigeración (35%)", "❄️", "#2196F3"),
            EstadisticaItem("Empleado del mes", "Carlos Rodríguez", "👨‍💼", "#FF9800"),
            EstadisticaItem("Stock bajo", "3 productos", "⚠️", "#F44336"),
            EstadisticaItem("Clientes atendidos hoy", "23 clientes", "👥", "#9C27B0"),
            EstadisticaItem("Promedio de venta", "S/ 650.00", "💰", "#009688")
        )
        adapter.submitList(estadisticas)
    }
}
