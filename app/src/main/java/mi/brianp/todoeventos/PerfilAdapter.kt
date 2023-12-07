package mi.brianp.todoeventos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PerfilAdapter : RecyclerView.Adapter<PerfilVH> {
    private var context : Context
    private var datos : ArrayList<Even>
    constructor(context: Context){
        this.context = context
        this.datos = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilVH {
        val view = LayoutInflater.from(context).inflate(
            R.layout.fila_eventos, parent, false)
        return PerfilVH( view )
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: PerfilVH, position: Int) {
        var evento: Even = datos[position]

        holder.ivIcono.setImageResource(evento.icono)
        holder.tvNomReunion.text = evento.NomReunion
        holder.tvDescripcion.text = evento.Descripcion
        holder.tvLocalizacion.text = "Loca. ${evento.Localizacion}"
        holder.tvCupo.text = "Cupo: ${evento.Cupo}"
        holder.tvFecha.text = "Fecha: ${evento.Fecha}"
        holder.tvAnfitrion.text = "Anfitrion ${evento.Anfitrion}"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AdminEventoActivity::class.java)
            intent.putExtra("NomReunion", evento.NomReunion)
            intent.putExtra("Descripcion", evento.Descripcion)
            intent.putExtra("Localizacion", evento.Localizacion)
            intent.putExtra("Cupo", evento.Cupo.toString())
            intent.putExtra("Fecha", evento.Fecha)
            intent.putExtra("Hora", evento.Hora)
            intent.putExtra("Anfitrion", evento.Anfitrion)
            context.startActivity(intent)
        }
    }

    fun llenar(datos : java.util.ArrayList<Even>) {
        this.datos = datos
        this.notifyDataSetChanged()
    }
}
