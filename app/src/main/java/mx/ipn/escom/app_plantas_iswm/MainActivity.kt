package mx.ipn.escom.app_plantas_iswm;

import android.app.Activity;
import android.app.Instrumentation
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with

import android.os.Bundle;
import android.os.Environment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.google.android.gms.cast.framework.media.ImagePicker
import mx.ipn.escom.app_plantas_iswm.databinding.GetStartedBinding
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        actionBar?.hide()
        binding.root

        findViewById<Button>(R.id.load_img_btn).setOnClickListener{
            selectPictureLauncher.launch("image/*")
        }
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