package com.itsch.edu.mx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.itsch.edu.mx.Adaptadores.AdaptadorArtesania
import com.itsch.edu.mx.DataClass.Producto
import com.itsch.edu.mx.DataClass.Usuario

class Tienda : AppCompatActivity() {

    private lateinit var btnSalir: Button
    private lateinit var btnCarrito: Button

    private lateinit var nombreCliente: TextView

    private lateinit var rvArtesanias: RecyclerView

    private lateinit var listaArtesanias: ArrayList<Producto>

    private lateinit var adaptadorArtesania: AdaptadorArtesania

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)

        btnSalir = findViewById(R.id.btnSalir)
        btnCarrito = findViewById(R.id.btnCarrito)

        nombreCliente = findViewById(R.id.tvNombreCliente)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")

        nombreCliente.text = "${usuario?.nombre} ${usuario?.apaterno}" +
                "${usuario?.amaterno}"

        rvArtesanias = findViewById(R.id.rvArtesanias)
        rvArtesanias.setHasFixedSize(true)

        //RecyclerView horizontal
        rvArtesanias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        listaArtesanias = ArrayList()

        listaArtesanias.add(
            Producto(
                R.drawable.artesania01,
                "Xico Hoja",
                "Xico-Talavera Hoja",
                3309.0,
                "Lorem ipsum",
                "Artesania"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania02,
                "Xico Rojo",
                "Xico-Piel de Alebrije Rojo",
                2959.0,
                "Lorem ipsum",
                "Artesania"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania03,
                "Alebrije Liebre",
                "Alebrije Liebre Chico Azul/Rojo",
                1180.0,
                "Lorem ipsum",
                "Artesania"
            )
        )

        adaptadorArtesania = AdaptadorArtesania(listaArtesanias)
        rvArtesanias.adapter = adaptadorArtesania

        btnSalir.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btnCarrito.setOnClickListener { }
    }
}