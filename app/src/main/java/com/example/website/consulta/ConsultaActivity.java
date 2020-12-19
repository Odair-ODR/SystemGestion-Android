package com.example.website.consulta;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class ConsultaActivity extends AppCompatActivity {
    private Spinner spFiltro;
    private FragmentTransaction frManager;
    private Fragment frMotor, frAlternante, frCodBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        frMotor = new MotorFragment();
        frAlternante = new AlternanteFragment();
        frCodBar = new CodbarFragment();

        spFiltro = findViewById(R.id.spFiltro);
        spFiltro.setAdapter(ListaFiltros());
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                frManager = getSupportFragmentManager().beginTransaction();
                if (i == 0) {
                    //>  frManager.addToBackStack("");
                    frManager.replace(R.id.frContent, frAlternante);
                    frManager.addToBackStack(null);
                } else if(i == 1) {
                    frManager.replace(R.id.frContent, frCodBar);
                    frManager.addToBackStack(null);
                }
                else{
                    frManager.replace(R.id.frContent, frMotor);
                    frManager.addToBackStack(null);
                }
                frManager.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //> getSupportFragmentManager().beginTransaction().add(R.id.frContent, frMotor);
    }

    private ArrayAdapter<CharSequence> ListaFiltros() {
        //> String[] opciones = {"Alternante", "codigo.bar", "Nombre", "Motores"};

        //> ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_editor, opciones);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.consulta_opcion, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return adapter;
    }
}
