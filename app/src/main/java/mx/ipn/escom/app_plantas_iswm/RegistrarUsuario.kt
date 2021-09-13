package mx.ipn.escom.app_plantas_iswm

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import mx.ipn.escom.app_plantas_iswm.databinding.P3bSignUpBinding

class RegistrarUsuario : AppCompatActivity(), View.OnClickListener{
    private val binding: P3bSignUpBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.p3b_sign_up)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.p3b_sign_up)
    }
    override fun onClick(v: View?) {
        Toast.makeText(this,"Registrarse",Toast.LENGTH_SHORT).show()
    }
}