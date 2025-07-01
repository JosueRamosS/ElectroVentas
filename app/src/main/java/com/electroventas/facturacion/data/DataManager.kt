// ======= DataManager.kt =======
package com.electroventas.facturacion.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.electroventas.facturacion.fragments.Producto
import com.electroventas.facturacion.fragments.Empleado
import com.electroventas.facturacion.fragments.EstadoEmpleado

/**
 * Clase singleton para manejar la persistencia de datos usando SharedPreferences
 * Gestiona productos, empleados, documentos generados y estadísticas del sistema
 */
class DataManager private constructor(context: Context) {

    companion object {
        @Volatile
        private var INSTANCE: DataManager? = null

        /**
         * Obtiene la instancia singleton de DataManager
         * @param context Contexto de la aplicación
         * @return Instancia única de DataManager
         */
        fun getInstance(context: Context): DataManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataManager(context.applicationContext).also { INSTANCE = it }
            }
        }

        // Constantes para las claves de SharedPreferences
        private const val PREFS_NAME = "electroventas_prefs"
        private const val KEY_PRODUCTOS = "productos"
        private const val KEY_EMPLEADOS = "empleados"
        private const val KEY_DOCUMENTOS = "documentos"
        private const val KEY_VENTAS_DIARIAS = "ventas_diarias"
        private const val KEY_VENTAS_MENSUALES = "ventas_mensuales"
        private const val KEY_PRODUCTOS_VENDIDOS = "productos_vendidos"
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    // ==================== PRODUCTOS ====================

    /**
     * Guarda la lista de productos en SharedPreferences
     * @param productos Lista de productos a guardar
     */
    fun guardarProductos(productos: List<Producto>) {
        val productosJson = gson.toJson(productos)
        sharedPreferences.edit()
            .putString(KEY_PRODUCTOS, productosJson)
            .apply()
    }

    /**
     * Obtiene la lista de productos desde SharedPreferences
     * @return Lista de productos guardados o lista por defecto si no hay datos
     */
    fun obtenerProductos(): List<Producto> {
        val productosJson = sharedPreferences.getString(KEY_PRODUCTOS, null)
        return if (productosJson != null) {
            val type = object : TypeToken<List<Producto>>() {}.type
            gson.fromJson(productosJson, type)
        } else {
            // Datos por defecto si es la primera vez
            obtenerProductosPorDefecto()
        }
    }

    /**
     * Agrega un nuevo producto a la lista existente
     * @param producto Producto a agregar
     */
    fun agregarProducto(producto: Producto) {
        val productosActuales = obtenerProductos().toMutableList()
        productosActuales.add(producto)
        guardarProductos(productosActuales)
    }

    /**
     * Actualiza el stock de un producto específico
     * @param productoId ID del producto a actualizar
     * @param nuevoStock Nuevo valor de stock
     */
    fun actualizarStockProducto(productoId: Int, nuevoStock: Int) {
        val productos = obtenerProductos().toMutableList()
        val index = productos.indexOfFirst { it.id == productoId }
        if (index != -1) {
            productos[index] = productos[index].copy(stock = nuevoStock)
            guardarProductos(productos)
        }
    }

    /**
     * Proporciona datos por defecto de productos para inicializar la app
     */
    private fun obtenerProductosPorDefecto(): List<Producto> {
        val productos = listOf(
            Producto(1, "Refrigeradora LG", "LG", "GN-H702HLHU", "Refrigeración", 2500.0, 5),
            Producto(2, "Lavadora Samsung", "Samsung", "WA70H4200SW", "Lavado", 1200.0, 8),
            Producto(3, "Microondas Panasonic", "Panasonic", "NN-ST27JW", "Cocina", 350.0, 12),
            Producto(4, "TV Smart Sony", "Sony", "KD-55X80J", "Entretenimiento", 1800.0, 3),
            Producto(5, "Licuadora Oster", "Oster", "BLSTMG-W00", "Cocina", 180.0, 15),
            Producto(6, "Aire Acondicionado Midea", "Midea", "MAC-12000BTU", "Climatización", 1500.0, 6),
            Producto(7, "Cocina a Gas Bosch", "Bosch", "PRO465-4Q", "Cocina", 800.0, 4)
        )
        guardarProductos(productos) // Guardar datos por defecto
        return productos
    }

    // ==================== EMPLEADOS ====================

    /**
     * Guarda la lista de empleados en SharedPreferences
     * @param empleados Lista de empleados a guardar
     */
    fun guardarEmpleados(empleados: List<Empleado>) {
        val empleadosJson = gson.toJson(empleados)
        sharedPreferences.edit()
            .putString(KEY_EMPLEADOS, empleadosJson)
            .apply()
    }

    /**
     * Obtiene la lista de empleados desde SharedPreferences
     * @return Lista de empleados guardados o lista por defecto
     */
    fun obtenerEmpleados(): List<Empleado> {
        val empleadosJson = sharedPreferences.getString(KEY_EMPLEADOS, null)
        return if (empleadosJson != null) {
            val type = object : TypeToken<List<Empleado>>() {}.type
            gson.fromJson(empleadosJson, type)
        } else {
            // Datos por defecto si es la primera vez
            obtenerEmpleadosPorDefecto()
        }
    }

    /**
     * Actualiza el estado de un empleado específico
     * @param empleadoId ID del empleado
     * @param nuevoEstado Nuevo estado del empleado
     * @param horaEntrada Hora de entrada (opcional)
     * @param horaSalida Hora de salida (opcional)
     */
    fun actualizarEstadoEmpleado(empleadoId: Int, nuevoEstado: EstadoEmpleado,
                                 horaEntrada: String? = null, horaSalida: String? = null) {
        val empleados = obtenerEmpleados().toMutableList()
        val index = empleados.indexOfFirst { it.id == empleadoId }
        if (index != -1) {
            val empleadoActual = empleados[index]
            empleados[index] = empleadoActual.copy(
                estado = nuevoEstado,
                horaEntrada = horaEntrada ?: empleadoActual.horaEntrada,
                horaSalida = horaSalida ?: empleadoActual.horaSalida
            )
            guardarEmpleados(empleados)
        }
    }

    /**
     * Proporciona datos por defecto de empleados
     */
    private fun obtenerEmpleadosPorDefecto(): List<Empleado> {
        val empleados = listOf(
            Empleado(1, "María González", "Admin", "09:00", null, EstadoEmpleado.ACTIVO),
            Empleado(2, "Carlos Rodríguez", "Vendedor", "09:15", null, EstadoEmpleado.ACTIVO),
            Empleado(3, "Ana Martínez", "Vendedor", null, null, EstadoEmpleado.AUSENTE),
            Empleado(4, "Luis Pérez", "Vendedor", "09:30", "18:00", EstadoEmpleado.INACTIVO)
        )
        guardarEmpleados(empleados)
        return empleados
    }

    // ==================== DOCUMENTOS GENERADOS ====================

    /**
     * Data class para representar un documento generado
     */
    data class DocumentoGenerado(
        val id: String,
        val tipo: String, // Boleta, Factura, Nota de Venta
        val fecha: String,
        val hora: String,
        val clienteDni: String,
        val clienteNombre: String,
        val producto: String,
        val marca: String,
        val modelo: String,
        val precio: Double,
        val vendedor: String
    )

    /**
     * Guarda un nuevo documento generado
     * @param documento Documento a guardar
     */
    fun guardarDocumento(documento: DocumentoGenerado) {
        val documentos = obtenerDocumentos().toMutableList()
        documentos.add(0, documento) // Agregar al inicio para mostrar los más recientes primero
        val documentosJson = gson.toJson(documentos)
        sharedPreferences.edit()
            .putString(KEY_DOCUMENTOS, documentosJson)
            .apply()
    }

    /**
     * Obtiene la lista de documentos generados
     * @return Lista de documentos ordenados por fecha (más recientes primero)
     */
    fun obtenerDocumentos(): List<DocumentoGenerado> {
        val documentosJson = sharedPreferences.getString(KEY_DOCUMENTOS, null)
        return if (documentosJson != null) {
            val type = object : TypeToken<List<DocumentoGenerado>>() {}.type
            gson.fromJson(documentosJson, type)
        } else {
            emptyList()
        }
    }

    // ==================== ESTADÍSTICAS Y VENTAS ====================

    /**
     * Actualiza las ventas diarias
     * @param monto Monto a agregar a las ventas del día
     */
    fun actualizarVentasDiarias(monto: Double) {
        val ventasActuales = sharedPreferences.getFloat(KEY_VENTAS_DIARIAS, 0f)
        sharedPreferences.edit()
            .putFloat(KEY_VENTAS_DIARIAS, ventasActuales + monto.toFloat())
            .apply()
    }

    /**
     * Actualiza las ventas mensuales
     * @param monto Monto a agregar a las ventas del mes
     */
    fun actualizarVentasMensuales(monto: Double) {
        val ventasActuales = sharedPreferences.getFloat(KEY_VENTAS_MENSUALES, 125300f) // Valor por defecto
        sharedPreferences.edit()
            .putFloat(KEY_VENTAS_MENSUALES, ventasActuales + monto.toFloat())
            .apply()
    }

    /**
     * Incrementa el contador de productos vendidos
     */
    fun incrementarProductosVendidos() {
        val productosActuales = sharedPreferences.getInt(KEY_PRODUCTOS_VENDIDOS, 47) // Valor por defecto
        sharedPreferences.edit()
            .putInt(KEY_PRODUCTOS_VENDIDOS, productosActuales + 1)
            .apply()
    }

    /**
     * Obtiene las ventas diarias actuales
     * @return Monto total de ventas del día
     */
    fun obtenerVentasDiarias(): Double {
        return sharedPreferences.getFloat(KEY_VENTAS_DIARIAS, 8450f).toDouble() // Valor por defecto
    }

    /**
     * Obtiene las ventas mensuales actuales
     * @return Monto total de ventas del mes
     */
    fun obtenerVentasMensuales(): Double {
        return sharedPreferences.getFloat(KEY_VENTAS_MENSUALES, 125300f).toDouble()
    }

    /**
     * Obtiene el número de productos vendidos
     * @return Cantidad total de productos vendidos
     */
    fun obtenerProductosVendidos(): Int {
        return sharedPreferences.getInt(KEY_PRODUCTOS_VENDIDOS, 47)
    }

    /**
     * Resetea las ventas diarias (útil para comenzar un nuevo día)
     */
    fun resetearVentasDiarias() {
        sharedPreferences.edit()
            .putFloat(KEY_VENTAS_DIARIAS, 0f)
            .apply()
    }

    /**
     * Limpia todos los datos almacenados (útil para testing o reset completo)
     */
    fun limpiarTodosLosDatos() {
        sharedPreferences.edit().clear().apply()
    }
}