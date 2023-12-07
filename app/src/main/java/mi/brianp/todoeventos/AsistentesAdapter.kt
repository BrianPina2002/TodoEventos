package mi.brianp.todoeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AsistentesAdapter(private val asistentes: List<String>) :
    RecyclerView.Adapter<AsistentesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreAsistente: TextView = view.findViewById(R.id.tvNombreAsistente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_asistente, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombreAsistente.text = asistentes[position]
    }

    override fun getItemCount(): Int {
        return asistentes.size
    }
}
