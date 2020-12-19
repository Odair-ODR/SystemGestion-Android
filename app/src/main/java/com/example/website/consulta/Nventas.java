package com.example.website.consulta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class Nventas extends AppCompatActivity {
    private Spinner cboTipo, cboTienda;
    private EditText txtRuc, txtNombre, txtDireccion;
    private Button btnGrabar, btnItem;
    private static int idPreFactura = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        InitializeComponents();
        InitializeEvents();
        CargarSpinnerTipoDocumento();
        CargarTienda();
    }

    private void InitializeComponents() {
        cboTipo = findViewById(R.id.cboTipo);
        txtRuc = findViewById(R.id.txtRuc);
        txtNombre = findViewById(R.id.txtNombre);
        txtDireccion = findViewById(R.id.txtDireccion);
        btnGrabar = findViewById(R.id.btnGrabar);
        btnItem = findViewById(R.id.btnItem);
        cboTienda = findViewById(R.id.cboTienda);
    }

    private void InitializeEvents() {
        btnGrabar.setOnClickListener(btnGrabar_OnClickListener);
        btnItem.setOnClickListener(btnItem_OnClickListener);
    }

    private void CargarSpinnerTipoDocumento() {
        String[] tipos = {"Factura", "Boleta", "Nota de Venta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_editor, tipos);
        cboTipo.setAdapter(adapter);
    }

    private void CargarTienda() {
        try {
            final String procedure = "call ups_AndroidObtenerTiendasHabilitadas()";
            PreparedStatement ps = ConnectionDB.Conexion().prepareCall(procedure);
            ResultSet rs = ps.executeQuery();
            String[] tienda = new String[rs.getRow()];
            int index = 0;
            while (rs.next()) {
                tienda[index] = rs.getString("idTienda");
                index += 1;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item_editor, tienda);
            cboTienda.setAdapter(adapter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private View.OnClickListener btnGrabar_OnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Connection con = ConnectionDB.Conexion();
                con.setAutoCommit(false);
                if (RegistrarPreFacturaCabDet(con)) {
                    con.commit();
                    con.setAutoCommit(true);
                    Toast.makeText(getApplicationContext(), "Registrado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    con.rollback();
                    Toast.makeText(getApplicationContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private View.OnClickListener btnItem_OnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            Intent inten = new Intent(Nventas.this, ConsultaItemsVenta.class);
            startActivity(inten, null);
        }
    };

    private Boolean RegistrarPreFacturaCabDet(Connection con) throws Exception {
        if (ValidarDatosGrabar()) {
            if (RegistrarPreFacturaCab(con)) {
                return RegistrarPreFactureDet(con);
            }
        }
        return false;
    }

    private Boolean RegistrarPreFacturaCab(Connection con) throws Exception {
        final String procedure = "call usp_AndroidInsertAdicionaPreFactura (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        final String idTipoOper = "0101";
        int tipdoc = (int) cboTipo.getSelectedItemId() == 0 ? 24 : 28;
        Object correlativo[] = ObtenerCorrelativo(con);
        double igv = ObtenerIGV();
        CallableStatement st = con.prepareCall(procedure);
        Date dt = new Date();
        st.registerOutParameter(1, Types.INTEGER); //> output id
        st.setObject(2, 3); //> almacen
        st.setObject(3, 1); //> numumero caja
        st.setObject(4, tipdoc); //> tipdoc
        st.setObject(5, correlativo[0]); //> serdoc
        st.setObject(6, correlativo[1]); //> numdoc
        st.setObject(7, 1); //> numfac
        st.setObject(8, dt.getDate()); //> fectra
        st.setObject(9, txtRuc.getText().toString()); //> numruc
        st.setObject(10, txtNombre.getText().toString()); //> nomcli
        st.setObject(11, txtDireccion.getText().toString()); //> dirli
        st.setObject(12, "-"); //> placa
        st.setObject(13, 105); //> conven
        st.setObject(14, 0); //> tipgui
        st.setObject(15, "0"); //> sergui
        st.setObject(16, 0); //> numgui
        st.setObject(17, null); //> fecgui
        st.setObject(18, 0); //> tipgui2
        st.setObject(19, ""); //> sergui2
        st.setObject(20, 0); //> numgui2
        st.setObject(21, null); //> fecgui2
        st.setObject(22, 1); //> conpag
        st.setObject(23, 9.2); //> valven
        st.setObject(24, igv); //> valigv
        st.setObject(25, 18.1); //> valbru
        st.setObject(26, ""); //> numlet
        st.setObject(27, "1"); //> moneda
        st.setObject(28, ""); //> serper
        st.setObject(29, 0); //> numper
        st.setObject(30, null); //> fecper
        st.setObject(31, 0.0); //> totper
        st.setObject(32, 3.32); //> tipcamp
        st.setObject(33, null); //> taller
        st.setObject(34, 105); //> idus,
        st.setObject(35, idTipoOper); //> tipOperacion
        st.setObject(36, 6); //> tipDocItendtidad
        st.setObject(37, 0); //> opergratuita
        st.setObject(38, null); //> oFechaInicio
        st.setObject(39, null); //> oFechaFin
        st.setObject(40, 0.0); //> oDescuento

        int result = st.executeUpdate();
        idPreFactura = st.getInt(1);
        return result > 0;
    }

    private Boolean RegistrarPreFactureDet(Connection con) throws SQLException {
        final String procedure = "call usp_AndroidInsertPreFacturaDetalle " +
                "(?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?)";
        int tipdoc = (int) cboTipo.getSelectedItemId() == 0 ? 24 : 28;
        Object correlativo[] = ObtenerCorrelativo(con);
        CallableStatement st = con.prepareCall(procedure);
        Date dt = new Date();
        st.setObject(1, idPreFactura); //> id
        st.setObject(2, 3); //> almacen
        st.setObject(3, 1); //> numumero caja
        st.setObject(4, tipdoc); //> tipdoc
        st.setObject(5, correlativo[0]); //> serdoc
        st.setObject(6, correlativo[1]); //> numdoc
        st.setObject(7, 1); //> numfac
        st.setObject(8, 1);
        st.setObject(9, dt.getDate()); //> fectra
        st.setObject(10, ""); //> flag
        st.setObject(11, 30976); //> idArticulo
        st.setObject(12, "NI"); //> vehimarc
        st.setObject(13, 1); //> marvehi
        st.setObject(14, 300); //> codprod
        st.setObject(15, 262); //> patronarti
        st.setObject(16, "S"); //> superarti
        st.setObject(17, "PK"); //> marproarti
        st.setObject(18, "FS80262-S-NPK"); //> alternante
        st.setObject(19, "JG"); //> unimed
        st.setObject(20, 1); //> campar
        st.setObject(21, "JGO. EMP. MOTOR"); //> descriarti
        st.setObject(22, "01.9333"); //> codbar
        st.setObject(23, "01300.262.SPK"); //> cpdnew
        st.setObject(24, "NI300.262.SPK"); //> cpdold
        st.setObject(25, 1); //> totcan
        st.setObject(26, 1.00); //> preini
        st.setObject(27, 0.00); //> peruan
        st.setObject(28, 1.00); //> pretot
        st.setObject(29, 0); //> tipdcm
        st.setObject(30, ""); //> serdcm
        st.setObject(31, 0); //> numdcm
        st.setObject(32, 0); //> secdcm
        st.setObject(33, null); //> fecdcm
        st.setObject(34, ""); //> serabo
        st.setObject(35, 0); //> notabo,
        st.setObject(36, 0); //> secabo
        st.setObject(37, null); //> fecabo
        st.setObject(38, 105); //> idus
        st.setObject(39, null); //> glosa

        return st.executeUpdate() > 0;
    }

    private Boolean ValidarDatosGrabar() {
        return true;
    }

    private Object[] ObtenerCorrelativo(Connection con) throws SQLException {
        final String procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)";
        int idTipoDocumento = cboTipo.getSelectedItemPosition() == 0 ? 24 : 28;
        CallableStatement st = con.prepareCall(procedure);
        st.setInt(1, 3); //> idTienda
        st.setInt(2, idTipoDocumento); //> idDocumento
        st.setInt(3, 1); //> caja
        ResultSet rs = st.executeQuery();
        String serie = "";
        int nro = 0;
        int nroReg = 0;
        if (rs.next()) {
            serie = rs.getString("serie");
            nro = Integer.parseInt(rs.getString("nro"));
            nroReg = Integer.parseInt(rs.getString("nroreg"));
        }
        return new Object[]{serie, nro, nroReg};
    }

    private double ObtenerIGV() throws Exception {
        Connection con = ConnectionDB.Conexion();
        final String procedure = "call usp_AndroidObtenerIGV ()";
        CallableStatement st = con.prepareCall(procedure);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            return rs.getDouble("descuento");
        }
        return 0;
    }
}
