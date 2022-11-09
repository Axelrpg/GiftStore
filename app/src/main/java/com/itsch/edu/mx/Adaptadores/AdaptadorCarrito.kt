package com.itsch.edu.mx.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itsch.edu.mx.DataClass.Producto
import com.itsch.edu.mx.R

class AdaptadorCarrito(private val listaProductos: ArrayList<Producto>) :
    RecyclerView.Adapter<AdaptadorCarrito.ProductoViewHolder>() {

    var onProductoClick: ((Producto) -> Unit)? = null

    class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vistaImagen: ImageView = itemView.findViewById(R.id.vistaImagen)
        val vistaNombre: TextView = itemView.findViewById(R.id.tvVistaNombreProductoXML)
        val vistaPrecio: TextView = itemView.findViewById(R.id.tvVistaPrecioProductoXML)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_carrito, parent, false)

        return ProductoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.vistaImagen.setImageResource(producto.imagen)
        holder.vistaNombre.text = producto.nombreCorto
        holder.vistaPrecio.text = producto.precio.toString()

        holder.itemView.setOnClickListener {
            onProductoClick?.invoke(producto)
        }
    }

    override fun getItemCount(): Int {
        return listaProductos.size
    }
}