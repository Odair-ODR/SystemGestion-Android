package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo

class CodbarFragmentRepositoryImp : ICodbarFragmentRepository {

    override fun ObtenerArticuloXCodbar(codbar: String): ArrayList<Articulo> {
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "{call usp_AndroidSelectObtenerArticulosporCodBar (?)}"
            val callStatement = connection.prepareCall(procedure).also {
                it.setString(1, codbar)
            }
            val rs = callStatement.executeQuery()
            while (rs.next()) {
                lstArticulo.add(Articulo().also {
                    it.alternante = rs.getString("alternante")
                    it.campar = rs.getInt("campar")
                    it.cpdnew = rs.getString("cpdnew")
                    it.unimed = rs.getString("unimed")
                    it.motor = rs.getString("motor")
                    it.totSaldo = rs.getInt("totSaldo")
                    it.precioVenta = rs.getDouble("precioVenta")
                })
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            connection.close()
        }
        return lstArticulo
    }
}