package com.example.proyectofinal.Adapter

import android.os.Bundle
import android.view.AbsSavedState
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinal.Jugadores
import com.example.proyectofinal.R
import com.example.proyectofinal.databinding.ItemJugadorBinding

class JugadoresViewHolder (view : View):RecyclerView.ViewHolder(view){
    val binding = ItemJugadorBinding.bind(view)

    fun render(jugadorModel : Jugadores){
        binding.tvNombre.text = jugadorModel.nombre
        binding.tvPosicion.text = jugadorModel.posicion
        binding.tvEquipo.text = jugadorModel.equipo
        binding.tvEdad.text = jugadorModel.edad
        Glide.with(binding.imageView3.context).load(jugadorModel.imagen).into(binding.imageView3)
        }

    }


