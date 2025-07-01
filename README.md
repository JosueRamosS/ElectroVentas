# 🏪 ElectroVentas - Sistema de Facturación Integral

<div align="center">

![ElectroVentas Logo](https://img.shields.io/badge/ElectroVentas-Sistema%20de%20Facturaci%C3%B3n-blue?style=for-the-badge&logo=android)

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-purple?style=flat&logo=kotlin)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API%2024+-green?style=flat&logo=android)](https://developer.android.com/)

*Sistema móvil integral para la gestión de ventas, inventario y personal en MYPE del sector electrodomésticos*

</div>

## 📋 Tabla de Contenidos

- [🎯 Descripción del Proyecto](#-descripción-del-proyecto)
- [✨ Características Principales](#-características-principales)
- [🏗️ Arquitectura](#️-arquitectura)
- [🚀 Instalación y Configuración](#-instalación-y-configuración)
- [📱 Capturas de Pantalla](#-capturas-de-pantalla)
- [🔧 Uso de la Aplicación](#-uso-de-la-aplicación)
- [💾 Persistencia de Datos](#-persistencia-de-datos)
- [🎨 Diseño y UX](#-diseño-y-ux)

## 🎯 Descripción del Proyecto

**ElectroVentas** es una aplicación móvil nativa para Android desarrollada en Kotlin que permite a las pequeñas y medianas empresas (MYPE) del sector electrodomésticos gestionar de manera integral sus operaciones comerciales diarias.

### 🎯 Problema que Resuelve

Lazs MYPE del sector electrodomésticos enfrentan desafíos significativos en:
- Gestión manual de inventarios propensa a errores
- Falta de control en la generación de documentos de venta
- Ausencia de sistemas de control de personal
- Dificultad para generar reportes y análisis de ventas
- Procesos lentos que afectan la productividad

### ✅ Nuestra Solución

ElectroVentas digitaliza y automatiza estos procesos proporcionando:
- ✅ **Dashboard visual** con métricas en tiempo real
- ✅ **Generación automatizada** de boletas, facturas y notas de venta
- ✅ **Control de inventario** con alertas de stock bajo
- ✅ **Gestión de personal** con registro de asistencia
- ✅ **Análisis estadísticos** con gráficos interactivos
- ✅ **Persistencia local** que funciona sin internet

## ✨ Características Principales

### 📊 Dashboard Inteligente
- **Métricas en tiempo real**: Ventas diarias, stock total, personal activo
- **Gráficos interactivos**: Pie charts de categorías, gráficos de barras de ventas
- **Accesos rápidos**: Navegación directa a funcionalidades principales
- **Historial de documentos**: Acceso rápido a todos los documentos generados

### 📄 Sistema de Facturación
- **Múltiples tipos de documento**: Boletas, facturas, notas de venta
- **Validación de clientes**: Integración simulada con RENIEC por DNI
- **Cálculos automáticos**: IGV, subtotales y totales
- **Generación de IDs únicos**: Sistema de numeración automática
- **Actualización automática de inventario**: Reduce stock al generar documentos

### 📦 Gestión de Inventario
- **Categorización inteligente**: Refrigeración, cocina, lavado, entretenimiento, climatización
- **Control de stock en tiempo real**: Actualizaciones automáticas
- **Alertas visuales**: Indicadores de color para stock bajo, normal y crítico
- **Información detallada**: Marca, modelo, precio, categoría por producto
- **Historial de movimientos**: Seguimiento de entradas y salidas

### 👥 Control de Personal
- **Registro de asistencia**: Sistema de entrada y salida
- **Estados en tiempo real**: Activo, inactivo, ausente
- **Horarios de trabajo**: Control de jornada laboral (9:00 AM - 9:00 PM)
- **Roles diferenciados**: Administrador y vendedor
- **Historial de asistencia**: Seguimiento de puntualidad y productividad

### 📈 Reportes y Estadísticas
- **Ventas por período**: Diarias, semanales, mensuales
- **Análisis de productos**: Más vendidos, por categoría, rentabilidad
- **Métricas de personal**: Asistencia, productividad, rendimiento
- **Gráficos visuales**: Representación clara de datos importantes
- **Exportación de datos**: Preparado para integración futura

## 🏗️ Arquitectura

### 🎯 Patrón de Diseño
- **Arquitectura**: Fragment-based con Navigation Component
- **Patrón**: Adapter Pattern para RecyclerViews
- **Gestión de datos**: Singleton Pattern para DataManager
- **UI**: Material Design 3 con temas adaptativos

### 📁 Estructura del Proyecto
```
app/
├── src/main/
│   ├── java/com/electroventas/facturacion/
│   │   ├── data/
│   │   │   └── DataManager.kt              # Gestión de persistencia
│   │   ├── fragments/
│   │   │   ├── HomeFragment.kt             # Dashboard principal
│   │   │   ├── FacturacionFragment.kt      # Generación de documentos
│   │   │   ├── InventarioFragment.kt       # Gestión de inventario
│   │   │   ├── PersonalFragment.kt         # Control de personal
│   │   │   ├── EstadisticasFragment.kt     # Reportes y análisis
│   │   │   ├── HistorialFragment.kt        # Historial de documentos
│   │   │   └── *Adapter.kt                 # Adaptadores para RecyclerViews
│   │   ├── widgets/
│   │   │   ├── PieChartWidget.kt           # Gráfico circular personalizado
│   │   │   └── BarChartWidget.kt           # Gráfico de barras personalizado
│   │   └── MainActivity.kt                 # Actividad principal
│   └── res/
│       ├── layout/                         # Layouts XML
│       ├── menu/                           # Menús de navegación
│       ├── navigation/                     # Grafo de navegación
│       ├── values/                         # Recursos (colores, strings, temas)
│       └── drawable/                       # Iconos y recursos gráficos
```

### 🛠️ Tecnologías Utilizadas
- **[Kotlin](https://kotlinlang.org/)**: Lenguaje principal de desarrollo
- **[Android Jetpack](https://developer.android.com/jetpack)**: Componentes de arquitectura moderna
  - Navigation Component
  - Fragment KTX
  - Lifecycle Components
- **[Material Design 3](https://material.io/design)**: Sistema de diseño moderno
- **[SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences)**: Persistencia local de datos
- **[Gson](https://github.com/google/gson)**: Serialización JSON para persistencia
- **Custom Views**: Widgets personalizados para gráficos

## 🚀 Instalación y Configuración

### 📋 Prerrequisitos
- **Android Studio**: Flamingo (2022.2.1) o superior
- **Android SDK**: API 24 (Android 7.0) como mínimo
- **Kotlin**: 1.9.0 o superior
- **Gradle**: 8.0 o superior

### ⬇️ Proceso de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/JosueRamosS/ExamenFinal
   ```

2. **Abrir en Android Studio**
   - Abrir Android Studio
   - File → Open → Seleccionar la carpeta del proyecto
   - Esperar a que Gradle sincronice automáticamente

3. **Configurar el SDK**
   - File → Project Structure → SDK Location
   - Verificar que Android SDK esté configurado correctamente

4. **Ejecutar la aplicación**
   - Conectar dispositivo Android o iniciar emulador
   - Click en "Run" (▶️) o usar `Ctrl+R`

## 🔧 Uso de la Aplicación

### 🏠 Navegación Principal
1. **Dashboard**: Vista general con métricas y accesos rápidos
2. **Bottom Navigation**: Navegación entre módulos principales
3. **Floating Action Buttons**: Acciones rápidas contextuales

### 📄 Generar Documentos
1. Seleccionar tipo de documento (Boleta/Factura/Nota)
2. Ingresar DNI del cliente y presionar buscar (simula búsqueda en RENIEC)
3. Completar detalles del producto
4. Generar documento automáticamente
5. El sistema actualiza inventario y estadísticas

### 📦 Gestionar Inventario
1. Ver lista completa de productos por categorías
2. Filtrar por stock bajo, categoría o búsqueda
3. Agregar nuevos productos (FAB)
4. Las ventas actualizan automáticamente el stock

### 👥 Controlar Personal
1. Ver estado actual de todos los empleados
2. Registrar entrada/salida con hora automática
3. Validar horarios laborales (9 AM - 9 PM)
4. Seguimiento de asistencia y productividad

### 📊 Analizar Estadísticas
1. Dashboard visual con métricas principales
2. Gráficos interactivos de ventas y categorías
3. Seguimiento de productos más vendidos
4. Análisis de rendimiento del personal

## 💾 Persistencia de Datos

### 🗄️ Sistema de Almacenamiento
ElectroVentas utiliza **SharedPreferences** con **Gson** para persistencia local:

```kotlin
// Ejemplo de guardado de datos
dataManager.guardarProductos(listaProductos)
dataManager.actualizarVentasDiarias(montoVenta)
dataManager.guardarDocumento(documento)
```

### 📊 Estructura de Datos

#### Productos
```kotlin
data class Producto(
    val id: Int,
    val nombre: String,
    val marca: String,
    val modelo: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)
```

#### Empleados
```kotlin
data class Empleado(
    val id: Int,
    val nombre: String,
    val rol: String,
    val horaEntrada: String?,
    val horaSalida: String?,
    val estado: EstadoEmpleado
)
```

#### Documentos Generados
```kotlin
data class DocumentoGenerado(
    val id: String,
    val tipo: String,
    val fecha: String,
    val hora: String,
    val clienteDni: String,
    val clienteNombre: String,
    val producto: String,
    val precio: Double,
    val vendedor: String
)
```

### 🔄 Sincronización de Datos
- **Automática**: Los datos se guardan inmediatamente después de cada operación
- **Tiempo real**: Las vistas se actualizan automáticamente
- **Respaldo**: Función de backup completo disponible
- **Restauración**: Capacidad de limpiar y restaurar datos

## 🎨 Diseño y UX

### 🎯 Principios de Diseño
- **Material Design 3**: Diseño moderno y consistente
- **Usabilidad**: Interfaz intuitiva para usuarios no técnicos
- **Accesibilidad**: Contrastes adecuados y navegación clara
- **Responsividad**: Adaptación a diferentes tamaños de pantalla

### 🌙 Temas Adaptativos
- **Tema claro**: Optimizado para uso durante el día
- **Tema oscuro**: Reducción de fatiga visual en entornos oscuros
- **Adaptación automática**: Sigue las preferencias del sistema

### 🎨 Paleta de Colores
```xml
<!-- Colores principales -->
<color name="primary">#1976D2</color>          <!-- Azul principal -->
<color name="secondary">#03DAC6</color>        <!-- Verde secundario -->
<color name="success">#4CAF50</color>          <!-- Verde éxito -->
<color name="warning">#FF9800</color>          <!-- Naranja advertencia -->
<color name="error">#F44336</color>            <!-- Rojo error -->
<color name="info">#2196F3</color>             <!-- Azul información -->
```

### 📐 Componentes UI Personalizados
- **PieChartWidget**: Gráfico circular para categorías de productos
- **BarChartWidget**: Gráfico de barras para ventas semanales
- **Cards responsivas**: Diseño adaptativo para diferentes contenidos
- **Estados visuales**: Indicadores de color para stock y estados
---

<div align="center">

### 🌟 ¡Hecho con ❤️ en Perú!

**ElectroVentas** - Transformando las MYPE, una venta a la vez.

[📧 Email](mailto:jramoss@ulasalle.edu.pe) 

</div>