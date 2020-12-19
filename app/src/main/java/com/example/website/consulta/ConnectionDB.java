package com.example.website.consulta;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {

    public static Connection Conexion() throws Exception {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            //> return DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.17;databaseName=BDANDROID;user=WOODY;password=123456789;");
            return DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;databaseName=AOSHIMA;user=WOODY;password=123456789;");
        } catch (Exception ex) {
            throw new Exception();
        }
    }
}
