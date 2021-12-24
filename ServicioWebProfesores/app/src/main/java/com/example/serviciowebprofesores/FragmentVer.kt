package com.example.serviciowebprofesores

import Modelo.Profesor
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentVer.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentVer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //Definimos un listener para comunicarnos con otro framgmento aunq en este caso se quedara en el main.
    private var listener:OnFragmentInteractionListener?=null
    private lateinit var intentMain:Intent

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
        return inflater.inflate(R.layout.fragment_ver, container, false)
    }
    //hay que crearlo
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }
    //hay que crearlo
    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    //hay que crearlo
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val botonProfe: Button = view.findViewById<Button>(R.id.btnMostrarPro)
        val botonAula:Button = view.findViewById<Button>(R.id.btnVerAulas)
        val botonEquipo:Button = view.findViewById<Button>(R.id.btnVerEquipos)
        val botonAñadirProfe:Button = view.findViewById<Button>(R.id.btnAñadirProfe)
        val botonAñadirAula:Button = view.findViewById<Button>(R.id.btnAñadirAula)
        val botonAñadirEquipo:Button = view.findViewById<Button>(R.id.btnAñadirEquipo)
        botonProfe.setOnClickListener(){
            //val caja = view.findViewById<EditText>(R.id.edTexto)
            listener?.onFragmentInteraction("Profe")
        }
        botonAula.setOnClickListener(){
            //val caja = view.findViewById<EditText>(R.id.edTexto)
            listener?.onFragmentInteraction("Aula")
        }
        botonEquipo.setOnClickListener(){
            //val caja = view.findViewById<EditText>(R.id.edTexto)
            listener?.onFragmentInteraction("Equipo")
        }
        botonAñadirProfe.setOnClickListener(){
            intentMain = Intent(context,NewProfesor::class.java)
            intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }
        botonAñadirAula.setOnClickListener(){
            intentMain = Intent(context,NewAula::class.java)
            intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }
        botonAñadirEquipo.setOnClickListener(){
            //intentMain = Intent(context,NotaSimple::class.java)
            //startActivity(intentMain)
        }
        /*val image = view.findViewById<ImageView>(R.id.imgImagen)
        image.setOnClickListener(){
            val t = Toast.makeText(this.context,"Hola holita vecinito...",Toast.LENGTH_SHORT)
            t.setGravity(Gravity.CENTER,0,0)
            t.show()
        }*/
    }

    //Se crea manualmente.
    public interface OnFragmentInteractionListener {
        fun onFragmentInteraction(texto:String)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentVer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentVer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}