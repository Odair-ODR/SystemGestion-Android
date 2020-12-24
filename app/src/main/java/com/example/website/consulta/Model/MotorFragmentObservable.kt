package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor

class MotorFragmentObservable {
    private val motorFragmentRepository: IMotorFragmentRepository = MotorFragmentRepositoryImp()

    fun ObtenerMotores(codProd: String, motor: String): ArrayList<Motor>{
        return motorFragmentRepository.ObtenerMotores(codProd, motor)
    }

    fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo>{
        return motorFragmentRepository.ObtenerArticulosXMotorCodProd(codProd, motor)
    }
}