package com.example.website.consulta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.website.consulta.Entidad.Articulos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlternanteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlternanteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText txtAlternante;
    private ListView listAlternante;
    private Button btnConsultar;

    public AlternanteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AlternanteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlternanteFragment newInstance(String param1, String param2) {
        AlternanteFragment fragment = new AlternanteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alternante, container, false);
        txtAlternante = view.findViewById(R.id.txtAlternante);
        listAlternante = view.findViewById(R.id.lstAlternante);

        btnConsultar = view.findViewById(R.id.btnConsultarFra);
        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ConsultarAlternante();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void ConsultarAlternante() throws Exception {
        final String procedure = "{call AdroidSelectObtenerArticulosporAlternante (?)}";
        CallableStatement callStatment = ConnectionDB.Conexion().prepareCall(procedure);
        callStatment.setString(1, txtAlternante.getText().toString());
        ResultSet rs = callStatment.executeQuery();
        ArrayList<Articulos> lstArtculos = new ArrayList<>();
        Articulos art;
        while (rs.next()) {
            art = new Articulos();
            art.setAlternante(rs.getString("alternante"));
            art.setCampar(Integer.parseInt(rs.getString("campar")));
            art.setCodbar(rs.getString("codbar"));
            art.setCpdnew(rs.getString("cpdnew"));
            art.setUnimed(rs.getString("unimed"));
            art.setMotor(rs.getString("Motor"));
            art.setTotSaldo(Integer.parseInt(rs.getString("totsaldo")));
            lstArtculos.add(art);
        }
        CargarDataListView(lstArtculos);
    }

    private void CargarDataListView(ArrayList<Articulos> articulos) {
        MasterAdapter adpater = new MasterAdapter(getContext(), articulos, 1);
        listAlternante.setAdapter(adpater);
    }
}
