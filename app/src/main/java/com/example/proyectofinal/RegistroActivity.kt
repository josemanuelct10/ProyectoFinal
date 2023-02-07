package com.example.proyectofinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinal.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class RegistroActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instancia de la base de datos
        val db = FirebaseFirestore.getInstance()

        binding.bRegistroR.setOnClickListener {
            // Comprobacion de campos vacios
                if(binding.etEmailRegistro.text.isNotEmpty() && binding.etPasswordRegistro.text.isNotEmpty() && binding.etNombre.text.isNotEmpty() && binding.etApellidos.text.isNotEmpty()){
                    // Creacion del usuario y contraseña
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.etEmailRegistro.text.toString(), binding.etPasswordRegistro.text.toString()
                    ).addOnCompleteListener{
                        if(it.isSuccessful){
                            // Añadimos los demas campos a la tabla de usuarios
                            db.collection("Usuarios").document(binding.etEmailRegistro.text.toString())
                                .set(mapOf(
                                    "nombre" to binding.etNombre.text.toString(),
                                    "apellidos" to binding.etApellidos.text.toString(),
                                    "email" to binding.etEmailRegistro.text.toString()
                                ))

                            // Cambiamos a la activity del recycler
                            val intent = Intent(this, ListadoActivity::class.java)
                            startActivity(intent)
                            // Toast en caso de error al registro
                        }else{Toast.makeText(this, "Error en el registro del nuevo usuario", Toast.LENGTH_SHORT).show()}
                    }

                }else{
                    // Toast en caso de que este algun campo vacío
                    Toast.makeText(this,"Algún campo está vacío", Toast.LENGTH_SHORT).show()
                }

        }



    }

}