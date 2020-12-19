package com.example.website.consulta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;

import com.example.website.consulta.Entidad.Articulos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CodbarFragment extends Fragment {
    private EditText txtCodba, txtMotor;
    private ListView listAlternante;
    private Button btnConsultar;

    public CodbarFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_codbar, container, false);
        txtCodba = view.findViewById(R.id.txtCodBar);
        listAlternante = view.findViewById(R.id.lstAlternante);
        btnConsultar = view.findViewById(R.id.btnConsultarFra);
        txtMotor = view.findViewById(R.id.txtMotor);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConsultarXCodBar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void ConsultarXCodBar() throws Exception {
        final String procedure = "{call usp_AndroidSelectObtenerArticulosporCodBar (?)}";
        CallableStatement callStatment = ConnectionDB.Conexion().prepareCall(procedure);
        callStatment.setString(1, txtCodba.getText().toString());
        ResultSet rs = callStatment.executeQuery();
        ArrayList<Articulos> lstArtculos = new ArrayList<>();
        Articulos art = null;
        while (rs.next()) {
            art = new Articulos();
            art.setAlternante(rs.getString("alternante"));
            art.setCampar(Integer.parseInt(rs.getString("campar")));
            art.setCpdnew(rs.getString("cpdnew"));
            art.setUnimed(rs.getString("unimed"));
            art.setMotor(rs.getString("Motor"));
            art.setTotSaldo(Integer.parseInt(rs.getString("totsaldo")));
            lstArtculos.add(art);
        }
        if(art != null){
            txtCodba.setText(txtCodba.getText());
            txtMotor.setText(art.getMotor());
        }
        CargarDataListView(lstArtculos);
    }

    private void CargarDataListView(ArrayList<Articulos> articulos) {
        MasterAdapter adpater = new MasterAdapter(getContext(), articulos, 2);
        listAlternante.setAdapter(adpater);
    }
}
