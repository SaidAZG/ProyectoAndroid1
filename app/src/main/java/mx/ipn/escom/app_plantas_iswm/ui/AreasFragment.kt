package mx.ipn.escom.app_plantas_iswm.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.app_plantas_iswm.*
import mx.ipn.escom.app_plantas_iswm.databinding.FragmentConsultAreasBinding
import mx.ipn.escom.app_plantas_iswm.dto.DtoAreas
import mx.ipn.escom.app_plantas_iswm.dto.DtoPlanta

class AreasFragment : Fragment(), View.OnClickListener, OnItemClick {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private lateinit var adapter: AdapterAreas

    private val binding: FragmentConsultAreasBinding by lazy {
        FragmentConsultAreasBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val prefM = PreferenceManager
        PreferenceManager.initialize(requireContext())

        //Variable de Sesion
        val id: String? = prefM.getUserID()
        Log.d(ContentValues.TAG,"------------------------------------ $id")

        //ClickListeners
        binding.ETPlantSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.fabAreas.setOnClickListener{
            goToRegisterAreas()
        }
        iniciarRecyclerView(id)
        return binding.root
    }

    private fun iniciarRecyclerView(id: String?) {
        binding.RVAreas.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdapterAreas(this)
        binding.RVAreas.adapter = adapter

        db.collection("plants").whereEqualTo("owner", id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result!!.documents.isNotEmpty()) {
                        val listDatos = mutableListOf<DtoAreas>()
                        for (document in task.result!!) {
                            Log.d(ContentValues.TAG, document.id + " => " + document.data)
                            val dto = DtoAreas()
                            dto.idDocument = document.id
                            dto.nombreArea = document.data["commonName"].toString()
                            dto.usuario = document.data["owner"].toString()
                            /*
                            dto.especie = document.data["species"].toString()
                            dto.subespecie = document.data["subspecies"].toString()
                            dto.fechaPlantacion = document.data["datePlant"].toString()
                            dto.fechaRegistro = document.data["registerDate"].toString()
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
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }
    }

    override fun onClick(v: View) {}

    override fun seeMore(dtoPlanta: DtoPlanta) {
        TODO("Not yet implemented")
    }

    override fun seeMore(dtoAreas: DtoAreas) {
        var intent: Intent = Intent(requireContext(), More::class.java)
        intent.putExtra("dto",dtoAreas)
        //intent.putExtra("id",dtoPlanta.usuario)
        startActivity(intent)
    }

    fun goToRegisterAreas() {
        //TODO [Agregar la pantalla de agregar Areas]
        var intent: Intent = Intent(requireContext(), RegistrarPlanta::class.java)
        intent.putExtra("back","M")
        startActivity(intent)
    }

}
