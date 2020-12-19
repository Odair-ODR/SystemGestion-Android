package com.example.website.consulta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Emision extends AppCompatActivity {

    Button nuevo,consulta,anular,modificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emision);

        nuevo     = findViewById(R.id.Nueva_v);
        consulta  = findViewById(R.id.Consultar_v);
        anular    = findViewById(R.id.Anular_V);
        modificar = findViewById(R.id.Modificar_v);

        nuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent n = new Intent(Emision.this,Nventas.class);
                startActivity(n);
            }
        });

        consulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent c = new Intent(Emision.this,Consultarve.class);
                startActivity(c);
            }
        });
        anular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Emision.this,Anularve.class);
                startActivity(a);
            }
        });
        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(Emision.this,Modificarve.class);
                startActivity(m);
            }
        });
    }
}
