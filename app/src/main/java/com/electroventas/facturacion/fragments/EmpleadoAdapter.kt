package com.electroventas.facturacion.fragments

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class EmpleadoAdapter(private val onAsistenciaClick: (Empleado) -> Unit) :
    ListAdapter<Empleado, EmpleadoAdapter.ViewHolder>(EmpleadoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empleado, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onAsistenciaClick)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val card: MaterialCardView = itemView.findViewById(R.id.card_empleado)
        private val tvNombre: MaterialTextView = itemView.findViewById(R.id.tv_nombre_empleado)
        private val tvRol: MaterialTextView = itemView.findViewById(R.id.tv_rol_empleado)
        private val tvHorarios: MaterialTextView = itemView.findViewById(R.id.tv_horarios_empleado)
        private val tvEstado: MaterialTextView = itemView.findViewById(R.id.tv_estado_empleado)
        private val btnAsistencia: MaterialButton = itemView.findViewById(R.id.btn_asistencia)

        fun bind(empleado: Empleado, onAsistenciaClick: (Empleado) -> Unit) {
            tvNombre.text = empleado.nombre
            tvRol.text = empleado.rol

            val horarios = when {
                empleado.horaEntrada != null && empleado.horaSalida != null ->
                    "Entrada: ${empleado.horaEntrada} | Salida: ${empleado.horaSalida}"
                empleado.horaEntrada != null ->
                    "Entrada: ${empleado.horaEntrada} | En turno"
                else -> "No registrado"
            }
            tvHorarios.text = horarios

            // Configurar estado
            val (estadoText, estadoColor, buttonText) = when (empleado.estado) {
                EstadoEmpleado.ACTIVO -> Triple("🟢 ACTIVO", Color.parseColor("#4CAF50"), "Registrar Salida")
                EstadoEmpleado.INACTIVO -> Triple("🔴 INACTIVO", Color.parseColor("#F44336"), "Jornada Completa")
                EstadoEmpleado.AUSENTE -> Triple("🟡 AUSENTE", Color.parseColor("#FF9800"), "Registrar Entrada")
            }

            tvEstado.text = estadoText
            tvEstado.setTextColor(estadoColor)
            btnAsistencia.text = buttonText
            btnAsistencia.isEnabled = empleado.estado != EstadoEmpleado.INACTIVO

            btnAsistencia.setOnClickListener { onAsistenciaClick(empleado) }
        }
    }
}

class EmpleadoDiffCallback : DiffUtil.ItemCallback<Empleado>() {
    override fun areItemsTheSame(oldItem: Empleado, newItem: Empleado): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Empleado, newItem: Empleado): Boolean {
        return oldItem == newItem
    }
}