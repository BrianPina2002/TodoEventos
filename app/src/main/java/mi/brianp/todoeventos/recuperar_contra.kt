package mi.brianp.todoeventos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class recuperar_contra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_contra)

        val editTextRecovery : EditText = findViewById(R.id.editTextRecoveryEmail)
        val buttonContinue : Button = findViewById(R.id.buttonContinue)
        val buttonReturn : Button = findViewById(R.id.buttonReturn)

        buttonContinue.setOnClickListener{
            val email = editTextRecovery.text.toString().trim()
            if (email.isNotEmpty()) {
                sendReset(email)
            }else{
                Toast.makeText(this, "Ingresa un correo electronico", Toast.LENGTH_LONG).show()
            }
        }

        buttonReturn.setOnClickListener{
            val ret = Intent(this, MainActivity::class.java)
            startActivity(ret)
            finish()
        }

    }

    private fun sendReset(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de restablecimiento enviado", Toast.LENGTH_LONG).show()
                    val ret = Intent(this, MainActivity::class.java)
                    startActivity(ret)
                    finish()
                }else {
                    Toast.makeText(this, "Error al enviar correo: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }

    }
}