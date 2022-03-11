package com.example.website.consulta.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.dummy.Tienda
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types
import java.util.*
import kotlin.collections.ArrayList

class NVentasRepositoryImp : INVentasRepository {

    private var entidadToLive = MutableLiveData<EntidadTo?>()

    override fun ObtenerTiendas(): ArrayList<Tienda> {
        try {
            val procedure = "call ObtenerTiendasHabilitadas"
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            val st: ResultSet = pc.executeQuery()
            val tiendas: ArrayList<Tienda> = ArrayList()
            var tienda: Tienda
            while (st.next()) {
                tienda = Tienda()
                tienda.idTienda = st.getInt("idTienda")
                tienda.nomTienda = st.getString("nomTienda")
                tiendas.add(tienda)
            }
            return tiendas
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun ObtenerTipoDocumento(): ArrayList<String> {
        try {
            val procedure = "call ObtenerTiendasHabilitadas()"
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            val st: ResultSet = pc.executeQuery()
            val tiendas: ArrayList<String> = ArrayList()
            while (st.next()) {
                tiendas.add(
                    st.getInt("nomTienda").toString()
                )
            }
            return tiendas
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun ObtenerMoneda(): ArrayList<Moneda> {
        try {
            val procedure = "call selectObtenerMonedaPedido"
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            val st: ResultSet = pc.executeQuery()
            val tiendas: ArrayList<Moneda> = ArrayList()
            var moneda: Moneda
            while (st.next()) {
                moneda = Moneda()
                moneda.idMoneda = st.getInt("idmoneda")
                moneda.descMoneda = st.getString("descripcion")
                tiendas.add(moneda)
            }
            return tiendas
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun llamarEntidadToXRucApi(ruc: String) {
        val apiAdapter = ApiAdapter()
        val apiService = apiAdapter.getClientService()
        val call = apiService.obtenerEntidadXRuc(ruc)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.message?.let { Log.e("ERROR: ", it) }
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val entidadToJsonObject = response.body()?.getAsJsonObject()
                entidadToLive.value = EntidadTo(entidadToJsonObject, TIPO_DOCUMENTO_IDENTIDAD.RUC)
            }
        })
    }

    override fun llamarEntidadToXDniApi(dni: String) {
        val apiAdapter = ApiAdapter()
        val apiService = apiAdapter.getClientService()
        val call = apiService.obtenerEntidadXDni(dni)
        call.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                t.message?.let { Log.e("ERROR: ", it) }
                t.stackTrace
            }

            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val entidadToJsonObject = response.body()?.getAsJsonObject()
                entidadToLive.value = EntidadTo(entidadToJsonObject, TIPO_DOCUMENTO_IDENTIDAD.DNI)
            }
        })
    }

    override fun obtenerEntidadToApi(): MutableLiveData<EntidadTo?> {
        return entidadToLive
    }

    override fun registrarPreFacturaCabDet(facturaTo: FacturaCabTo, lstFacturaDetTo: ArrayList<FacturaDetTo>): Boolean {
        val con = ConnectionDB.Conexion()
        try {
            con.autoCommit = false
            if (!registrarPreFacturaCab(con, facturaTo)) {
                con.rollback()
                return false
            }
            if (!registrarPreFacturaDet(con, lstFacturaDetTo, facturaTo)) {
                con.rollback()
                return false
            }
            con.commit()
            con.autoCommit = true
            return true
        } catch (ex: Exception) {
            con.rollback()
            throw ex
        }
    }

    private fun registrarPreFacturaCab(con: Connection, facturaTo: FacturaCabTo): Boolean {
        val procedure = "call usp_AndroidInsertAdicionaPreFactura (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        val correlativo = obtenerCorrelativo(con)
        val igv = obtenerIGV()
        val st = con.prepareCall(procedure)
        st.registerOutParameter(1, Types.INTEGER) //> output id
        val result = st.executeUpdate()
        facturaTo.idPreFactura = st.getInt(1)
        return result > 0
    }

    private fun registrarPreFacturaDet(con: Connection, facturaDetTo: ArrayList<FacturaDetTo>, facturaTo: FacturaCabTo): Boolean {
        val procedure = "call usp_AndroidInsertPreFacturaDetalle " +
                "(?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?)"

        val st = con.prepareCall(procedure)
        val dt = Date()
        st.setObject(1, facturaTo.idPreFactura) //> id
        st.setObject(2, facturaTo.idTienda) //> almacen
        st.setObject(3, facturaTo.nroCaja) //> numumero caja
        st.setObject(4, facturaTo.tipoDoc) //> tipdoc
        st.setObject(5, facturaTo.serDoc) //> serdoc
        st.setObject(6, facturaTo.numDoc) //> numdoc
        st.setObject(7, 1) //> numfac
        st.setObject(8, 1)
        st.setObject(9, dt.date) //> fectra
        st.setObject(10, "") //> flag
        st.setObject(11, 30976) //> idArticulo
        st.setObject(12, "NI") //> vehimarc
        st.setObject(13, 1) //> marvehi
        st.setObject(14, 300) //> codprod
        st.setObject(15, 262) //> patronarti
        st.setObject(16, "S") //> superarti
        st.setObject(17, "PK") //> marproarti
        st.setObject(18, "FS80262-S-NPK") //> alternante
        st.setObject(19, "JG") //> unimed
        st.setObject(20, 1) //> campar
        st.setObject(21, "JGO. EMP. MOTOR") //> descriarti
        st.setObject(22, "01.9333") //> codbar
        st.setObject(23, "01300.262.SPK") //> cpdnew
        st.setObject(24, "NI300.262.SPK") //> cpdold
        st.setObject(25, 1) //> totcan
        st.setObject(26, 1.00) //> preini
        st.setObject(27, 0.00) //> peruan
        st.setObject(28, 1.00) //> pretot
        st.setObject(29, 0) //> tipdcm
        st.setObject(30, "") //> serdcm
        st.setObject(31, 0) //> numdcm
        st.setObject(32, 0) //> secdcm
        st.setObject(33, null) //> fecdcm
        st.setObject(34, "") //> serabo
        st.setObject(35, 0) //> notabo,
        st.setObject(36, 0) //> secabo
        st.setObject(37, null) //> fecabo
        st.setObject(38, 105) //> idus
        st.setObject(39, null) //> glosa
        return st.executeUpdate() > 0
    }

    private fun obtenerCorrelativo(con: Connection): Array<Any> {
        val procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)"
        val idTipoDocumento = 24
        val st = con.prepareCall(procedure)
        st.setInt(1, 3) //> idTienda
        st.setInt(2, idTipoDocumento) //> idDocumento
        st.setInt(3, 1) //> caja
        val rs = st.executeQuery()
        var serie = ""
        var nro = 0
        var nroReg = 0
        if (rs.next()) {
            serie = rs.getString("serie")
            nro = rs.getString("nro").toInt()
            nroReg = rs.getString("nroreg").toInt()
        }
        return arrayOf(serie, nro, nroReg)
    }

    private fun obtenerIGV(): Double {
        val con = ConnectionDB.Conexion()
        val procedure = "call usp_AndroidObtenerIGV ()"
        val st = con.prepareCall(procedure)
        val rs = st.executeQuery()
        return if (rs.next()) {
            rs.getDouble("descuento")
        } else return 0.0
    }
}