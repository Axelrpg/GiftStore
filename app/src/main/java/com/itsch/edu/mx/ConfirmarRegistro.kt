package com.itsch.edu.mx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itsch.edu.mx.DataClass.Usuario

class ConfirmarRegistro : AppCompatActivity() {

    private lateinit var texto1: TextView
    private lateinit var texto2: TextView
    private lateinit var btnConfirmar: Button
    private lateinit var btnCorregir: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_registro)

        //Inicializar la autenticacion
        auth = Firebase.auth

        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        texto1 = findViewById(R.id.etiqueta1)
        texto2 = findViewById(R.id.etiqueta2)

        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnCorregir = findViewById(R.id.btnCorregir)

        val nombre = intent.getStringExtra("nombre")
        val apaterno = intent.getStringExtra("apaterno")
        val amaterno = intent.getStringExtra("amaterno")
        val correo = intent.getStringExtra("correo")
        val password = intent.getStringExtra("password")

        texto1.text = "$nombre $apaterno $amaterno"

        texto2.text = "Tus datos proporcionados son:\n" +
                "Correo electronico: $correo\n" +
                "Contraseña: $password\n"

        val usuario = Usuario(
            correo.toString(),
            nombre.toString(),
            apaterno.toString(),
            amaterno.toString(),
        )

        btnConfirmar.setOnClickListener {
            if (correo.toString().isNotEmpty() && password.toString().isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    correo.toString(), password.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, Login::class.java)
                        db.collection("usuarios")
                            .document(correo.toString())
                            .set(usuario)  //UID
                        startActivity(intent)
                    } else {
                        showAlert()
                    }
                }
            }
        }

        btnCorregir.setOnClickListener {
            finish()
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