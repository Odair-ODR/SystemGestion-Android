package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo

data class FacturaTicket(val facturaCab: FacturaCabTo, val facturaDet: List<FacturaDetTo>) : ITicket{

    override fun getEmpresa(): EmpresaTicket {
        val empresa = EmpresaTicket().also {
            it.nombre = "Ediciones Americanas"
            it.descripcion = "Ediciones Americanas SAC"
            it.ruc = "R.U.C.: 108394859394"
            it.direccion = "AV. Las gardenias - Surco"
            it.telefono = "TELF.: +51 929384743"
            it.correo = "Correo: edicionesamericanas@grupon.com"
        }
        return empresa
    }

    override fun getDescTipoDocumento(): String {
        return "FACTURA DE VENTA ELECTRONICA"
    }

    override fun getNroTipoDocumento(): String {
        return "F001-03439546"
    }

    override fun getFechaDocumento(): String {
        return "Fecha: 07/09/2024"
    }

    override fun getVendedor(): VendedorTicket {
        val vendedor = VendedorTicket().also {
            it.nombre = "Vendedor: 105 - Woody"
        }
        return vendedor
    }

    override fun getCliente(): ClienteTicket {
        val cliente = ClienteTicket().also {
            it.nombre = "Cliente: Alefredo Veldez"
            it.nroDocumento = "DNI: 70839485"
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
                it.total = det.al32pretot.toString()
            })
            i += 1
        }
        return items
    }

    override fun getTotales(): TotalesTicket {
        val totales = TotalesTicket().also {
            it.subTotal = facturaCab.valven.toString()
            it.igv = facturaCab.valigv.toString()
            it.total = facturaCab.valbru.toString()
        }
        return totales
    }
}
