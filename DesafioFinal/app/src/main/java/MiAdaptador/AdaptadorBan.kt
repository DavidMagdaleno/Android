package MiAdaptador

import Model.User
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.R
import com.google.firebase.firestore.FirebaseFirestore

class AdaptadorBan (var usuarios : ArrayList<User>, var  context: Context) : RecyclerView.Adapter<AdaptadorBan.ViewHolder>() {
    companion object {
        var seleccionado:Int = -1
        var con=this
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_bann, parent,false))
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
        var croles = ArrayList<Int>()

        val usNYA = view.findViewById(R.id.txtUsuBan) as TextView
        val btAc=view.findViewById(R.id.btnBloq) as Button

        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: User, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorBan){
            //usNYA.text=pers.Apellidos+", "+pers.Nombre

            /*if (pers.Aceptado=="En Espera"){
                btAc.isEnabled=false
                btAc.isVisible=false
            }
            if (pers.Aceptado=="Rechazado"){
                btAc.isEnabled=false
                btAc.isVisible=false
            }*/
            if (pers.Aceptado=="Aceptado"){
                usNYA.text=pers.Apellidos+", "+pers.Nombre
                //btAc.isVisible=true
                //btAc.isEnabled=true
                with(usNYA) {
                    this.setTextColor(resources.getColor(R.color.green))
                }
            }

            btAc.setOnClickListener(){
                croles.add(1)
                var user = hashMapOf(
                    "DNI" to pers.DNI,
                    "Nombre" to pers.Nombre,
                    "Apellidos" to pers.Apellidos,
                    "Aceptado" to "Rechazado",
                    "email" to pers.email,
                    "Ubicacion" to pers.Ubicacion,
                    "Hora" to pers.Hora,
                    "roles" to croles
                )

                db.collection("usuarios")//añade o sebreescribe
                    .document(pers.email) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        Toast.makeText(context, "Usuario Bloqueado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this@AdaptadorAdminUser, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
            }

            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == AdaptadorAdminUser.seleccionado){
                        AdaptadorAdminUser.seleccionado = -1
                    }
                    else {
                        AdaptadorAdminUser.seleccionado = pos
                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })

        }
    }
}