package Adapter

import Model.Evento
import Model.User
import android.content.Context
import android.content.Intent
import android.util.Log
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

class MiAdapterCrear (var eventos : ArrayList<String>, var  context: Context) : RecyclerView.Adapter<MiAdapterCrear.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
        var titulo:String=""
        lateinit var eve: Evento
        lateinit var asist: User
        var email:String=""
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

        fun bind(pers: String, context: Context, pos: Int, miAdaptadorRecycler: MiAdapterCrear){
            usEvento.text = pers


            if (pos == MiAdapterCrear.seleccionado) {
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
                    if (pos == MiAdapterCrear.seleccionado){
                        MiAdapterCrear.seleccionado = -1
                    }
                    else {
                        MiAdapterCrear.seleccionado = pos
                        titulo=pers
                        var intentmais: Intent = Intent(context, Maps::class.java)
                        intentmais.putExtra("marcarPosicion",pers)
                        intentmais.putExtra("user", email)
                        ContextCompat.startActivity(context, intentmais, null)
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })

            itemView.setOnLongClickListener(
                View.OnLongClickListener
                {
                    miAdaptadorRecycler.eventos.removeAt(pos)
                    //borrar el evento---------------------------------------------------------------------------------------
                    db.collection("eventos").document(pers)
                        .delete()
                        .addOnSuccessListener { Log.d("Consegido", "Documento borrado.!") }
                        .addOnFailureListener { e -> Log.w("No Conseguido", "Error al borrar el documento.", e) }
                    miAdaptadorRecycler.notifyDataSetChanged()
                    return@OnLongClickListener true
                })


        }

        fun asistir(pers: String){
            db.collection("eventos").document(pers).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                //txtDNI.setText(it.get("DNI") as String?)
                var asistentes = it.get("asistentes") as ArrayList<User>
                //asistentes.add(asist)

                //Se guardarán en modo HashMap (clave, valor).
                var user = hashMapOf(
                    "asistentes" to asistentes
                )

                db.collection("eventos")//añade o sebreescribe
                    .document(titulo) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        //Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }

                //Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }

        }
    }
}