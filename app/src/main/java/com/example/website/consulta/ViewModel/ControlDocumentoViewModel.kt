package com.example.website.consulta.ViewModel

import com.example.website.consulta.Model.ControlDocumentoObs
import com.example.website.consulta.Model.Entidad.CorrelativoTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo

class ControlDocumentoViewModel {
    private val controlDocumentoObs = ControlDocumentoObs()

    fun obtenerCorrelativoDocumento(facturaCabTo: FacturaCabTo): CorrelativoTo?{
        return controlDocumentoObs.obtenerCorrelativoDocumento(facturaCabTo)
    }
}