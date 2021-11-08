package MiAdaptador

import android.app.Activity
import Auxiliar.Conexion
import Auxiliar.Encuestados
import Modelo.Especialidad
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.encuesta.Persona
import com.example.encuesta.R
import com.example.encuesta.ResumenListViews


class MiAdaptador : ArrayAdapter<Int> {
    private var context: Activity
    private var resource: Int
    private var valores: ArrayList<Persona>
    private var seleccionado: Int


    constructor(context: Activity, resource: Int, valores: ArrayList<Persona>,seleccionado:Int) : super(context, resource) {
        this.context = context
        this.resource = resource
        this.valores = valores
        this.seleccionado=seleccionado
    }


    override fun getCount(): Int {
        return this.valores?.size!!
    }

    override fun getItem(position: Int): Int? {
    return this.valores?.get(position).getHoras()
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
                holder.txtItem = view.findViewById(R.id.lbllstResumen)
                holder.imagen = view.findViewById(R.id.imgResumen)
                view.tag=holder
            }
        }else{
            holder=view?.tag as ViewHolder
        }
            var valor: Persona = this.valores!![position]
            holder.txtItem?.text = valor.getNombre()+"\r\n"
            holder.txtItem?.append(valor.getSistema())
            //holder.txtItem?.append(Encuestados.listaespe.toString())
            //Log.e("espe",Encuestados.listaespe.toString())
            if (valor.getSistema().equals("Linux")) {
                with(holder.imagen) {this?.setImageResource(R.drawable.linux)}
            }
            if (valor.getSistema().equals("Windows")) {
                with(holder.imagen) {this?.setImageResource(R.drawable.windows)}
            }
            if (valor.getSistema().equals("Mac")) {
                with(holder.imagen) {this?.setImageResource(R.drawable.mac)}
            }
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
        var imagen:ImageView? = null
    }
}