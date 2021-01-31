package com.example.website.consulta.Model

import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.Articulo

interface IArticuloRepository {
    fun ObtenerArticulosFactuera(codbar: String, alternante: String): MutableLiveData<List<Articulo>>
    fun ObtenerArticulosXCobar(marvehi: Int, ini: Int, fin: Int): ArrayList<Articulo>
    fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>
    fun ObtenerArticuloXIdArticulo(idArticulo: Int): Articulo?
}