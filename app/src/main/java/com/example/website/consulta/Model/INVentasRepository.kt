package com.example.website.consulta.Model

import com.example.website.consulta.dummy.Tienda

interface INVentasRepository {
    fun ObtenerTiendas(): ArrayList<String>
    fun ObtenerTipoDocumento(): ArrayList<String>
}