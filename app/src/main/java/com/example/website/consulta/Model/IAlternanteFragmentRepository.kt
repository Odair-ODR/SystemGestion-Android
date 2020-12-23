package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

interface IAlternanteFragmentRepository {
    fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>
}