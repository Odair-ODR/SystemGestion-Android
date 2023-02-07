package com.example.website.consulta.Helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class UtilsMethod {
    companion object {
        private val formatDateShortSql = "yyyy-MM-dd"
        private val formatDateLongSql = "yyyy-MM-dd HH:mm:ss"
        private val formatDate = "dd/MM/yyyy HH:mm:ss"
        private val formatOnlyDate = "dd/MM/yyyy"
        private val formatOnlyTime = "HH:mm:ss"
        fun getSqlDateShort(): String {
            val fechaEspanol = SimpleDateFormat(formatDateShortSql)
            return fechaEspanol.format(Date())
        }

        fun getDateSqlLong(): String {
            val fechaEspanol = SimpleDateFormat(formatDateLongSql)
            return fechaEspanol.format(Date())
        }

        fun String.getDateFromSqlFormat(): String {
            val format = DateTimeFormatter.ofPattern(formatDateShortSql)
            val localDate = LocalDate.parse(this, format)
            val formatter = DateTimeFormatter.ofPattern(formatOnlyDate)
            return localDate.format(formatter)
        }

        fun getDateNow(): String {
            val date = Date()
            val fechaActual = SimpleDateFormat(formatDate).format(date)
            return fechaActual
        }

        fun getOnlyDateNow(): String {
            val date = Date()
            val fechaActual = SimpleDateFormat(formatOnlyDate).format(date)
            return fechaActual
        }

        fun getOnlyTimeNow(): String {
            val date = Date()
            val horaActual = SimpleDateFormat(formatOnlyTime).format(date)
            return horaActual
        }

        fun esInternetAccesible(context: Context): Boolean{
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                }
            }
            return false
        }

        fun getDecimalthousandFormatted(value: String): String {
            val lst = StringTokenizer(value, ".")
            var str1 = value
            var str2 = ""
            if (lst.countTokens() > 1) {
                str1 = lst.nextToken()
                str2 = lst.nextToken()
            }
            var str3 = ""
            var i = 0
            var j = -1 + str1.length
            if (str1[-1 + str1.length] == '.') {
                j--
                str3 = "."
            }
            var k = j
            while (true) {
                if (k < 0) {
                    if (str2.isNotEmpty()) str3 = "$str3.$str2"
                    return str3
                }
                if (i == 3) {
                    str3 = ",$str3"
                    i = 0
                }
                str3 = str1[k].toString() + str3
                i++
                k--
            }
        }
    }
}