package com.example.website.consulta.View.Adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.website.consulta.Helpers.UtilsMethod.Companion.getDateFormat
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.databinding.RowPrefacturaBinding


class CancelacionDocumentoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = RowPrefacturaBinding.bind(view)

    fun render(preFactura: FacturaCabTo, onClickListener: (FacturaCabTo, Int) -> Unit) {
        binding.lblTienda.text = preFactura.idTienda.toString()
        binding.lblSerieNumero.text =
            String.format("%1\$2s %2\$2s", preFactura.serDoc, preFactura.numDoc.toString())
        binding.lblCliente.text = preFactura.nombres
        //> binding.lblNroDocIdentidad.text = preFactura.nroDocIdenti
        binding.lblFectra.text = preFactura.fectra.getDateFormat()
        binding.lblTotal.text = preFactura.valbru.toString()
        binding.btnFacturar.setOnClickListener(btnFacturarOnClickListener(preFactura,  onClickListener))
    }

    private fun btnFacturarOnClickListener(preFactura: FacturaCabTo, onClickListener: (FacturaCabTo, Int) -> Unit)
    = View.OnClickListener { onClickListener(preFactura, adapterPosition) }
}