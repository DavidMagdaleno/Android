package MiAdaptador


import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.encuesta.Persona
import com.example.encuesta.R

class MiAdaptadorRecycler (var personajes : ArrayList<Persona>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>() {

    companion object {
        var seleccionado:Int = -1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return ViewHolder(layoutInflater.inflate(R.layout.item_lo,parent,false))
        return ViewHolder(layoutInflater.inflate(R.layout.resumencardview,parent,false))
    }

    override fun getItemCount(): Int {
        return personajes.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombrePersona = view.findViewById(R.id.txtCard) as TextView
        val sistemaPersona = view.findViewById(R.id.txtCard2) as TextView
        val avatar = view.findViewById(R.id.imgRecycler) as ImageView

        fun bind(pers: Persona, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){
            nombrePersona.text = pers.getNombre()
            sistemaPersona.text = pers.getSistema()

            if (sistemaPersona.text.toString().equals("Mac")){
                Log.e("imagen",pers.getImagen())
                val uri = "@drawable/" +pers.getImagen()
                Log.e("imagen2",uri)
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
                Log.e("imagen3",avatar.drawable.current.toString())

            }
            else {
                //Glide.with(context).load(pers.getImagen()).into(avatar)
            }
            if (sistemaPersona.text.toString().equals("Linux")){

                val uri = "@drawable/" + pers.getImagen()
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)

            }
            else {
                //Glide.with(context).load(pers.getImagen()).into(avatar)
            }
            if (sistemaPersona.text.toString().equals("Windows")){

                val uri = "@drawable/" + pers.getImagen()
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
                Log.e("imagen3",avatar.toString())
            }
            else {
                //Glide.with(context).load(pers.getImagen()).into(avatar)
            }

            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(nombrePersona) {
                    this.setTextColor(resources.getColor(R.color.purple_200))
                }
            }
            else {
                with(nombrePersona) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
            }

            itemView.setOnClickListener(View.OnClickListener
            {
                if (pos == MiAdaptadorRecycler.seleccionado){
                    MiAdaptadorRecycler.seleccionado = -1
                }
                else {
                    MiAdaptadorRecycler.seleccionado = pos
                }
                //Con la siguiente instrucción forzamos a recargar el viewHolder porque han cambiado los datos. Así pintará al seleccionado.
                miAdaptadorRecycler.notifyDataSetChanged()

            })
        }
    }
}
