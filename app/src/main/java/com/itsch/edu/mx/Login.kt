package com.itsch.edu.mx

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    private lateinit var correo: TextInputLayout
    private lateinit var password: TextInputLayout

    private lateinit var btnIngresar: MaterialButton
    private lateinit var btnNoEstoyRegistrado: MaterialButton

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        correo = findViewById(R.id.txtCorreoXML)
        password = findViewById(R.id.txtPassXML)

        btnIngresar = findViewById(R.id.btnIngresarXML)
        btnNoEstoyRegistrado = findViewById(R.id.btnRegistrarXML)

        btnNoEstoyRegistrado.setOnClickListener {
            val intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            if (correo.editText?.text.toString().isNotEmpty() &&
                password.editText?.text.toString().isNotEmpty()
            ) {
                auth.signInWithEmailAndPassword(
                    correo.editText?.text.toString(),
                    password.editText?.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, Tienda::class.java)
                        startActivity(intent)
                    } else {
                        showAlert()
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("¡Se ha producido un error en la autenticacion del usuario!")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}