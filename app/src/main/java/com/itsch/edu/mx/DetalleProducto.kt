package com.itsch.edu.mx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.itsch.edu.mx.DataClass.Producto
import com.itsch.edu.mx.DataClass.Usuario

class DetalleProducto : AppCompatActivity() {

    private lateinit var btnRegresar: Button
    private lateinit var btnCarrito: Button
    private lateinit var btnComprar: Button

    private lateinit var nombreCliente: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        btnRegresar = findViewById(R.id.btnRegresarXML)
        btnCarrito = findViewById(R.id.btnCarritoXML)
        btnComprar = findViewById(R.id.btnComprarXML)

        nombreCliente = findViewById(R.id.tvNombreCliente)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")
        val producto = intent.getParcelableExtra<Producto>("producto")
        val misCompras = intent.getParcelableArrayListExtra<Producto>("misCompras")

        nombreCliente.text = "Cliente: ${usuario?.nombre} ${usuario?.apaterno}" +
                "${usuario?.amaterno}"

        if (producto != null) {
            val vistaImagen: ImageView = findViewById(R.id.ivDetalleImagenXML)
            val vistaNombre: TextView = findViewById(R.id.tvDetalleNombreXML)
            val vistaDescripcion: TextView = findViewById(R.id.tvDetalleDescripcionXML)
            val vistaPrecio: TextView = findViewById(R.id.tvDetallePrecioXML)

            vistaImagen.setImageResource(producto.imagen)
            vistaNombre.text = producto.nombre
            vistaDescripcion.text = producto.descripcion
            vistaPrecio.text = "$${producto.precio.toString()}"
        }

        btnRegresar.setOnClickListener {
            val intent = Intent(this, Tienda::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        btnCarrito.setOnClickListener {
            val intent = Intent(this, Carrito::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        btnComprar.setOnClickListener {
            val intent = Intent(this, Tienda::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("compraProducto", producto)
            startActivity(intent)
        }
    }
}