package com.example.website.consulta.Model

import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.Articulo

interface IArticuloRepository {
    fun ObtenerArticulosFactuera(codbar: String, alternante: String): MutableLiveData<List<Articulo>>
    fun ObtenerArticulosFactura(codbar: String, alternante: String): ArrayList<Articulo>
}