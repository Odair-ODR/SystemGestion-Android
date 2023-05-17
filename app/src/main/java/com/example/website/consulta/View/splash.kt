package com.example.website.consulta.View

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.website.consulta.Helpers.UtilsMethod
import com.example.website.consulta.Helpers.UtilsSistema
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.Model.Entidad.PERMISSION_TYPE
import com.example.website.consulta.Model.Entidad.UsuarioTo
import com.example.website.consulta.databinding.ActivitySplashBinding
import java.sql.SQLException
import java.util.jar.Manifest

class splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        verificarPermisosApp()
        initializeEvents()
        verficarAccesoInternet()
    }

    private fun initializeEvents(){
        binding.btnIngresar.setOnClickListener(View.OnClickListener { iniciarSesion() })
        binding.btnCancelar.setOnClickListener(View.OnClickListener { cancelar() })
    }

    private fun iniciarSesion() {
        val cn = ConnectionDB.Conexion()
        try {
            if(!verficarAccesoInternet())
                return
            val procedure = "{call selectvalidarUsuario (?,?)}"
            val st = cn.prepareCall(procedure)
            st.setString(1, binding.txtUser.text.toString())
            st.setString(2, binding.txtPassaword.text.toString())
            val rs = st.executeQuery()
            if (rs.next()) {
                val usuario = UsuarioTo().also {
                    it.idUsuario = rs.getInt("idUsuario")
                    it.nombre = rs.getString("nombre")
                }
                UtilsSistema.setUsuario = usuario
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
        } finally {
            cn.close()
        }
    }

    private fun cancelar(){
        binding.txtUser.setText("")
        binding.txtPassaword.setText("")
    }

    private fun verficarAccesoInternet(): Boolean{
        if(!UtilsMethod.esInternetAccesible(this)){
            Toast.makeText(this, "No tienes acceso a internet", Toast.LENGTH_SHORT).show()
            return false
        }
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            solicitarPermisosNecesarios(PERMISSION_TYPE.READ_WRITE_EXTERNAL_STORAGE)
        }

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            Toast.makeText(this, "Debe encender el bluetooth para una correcta funcionalidad de la aplicación", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun verificarPermisosApp() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            solicitarPermisosNecesarios(PERMISSION_TYPE.READ_WRITE_EXTERNAL_STORAGE)
        }

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
            // El Bluetooth está apagado
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Encender Bluetooth")
            dialogBuilder.setMessage("Para utilizar esta función, es necesario encender el Bluetooth. ¿Deseas encenderlo ahora?")
            dialogBuilder.setPositiveButton("Aceptar") { _, _ ->
                bluetoothAdapter?.enable()
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                    solicitarPermisosNecesarios(PERMISSION_TYPE.BLUETOOTH)
                }
            }
            dialogBuilder.setNegativeButton("Cancelar") { _, _ ->
                // El usuario ha cancelado la solicitud de encender el Bluetooth
                // Aquí puedes realizar cualquier acción adicional si el usuario cancela
            }
            dialogBuilder.create().show()
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                solicitarPermisosNecesarios(PERMISSION_TYPE.BLUETOOTH)
            }
        }
    }

    private fun solicitarPermisosNecesarios(permission: PERMISSION_TYPE){
        if (permission == PERMISSION_TYPE.READ_WRITE_EXTERNAL_STORAGE) {
            val permisos = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this@splash, permisos, PERMISSION_CODE)
        }
        if (permission == PERMISSION_TYPE.BLUETOOTH) {
            val permisos = arrayOf(android.Manifest.permission.BLUETOOTH, android.Manifest.permission.BLUETOOTH_CONNECT)
            ActivityCompat.requestPermissions(this, permisos, PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode == PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this, "Permisos denegados", Toast.LENGTH_SHORT).show()
            }
        }
    }
}