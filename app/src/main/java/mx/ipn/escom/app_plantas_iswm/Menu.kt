package mx.ipn.escom.app_plantas_iswm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import mx.ipn.escom.app_plantas_iswm.databinding.MenuscreenBinding
import mx.ipn.escom.app_plantas_iswm.databinding.P3aLoginBinding

class Menu : AppCompatActivity() {
    private val binding: MenuscreenBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.menuscreen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.root
    }

    fun goToPlants(view: android.view.View) {
        //Variable de Sesion para obtencion de datos respecto a usuario
        var user: String = this.intent.extras?.getString("user").toString()
        var intent: Intent = Intent(this,ConsultarPlantas::class.java)
        intent.putExtra("user",user)
        startActivity(intent)
    }
    fun goToRegisterPlants(view: android.view.View) {
        var intent: Intent = Intent(this,ConsultarPlantas::class.java)
        startActivity(intent)
    }
}