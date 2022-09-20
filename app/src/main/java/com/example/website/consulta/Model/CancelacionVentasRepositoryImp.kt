package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.*
import java.lang.Exception
import java.sql.Connection
import java.sql.Types

class CancelacionVentasRepositoryImp: ICancelacionDocumentoRepository {

    override fun listarPrefactura(preFactura: FacturaCabTo): List<FacturaCabTo> {
        val procedure = "call AndroidObtenerPreFacturaporcodAlmCaja(?,?)"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.prepareCall(procedure)
        prepareCall.setInt("@al31codalm", preFactura.idTienda)
        prepareCall.setInt("@al31numcaj", preFactura.nroCaja)
        val resulSet = prepareCall.executeQuery()
        val lstPrefactura: ArrayList<FacturaCabTo> = ArrayList()
        try {
            while (resulSet.next()) {
                lstPrefactura.add(FacturaCabTo().also {
                    it.idPreFactura = resulSet.getInt("idfactucab")
                    it.tipoDoc = TIPO_DOCUMENTO.fromInt(resulSet.getInt("al31tipdoc"))
                    it.nroCaja = resulSet.getInt("al31numcaj")
                    it.serDoc = resulSet.getString("al31serdoc")
                    it.numDoc = resulSet.getInt("al31numdoc")
                    it.nombres = resulSet.getString("al31nomcli")
                    it.nroDocIdenti = resulSet.getString("al31numruc")
                    it.conpag = FORMA_DE_PAGO.fromInt(resulSet.getInt("al31conpag"))
                    it.idMoneda = resulSet.getInt("al31moneda")
                    it.valbru = resulSet.getDouble("al31valbru")
                    it.idTienda = resulSet.getInt("al31codalm")
                    it.fectra = resulSet.getString("al31fectra")
                    it.sergui = resulSet.getString("al31sergui1")
                    it.sergui2 = resulSet.getString("al31sergui2")
                    it.direccion = resulSet.getString("al31dircli")
                    it.taller = resulSet.getString("al31Taller")
                    it.idTipoDocIdenti = resulSet.getInt("IDTIPIDENTIDAD")
                })
            }
            return lstPrefactura
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun verificaFacturaBoletaporNro(preFactura: FacturaCabTo, connection: Connection): Boolean {
        val procedure = "call AndroidVerificaFacturaBoletaporNro(?,?,?,?,?)"
        try {
            val prepareCall = connection.prepareCall(procedure)
            prepareCall.setInt("@al31codalm", preFactura.idTienda)
            prepareCall.setInt("@al31tipdoc", preFactura.tipoDoc.id)
            prepareCall.setString("@al31serdoc", preFactura.serDoc)
            prepareCall.setInt("@al31numfac", preFactura.numFac)
            prepareCall.registerOutParameter("@flag", Types.BOOLEAN)
            prepareCall.executeUpdate()
            return prepareCall.getBoolean(5)
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun actualizaPreFacturaCabFacturacion(preFactura: FacturaCabTo, connection: Connection): Boolean {
        val procedure = "call AndroidUpdateActualizaPreFacturaporFacturacion(?,?,?)"
        try {
            val prepareCall = connection.prepareCall(procedure)
            prepareCall.setInt("@idfactucab", preFactura.idPreFactura)
            prepareCall.setInt("@al31numfac", preFactura.numFac)
            prepareCall.setInt("@idUs", preFactura.idUs)
            return prepareCall.executeUpdate() > 0
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun actualizaPreFacturaDetFacturacion(preFactura: FacturaCabTo, connection: Connection): Boolean {
        val procedure = "call AndroidUpdateActualizaPreFacturaDetalleporFacturacion(?,?,?)"
        try {
            val prepareCall = connection.prepareCall(procedure)
            prepareCall.setInt("@idfactdet", preFactura.idPreFactura)
            prepareCall.setInt("@al32numfac", preFactura.numFac)
            prepareCall.setInt("@idUs", preFactura.idUs)
            return prepareCall.executeUpdate() > 0
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun actualizaAlmacendeFacturaCompras(preFacturaDet: FacturaDetTo, connection: Connection): Boolean {
        val procedure = "call dsp_AndroidActualizarAlmacenFacturacion(?,?,?,?)"
        try {
            val prepareCall = connection.prepareCall(procedure)
            if (preFacturaDet.al32codAlm == 91) preFacturaDet.al32codAlm = 9
            prepareCall.setInt("@IdArticulo", preFacturaDet.al32idarticulo)
            prepareCall.setInt("@IdTienda", preFacturaDet.al32codAlm)
            prepareCall.setInt("@idUs", preFacturaDet.idUs)
            prepareCall.setInt("@Saldo", preFacturaDet.al32Totcan)
            return prepareCall.executeUpdate() > 0
        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun obtenerPreFacturaDet(idPreFacturaCab: Int): List<FacturaDetTo> {
        val procedure = "call dsp_AndroidObtenerPrefacturaDetXIdPrefacturaCab(?)"
        val dbConnection = ConnectionDB.Conexion()
        val prepareCall = dbConnection.prepareCall(procedure)
        prepareCall.setInt("@IdPrefacturaCab", idPreFacturaCab)
        val resulSet = prepareCall.executeQuery()
        val lstPrefactura: ArrayList<FacturaDetTo> = ArrayList()
        try {
            while (resulSet.next()) {
                lstPrefactura.add(FacturaDetTo().also {
                    it.idfactdet = resulSet.getInt("idfactdet")
                    it.al32codAlm = resulSet.getInt("al32codAlm")
                    it.al32numCaj = resulSet.getInt("al32numCaj")
                    it.al32tipDoc = TIPO_DOCUMENTO.fromInt(resulSet.getInt("al32tipDoc"))
                    it.al32serDoc = resulSet.getString("al32serDoc")
                    it.al32numDoc = resulSet.getInt("al32numDoc")
                    it.al32numfac = resulSet.getInt("al32numfac")
                    it.al33numSec = resulSet.getInt("al33numSec")
                    it.al32fecTra = resulSet.getString("al32fecTra")
                    it.AL32flag = resulSet.getString("AL32flag")
                    it.al32idarticulo = resulSet.getInt("al32idarticulo")
                    it.al32Totcan = resulSet.getInt("al32Totcan")
                    it.al32Preuni = resulSet.getDouble("al32Preuni")
                    it.al32pretot = resulSet.getDouble("al32pretot")
                })
            }
            return lstPrefactura
        } catch (ex: Exception) {
            throw ex
        }
    }
}