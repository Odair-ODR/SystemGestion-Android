package com.example.website.consulta.View

import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.example.website.consulta.R
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class Motores : AppCompatActivity() {
    private val adpter: CustomAdapter? = null
    var btnbuscar: Button? = null
    var lblen: EditText? = null
    var lblen1: EditText? = null
    var Lt01: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_motores)
        lblen = findViewById(R.id.lblen)
        lblen1 = findViewById(R.id.lblen1)
        btnbuscar = findViewById(R.id.btnbuscar)
        Lt01 = findViewById(R.id.Lt01)
        btnbuscar?.setOnClickListener(View.OnClickListener { Buscar() })
    }

    fun conexionBD(): Connection? {
        var cnn: Connection? = null
        try {
            val politica = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(politica)
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;port=1433;databaseName=AOSHIMA;user=WOODY;password=123456789;")
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "Conexión invalida.", Toast.LENGTH_LONG).show()
        }
        return cnn
    }
    fun Buscar() {}
}