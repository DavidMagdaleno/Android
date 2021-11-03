package Auxiliar

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.R


class MiAdaptador : ArrayAdapter<Int> {
    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<String>
    private var seleccionado: Int

    constructor(context: Activity, resource: Int, valores: ArrayList<String>,seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado=seleccionado
    }


    override fun getCount(): Int {
        return this.valores?.size!!
    }

    /*override fun getItem(position: Int): Int? {
        return this.valores?.get(position)
    }*/

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
                holder.txtItem = view.findViewById(R.id.txtNotas)
                //holder.imagen = view.findViewById(R.id.imgResumen)
                view.tag=holder
            }
        }else{
            holder=view?.tag as ViewHolder
        }
        var valor: String = this.valores!![position]
        holder.txtItem?.text = valor+"\r\n"
        //holder.txtItem?.append(valor.getSistema()
        if (position==seleccionado) {
            with(holder.txtItem) { this?.setBackgroundResource(R.color.purple_200)}
            //with(imagen) {
            //setImageResource(R.drawable.ic_baseline_perm_identity_24)
            //}
        }

        return view!!
    }
    class ViewHolder(){
        var txtItem:TextView? = null
        //var imagen:ImageView? = null
    }
}