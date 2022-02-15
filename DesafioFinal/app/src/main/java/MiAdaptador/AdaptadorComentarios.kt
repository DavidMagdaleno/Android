package MiAdaptador

import Model.Comentario
import Model.Imagenes
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AdaptadorComentarios (var cmt : ArrayList<Comentario>, var  context: Context) : RecyclerView.Adapter<AdaptadorComentarios.ViewHolder>(){
    companion object {
        var seleccionado:Int = -1
        var titulo:String=""
        var email:String=""
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = cmt.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_adapcomentario, parent, false))
    }

    override fun getItemCount(): Int {
        return cmt.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val avatar = view.findViewById(R.id.imgFoto) as ImageView
        val usu = view.findViewById(R.id.txtMailUsu) as TextView
        val come = view.findViewById(R.id.txtComentAdap) as TextView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pers: Comentario, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorComentarios){

            //avatar.setImageBitmap(pers.img)
            usu.setText(pers.em)
            come.setText(pers.comen)

            with(usu) {
                this.setTextColor(resources.getColor(com.example.desafiofinal.R.color.purple_200))
            }

            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == AdaptadorComentarios.seleccionado){
                        AdaptadorComentarios.seleccionado = -1
                    }
                    else {
                        AdaptadorComentarios.seleccionado = pos
                    }
                })
            itemView.setOnLongClickListener(View.OnLongClickListener{
                /*val desRef = Firebase.storage.reference.child(AdaptadorFotos.titulo +"/").child(
                    AdaptadorFotos.email +"/").child(pers.nombre)

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        desRef.delete().addOnSuccessListener {
                            // File deleted successfully--------------------------------------------------------------------------
                            miAdaptadorRecycler.fotos.removeAt(pos)
                        }.addOnFailureListener {
                            // Uh-oh, an error occurred!
                            Toast.makeText(context, "Esta foto no es tuya, no lo puedes borrar", Toast.LENGTH_SHORT).show()
                        }
                    })
                dialogo.setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                dialogo.setTitle("¿Borrar Elemento?")
                dialogo.setMessage("¿Deseas eliminar este elemento?")
                dialogo.show()*/

                miAdaptadorRecycler.notifyDataSetChanged()
                return@OnLongClickListener true
            })
        }
    }
}