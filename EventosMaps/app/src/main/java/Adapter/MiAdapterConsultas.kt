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
import com.example.eventosmaps.Maps
import com.example.eventosmaps.R
import com.google.firebase.firestore.FirebaseFirestore

class MiAdapterConsultas (var eventos : ArrayList<String>, var  context: Context) : RecyclerView.Adapter<MiAdapterConsultas.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
        var titulo:String=""
        lateinit var eve:Evento
        lateinit var asist: User
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = eventos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_recicler,parent,false))
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance()
        val usEvento = view.findViewById(R.id.txtEvent) as TextView
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: String, context: Context, pos: Int, miAdaptadorRecycler: MiAdapterConsultas){
            usEvento.text = pers


            if (pos == MiAdapterConsultas.seleccionado) {
                with(usEvento) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(usEvento) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == MiAdapterConsultas.seleccionado){
                        MiAdapterConsultas.seleccionado = -1
                    }
                    else {
                        MiAdapterConsultas.seleccionado = pos
                        titulo=pers
                        var intentmais: Intent = Intent(context, Asistentes::class.java)
                        intentmais.putExtra("evento",pers)
                        ContextCompat.startActivity(context, intentmais, null)
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })
        }
    }
}