package mx.ipn.escom.app_plantas_iswm

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.AddPlantBinding
import mx.ipn.escom.app_plantas_iswm.dto.DtoPlanta
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class RegistrarPlanta : AppCompatActivity() {

    private val binding: AddPlantBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.add_plant)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Barra de navegacion superior
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.root

        /*Datos de Spinner
        val spinner: Spinner = binding.temporadaPlantacion
        ArrayAdapter.createFromResource(
            this,
            R.array.seasonsArray,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }*/

        //Comprobar precedencia para rellenar campos
        //Si viene de registrar muestra los campos vacios, si viene de editar rellena los campos como estan en la base de datos
        val back: String = this.intent.extras?.getString("back").toString()
        if(back == "P"){
            val dto:DtoPlanta = this.intent.extras?.get("dto") as DtoPlanta
            editarPlantas(dto)
        }

        binding.fPlantacion.text = getTodaysDate()
    }

    private fun editarPlantas(dto: DtoPlanta) {
        binding.nComun.setText(dto.nombrePlanta)
        binding.especie.setText(dto.especie)
        binding.subespecie.setText(dto.subespecie)
        binding.fPlantacion.text = dto.fechaPlantacion
        binding.idBtnadd.text = "Actualizar"
        /*TODO [
        //  Separar los campos de dimensiones en 3 diferentes con longitud maxima de 3
        //  Despues concatenar los valores en el formato esaperado
        // ]
        binding.d1.setText(dto.alto)
        binding.d2.setText(dto.ancho)
        binding.d3.setText(dto.largo)
        binding.tExposicion.setText(dto.tiempoExposicion)

        if (dto.lugarPlantacion == "Jardín"){
            binding.Jardin.isChecked = true
        }
        if (dto.lugarPlantacion == "Maceta"){
            binding.Maceta.isChecked = true
        }
        if (dto.lugarPlantacion == "Jardinera"){
            binding.Jardinera.isChecked = true
        }
        if (dto.exposicionLuz == "Sol"){
            binding.Sol.isChecked = true
        }
        if (dto.exposicionLuz == "Sombra"){
            binding.Sombra.isChecked = true
        }
        if (dto.exposicionLuz == "Resolana"){
            binding.Resolana.isChecked = true
        }
        if (dto.temporadaPlantacion == "Primavera"){
            binding.temporadaPlantacion.setSelection(0)
        }
        if (dto.temporadaPlantacion == "Verano"){
            binding.temporadaPlantacion.setSelection(1)
        }
        if (dto.temporadaPlantacion == "Otoño"){
            binding.temporadaPlantacion.setSelection(2)
        }
        if (dto.temporadaPlantacion == "Invierno"){
            binding.temporadaPlantacion.setSelection(3)
        }
        */
    }

    fun registrarPlanta(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val prefM = PreferenceManager
        prefM.initialize(this)
        val id: String? = prefM.getUserID()

        val commonName: String = binding.nComun.text.toString()
        val species: String = binding.especie.text.toString()
        val subspecies: String = binding.subespecie.text.toString()
        val datePlant: String = binding.fPlantacion.text.toString()
        val registerDate: String? = getTodaysDate()

        /*
        val lightTime: String = binding.tExposicion.text.toString()

        val alto: String = binding.d1.text.toString()
        val ancho: String = binding.d2.text.toString()
        val largo: String = binding.d3.text.toString()

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
        */

        val map = hashMapOf(
            "commonName" to commonName,
            "species" to species,
            "subspecies" to subspecies,
            "datePlant" to datePlant,
            "registerDate" to registerDate,
            "owner" to id
        )

        if(commonName.isEmpty()){
            binding.nComun.error = "Datos Vacíos"
        }else if(species.isEmpty()){
            binding.especie.error = "Datos Vacíos"
        }else if(subspecies.isEmpty()){
            binding.subespecie.error = "Datos Vacíos"
        }
        /*else if(alto.isEmpty()) {
            binding.d1.error = "Datos Vacíos"
        }else if(ancho.isEmpty()) {
            binding.d2.error = "Datos Vacíos"
        }else if(largo.isEmpty()){
            binding.d3.error = "Datos Vacíos"
        }else if(lightTime.isEmpty()){
            binding.tExposicion.error = "Datos Vacíos"
        }else if(group1.checkedRadioButtonId == -1){
            Toast.makeText(this, "Seleccione un lugar de plantación",Toast.LENGTH_LONG).show()
        }else if(group2.checkedRadioButtonId == -1){
            Toast.makeText(this, "Seleccione el tipo de exposición a Luz",Toast.LENGTH_LONG).show()
        }*/
        else{
            val back: String = this.intent.extras?.getString("back").toString()
            if(back == "P"){
                val dto:DtoPlanta = this.intent.extras?.get("dto") as DtoPlanta
                db.collection("plants").document(dto.idDocument)
                    .update("commonName", map["commonName"].toString(),
                        "datePlant",map["datePlant"].toString(),
                        /*"height",map["height"].toString(),
                        "width",map["width"].toString(),
                        "length",map["length"].toString(),
                        "lightTime",map["lightTime"].toString(),
                        "lightExposure",map["lightExposure"].toString(),
                        "plantPlace",map["plantPlace"].toString(),
                        "seasonPlant",map["seasonPlant"].toString(),
                        */
                        "species",map["species"].toString(),
                        "subspecies",map["subspecies"].toString(),
                        "owner",map["owner"].toString())
                    .addOnSuccessListener {
                        Log.d(TAG, "------------------"+dto.idDocument)
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Planta Actualizada", Toast.LENGTH_SHORT).show()
                        finish()}
                    .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            }else{
                db.collection("plants")
                    .add(map)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            TAG,
                            "DocumentSnapshot added with ID: " + documentReference.id
                        )
                        val intent = Intent(this, Menu::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Planta Registrada", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
            }
        }

    }

    fun onRadioButtonClicked() {}

    private fun getTodaysDate(): String? {
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        var month: Int = cal.get(Calendar.MONTH)
        month += 1
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)
        return makeDateString(day, month, year)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String? {
        return "$day/$month/$year"
    }

    fun openDatePicker(view: View?) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            binding.fPlantacion.text = "$dayOfMonth/"+(monthOfYear+1)+"/$year"
        }, year, month, day)

        dpd.datePicker.maxDate = System.currentTimeMillis()
        dpd.show()
    }
}
