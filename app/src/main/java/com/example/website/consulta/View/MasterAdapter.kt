package com.example.website.consulta.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import com.example.website.consulta.dummy.Tienda

class MasterAdapter<T>(context: Context, lstAr: ArrayList<T>, private val acces: Int) : ArrayAdapter<T>(context, 0, lstAr) {
    private val t: T? = null

    internal class ViewHolder {
        var item1: TextView? = null
        var item2: TextView? = null
        var item3: TextView? = null
        var item4: TextView? = null
        var item5: TextView? = null
        var item6: TextView? = null
        var item7: TextView? = null
    }

    internal class ViewHolder2 {
        var item1: RadioButton? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        when (acces) {
            1 -> return ConvertViewArticulo(position, convertView, parent)
            2 -> return ConvertVieCodBar(position, convertView, parent)
            3 -> return ConvertVieMotores(position, convertView, parent)
            4 -> return ConvertVieMotor(position, convertView, parent)
            5 -> return ConvertViewTiendas(position, convertView, parent)
            else -> throw IllegalArgumentException()
        }
    }

    private fun ConvertViewArticulo(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            cv = layoutInflater.inflate(R.layout.row_list_alternante, parent, false)
            viewHolder.item3 = cv.findViewById(R.id.txtCpdnew)
            viewHolder.item2 = cv.findViewById(R.id.txtAlternante)
            viewHolder.item1 = cv.findViewById(R.id.txtCodbar)
            viewHolder.item4 = cv.findViewById(R.id.txtTotSaldo)
            viewHolder.item7 = cv.findViewById(R.id.txtMotor)
            cv.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            cv = convertView
        }
        val articulo: Articulo = getItem(position) as Articulo
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item1?.setText(articulo.codbar)
        viewHolder.item4?.setText(articulo.totSaldo.toString())
        viewHolder.item7?.setText(articulo.motor)
        return cv
    }

    private fun ConvertVieMotor(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            cv = layoutInflater.inflate(R.layout.row_motores, parent, false)
            viewHolder.item1 = cv.findViewById(R.id.txtMarca)
            viewHolder.item2 = cv.findViewById(R.id.txtMotor)
            viewHolder.item3 = cv.findViewById(R.id.txtCili1)
            cv.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            cv = convertView
        }
        val motor: Motor = getItem(position) as Motor
        viewHolder.item1?.setText(motor.marca)
        viewHolder.item2?.setText(motor.motor)
        viewHolder.item3?.setText(motor.cili1)
        return cv
    }

    private fun ConvertVieCodBar(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            cv = layoutInflater.inflate(R.layout.row_list_codbar, parent, false)
            viewHolder.item3 = cv.findViewById(R.id.txtCpdnew)
            viewHolder.item2 = cv.findViewById(R.id.txtAlternante)
            viewHolder.item5 = cv.findViewById(R.id.txtUnimed)
            viewHolder.item6 = cv.findViewById(R.id.txtCampar)
            viewHolder.item4 = cv.findViewById(R.id.txtTotSaldo)
            cv.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            cv = convertView
        }
        val articulo: Articulo = getItem(position) as Articulo
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item5?.setText(articulo.unimed)
        viewHolder.item6?.setText(articulo.campar.toString())
        viewHolder.item4?.setText(articulo.totSaldo.toString())
        return cv
    }

    private fun ConvertVieMotores(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            viewHolder = ViewHolder()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            cv = layoutInflater.inflate(R.layout.row_list_motor, parent, false)
            viewHolder.item3 = cv.findViewById(R.id.txtCpdnew)
            viewHolder.item2 = cv.findViewById(R.id.txtAlternante)
            viewHolder.item1 = cv.findViewById(R.id.txtCodbar)
            viewHolder.item5 = cv.findViewById(R.id.txtUnimed)
            viewHolder.item6 = cv.findViewById(R.id.txtCampar)
            viewHolder.item4 = cv.findViewById(R.id.txtTotSaldo)
            cv.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            cv = convertView
        }
        val articulo: Articulo = getItem(position) as Articulo
        viewHolder.item2?.setText(articulo.alternante)
        viewHolder.item3?.setText(articulo.cpdnew)
        viewHolder.item1?.setText(articulo.codbar)
        viewHolder.item5?.setText(articulo.unimed)
        viewHolder.item6?.setText(articulo.campar.toString())
        viewHolder.item4?.setText(articulo.totSaldo.toString())
        return cv
    }

    private fun ConvertViewTiendas(position: Int, convertView: View?, parent: ViewGroup): View {
        val cv: View
        val viewHolder: ViewHolder2
        if (convertView == null) {
            viewHolder = ViewHolder2()
            val layoutInflater: LayoutInflater = LayoutInflater.from(getContext())
            cv = layoutInflater.inflate(R.layout.tiendas_alert_dialog, parent, false)
            //> viewHolder.item1 = cv.findViewById(R.id.rdbItem)
            cv.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder2
            cv = convertView
        }
        val tienda: Tienda = getItem(position) as Tienda
        tienda.nomTienda.also { viewHolder.item1?.text = it }
        tienda.idTienda.also { viewHolder.item1?.tag = it }
        return cv
    }
}