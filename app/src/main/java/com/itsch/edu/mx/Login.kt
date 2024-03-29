package com.itsch.edu.mx

import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.itsch.edu.mx.DataClass.Usuario
import kotlin.math.log

class Login : AppCompatActivity() {
    private lateinit var correo: TextInputLayout
    private lateinit var password: TextInputLayout

    private lateinit var btnIngresar: MaterialButton
    private lateinit var btnNoEstoyRegistrado: MaterialButton

    private lateinit var btnGoogle: MaterialButton

    private lateinit var btnFacebook: MaterialButton

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    var firebaseAuth = FirebaseAuth.getInstance()

    private val callbackManager = CallbackManager.Factory.create()

    private lateinit var usuario: Usuario

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        //Acceso a la base de datos CloudFirestore
        val db = Firebase.firestore

        correo = findViewById(R.id.txtCorreoXML)
        password = findViewById(R.id.txtPassXML)

        btnIngresar = findViewById(R.id.btnIngresarXML)
        btnNoEstoyRegistrado = findViewById(R.id.btnRegistrarXML)

        btnGoogle = findViewById(R.id.btnGoogleXML)

        btnFacebook = findViewById(R.id.btnFacebookXML)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        firebaseAuth = FirebaseAuth.getInstance()

        btnGoogle.setOnClickListener {
            signInGoogle()
        }

        btnFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onCancel() {
                        TODO("Not yet implemented")
                    }

                    override fun onError(error: FacebookException) {
                        Log.d(TAG, error.toString())
                        showAlert()
                    }

                    override fun onSuccess(result: LoginResult) {
                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        showHome(it.result?.user?.email ?: "")
                                    }
                                }
                        }
                    }

                })
        }

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
                        db.collection("usuarios")
                            .whereEqualTo("correo", correo.editText?.text.toString())
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    usuario = Usuario(
                                        "${document.data.get("correo")}",
                                        "${document.data.get("nombre")}",
                                        "${document.data.get("apaterno")}",
                                        "${document.data.get("amaterno")}",
                                    )
                                }
                                val intent = Intent(this, Tienda::class.java)
                                intent.putExtra("usuario", usuario)
                                startActivity(intent)
                            }
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

    private fun showHome(email: String) {
        val intent = Intent(this, Tienda::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                SavedPreference.setEmail(this, account.email.toString())
                SavedPreference.setUsername(this, account.displayName.toString())
                val intent = Intent(this, Tienda::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(Intent(this, Tienda::class.java))
            finish()
        }
    }

}


