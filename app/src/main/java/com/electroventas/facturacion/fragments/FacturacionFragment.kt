// ======= FacturacionFragment.kt (ACTUALIZADO) =======
package com.electroventas.facturacion.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.electroventas.facturacion.R
import com.electroventas.facturacion.data.DataManager
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

/**
 * Fragment para la generación de documentos de venta (boletas, facturas, notas de venta)
 * Integrado con DataManager para persistencia de datos y actualización de estadísticas
 */
class FacturacionFragment : Fragment() {

    // Variables para manejar la interfaz
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

    // DataManager para persistencia
    private lateinit var dataManager: DataManager

    /**
     * Infla el layout del fragment
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_facturacion, container, false)
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
        setupSpinner()
        setupClickListeners()
    }

    /**
     * Inicializa todas las vistas del fragment
     */
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

    /**
     * Configura el spinner con los tipos de documento disponibles
     */
    private fun setupSpinner() {
        val tiposDocumento = arrayOf("Boleta", "Factura", "Nota de Venta")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, tiposDocumento)
        spinnerTipoDocumento.setAdapter(adapter)
    }

    /**
     * Configura los listeners de los botones
     */
    private fun setupClickListeners() {
        btnBuscarCliente.setOnClickListener {
            buscarClientePorDni()
        }

        btnGenerarDocumento.setOnClickListener {
            generarDocumento()
        }
    }

    /**
     * Simula la búsqueda de un cliente por DNI en la base de datos de RENIEC
     * En una implementación real, esto haría una llamada a la API de RENIEC
     */
    private fun buscarClientePorDni() {
        val dni = editDni.text.toString().trim()

        // Validar DNI
        if (dni.isEmpty() || dni.length != 8) {
            Toast.makeText(context, "Ingrese un DNI válido de 8 dígitos", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar que sea numérico
        if (!dni.all { it.isDigit() }) {
            Toast.makeText(context, "El DNI debe contener solo números", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulación de búsqueda en RENIEC
        Toast.makeText(context, "Buscando cliente...", Toast.LENGTH_SHORT).show()

        // Simular respuesta exitosa con datos aleatorios basados en el DNI
        val nombresSimulados = arrayOf(
            "Juan Carlos Pérez Rodríguez",
            "María Elena García López",
            "Carlos Alberto Mendoza Silva",
            "Ana Patricia Torres Vega",
            "Luis Fernando Castro Ruiz",
            "Rosa María Flores Herrera"
        )

        // Seleccionar nombre basado en el último dígito del DNI
        val indice = dni.last().toString().toInt() % nombresSimulados.size
        val nombreCompleto = nombresSimulados[indice]

        // Mostrar resultado
        editNombreCliente.setText(nombreCompleto)
        Toast.makeText(context, "Cliente encontrado", Toast.LENGTH_SHORT).show()
    }

    /**
     * Genera el documento de venta y lo guarda en el sistema
     * Actualiza las estadísticas y el inventario correspondiente
     */
    private fun generarDocumento() {
        // Obtener datos del formulario
        val tipoDocumento = spinnerTipoDocumento.text.toString()
        val dni = editDni.text.toString().trim()
        val nombre = editNombreCliente.text.toString().trim()
        val producto = editProducto.text.toString().trim()
        val marca = editMarca.text.toString().trim()
        val modelo = editModelo.text.toString().trim()
        val serie = editSerie.text.toString().trim()
        val precioText = editPrecio.text.toString().trim()

        // Validar todos los campos
        if (!validarCampos(tipoDocumento, dni, nombre, producto, precioText)) {
            return
        }

        // Convertir precio a número
        val precio = try {
            precioText.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(context, "Ingrese un precio válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Validar precio mínimo
        if (precio <= 0) {
            Toast.makeText(context, "El precio debe ser mayor a cero", Toast.LENGTH_SHORT).show()
            return
        }

        // Generar ID único para el documento
        val documentoId = generarIdDocumento()

        // Obtener fecha y hora actual
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val horaActual = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        // Crear objeto documento
        val documento = DataManager.DocumentoGenerado(
            id = documentoId,
            tipo = tipoDocumento,
            fecha = fechaActual,
            hora = horaActual,
            clienteDni = dni,
            clienteNombre = nombre,
            producto = producto,
            marca = marca,
            modelo = modelo,
            precio = precio,
            vendedor = obtenerVendedorActual()
        )

        // Guardar documento en el sistema
        dataManager.guardarDocumento(documento)

        // Actualizar estadísticas
        actualizarEstadisticas(precio)

        // Actualizar inventario (reducir stock si es posible)
        actualizarInventario(producto, marca, modelo)

        // Mostrar mensaje de éxito
        Toast.makeText(context, "$tipoDocumento generada exitosamente\nN° $documentoId", Toast.LENGTH_LONG).show()

        // Limpiar formulario
        limpiarFormulario()
    }

    /**
     * Valida que todos los campos obligatorios estén completos
     */
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
            dni.length != 8 -> {
                Toast.makeText(context, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show()
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

    /**
     * Genera un ID único para el documento usando timestamp y número aleatorio
     */
    private fun generarIdDocumento(): String {
        val timestamp = System.currentTimeMillis()
        val random = (1000..9999).random()
        return "${timestamp.toString().takeLast(6)}$random"
    }

    /**
     * Obtiene el nombre del vendedor actual (simulado)
     * En una implementación real, esto vendría del sistema de autenticación
     */
    private fun obtenerVendedorActual(): String {
        // Simular vendedor actual basado en empleados activos
        val empleados = dataManager.obtenerEmpleados()
        val empleadosActivos = empleados.filter { it.estado == EstadoEmpleado.ACTIVO }

        return if (empleadosActivos.isNotEmpty()) {
            empleadosActivos.first().nombre
        } else {
            "Sistema" // Fallback si no hay empleados activos
        }
    }

    /**
     * Actualiza las estadísticas de ventas con el nuevo documento
     */
    private fun actualizarEstadisticas(precio: Double) {
        // Actualizar ventas diarias
        dataManager.actualizarVentasDiarias(precio)

        // Actualizar ventas mensuales
        dataManager.actualizarVentasMensuales(precio)

        // Incrementar contador de productos vendidos
        dataManager.incrementarProductosVendidos()
    }

    /**
     * Actualiza el inventario reduciendo el stock del producto vendido
     * Busca el producto por nombre, marca y modelo para actualizar su stock
     */
    private fun actualizarInventario(nombreProducto: String, marca: String, modelo: String) {
        val productos = dataManager.obtenerProductos()

        // Buscar producto que coincida (búsqueda flexible)
        val productoEncontrado = productos.find { producto ->
            producto.nombre.contains(nombreProducto, ignoreCase = true) ||
                    (producto.marca.equals(marca, ignoreCase = true) && producto.modelo.equals(modelo, ignoreCase = true))
        }

        // Si se encuentra el producto y tiene stock, reducir en 1
        productoEncontrado?.let { producto ->
            if (producto.stock > 0) {
                dataManager.actualizarStockProducto(producto.id, producto.stock - 1)
                Toast.makeText(context, "Stock actualizado: ${producto.nombre}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Advertencia: Producto sin stock en inventario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Limpia todos los campos del formulario después de generar un documento
     */
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