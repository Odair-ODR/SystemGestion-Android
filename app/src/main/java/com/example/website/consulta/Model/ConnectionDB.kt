package com.example.website.consulta.Model

import android.os.StrictMode
import java.sql.Connection
import java.sql.DriverManager

object ConnectionDB {
    @Throws(Exception::class)
    fun Conexion(): Connection {
        return try {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            //> return DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.17;databaseName=BDANDROID;user=WOODY;password=123456789;");
            DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.17;databaseName=BDANDROID;user=WOODY;password=123456789;")
            //> DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;databaseName=AOSHIMA;user=WOODY;password=123456789;")
        } catch (ex: Exception) {
            throw Exception()
        }
    }
}