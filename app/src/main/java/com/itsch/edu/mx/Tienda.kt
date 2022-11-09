package com.itsch.edu.mx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.itsch.edu.mx.Adaptadores.AdaptadorArtesania
import com.itsch.edu.mx.Adaptadores.AdaptadorBolsos
import com.itsch.edu.mx.Adaptadores.AdaptadorPerfumes
import com.itsch.edu.mx.Adaptadores.AdaptadorPulseras
import com.itsch.edu.mx.DataClass.Producto
import com.itsch.edu.mx.DataClass.Usuario

enum class ProviderType {
    BASIC,
    GOOGLE,
    FACEBOOK
}

class Tienda : AppCompatActivity() {

    private lateinit var btnSalir: Button
    private lateinit var btnCarrito: Button

    private lateinit var nombreCliente: TextView

    private lateinit var rvArtesanias: RecyclerView
    private lateinit var rvBolsos: RecyclerView
    private lateinit var rvPerfumes: RecyclerView
    private lateinit var rvPulseras: RecyclerView

    private lateinit var listaArtesanias: ArrayList<Producto>
    private lateinit var listaBolsos: ArrayList<Producto>
    private lateinit var listaPerfumes: ArrayList<Producto>
    private lateinit var listaPulseras: ArrayList<Producto>

    private lateinit var adaptadorArtesania: AdaptadorArtesania
    private lateinit var adaptadorBolsos: AdaptadorBolsos
    private lateinit var adaptadorPerfumes: AdaptadorPerfumes
    private lateinit var adaptadorPulseras: AdaptadorPulseras

