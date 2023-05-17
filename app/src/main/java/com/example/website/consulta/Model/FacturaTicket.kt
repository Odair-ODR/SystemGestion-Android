package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.EmpresaTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import com.example.website.consulta.Model.Entidad.TIPO_DOCUMENTO
import java.text.DecimalFormat
import kotlin.math.roundToLong

data class FacturaTicket(val facturaCab: FacturaCabTo, val facturaDet: List<FacturaDetTo>) : ITicket{

    private var empresaObs = MultipleObservable()
    private var empresa:EmpresaTo? = null

    init {
        empresa = empresaObs.obtenerEmpresaSistema()
    }

    override fun getEmpresa(): EmpresaTicket {
        val empresa = EmpresaTicket().also {
            it.nombre = ""
            it.descripcion = empresa!!.nombre
            it.ruc = "R.U.C.: ${empresa!!.ruc}"
            it.direccion = empresa!!.direccion
            it.telefono = "TELF.: "
            it.correo = "Correo: "
        }
        return empresa
    }

    override fun getDescTipoDocumento(): String {
        val factura = "FACTURA DE VENTA ELECTRONICA"
        val boleta = "BOLETA DE VENTA ELECTRONICA"
        return if (facturaCab.tipoDoc == TIPO_DOCUMENTO.FACTURA) factura else boleta
    }

    override fun getNroTipoDocumento(): String {
        val initText = if(facturaCab.tipoDoc == TIPO_DOCUMENTO.FACTURA) "F" else "B"
        return "${initText}${facturaCab.serDoc}-${facturaCab.numDoc}"
    }

    override fun getFechaDocumento(): String {
        return "Fecha: ${facturaCab.fectra}"
    }

    override fun getVendedor(): VendedorTicket {
        val vendedor = VendedorTicket().also {
            it.nombre = "Vendedor: ${facturaCab.idUs}"
        }
        return vendedor
    }

    override fun getCliente(): ClienteTicket {
        val cliente = ClienteTicket().also {
            it.nombre = "Cliente: ${facturaCab.nombres}"
            it.nroDocumento = "Nro. Doc.: ${facturaCab.nroDocIdenti}"
        }
        return cliente
    }

    override fun getItems(): List<ItemsTicket> {
        val items:ArrayList<ItemsTicket> = ArrayList()
        var i = 1
        facturaDet.forEach{ det ->
            items.add(ItemsTicket().also {
                it.sec = i.toString()
                it.descItem = det.al32DescriArti.toString()
                it.can = det.al32Totcan.toString()
                it.preUni = det.al32Preuni.toString()
                it.total = det.al32pretot.toString()
            })
            i += 1
        }
        return items
    }

    override fun getTotales(): TotalesTicket {
        val decimalFormat = DecimalFormat("#.##")
        val totales = TotalesTicket().also {
            it.subTotal = decimalFormat.format(facturaCab.valven)
            it.igv = decimalFormat.format(facturaCab.valigv)
            it.total = decimalFormat.format(facturaCab.valbru)
        }
        return totales
    }
}
