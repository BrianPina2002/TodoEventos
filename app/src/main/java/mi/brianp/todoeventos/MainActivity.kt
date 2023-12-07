package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val emailEditText       : EditText = findViewById(R.id.editTextEmail)
        val passwordEditText    : EditText = findViewById(R.id.editTextPassword)
        val loginButton         : Button = findViewById(R.id.btAcceder)
        val registerTextView    : TextView = findViewById(R.id.tvRegistrar)
        val forgotTextView      : TextView = findViewById(R.id.tvOlvide)

        loginButton.setOnClickListener{
            val email       = emailEditText.text.toString()
            val password    = passwordEditText.text.toString()
            signIn(email, password)
        }

        onStart()

        registerTextView.setOnClickListener{
            val reg = Intent(this, RegisterActivity::class.java)
            startActivity(reg)
            finish()
        }

        forgotTextView.setOnClickListener{
            val forg = Intent(this, recuperar_contra::class.java)
            startActivity(forg)
            finish()
        }
    }

    private fun signIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val intent = Intent(this, Eventos::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    task.exception?.message?.let {
                        showMessage(it)
                    }
                }
            }
        }
    }

    private fun showMessage(it: String) {
        Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            val intent = Intent(this, Eventos::class.java)
            startActivity(intent)
            finish()
        }
    }
}