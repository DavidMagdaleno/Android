package Adaptador


import Auxiliar.Fichero
import Modelo.Tarea
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.minotas.R

class MiAdaptadorTarea (var tarea : ArrayList<Tarea>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorTarea.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tarea.get(position)
        holder.bind(item, context, position, this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.notatarea,parent,false))
    }
    override fun getItemCount(): Int {
        return tarea.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomTarea = view.findViewById(R.id.txtCard) as TextView
        val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(tars: Tarea, context: Context, pos: Int, miAdaptadorTarea: MiAdaptadorTarea){
            nomTarea.text = tars.getTarea()

            /*if (sistemaPersona.text.toString().equals("Mac")){
                Log.e("imagen",pers.getImagen())
                val uri = "@drawable/" +pers.getImagen()
                Log.e("imagen2",uri)
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
                Log.e("imagen3",avatar.drawable.current.toString())

            }
            else {
                //Glide.with(context).load(pers.getImagen()).into(avatar)
            }*/

            if (pos == MiAdaptadorTarea.seleccionado) {
                with(nomTarea) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(nomTarea) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }

            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == MiAdaptadorTarea.seleccionado){
                    MiAdaptadorTarea.seleccionado = -1
                }
                else {
                    MiAdaptadorTarea.seleccionado = pos
                }
                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorTarea.notifyDataSetChanged()

            })
        }
    }

}