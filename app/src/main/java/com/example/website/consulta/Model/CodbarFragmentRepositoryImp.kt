package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

class CodbarFragmentRepositoryImp : ICodbarFragmentRepository {

    override fun ObtenerArticuloXCodbar(codbar: String): ArrayList<Articulo> {
        val procedure = "{call usp_AndroidSelectObtenerArticulosporCodBar (?)}"
        val connection = ConnectionDB.Conexion()
        val callStatement = connection.prepareCall(procedure).also {
            it.setString(1, codbar)
        }
        val rs = callStatement.executeQuery()
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        var articulo: Articulo
        while (rs.next()){
            articulo = Articulo()
            articulo.alternante = rs.getString("alternante")
            articulo.campar = rs.getInt("campar")
            articulo.cpdnew = rs.getString("cpdnew")
            articulo.unimed = rs.getString("unimed")
            articulo.motor = rs.getString("motor")
            articulo.totSaldo = rs.getInt("totSaldo")
            lstArticulo.add(articulo)
        }
        return lstArticulo
    }
}