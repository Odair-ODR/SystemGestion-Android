package com.example.website.consulta;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class Principal extends AppCompatActivity  {

    Spinner spinner;
    Button  btnbuscar;
    EditText lblen;
    TextView txtdes,txtsald,txtalter,txtrefe,txtcod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_principal);


        txtdes    = findViewById(R.id.txtdes);
        txtsald   = findViewById(R.id.txtsald);
        txtalter  = findViewById(R.id.txtalter);
        txtrefe   = findViewById(R.id.txtrefe);
        txtcod    = findViewById(R.id.txtcod);
        lblen     = findViewById(R.id.lblen);
        btnbuscar = findViewById(R.id.btnbuscar);
        spinner   = findViewById(R.id.spinner);


        String [] opciones ={"Alternante","codigo.bar","Nombre","Motores"};

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_editor, opciones);
        spinner.setAdapter(adapter);

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Buscar();
            }
        });
    }
    public Connection conexionBD(){

        Connection cnn=null;
        try{
            StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(politica);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
          //  cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.1.17;databaseName=MOTRIZNEW;user=WOODY;password=123456789;");
             cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;port=1433;databaseName=AOSHIMA;user=WOODY;password=123456789;");
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Conexión invalida." , Toast.LENGTH_LONG).show();
        }
        return cnn;
    }

    public  void Buscar() {

        String seleccion = spinner.getSelectedItem().toString();
        if (seleccion.equals("Alternante")) {
            try {
                Statement stm = conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Articulos WHERE alternante ='" + lblen.getText().toString() + "'");
                if (rs.next())

                {
                    txtalter.setText(rs.getString(8));
                    txtdes.setText(rs.getString(14));
                    txtsald.setText(rs.getString(29));
                    txtcod.setText(rs.getString(20));
                    txtrefe.setText(rs.getString(15));

                }
                lblen.setText("");


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (seleccion.equals("codigo.bar")) {
            try {
                Statement stm = conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Articulos WHERE codbar ='" + lblen.getText().toString() + "'" );
                if (rs.next())

                {
                    txtalter.setText(rs.getString(8));
                    txtdes.setText(rs.getString(14));
                    txtsald.setText(rs.getString(29));
                    txtcod.setText(rs.getString(20));
                    txtrefe.setText(rs.getString(15));

                }
                lblen.setText("");


            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (seleccion.equals("Nombre")) {
            try {
                Statement stm = conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Articulos WHERE  descriArti ='" + lblen.getText().toString() + "'");
                if (rs.next())

                {
                    txtalter.setText(rs.getString(8));
                    txtdes.setText(rs.getString(14));
                    txtsald.setText(rs.getString(29));
                    txtcod.setText(rs.getString(20));
                    txtrefe.setText(rs.getString(15));
                }
                lblen.setText("");


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }else if(seleccion.equals("Motores")){
            try {
                Statement stm = conexionBD().createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Articulos WHERE  descriArti ='" + lblen.getText().toString() + "'");
                if (rs.next())

                {
                    txtalter.setText(rs.getString(8));
                    txtdes.setText(rs.getString(14));
                    txtsald.setText(rs.getString(29));
                    txtcod.setText(rs.getString(20));
                    txtrefe.setText(rs.getString(15));
                }
                lblen.setText("");


            } catch (Exception e) {

                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
