package com.example.proyectofinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinal.Adapter.JugadoresAdapter
import com.example.proyectofinal.databinding.ActivityListadoBinding
import com.google.firebase.firestore.FirebaseFirestore

class ListadoActivity : ActivityWithMenus() {

    // Creamos la instancia de la base de datos
    val db = FirebaseFirestore.getInstance()

    // Instanciamos el adapter
    private lateinit var adapterJugadores: JugadoresAdapter

    // Creamos el array de jugadores
    private lateinit var listaJugadores: ArrayList<Jugadores>

    // Creamos la variable binding
    private lateinit var binding : ActivityListadoBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Instanciamos el array de jugadores
        listaJugadores = ArrayList()

        // Asignamos el array al adapter
        adapterJugadores = JugadoresAdapter(listaJugadores)

        // Aqui consultamos todos los jugadores y los añadimos al array de jugadores
        db.collection("Jugadores").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val jugador = document.toObject(Jugadores::class.java)
                    jugador.nombre = document["nombre"].toString()
                    jugador.equipo = document["equipo"].toString()
                    jugador.posicion = document["posicion"].toString()
                    jugador.edad = document["edad"].toString()
                    jugador.imagen = document["imagen"].toString()
                    listaJugadores.add(jugador)

                    // Aqui asignamos al recyclerview el adapter
                    binding.listadoJugadores.adapter = adapterJugadores
                    binding.listadoJugadores.layoutManager = LinearLayoutManager(this)

                }
            }

    }


    }

