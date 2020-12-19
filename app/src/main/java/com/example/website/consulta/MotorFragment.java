package com.example.website.consulta;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.website.consulta.Entidad.Articulos;
import com.example.website.consulta.Entidad.Motor;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class MotorFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private EditText txtMotor, txtCodProd;
    private Button btnConsultar;
    private ListView listMotores;

    public MotorFragment() {
        // Required empty public constructor
    }

    public static MotorFragment newInstance(String param1, String param2) {
        MotorFragment fragment = new MotorFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_motor, container, false);
        txtMotor = view.findViewById(R.id.txtMotor);
        txtCodProd = view.findViewById(R.id.txtCodProd);
        btnConsultar = view.findViewById(R.id.btnConsultarFra);
        listMotores = view.findViewById(R.id.lstMotor);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ConsultaMotor();
                    //> ConsultarArticulo();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return view;
    }

    private void ConsultarArticulo() throws Exception {
        final String procedure = "{call usp_AndroidObtenerArticulosporProdMotor (?,?)}";
        CallableStatement callStatment = ConnectionDB.Conexion().prepareCall(procedure);
        callStatment.setString(1, txtCodProd.getText().toString());
        callStatment.setString(2, txtMotor.getText().toString());
        ResultSet rs = callStatment.executeQuery();
        ArrayList<Articulos> lstArticulo = new ArrayList<>();
        while (rs.next()) {
            Articulos articulo = new Articulos();
            articulo.setCodbar(rs.getString("codbar"));
            articulo.setCampar(Integer.parseInt(rs.getString("campar")));
            articulo.setUnimed(rs.getString("unimed"));
            articulo.setMotor(rs.getString("motor"));
            articulo.setAlternante(rs.getString("alternante"));
            articulo.setCpdnew(rs.getString("cpdnew"));
            articulo.setTotSaldo(Integer.parseInt(rs.getString("totsaldo")));
            lstArticulo.add(articulo);
        }
        CargarDataListView(lstArticulo, null, 1);
    }

    private void ConsultaMotor() throws Exception {
        final String procedure = "{call usp_AndroidSelectObtenerMotoresporMantMotores(?,?)}";
        CallableStatement callStatment = ConnectionDB.Conexion().prepareCall(procedure);
        callStatment.setString(1, txtCodProd.getText().toString());
        callStatment.setString(2, txtMotor.getText().toString());
        ResultSet rs = callStatment.executeQuery();
        ArrayList<Motor> lstMotor = new ArrayList<>();
        Motor motor = new Motor();
        motor.setMarca("Marca");
        motor.setMotor("Motor");
        motor.setCili1("Cili1");
        lstMotor.add(motor);
        while (rs.next()) {
            motor = new Motor();
            motor.setMarca(rs.getString("marcavehi"));
            motor.setMotor(rs.getString("motor"));
            motor.setCili1(rs.getString("cili1"));
            lstMotor.add(motor);
        }
        CargarDataListView(null, lstMotor, 2);
    }

    private void CargarDataListView(ArrayList<Articulos> lstArticulo, ArrayList<Motor> lstMotor, int acces) {
        MasterAdapter adapter;
        if (acces == 1) {
            adapter = new MasterAdapter(getContext(), lstArticulo, 3);
            listMotores.setAdapter(adapter);
        } else {
            adapter = new MasterAdapter(getContext(), lstMotor, 4);
            showAlertDialog(adapter);
        }
    }

    public void showAlertDialog(final ArrayAdapter adapter) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogCustom));
        alertDialog.setTitle("Vista de Motores");
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                Motor motor = (Motor) adapter.getItem(position);
                try {
                    dialog.dismiss();
                    txtMotor.setText(motor.getMotor());
                    ConsultarArticulo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.show();
    }
}
