package com.example.website.consulta.Model
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.dummy.Tienda

class NVentasObservable {
    private val nVentasRepository: INVentasRepository = NVentasRepositoryImp()
    private var articuloRespository: IArticuloRepository = ArticuloRepositoryImp()

    fun ObtnerTiendas(): ArrayList<String>{
        return  nVentasRepository.ObtenerTiendas()
    }

    fun ObtenerArticuloXIdArticulo(idArticulo: Int): Articulo?{
        return articuloRespository.ObtenerArticuloXIdArticulo(idArticulo)
    }
}
