package com.example.website.consulta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    Button btnconsu,btnmoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio);

        btnconsu = findViewById(R.id.btnconsu);
        btnmoto  = findViewById(R.id.btnmoto);

        btnconsu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent( Inicio.this,Principal.class));

            }
        });
        btnmoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inicio.this,Motores.class));
            }
        });
    }
}
