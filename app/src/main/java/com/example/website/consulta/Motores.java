package com.example.website.consulta;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Motores extends AppCompatActivity {
    private CustomAdapter adpter;
        Button btnbuscar;
        EditText lblen,lblen1;
        ListView Lt01;
        public ArrayList<List>CustomListViewValuewArr=new ArrayList<List>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_motores);

        lblen     = findViewById(R.id.lblen);
        lblen1    = findViewById(R.id.lblen1);
        btnbuscar = findViewById(R.id.btnbuscar);
        Lt01      = findViewById(R.id.Lt01);


        btnbuscar.setOnClickListener(new View.OnClickListener(){

          @Override
         public void onClick(View v) {
            Buscar();
         }
          });

    }
    public class FiLList extends AsyncTask<String,String,String>
    {
            String z="";
            List<Map<String,String>> proList=new ArrayList<Map<String,String>>();

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
    public Connection conexionBD(){
        Connection cnn=null;
            try{
                StrictMode.ThreadPolicy politica = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(politica);

                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                cnn = DriverManager.getConnection("jdbc:jtds:sqlserver://148.102.23.8;port=1433;databaseName=AOSHIMA;user=WOODY;password=123456789;");

                }catch (Exception e)
        {
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Conexión invalida." , Toast.LENGTH_LONG).show();
        }
             return cnn;

    }
    public void Buscar(){

    }


}
