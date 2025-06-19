package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DashboardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupDashboardData()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_dashboard)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter = DashboardAdapter { item ->
            when(item.id) {
                1 -> findNavController().navigate(R.id.action_home_to_facturacion)
                2 -> findNavController().navigate(R.id.action_home_to_inventario)
                3 -> findNavController().navigate(R.id.action_home_to_personal)
                4 -> findNavController().navigate(R.id.action_home_to_estadisticas)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun setupDashboardData() {
        val dashboardItems = listOf(
            DashboardItem(1, "Generar Documento", "📄", "Boletas, Facturas, Notas", "#4CAF50"),
            DashboardItem(2, "Inventario", "📦", "Gestión de Productos", "#2196F3"),
            DashboardItem(3, "Personal", "👥", "Control de Asistencia", "#FF9800"),
            DashboardItem(4, "Estadísticas", "📊", "Reportes y Análisis", "#9C27B0")
        )
        adapter.submitList(dashboardItems)
    }
}