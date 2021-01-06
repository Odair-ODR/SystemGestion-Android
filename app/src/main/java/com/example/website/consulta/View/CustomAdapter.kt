package com.example.website.consulta.View

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*

class CustomAdapter(private val mActivity: Activity, d: ArrayList<*>) : BaseAdapter() {
    private val mList: ArrayList<Any>? = null
    private var tempValues: Any? = null
    private val i = 0
    override fun getCount(): Int {
        if (mList != null) {
            return if (mList.size <= 0) 1 else mList.size
        }
        return 0
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class ViewHolder {
        var txtcodbar: TextView? = null
        var txtalternante: TextView? = null
        var txtdescripcion: TextView? = null
        var txtsaldo: TextView? = null
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var vi = convertView
        val holder: ViewHolder
        holder = vi.tag as ViewHolder
        try {
            if (mList?.size!! <= 0) {
                holder.txtcodbar!!.text = "No data"
                holder.txtalternante!!.text = "No data"
                holder.txtdescripcion!!.text = "No data"
                holder.txtsaldo!!.text = "No data"
            } else {
                tempValues = null
                tempValues = mList?.get(position)
                /*holder.txtcodbar?.setText(tempValues!!.codbar)
                holder.txtalternante?.setText(tempValues!!.alternante)
                holder.txtdescripcion?.setText(tempValues!!.descripcion)
                holder.txtsaldo?.setText(tempValues!!.saldo)*/
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return vi
    }
}