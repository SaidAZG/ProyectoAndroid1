package mx.ipn.escom.app_plantas_iswm

import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.NavigationDrawerBinding
import mx.ipn.escom.app_plantas_iswm.PreferenceManager

class Menu : AppCompatActivity(){

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val binding: NavigationDrawerBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.navigation_drawer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefM = PreferenceManager
        prefM.initialize(this)

        super.onCreate(savedInstanceState)
            actionBar?.hide()
            setSupportActionBar(binding.topAppBar)
            binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(findViewById(R.id.navigation_view),true)
        }
        //Agregar el nombre del usuario para el menu lateral
        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.menu_userName).text = PreferenceManager.getName()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            Toast.makeText(this,menuItem.title,Toast.LENGTH_LONG).show()
            menuItem.isChecked = true
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}