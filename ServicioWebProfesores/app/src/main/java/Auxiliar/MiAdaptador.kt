package Auxiliar

import Api.ServiceBuilder
import Api.UserAPI
import Modelo.Aula
import Modelo.Equipo
import Modelo.Profesor
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.serviciowebprofesores.NewAula
import com.example.serviciowebprofesores.NewProfesor
import com.example.serviciowebprofesores.R
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MiAdaptador (var objeto : ArrayList<Any>, var  context: Context) : RecyclerView.Adapter<MiAdaptador.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
        var IddelaTarea:Int=0
        var nombreTarea:String=""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = objeto.get(position)
        holder.bind(item, context, position, this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_recycler,parent,false))
    }
    override fun getItemCount(): Int {
        return objeto.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val texto1 = view.findViewById(R.id.txtId) as TextView
        val texto2 = view.findViewById(R.id.txtProfe) as TextView
        val texto3 = view.findViewById(R.id.txtDes) as TextView
        val texto4 = view.findViewById(R.id.txt4) as TextView
        val avatar = view.findViewById(R.id.imgRecycler) as ImageView


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(tars: Any, context: Context, pos: Int, miAdaptador: MiAdaptador){
            if(tars is Profesor){
                tars as Profesor
                texto1.text=tars.DNIProfesor.toString()
                texto2.text=tars.Nombre.toString()
                texto3.text=tars.Apellidos.toString()
            }
            if(tars is Aula){
                tars as Aula
                texto1.text=tars.IdAula.toString()
                texto2.text=tars.DNI.toString()
                texto3.text=tars.Descripcion.toString()
                with(avatar){
                    setImageResource(R.drawable.aula)
                }
            }
            if(tars is Equipo){tars as Equipo}

            if (pos == MiAdaptador.seleccionado) {
                with(texto1) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                    //this.paint.isStrikeThruText=true
                }
            }
            else {
                with(texto1) {
                    this.setTextColor(resources.getColor(R.color.black))
                    //this.paint.isStrikeThruText=false
                }
            }

            itemView.setOnClickListener(
                View.OnClickListener
            {
                if (pos == MiAdaptador.seleccionado){
                    MiAdaptador.seleccionado = -1
                }
                else {
                    MiAdaptador.seleccionado = pos
                    if(tars is Profesor){
                        var intentMain:Intent = Intent(context, NewProfesor::class.java)
                        intentMain.putExtra("opcion","modificar")
                        intentMain.putExtra("modificar",tars)
                        startActivity(context,intentMain,null)
                    }
                    if(tars is Aula){
                        var intentMain:Intent = Intent(context, NewAula::class.java)
                        intentMain.putExtra("opcion","modificar")
                        intentMain.putExtra("modificar",tars)
                        startActivity(context,intentMain,null)
                    }
                    if(tars is Equipo){}

                }
                miAdaptador.notifyDataSetChanged()

            })
            itemView.setOnLongClickListener(
                View.OnLongClickListener
            {

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        if(tars is Profesor){}
                        if(tars is Aula){
                            var a:Aula=miAdaptador.objeto[pos] as Aula
                            Log.e("objeto",a.IdAula.toString())
                            borrarPorIDAula(a.IdAula.toString().toInt())
                            miAdaptador.objeto.removeAt(pos)
                        }
                        if(tars is Equipo){}
                    })
                dialogo.setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                dialogo.setTitle("¿Borrar Elemento?")
                dialogo.setMessage("¿Deseas eliminar este elemento?")
                dialogo.show()

                miAdaptador.notifyDataSetChanged()
                return@OnLongClickListener true
            })
        }

        fun borrarPorIDAula(id:Int){
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.borrarAula(id)
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.e("Fernando",response.message())
                    Log.e ("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando","Registro eliminado con éxito.")
                        //Toast.makeText(this@MainActivity, "Registro eliminado con éxito", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                        //Toast.makeText(this@MainActivity, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                    }
                    if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando","Registro eliminado con éxito.")
                        //Toast.makeText(this@MainActivity, "Registro eliminado con éxito", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando","Algo ha fallado en la conexión.")
                    //Toast.makeText(this@MainActivity, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG).show()
                }
            })
        }

    }

}