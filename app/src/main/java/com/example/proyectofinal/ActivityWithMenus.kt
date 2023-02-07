package com.example.proyectofinal

import android.annotation.SuppressLint
import android.content.Intent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

open class ActivityWithMenus : AppCompatActivity(){
    companion object{
        var actividadActual = 1;
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        for (i in 0 until 2){
            if (i == actividadActual) menu.getItem(i).isEnabled = false
            else menu.getItem(i).isEnabled = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.listadoJugadores -> {
                val intent = Intent(this, ListadoActivity::class.java)
                actividadActual = 1;
                startActivity(intent)
                true
            }
            R.id.anadirJugador -> {
                val intent= Intent(this, NuevoJugador::class.java)
                actividadActual=2;
                startActivity(intent)
                true
            }

            R.id.cerrarSesion ->{
                FirebaseAuth.getInstance().signOut()
                // Volvemos al mainActivity
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}