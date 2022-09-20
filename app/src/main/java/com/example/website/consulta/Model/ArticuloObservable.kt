package com.example.website.consulta.Model

import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.Articulo

class ArticuloObservable{
    //> Conexión directa con el repositorio
    //> Conexión directa con ViewModel
    //> Estas clases va tener una conexión directa con el respositorio y con el viewModel
    private var articuloRespository: IArticuloRepository = ArticuloRepositoryImp()

    fun obtenerArticulosFactura(codbar: String, alternante: String): MutableLiveData<List<Articulo>>{
        return articuloRespository.ObtenerArticulosFactuera(codbar, alternante)
    }

    fun ObtenerArticulosXCobar(marvehi: Int, ini: Int, fin: Int): ArrayList<Articulo>{
        return articuloRespository.ObtenerArticulosXCobar(marvehi, ini, fin)
    }

    fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>{
        return articuloRespository.ObtenerArticulosXAlternante(alternante)
    }

    fun obtenerArticuloXIdArticulo(idArticulo: Int): Articulo? {
        return articuloRespository.obtenerArticuloXIdArticulo(idArticulo)
    }
}