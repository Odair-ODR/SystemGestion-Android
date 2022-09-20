package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import java.sql.Connection

interface ICancelacionDocumentoRepository {

    fun listarPrefactura(preFactura: FacturaCabTo): List<FacturaCabTo>
    fun verificaFacturaBoletaporNro(preFactura: FacturaCabTo, connection: Connection): Boolean
    fun actualizaPreFacturaCabFacturacion(preFactura: FacturaCabTo, connection: Connection): Boolean
    fun actualizaPreFacturaDetFacturacion(preFactura: FacturaCabTo, connection: Connection): Boolean
    fun actualizaAlmacendeFacturaCompras(preFacturaDet: FacturaDetTo, connection: Connection): Boolean
    fun obtenerPreFacturaDet(idPreFacturaCab: Int): List<FacturaDetTo>
}