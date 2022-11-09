package com.itsch.edu.mx.DataClass

data class Compra(
    var correo:String?,
    var total:String?,
    var numeroProductos:String?,
    var producto: ArrayList<Producto>
)
