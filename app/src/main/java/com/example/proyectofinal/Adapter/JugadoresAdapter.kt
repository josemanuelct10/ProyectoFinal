package com.example.proyectofinal.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.Jugadores
import com.example.proyectofinal.R

class JugadoresAdapter(private val listaJugadores:List<Jugadores>) : RecyclerView.Adapter<JugadoresViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadoresViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        return JugadoresViewHolder(LayoutInflater.inflate(R.layout.item_jugador,parent,false))
    }

    override fun onBindViewHolder(holder: JugadoresViewHolder, position: Int) {
        val item = listaJugadores[position]
        holder.render(item)

    }

    override fun getItemCount(): Int {
        return listaJugadores.size
    }
}