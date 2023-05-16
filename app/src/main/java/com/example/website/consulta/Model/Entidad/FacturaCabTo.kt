package com.example.website.consulta.Model.Entidad

import java.io.Serializable
import java.lang.StringBuilder

class FacturaCabTo {
    var al31AplicAnticipo: Boolean? = false
    var al31Anticipo: Boolean = false
    var al31TotAnticipo: Double = 0.0
    var afectaIGV: String = ""
    var numFac: Int = 0
    var idPreFactura: Int = 0
    var oDescuento: Int = 0
    var oFechaFin: Any? = null
    var oFechaInicio: Any? = null
    var operaGratuita: Int = 0
    var idTipoDocIdenti: Int = 0
    var idTipOperacion: String = ""
    var idUs: Int = 0
    var taller: Any? = null
    var tipCam: Double = 0.0
    var totper: Double = 0.0
    var fecper: Any? = null
    var numper: Int = 0
    var serper: String = ""
    var idMoneda: Int = 0
    var numlet: String = ""
    var valbru: Double = 0.0
    var valigv: Double = 0.0
    var valven: Double = 0.0
    var conpag: FORMA_DE_PAGO = FORMA_DE_PAGO.CONTADO
    var fecgui2: Any? = null
    var numgui2: Int = 0
    var sergui2: String = ""
    var tipgui2: Int = 0
    var fecgui: Any? = null
    var numgui: Int = 0
    var sergui: String = ""
    var tipgui: Int = 0
    var placa: String = "-"
    var direccion: String = ""
    var nombres: String = ""
    var nroDocIdenti: String = ""
    var fectra: String = ""
    var nroFactura: Int = 0
    var numDoc: Int = 0
    var serDoc: String = ""
    var tipoDoc: TIPO_DOCUMENTO = TIPO_DOCUMENTO.FACTURA
    var nroCaja:Int = 0
    var idTienda: Int = 0
    val tipoDocPrefactura: TIPO_DOCUMENTO = TIPO_DOCUMENTO.PRE_FACTURA
    var tipoDocIdentidadTo: TipoDocIdentidadTo? = null
    var codVen = ""
    var credito_d: String? = ""
    var observacion:String? = ""

    private val prefijoNombrePdf: String = when(tipoDocPrefactura) {
        TIPO_DOCUMENTO.FACTURA -> "F"
        TIPO_DOCUMENTO.BOLETA -> "B"
        TIPO_DOCUMENTO.NOTA_CREDITO -> "NC"
        TIPO_DOCUMENTO.PRE_FACTURA -> "F"
        else -> ""
    }

    val nombreArchivoPdf: String
        get() {
            val nombreArchivoPdf: String = StringBuilder()
                .append(prefijoNombrePdf)
                .append(serDoc)
                .append("-")
                .append(numDoc)
                .append(".pdf").toString()
            return nombreArchivoPdf
        }
}