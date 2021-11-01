package MiAdaptador

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.encuesta.Persona
import com.example.encuesta.R


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
        if (this.context != null) {
            view = context.layoutInflater.inflate(this.resource, null)
            var txtItem: TextView = view.findViewById(R.id.lbllstResumen)
            var imagen: ImageView = view.findViewById(R.id.imgResumen)
            var valor: Persona = this.valores!![position]
            txtItem.text = valor.getNombre()
            txtItem.append(valor.getSistema())
            if (valor.getSistema().equals("Linux")) {
                with(imagen) {setImageResource(R.drawable.linux)}
            }
            if (valor.getSistema().equals("Windows")) {
                with(imagen) {setImageResource(R.drawable.windows)}
            }
            if (valor.getSistema().equals("Mac")) {
                with(imagen) {setImageResource(R.drawable.mac)}
            }
            if (position==seleccionado) {
                with(txtItem) { setBackgroundResource(R.color.purple_200)}
                //with(imagen) {
                //setImageResource(R.drawable.ic_baseline_perm_identity_24)
                //}
            }
        }
        return view!!
    }
}