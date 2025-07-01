// ======= MainActivity.kt (SOLUCIÓN SIMPLE) =======
package com.electroventas.facturacion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar tema predeterminado
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Configurar navegación
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Configuración estándar
        bottomNav.setupWithNavController(navController)

        // SOLUCIÓN SIMPLE: Listener personalizado solo para Home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Siempre ir al fragment Home principal
                    navController.popBackStack(R.id.nav_home, false)
                    true
                }
                else -> {
                    // Para otros items, usar navegación normal
                    navController.navigate(item.itemId)
                    true
                }
            }
        }
    }
}