package Adaptador

import Modelo.Notas
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minotas.Contactos
import com.example.minotas.R

class MiAdaptador : ArrayAdapter<Int> {
    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<Notas>
    private var seleccionado: Int


    constructor(context: Activity, resource: Int, valores: ArrayList<Notas>, seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado=seleccionado
    }


    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): Int? {
        return this.valores?.get(position).getHora().length
    }
    /**
     * Este método se carga para cada elemento de la lista. Tanta llamada a findviewbyid hace que se pueda
     * ralentizar y que caiga el rendimiento.
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView
        var holder = ViewHolder()
        if(view==null){
            if (this.context != null) {
                view = context.layoutInflater.inflate(this.resource, null)
                holder.txtItem = view.findViewById(R.id.txtNote)
                //holder.imagen = view.findViewById(R.id.imgResumen)
                view.tag=holder
            }
        }else{
            holder=view?.tag as ViewHolder
        }
        var valor: Notas = this.valores!![position]
        holder.txtItem?.text = valor.getAsunto()+"      "+valor.getFecha()+" "+valor.getHora()
        //holder.txtItem?.append(Encuestados.listaespe.toString())
        //Log.e("espe",Encuestados.listaespe.toString())
        if (valor.getTipo().equals("Nota Simple")) {
            //with(holder.imagen) {this?.setImageResource(R.drawable.linux)}
        }
        if (valor.getTipo().equals("Tareas")) {
            //with(holder.imagen) {this?.setImageResource(R.drawable.windows)}
        }
        if (position==seleccionado) {
            with(holder.txtItem) { this?.setBackgroundResource(R.color.purple_200)}
        }

        return view!!
    }
    class ViewHolder(){
        var txtItem: TextView? = null
        //var imagen: ImageView? = null
    }
}