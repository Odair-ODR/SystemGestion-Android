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
        val articuloLiveData = MutableLiveData<List<Articulo>>()
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            if (1 > 0) {
                lstArticulo.add(Articulo().also {
                    it.codbar = "02.30"
                    it.alternante = "092-01"
                    it.descriArti = "motores de viela"
                    it.totSaldo = 90
                })
            }
            articuloLiveData.value = lstArticulo
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return articuloLiveData
    }

    override fun ObtenerArticulosFactura(codbar: String, alternante: String): ArrayList<Articulo> {
        //> val procedure = ""
        //> val dbConnection = ConnectionDB.Conexion()
        //> val prepareCall = dbConnection.prepareCall(procedure)
        //> prepareCall.setString(1, codbar)
        //> prepareCall.setString(2, alternante)
        //> val resulSet = prepareCall.executeQuery()
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            if (1 > 0) {
                lstArticulo.add(Articulo().also {
                    it.codbar = "02.30"
                    it.alternante = "092-01"
                    it.descriArti = "motores de viela"
                    it.totSaldo = 90
                })
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
        return lstArticulo
    }
}