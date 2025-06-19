package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.electroventas.facturacion.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText

class FacturacionFragment : Fragment() {

    private lateinit var spinnerTipoDocumento: MaterialAutoCompleteTextView
    private lateinit var editDni: TextInputEditText
    private lateinit var editNombreCliente: TextInputEditText
    private lateinit var editProducto: TextInputEditText
    private lateinit var editMarca: TextInputEditText
    private lateinit var editModelo: TextInputEditText
    private lateinit var editSerie: TextInputEditText
    private lateinit var editPrecio: TextInputEditText
    private lateinit var btnBuscarCliente: MaterialButton
    private lateinit var btnGenerarDocumento: MaterialButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_facturacion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupSpinner()
        setupClickListeners()
    }

    private fun initViews(view: View) {
        spinnerTipoDocumento = view.findViewById(R.id.spinner_tipo_documento)
        editDni = view.findViewById(R.id.edit_dni)
        editNombreCliente = view.findViewById(R.id.edit_nombre_cliente)
        editProducto = view.findViewById(R.id.edit_producto)
        editMarca = view.findViewById(R.id.edit_marca)
        editModelo = view.findViewById(R.id.edit_modelo)
        editSerie = view.findViewById(R.id.edit_serie)
        editPrecio = view.findViewById(R.id.edit_precio)
        btnBuscarCliente = view.findViewById(R.id.btn_buscar_cliente)
        btnGenerarDocumento = view.findViewById(R.id.btn_generar_documento)
    }

    private fun setupSpinner() {
        val tiposDocumento = arrayOf("Boleta", "Factura", "Nota de Venta")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tiposDocumento)
        spinnerTipoDocumento.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        btnBuscarCliente.setOnClickListener {
            buscarClientePorDni()
        }

        btnGenerarDocumento.setOnClickListener {
            generarDocumento()
        }
    }

    private fun buscarClientePorDni() {
        val dni = editDni.text.toString().trim()

        if (dni.isEmpty() || dni.length != 8) {
            Toast.makeText(context, "Ingrese un DNI válido de 8 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulación de búsqueda en RENIEC
        // En implementación real, aquí iría la llamada a la API
        Toast.makeText(context, "Buscando cliente...", Toast.LENGTH_SHORT).show()

        // Simulación de respuesta exitosa
        editNombreCliente.setText("Juan Carlos Pérez Rodríguez")
        Toast.makeText(context, "Cliente encontrado", Toast.LENGTH_SHORT).show()
    }

    private fun generarDocumento() {
        val tipoDocumento = spinnerTipoDocumento.text.toString()
        val dni = editDni.text.toString()
        val nombre = editNombreCliente.text.toString()
        val producto = editProducto.text.toString()
        val precio = editPrecio.text.toString()

        if (validarCampos(tipoDocumento, dni, nombre, producto, precio)) {
            // Aquí iría la lógica para generar el documento
            Toast.makeText(context, "$tipoDocumento generada exitosamente", Toast.LENGTH_LONG).show()
            limpiarFormulario()
        }
    }

    private fun validarCampos(tipo: String, dni: String, nombre: String, producto: String, precio: String): Boolean {
        when {
            tipo.isEmpty() -> {
                Toast.makeText(context, "Seleccione el tipo de documento", Toast.LENGTH_SHORT).show()
                return false
            }
            dni.isEmpty() -> {
                Toast.makeText(context, "Ingrese el DNI del cliente", Toast.LENGTH_SHORT).show()
                return false
            }
            nombre.isEmpty() -> {
                Toast.makeText(context, "Busque el cliente por DNI", Toast.LENGTH_SHORT).show()
                return false
            }
            producto.isEmpty() -> {
                Toast.makeText(context, "Ingrese el producto", Toast.LENGTH_SHORT).show()
                return false
            }
            precio.isEmpty() -> {
                Toast.makeText(context, "Ingrese el precio", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> return true
        }
    }

    private fun limpiarFormulario() {
        editDni.text?.clear()
        editNombreCliente.text?.clear()
        editProducto.text?.clear()
        editMarca.text?.clear()
        editModelo.text?.clear()
        editSerie.text?.clear()
        editPrecio.text?.clear()
        spinnerTipoDocumento.text.clear()
    }
}