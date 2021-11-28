package Adaptador

import Modelo.Notas
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
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
                holder.txtItem2 = view.findViewById(R.id.txtFecha)
                view.tag=holder
            }
        }else{
            holder=view?.tag as ViewHolder
        }
        var valor: Notas = this.valores!![position]
        holder.txtItem?.text = "    "+valor.getAsunto()
        holder.txtItem2?.text= valor.getFecha()+" "+valor.getHora()
        if (valor.getTipo().equals("Nota Simple")) {
            with(holder.txtItem) { this?.setBackgroundResource(R.color.nota)}
            with(holder.txtItem2) { this?.setBackgroundResource(R.color.nota)}
        }
        if (valor.getTipo().equals("Tareas")) {
            with(holder.txtItem) { this?.setBackgroundResource(R.color.cyan)}
            with(holder.txtItem2) { this?.setBackgroundResource(R.color.cyan)}
        }
        if (position==seleccionado) {
            with(holder.txtItem) { this?.setBackgroundResource(R.color.purple_200)}
            with(holder.txtItem2) { this?.setBackgroundResource(R.color.purple_200)}
        }
        return view!!
    }
    class ViewHolder(){
        var txtItem: TextView? = null
        var txtItem2: TextView? = null

    }
}