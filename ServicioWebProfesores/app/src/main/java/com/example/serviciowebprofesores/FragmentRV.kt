package com.example.serviciowebprofesores

import Api.ServiceBuilder
import Api.UserAPI
import Auxiliar.MiAdaptador
import Modelo.Aula
import Modelo.Equipo
import Modelo.Profesor
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentRV.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentRV : Fragment(),FragmentVer.OnFragmentInteractionListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var miRecyclerView:RecyclerView
    //private var ventanaactual:FragmentRV=this
    private var opciones=ArrayList<Any>()
    private var idAula:Int = 0

    //private lateinit var opciones:MutableList<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_r_v, container, false)
    }

    //Se añade manualmente.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        miRecyclerView =view.findViewById(R.id.rvTodo) as RecyclerView
        miRecyclerView.setHasFixedSize(true)
        miRecyclerView.layoutManager = LinearLayoutManager(context)
        contexto2= requireContext()
    }

    //Se obliga su implementación al incluir la interfaz.
    override fun onFragmentInteraction(texto: String) {
        if(texto=="Profe"){
            Profesores()
        }
        if(texto=="Aula"){
            if(role=="P"){
                AulaDNI(Dni)
            }
            if(role=="J"){
                Aulas()
            }
            if(role=="E"){
                AulaDNI(Dni)
            }
        }
        if(texto=="Equipo"){
            if(role=="P"){
                AulaDNI2(Dni)
                //EquiposAula(idAula)
            }
            if(role=="J"){
                Equipos()
            }
            if(role=="E"){
                AulaDNI2(Dni)
                //EquiposAula(idAula)
            }
        }

        //var miAdapter = context?.let { MiAdaptador(opciones, it) }
        var miAdapter = MiAdaptador(opciones,contexto2)
        miRecyclerView.adapter = miAdapter

    }

    companion object {
        var role=""
        var Dni=""
        lateinit var contexto2:Context
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentRV.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentRV().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun Profesores(){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getProfesores();
        //Call<MutableList<Profesor>>
        //call.enqueue(object : Callback<Profesor> {
        call.enqueue(object : Callback<MutableList<Profesor>> {
            override fun onResponse(call: Call<MutableList<Profesor>>, response: Response<MutableList<Profesor>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        opciones=post as ArrayList<Any>
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Profesor>>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun Aulas(){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getAulas();
        call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        opciones=post as ArrayList<Any>
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun Equipos(){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getEquipos();
        call.enqueue(object : Callback<MutableList<Equipo>> {
            override fun onResponse(call: Call<MutableList<Equipo>>, response: Response<MutableList<Equipo>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        //var nom: TextView =findViewById(R.id.txtNomCabez)
                        //nom.setText(post.Nombre.toString())
                        opciones=post as ArrayList<Any>
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Equipo>>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    //permite ver las aulas de un profesor en concreto
    fun AulaDNI(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula2(idBusc);
        //si solo es un aula
        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        opciones.add(post)
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        //si es mas de una
        /*call.enqueue(object : Callback<MutableList<Aula>> {
            override fun onResponse(call: Call<MutableList<Aula>>, response: Response<MutableList<Aula>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        opciones=post as ArrayList<Any>
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Aula>>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })*/
    }

    fun AulaDNI2(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnAula2(idBusc);

        call.enqueue(object : Callback<Aula> {
            override fun onResponse(call: Call<Aula>, response: Response<Aula>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        idAula= post.IdAula!!
                        EquiposAula(post.IdAula!!)
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<Aula>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun EquiposAula(idBusc:Int){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getEquiposAula(idBusc);
        call.enqueue(object : Callback<MutableList<Equipo>> {
            override fun onResponse(call: Call<MutableList<Equipo>>, response: Response<MutableList<Equipo>>) {
                val post = response.body()
                if (post != null) {
                    if (response.isSuccessful){
                        opciones=post as ArrayList<Any>
                    }
                    else {
                        Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Equipo>>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}