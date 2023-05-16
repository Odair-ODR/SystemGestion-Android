package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo
import java.lang.Exception

class AlternanteFragmentRepositoryImp : IAlternanteFragmentRepository {

    override fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo> {
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "{call AdroidSelectObtenerArticulosporAlternante (?)}"
            val pc = connection.prepareCall(procedure)
            pc.setString(1, alternante)
            val rs = pc.executeQuery()
            var articulo: Articulo
            while (rs.next()) {
                articulo = Articulo()
                articulo.alternante = rs.getString("alternante")
                articulo.codbar = rs.getString("codbar")
                articulo.cpdnew = rs.getString("cpdnew")
                articulo.motor = rs.getString("Motor")
                articulo.totSaldo = rs.getString("totsaldo").toInt()
                articulo.precioVenta = rs.getDouble("precioVenta")
                lstArticulo.add(articulo)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            connection.close()
        }
        return lstArticulo
    }
}