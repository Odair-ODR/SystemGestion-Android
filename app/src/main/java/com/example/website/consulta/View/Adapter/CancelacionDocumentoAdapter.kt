package com.example.website.consulta.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.R

class CancelacionDocumentoAdapter(
    private val lstPreFactura: List<FacturaCabTo>,
    private val onClickListener: (FacturaCabTo, index: Int) -> Unit
) :
    RecyclerView.Adapter<CancelacionDocumentoViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CancelacionDocumentoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CancelacionDocumentoViewHolder(inflater.inflate(R.layout.row_prefactura, parent, false))
    }

    override fun onBindViewHolder(holder: CancelacionDocumentoViewHolder, position: Int) {
        val item = lstPreFactura[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int = lstPreFactura.size
}