package mx.ipn.escom.app_plantas_iswm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class AdapterPlantas(private val listener: ConsultarPlantas) :
    ListAdapter<ConsultarPlantas.DtoPlanta, AdapterPlantas.ViewHolderDatos>(PlantasDiffUtil()) {

    //Enlazar adaptador con el itemlist
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.itemlist_p5_consultarplantas, parent, false)
        return ViewHolderDatos(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val dto: ConsultarPlantas.DtoPlanta = currentList[position]
        holder.plantName.text = dto.nombrePlanta
        holder.edit.setOnClickListener { listener.editarPlanta(dto) }
        holder.delete.setOnClickListener { listener.eliminarPlanta(dto) }
        holder.seeMore.setOnClickListener { listener.seeMore(dto) }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolderDatos(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantName: TextView = itemView.findViewById<View>(R.id.plantName) as TextView
        var delete: ImageButton = itemView.findViewById<View>(R.id.delete) as ImageButton
        var edit: ImageButton = itemView.findViewById<View>(R.id.edit) as ImageButton
        var seeMore: TextView = itemView.findViewById<View>(R.id.seeMore) as TextView
    }
}

class PlantasDiffUtil : DiffUtil.ItemCallback<ConsultarPlantas.DtoPlanta>() {
    override fun areItemsTheSame(oldItem: ConsultarPlantas.DtoPlanta, newItem: ConsultarPlantas.DtoPlanta): Boolean {
        return oldItem.nombrePlanta == newItem.nombrePlanta
    }

    override fun areContentsTheSame(oldItem: ConsultarPlantas.DtoPlanta, newItem: ConsultarPlantas.DtoPlanta): Boolean {
        return oldItem.nombrePlanta == newItem.nombrePlanta
    }
}