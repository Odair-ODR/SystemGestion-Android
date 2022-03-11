package com.example.website.consulta.Helpers

import com.google.gson.JsonElement
import java.text.SimpleDateFormat
import java.util.*

class UtilsMethod {
    companion object {
        private val formatDateShortSql = "yyyy-MM-dd"
        fun getSqlDateShort(): String {
            val fechaEspanol = SimpleDateFormat(formatDateShortSql)
            return fechaEspanol.format(Date())
        }

        private val formatDateLongSql = "yyyy-MM-dd HH:mm:ss"
        fun getDateSqlLong(): String {
            val fechaEspanol =
                SimpleDateFormat(formatDateLongSql)
            return fechaEspanol.format(Date())
        }
    }
}