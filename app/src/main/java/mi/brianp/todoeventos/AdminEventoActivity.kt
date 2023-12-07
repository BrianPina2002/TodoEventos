package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject

class AdminEventoActivity : AppCompatActivity() {
    private lateinit var tvAnfitri       : TextView
    private lateinit var etNombreEven    : EditText
    private lateinit var etDeta          : EditText
    private lateinit var etDirecc        : EditText
    private lateinit var etCup           : EditText
    private lateinit var etFec           : EditText
    private lateinit var etHora          : EditText
    private lateinit var rvAsistencia    : RecyclerView
    private lateinit var btActualizar    : Button
    private lateinit var btEliminar      : Button
    private lateinit var btReturn        : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_evento)

        tvAnfitri       = findViewById(R.id.tvAnfitri)
        etNombreEven    = findViewById(R.id.etNombreEven)
        etDeta          = findViewById(R.id.etDeta)
        etDirecc        = findViewById(R.id.etDirecc)
        etCup           = findViewById(R.id.etCup)
        etFec           = findViewById(R.id.etFec)
        etHora          = findViewById(R.id.etHora)
        rvAsistencia    = findViewById(R.id.rvAsistencia)
        btActualizar    = findViewById(R.id.btActualizar)
        btEliminar      = findViewById(R.id.btEliminar)
        btReturn        = findViewById(R.id.btReturn)

        val anfitrion = intent.getStringExtra("Anfitrion")
        tvAnfitri.text = anfitrion
        etNombreEven.setText(intent.getStringExtra("NomReunion"))
        etDeta.setText(intent.getStringExtra("Descripcion"))
        etDirecc.setText(intent.getStringExtra("Localizacion"))
        etCup.setText(intent.getIntExtra("Cupo", 0).toString())
        tvAnfitri.setText(intent.getStringExtra("Anfitrion"))
        etFec.setText(intent.getStringExtra("Fecha"))
        etHora.setText(intent.getStringExtra("Hora"))

        btActualizar.setOnClickListener {
            Actualizar()
        }

        btEliminar.setOnClickListener {
            Eliminar()
        }

        btReturn.setOnClickListener {
            val int = Intent(this, PerfilActivity::class.java)
            startActivity(int)
            finish()
        }

        val nomReunion = intent.getStringExtra("NomReunion") ?: ""
        cargarAsistentes(nomReunion)

    }

    private fun cargarAsistentes(nomReunion: String) {
        val url = "http://192.168.12.33/te/TeAsistentes.php?nomreunion=$nomReunion"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val asistentes = response.getJSONArray("nombres").let { jsonArray ->
                    List(jsonArray.length()) { jsonArray.getString(it) }
                }
                rvAsistencia.adapter = AsistentesAdapter(asistentes)
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
                finish()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }


    private fun Eliminar() {
        val nomReunion = etNombreEven.text.toString()
        val jsonObject = JSONObject().apply{
            put("nomreunion", nomReunion)
        }
        val url = "http://192.168.12.33/te/TeEliminar.php"

        val request = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            Response.Listener { response ->
                println(response)
                val int = Intent(this, PerfilActivity::class.java)
                startActivity(int)
                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
                finish()
            }
        )
        println("JSON enviado: ${jsonObject.toString()}")
        Volley.newRequestQueue(this).add(request)
    }


    private fun Actualizar() {
        val nomReunion = etNombreEven.text.toString()
        val descripcion = etDeta.text.toString()
        val localizacion = etDirecc.text.toString()
        val cupo = etCup.text.toString().toIntOrNull() ?: 0
        val fecha = etFec.text.toString()
        val hora = etHora.text.toString()

        val jsonObject = JSONObject().apply {
            put("nomreunion", nomReunion)
            put("descripcion", descripcion)
            put("localizacion", localizacion)
            put("cupo", cupo)
            put("fecha", fecha)
            put("hora", hora)
        }
        val url = "http://192.168.12.33/te/TeActualizar.php"

        val request = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            Response.Listener {response ->
                println(response)
                val int = Intent(this, PerfilActivity::class.java)
                startActivity(int)
                Toast.makeText(this,
                    "Actualizado",Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener {error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
                finish()
            }
        )
        println("JSON enviado: ${jsonObject.toString()}")
        Volley.newRequestQueue(this).add(request)
    }
}