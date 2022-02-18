package MiAdaptador

import Model.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.R
import com.google.firebase.firestore.FirebaseFirestore

class AdaptadorAsistencia (var usuarios : ArrayList<User>, var  context: Context) : RecyclerView.Adapter<AdaptadorAsistencia.ViewHolder>() {
    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: AdaptadorAsistencia.ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(R.layout.item_asistencia, parent,false))
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance()
        var croles = ArrayList<Int>()

        val usApel = view.findViewById(R.id.lblApelUsu) as TextView
        val usNom = view.findViewById(R.id.lblNomUsu) as TextView
        val usUbi = view.findViewById(R.id.lblUbiUsu) as TextView
        val usHora = view.findViewById(R.id.lblHoraUsu) as TextView
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: User, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorAsistencia){
            usApel.text = pers.Apellidos
            usNom.text=pers.Nombre
            usUbi.text=pers.Ubicacion
            usHora.text=pers.Hora

            if (pos == AdaptadorAsistencia.seleccionado) {
                with(usApel) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(usNom) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(usUbi) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
                with(usHora) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(usApel) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(usNom) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(usUbi) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                with(usHora) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == AdaptadorAsistencia.seleccionado){
                        AdaptadorAsistencia.seleccionado = -1
                    }
                    else {
                        AdaptadorAsistencia.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })
        }
    }

}