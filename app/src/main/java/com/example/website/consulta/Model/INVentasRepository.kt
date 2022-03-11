package com.example.website.consulta.Model

import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.dummy.Tienda

interface INVentasRepository {
    fun ObtenerTiendas(): ArrayList<Tienda>
    fun ObtenerTipoDocumento(): ArrayList<String>
    fun ObtenerMoneda(): ArrayList<Moneda>
    fun llamarEntidadToXRucApi(ruc: String)
    fun llamarEntidadToXDniApi(dni: String)
    fun obtenerEntidadToApi(): MutableLiveData<EntidadTo?>
    fun registrarPreFacturaCabDet(facturaTo: FacturaCabTo, lstArticulo: ArrayList<FacturaDetTo>): Boolean
}