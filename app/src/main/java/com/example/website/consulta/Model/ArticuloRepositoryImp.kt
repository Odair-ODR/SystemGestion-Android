package com.example.website.consulta.Model

import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.Articulo
import java.lang.Exception

class ArticuloRepositoryImp : IArticuloRepository {

    override fun ObtenerArticulosFactuera(codbar: String, alternante: String): MutableLiveData<List<Articulo>> {
        //> val procedure = ""
        //> val dbConnection = ConnectionDB.Conexion()
        //> val prepareCall = dbConnection.prepareCall(procedure)
        //> prepareCall.setString(1, codbar)
        //> prepareCall.setString(2, alternante)
        //> val resulSet = prepareCall.executeQuery()
        var articuloLiveData = MutableLiveData<List<Articulo>>()
        var lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            var articulo: Articulo? = null
            if (1>0) {
                articulo = Articulo()
                articulo.codbar = "02.30"
                articulo.alternante = "092-01"
                articulo.descriArti = "motores de viela"
                articulo.totSaldo = 90
                lstArticulo.add(articulo)
            }
            articuloLiveData.value = lstArticulo
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return articuloLiveData
    }
}