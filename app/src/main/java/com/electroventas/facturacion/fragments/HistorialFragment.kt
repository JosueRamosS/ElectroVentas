// ======= HistorialFragment.kt =======
package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.electroventas.facturacion.data.DataManager
import com.google.android.material.textview.MaterialTextView

/**
 * Fragment que muestra el historial de documentos generados (boletas, facturas, notas de venta)
 * Permite visualizar todos los documentos creados en orden cronológico
 */
class HistorialFragment : Fragment() {

    // Variables para manejar la interfaz
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HistorialAdapter
    private lateinit var tvEmptyState: MaterialTextView
    private lateinit var dataManager: DataManager

    /**
     * Infla el layout del fragment
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

    /**
     * Configura la vista una vez que ha sido creada
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar DataManager
        dataManager = DataManager.getInstance(requireContext())

        // Configurar componentes de la interfaz
        initViews(view)
        setupRecyclerView()
        cargarHistorial()
    }

    /**
     * Inicializa las vistas del fragment
     */
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_historial)
        tvEmptyState = view.findViewById(R.id.tv_empty_state)
    }

    /**
     * Configura el RecyclerView con su adapter y layout manager
     */
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HistorialAdapter()
        recyclerView.adapter = adapter
    }

    /**
     * Carga el historial de documentos desde el DataManager
     * y actualiza la interfaz según si hay datos o no
     */
    private fun cargarHistorial() {
        val documentos = dataManager.obtenerDocumentos()

        if (documentos.isEmpty()) {
            // Mostrar estado vacío
            recyclerView.visibility = View.GONE
            tvEmptyState.visibility = View.VISIBLE
        } else {
            // Mostrar lista de documentos
            recyclerView.visibility = View.VISIBLE
            tvEmptyState.visibility = View.GONE
            adapter.submitList(documentos)
        }
    }

    /**
     * Actualiza el historial cuando el fragment vuelve a ser visible
     * (útil cuando se genera un nuevo documento)
     */
    override fun onResume() {
        super.onResume()
        cargarHistorial()
    }
}