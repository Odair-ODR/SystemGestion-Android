package com.example.website.consulta.View.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.website.consulta.Model.Entidad.VentasProceso
import com.example.website.consulta.databinding.ItemProcessVentasBinding

class VentasProcesoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemProcessVentasBinding.bind(view)

    fun render(ventasProceso: VentasProceso, onClickListener: (VentasProceso) -> Unit) {
        binding.lblTitle.text = ventasProceso.title
        binding.imageCardView.setImageDrawable(binding.lblTitle.context.getDrawable(ventasProceso.imageDrawable))
        itemView.setOnClickListener { onClickListener(ventasProceso) }
    }
}