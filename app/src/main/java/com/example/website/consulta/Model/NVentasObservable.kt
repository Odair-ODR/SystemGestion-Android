package com.example.website.consulta.Model

import androidx.lifecycle.LiveData
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.dummy.Tienda

class NVentasObservable {
    private val nVentasRepository: INVentasRepository = NVentasRepositoryImp()
    private var articuloRespository: IArticuloRepository = ArticuloRepositoryImp()

    fun ObtnerTiendas(): ArrayList<Tienda> {
        return nVentasRepository.ObtenerTiendas()
    }

    fun ObtenerMoneda(): ArrayList<Moneda> {
        return nVentasRepository.ObtenerMoneda()
    }

    fun llamarEntidadToXRucApi(ruc: String) {
        return nVentasRepository.llamarEntidadToXRucApi(ruc)
    }

    fun llamarEntidadToXDniApi(dni: String) {
        return nVentasRepository.llamarEntidadToXDniApi(dni)
    }

    fun obtenerEntidadToApi(): LiveData<EntidadTo?> {
        return nVentasRepository.obtenerEntidadToApi()
    }

    fun registrarPreFacturaCabDet(facturaTo: FacturaCabTo, lstFacturaDetTo: ArrayList<FacturaDetTo>): Boolean {
        return nVentasRepository.registrarPreFacturaCabDet(facturaTo, lstFacturaDetTo)
    }

    fun obtenerIGV(): Double{
        return nVentasRepository.obtenerIGV()
    }
}
