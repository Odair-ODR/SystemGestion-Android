package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.databinding.ActivitySplashBinding
import java.sql.SQLException

class splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener(View.OnClickListener { IniciarSesion() })
        binding.btnCancelar.setOnClickListener(View.OnClickListener {
            binding.txtUser.setText("")
            binding.txtPassaword.setText("")
        })
    }

    private fun IniciarSesion() {
        try {
            val procedure = "{call selectvalidarUsuario (?,?)}"
            val st = ConnectionDB.Conexion().prepareCall(procedure)
            st.setString(1, binding.txtUser.text.toString())
            st.setString(2, binding.txtPassaword.text.toString())
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