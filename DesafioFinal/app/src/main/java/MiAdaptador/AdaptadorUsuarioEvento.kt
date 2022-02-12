package MiAdaptador

import Model.User
import android.content.Context
import android.util.Log
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

class AdaptadorUsuarioEvento (var usuarios : ArrayList<User>, var  context: Context) : RecyclerView.Adapter<AdaptadorUsuarioEvento.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
        var tituloevento:String=""
        //var usersdelevento:ArrayList<User> = ArrayList<User>()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_usuev,parent,false))
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
        var croles = ArrayList<Int>()

        val usNYA = view.findViewById(R.id.txtUsuEv) as TextView
        val btAc=view.findViewById(R.id.btnAgre) as Button
        val btRz=view.findViewById(R.id.btnSacar) as Button
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        //val avatar = view.findViewById(R.id.imgRecycler) as ImageView


        //sacar los usuarios de ese evento justo y meterlos en un array para comparar-------------------------------------------------------------

        fun bind(pers: User, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorUsuarioEvento){

            db.collection("eventos").document(tituloevento).get().addOnSuccessListener {
                //Si encuentra el documento será satisfactorio este listener y entraremos en él
                var usersdelevento=it.get("asistentes") as ArrayList<User>

                for (i in 0..usersdelevento.size-1){
                    var aux= usersdelevento[i] as Map<String,Any>

                    if (pers.Aceptado=="Aceptado" && aux.containsValue(pers.DNI)){
                        btAc.isEnabled=false
                        btRz.isEnabled=true

                        with(usNYA) {
                            this.setTextColor(resources.getColor(R.color.green))
                        }
                    }
                }

                usNYA.text=pers.Apellidos+", "+pers.Nombre

                btAc.setOnClickListener(){
                    usersdelevento.add(pers)
                    db.collection("eventos").document(tituloevento).get().addOnSuccessListener {
                        var p=it.get("Ubicacion").toString()
                        //var p=it.get("Ubicacion") as Map<String,String>
                        //var aux=p.get("ubicacion").toString()

                        var user = hashMapOf(
                            "Ubicacion" to p,
                            "asistentes" to usersdelevento,
                            "comentarios" to it.get("cometarios").toString(),
                            "fotos" to it.get("fotos").toString()
                        )
                        db.collection("eventos")//añade o sebreescribe
                            .document(tituloevento) //Será la clave del documento.
                            .set(user).addOnSuccessListener {
                                btAc.isEnabled=false
                                btRz.isEnabled=true
                                //Toast.makeText(Context, "Almacenado", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener{
                                //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                            }
                        //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                    }


                }

                btRz.setOnClickListener(){
                    for (i in 0..usersdelevento.size-1){
                        var aux= usersdelevento[i] as Map<String,Any>

                        if (pers.Aceptado=="Aceptado" && aux.containsValue(pers.DNI)){
                            usersdelevento.remove(usersdelevento[i])
                            btAc.isEnabled=true
                            btRz.isEnabled=false

                            with(usNYA) {
                                this.setTextColor(resources.getColor(R.color.red))
                            }
                        }
                    }
                    db.collection("eventos").document(tituloevento).get().addOnSuccessListener {
                        var p=it.get("Ubicacion").toString()
                        //var p=it.get("Ubicacion") as Map<String,String>
                        //var aux=p.get("ubicacion").toString()

                        var user = hashMapOf(
                            "Ubicacion" to p,
                            "asistentes" to usersdelevento,
                            "comentarios" to it.get("cometarios").toString(),
                            "fotos" to it.get("fotos").toString()
                        )
                        db.collection("eventos")//añade o sebreescribe
                            .document(tituloevento) //Será la clave del documento.
                            .set(user).addOnSuccessListener {
                                //Toast.makeText(Context, "Almacenado", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener{
                                //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                            }
                        //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                    }
                }

                if (pos == AdaptadorUsuarioEvento.seleccionado) {
                    with(usNYA) {
                        this.setTextColor(resources.getColor(R.color.purple_200))
                    }
                }
                else {
                    with(usNYA) {
                        this.setTextColor(resources.getColor(R.color.black))
                    }
                }
                itemView.setOnClickListener(
                    View.OnClickListener
                    {
                        if (pos == AdaptadorUsuarioEvento.seleccionado){
                            AdaptadorAdminUser.seleccionado = -1
                        }
                        else {
                            AdaptadorUsuarioEvento.seleccionado = pos
                        }
                        //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                        miAdaptadorRecycler.notifyDataSetChanged()

                    })

                //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
            }

        }
    }

    /*fun usuariosdelevento(){
        val db = FirebaseFirestore.getInstance()
        db.collection("eventos").document(tituloevento).get().addOnSuccessListener {
            //Si encuentra el documento será satisfactorio este listener y entraremos en él
            usersdelevento=it.get("asistentes") as ArrayList<User>
            //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
        }
    }*/

}