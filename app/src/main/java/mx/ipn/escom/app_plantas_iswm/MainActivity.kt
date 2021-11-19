package mx.ipn.escom.app_plantas_iswm;

import android.app.Activity;
import android.content.Intent

import android.os.Bundle;
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import mx.ipn.escom.app_plantas_iswm.databinding.GetStartedBinding

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private val binding: GetStartedBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.get_started)
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
}