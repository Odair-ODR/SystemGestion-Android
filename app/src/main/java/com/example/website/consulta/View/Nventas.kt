package com.example.website.consulta.View

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Helpers.UtilsMethod
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.NVentasViewModel
import com.example.website.consulta.dummy.Tienda
import kotlinx.android.synthetic.main.activity_factura.*
import kotlinx.android.synthetic.main.activity_factura.horizontalScrollViewDetail
import kotlinx.android.synthetic.main.activity_factura.horizontalScrollViewHead
import kotlinx.android.synthetic.main.activity_factura.tblArticuloDetail
import kotlinx.android.synthetic.main.activity_factura.tblArticuloHead
import kotlinx.android.synthetic.main.activity_factura.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class Nventas : AppCompatActivity() {
    private var txtTipoDoc: EditText? = null
    private var txtTienda: EditText? = null
    private var txtNroDocIdenti: EditText? = null
    private var txtNombre: EditText? = null
    private var txtDireccion: EditText? = null
    private var btnGrabar: Button? = null
    private var btnItem: Button? = null

    private lateinit var btnBuscarRucDni: Button
    private lateinit var view: View
    private lateinit var nVentasViewModel: NVentasViewModel
    private lateinit var alertDialog: AlertDialog
    private lateinit var rdbTienda: RadioButton
    private lateinit var rdbMoneda: RadioButton
    private lateinit var dni_ruc: String
    private lateinit var progresDialog: Dialog
    private lateinit var tipoDocumento: TIPO_DOCUMENTO

    private val FACTURA = 24
    private val BOLETA = 28
    private val CONTADO = 1
    private val CREDITO = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_factura)
        initializeComponents()
        initializeEvents()
        startOptions()
        observerObtenerEntidadToApi()
        //> CargarSpinnerTipoDocumento()

    }

    private fun startOptions() {
        tipoDocumento = intent.getSerializableExtra("TipoDocumento") as TIPO_DOCUMENTO
        txtTipoDoc?.setText(tipoDocumento.descripcion)
    }

    private fun initializeComponents() {
        txtTipoDoc = findViewById(R.id.txtTipoDoc)
        txtNroDocIdenti = findViewById(R.id.txtNroDocIdenti)
        txtNombre = findViewById(R.id.txtNombre)
        txtDireccion = findViewById(R.id.txtDireccion)
        btnGrabar = findViewById(R.id.btnGrabar)
        btnItem = findViewById(R.id.btnItem)
        txtTienda = findViewById(R.id.txtTienda)
        btnBuscarRucDni = findViewById(R.id.btnBuscarRucDni)
        initializeComponentsNVentasViewModel()
    }

    private fun initializeEvents() {
        btnGrabar!!.setOnClickListener(btnGrabarOnClickListener)
        btnItem!!.setOnClickListener(btnItem_OnClickListener)
        txtTienda!!.setOnClickListener(txtTienda_OnClickListener)
        txtMoneda!!.setOnClickListener(txtMoneda_OnClickListener)
        btnBuscarRucDni.setOnClickListener(btnBuscarRucDniOnClickListener)
    }

    private fun initializeComponentsNVentasViewModel() {
        nVentasViewModel = NVentasViewModel(this)
        nVentasViewModel.horizontalScrollViewHead = horizontalScrollViewHead
        nVentasViewModel.tblArticuloHead = tblArticuloHead
        nVentasViewModel.horizontalScrollViewDetail = horizontalScrollViewDetail
        nVentasViewModel.tblArticuloDetail = tblArticuloDetail
        nVentasViewModel.initializeEvents()
        nVentasViewModel.startControls()
        nVentasViewModel.swipeDismissTouchTableAdapter()
    }

    /*private fun CargarSpinnerTipoDocumento() {
        val tipos = arrayOf("Factura", "Boleta", "Nota de Venta")
        val adapter = ArrayAdapter(this, R.layout.spinner_item_editor, tipos)
        cboTipo!!.adapter = adapter
    }*/

    /*private fun CargarTienda() {
        try {
            val tiendas = nVentasViewModel.ObtenerTiendas()
            val adapter = ArrayAdapter(this, R.layout.spinner_item_editor, tiendas)
            cboTienda!!.adapter = adapter
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }*/

    private val btnItem_OnClickListener = View.OnClickListener {
        val inten = Intent(this@Nventas, ConsultaItemsVenta::class.java)
        startActivityForResult(inten, 1)
    }

    private val txtTienda_OnClickListener = View.OnClickListener {
        view = this.layoutInflater.inflate(R.layout.tiendas_alert_dialog, null, false)
        startAlerDialogRdb(view, "Tiendas")
        cargarTiendas()
    }

    private fun cargarTiendas() {
        val lstTienda: ArrayList<Tienda> = nVentasViewModel.obtenerTiendas()
        val radioGroup = view.findViewById<RadioGroup>(R.id._RadioGruoup)
        var radioButton: RadioButton
        //> if (radioButton.getParent() != null) {
        //> (radioButton.getParent() as ViewGroup).removeView(radioButton) // <- fix
        //>}
        for (item in lstTienda) {
            radioButton = RadioButton(view.context)
            radioButton.text = item.nomTienda
            radioButton.tag = item.idTienda
            radioButton.setOnClickListener(radioButtonTienda_OnClickListener)
            radioGroup?.addView(radioButton)
        }
    }

    private var radioButtonTienda_OnClickListener = View.OnClickListener {
        rdbTienda = it as RadioButton
        txtTienda!!.setText(rdbTienda.text)
        alertDialog.dismiss()
    }

    private fun startAlerDialogRdb(view: View, title: String) {
        alertDialog = UtilsInterface.alertDialog1(this, view, this.window, title)
        alertDialog.setOnDismissListener(AlertDialog_OnDismissListener)
    }

    private var AlertDialog_OnDismissListener = DialogInterface.OnDismissListener {

    }

    private val txtMoneda_OnClickListener = View.OnClickListener {
        view = this.layoutInflater.inflate(R.layout.tiendas_alert_dialog, null, false)
        startAlerDialogRdb(view, "Moneda")
        cargarMoneda()
    }

    private fun cargarMoneda() {
        val lstTienda: ArrayList<Moneda> = nVentasViewModel.obtenerMoneda()
        val radioGroup = view.findViewById<RadioGroup>(R.id._RadioGruoup)
        var radioButton: RadioButton
        for (item in lstTienda) {
            radioButton = RadioButton(view.context)
            radioButton.text = item.descMoneda
            radioButton.tag = item.idMoneda
            radioButton.setOnClickListener(radioButtonMonedaOnClickListener)
            radioGroup?.addView(radioButton)
        }
    }

    private var radioButtonMonedaOnClickListener = View.OnClickListener {
        rdbMoneda = it as RadioButton
        txtMoneda!!.setText(rdbMoneda.text)
        alertDialog.dismiss()
    }

    private val btnBuscarRucDniOnClickListener = View.OnClickListener {
        try {
            showProgessBar();
            dni_ruc = txtNroDocIdenti?.text.toString()
            when (dni_ruc.length) {
                TIPO_DOCUMENTO_IDENTIDAD.RUC.length -> {
                    nVentasViewModel.llamarEntidadToXRucApi(txtNroDocIdenti?.text.toString())
                }
                TIPO_DOCUMENTO_IDENTIDAD.DNI.length -> {
                    nVentasViewModel.llamarEntidadToXDniApi(txtNroDocIdenti?.text.toString())
                }
                else -> {
                    closeProgessBar()
                    Toast.makeText(
                        baseContext,
                        "No se encontró la persona o entidad",
                        Toast.LENGTH_SHORT
                    ).show()
                    limpiarControlesEntidadTo()
                }
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            progresDialog.dismiss()
        }
    }

    private fun observerObtenerEntidadToApi() {
        nVentasViewModel.obtenerEntidadToApi()
            .observe(this, androidx.lifecycle.Observer { entidadTo: EntidadTo? ->
                mostrarDatosEntidad(entidadTo)
                closeProgessBar()
            })
    }

    private fun mostrarDatosEntidad(entidadTo: EntidadTo?) {
        if (entidadTo != null) {
            when (entidadTo.documentoIdentidad) {
                TIPO_DOCUMENTO_IDENTIDAD.RUC -> {
                    txtNroDocIdenti?.setText(entidadTo.ruc)
                    txtNombre?.setText(entidadTo.razonSocial)
                    txtDireccion?.setText(entidadTo.direccion)
                }
                TIPO_DOCUMENTO_IDENTIDAD.DNI -> {
                    val nombres = entidadTo.nombres
                        .plus(" ")
                        .plus(entidadTo.apellidoPaterno)
                        .plus(" ")
                        .plus(entidadTo.apellidMaterno)

                    txtNroDocIdenti?.setText(entidadTo.dni)
                    txtNombre?.setText(nombres.trim())
                    txtDireccion?.setText(entidadTo.direccion)
                }
                else -> throw IllegalArgumentException()
            }
        }

        if (entidadTo == null || (entidadTo.ruc?.length == TIPO_DOCUMENTO_IDENTIDAD.NONE.length
                    && entidadTo.dni?.length == TIPO_DOCUMENTO_IDENTIDAD.NONE.length)
        ) {
            Toast.makeText(baseContext, "No se encontró la persona o entidad", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun limpiarControlesEntidadTo() {
        txtNroDocIdenti?.setText("")
        txtNombre?.setText("")
        txtDireccion?.setText("")
    }

    private fun showProgessBar() {
        progresDialog = UtilsInterface.progressDialog(this)
    }

    private fun closeProgessBar() {
        progresDialog.dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 1 && resultCode == 1) {
                val bundle = data?.extras!!.getBundle("articulo")
                val idArticulo = bundle?.getInt("idArticulo")
                val cantidad = bundle?.getInt("cantidad")
                val pretot = bundle?.getDouble("pretot")
                val articulo: Articulo? = nVentasViewModel.obtenerArticuloXIdArticulo(idArticulo!!)
                nVentasViewModel.cargarDataTableLayout(
                    articulosFactura(articulo!!, cantidad!!, pretot!!)
                )
                Toast.makeText(
                    baseContext,
                    "Mensaje recibido : " + idArticulo.toString() + "-" + articulo.idArticulo.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun articulosFactura(articulo: Articulo, cantidad: Int, pretot: Double): ArrayList<Articulo> {
        return ArrayList<Articulo>().also {
            articulo.totCan = cantidad
            articulo.precioVenta = pretot
            it.add(articulo)
        }
    }

    private val btnGrabarOnClickListener = View.OnClickListener {
        try {
            if (validarDatosGrabar()) {
                val facturaToCab = obtenerDatosPreFacturaCab()
                val facturaToDet = obtenerDatosPreFacturaDet()
                actualizarImportesVenta(facturaToCab, facturaToDet)
                if (nVentasViewModel.registrarPreFacturaCabDet(facturaToCab, facturaToDet)) {
                    Toast.makeText(
                        applicationContext,
                        "Registrado correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(applicationContext, "Error al registrar", Toast.LENGTH_SHORT)
                        .show()
                }
                limpiarGuardar()
            }
        } catch (ex: Exception) {
            Toast.makeText(applicationContext, "Error al registrar", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }

    private fun obtenerDatosPreFacturaCab(): FacturaCabTo {
        val idTipoOper = "0101"
        val idTipoIdentidad =
            if (tipoDocumento.id == FACTURA) 6 else if (tipoDocumento.id == BOLETA) 1 else 0
        val idUs = 105
        val facturaTo = FacturaCabTo()
        facturaTo.idTienda = rdbTienda.tag.toString().toInt()
        facturaTo.nroCaja = 1
        facturaTo.tipoDoc = tipoDocumento.id
        facturaTo.serDoc = ""
        facturaTo.numDoc = 0
        facturaTo.numFac = 0
        facturaTo.nroFactura = 1
        facturaTo.fectra = UtilsMethod.getSqlDateShort()
        facturaTo.nroDocIdenti = txtNroDocIdenti!!.text.toString()
        facturaTo.nombres = txtNombre!!.text.toString()
        facturaTo.direccion = txtDireccion!!.text.toString()
        facturaTo.placa = txtPlaca.text.toString()
        facturaTo.tipgui = 0
        facturaTo.sergui = ""
        facturaTo.numgui = 0
        facturaTo.fecgui = null
        facturaTo.tipgui2 = 0
        facturaTo.sergui2 = ""
        facturaTo.numgui2 = 0
        facturaTo.fecgui2 = null
        facturaTo.conpag = obtenerConPag()
        facturaTo.valven = 0.0
        facturaTo.valigv = 0.0
        facturaTo.valbru = 0.0
        facturaTo.numlet = ""
        facturaTo.idMoneda = rdbMoneda.tag.toString().toInt()
        facturaTo.serper = ""
        facturaTo.numper = 0
        facturaTo.fecper = null
        facturaTo.totper = 0.0
        facturaTo.tipCam = 0.0
        facturaTo.taller = 0
        facturaTo.idUs = idUs
        facturaTo.idTipOperacion = idTipoOper
        facturaTo.idTipoDocIdenti = idTipoIdentidad
        facturaTo.operaGratuita = 0
        facturaTo.oFechaInicio = null
        facturaTo.oFechaFin = obtenerFechaFin()
        facturaTo.oDescuento = 0
        facturaTo.afectaIGV = "10"
        facturaTo.al31TotAnticipo = 0.0
        facturaTo.al31Anticipo = false
        facturaTo.al31AplicAnticipo = false
        return facturaTo
    }

    private fun obtenerDatosPreFacturaDet(): ArrayList<FacturaDetTo> {
        val idUs = 105
        val codigoUniversal = "25101503"
        val lista = ArrayList<FacturaDetTo>()
        var facturaDet: FacturaDetTo
        var articulo: Articulo?
        for (index in 0 until tblArticuloDetail.childCount) {
            val row = tblArticuloDetail.getChildAt(index) as TableRow
            val cell = row.getChildAt(0) as TextView
            val idArticulo = cell.text.toString().toInt()
            val cellCantidad = row.getChildAt(5) as TextView
            val cantidad = cellCantidad.text.toString().toInt()
            val cellPretot = row.getChildAt(4) as TextView
            val pretot = cellPretot.text.toString().toDouble()
            articulo = nVentasViewModel.obtenerArticuloXIdArticulo(idArticulo)
            if (articulo == null) {
                Toast.makeText(baseContext, "Error al registrar", Toast.LENGTH_LONG).show()
                break
            }
            facturaDet = FacturaDetTo()
            facturaDet.al32numfac = 0
            facturaDet.al33numSec = 0
            facturaDet.al32fecTra = UtilsMethod.getSqlDateShort()
            facturaDet.al32flag = if (articulo.totSaldo > 0) "*" else ""
            facturaDet.codigoUniversal = codigoUniversal
            facturaDet.al32idarticulo = articulo.idArticulo
            facturaDet.al32Vehimarc = articulo.vehimarc
            facturaDet.al32Marvehi = articulo.marvehi
            facturaDet.al32Codprod = articulo.codprod
            facturaDet.al32Patronarti = articulo.patronarti
            facturaDet.al32Superarti = articulo.superarti
            facturaDet.al32Marproarti = articulo.marproarti
            facturaDet.al32Alternante = articulo.alternante
            facturaDet.al32Unimed = articulo.unimed
            facturaDet.al32Campar = articulo.campar
            facturaDet.al32DescriArti = articulo.descriArti
            facturaDet.al32Codbar = articulo.codbar
            facturaDet.al32Cpdnew = articulo.cpdnew
            facturaDet.al32Cpdold = articulo.cpdold
            facturaDet.al32Totcan = cantidad
            facturaDet.al32peruan = 0.0
            facturaDet.al32pretot = pretot
            facturaDet.al32Preuni = facturaDet.al32pretot / facturaDet.al32Totcan
            facturaDet.al32tipdcm = 0
            facturaDet.al32serdcm = ""
            facturaDet.al32numdcm = 0
            facturaDet.al32secdcm = 0
            facturaDet.al32fecdcm = null
            facturaDet.al32serabo = ""
            facturaDet.al32notabo = 0
            facturaDet.al32secabo = 0
            facturaDet.al32fecabo = null
            facturaDet.idUs = idUs
            facturaDet.al32glosa = ""
            facturaDet.al32Anticipo = 0
            lista.add(facturaDet)
        }
        return lista
    }

    private fun actualizarImportesVenta(
        facturaCabTo: FacturaCabTo,
        facturaDetTo: ArrayList<FacturaDetTo>
    ) {
        var valven = 0.0
        facturaDetTo.forEach { valven += it.al32pretot }
        facturaCabTo.also {
            it.valigv = nVentasViewModel.obtenerIGV()
            it.valven = valven / it.valigv
            it.valbru = it.valven + it.valigv
        }
    }

    private fun validarDatosGrabar(): Boolean {
        if (txtTienda?.text.toString().length == 0 || rdbTienda.tag.toString().length == 0) {
            Toast.makeText(this, "Ingrese la tienda", Toast.LENGTH_LONG).show()
            return false
        }
        if (txtNroDocIdenti?.text.toString().length != 11 && txtNroDocIdenti?.text.toString().length != 8) {
            Toast.makeText(this, "Ingrese un número de ruc o dni válido", Toast.LENGTH_LONG).show()
            return false
        }
        if (txtNombre?.text.toString().length == 0) {
            Toast.makeText(this, "Ingrese el nombre de la persona o entidad", Toast.LENGTH_LONG)
                .show()
            return false
        }
        if (txtMoneda.text.toString().length == 0 || rdbMoneda.tag.toString().length == 0) {
            Toast.makeText(this, "Ingrese la moneda", Toast.LENGTH_LONG).show()
            return false
        }
        var result = false
        for (index in 0..rdgFormaPago.childCount) {
            if (rdgFormaPago.getChildAt(index) is RadioButton) {
                if ((rdgFormaPago.getChildAt(index) as RadioButton).isChecked) {
                    result = true
                    break
                }
            }
        }
        if (!result) {
            Toast.makeText(this, "Seleccione la forma de pago", Toast.LENGTH_SHORT).show()
            return false
        }
        if (tblArticuloDetail.childCount <= 0) {
            Toast.makeText(this, "Agregue al menos un artículo", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun obtenerConPag(): Int {
        for (i in 0 until rdgFormaPago.childCount) {
            if (rdgFormaPago.getChildAt(1) !is RadioButton)
                continue
            val radioButton: RadioButton = rdgFormaPago.getChildAt(i) as RadioButton
            if (radioButton.isChecked)
                return radioButton.tag.toString().toInt()
        }
        throw Exception()
    }

    private fun obtenerFechaFin(): String? {
        for (i in 0 until rdgFormaPago.childCount) {
            if (rdgFormaPago.getChildAt(1) !is RadioButton)
                continue
            val radioButton: RadioButton = rdgFormaPago.getChildAt(i) as RadioButton
            if (radioButton.isChecked) {
                return when {
                    radioButton.tag.toString().toInt() == CONTADO -> UtilsMethod.getSqlDateShort()
                    radioButton.tag.toString()
                        .toInt() == CREDITO -> LocalDate.parse(UtilsMethod.getSqlDateShort())
                        .plusDays(30).toString()
                    else -> null
                }
            }
        }
        throw Exception()
    }

    private fun limpiarGuardar() {
        txtNroDocIdenti?.setText("")
        txtNombre?.setText("")
        txtDireccion?.setText("")
        txtTienda?.setText("")
        rdbTienda.tag = ""
        txtMoneda.setText("")
        rdbMoneda.tag = ""
        txtPlaca.setText("")
        tblArticuloDetail.removeAllViews()
    }
}