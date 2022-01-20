package Adaptador

import Modelo.Users
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nuevoproyecfirebase.R

class MiAdaptadorRecycler (var usuarios : ArrayList<Users>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_todos,parent,false))
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val usDNI = view.findViewById(R.id.txtDN) as TextView
        val usNombre = view.findViewById(R.id.txtNom) as TextView
        val usApel = view.findViewById(R.id.txtApellido) as TextView
        val usProv = view.findViewById(R.id.txtProvid) as TextView
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: Users, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){
            usDNI.text = pers.DNI
            usNombre.text=pers.Nombre
            usApel.text=pers.Apellidos
            usProv.text=pers.provider

            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == MiAdaptadorRecycler.seleccionado){
                    MiAdaptadorRecycler.seleccionado = -1
                }
                else {
                    MiAdaptadorRecycler.seleccionado = pos
                }
                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

            })
        }
    }
}