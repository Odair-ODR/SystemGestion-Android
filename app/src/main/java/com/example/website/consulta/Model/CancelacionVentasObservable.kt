package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import java.sql.Connection

class CancelacionVentasObservable {
    private val impCancelacion: ICancelacionDocumentoRepository = CancelacionVentasRepositoryImp()
    private lateinit var verificarCancelacionFacturacion: IVerificarCancelacionFacturacion
    private val impControlDocuento: IControlDocumento = ControlDocumentoImp()

    fun listarPrefactura(preFactura: FacturaCabTo): List<FacturaCabTo> {
        return impCancelacion.listarPrefactura(preFactura)
    }

    fun grabarCancelacionDocumentosFacturacion(
        preFacturaCab: FacturaCabTo,
        lstPreFacturaDet: List<FacturaDetTo>,
        verificarCorrelativo: IVerificarCancelacionFacturacion
    ): Boolean {
        val bdConnection = ConnectionDB.Conexion()
        verificarCancelacionFacturacion = verificarCorrelativo
        try {
            bdConnection.autoCommit = false
            if (impCancelacion.verificaFacturaBoletaporNro(preFacturaCab, bdConnection)) {
                val correlativoTo =
                    impControlDocuento.obtenerCorrelativoDocumento(preFacturaCab, bdConnection)
                if (correlativoTo == null) {
                    bdConnection.rollback()
                    return false
                }
                preFacturaCab.numFac = correlativoTo.numero
            }
            if (!impCancelacion.actualizaPreFacturaCabFacturacion(preFacturaCab, bdConnection)) {
                bdConnection.rollback()
                verificarCancelacionFacturacion.error()
                return false
            }
            if (!impCancelacion.actualizaPreFacturaDetFacturacion(preFacturaCab, bdConnection)) {
                bdConnection.rollback()
                verificarCancelacionFacturacion.error()
                return false
            }
            if (!actualizaAlmacendeFacturaCompras(lstPreFacturaDet, bdConnection)) {
                bdConnection.rollback()
                verificarCancelacionFacturacion.error()
                return false
            }
            if (!impControlDocuento.actualizarNroDocumentoCaja(preFacturaCab, bdConnection)) {
                bdConnection.rollback()
                verificarCancelacionFacturacion.error()
                return false
            }
            bdConnection.commit()
            verificarCancelacionFacturacion.success()
            return true
        } catch (ex: Exception) {
            bdConnection.rollback()
            throw ex
        } finally {
            bdConnection.autoCommit = true
        }
    }

    private fun actualizaAlmacendeFacturaCompras(lstPreFacturaDet: List<FacturaDetTo>, cn: Connection): Boolean {
        var result = false
        lstPreFacturaDet.forEach { item ->
            if (item.AL32flag == "*") {
                if (!impCancelacion.actualizaAlmacendeFacturaCompras(item, cn)) {
                    return false
                }
            }
            result = true
        }
        return result
    }

    fun obtenerPreFacturaDet(idPreFacturaCab: Int): List<FacturaDetTo> {
        return impCancelacion.obtenerPreFacturaDet(idPreFacturaCab)
    }
}