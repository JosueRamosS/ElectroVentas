package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class Producto(
    val id: Int,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)

class InventarioFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductoAdapter
    private lateinit var fabAgregar: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        setupFab(view)
        cargarProductos()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_productos)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ProductoAdapter()
        recyclerView.adapter = adapter
    }

    private fun setupFab(view: View) {
        fabAgregar = view.findViewById(R.id.fab_agregar_producto)
        fabAgregar.setOnClickListener {
            // Aquí iría la navegación al formulario de agregar producto
            android.widget.Toast.makeText(context, "Agregar nuevo producto", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarProductos() {
        val productos = listOf(
            Producto(1, "Refrigeradora LG", "LG", "GN-H702HLHU", "Refrigeración", 2500.0, 5),
            Producto(2, "Lavadora Samsung", "Samsung", "WA70H4200SW", "Lavado", 1200.0, 8),
            Producto(3, "Microondas Panasonic", "Panasonic", "NN-ST27JW", "Cocina", 350.0, 12),
            Producto(4, "TV Smart Sony", "Sony", "KD-55X80J", "Entretenimiento", 1800.0, 3),
            Producto(5, "Licuadora Oster", "Oster", "BLSTMG-W00", "Cocina", 180.0, 15)
        )
        adapter.submitList(productos)
    }
}