package mi.brianp.todoeventos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PerfilVH : RecyclerView.ViewHolder {
    val ivIcono : ImageView
    val tvNomReunion : TextView
    val tvDescripcion : TextView
    val tvLocalizacion : TextView
    val tvCupo : TextView
    val tvFecha : TextView
    val tvAnfitrion : TextView

    constructor(itemView: View) : super(itemView){
        ivIcono = itemView.findViewById(R.id.ivIcono)
        tvNomReunion = itemView.findViewById(R.id.tvNomReunion)
        tvDescripcion = itemView.findViewById(R.id.tvDescripcion)
        tvLocalizacion = itemView.findViewById(R.id.tvLocalizacion)
        tvCupo = itemView.findViewById(R.id.tvCupo)
        tvFecha = itemView.findViewById(R.id.tvFecha)
        tvAnfitrion = itemView.findViewById(R.id.tvAnfitrion)
    }
}
