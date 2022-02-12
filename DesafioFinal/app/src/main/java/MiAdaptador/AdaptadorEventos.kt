package MiAdaptador

import Model.User
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.Maps
import com.example.desafiofinal.OpcionesEventos
import com.example.desafiofinal.R
import com.google.firebase.firestore.FirebaseFirestore

class AdaptadorEventos (var eventos : ArrayList<String>, var  context: Context) : RecyclerView.Adapter<AdaptadorEventos.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
        var titulo:String=""
        var email:String=""
        var creacion:Boolean=false
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

        fun bind(pers: String, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorEventos){
            usEvento.text = pers


            if (pos == AdaptadorEventos.seleccionado) {
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
                    if (pos == AdaptadorEventos.seleccionado){
                        AdaptadorEventos.seleccionado = -1
                    }
                    else {
                        AdaptadorEventos.seleccionado = pos
                        titulo=pers

                        val dialogo: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
                        val Myview= RecyclerView.inflate(context,R.layout.item_dialogo3, null)
                        var btConf = Myview.findViewById<Button>(R.id.btnConfirmar)
                        var btDes = Myview.findViewById<Button>(R.id.btnDescubrir)
                        dialogo.setView(Myview)
                        btConf.setOnClickListener {
                            db.collection("eventos").document(pers).get().addOnSuccessListener {
                                //Si encuentra el documento será satisfactorio este listener y entraremos en él
                                var usersdelevento = it.get("asistentes") as ArrayList<User>

                                db.collection("usuarios").document(email).get()
                                    .addOnSuccessListener {
                                        var e: User = User(
                                            it.get("DNI").toString(),
                                            it.get("Nombre").toString(),
                                            it.get("Apellidos").toString(),
                                            it.get("Aceptado").toString(),
                                            it.get("email").toString(),
                                            it.get("Ubicacion").toString(),
                                            it.get("Hora").toString(),
                                            it.get("roles") as ArrayList<Int>
                                        )

                                        usersdelevento.add(e)
                                        db.collection("eventos").document(pers).get()
                                            .addOnSuccessListener {
                                                var p = it.get("Ubicacion").toString()

                                                var user = hashMapOf(
                                                    "Ubicacion" to p,
                                                    "asistentes" to usersdelevento,
                                                    "comentarios" to it.get("cometarios").toString(),
                                                    "fotos" to it.get("fotos").toString()
                                                )
                                                db.collection("eventos")//añade o sebreescribe
                                                    .document(pers) //Será la clave del documento.
                                                    .set(user).addOnSuccessListener {
                                                        //Toast.makeText(Context, "Almacenado", Toast.LENGTH_SHORT).show()
                                                    }.addOnFailureListener {
                                                        //Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                                                    }
                                                //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
                                            }.addOnFailureListener {
                                            //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                                        }

                                        //Toast.makeText(this, "Recuperado",Toast.LENGTH_SHORT).show()
                                    }.addOnFailureListener {
                                    //Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        btDes.setOnClickListener {
                            var intentmais: Intent = Intent(context, OpcionesEventos::class.java)
                            intentmais.putExtra("tituloEvento",pers)
                            intentmais.putExtra("user", email)
                            ContextCompat.startActivity(context, intentmais, null)
                        }
                        dialogo.show()
                    }
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
    }


}