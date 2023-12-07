package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject

class InfoEventoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_evento)

        val tvNomEven   : TextView  = findViewById(R.id.tvNomEven)
        val tvAnfi      : TextView  = findViewById(R.id.tvAnfitri)
        val tvDetalle   : TextView  = findViewById(R.id.tvDetalle)
        val tvDireccio  : TextView  = findViewById(R.id.tvDireccio)
        val tvCup       : TextView  = findViewById(R.id.tvCup)
        val tvFech      : TextView  = findViewById(R.id.tvFech)
        val tvHora      : TextView  = findViewById(R.id.tvHora)
        val btAsistir   : Button    = findViewById(R.id.btActualizar)
        val btRegresar  : Button    = findViewById(R.id.btReturn)

        val nomReunion = intent.getStringExtra("NomReunion")
        val descripcion = intent.getStringExtra("Descripcion")
        val localizacion = intent.getStringExtra("Localizacion")
        val cupo = intent.getStringExtra("Cupo")
        val fecha = intent.getStringExtra("Fecha")
        val hora = intent.getStringExtra("Hora")
        val anfitrion = intent.getStringExtra("Anfitrion")

        tvNomEven.text = nomReunion
        tvAnfi.text = anfitrion
        tvDetalle.text = descripcion
        tvDireccio.text = localizacion
        tvCup.text = cupo
        tvFech.text = fecha
        tvHora.text = hora


        btAsistir.setOnClickListener {
            registrarAsistencia(nomReunion)
        }

        btRegresar.setOnClickListener {
            val intentRegresar = Intent(this, Eventos::class.java)
            startActivity(intentRegresar)
            finish()
        }
    }

    private fun registrarAsistencia(nomReunion: String?) {
        val url = "http://192.168.12.33/te/TeAsistencia.php"

        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid ?: return

        val jsonObject = JSONObject()
        jsonObject.put("nomreunion", nomReunion)
        jsonObject.put("uid", uid)

        val request = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            Response.Listener { response ->
                println(response)
                val intentRegresar = Intent(this, Eventos::class.java)
                startActivity(intentRegresar)
                Toast.makeText(this, "Asistencia registrada", Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener{ error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
                Toast.makeText(this, "Error al registrar asistencia", Toast.LENGTH_SHORT).show()
            })

        println("JSON enviado: ${jsonObject.toString()}")
        Volley.newRequestQueue(this).add(request)
    }
}