    private lateinit var misCompras: ArrayList<Producto>

    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tienda)

        btnSalir = findViewById(R.id.btnSalir)
        btnCarrito = findViewById(R.id.btnCarrito)

        nombreCliente = findViewById(R.id.tvNombreCliente)

        val usuario = intent.getParcelableExtra<Usuario>("usuario")
        val comprarProducto = intent.getParcelableExtra<Producto>("compraProducto")
        val listaCompras = intent.getParcelableArrayListExtra<Producto>("misCompras")

        nombreCliente.text = "${usuario?.nombre} ${usuario?.apaterno} ${usuario?.amaterno}"

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        rvArtesanias = findViewById(R.id.rvArtesanias)
        rvArtesanias.setHasFixedSize(true)

        rvBolsos = findViewById(R.id.rvBolsos)
        rvBolsos.setHasFixedSize(true)

        rvPerfumes = findViewById(R.id.rvPerfumes)
        rvPerfumes.setHasFixedSize(true)

        rvPulseras = findViewById(R.id.rvPulseras)
        rvPulseras.setHasFixedSize(true)

        //RecyclerView horizontal
        rvArtesanias.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvBolsos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvPerfumes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        rvPulseras.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        listaArtesanias = ArrayList()
        listaBolsos = ArrayList()
        listaPerfumes = ArrayList()
        listaPulseras = ArrayList()

        misCompras = ArrayList()

        if (comprarProducto != null) {
            if (listaCompras != null) {
                misCompras = listaCompras
            }
            misCompras.add(comprarProducto)
        }

        listaArtesanias.add(
            Producto(
                R.drawable.artesania01,
                "Xico Hoja",
                "Xico-Talavera Hoja",
                3309.0,
                "Xico es un personaje que busca generar un cambio positivo a través del arte y la cultura.",
                "Artesanía"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania02,
                "Xico Rojo",
                "Xico-Alebrije Rojo",
                2959.0,
                "Xico es un personaje que busca generar un cambio positivo a través del arte y la cultura.",
                "Artesanía"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania03,
                "Alebrije Liebre",
                "Alebrije Liebre Chico Azul/Rojo",
                1180.0,
                "Los alebrijes surgen de un sueño del artesano don Pedro Linares: \"Me morí y anduve por montañas, barrancas y en un lugar de ésos, donde había un bosque, de ahí salían los alebrijes\".",
                "Artesanía"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania04,
                "Alebrije Camaleón",
                "Alebrije Camaleón Mediano",
                5189.0,
                "Los alebrijes surgen de un sueño del artesano don Pedro Linares: \"Me morí y anduve por montañas, barrancas y en un lugar de ésos, donde había un bosque, de ahí salían los alebrijes\".",
                "Artesanía"
            )
        )

        listaArtesanias.add(
            Producto(
                R.drawable.artesania05,
                "Catrina Rosa",
                "Catrina Arrecifes Rosa",
                4540.0,
                "Pieza artesanal con la figura de una catrina cuyo elegante vestir plasma las tradiciones de la cultura mexicana.",
                "Artesanía"
            )
        )

        listaPerfumes.add(
            Producto(
                R.drawable.perfume01,
                "DIOR Savauge",
                "Xico-Talavera Hoja",
                4390.0,
                "Lorem ipsum",
                "Perfumes"
            )
        )
        listaPerfumes.add(
            Producto(
                R.drawable.perfume02,
                "Tom Ford Ombré",
                "Eau de parfum Tom Ford Ombré Leather unisex",
                4440.00,
                "La libertad viene de dentro, el corazón del desierto del oeste envuelto en cuero. El polvo en el viento, la piel en la piel, Ombré Leather se revela como un paisaje en las capas",
                "Perfumes"
            )
        )
        listaPerfumes.add(
            Producto(
                R.drawable.perfume03,
                "Dolce&Gabbana The One",
                "Fragancia Dolce&Gabbana The One para hombre",
                2900.00,
                "The One for Men es el nuevo aroma del icónico mundo de The One, que ofrece una experiencia de fragancia más profunda para los expertos en perfumes. Esta fragancia de gran intensidad, masculina y magnética es una declaración de carisma y sofisticación",
                "Perfumes"
            )
        )
        listaPerfumes.add(
            Producto(
                R.drawable.perfume04,
                "Gucci Guilty",
                "Eau de parfum Gucci Guilty para hombre",
                3600.00,
                "El universo #ForeverGuilty continúa buscando una noción liberada de pasión con Gucci Guilty Parfum For Him. El aroma reinterpreta el clásico Gucci Guilty For Him en un movimiento que eleva el jugo a niveles más altos de intensidad. Para aquellos que buscan una declaración de amor libre de las reglas y definiciones de la sociedad, el aroma está diseñado para actuar como emblema de la conexión entre amantes excéntricos y de ideas afines. Gucci Guilty Parfum For Him es una fragancia amaderada aromática que magnifica la verdadera esencia de la línea Gucci Guilty For Him",
                "Perfumes"
            )
        )
        listaPerfumes.add(
            Producto(
                R.drawable.perfume05,
                "Salvatore Ferragamo Uomo",
                "Eau de toilette Salvatore Ferragamo Uomo para hombre",
                2140.00,
                "La mezcla de cedro, madera y un toque de comino logran crear un perfume ideal para llevarlo de día o de noche ",
                "Perfumes"
            )
        )

        listaBolsos.add(
            Producto(
                R.drawable.bolso01,
                "Bolso Guess",
                "Bolso Guess mini satchel con animal skin",
                1992.00,
                "Bolso mini Guess tipo satchel Katey Croc; elaborado en material sintético, textura tipo animal skin de cocodrilo, doble asa, correa ajustable para hombro, compartimento principal con cierre y logo a contraste.",
                "Bolso"
            )
        )

        listaBolsos.add(
            Producto(
                R.drawable.bolso02,
                "Bolso tote",
                "Bolso tote Lacoste",
                2190.00,
                "Bolso Lacoste tipo tote elaborado en material sintético color negro, textura semicorrugada, doble asa rígida, compartimento principal con cierre y logotipo de la marca bordado al frente.\n" +
                        "Confeccionadas en piel ante\n" +
                        "Planta de piel ovina\n" +
                        "Tacón: 9 cm\n" +
                        "Incluye plantilla AIRFIT®: compuesta de espuma y látex para ofrecer el mayor confort y la máxima amortiguación.\n" +
                        "CARE FOR PLANET: piel curtida Join Life. Esta piel se ha producido en tenerías certificadas Leather Working Group Oro o Plata. LWG es un protocolo que audita el proceso de curtición en el uso de energía y agua, la gestión de residuos y el uso de químicos pre-aprobados en los procesos de curtición.\n" +
                        "Para poder tramitar la devolución o cambio de este artículo, es indispensable conservar el embalaje original.\n",
                "Mujer"
            )
        )

        listaBolsos.add(
            Producto(
                R.drawable.bolso03,
                "Bolso Bandolera",
                "Bolso Bandolera Piel Detalle Costuras",
                2795.00,
                "Confeccionado en piel bovina\n" +
                        "Un compartimento principal\n" +
                        "Correa de hombro ajustable\n" +
                        "Cierre mediante imán oculto\n" +
                        "Forro interior en algodón\n",
                "Mujer"
            )
        )

        listaBolsos.add(
            Producto(
                R.drawable.bolso04,
                "Bolso",
                "Bolso Trapecio Mini Piel Pouch Y Bandolera",
                2595.00,
                "Se puede utilizar como pouch de mano o como bolso tipo bandolera\n" +
                        "Confeccionado en piel ovina en acabado acharolado \"roto\"\n" +
                        "Un compartimento principal\n" +
                        "Asa extraíble regulable\n" +
                        "Forro interior en algodón\n" +
                        "CARE FOR PLANET: 100% piel curtida Join Life. Esta piel se ha producido en tenerías certificadas Leather Working Group Oro o Plata. LWG es un protocolo que audita el proceso de curtición en el uso de energía y agua, la gestión de residuos y el uso de químicos pre-aprobados en los procesos de curtición.\n",
                "Bolso"
            )
        )

        listaBolsos.add(
            Producto(
                R.drawable.bolso05,
                "Bolso Sobre",
                "Bolso Sobre Bandolera Piel",
                2295.00,
                "Confeccionado en piel bovina\n" +
                        "Un compartimento principal\n" +
                        "Correa de hombro\n" +
                        "CARE FOR PLANET: 100% piel curtida Join Life. Esta piel se ha producido en tenerías certificadas Leather Working Group Oro o Plata. LWG es un protocolo que audita el proceso de curtición en el uso de energía y agua, la gestión de residuos y el uso de químicos pre-aprobados en los procesos de curtición.\n",
                "Bolso"
            )
        )

        listaPulseras.add(
            Producto(
                R.drawable.pulsera01,
                "Pulsera Amalfi",
                "Amalfi Colore Acero Inoxidable",
                795.00,
                "Pulsera Ajustable a cualquier muñeca\n" +
                        "Cilindro Acero Inoxidable\n" +
                        "Cordón 100% Algodón Tibetano\n",
                "Pulseras"
            )
        )

        listaPulseras.add(
            Producto(
                R.drawable.pulsera02,
                "Pulsera Bonassola",
                "Bonassola 6 mm Blue / Yellow",
                1495.00,
                "Broche con Imán de Acero Inoxidable\n" +
                        "Cuerda Alto Grado Marítimo\n",
                "Pulseras"
            )
        )

        listaPulseras.add(
            Producto(
                R.drawable.pulsera03,
                "Pulsera Amalfi",
                "Amalfi Cotton 44",
                795.00,
                "Cilindro Acero Inoxidable\n" +
                        "Cordón 100% Algodón\n",
                "Pulseras"
            )
        )

        listaPulseras.add(
            Producto(
                R.drawable.pulsera04,
                "Pulsera Piel",
                "Pulsera Piel Doble Trenza",
                749.00,
                "Pulsera trenza tubular\n" +
                        "Pulsera de triple tira\n" +
                        "Confeccionada en piel bovina\n" +
                        "Cierre metálico\n",
                "Pulseras"
            )
        )

        listaPulseras.add(
            Producto(
                R.drawable.pulsera05,
                "Pulsera Trenzada",
                "Pulsera Trenzada Piel Dos Colores",
                749.00,
                "Pulsera con doble vuelta\n" +
                        "Confeccionada en piel bovina\n" +
                        "Cierre metálico\n",
                "Pulseras"
            )
        )

        adaptadorArtesania = AdaptadorArtesania(listaArtesanias)
        rvArtesanias.adapter = adaptadorArtesania

        adaptadorBolsos = AdaptadorBolsos(listaBolsos)
        rvBolsos.adapter = adaptadorBolsos

        adaptadorPerfumes = AdaptadorPerfumes(listaPerfumes)
        rvPerfumes.adapter = adaptadorPerfumes

        adaptadorPulseras = AdaptadorPulseras(listaPulseras)
        rvPulseras.adapter = adaptadorPulseras

        adaptadorArtesania.onProductoClick = {
            val intent = Intent(this, DetalleProducto::class.java)
            intent.putExtra("producto", it)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        adaptadorPerfumes.onProductoClick = {
            val intent = Intent(this, DetalleProducto::class.java)
            intent.putExtra("producto", it)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        adaptadorBolsos.onProductoClick = {
            val intent = Intent(this, DetalleProducto::class.java)
            intent.putExtra("producto", it)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        adaptadorPulseras.onProductoClick = {
            val intent = Intent(this, DetalleProducto::class.java)
            intent.putExtra("producto", it)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }

        btnSalir.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)

            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }

        }

        btnCarrito.setOnClickListener {
            val intent = Intent(this, Carrito::class.java)
            intent.putExtra("usuario", usuario)
            intent.putExtra("misCompras", misCompras)
            startActivity(intent)
        }
    }
}