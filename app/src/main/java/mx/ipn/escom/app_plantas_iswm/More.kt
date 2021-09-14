package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P5ConsultarplantasBinding
import mx.ipn.escom.app_plantas_iswm.databinding.P5aVermasBinding

class More : AppCompatActivity() {
    private val binding: P5aVermasBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.p5a_vermas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activar la barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Objeto Planta Seleccionado
        val dto:ConsultarPlantas.DtoPlanta = this.intent.extras?.get("dto") as ConsultarPlantas.DtoPlanta
        binding.plantName.text = dto.nombrePlanta
        binding.especie.text = dto.especie
        binding.dimensiones.text = dto.dimensiones
        binding.fecha.text = dto.fechaPlantacion
        binding.lugar.text = dto.lugarPlantacion
        binding.temporada.text = dto.temporadaPlantacion
        binding.exposicion.text = dto.exposicionLuz
        binding.tiempoExposicion.text = dto.tiempoExposicion

    }

    fun editPlant(view: android.view.View) {
        val dto:ConsultarPlantas.DtoPlanta = this.intent.extras?.get("dto") as ConsultarPlantas.DtoPlanta
        var intent: Intent = Intent(this,RegistrarPlanta::class.java)
        intent.putExtra("dto",dto)
        intent.putExtra("id",dto.usuario)
        intent.putExtra("back","P")
        startActivity(intent)
        finish()
    }
    fun deletePlant(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dto:ConsultarPlantas.DtoPlanta = this.intent.extras?.get("dto") as ConsultarPlantas.DtoPlanta
        db.collection("plants").document(dto.idDocument)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
            Toast.makeText(this,"Planta Eliminada :",Toast.LENGTH_LONG).show()}
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}
