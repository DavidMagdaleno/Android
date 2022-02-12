package MiAdaptador

import Model.User
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiofinal.R
import com.google.firebase.firestore.FirebaseFirestore

class AdaptadorFotos (var fotos : ArrayList<Bitmap>, var  context: Context) : RecyclerView.Adapter<AdaptadorFotos.ViewHolder>() {
    companion object {
        var seleccionado:Int = -1
    }

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
        private val db = FirebaseFirestore.getInstance() //Variable con la que accederemos a Firestore. Será una instancia a la bd.
        //var croles = ArrayList<Int>()

        //val usDNI = view.findViewById(R.id.lblDni) as TextView
        //val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        val avatar = view.findViewById(R.id.imgFoto) as ImageView

        fun bind(pers: Bitmap, context: Context, pos: Int, miAdaptadorRecycler: AdaptadorFotos){

            //val uri = "@drawable/" +pers
            //Log.e("imagen2",uri)
            //val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
            //var res: Drawable = context.resources.getDrawable(imageResource)
            //avatar.setImageDrawable(res)
            avatar.setImageBitmap(pers)


            if (pos == AdaptadorFotos.seleccionado) {
                //with(avatar) {
                    //this.setTextColor(resources.getColor(R.color.purple_200))
                //}
            }
            else {
                //with(avatar) {
                    //this.setTextColor(resources.getColor(R.color.black))
                //}
            }
            itemView.setOnClickListener(
                View.OnClickListener
                {
                    if (pos == AdaptadorFotos.seleccionado){
                        AdaptadorFotos.seleccionado = -1
                    }
                    else {
                        AdaptadorFotos.seleccionado = pos
                        //borrar fotos que solo sean del usuario


                    }
                    //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                    miAdaptadorRecycler.notifyDataSetChanged()

                })
        }
    }

}