package com.example.website.consulta.Model

import com.example.website.consulta.dummy.Tienda
import java.lang.Exception
import java.sql.ResultSet

class NVentasRepositoryImp : INVentasRepository {

    override fun ObtenerTiendas(): ArrayList<String> {
        try {
            val procedure = ""
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            val st: ResultSet = pc.executeQuery()
            val tiendas: ArrayList<String> = ArrayList()
            while (st.next()) {
                tiendas.add(
                    st.getInt("").toString()
                )
            }
            return tiendas
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun ObtenerTipoDocumento(): Array<String> {
        return arrayOf("")
    }
}