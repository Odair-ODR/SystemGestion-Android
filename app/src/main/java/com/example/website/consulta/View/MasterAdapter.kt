package com.example.website.consulta.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import java.lang.String

class MasterAdapter<T>(context: Context?, lstAr: ArrayList<T?>?, private val acces: Int) : ArrayAdapter<T>(context, 0, lstAr) {
    private val t: T? = null

    class ViewHolder {
        var item1: TextView? = null
        var item2: TextView? = null
        var item3: TextView? = null
        var item4: TextView? = null
        var item5: TextView? = null
        var item6: TextView? = null
        var item7: TextView? = null
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        if (acces == 1) {
            return ConvertViewArticulo(position, convertView, parent)
        } else if (acces == 2) {
            return ConvertVieCodBar(position, convertView, parent)
        } else if (acces == 3) {
            return ConvertVieMotores(position, convertView, parent)
        } else if (acces == 4) {
            return ConvertVieMotor(position, convertView, parent)
        }
        return convertView
    }

    private fun ConvertViewArticulo(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView: View = convertView
        val entidad: T = getItem(position)
        val articulo: Articulo = entidad as Articulo
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            convertView = layoutInflater.inflate(R.layout.row_list_alternante, parent, false)
            viewHolder.item3 = convertView.findViewById<TextView>(R.id.txtCpdnew)
            viewHolder.item2 = convertView.findViewById<TextView>(R.id.txtAlternante)
            viewHolder.item1 = convertView.findViewById<TextView>(R.id.txtCodbar)
            //> viewHolder.item5 = convertView.findViewById(R.id.txtUnimed);
            //> viewHolder.item6 = convertView.findViewById(R.id.txtCampar);
            viewHolder.item4 = convertView.findViewById<TextView>(R.id.txtTotSaldo)
            viewHolder.item7 = convertView.findViewById<TextView>(R.id.txtMotor)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item1?.setText(articulo.codbar)
        viewHolder.item4?.setText(String.valueOf(articulo.totSaldo))
        viewHolder.item7?.setText(articulo.motor)
        return convertView
    }

    private fun ConvertVieMotor(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView: View = convertView
        val entidad: T = getItem(position)
        val motor: Motor = entidad as Motor
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            convertView = layoutInflater.inflate(R.layout.row_motores, parent, false)
            viewHolder.item1 = convertView.findViewById<TextView>(R.id.txtMarca)
            viewHolder.item2 = convertView.findViewById<TextView>(R.id.txtMotor)
            viewHolder.item3 = convertView.findViewById<TextView>(R.id.txtCili1)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.item1?.setText(motor.marca)
        viewHolder.item2?.setText(motor.motor)
        viewHolder.item3?.setText(motor.cili1)
        return convertView
    }

    private fun ConvertVieCodBar(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView: View = convertView
        val entidad: T = getItem(position)
        val articulo: Articulo = entidad as Articulo
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            convertView = layoutInflater.inflate(R.layout.row_list_codbar, parent, false)
            viewHolder.item3 = convertView.findViewById(R.id.txtCpdnew)
            viewHolder.item2 = convertView.findViewById(R.id.txtAlternante)
            viewHolder.item5 = convertView.findViewById(R.id.txtUnimed)
            viewHolder.item6 = convertView.findViewById(R.id.txtCampar)
            viewHolder.item4 = convertView.findViewById(R.id.txtTotSaldo)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item5?.setText(articulo.unimed)
        viewHolder.item6?.setText(String.valueOf(articulo.campar))
        viewHolder.item4?.setText(String.valueOf(articulo.totSaldo))
        return convertView
    }

    private fun ConvertVieMotores(position: Int, convertView: View, parent: ViewGroup): View {
        var convertView: View = convertView
        val entidad: T = getItem(position)
        val articulo: Articulo = entidad as Articulo
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            convertView = layoutInflater.inflate(R.layout.row_list_motor, parent, false)
            viewHolder.item3 = convertView.findViewById(R.id.txtCpdnew)
            viewHolder.item2 = convertView.findViewById(R.id.txtAlternante)
            viewHolder.item1 = convertView.findViewById(R.id.txtCodbar)
            viewHolder.item5 = convertView.findViewById(R.id.txtUnimed)
            viewHolder.item6 = convertView.findViewById(R.id.txtCampar)
            viewHolder.item4 = convertView.findViewById(R.id.txtTotSaldo)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item1?.setText(articulo.codbar)
        viewHolder.item5?.setText(articulo.unimed)
        viewHolder.item6?.setText(String.valueOf(articulo.campar))
        viewHolder.item4?.setText(String.valueOf(articulo.totSaldo))
        return convertView
    }
}