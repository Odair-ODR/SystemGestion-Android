package com.example.website.consulta.Model

import com.example.website.consulta.dummy.Tienda

class NVentasObservable {
    private val nVentasRepository: INVentasRepository = NVentasRepositoryImp()

    fun ObtnerTiendas(): ArrayList<String>{
        return  nVentasRepository.ObtenerTiendas()
    }
}
