package com.electroventas.facturacion.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.electroventas.facturacion.R
import com.electroventas.facturacion.data.DataManager
import com.electroventas.facturacion.widgets.BarChartWidget
import com.electroventas.facturacion.widgets.PieChartWidget
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

/**
 * Fragment principal que muestra el dashboard con métricas, gráficas y accesos directos
 * Incluye gráficos interactivos y estadísticas en tiempo real del negocio
 */
class HomeFragment : Fragment() {

    // Variables para manejar la interfaz
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DashboardAdapter
    private lateinit var dataManager: DataManager

    // Widgets de gráficas
    private lateinit var pieChartWidget: PieChartWidget
    private lateinit var barChartWidget: BarChartWidget

    // TextViews para métricas principales
    private lateinit var tvVentasHoy: MaterialTextView
    private lateinit var tvProductosTotal: MaterialTextView
    private lateinit var tvPersonalActivo: MaterialTextView

    // Botón para acceder al historial
    private lateinit var btnVerHistorial: MaterialButton

    /**
     * Infla el layout del fragment
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_mejorado, container, false)
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
        setupRecyclerView(view)
        setupCharts()
        setupClickListeners()
        cargarDatos()
    }

    /**
     * Inicializa todas las vistas del fragment
     */
    private fun initViews(view: View) {
        // Métricas principales
        tvVentasHoy = view.findViewById(R.id.tv_ventas_hoy_home)
        tvProductosTotal = view.findViewById(R.id.tv_productos_total_home)
        tvPersonalActivo = view.findViewById(R.id.tv_personal_activo_home)

        // Widgets de gráficas
        pieChartWidget = view.findViewById(R.id.pie_chart_widget)
        barChartWidget = view.findViewById(R.id.bar_chart_widget)

        // Botón de historial
        btnVerHistorial = view.findViewById(R.id.btn_ver_historial)

        // RecyclerView para accesos directos
        recyclerView = view.findViewById(R.id.recycler_dashboard)
    }

    /**
     * Configura el RecyclerView con accesos directos a las funcionalidades
     */
    private fun setupRecyclerView(view: View) {
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

    /**
     * Configura los gráficos con datos iniciales
     */
    private fun setupCharts() {
        // Configurar gráfico circular de categorías de productos
        setupPieChart()

        // Configurar gráfico de barras de ventas por día
        setupBarChart()
    }

    /**
     * Configura el gráfico circular con categorías de productos
     */
    private fun setupPieChart() {
        val productos = dataManager.obtenerProductos()
        val categorias = productos.groupBy { it.categoria }

        val pieData = categorias.map { (categoria, productosCategoria) ->
            val stock = productosCategoria.sumOf { it.stock }
            PieChartWidget.ChartDataItem(
                label = categoria,
                value = stock.toFloat(),
                color = getCategoryColor(categoria)
            )
        }

        pieChartWidget.setData(pieData)
    }

    /**
     * Configura el gráfico de barras con datos de ventas semanales (simuladas)
     */
    private fun setupBarChart() {
        val ventasSemana = listOf(
            BarChartWidget.BarDataItem("Lun", 1200f, Color.parseColor("#4CAF50")),
            BarChartWidget.BarDataItem("Mar", 1800f, Color.parseColor("#2196F3")),
            BarChartWidget.BarDataItem("Mié", 1500f, Color.parseColor("#FF9800")),
            BarChartWidget.BarDataItem("Jue", 2200f, Color.parseColor("#9C27B0")),
            BarChartWidget.BarDataItem("Vie", 2800f, Color.parseColor("#F44336")),
            BarChartWidget.BarDataItem("Sáb", 3200f, Color.parseColor("#009688")),
            BarChartWidget.BarDataItem("Dom", 1600f, Color.parseColor("#795548"))
        )

        barChartWidget.setData(ventasSemana)
    }

    /**
     * Asigna colores específicos a cada categoría de productos
     */
    private fun getCategoryColor(categoria: String): Int {
        return when (categoria) {
            "Refrigeración" -> Color.parseColor("#2196F3")
            "Lavado" -> Color.parseColor("#4CAF50")
            "Cocina" -> Color.parseColor("#FF9800")
            "Entretenimiento" -> Color.parseColor("#9C27B0")
            "Climatización" -> Color.parseColor("#00BCD4")
            else -> Color.parseColor("#757575")
        }
    }

    /**
     * Configura los listeners de los botones
     */
    private fun setupClickListeners() {
        btnVerHistorial.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_historial)
        }
    }

    /**
     * Carga y actualiza todos los datos del dashboard
     */
    private fun cargarDatos() {
        // Actualizar métricas principales
        actualizarMetricas()

        // Configurar datos del dashboard de accesos directos
        setupDashboardData()

        // Actualizar gráficos
        setupCharts()
    }

    /**
     * Actualiza las métricas principales mostradas en cards
     */
    private fun actualizarMetricas() {
        // Ventas del día
        val ventasHoy = dataManager.obtenerVentasDiarias()
        tvVentasHoy.text = "S/ ${String.format("%.0f", ventasHoy)}"

        // Total de productos en inventario
        val productos = dataManager.obtenerProductos()
        val totalProductos = productos.sumOf { it.stock }
        tvProductosTotal.text = totalProductos.toString()

        // Personal activo
        val empleados = dataManager.obtenerEmpleados()
        val personalActivo = empleados.count { it.estado == EstadoEmpleado.ACTIVO }
        val totalPersonal = empleados.size
        tvPersonalActivo.text = "$personalActivo / $totalPersonal"
    }

    /**
     * Configura los datos de accesos directos en el dashboard
     */
    private fun setupDashboardData() {
        val dashboardItems = listOf(
            DashboardItem(1, "Generar Documento", "📄", "Boletas, Facturas, Notas", "#4CAF50"),
            DashboardItem(2, "Inventario", "📦", "Gestión de Productos", "#2196F3"),
            DashboardItem(3, "Personal", "👥", "Control de Asistencia", "#FF9800"),
            DashboardItem(4, "Estadísticas", "📊", "Reportes y Análisis", "#9C27B0")
        )
        adapter.submitList(dashboardItems)
    }

    /**
     * Actualiza los datos cuando el fragment vuelve a ser visible
     */
    override fun onResume() {
        super.onResume()
        cargarDatos()
    }
}