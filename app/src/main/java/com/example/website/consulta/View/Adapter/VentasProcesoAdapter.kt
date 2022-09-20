package com.example.website.consulta.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.website.consulta.Model.Entidad.VentasProceso
import com.example.website.consulta.R

class VentasProcesoAdapter(private val lstVentasProceso: List<VentasProceso>, private val onClickListener: (VentasProceso) -> Unit): RecyclerView.Adapter<VentasProcesoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentasProcesoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VentasProcesoViewHolder(layoutInflater.inflate(R.layout.item_process_ventas, parent, false))
    }

    override fun onBindViewHolder(holder: VentasProcesoViewHolder, position: Int) {
        val item = lstVentasProceso[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = lstVentasProceso.size
}