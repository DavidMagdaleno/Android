package com.example.serviciowebprofesores

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentCabecera.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentCabecera : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        return inflater.inflate(R.layout.fragment_cabecera, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var nom:TextView=view.findViewById(R.id.txtNomCabez)
        var apel:TextView=view.findViewById(R.id.txtApelCabez)
        var dn:TextView=view.findViewById(R.id.txtDNI)
        var ro:TextView=view.findViewById(R.id.txtRol)
        nom.setText(nombre)
        apel.setText(apellidos)
        dn.setText(dni)
        ro.setText(rol)

        nom.setOnClickListener(){
            intentMain = Intent(context,MisDatos::class.java)
            //intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }
        apel.setOnClickListener(){
            intentMain = Intent(context,MisDatos::class.java)
            //intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }
        dn.setOnClickListener(){
            intentMain = Intent(context,MisDatos::class.java)
            //intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }
        ro.setOnClickListener(){
            intentMain = Intent(context,MisDatos::class.java)
            //intentMain.putExtra("opcion","nuevo")
            startActivity(intentMain)
        }


    }


    companion object {
        var nombre:String=""
        var apellidos:String=""
        var dni:String=""
        var rol:String=""
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentCabecera.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentCabecera().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}