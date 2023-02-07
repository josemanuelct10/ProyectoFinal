package com.example.proyectofinal

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.proyectofinal.databinding.ActivityNuevoJugadorBinding
import com.example.proyectofinal.databinding.ActivityRegistroBinding
import com.google.android.gms.common.data.DataHolder
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class NuevoJugador : ActivityWithMenus(){
    lateinit var imagen: ImageButton
    lateinit var storage: FirebaseStorage
    lateinit var binding: ActivityNuevoJugadorBinding

    // Instancia de la base de datos
    val db = FirebaseFirestore.getInstance()


    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        // Devuelve la uri de la imagen seleccionada
        uri ->
        if(uri!=null){
            // Imagen seleccionada
            imagen.setImageURI(uri)
        }
        else{
            // Toast en caso de que no se ha seleccinada la imagen
            Toast.makeText(this, "Ha habido un error al seleccionar la imagen.", Toast.LENGTH_SHORT).show()

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoJugadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagen = binding.iJugador

        val db = FirebaseFirestore.getInstance()
        storage = Firebase.storage

        binding.bGuardar.setOnClickListener {

            // alerta de dialogo guardando o cancelar
            val builder = AlertDialog.Builder(this)
            builder.setMessage("¿Deseas guardar el jugador?")

            // Si se ha pulsado guardar
            builder.setPositiveButton("Guardar"){ dialog, which ->
                // Subimos la imagen llamando al metodo
                subirImagen()
                // Si ningun campo esta vacío
                if (binding.etJugador.text.isNotEmpty() && binding.etEquipo.text.isNotEmpty()
                    && binding.etPosicion.text.isNotEmpty() && binding.etEdad.text.isNotEmpty()){
                    db.collection("Jugadores")
                        .add(mapOf(
                            "edad" to binding.etEdad.text.toString(),
                            "equipo" to binding.etEquipo.text.toString(),
                            "nombre" to binding.etJugador.text.toString(),
                            "posicion" to binding.etPosicion.text.toString(),
                            "imagen" to ""

                        ))
                        .addOnSuccessListener { documento ->
                            Toast.makeText(this, "Jugador añadido con id: ${documento.id}", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, ListadoActivity::class.java)
                            startActivity(intent)
                            //Log.d(TAG, "Nuevo jugador añadido con id: ${documento.id}")
                        }
                       .addOnFailureListener{
                           Toast.makeText(this, "ERROR al añadir jugador", Toast.LENGTH_SHORT).show()


                        }
               } else{Toast.makeText(this, "Algún campo esta vacío", Toast.LENGTH_SHORT).show()}
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->


         }

        val dialog = builder.create()
        dialog.show()

        }

        // Cuando pulsemos sobre el imageButton, llamaremos al launcher para lanzarlo
        binding.iJugador.setOnClickListener{
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

    }

    // Metodo para subir la imagen a firestore y añadir al campo imagen de cada jugador el link de la imagen
    private fun subirImagen(){
        val storageRef = storage.reference
        val rutaImagen = storageRef.child("Imagenes/imagen/ " + binding.etJugador.text + ".jpeg")

        //binding.iJugador.isDrawingCacheEnabled = true
        val bitmap = (binding.iJugador.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
        val data = baos.toByteArray()
        var uploadTask = rutaImagen.putBytes(data)

        uploadTask.addOnFailureListener{
            Toast.makeText(this, "ERROR al guardar la foto", Toast.LENGTH_SHORT).show()


        }.addOnSuccessListener { taskSnapshot ->
            rutaImagen.downloadUrl.addOnSuccessListener {
                // Consultamos el jugador a través del nombre
                val query = db.collection("Jugadores")
                    .whereEqualTo("nombre", binding.etJugador.text.toString())
                    .limit(1)

                // Si la consulta ha salido bien se actualiza el jugador y se añade el link
                query.get()
                    .addOnSuccessListener { result ->
                        for (document in result){
                            document.reference.update("imagen",it.toString())
                        }
                    }
            }




        }

    }



}