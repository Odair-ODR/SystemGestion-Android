package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

class CodbarFragmentObservable {
    private val codbarFragmenRepository: ICodbarFragmentRepository = CodbarFragmentRepositoryImp()

    fun ObtenerArticuloXCodbar(codbar: String): ArrayList<Articulo>{
        return codbarFragmenRepository.ObtenerArticuloXCodbar(codbar)
    }
}