package com.example.website.consulta.Model

import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.Articulo

class ArticuloObservable: BaseObservable() {
    //> Conexión directa con el repositorio
    //> Conexión directa con ViewModel
    //> Estas clases va tener una conexión directa con el respositorio y con el viewModel
    private var articuloRespository: IArticuloRepository = ArticuloRepositoryImp()

    fun obtenerArticulosFactura(codbar: String, alternante: String): MutableLiveData<List<Articulo>>{
        return articuloRespository.ObtenerArticulosFactuera(codbar, alternante)
    }
}