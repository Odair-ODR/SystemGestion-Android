package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.CorrelativoTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo

class ControlDocumentoObs {
    private val controlDocumento: IControlDocumento = ControlDocumentoImp()

    fun obtenerCorrelativoDocumento(facturaCabTo: FacturaCabTo): CorrelativoTo? {
     return controlDocumento.obtenerCorrelativoDocumento(facturaCabTo)
    }
}