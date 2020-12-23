package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

interface ICodbarFragmentRepository {
    fun ObtenerArticuloXCodbar(codbar: String): ArrayList<Articulo>
}