package com.example.proyectofinalfct

import Model.RegistroL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.proyectofinalfct.databinding.ActivityRegistroLaboralBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegistroLaboral : AppCompatActivity() {
    lateinit var binding: ActivityRegistroLaboralBinding

    var dni:String=""
    var nombre:String=""
    var ape:String=""
    var dire:String=""
    var nac:String=""
    var f:String=""
    private var nveces:Int=0
    var rhoras = ArrayList<RegistroL>()

    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
    val sdf2 = SimpleDateFormat("HH:mm:ss", Locale("es", "ES"))
    val currentdate = sdf.format(Date())
    val currenthour = sdf2.format(Date())


    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroLaboralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()


        //val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
        //val sdf2 = SimpleDateFormat("HH:mm:ss", Locale("es", "ES"))
        //val currentdate = sdf.format(Date())
        //val currenthour = sdf2.format(Date())
        //val fechaactual = sdf.parse(currentdate)

        binding.txtFecha.text=currentdate.toString()
        binding.txtHora.text=currenthour.toString()

        binding.btnRegistro.setOnClickListener {
            RegistroOnline()
        }
    }



    fun RegistroOnline(){
        val bundle:Bundle? = intent.extras
        val email = bundle?.getString("email").toString()
        db.collection("usuarios").document(email).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él.
            dni=(it.get("DNI").toString())
            nombre=(it.get("Nombre").toString())
            ape=(it.get("Apellidos").toString())
            dire=(it.get("Direccion").toString())
            nac=(it.get("FechaNac").toString())
            if (it.get("Foto").toString()!=""){f=it.get("Foto").toString()}
            rhoras=it.get("Registro") as ArrayList<RegistroL>
            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()

            //--------------------------------------------------------comprobar esta parte para luego restar horas------------------------------
            var contiene:Boolean=false
            if (rhoras.isNotEmpty()){
                for (i in 0..rhoras.size){
                    Log.e("Prueba",rhoras[i].HoraFin)
                    if (rhoras[i].HoraFin.equals("")){
                        if (rhoras[i].Fecha.equals(currentdate.toString())){
                            if (rhoras[i].HoraIni.isNotEmpty()){
                                rhoras[i].HoraFin=currenthour.toString()
                            }
                            contiene=true
                        }
                    }
                }
            }
            if (!contiene){
                rhoras.add(RegistroL(currentdate.toString(),currenthour.toString(),""))
            }

        }.addOnFailureListener{
            Toast.makeText(this, "Algo ha ido mal al recuperar",Toast.LENGTH_SHORT).show()
        }


        //---------------------------------------------------------Realiza esto antes que lo anterior------------------------------------------------------
        //Se guardarán en modo HashMap (clave, valor).
        var user = hashMapOf(
            "DNI" to dni,
            "Nombre" to nombre,
            "Apellidos" to ape,
            "Direccion" to dire,
            "FechaNac" to nac,
            "Foto" to f,
            "Registro" to rhoras
        )

        db.collection("usuarios")//añade o sebreescribe
            .document(email) //Será la clave del documento.
            .set(user).addOnSuccessListener {
                Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
            }

    }

}