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
        binding.userName.text = id
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
        var nombrePlanta: String = ""
    ) : Serializable

    override fun editarPlanta(dtoPlanta: DtoPlanta) {
        TODO("Not yet implemented")
    }

    override fun eliminarPlanta(dtoPlanta: DtoPlanta) {
        TODO("Not yet implemented")
    }

    override fun seeMore(dtoPlanta: DtoPlanta) {
        var intent: Intent = Intent(this,More::class.java)
        intent.putExtra("id",dtoPlanta.idDocument)
        startActivity(intent)
        finish()
    }
}