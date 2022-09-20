package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.CorrelativoTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import java.sql.Connection

interface IControlDocumento {
    fun obtenerCorrelativoDocumento(facturaCabTo: FacturaCabTo): CorrelativoTo?
    fun actualizarNroDocumentoCaja(facturaTo: FacturaCabTo, cn: Connection): Boolean
    fun obtenerCorrelativoDocumento(facturaCabTo: FacturaCabTo, cn: Connection): CorrelativoTo?
}