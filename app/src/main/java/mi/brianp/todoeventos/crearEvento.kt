package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class crearEvento : AppCompatActivity() {
    private lateinit var auth           : FirebaseAuth
    private lateinit var etNomEvento    : EditText
    private lateinit var etDescripcion  : EditText
    private lateinit var etDireccion    : EditText
    private lateinit var spTipoEvento   : Spinner
    private lateinit var etCupo         : EditText
    private lateinit var cvFecha        : CalendarView
    private lateinit var spHora         : Spinner
    private lateinit var btCrear        : Button
    private lateinit var requestQueue   : RequestQueue
    private var fechaSeleccionada : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_evento)

        auth = FirebaseAuth.getInstance()
        requestQueue = Volley.newRequestQueue(this)

        etNomEvento     = findViewById(R.id.etNombreEvento)
        etDescripcion   = findViewById(R.id.etDescripcion)
        etDireccion     = findViewById(R.id.etDireccion)
        spTipoEvento    = findViewById(R.id.spTipoEvento)
        etCupo          = findViewById(R.id.etCupo)
        cvFecha         = findViewById(R.id.cvFecha)
        spHora          = findViewById(R.id.spHora)
        btCrear         = findViewById(R.id.btCrear)

        val user = auth.currentUser
        val calendario = Calendar.getInstance()
        fechaSeleccionada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendario.time)
        cvFecha.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val mesAjustado = month + 1
            fechaSeleccionada = String.format(Locale.getDefault(), "%d-%02d-%02d", year, mesAjustado, dayOfMonth)
        }


        btCrear.setOnClickListener {
            val nomEven = etNomEvento.text.toString().trim()
            val descrip = etDescripcion.text.toString().trim()
            val dire = etDireccion.text.toString().trim()
            val tipo = spTipoEvento.selectedItem.toString()
            val cupoString = etCupo.text.toString().trim()
            val cupo = if (cupoString.isNotEmpty()) cupoString.toInt() else 0

            if (nomEven.isNotEmpty() && descrip.isNotEmpty()
                && dire.isNotEmpty() && tipo.isNotEmpty() && cupo > 0) {
                creaEvento(user!!.uid, fechaSeleccionada)
            } else {
                Toast.makeText(this,"Por favor, llena todos los campos.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        val btEventos : Button = findViewById(R.id.btEventos)
        btEventos.setOnClickListener {
            val eve = Intent(this, Eventos::class.java)
            startActivity(eve)
            finish()
        }

        val btPerfil : Button = findViewById(R.id.btPerfil)
        btPerfil.setOnClickListener {
            val per = Intent(this, PerfilActivity::class.java)
            startActivity(per)
            finish()
        }
    }

    private fun creaEvento(uid: String, fecha : String) {
        val nomEven = etNomEvento.text.toString().trim()
        val descrip = etDescripcion.text.toString().trim()
        val dire = etDireccion.text.toString().trim()

        val tipoSelec = spTipoEvento.selectedItem.toString()
        val tipo = when (tipoSelec){
            "Fiesta" -> "P"
            "Peliculas" -> "Mo"
            "Reunion" -> "Me"
            else -> ""
        }

        val cupoString = etCupo.text.toString().trim()
        val cupo = if (cupoString.isNotEmpty()) cupoString.toInt() else 0
        val horaSeleccionada = spHora.selectedItem.toString()

        val url = "http://192.168.12.33/te/TeCrear.php"

        val jsonObject = JSONObject()
        jsonObject.put("uid",uid)
        jsonObject.put("nomreunion",nomEven)
        jsonObject.put("descripcion",descrip)
        jsonObject.put("localizacion",dire)
        jsonObject.put("tipo",tipo)
        jsonObject.put("ocupado", 0)
        jsonObject.put("cupo",cupo)
        jsonObject.put("fecha",fecha)
        jsonObject.put("hora", horaSeleccionada)

        val request = JsonObjectRequest(
            Request.Method.PUT, url, jsonObject,
            Response.Listener {response ->
                println(response)
                val int = Intent(this, Eventos::class.java)
                startActivity(int)
                Toast.makeText(this,
                    "Evento Creado",Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener { error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
            })
        println("JSON enviado: ${jsonObject.toString()}")
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
