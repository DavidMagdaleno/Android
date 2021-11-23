package Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gatosweb.R

class MiAdaptadorRV (private var context: Context,
                     private var message: ArrayList<String>,//cambiar en funcion de un arraylist
                     private var status: String
) :
    RecyclerView.Adapter<MiAdaptadorRV.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // set the data in items
        //holder.imagen = message[position]
        Glide.with(context).load(message[position]).centerCrop().into(holder.imagen)//las url se cargan asi (centercrop para redimensionar)
        holder.status.text = status//Cambiar si es array list
        //holder.mobileNo.text = mobileNumbers[position]
        holder.itemView.setOnClickListener {
            Toast.makeText(context, message[position], Toast.LENGTH_SHORT).show()
        }
    }
    override fun getItemCount(): Int {
        return message.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imagen: ImageView = itemView.findViewById<View>(R.id.img) as ImageView
        var status: TextView = itemView.findViewById<View>(R.id.txtImagen) as TextView
        //var mobileNo: TextView = itemView.findViewById<View>(R.id.txtMovil) as TextView
    }
}