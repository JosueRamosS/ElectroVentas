package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import java.text.SimpleDateFormat
import java.util.*

data class Empleado(
    val id: Int,
    val nombre: String,
    val rol: String,
    val horaEntrada: String?,
    val horaSalida: String?,
    val estado: EstadoEmpleado
)

enum class EstadoEmpleado {
    ACTIVO, INACTIVO, AUSENTE
}

class PersonalFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpleadoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(view)
        cargarEmpleados()
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recycler_empleados)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = EmpleadoAdapter { empleado ->
            manejarAsistencia(empleado)
        }
        recyclerView.adapter = adapter
    }

    private fun cargarEmpleados() {
        val empleados = listOf(
            Empleado(1, "María González", "Admin", "09:00", null, EstadoEmpleado.ACTIVO),
            Empleado(2, "Carlos Rodríguez", "Vendedor", "09:15", null, EstadoEmpleado.ACTIVO),
            Empleado(3, "Ana Martínez", "Vendedor", null, null, EstadoEmpleado.AUSENTE),
            Empleado(4, "Luis Pérez", "Vendedor", "09:30", "18:00", EstadoEmpleado.INACTIVO)
        )
        adapter.submitList(empleados)
    }

    private fun manejarAsistencia(empleado: Empleado) {
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val message = when (empleado.estado) {
            EstadoEmpleado.AUSENTE -> "Registrar entrada: $currentTime"
            EstadoEmpleado.ACTIVO -> "Registrar salida: $currentTime"
            EstadoEmpleado.INACTIVO -> "Empleado ya completó su jornada"
        }
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
