package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor

interface IMotorFragmentRepository {
    fun ObtenerMotores(codProd: String, motor: String): ArrayList<Motor>
    fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo>
}