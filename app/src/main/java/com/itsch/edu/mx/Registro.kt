package com.itsch.edu.mx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension
import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable
import com.google.android.material.textfield.TextInputLayout

class Registro : AppCompatActivity() {

    private lateinit var nombre: TextInputLayout
    private lateinit var apaterno: TextInputLayout
    private lateinit var amaterno: TextInputLayout
    private lateinit var correo: TextInputLayout
    private lateinit var pass: TextInputLayout

    private lateinit var btnConfirmar: Button
    private lateinit var btnEstoyRegistrado: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        nombre = findViewById(R.id.txtNombre)
        apaterno = findViewById(R.id.txtApaterno)
        amaterno = findViewById(R.id.txtAmaterno)
        correo = findViewById(R.id.txtCorreoXML)
        pass = findViewById(R.id.txtPassXML)

        btnConfirmar = findViewById(R.id.btnRegistrarXML)
        btnEstoyRegistrado = findViewById(R.id.btnIngresarXML)

        btnConfirmar.setOnClickListener {
            val intent = Intent(this, ConfirmarRegistro::class.java)
            intent.putExtra("nombre",nombre.editText?.text.toString())
            intent.putExtra("apaterno",apaterno.editText?.text.toString())
            intent.putExtra("amaterno",amaterno.editText?.text.toString())
            intent.putExtra("correo",correo.editText?.text.toString())
            intent.putExtra("password",pass.editText?.text.toString())
            startActivity(intent)
        }

        btnEstoyRegistrado.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}