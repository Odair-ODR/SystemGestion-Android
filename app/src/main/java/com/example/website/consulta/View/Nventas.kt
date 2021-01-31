package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.R
import com.example.website.consulta.View.Nventas
import com.example.website.consulta.ViewModel.NVentasViewModel
import com.example.website.consulta.dummy.Tienda
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Types
import java.util.*
import kotlin.collections.ArrayList

class Nventas : AppCompatActivity() {
    private var cboTipo: Spinner? = null
    private var cboTienda: Spinner? = null
    private var txtRuc: EditText? = null
    private var txtNombre: EditText? = null
    private var txtDireccion: EditText? = null
    private var btnGrabar: Button? = null
    private var btnItem: Button? = null
    private lateinit var nVentasViewModel: NVentasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)
        InitializeComponents()
        InitializeEvents()
        CargarSpinnerTipoDocumento()
        CargarTienda()
    }

    private fun InitializeComponents() {
        cboTipo = findViewById(R.id.cboTipo)
        txtRuc = findViewById(R.id.txtRuc)
        txtNombre = findViewById(R.id.txtNombre)
        txtDireccion = findViewById(R.id.txtDireccion)
        btnGrabar = findViewById(R.id.btnGrabar)
        btnItem = findViewById(R.id.btnItem)
        cboTienda = findViewById(R.id.cboTienda)
    }

    private fun InitializeEvents() {
        btnGrabar!!.setOnClickListener(btnGrabar_OnClickListener)
        btnItem!!.setOnClickListener(btnItem_OnClickListener)
    }

    private fun CargarSpinnerTipoDocumento() {
        val tipos = arrayOf("Factura", "Boleta", "Nota de Venta")
        val adapter = ArrayAdapter(this, R.layout.spinner_item_editor, tipos)
        cboTipo!!.adapter = adapter
    }

    private fun CargarTienda() {
        try {
            val tiendas = nVentasViewModel.ObtenerTiendas()
            val adapter = ArrayAdapter(this, R.layout.spinner_item_editor, tiendas)
            cboTienda!!.adapter = adapter
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private val btnGrabar_OnClickListener = View.OnClickListener {
        try {
            val con = ConnectionDB.Conexion()
            con.autoCommit = false
            if (RegistrarPreFacturaCabDet(con)) {
                con.commit()
                con.autoCommit = true
                Toast.makeText(applicationContext, "Registrado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                con.rollback()
                Toast.makeText(applicationContext, "Error al registrar", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val btnItem_OnClickListener = View.OnClickListener {
        val inten = Intent(this@Nventas, ConsultaItemsVenta::class.java)
        startActivityForResult(inten, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            val bundle = data?.extras!!.getBundle("articulo")
            val param = bundle?.getInt("idArticulo")
            Toast.makeText(baseContext, "Mensaje recibido : " + param.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    @Throws(Exception::class)
    private fun RegistrarPreFacturaCabDet(con: Connection): Boolean {
        if (ValidarDatosGrabar()) {
            if (RegistrarPreFacturaCab(con)) {
                return RegistrarPreFactureDet(con)
            }
        }
        return false
    }

    @Throws(Exception::class)
    private fun RegistrarPreFacturaCab(con: Connection): Boolean {
        val procedure = "call usp_AndroidInsertAdicionaPreFactura (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        val idTipoOper = "0101"
        val tipdoc = if (cboTipo!!.selectedItemId.toInt() == 0) 24 else 28
        val correlativo = ObtenerCorrelativo(con)
        val igv = ObtenerIGV()
        val st = con.prepareCall(procedure)
        val dt = Date()
        st.registerOutParameter(1, Types.INTEGER) //> output id
        st.setObject(2, 3) //> almacen
        st.setObject(3, 1) //> numumero caja
        st.setObject(4, tipdoc) //> tipdoc
        st.setObject(5, correlativo[0]) //> serdoc
        st.setObject(6, correlativo[1]) //> numdoc
        st.setObject(7, 1) //> numfac
        st.setObject(8, dt.date) //> fectra
        st.setObject(9, txtRuc!!.text.toString()) //> numruc
        st.setObject(10, txtNombre!!.text.toString()) //> nomcli
        st.setObject(11, txtDireccion!!.text.toString()) //> dirli
        st.setObject(12, "-") //> placa
        st.setObject(13, 105) //> conven
        st.setObject(14, 0) //> tipgui
        st.setObject(15, "0") //> sergui
        st.setObject(16, 0) //> numgui
        st.setObject(17, null) //> fecgui
        st.setObject(18, 0) //> tipgui2
        st.setObject(19, "") //> sergui2
        st.setObject(20, 0) //> numgui2
        st.setObject(21, null) //> fecgui2
        st.setObject(22, 1) //> conpag
        st.setObject(23, 9.2) //> valven
        st.setObject(24, igv) //> valigv
        st.setObject(25, 18.1) //> valbru
        st.setObject(26, "") //> numlet
        st.setObject(27, "1") //> moneda
        st.setObject(28, "") //> serper
        st.setObject(29, 0) //> numper
        st.setObject(30, null) //> fecper
        st.setObject(31, 0.0) //> totper
        st.setObject(32, 3.32) //> tipcamp
        st.setObject(33, null) //> taller
        st.setObject(34, 105) //> idus,
        st.setObject(35, idTipoOper) //> tipOperacion
        st.setObject(36, 6) //> tipDocItendtidad
        st.setObject(37, 0) //> opergratuita
        st.setObject(38, null) //> oFechaInicio
        st.setObject(39, null) //> oFechaFin
        st.setObject(40, 0.0) //> oDescuento
        val result = st.executeUpdate()
        idPreFactura = st.getInt(1)
        return result > 0
    }

    @Throws(SQLException::class)
    private fun RegistrarPreFactureDet(con: Connection): Boolean {
        val procedure = "call usp_AndroidInsertPreFacturaDetalle " +
                "(?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?,?" +
                ",?,?,?,?,?,?,?,?,?)"
        val tipdoc = if (cboTipo!!.selectedItemId.toInt() == 0) 24 else 28
        val correlativo = ObtenerCorrelativo(con)
        val st = con.prepareCall(procedure)
        val dt = Date()
        st.setObject(1, idPreFactura) //> id
        st.setObject(2, 3) //> almacen
        st.setObject(3, 1) //> numumero caja
        st.setObject(4, tipdoc) //> tipdoc
        st.setObject(5, correlativo[0]) //> serdoc
        st.setObject(6, correlativo[1]) //> numdoc
        st.setObject(7, 1) //> numfac
        st.setObject(8, 1)
        st.setObject(9, dt.date) //> fectra
        st.setObject(10, "") //> flag
        st.setObject(11, 30976) //> idArticulo
        st.setObject(12, "NI") //> vehimarc
        st.setObject(13, 1) //> marvehi
        st.setObject(14, 300) //> codprod
        st.setObject(15, 262) //> patronarti
        st.setObject(16, "S") //> superarti
        st.setObject(17, "PK") //> marproarti
        st.setObject(18, "FS80262-S-NPK") //> alternante
        st.setObject(19, "JG") //> unimed
        st.setObject(20, 1) //> campar
        st.setObject(21, "JGO. EMP. MOTOR") //> descriarti
        st.setObject(22, "01.9333") //> codbar
        st.setObject(23, "01300.262.SPK") //> cpdnew
        st.setObject(24, "NI300.262.SPK") //> cpdold
        st.setObject(25, 1) //> totcan
        st.setObject(26, 1.00) //> preini
        st.setObject(27, 0.00) //> peruan
        st.setObject(28, 1.00) //> pretot
        st.setObject(29, 0) //> tipdcm
        st.setObject(30, "") //> serdcm
        st.setObject(31, 0) //> numdcm
        st.setObject(32, 0) //> secdcm
        st.setObject(33, null) //> fecdcm
        st.setObject(34, "") //> serabo
        st.setObject(35, 0) //> notabo,
        st.setObject(36, 0) //> secabo
        st.setObject(37, null) //> fecabo
        st.setObject(38, 105) //> idus
        st.setObject(39, null) //> glosa
        return st.executeUpdate() > 0
    }

    private fun ValidarDatosGrabar(): Boolean {
        return true
    }

    @Throws(SQLException::class)
    private fun ObtenerCorrelativo(con: Connection): Array<Any> {
        val procedure = "call usp_AndroidSelectObtenerControlDocumentos (?,?,?)"
        val idTipoDocumento = if (cboTipo!!.selectedItemPosition == 0) 24 else 28
        val st = con.prepareCall(procedure)
        st.setInt(1, 3) //> idTienda
        st.setInt(2, idTipoDocumento) //> idDocumento
        st.setInt(3, 1) //> caja
        val rs = st.executeQuery()
        var serie = ""
        var nro = 0
        var nroReg = 0
        if (rs.next()) {
            serie = rs.getString("serie")
            nro = rs.getString("nro").toInt()
            nroReg = rs.getString("nroreg").toInt()
        }
        return arrayOf(serie, nro, nroReg)
    }

    @Throws(Exception::class)
    private fun ObtenerIGV(): Double {
        val con = ConnectionDB.Conexion()
        val procedure = "call usp_AndroidObtenerIGV ()"
        val st = con.prepareCall(procedure)
        val rs = st.executeQuery()
        return if (rs.next()) {
            rs.getDouble("descuento")
        } else return 0.0
    }

    companion object {
        private var idPreFactura = 0
    }
}