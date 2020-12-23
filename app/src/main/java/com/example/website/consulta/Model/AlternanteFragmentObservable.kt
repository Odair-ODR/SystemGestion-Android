package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

class AlternanteFragmentObservable {
    private val altenanteFragmentRepository: IAlternanteFragmentRepository = AlternanteFragmentRepositoryImp()

    fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>{
        return altenanteFragmentRepository.ObtenerArticulosXAlternante(alternante)
    }
}