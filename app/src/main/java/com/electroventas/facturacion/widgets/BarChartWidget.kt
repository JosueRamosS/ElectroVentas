// ======= BarChartWidget.kt =======
// Ubicación: app/src/main/java/com/electroventas/facturacion/widgets/BarChartWidget.kt

package com.electroventas.facturacion.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Widget personalizado para mostrar gráficas de barras verticales en el dashboard
 * Ideal para mostrar datos de ventas, inventario u otras métricas temporales
 *
 * Características:
 * - Barras verticales con colores personalizables
 * - Etiquetas en la base de cada barra
 * - Valores mostrados arriba de cada barra
 * - Líneas de cuadrícula para mejor lectura
 * - Escala automática basada en el valor máximo
 *
 * Uso:
 * val barChart = findViewById<BarChartWidget>(R.id.bar_chart_widget)
 * barChart.setData(listOf(
 *     BarChartWidget.BarDataItem("Lun", 100f, Color.BLUE),
 *     BarChartWidget.BarDataItem("Mar", 150f, Color.GREEN)
 * ))
 */
class BarChartWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Datos para el gráfico
    private var chartData: List<BarDataItem> = emptyList()
    private var maxValue: Float = 0f

    // Paints para diferentes elementos del gráfico
    private val paintBar = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 24f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT
    }
    private val paintValueText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 20f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
    }
    private val paintGrid = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        strokeWidth = 1f
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f) // Líneas punteadas
    }

    // Configuración de espaciado y márgenes
    private val topPadding = 60f      // Espacio superior para valores
    private val bottomPadding = 80f   // Espacio inferior para etiquetas
    private val sidePadding = 40f     // Espacios laterales
    private val gridLines = 5         // Número de líneas de cuadrícula

    /**
     * Data class para representar una barra del gráfico
     * @param label Etiqueta que aparece debajo de la barra (ej: "Lun", "Enero")
     * @param value Valor numérico de la barra
     * @param color Color de la barra en formato Int
     */
    data class BarDataItem(
        val label: String,
        val value: Float,
        val color: Int
    )

    /**
     * Establece los datos del gráfico de barras
     * Calcula automáticamente el valor máximo y redibuja
     * @param data Lista de barras a mostrar
     */
    fun setData(data: List<BarDataItem>) {
        chartData = data
        maxValue = data.maxOfOrNull { it.value } ?: 0f
        invalidate() // Redibuja la vista
    }

    /**
     * Método principal de dibujo del widget
     * Sobrescribe el método onDraw de View para dibujar el gráfico personalizado
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBarChart(canvas)
    }

    /**
     * Dibuja el gráfico de barras completo
     * Incluye cuadrícula, barras, etiquetas y valores
     */
    private fun drawBarChart(canvas: Canvas) {
        // Verificar que hay datos para mostrar
        if (chartData.isEmpty()) {
            drawEmptyState(canvas)
            return
        }

        // Calcular dimensiones del área de dibujo
        val chartWidth = width - 2 * sidePadding
        val chartHeight = height - topPadding - bottomPadding
        val barWidth = chartWidth / chartData.size * 0.6f  // 60% del espacio disponible
        val barSpacing = chartWidth / chartData.size * 0.4f // 40% para espaciado

        // Dibujar líneas de cuadrícula
        drawGridLines(canvas, chartHeight)

        // Dibujar cada barra con sus etiquetas
        chartData.forEachIndexed { index, item ->
            drawBar(canvas, item, index, barWidth, barSpacing, chartHeight)
        }
    }

    /**
     * Dibuja las líneas de cuadrícula horizontales
     * Ayuda a leer los valores de las barras
     */
    private fun drawGridLines(canvas: Canvas, chartHeight: Float) {
        for (i in 0..gridLines) {
            val y = topPadding + (chartHeight / gridLines) * i
            canvas.drawLine(sidePadding, y, width - sidePadding, y, paintGrid)

            // Dibujar valores de la escala en el lado izquierdo
            if (maxValue > 0) {
                val scaleValue = maxValue * (gridLines - i) / gridLines
                val scaleText = if (scaleValue >= 1000) {
                    "${(scaleValue / 1000).toInt()}k"
                } else {
                    scaleValue.toInt().toString()
                }

                val scalePaint = Paint(paintText).apply {
                    textAlign = Paint.Align.RIGHT
                    textSize = 18f
                    color = Color.GRAY
                }
                canvas.drawText(scaleText, sidePadding - 10f, y + 6f, scalePaint)
            }
        }
    }

    /**
     * Dibuja una barra individual con su etiqueta y valor
     */
    private fun drawBar(
        canvas: Canvas,
        item: BarDataItem,
        index: Int,
        barWidth: Float,
        barSpacing: Float,
        chartHeight: Float
    ) {
        // Calcular posición y dimensiones de la barra
        val barHeight = if (maxValue > 0) (item.value / maxValue) * chartHeight else 0f
        val left = sidePadding + index * (barWidth + barSpacing) + barSpacing / 2
        val top = topPadding + chartHeight - barHeight
        val right = left + barWidth
        val bottom = topPadding + chartHeight

        // Crear rectángulo con esquinas redondeadas para la barra
        val barRect = RectF(left, top, right, bottom)
        val cornerRadius = 8f

        // Configurar color de la barra
        paintBar.color = item.color

        // Dibujar la barra con esquinas redondeadas
        canvas.drawRoundRect(barRect, cornerRadius, cornerRadius, paintBar)

        // Agregar efecto de borde para mejor definición
        val borderPaint = Paint(paintBar).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = darkenColor(item.color, 0.8f)
        }
        canvas.drawRoundRect(barRect, cornerRadius, cornerRadius, borderPaint)

        // Dibujar etiqueta debajo de la barra
        val labelX = left + barWidth / 2
        val labelY = bottom + 35f
        canvas.drawText(item.label, labelX, labelY, paintText)

        // Dibujar valor arriba de la barra (si hay espacio suficiente)
        if (barHeight > 20f) { // Solo si la barra es lo suficientemente alta
            val valueY = top - 15f
            val valueText = formatValue(item.value)
            canvas.drawText(valueText, labelX, valueY, paintValueText)
        }
    }

    /**
     * Dibuja un estado vacío cuando no hay datos
     */
    private fun drawEmptyState(canvas: Canvas) {
        val emptyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.LTGRAY
            textSize = 36f
            textAlign = Paint.Align.CENTER
        }

        val centerX = width / 2f
        val centerY = height / 2f

        // Dibujar icono de gráfico vacío
        val iconPaint = Paint().apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 6f
        }

        // Dibujar barras vacías
        for (i in 0..3) {
            val barX = centerX - 60f + i * 30f
            canvas.drawLine(barX, centerY - 20f, barX, centerY + 20f, iconPaint)
        }

        // Mensaje de estado vacío
        canvas.drawText("Sin datos de ventas", centerX, centerY + 60f, emptyPaint)
    }

    /**
     * Formatea un valor numérico para mostrar
     * Convierte números grandes a formato abreviado (ej: 1.5k)
     */
    private fun formatValue(value: Float): String {
        return when {
            value >= 1000000 -> "${(value / 1000000).let { if (it % 1 == 0f) it.toInt() else String.format("%.1f", it) }}M"
            value >= 1000 -> "${(value / 1000).let { if (it % 1 == 0f) it.toInt() else String.format("%.1f", it) }}k"
            value % 1 == 0f -> value.toInt().toString()
            else -> String.format("%.1f", value)
        }
    }

    /**
     * Oscurece un color para crear efectos de borde
     * @param color Color original
     * @param factor Factor de oscurecimiento (0.0 = negro, 1.0 = sin cambio)
     */
    private fun darkenColor(color: Int, factor: Float): Int {
        val r = (Color.red(color) * factor).toInt()
        val g = (Color.green(color) * factor).toInt()
        val b = (Color.blue(color) * factor).toInt()
        return Color.rgb(r, g, b)
    }

    /**
     * Obtiene el valor máximo actual del gráfico
     */
    fun getMaxValue(): Float = maxValue

    /**
     * Obtiene los datos actuales del gráfico
     */
    fun getData(): List<BarDataItem> = chartData.toList()

    /**
     * Limpia todos los datos del gráfico
     */
    fun clearData() {
        chartData = emptyList()
        maxValue = 0f
        invalidate()
    }

    /**
     * Actualiza el color de una barra específica
     */
    fun updateBarColor(index: Int, newColor: Int) {
        if (index in chartData.indices) {
            chartData = chartData.mapIndexed { i, item ->
                if (i == index) item.copy(color = newColor) else item
            }
            invalidate()
        }
    }
}