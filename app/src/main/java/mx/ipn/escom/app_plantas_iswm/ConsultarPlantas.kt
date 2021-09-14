package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P5ConsultarplantasBinding
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener




class ConsultarPlantas : AppCompatActivity(), View.OnClickListener {

    private val binding: P5ConsultarplantasBinding by lazy{
        DataBindingUtil.setContentView(this, R.layout.p5_consultarplantas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activar la barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Variable de Sesion
        var id:String = this.intent.extras?.getString("id").toString()

        //ClickListeners
        binding.ETPlantSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.userName.text = id
    }


    override fun onClick(v: View) {}
}