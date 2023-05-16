package com.example.website.consulta.Model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.dummy.Tienda
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Connection
import java.sql.ResultSet
import java.sql.Types
import kotlin.Exception
import kotlin.collections.ArrayList

class NVentasRepositoryImp : INVentasRepository {

    private var entidadToLive = MutableLiveData<EntidadTo?>()

    override fun ObtenerTiendas(): ArrayList<Tienda> {
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "call ObtenerTiendasHabilitadas"
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
        } finally {
            connection.close()
        }
    }

    override fun ObtenerTipoDocumento(): ArrayList<String> {
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "call ObtenerTiendasHabilitadas()"
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
        } finally {
            connection.close()
        }
    }

    override fun ObtenerMoneda(): ArrayList<Moneda> {
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "call selectObtenerMonedaPedido"
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
        } finally {
            connection.close()
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

    override fun registrarPreFacturaCabDet(
        facturaTo: FacturaCabTo,
        lstFacturaDetTo: ArrayList<FacturaDetTo>
    ): Boolean {
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
            if(!insertarCuotasCredito(facturaTo, con)){
                con.rollback()
                return false
            }
            if (!actualizarNroDocumentoPrefactura(con, facturaTo)) {
                con.rollback()
                return false
            }
            con.commit()
            return true
        } catch (ex: Exception) {
            con.rollback()
            throw ex
        } finally {
            con.autoCommit = true
            con.close()
        }
    }

    private fun registrarPreFacturaCab(con: Connection, facturaTo: FacturaCabTo): Boolean {
        val procedure =
            "call usp_AndroidInsertAdicionaPreFactura (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        val correlativo =
            obtenerCorrelativoPrefactura(facturaTo, con)
                ?: return false
        val st = con.prepareCall(procedure)
        facturaTo.serDoc = correlativo.serie
        facturaTo.numDoc = correlativo.numero
        st.registerOutParameter("@idfactucab", Types.INTEGER)
        st.setObject("@al31codalm", facturaTo.idTienda)
        st.setObject("@al31numcaj", facturaTo.nroCaja)
        st.setObject("@al31tipdoc", facturaTo.tipoDoc.id)
        st.setObject("@al31serdoc", facturaTo.serDoc)
        st.setObject("@al31numdoc", facturaTo.numDoc)
        st.setObject("@al31numfac", facturaTo.numFac)
        st.setObject("@al31fectra", facturaTo.fectra)
        st.setObject("@al31numruc", facturaTo.nroDocIdenti)
        st.setObject("@al31nomcli", facturaTo.nombres)
        st.setObject("@al31dircli", facturaTo.direccion)
        st.setObject("@al31placa", facturaTo.placa)
        st.setObject("@al31codven", facturaTo.idUs)
        st.setObject("@al31tipgui1", facturaTo.tipgui)
        st.setObject("@al31sergui1", facturaTo.sergui)
        st.setObject("@al31numgui1", facturaTo.numgui)
        st.setObject("@al31fecgui1", facturaTo.fecgui)
        st.setObject("@al31tipgui2", facturaTo.tipgui2)
        st.setObject("@al31sergui2", facturaTo.sergui2)
        st.setObject("@al31numgui2", facturaTo.numgui2)
        st.setObject("@al31fecgui2", facturaTo.fecgui2)
        st.setObject("@al31conpag", facturaTo.conpag.id)
        st.setObject("@al31valven", facturaTo.valven)
        st.setObject("@al31valigv", facturaTo.valigv)
        st.setObject("@al31valbru", facturaTo.valbru)
        st.setObject("@al31numlet", facturaTo.numlet)
        st.setObject("@al31moneda", facturaTo.idMoneda)
        st.setObject("@al31serper", facturaTo.serper)
        st.setObject("@al31numper", facturaTo.numper)
        st.setObject("@al31fecper", facturaTo.fecper)
        st.setObject("@al31totper", facturaTo.totper)
        st.setObject("@al31tipcam", facturaTo.tipCam)
        st.setObject("@taller", facturaTo.taller)
        st.setObject("@idUs", facturaTo.idUs)
        st.setObject("@tipoOperacion", facturaTo.idTipOperacion)
        st.setObject("@tipoDocIdentidad", facturaTo.idTipoDocIdenti)
        st.setObject("@opergratuita", facturaTo.operaGratuita)
        st.setObject("@o_fechaInicio", facturaTo.oFechaInicio)
        st.setObject("@o_fechaFin", facturaTo.oFechaFin)
        st.setObject("@op_descuento", facturaTo.oDescuento)
        st.setObject("@afectaIGV", facturaTo.afectaIGV)
        st.setObject("@al31totanticipo", facturaTo.al31TotAnticipo)
        st.setObject("@al31Anticipo", facturaTo.al31Anticipo)
        st.setObject("@al31AplicaAnticipo", facturaTo.al31AplicAnticipo)
        val result = st.executeUpdate()
        facturaTo.idPreFactura = st.getInt(1)
        return result > 0
    }

    private fun registrarPreFacturaDet(
        con: Connection,
        facturaDetTo: ArrayList<FacturaDetTo>,
        facturaTo: FacturaCabTo
    ): Boolean {
        val procedure = "call usp_AndroidInsertPreFacturaDetalle " +
                "(?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?,?)"
        if (facturaDetTo.count() == 0)
            return false
        val st = con.prepareCall(procedure)
        facturaDetTo.forEach { it ->
            st.setObject("@idfactdet", facturaTo.idPreFactura)
            st.setObject("@al32codAlm", facturaTo.idTienda)
            st.setObject("@al32numCaj", facturaTo.nroCaja)
            st.setObject("@al32tipDoc", facturaTo.tipoDoc.id)
            st.setObject("@al32serDoc", facturaTo.serDoc)
            st.setObject("@al32numDoc", facturaTo.numDoc)
            st.setObject("@al32numfac", it.al32numfac)
            st.setObject("@al33numSec", it.al33numSec)
            st.setObject("@al32fecTra", facturaTo.fectra)
            st.setObject("@AL32flag", it.al32flag)
            st.setObject("@codigoUniversal", it.codigoUniversal)
            st.setObject("@al32idarticulo", it.al32idarticulo)
            st.setObject("@al32Vehimarc", it.al32Vehimarc)
            st.setObject("@al32Marvehi", it.al32Marvehi)
            st.setObject("@al32Codprod", it.al32Codprod)
            st.setObject("@al32Patronarti", it.al32Patronarti)
            st.setObject("@al32Superarti", it.al32Superarti)
            st.setObject("@al32Marproarti", it.al32Marproarti)
            st.setObject("@al32Alternante", it.al32Alternante)
            st.setObject("@al32Unimed", it.al32Unimed)
            st.setObject("@al32Campar", it.al32Campar)
            st.setObject("@al32DescriArti", it.al32DescriArti)
            st.setObject("@al32Codbar", it.al32Codbar)
            st.setObject("@al32Cpdnew", it.al32Cpdnew)
            st.setObject("@al32Cpdold", it.al32Cpdold)
            st.setObject("@al32Totcan", it.al32Totcan)
            st.setObject("@al32Preuni", it.al32Preuni)
            st.setObject("@al32peruan", it.al32peruan)
            st.setObject("@al32pretot", it.al32pretot)
            st.setObject("@al32tipdcm", it.al32tipdcm)
            st.setObject("@al32serdcm", it.al32serdcm)
            st.setObject("@al32numdcm", it.al32numdcm)
            st.setObject("@al32secdcm", it.al32secdcm)
            st.setObject("@al32fecdcm", it.al32fecdcm)
            st.setObject("@al32serabo", it.al32serabo)
            st.setObject("@al32notabo", it.al32notabo)
            st.setObject("@al32secabo", it.al32secabo)
            st.setObject("@al32fecabo", it.al32fecabo)
            st.setObject("@idUs", it.idUs)
            st.setObject("@al32glosa", it.al32glosa)
            st.setObject("@al32Anticipo", it.al32Anticipo)
            if (st.executeUpdate() <= 0)
                return false
        }
        return true
    }

    private fun obtenerCorrelativoPrefactura(
        facturaTo: FacturaCabTo,
        con: Connection
    ): CorrelativoTo? {
        try {
            val procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)"
            val st = con.prepareCall(procedure)
            st.setInt(1, facturaTo.idTienda)
            st.setInt(2, facturaTo.tipoDocPrefactura.id)
            st.setInt(3, facturaTo.nroCaja)
            val rs = st.executeQuery()
            if (rs.next()) {
                return CorrelativoTo().also {
                    it.serie = rs.getString("serie")
                    it.numero = rs.getInt("nro")
                    it.nro_reg = rs.getInt("nroreg")
                }
            }
            return null
        } catch (ex: Exception) {
            throw ex
        }
    }

    private fun actualizarNroDocumentoPrefactura(
        con: Connection,
        facturaTo: FacturaCabTo
    ): Boolean {
        try {
            val procedure = "call AndroidUpdateActualizaNroControlDocumentosCaja (?,?,?,?,?)"
            val st = con.prepareCall(procedure)
            st.setInt("@idtienda", facturaTo.idTienda)
            st.setInt("@caja", facturaTo.nroCaja)
            st.setInt("@iddocumento", facturaTo.tipoDocPrefactura.id)
            st.setString("@serie", facturaTo.serDoc)
            st.registerOutParameter("@nro", Types.INTEGER)
            return st.executeUpdate() > 0
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun obtenerIGV(): Double {
        val con = ConnectionDB.Conexion()
        try {
            val procedure = "call usp_AndroidObtenerIGV()"
            val st = con.prepareCall(procedure)
            val rs = st.executeQuery()
            return if (rs.next()) {
                rs.getDouble("descuento")
            } else return 0.0
        } catch (ex: Exception){
            throw ex
        }
        finally {
            con.close()
        }
    }

    private fun insertarCuotasCredito(facturaTo: FacturaCabTo, con: Connection): Boolean{
        try {
            if(facturaTo.conpag != FORMA_DE_PAGO.CREDITO)
                return  true
            val procedure = "call dsp_AndroidInsertarCuotasCredito (?,?,?,?,?,?,?,?,?,?;?,?,?)"
            val st = con.prepareCall(procedure)
            st.setInt("@IdFac", facturaTo.idPreFactura)
            st.setInt("@CodAlm", facturaTo.idTienda)
            st.setInt("@TipDoc", facturaTo.tipoDoc.id)
            st.setString("@SerDoc", facturaTo.serDoc)
            st.setInt("@NumDoc", facturaTo.numDoc)
            st.setString("@Fectra", facturaTo.fectra)
            st.setInt("@NroCuotas", 1)
            st.setDouble("@ValorCuota", facturaTo.valbru)
            st.setString("@FechaPago", facturaTo.oFechaFin.toString())
            st.setDouble("@TotaFactura", facturaTo.valbru)
            st.setString("@NumRuc", facturaTo.nroDocIdenti)
            st.setInt("@Flag", 0)
            return st.executeUpdate() > 0
        }
        catch (ex: Exception){
            throw  ex
        }
    }
}