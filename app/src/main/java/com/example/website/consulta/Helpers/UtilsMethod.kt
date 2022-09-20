package com.example.website.consulta.Helpers

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class UtilsMethod {
    companion object {
        private val formatDateShortSql = "yyyy-MM-dd"
        private val formatDateLongSql = "yyyy-MM-dd HH:mm:ss"
        private val formatDate = "dd/MM/yyyy"
        fun getSqlDateShort(): String {
            val fechaEspanol = SimpleDateFormat(formatDateShortSql)
            return fechaEspanol.format(Date())
        }

        fun getDateSqlLong(): String {
            val fechaEspanol = SimpleDateFormat(formatDateLongSql)
            return fechaEspanol.format(Date())
        }

        fun String.getDateFormat(): String {
            val format = DateTimeFormatter.ofPattern(formatDateShortSql)
            val localDate = LocalDate.parse(this, format)
            val formatter = DateTimeFormatter.ofPattern(formatDate)
            return localDate.format(formatter)
        }
    }
}