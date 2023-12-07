package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONObject

class Eventos : AppCompatActivity() {
    private lateinit var adapter    : EventosAdapter
    private lateinit var cbFiesta   : CheckBox
    private lateinit var cbReunion  : CheckBox
    private lateinit var cbPelicula : CheckBox
    private lateinit var cbTodo     : CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        val btCrearEvento : Button = findViewById(R.id.btCrearEvento)
        val btPerfil : Button = findViewById(R.id.btPerfil)

        btCrearEvento.setOnClickListener {
            val crea = Intent(this, crearEvento::class.java)
            startActivity(crea)
            finish()
        }

        btPerfil.setOnClickListener {
            val perfil = Intent(this, PerfilActivity::class.java)
            startActivity(perfil)
            finish()
        }

        val rvEventos : RecyclerView = findViewById(R.id.rvEventos)
        adapter = EventosAdapter(this)

        rvEventos.adapter = adapter

        val llenarLayoutManager : LayoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL, false)
        rvEventos.layoutManager = llenarLayoutManager

        cbFiesta    = findViewById(R.id.cbFiesta)
        cbReunion   = findViewById(R.id.cbReunion)
        cbPelicula  = findViewById(R.id.cbPelicula)
        cbTodo      = findViewById(R.id.cbTodo)

        val checkBoxListener = CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cbFiesta -> {
                        cbReunion.isChecked = false
                        cbPelicula.isChecked = false
                        cbTodo.isChecked = false
                    }

                    R.id.cbReunion -> {
                        cbFiesta.isChecked = false
                        cbPelicula.isChecked = false
                        cbTodo.isChecked = false
                    }

                    R.id.cbPelicula -> {
                        cbFiesta.isChecked = false
                        cbReunion.isChecked = false
                        cbTodo.isChecked = false
                    }

                    R.id.cbTodo -> {
                        cbFiesta.isChecked = false
                        cbReunion.isChecked = false
                        cbPelicula.isChecked = false
                    }
                }
            }
            consultarEventos()
        }

        cbFiesta.setOnCheckedChangeListener(checkBoxListener)
        cbReunion.setOnCheckedChangeListener(checkBoxListener)
        cbPelicula.setOnCheckedChangeListener(checkBoxListener)
        cbTodo.setOnCheckedChangeListener(checkBoxListener)
    }

    fun consultarEventos(){
        val requestQueue = Volley.newRequestQueue(this)
        val tipoEvento = when {
            cbFiesta.isChecked -> "P"
            cbReunion.isChecked -> "Me"
            cbPelicula.isChecked -> "Mo"
            else -> ""
        }
        val url="http://192.168.12.33/te/TeConsultar.php?tipo=$tipoEvento"
        val request = JsonObjectRequest(
            Request.Method.GET,
            url, null,
            Response.Listener { response ->
                procesarDatos(response)
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
            }
        )
        requestQueue.add( request )
    }

    private fun procesarDatos(response: JSONObject?) {
        if (response != null && response.getInt("response") == 200) {
            val content : JSONArray = response.getJSONArray("content")
            val datos : ArrayList<Even> = ArrayList()

            for (i in 0 until content.length()){
                val eventoJson : JSONObject = content.getJSONObject( i )

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
                    Hora = eventoJson.getString("hora")
                    Anfitrion = eventoJson.getString("nombreAnfi")
                }
                datos.add(evento)
            }
            adapter.llenar(datos)
        }
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

    override fun onStart() {
        super.onStart()
        consultarEventos()
    }
}