package mx.ipn.escom.app_plantas_iswm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.NavigationDrawerBinding

class Menu : AppCompatActivity(){

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val binding: NavigationDrawerBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.navigation_drawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefM = PreferenceManager
        prefM.initialize(this)

        super.onCreate(savedInstanceState)
        //actionBar?.hide()
        setSupportActionBar(binding.topAppBar)


        //Codigo basado en proyecto
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navigationView
        val navController = findNavController(R.id.nav_host_fragment_container)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.plantsFragment, R.id.areasFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Agregar el nombre del usuario para el menu lateral
        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.menu_userName).text =
            PreferenceManager.getName()

        //Codigo semi-improvisado
        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(findViewById(R.id.navigation_view), true)
        }

        //Handle menu item selected
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.title) {
                "Cerrar Sesión" -> logOut()
            }
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_drawer, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun logOut() {
        val prefM = PreferenceManager
        prefM.saveStatus(false)
        prefM.saveName("NOT FOUND")
        prefM.saveUserID("NOT FOUND")
        var intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}

