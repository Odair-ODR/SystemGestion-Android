package com.example.website.consulta.Model.Entidad

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.Serializable
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EntidadTo(entidadToJson: JsonObject?, documentoIdentidad: TIPO_DOCUMENTO_IDENTIDAD = TIPO_DOCUMENTO_IDENTIDAD.NONE) : Serializable {

    //> RUC
    var ruc: String? = ""
    var razonSocial: String? = ""
    var nombreComercial: String? = ""
    var tipo: String? = ""
    var estado: String? = ""
    var condicion: String = ""
    var direccion: String = ""
    var departamento: String = ""
    var provincia: String = ""
    var distrito: String = ""
    var fechaInscripcion: String = ""
    var sistEmision: String = ""
    var sistContabilidad: String = ""
    var actExterior: String = ""
    var ubigeo: String = ""
    var capital: String = ""

    //> DNI
    var dni: String? = ""
    var nombres: String? = ""
    var apellidoPaterno: String? = ""
    var apellidMaterno: String? = ""
    var codVerifica: String? = ""

    val documentoIdentidad: TIPO_DOCUMENTO_IDENTIDAD
    val stringEmpty = ""

    init {
        this.documentoIdentidad = documentoIdentidad
        try {
            if (documentoIdentidad == TIPO_DOCUMENTO_IDENTIDAD.RUC) {
                ruc = obtenerValorJson(entidadToJson!!.get(RUC))
                razonSocial = obtenerValorJson(entidadToJson.get(RAZON_SOCIAL))
                nombreComercial = obtenerValorJson(entidadToJson.get(NOMBRE_COMERCIAL))
                tipo = obtenerValorJson(entidadToJson.get(TIPO))
                estado = obtenerValorJson(entidadToJson.get(ESTADO))
                condicion = obtenerValorJson(entidadToJson.get(CONDICION))
                direccion = obtenerValorJson(entidadToJson.get(DIRECCION))
                departamento = obtenerValorJson(entidadToJson.get(DEPARTAMENTO))
                provincia = obtenerValorJson(entidadToJson.get(PROVINCIA))
                distrito = obtenerValorJson(entidadToJson.get(DISTRITO))
                fechaInscripcion = getFormatDate(entidadToJson.get(FECHA_INSCRIPCION))
                sistEmision = obtenerValorJson(entidadToJson.get(SIST_EMISION))
                sistContabilidad = obtenerValorJson(entidadToJson.get(SIST_CONTABILIDAD))
                actExterior = obtenerValorJson(entidadToJson.get(ACT_EXTERIOR))
                ubigeo = obtenerValorJson(entidadToJson.get(UBIGEO))
                capital = obtenerValorJson(entidadToJson.get(CAPITAL))
            } else if (documentoIdentidad == TIPO_DOCUMENTO_IDENTIDAD.DNI) {
                dni = obtenerValorJson(entidadToJson!!.get(DNI))
                nombres = obtenerValorJson(entidadToJson.get(NOMBRES))
                apellidMaterno = obtenerValorJson(entidadToJson.get(APELLIDO_MATERNO))
                apellidoPaterno = obtenerValorJson(entidadToJson.get(APELLIDO_PATERNO))
                codVerifica = obtenerValorJson(entidadToJson.get(COD_VERIFICA))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        //> RUC
        private var RUC = "ruc"
        private var RAZON_SOCIAL = "razonSocial"
        private var NOMBRE_COMERCIAL = "nombreComercial"
        private var TIPO = "tipo"
        private var ESTADO = "estado"
        private var CONDICION = "condicion"
        private var DIRECCION = "direccion"
        private var DEPARTAMENTO = "departamento"
        private var PROVINCIA = "provincia"
        private var DISTRITO = "distrito"
        private var FECHA_INSCRIPCION = "fechaInscripcion"
        private var SIST_EMISION = "sistEmsion"
        private var SIST_CONTABILIDAD = "sistContabilidad"
        private var ACT_EXTERIOR = "actExterior"
        private var UBIGEO = "ubigeo"
        private var CAPITAL = "capital"

        //> DNI
        private var DNI = "dni"
        private var NOMBRES = "nombres"
        private var APELLIDO_PATERNO = "apellidoPaterno"
        private var APELLIDO_MATERNO = "apellidoMaterno"
        private var COD_VERIFICA = "codVerifica"
    }

    private fun getFormatDate(element: JsonElement): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")
        try {
            val date: String = if (!element.isJsonNull) element.asString else stringEmpty
            if(date.length > 0){
                val parsedDateFormat = format.parse(date)
                val cal = Calendar.getInstance()
                cal.time = parsedDateFormat
                return dateFormat.format(cal.time)
            }
            else return stringEmpty
        } catch (e: ParseException) {
            e.printStackTrace()
            return stringEmpty
        }
    }

    private fun obtenerValorJson(element: JsonElement?): String {
        if (element != null) {
            return if (!element.isJsonNull) element.asString else stringEmpty
        }
        return stringEmpty
    }
}