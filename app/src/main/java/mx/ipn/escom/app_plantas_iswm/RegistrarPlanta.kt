package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P3aLoginBinding
import mx.ipn.escom.app_plantas_iswm.databinding.P6AddplantsBinding
import java.util.*
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

class RegistrarPlanta : AppCompatActivity() {

    private val binding: P6AddplantsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.p6_addplants)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.root

        //Datos de Spinner
        val spinner: Spinner = binding.temporadaPlantacion
        ArrayAdapter.createFromResource(
            this,
            R.array.seasonsArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    fun registrarPlanta(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var id: String = this.intent.extras?.getString("id").toString()
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

        val commonName: String = binding.nComun.text.toString()
        val species: String = binding.especie.text.toString()
        val dimention: String = binding.dimensiones.text.toString()
        val datePlant: String = binding.fPlantacion.text.toString()
        val lightTime: String = binding.tExposicion.text.toString()

        val group1: RadioGroup = binding.rbg1
        val plantPlace: String = when (group1.checkedRadioButtonId) {
            binding.Jardin.id -> binding.Jardin.tag as String
            binding.Maceta.id -> binding.Maceta.tag as String
            else -> binding.Jardinera.tag as String
        }

        val group2: RadioGroup = binding.rbg2
        val lightExposure: String = when (group2.checkedRadioButtonId) {
            binding.Sol.id -> binding.Sol.tag as String
            binding.Sombra.id -> binding.Sombra.tag as String
            else -> binding.Resolana.tag as String
        }

        val seasonSpinner: Spinner = binding.temporadaPlantacion
        val seasonPlant: String = seasonSpinner.selectedItem.toString()


        val map = hashMapOf(
            "commonName" to commonName,
            "species" to species,
            "dimention" to dimention,
            "datePlant" to datePlant,
            "lighTime" to lightTime,
            "lightExposure" to lightExposure,
            "plantPlace" to plantPlace,
            "seasonPlant" to seasonPlant,
            "owner" to id
        )

        db.collection("plants")
            .add(map)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    ContentValues.TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
                var intent = Intent(this, Menu::class.java)
                startActivity(intent)
                Toast.makeText(this, "Planta Registrada", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding document", e) }

        //TODO Aplicar validacion de claves coincidentes y formato de correo
    }

    fun onRadioButtonClicked(view: android.view.View) {

    }
}