// ======= PieChartWidget.kt =======
// Ubicación: app/src/main/java/com/electroventas/facturacion/widgets/PieChartWidget.kt

package com.electroventas.facturacion.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin

/**
 * Widget personalizado para mostrar gráficas circulares (pie chart) en el dashboard
 * Dibuja un gráfico de torta con colores personalizados y etiquetas de porcentaje
 *
 * Uso:
 * val pieChart = findViewById<PieChartWidget>(R.id.pie_chart_widget)
 * pieChart.setData(listOf(
 *     PieChartWidget.ChartDataItem("Categoría 1", 30f, Color.RED),
 *     PieChartWidget.ChartDataItem("Categoría 2", 70f, Color.BLUE)
 * ))
 */
class PieChartWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Datos para el gráfico
    private var chartData: List<ChartDataItem> = emptyList()
    private var total: Float = 0f

    // Paints para dibujar diferentes elementos
    private val paintSlice = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = 32f
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT_BOLD
        setShadowLayer(2f, 1f, 1f, Color.BLACK) // Sombra para mejor legibilidad
    }

    // Rectángulo para definir el área del círculo
    private val rectF = RectF()

    /**
     * Data class para representar un elemento del gráfico circular
     * @param label Etiqueta descriptiva del segmento
     * @param value Valor numérico del segmento
     * @param color Color del segmento en formato Int
     */
    data class ChartDataItem(
        val label: String,
        val value: Float,
        val color: Int
    )

    /**
     * Establece los datos del gráfico y actualiza la visualización
     * @param data Lista de elementos a mostrar en el gráfico
     */
    fun setData(data: List<ChartDataItem>) {
        chartData = data
        total = data.sumOf { it.value.toDouble() }.toFloat()
        invalidate() // Redibuja la vista con los nuevos datos
    }

    /**
     * Método principal de dibujo del widget
     * Se llama automáticamente cuando la vista necesita ser redibujada
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawPieChart(canvas)
    }

    /**
     * Dibuja el gráfico de torta completo con todos sus segmentos
     * Calcula ángulos, posiciones y dibuja tanto los segmentos como el texto
     */
    private fun drawPieChart(canvas: Canvas) {
        // Verificar que hay datos para dibujar
        if (chartData.isEmpty() || total == 0f) {
            drawEmptyState(canvas)
            return
        }

        // Calcular dimensiones del gráfico
        val centerX = width / 2f
        val centerY = height / 2f
        val radius = minOf(centerX, centerY) * 0.8f // 80% del espacio disponible

        // Configurar el rectángulo que contiene el círculo
        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        var startAngle = -90f // Comenzar desde la parte superior (12 en punto)

        // Dibujar cada segmento del gráfico
        chartData.forEach { item ->
            val sweepAngle = (item.value / total) * 360f

            // Configurar color del segmento actual
            paintSlice.color = item.color

            // Dibujar el segmento como un arco relleno
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paintSlice)

            // Calcular posición para mostrar el porcentaje
            val percentage = (item.value / total) * 100

            // Solo mostrar texto si el segmento es lo suficientemente grande
            if (percentage > 8) { // Umbral del 8% para evitar texto sobrepuesto
                val textAngle = startAngle + sweepAngle / 2 // Centro del segmento
                val textRadius = radius * 0.65f // Posición del texto dentro del segmento

                // Convertir ángulo polar a coordenadas cartesianas
                val textX = centerX + cos(Math.toRadians(textAngle.toDouble())).toFloat() * textRadius
                val textY = centerY + sin(Math.toRadians(textAngle.toDouble())).toFloat() * textRadius

                // Dibujar el porcentaje
                canvas.drawText("${percentage.toInt()}%", textX, textY, paintText)
            }

            // Avanzar al siguiente segmento
            startAngle += sweepAngle
        }

        // Dibujar un círculo interno para crear efecto "donut" (opcional)
        drawCenterCircle(canvas, centerX, centerY, radius * 0.3f)
    }

    /**
     * Dibuja un círculo central para crear efecto de gráfico tipo "donut"
     * Mejora la legibilidad y da un aspecto más moderno
     */
    private fun drawCenterCircle(canvas: Canvas, centerX: Float, centerY: Float, innerRadius: Float) {
        val centerPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }
        canvas.drawCircle(centerX, centerY, innerRadius, centerPaint)

        // Agregar borde al círculo central
        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }
        canvas.drawCircle(centerX, centerY, innerRadius, borderPaint)
    }

    /**
     * Dibuja un estado vacío cuando no hay datos disponibles
     * Muestra un mensaje informativo al usuario
     */
    private fun drawEmptyState(canvas: Canvas) {
        val emptyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.LTGRAY
            textSize = 40f
            textAlign = Paint.Align.CENTER
        }

        val centerX = width / 2f
        val centerY = height / 2f

        // Dibujar círculo vacío
        val emptyCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.LTGRAY
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }
        canvas.drawCircle(centerX, centerY, minOf(centerX, centerY) * 0.8f, emptyCirclePaint)

        // Mostrar mensaje
        canvas.drawText("Sin datos", centerX, centerY, emptyPaint)
    }

    /**
     * Define el tamaño mínimo del widget
     * Asegura que el gráfico sea cuadrado para mantener proporciones
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = minOf(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
        setMeasuredDimension(size, size)
    }

    /**
     * Obtiene el total de valores en el gráfico
     * Útil para cálculos externos
     */
    fun getTotal(): Float = total

    /**
     * Obtiene los datos actuales del gráfico
     * Útil para exportar o procesar los datos
     */
    fun getData(): List<ChartDataItem> = chartData.toList()

    /**
     * Limpia todos los datos del gráfico
     * Útil para resetear el widget
     */
    fun clearData() {
        chartData = emptyList()
        total = 0f
        invalidate()
    }
}