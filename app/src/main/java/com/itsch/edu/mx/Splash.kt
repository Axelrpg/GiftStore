package com.itsch.edu.mx

import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.util.Pair
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Splash : AppCompatActivity() {

    private lateinit var logo: ImageView

    private lateinit var animacion: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        printHashKey()

        logo = findViewById(R.id.ivLogoXML)
        animacion = AnimationUtils.loadAnimation(this, R.anim.anim_splash)

        logo.startAnimation(animacion)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, Login::class.java)
                val transicion = ActivityOptions.makeSceneTransitionAnimation(
                    this,
                    Pair(logo, "logo_transicionXML")
                )
                startActivity(intent, transicion.toBundle())
            }, 2000
        )
    }

    fun printHashKey() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }
}

//4tO8PiscrYQGzLDTBbROqwhaqNk=