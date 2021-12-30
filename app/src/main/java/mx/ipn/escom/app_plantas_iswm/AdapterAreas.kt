package mx.ipn.escom.app_plantas_iswm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import mx.ipn.escom.app_plantas_iswm.dto.DtoAreas
import mx.ipn.escom.app_plantas_iswm.ui.AreasFragment

class AdapterAreas(private val listener: AreasFragment) :
    ListAdapter<DtoAreas, AdapterAreas.ViewHolderDatos>(AreasDiffUtil()) {

    //Enlazar adaptador con el itemlist
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderDatos {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_areas, parent, false)
        return ViewHolderDatos(view)
    }

    override fun onBindViewHolder(holder: ViewHolderDatos, position: Int) {
        val dto: DtoAreas = currentList[position]
        holder.plantName.text = dto.nombreArea
        //holder.registerDate.text = ("Fecha Registro: "+ dto.fechaRegistro)
        holder.seeMore.setOnClickListener { listener.seeMore(dto) }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class ViewHolderDatos(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plantName: TextView = itemView.findViewById<View>(R.id.plantName) as TextView
        //var registerDate: TextView = itemView.findViewById<View>(R.id.registerDate) as TextView
        var seeMore: MaterialCardView = itemView.findViewById<View>(R.id.cardView) as MaterialCardView
    }
}

    class AreasDiffUtil : DiffUtil.ItemCallback<DtoAreas>() {
    override fun areItemsTheSame(oldItem: DtoAreas, newItem: DtoAreas): Boolean {
        return oldItem.nombreArea == newItem.nombreArea
    }

    override fun areContentsTheSame(oldItem: DtoAreas, newItem: DtoAreas): Boolean {
        return oldItem.nombreArea == newItem.nombreArea
    }
}