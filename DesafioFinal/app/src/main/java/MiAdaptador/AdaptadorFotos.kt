package MiAdaptador

import Model.Imagenes
import Model.User
import android.R.attr
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Build
import android.provider.MediaStore.Images.Media.getBitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.graphics.drawable.toAdaptiveIcon
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.Maps
import com.example.desafiofinal.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import android.R.attr.bitmap
import android.util.Base64
import android.widget.Toast
import java.io.ByteArrayOutputStream


class AdaptadorFotos (var fotos : ArrayList<Imagenes>, var  context: Context) : RecyclerView.Adapter<AdaptadorFotos.ViewHolder>() {
    companion object {
        var seleccionado:Int = -1
        var titulo:String=""
        var email:String=""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = fotos.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_foto,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return fotos.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatar = view.findViewById(R.id.imgFoto) as ImageView


        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(pers: Imagenes, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorFotos){

            avatar.setImageBitmap(pers.img)

            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == AdaptadorFotos.seleccionado){
                        AdaptadorFotos.seleccionado = -1
                    }
                    else {
                        AdaptadorFotos.seleccionado = pos
                    }
                })
            itemView.setOnLongClickListener(View.OnLongClickListener{
                miAdaptadorRecycler.fotos.removeAt(pos)
                val desRef = Firebase.storage.reference.child(titulo+"/").child(email+"/").child(pers.nombre)

                val dialogo: AlertDialog.Builder = AlertDialog.Builder(context)
                dialogo.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->
                        desRef.delete().addOnSuccessListener {
                            // File deleted successfully
                            //miAdaptadorRecycler.fotos.removeAt(pos)
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
                dialogo.show()

                miAdaptadorRecycler.notifyDataSetChanged()
                return@OnLongClickListener true
            })
        }
    }
}