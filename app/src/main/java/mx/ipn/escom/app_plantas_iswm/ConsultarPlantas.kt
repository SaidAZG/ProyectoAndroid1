package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.ConsultPlantsBinding
import java.io.Serializable
import java.util.*

class ConsultarPlantas : AppCompatActivity(), View.OnClickListener, OnItemClick {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var adapter: AdapterPlantas

    private val binding: ConsultPlantsBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.consult_plants)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefM = PreferenceManager
        prefM.initialize(this)
        super.onCreate(savedInstanceState)
        //Variable de Sesion
        val id: String? = prefM.getUserID()
        Log.d(TAG,"------------------------------------ $id")

        //ClickListeners
        binding.ETPlantSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        iniciarRecyclerView(id)
    }

    private fun iniciarRecyclerView(id: String?) {
        binding.RVConsults.layoutManager = LinearLayoutManager(this)
        adapter = AdapterPlantas(this)
        binding.RVConsults.adapter = adapter

        db.collection("plants").whereEqualTo("owner", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.documents.isNotEmpty()) {
                        val listDatos = ArrayList<DtoPlanta>()
                        for (document in task.result!!) {
                            Log.d(TAG, document.id + " => " + document.data)
                            val dto = DtoPlanta()
                            dto.idDocument = document.id
                            dto.nombrePlanta = document.data["commonName"].toString()
                            dto.especie = document.data["species"].toString()
                            dto.subespecie = document.data["subspecies"].toString()
                            dto.fechaPlantacion = document.data["datePlant"].toString()
                            dto.fechaRegistro = document.data["registerDate"].toString()
                            dto.usuario = document.data["owner"].toString()
                            /*
                            dto.alto = document.data["height"].toString()
                            dto.ancho = document.data["width"].toString()
                            dto.largo = document.data["length"].toString()
                            dto.lugarPlantacion = document.data["plantPlace"].toString()
                            dto.temporadaPlantacion = document.data["seasonPlant"].toString()
                            dto.exposicionLuz = document.data["lightExposure"].toString()
                            dto.tiempoExposicion = document.data["lightTime"].toString()
                            */
                            listDatos.add(dto)
                        }
                        adapter.submitList(listDatos)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    override fun onClick(v: View) {}

    data class DtoPlanta(
        var idDocument: String = "",
        var nombrePlanta: String = "",
        var especie:String = "",
        var subespecie:String = "",
        var fechaPlantacion :String = "",
        var fechaRegistro :String = "",
        /*
        var alto :String = "",
        var ancho :String = "",
        var largo :String = "",
        var lugarPlantacion:String = "",
        var temporadaPlantacion:String = "",
        var exposicionLuz:String = "",
        var tiempoExposicion:String = "",
        */
        var usuario:String =""
    ) : Serializable

    override fun seeMore(dtoPlanta: DtoPlanta) {
        var intent: Intent = Intent(this,More::class.java)
        intent.putExtra("dto",dtoPlanta)
        intent.putExtra("id",dtoPlanta.usuario)
        startActivity(intent)
    }

    fun goToRegisterPlants(view: android.view.View) {
        var id: String = this.intent.extras?.getString("id").toString()
        var intent: Intent = Intent(this,RegistrarPlanta::class.java)
        intent.putExtra("id",id)
        intent.putExtra("back","M")
        startActivity(intent)
    }
}