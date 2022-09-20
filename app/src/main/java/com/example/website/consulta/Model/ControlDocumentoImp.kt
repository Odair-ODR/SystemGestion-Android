package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.CorrelativoTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import java.sql.Connection
import java.sql.Types

class ControlDocumentoImp : IControlDocumento {
    override fun obtenerCorrelativoDocumento(
        facturaCabTo: FacturaCabTo
    ): CorrelativoTo? {
        try {
            val procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)"
            val con = ConnectionDB.Conexion()
            val st = con.prepareCall(procedure)
            st.setInt(1, facturaCabTo.idTienda)
            st.setInt(2, facturaCabTo.tipoDoc.id)
            st.setInt(3, facturaCabTo.nroCaja)
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

    override fun obtenerCorrelativoDocumento(
        facturaCabTo: FacturaCabTo,
        cn: Connection
    ): CorrelativoTo? {
        try {
            val procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)"
            val st = cn.prepareCall(procedure)
            st.setInt(1, facturaCabTo.idTienda)
            st.setInt(2, facturaCabTo.tipoDoc.id)
            st.setInt(3, facturaCabTo.nroCaja)
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

    override fun actualizarNroDocumentoCaja(facturaTo: FacturaCabTo, cn: Connection): Boolean {
        try {
            val procedure = "call AndroidUpdateActualizaNroControlDocumentosCaja (?,?,?,?,?,?)"
            val st = cn.prepareCall(procedure)
            st.setInt("@idtienda", facturaTo.idTienda)
            st.setInt("@caja", facturaTo.nroCaja)
            st.setInt("@iddocumento", facturaTo.tipoDoc.id)
            st.setString("@serie", facturaTo.serDoc)
            st.setInt("@idUs", facturaTo.idUs)
            st.registerOutParameter("@nro", Types.INTEGER)
            return st.executeUpdate() > 0
        } catch (ex: Exception) {
            throw ex
        }
    }
}