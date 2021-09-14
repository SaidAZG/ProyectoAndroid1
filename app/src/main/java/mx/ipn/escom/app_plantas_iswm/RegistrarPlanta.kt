package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P6AddplantsBinding

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
        //Comprobar precedencia para rellenar campos
        var back: String = this.intent.extras?.getString("back").toString()
        if(back == "P"){
            val dto:ConsultarPlantas.DtoPlanta = this.intent.extras?.get("dto") as ConsultarPlantas.DtoPlanta
            editarPlantas(dto)
        }

    }

    private fun editarPlantas(dto: ConsultarPlantas.DtoPlanta) {
        binding.nComun.setText(dto.nombrePlanta)
        binding.especie.setText(dto.especie)
        binding.dimensiones.setText(dto.dimensiones)
        binding.fPlantacion.setText(dto.fechaPlantacion)
        binding.tExposicion.setText(dto.tiempoExposicion)

        if (dto.lugarPlantacion.equals("Jardín")){
            binding.Jardin.isChecked = true
        }
        if (dto.lugarPlantacion.equals("Maceta")){
            binding.Maceta.isChecked = true
        }
        if (dto.lugarPlantacion.equals("Jardinera")){
            binding.Jardinera.isChecked = true
        }

        if (dto.exposicionLuz.equals("Sol")){
            binding.Sol.isChecked = true
        }
        if (dto.exposicionLuz.equals("Sombra")){
            binding.Sombra.isChecked = true
        }
        if (dto.exposicionLuz.equals("Resolana")){
            binding.Resolana.isChecked = true
        }

        if (dto.temporadaPlantacion.equals("Primavera")){
            binding.temporadaPlantacion.setSelection(0)
        }
        if (dto.temporadaPlantacion.equals("Verano")){
            binding.temporadaPlantacion.setSelection(1)
        }
        if (dto.temporadaPlantacion.equals("Otoño")){
            binding.temporadaPlantacion.setSelection(2)
        }
        if (dto.temporadaPlantacion.equals("Invierno")){
            binding.temporadaPlantacion.setSelection(3)
        }

        binding.idBtnadd.setText("Actualizar")
    }

    fun registrarPlanta(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        var id: String = this.intent.extras?.getString("id").toString()

        val commonName: String = binding.nComun.text.toString()
        val species: String = binding.especie.text.toString()
        val dimension: String = binding.dimensiones.text.toString()
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
            "dimention" to dimension,
            "datePlant" to datePlant,
            "lightTime" to lightTime,
            "lightExposure" to lightExposure,
            "plantPlace" to plantPlace,
            "seasonPlant" to seasonPlant,
            "owner" to id
        )

        var back: String = this.intent.extras?.getString("back").toString()
        if(back == "P"){
            val dto:ConsultarPlantas.DtoPlanta = this.intent.extras?.get("dto") as ConsultarPlantas.DtoPlanta
            db.collection("plants").document(dto.idDocument)
                .update("commonName", map["commonName"].toString(),
                "datePlant",map["datePlant"].toString(),
                "dimention",map["dimention"].toString(),
                    "lightTime",map["lightTime"].toString(),
                "lightExposure",map["lightExposure"].toString(),
                "plantPlace",map["plantPlace"].toString(),
                "seasonPlant",map["seasonPlant"].toString(),
                "species",map["species"].toString(),
                "owner",map["owner"].toString())
                .addOnSuccessListener {
                    Log.d(TAG, "------------------"+dto.idDocument)
                    var intent = Intent(this, Menu::class.java)
                    intent.putExtra("id",id)
                    startActivity(intent)
                    Toast.makeText(this, "Planta Actualizada", Toast.LENGTH_SHORT).show()
                    finish()}
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
        }else{
            db.collection("plants")
                .add(map)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        ContentValues.TAG,
                        "DocumentSnapshot added with ID: " + documentReference.id
                    )
                    var intent = Intent(this, Menu::class.java)
                    intent.putExtra("id",id)
                    startActivity(intent)
                    Toast.makeText(this, "Planta Registrada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error adding document", e) }
        }
        //TODO Aplicar validacion de claves coincidentes y formato de correo
    }

    fun onRadioButtonClicked(view: android.view.View) {

    }
}