package mx.ipn.escom.app_plantas_iswm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import mx.ipn.escom.app_plantas_iswm.databinding.P5ConsultarplantasBinding

class ConsultarPlantas : AppCompatActivity(), View.OnClickListener {

    private val binding: P5ConsultarplantasBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.p5_consultarplantas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activar la barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Variable de Sesion
        var user:String = this.intent.extras?.getString("user").toString()
        //ClickListeners
        binding.ETPlantSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding.userName.text = user
    }

    override fun onClick(v: View) {}
}