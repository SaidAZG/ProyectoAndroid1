package mx.ipn.escom.app_plantas_iswm;

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import mx.ipn.escom.app_plantas_iswm.databinding.WelcomeUserScreenBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private val binding: WelcomeUserScreenBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.welcome_user_screen)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        //supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar?.hide()
        binding.root
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    fun goToLogin(view: android.view.View) {
        var intent: Intent = Intent(this,IniciarSesion::class.java)
        startActivity(intent)
        finish()
    }
    //TODO [Definir datos de registro de áreas
//          Datos de especie y subespecie para registro de planta opcionales, subir a la base como no definido
//          Agregar success dialogs en registros (areas, plantas, cuidados)
//          ]
}