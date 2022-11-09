package com.itsch.edu.mx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsch.edu.mx.Adaptadores.AdaptadorCarrito
import com.itsch.edu.mx.DataClass.Producto
import com.itsch.edu.mx.DataClass.Usuario

class Carrito : AppCompatActivity() {

    private lateinit var rvCarrito: RecyclerView

    private lateinit var adaptadorCarrito: AdaptadorCarrito

    private lateinit var nombreCliente: TextView
    private lateinit var totalCompra: TextView

    private lateinit var btnRegresar: Button
    private lateinit var btnFinalizar: Button

    private lateinit var listaCompras: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        rvCarrito = findViewById(R.id.rvCarrito)
        rvCarrito.setHasFixedSize(true)

        btnRegresar = findViewById(R.id.btnRegresar)
        btnFinalizar = findViewById(R.id.btnFinalizarXML)

        nombreCliente = findViewById(R.id.tvNombreCliente)
        totalCompra = findViewById(R.id.tvTotal)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")
        val misCompras = intent.getParcelableArrayListExtra<Producto>("misCompras")

        val numeroProductos = misCompras?.size

        

        nombreCliente.text="Cliente: ${usuario?.nombre} ${usuario?.apaterno} ${usuario?.amaterno}"
    }
}