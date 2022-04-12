package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.R
import java.sql.SQLException

class splash : AppCompatActivity() {
    //> private final int DURACION_SPLASH = 40000;
    private var txtUsuario: EditText? = null
    private var txtContraseña: EditText? = null
    private var btnIngresar: Button? = null
    private var btnCancelar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)
        txtUsuario = findViewById(R.id.txtUsuario)
        txtContraseña = findViewById(R.id.txtContraseña)
        btnIngresar = findViewById(R.id.btnIngresar)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnIngresar?.setOnClickListener(View.OnClickListener { IniciarSesion() })
        btnCancelar?.setOnClickListener(View.OnClickListener {
            txtUsuario?.setText("")
            txtContraseña?.setText("")
        })
        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(splash.this,Inicio.class);
                startActivity(intent);
                finish();
            }
        },DURACION_SPLASH);*/
    }

    private fun IniciarSesion() {
        try {
            val procedure = "{call selectvalidarUsuario (?,?)}"
            val st = ConnectionDB.Conexion().prepareCall(procedure)
            st.setString(1, txtUsuario!!.text.toString())
            st.setString(2, txtContraseña!!.text.toString())
            val rs = st.executeQuery()
            if (rs.next()) {
                val intent = Intent(this@splash, MenuActivity::class.java)
                startActivity(intent, null)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "usuario  y/o contraseña incorrecta",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (sqlEx: SQLException){
            sqlEx.printStackTrace()
            Toast.makeText(
                applicationContext,
                "error de conexión a la base de datos",
                Toast.LENGTH_SHORT
            ).show()
        }
        catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                applicationContext,
                "error de conexión a la base de datos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}