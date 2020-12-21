package com.example.website.consulta.View

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.*
import com.example.website.consulta.R
import java.sql.Connection
import java.sql.DriverManager

class Principal : AppCompatActivity() {
    var spinner: Spinner? = null
    var btnbuscar: Button? = null
    var lblen: EditText? = null
    var txtdes: TextView? = null
    var txtsald: TextView? = null
    var txtalter: TextView? = null
    var txtrefe: TextView? = null
    var txtcod: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_principal)
        txtdes = findViewById(R.id.txtdes)
        txtsald = findViewById(R.id.txtsald)
        txtalter = findViewById(R.id.txtalter)
        txtrefe = findViewById(R.id.txtrefe)
        txtcod = findViewById(R.id.txtcod)
        lblen = findViewById(R.id.lblen)
        btnbuscar = findViewById(R.id.btnbuscar)
        spinner = findViewById(R.id.spinner)
        val opciones = arrayOf("Alternante", "codigo.bar", "Nombre", "Motores")
        val adapter = ArrayAdapter(this, R.layout.spinner_item_editor, opciones)
        spinner?.setAdapter(adapter)
        btnbuscar?.setOnClickListener(View.OnClickListener { Buscar() })
    }

    fun conexionBD(): Connection? {
        var cnn: Connection? = null
        try {
            val politica = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(politica)
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance()
            //  cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.17;databaseName=MOTRIZNEW;user=WOODY;password=123456789;");
            cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;port=1433;databaseName=AOSHIMA;user=WOODY;password=123456789;")
        } catch (e: Exception) {
            Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            Toast.makeText(applicationContext, "Conexión invalida.", Toast.LENGTH_LONG).show()
        }
        return cnn
    }

    fun Buscar() {
        val seleccion = spinner!!.selectedItem.toString()
        if (seleccion == "Alternante") {
            try {
                val stm = conexionBD()!!.createStatement()
                val rs = stm.executeQuery("SELECT * FROM Articulos WHERE alternante ='" + lblen!!.text.toString() + "'")
                if (rs.next()) {
                    txtalter!!.text = rs.getString(8)
                    txtdes!!.text = rs.getString(14)
                    txtsald!!.text = rs.getString(29)
                    txtcod!!.text = rs.getString(20)
                    txtrefe!!.text = rs.getString(15)
                }
                lblen!!.setText("")
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        } else if (seleccion == "codigo.bar") {
            try {
                val stm = conexionBD()!!.createStatement()
                val rs = stm.executeQuery("SELECT * FROM Articulos WHERE codbar ='" + lblen!!.text.toString() + "'")
                if (rs.next()) {
                    txtalter!!.text = rs.getString(8)
                    txtdes!!.text = rs.getString(14)
                    txtsald!!.text = rs.getString(29)
                    txtcod!!.text = rs.getString(20)
                    txtrefe!!.text = rs.getString(15)
                }
                lblen!!.setText("")
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        } else if (seleccion == "Nombre") {
            try {
                val stm = conexionBD()!!.createStatement()
                val rs = stm.executeQuery("SELECT * FROM Articulos WHERE  descriArti ='" + lblen!!.text.toString() + "'")
                if (rs.next()) {
                    txtalter!!.text = rs.getString(8)
                    txtdes!!.text = rs.getString(14)
                    txtsald!!.text = rs.getString(29)
                    txtcod!!.text = rs.getString(20)
                    txtrefe!!.text = rs.getString(15)
                }
                lblen!!.setText("")
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        } else if (seleccion == "Motores") {
            try {
                val stm = conexionBD()!!.createStatement()
                val rs = stm.executeQuery("SELECT * FROM Articulos WHERE  descriArti ='" + lblen!!.text.toString() + "'")
                if (rs.next()) {
                    txtalter!!.text = rs.getString(8)
                    txtdes!!.text = rs.getString(14)
                    txtsald!!.text = rs.getString(29)
                    txtcod!!.text = rs.getString(20)
                    txtrefe!!.text = rs.getString(15)
                }
                lblen!!.setText("")
            } catch (e: Exception) {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}