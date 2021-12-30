package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.PlantDetailsBinding
import mx.ipn.escom.app_plantas_iswm.dto.DtoPlanta

class More : AppCompatActivity() {
    private val binding: PlantDetailsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.plant_details)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activar la barra de navegacion superior
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Objeto Planta Seleccionado
        val dto:DtoPlanta = this.intent.extras?.get("dto") as DtoPlanta
        binding.plantName.text = dto.nombrePlanta
        binding.especie.text = dto.especie
        binding.subespecie.text = dto.subespecie
        binding.fecha.text = dto.fechaPlantacion
        binding.fechaRegistro.text = dto.fechaRegistro
        /*
        binding.dimensiones.text = (dto.alto+"cm. x "+dto.ancho+"cm. x "+dto.largo)
        binding.lugar.text = dto.lugarPlantacion
        binding.temporada.text = dto.temporadaPlantacion
        binding.exposicion.text = dto.exposicionLuz
        binding.tiempoExposicion.text = dto.tiempoExposicion
        */
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    fun editPlant(view: android.view.View) {
        val dto: DtoPlanta = this.intent.extras?.get("dto") as DtoPlanta
        var intent: Intent = Intent(this,RegistrarPlanta::class.java)
        intent.putExtra("dto",dto)
        intent.putExtra("back","P")
        startActivity(intent)
        finish()
    }

    fun deletePlant(view: android.view.View) {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dto:DtoPlanta = this.intent.extras?.get("dto") as DtoPlanta
        db.collection("plants").document(dto.idDocument)
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!")
            Toast.makeText(this,"Planta Eliminada :",Toast.LENGTH_LONG).show()}
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
        var intent: Intent = Intent(this,Menu::class.java)
        intent.putExtra("dto",dto)
        startActivity(intent)
        finish()
    }
}
