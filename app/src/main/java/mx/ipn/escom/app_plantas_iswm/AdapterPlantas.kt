package mx.ipn.escom.app_plantas_iswm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import mx.ipn.escom.app_plantas_iswm.dto.DtoPlanta
import mx.ipn.escom.app_plantas_iswm.ui.PlantsFragment

class AdapterPlantas(private val listener: PlantsFragment) :
    ListAdapter<DtoPlanta, AdapterPlantas.ViewHolderDatos>(PlantasDiffUtil()) {

    //Enlazar adaptador con el itemlist
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_plants, parent, false)
        return ViewHolderDatos(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val dto: DtoPlanta = currentList[position]
        holder.plantName.text = dto.nombrePlanta
        holder.registerDate.text = ("Fecha Registro: "+ dto.fechaRegistro)
        holder.seeMore.setOnClickListener { listener.seeMore(dto) }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolderDatos(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantName: TextView = itemView.findViewById<View>(R.id.plantName) as TextView
        var registerDate: TextView = itemView.findViewById<View>(R.id.registerDate) as TextView
        var seeMore: MaterialCardView = itemView.findViewById<View>(R.id.cardView) as MaterialCardView
    }
}

    class PlantasDiffUtil : DiffUtil.ItemCallback<DtoPlanta>() {
    override fun areItemsTheSame(oldItem: DtoPlanta, newItem: DtoPlanta): Boolean {
        return oldItem.nombrePlanta == newItem.nombrePlanta
    }

    override fun areContentsTheSame(oldItem: DtoPlanta, newItem: DtoPlanta): Boolean {
        return oldItem.nombrePlanta == newItem.nombrePlanta
    }
}