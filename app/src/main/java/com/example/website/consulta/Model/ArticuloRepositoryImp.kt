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

    override fun ObtenerArticulosXCobar(marvehi: Int, ini: Int, fin: Int): ArrayList<Articulo> {
        val procedure = "call AndroidSelectObtenerArticulosporCodBar(?,?,?)"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.prepareCall(procedure)
        prepareCall.setInt(1, marvehi)
        prepareCall.setInt(2, ini)
        prepareCall.setInt(3, fin)
        val resulSet = prepareCall.executeQuery()
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            if (resulSet.next()) {
                lstArticulo.add(Articulo().also {
                    it.idArticulo = resulSet.getInt("idArticulo")
                    it.cpdold = resulSet.getString("cpdold")
                    it.alternante = resulSet.getString("alternante")
                    it.descriArti = resulSet.getString("descriarti")
                    it.superarti = resulSet.getString("superarti")
                    it.marproarti = resulSet.getString("marproarti")
                    it.precioVenta = resulSet.getDouble("precioVenta")
                })
            }
            return lstArticulo
        } catch (ex: Exception) {
           throw ex
        }
    }

    override fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo> {
        val procedure = "call AdroidSelectObtenerArticulosporAlternante(?)"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.prepareCall(procedure)
        prepareCall.setString(1, alternante)
        val resulSet = prepareCall.executeQuery()
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            while (resulSet.next()) {
                lstArticulo.add(Articulo().also {
                    it.idArticulo = resulSet.getInt("idArticulo")
                    it.cpdold = resulSet.getString("cpdold")
                    it.alternante = resulSet.getString("alternante")
                    it.descriArti = resulSet.getString("descriarti")
                    it.superarti = resulSet.getString("superarti")
                    it.marproarti = resulSet.getString("marproarti")
                    it.precioVenta = resulSet.getDouble("precioVenta")
                })
            }
            return lstArticulo
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun obtenerArticuloXIdArticulo(idArticulo: Int): Articulo? {
        val procedure = "call AndroidObtenerArticuloporidArticulo(?)"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.prepareCall(procedure)
        prepareCall.setInt(1, idArticulo)
        val resulSet = prepareCall.executeQuery()
        try {
            if (resulSet.next()) {
                return Articulo().also {
                    it.idArticulo = resulSet.getInt("idArticulo")
                    it.vehimarc = resulSet.getString("vehimarc")
                    it.marvehi = resulSet.getInt("marvehi")
                    it.codprod = resulSet.getInt("codprod")
                    it.patronarti = resulSet.getInt("patronarti")
                    it.idUnimed = resulSet.getInt("idUnimed")
                    it.cpdnew = resulSet.getString("cpdnew")
                    it.cpdold = resulSet.getString("cpdold")
                    it.codbar = resulSet.getString("codbar")
                    it.alternante = resulSet.getString("alternante")
                    it.descriArti = resulSet.getString("descriarti")
                    it.superarti = resulSet.getString("superarti")
                    it.marproarti = resulSet.getString("marproarti")
                    it.unimed = resulSet.getString("unimed")
                    it.campar = resulSet.getInt("campar")
                    it.precioVenta = resulSet.getDouble("precioVenta")
                }
            }
            return null
        } catch (ex: Exception) {
            throw ex
        }
    }
}