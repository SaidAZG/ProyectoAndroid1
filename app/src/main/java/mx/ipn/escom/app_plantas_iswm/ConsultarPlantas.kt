package mx.ipn.escom.app_plantas_iswm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.databinding.P5ConsultarplantasBinding
import java.io.Serializable
import java.util.*

class ConsultarPlantas : AppCompatActivity(), View.OnClickListener, OnItemClick {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var adapter: AdapterPlantas

    private val binding: P5ConsultarplantasBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.p5_consultarplantas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Activar la barra de navegacion superior
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Variable de Sesion
        var id: String = this.intent.extras?.getString("id").toString()

        //ClickListeners
        binding.ETPlantSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        iniciarRecyclerView(id)

    }

    private fun iniciarRecyclerView(id: String) {
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
                            dto.dimensiones = document.data["dimention"].toString()
                            dto.fechaPlantacion = document.data["datePlant"].toString()
                            dto.lugarPlantacion = document.data["plantPlace"].toString()
                            dto.temporadaPlantacion = document.data["seasonPlant"].toString()
                            dto.exposicionLuz = document.data["lightExposure"].toString()
                            dto.tiempoExposicion = document.data["lightTime"].toString()
                            dto.usuario = document.data["owner"].toString()
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
        var dimensiones :String = "",
        var fechaPlantacion :String = "",
        var lugarPlantacion:String = "",
        var temporadaPlantacion:String = "",
        var exposicionLuz:String = "",
        var tiempoExposicion:String = "",
        var usuario:String =""
    ) : Serializable

    override fun editarPlanta(dtoPlanta: DtoPlanta) {
        var intent: Intent = Intent(this,RegistrarPlanta::class.java)
        intent.putExtra("dto",dtoPlanta)
        intent.putExtra("id",dtoPlanta.usuario)
        intent.putExtra("back","P")
        startActivity(intent)
    }

    override fun eliminarPlanta(dtoPlanta: DtoPlanta) {
        TODO("Not yet implemented")
    }

    override fun seeMore(dtoPlanta: DtoPlanta) {
        var intent: Intent = Intent(this,More::class.java)
        intent.putExtra("dto",dtoPlanta)
        intent.putExtra("id",dtoPlanta.usuario)
        startActivity(intent)
        finish()
    }
}