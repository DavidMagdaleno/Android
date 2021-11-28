package Adaptador


import Modelo.Tarea
import Auxiliar.Conexion
import Modelo.Notas
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import androidx.recyclerview.widget.RecyclerView
import com.example.minotas.NotaTarea
//import com.bumptech.glide.Glide
import com.example.minotas.R
import java.nio.file.Files.delete

class MiAdaptadorTarea (var tarea : ArrayList<Tarea>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorTarea.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
        var IddelaTarea:Int=0
        var nombreTarea:String=""
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tarea.get(position)
        holder.bind(item, context, position, this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.notatarea,parent,false))
    }
    override fun getItemCount(): Int {
        return tarea.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomTarea = view.findViewById(R.id.txtCard) as TextView
        val avatar = view.findViewById(R.id.imgRecycler) as ImageView


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(tars: Tarea, context: Context, pos: Int, miAdaptadorTarea: MiAdaptadorTarea){
            nomTarea.text = tars.getTarea()

            if(!tars.getImagen().equals("")){
                var img= tars.getImagen().substring(36)
                var path=tars.getImagen().subSequence(0,36)
                with(avatar){
                    this.setImageBitmap(BitmapFactory.decodeFile(path.toString()+ "/"+img+".png"))
                }
            }
            if (pos == MiAdaptadorTarea.seleccionado) {
                with(nomTarea) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                    this.paint.isStrikeThruText=true
                }
            }
            else {
                with(nomTarea) {
                    this.setTextColor(resources.getColor(R.color.black))
                    this.paint.isStrikeThruText=false
                }
            }

            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == MiAdaptadorTarea.seleccionado){
                    MiAdaptadorTarea.seleccionado = -1
                }
                else {
                    MiAdaptadorTarea.seleccionado = pos
                    MiAdaptadorTarea.IddelaTarea=tars.getIdTarea()
                    MiAdaptadorTarea.nombreTarea= nomTarea.text.toString()
                }
                miAdaptadorTarea.notifyDataSetChanged()

            })
            itemView.setOnLongClickListener(View.OnLongClickListener
            {
                miAdaptadorTarea.tarea.removeAt(pos)
                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        Conexion.delTarea(context as AppCompatActivity,tars.getId(),tars.getIdTarea())
                    })
                dialogo.setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                dialogo.setTitle("¿Borrar Elemento?")
                dialogo.setMessage("¿Deseas eliminar este elemento?")
                dialogo.show()

                miAdaptadorTarea.notifyDataSetChanged()
                return@OnLongClickListener true
            })
        }

    }
}