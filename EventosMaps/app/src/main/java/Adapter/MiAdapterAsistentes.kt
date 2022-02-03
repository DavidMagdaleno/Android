package Adapter

import Model.Evento
import Model.User
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eventosmaps.Asistentes
import com.example.eventosmaps.R
import com.google.firebase.firestore.FirebaseFirestore

class MiAdapterAsistentes (var eventos : ArrayList<User>, var  context: Context) : RecyclerView.Adapter<MiAdapterAsistentes.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_asistente,parent,false))
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance()
        val nombre = view.findViewById(R.id.lblNom) as TextView
        val apel = view.findViewById(R.id.lblApel) as TextView
        //val asis = view.findViewById(R.id.lblAsis) as TextView
        val ubi = view.findViewById(R.id.lblUbi) as TextView

        fun bind(pers: User, context: Context, pos: Int, miAdaptadorRecycler: MiAdapterAsistentes){
            nombre.text = "Nombre: "+pers.Nombre
            apel.text="Apellidos: "+pers.Apellidos
            //asis.text="Asistencia: "+pers.Asistencia
            ubi.text="Sitio: "+pers.Ubicacion


            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdapterAsistentes.seleccionado){
                        MiAdapterAsistentes.seleccionado = -1
                    }
                    else {
                        MiAdapterAsistentes.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })
        }
    }
}