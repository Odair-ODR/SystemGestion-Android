package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.*
import java.lang.Exception

class MultipleRepository {
    fun listaCuentasBancarias(): List<CuentasBancariasTo> {
        val sentencia = "SELECT * FROM CUENTASBANCARIAS"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.createStatement()
        val resulSet = prepareCall.executeQuery(sentencia)
        val lstCuentasBancarias: ArrayList<CuentasBancariasTo> = ArrayList()
        try {
            while (resulSet.next()) {
                lstCuentasBancarias.add(CuentasBancariasTo().also {
                    it.nombreBanco = resulSet.getString("idfactucab")
                    it.nroCuetaCorriente = resulSet.getString("")
                    it.nroCuentaCCI = resulSet.getString("")
                })
            }
            return lstCuentasBancarias
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun obtenerTiposDeCambio(): List<TipoCambioTo> {
        val sentencia = "SELECT idmoneda, descripcion, siglas, tipocambio, estado FROM Tipo_cambio"
        val dbConnection = ConnectionDB.Conexion()
        val st = dbConnection.createStatement()
        val resulSet = st.executeQuery(sentencia)
        val lstTipoCambio: ArrayList<TipoCambioTo> = ArrayList()
        try {
            while (resulSet.next()) {
                lstTipoCambio.add(TipoCambioTo().also {
                    it.idMoneda = resulSet.getInt("idmoneda")
                    it.descripcion = resulSet.getString("descripcion")
                    it.siglas = resulSet.getString("siglas")
                    it.tipoCambio = resulSet.getDouble("tipocambio")
                    it.estado = resulSet.getInt("estado")
                })
            }
            return lstTipoCambio
        } catch (ex: Exception) {
            throw ex
        }
    }
}