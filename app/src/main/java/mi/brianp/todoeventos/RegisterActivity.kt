package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.json.JSONObject
import java.lang.reflect.Method

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth           : FirebaseAuth
    private lateinit var buttonReg      : Button
    private lateinit var rbMeet         : RadioButton
    private lateinit var rbMovie        : RadioButton
    private lateinit var rbParty        : RadioButton
    private lateinit var editTextName   : EditText
    private lateinit var editTextLast   : EditText
    private lateinit var editTextEmail  : EditText
    private lateinit var etPassword     : EditText
    private lateinit var buttonRegister : Button
    private lateinit var spState        : Spinner
    private lateinit var etCity         : EditText
    private lateinit var requestQueue : RequestQueue


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        requestQueue = Volley.newRequestQueue(this)

        editTextName    = findViewById(R.id.editTextName)
        editTextLast    = findViewById(R.id.editTextLast)
        editTextEmail   = findViewById(R.id.editTextEmail)
        etPassword      = findViewById(R.id.etPassword)
        buttonRegister  = findViewById(R.id.buttonRegister)
        spState         = findViewById(R.id.spState)
        etCity          = findViewById(R.id.etCity)
        rbParty         = findViewById(R.id.rbParty)
        rbMovie         = findViewById(R.id.rbMovie)
        rbMeet          = findViewById(R.id.rbMeet)
        buttonReg       = findViewById(R.id.buttonReg)

        buttonRegister.setOnClickListener{
            val email = editTextEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()) {
                registerUser(email, password)
            }else{
                Toast.makeText(this,
                    "Por favor ingresa todos los campos requeridos.",
                    Toast.LENGTH_LONG).show()
            }
        }

        buttonReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val users = auth.currentUser
                    extra(users!!.uid)
                    Toast.makeText(baseContext,
                        "Registro Exitoso",
                        Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(baseContext,
                        "Registro fallido: ${task.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun extra(uid: String) {
        val nombre = editTextName.text.toString()
        val apellido = editTextLast.text.toString()
        val correo = editTextEmail.text.toString()
        val password = etPassword.text.toString()
        val estado = spState.selectedItem.toString()
        val ciudad = etCity.text.toString()
        val eventosInteres = getEventosDeInteres()

        val url = "http://192.168.12.33/te/TeRegister.php"

        val jsonObject = JSONObject()
        jsonObject.put("uid", uid)
        jsonObject.put("nombre", nombre)
        jsonObject.put("apellido", apellido)
        jsonObject.put("correo", correo)
        jsonObject.put("contrasena", password)
        jsonObject.put("estado", estado)
        jsonObject.put("ciudad", ciudad)
        jsonObject.put("evento", eventosInteres)

        val request = JsonObjectRequest(Request.Method.PUT,
            url,
            jsonObject,
            Response.Listener { Response ->
                println(Response)
                val int = Intent(this, MainActivity::class.java)
                startActivity(int)
                Toast.makeText(this,
                    "Guardado", Toast.LENGTH_SHORT).show()
                finish()
            },
            Response.ErrorListener {error ->
                println("Error en la solicitud: $error")
                error.printStackTrace()
            })
        val jsonString = jsonObject.toString()
        println("JSON enviado: $jsonString")
        requestQueue.add(request)
    }

    private fun getEventosDeInteres(): String {
        var rb : String = ""
        if (rbParty.isChecked){
            rb = "P"
        }else if (rbMovie.isChecked){
            rb = "Mo"
        }else if (rbMeet.isChecked){
            rb = "Me"
        }
        return rb
    }


}