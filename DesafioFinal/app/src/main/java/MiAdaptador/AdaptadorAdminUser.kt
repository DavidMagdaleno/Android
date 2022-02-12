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
import kotlinx.android.synthetic.main.activity_datos_usuario.*

class AdaptadorAdminUser (var usuarios : ArrayList<User>, var  context: Context) : RecyclerView.Adapter<AdaptadorAdminUser.ViewHolder>() {
    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_usu,parent,false))
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
        var croles = ArrayList<Int>()

        val usDNI = view.findViewById(R.id.lblDni) as TextView
        val usNYA = view.findViewById(R.id.lblNyA) as TextView
        val btAc=view.findViewById(R.id.btnAcep) as Button
        val btRz=view.findViewById(R.id.btnRechazar) as Button
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: User, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorAdminUser){
            usDNI.text = pers.DNI
            usNYA.text=pers.Apellidos+", "+pers.Nombre

            if (pers.Aceptado=="Aceptado"){
                btAc.isEnabled=false
                btAc.isVisible=false
                btRz.isEnabled=false
                btRz.isVisible=false

                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.green))
                }
                with(usNYA) {
                    this.setTextColor(resources.getColor(R.color.green))
                }
            }
            if (pers.Aceptado=="En Espera"){

                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.orange))
                }
                with(usNYA) {
                    this.setTextColor(resources.getColor(R.color.orange))
                }
            }
            if (pers.Aceptado=="Rechazado"){
                btAc.isEnabled=false
                btAc.isVisible=false
                btRz.isEnabled=false
                btRz.isVisible=false

                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.red))
                }
                with(usNYA) {
                    this.setTextColor(resources.getColor(R.color.red))
                }
            }

            btAc.setOnClickListener(){
                croles.add(1)
                var user = hashMapOf(
                    "DNI" to pers.DNI,
                    "Nombre" to pers.Nombre,
                    "Apellidos" to pers.Apellidos,
                    "Aceptado" to "Aceptado",
                    "email" to pers.email,
                    "Ubicacion" to pers.Ubicacion,
                    "Hora" to pers.Hora,
                    "roles" to croles
                )

                db.collection("usuarios")//añade o sebreescribe
                    .document(pers.email) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        //Toast.makeText(this@AdaptadorAdminUser, "Almacenado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this@AdaptadorAdminUser, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
            }

            btRz.setOnClickListener(){
                croles.add(1)
                var user = hashMapOf(
                    "DNI" to pers.DNI,
                    "Nombre" to pers.Nombre,
                    "Apellidos" to pers.Apellidos,
                    "Aceptado" to "Rechazado",
                    "email" to pers.email,
                    "roles" to croles
                )

                db.collection("usuarios")//añade o sebreescribe
                    .document(pers.email) //Será la clave del documento.
                    .set(user).addOnSuccessListener {
                        //Toast.makeText(this@AdaptadorAdminUser, "Almacenado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this@AdaptadorAdminUser, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                    }
            }

            if (pos == AdaptadorAdminUser.seleccionado) {
                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(usDNI) {
                    this.setTextColor(resources.getColor(R.color.black))
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