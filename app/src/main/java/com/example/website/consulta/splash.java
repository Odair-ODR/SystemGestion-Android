package com.example.website.consulta;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class splash extends AppCompatActivity {
    //> private final int DURACION_SPLASH = 40000;
    private EditText txtUsuario, txtContraseña;
    private Button btnIngresar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnCancelar = findViewById(R.id.btnCancelar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IniciarSesion();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUsuario.setText("");
                txtContraseña.setText("");
            }
        });
        /*new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(splash.this,Inicio.class);
                startActivity(intent);
                finish();
            }
        },DURACION_SPLASH);*/
    }

    private void IniciarSesion() {
        try {
            final String procedure = "{call selectvalidarUsuario (?,?)}";
            CallableStatement st = ConnectionDB.Conexion().prepareCall(procedure);
            st.setString(1, txtUsuario.getText().toString());
            st.setString(2, txtContraseña.getText().toString());
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Intent intent = new Intent(splash.this, MenuActivity.class);
                startActivity(intent, null);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "usuario  y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Cierre la aplicación y vuelva a ingresar", Toast.LENGTH_SHORT).show();
        }
    }
}

