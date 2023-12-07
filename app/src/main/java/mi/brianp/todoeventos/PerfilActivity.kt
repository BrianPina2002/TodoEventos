package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONObject

class PerfilActivity : AppCompatActivity() {
    private lateinit var adapter: PerfilAdapter
    private lateinit var rvMisEventos : RecyclerView
    private lateinit var tvNombre : TextView
    private lateinit var tvApellido : TextView
    private lateinit var tvCorreo : TextView
    private lateinit var tvEventoPreferido : TextView
    private lateinit var tvEstado : TextView
    private lateinit var tvCiudad : TextView

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        rvMisEventos = findViewById(R.id.rvMisEventos)
        tvNombre = findViewById(R.id.tvNombre)
        tvApellido = findViewById(R.id.tvApellido)
        tvCorreo = findViewById(R.id.tvCorreo)
        tvEventoPreferido = findViewById(R.id.tvEventoPreferido)
        tvEstado = findViewById(R.id.tvEstado)
        tvCiudad = findViewById(R.id.tvCiudad)

        adapter = PerfilAdapter(this)
        rvMisEventos.adapter = adapter
        val llenarLayoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false)
        rvMisEventos.layoutManager = llenarLayoutManager

        val btEventos : Button = findViewById(R.id.btEventos)
        val btcrearEvento : Button = findViewById(R.id.btcrearEventos)

        btEventos.setOnClickListener {
            val eve = Intent(this, Eventos::class.java)
            startActivity(eve)
            finish()
        }

        btcrearEvento.setOnClickListener {
            val creaE = Intent(this, crearEvento::class.java)
            startActivity(creaE)
            finish()
        }

        val currentUser = auth.currentUser
        currentUser?.let {
            val uid = it.uid
            cargarMisEventos(uid)
            cargarPerfil(uid)
        }

    }

    private fun cargarPerfil(uid: String) {
        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.12.33/te/TePerfil.php?uid=$uid"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            Response.Listener { response ->
                if (response.getInt("response") == 200) {
                    val usuarioJson = response.getJSONObject("content")
                    tvNombre.text = usuarioJson.getString("nombre")
                    tvApellido.text = usuarioJson.getString("apellido")
                    tvCorreo.text = usuarioJson.getString("correo")
                    tvEstado.text = usuarioJson.getString("estado")
                    tvCiudad.text = usuarioJson.getString("ciudad")
                    tvEventoPreferido.text = when (
                        usuarioJson.getString("evento")){
                        "P" -> "Fiestas"
                        "Mo" -> "Peliculas"
                        "Me" -> "Reuniones"
                        else -> "Desconocido"
                    }
                }

            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
            }
        )
        requestQueue.add(request)
    }

    private fun cargarMisEventos(uid: String) {
        val requestQueue = Volley.newRequestQueue(this)
        val url = "http://192.168.12.33/te/TeMisEventos.php?uid=$uid"

        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            Response.Listener { response ->
                if (response.getInt("response") == 200){
                    val content : JSONArray = response.getJSONArray("content")
                    val datos : ArrayList<Even> = ArrayList()
                    for (i in 0 until content.length()) {
                        val eventoJson : JSONObject = content.getJSONObject(i)
                        val icono = when (eventoJson.getString("tipo")) {
                            "P" -> R.drawable.fiesta_sign
                            "Mo" -> R.drawable.pelicula_sign
                            "Me" -> R.drawable.reunion_sign
                            else -> R.drawable.ic_launcher_foreground
                        }
                        val evento = Even().apply {
                            this.icono = icono
                            NomReunion = eventoJson.getString("nomreunion")
                            Descripcion = eventoJson.getString("descripcion")
                            Localizacion = eventoJson.getString("localizacion")
                            Cupo = eventoJson.getInt("cupo")
                            Fecha = eventoJson.getString("fecha")
                            Anfitrion = eventoJson.getString("nombreAnfi")
                        }
                        datos.add(evento)
                    }
                    adapter.llenar(datos)
                }
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
            }
        )
        requestQueue.add(request)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menuLogOut -> {
                logOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val int = Intent(this, MainActivity::class.java)
        startActivity(int)
        finish()
    }
}