package com.example.website.consulta.View

import android.app.AlertDialog
import android.content.Intent
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Helpers.UtilsMethod
import com.example.website.consulta.Model.Entidad.FORMATO_COMPROBANTE_PDF
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import com.example.website.consulta.Model.IVerificarCancelacionFacturacion
import com.example.website.consulta.R
import com.example.website.consulta.View.Adapter.CancelacionDocumentoAdapter
import com.example.website.consulta.ViewModel.CancelacionDocumentoViewModel
import com.example.website.consulta.ViewModel.ControlDocumentoViewModel
import com.example.website.consulta.databinding.*
import com.google.gson.Gson
import java.io.File
import java.io.FileOutputStream
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


class CancelacionDocumentos : AppCompatActivity(), IVerificarCancelacionFacturacion {

    private lateinit var binding: ActivityCancelacionDocumentosBinding
    private lateinit var viewModelCancelacionDocumento: CancelacionDocumentoViewModel
    private lateinit var viewModelControlDocumento: ControlDocumentoViewModel
    private lateinit var dialogResult: AlertDialog
    private lateinit var prefacturaMutalbleList: MutableList<FacturaCabTo>
    private lateinit var tableAdapter: CancelacionDocumentoAdapter
    private lateinit var lstPreFacturaDet: List<FacturaDetTo>
    private lateinit var preFactura: FacturaCabTo
    private var pageWidth: Int = 0
    private var pageHeight: Int = 0
    private var top: Float = 0f
    private var left: Float = 0f
    private var right: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCancelacionDocumentosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        InitializeComponents()
        cargarPrefacturaTableLayout()
    }

    private fun InitializeComponents() {
        viewModelCancelacionDocumento = CancelacionDocumentoViewModel(binding.root)
        viewModelControlDocumento = ControlDocumentoViewModel()
    }

    private fun cargarPrefacturaTableLayout() {
        val preFactura = FacturaCabTo().also {
            it.idTienda = 3
            it.nroCaja = 1
        }
        //> val lstPrefactura = viewModelCancelacionDocumento.listarPreFactura(preFactura)
        prefacturaMutalbleList = viewModelCancelacionDocumento.listarPreFactura(preFactura).toMutableList()
        tableAdapter = CancelacionDocumentoAdapter(
            lstPreFactura = prefacturaMutalbleList,
            onClickListener = { prefactura, position -> btnFacturarOnClickListener(prefactura, position) }
        )
        binding.tableRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.tableRecyclerView.adapter = tableAdapter
    }

    private fun btnFacturarOnClickListener(preFactura: FacturaCabTo, position: Int) {
        val correlativoTo = viewModelControlDocumento.obtenerCorrelativoDocumento(preFactura)
        preFactura.serDoc = correlativoTo!!.serie
        preFactura.numFac = correlativoTo.numero
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_result_facturar, null, false)
        val bindingDialogResult = DialogResultFacturarBinding.bind(view)
        dialogResult = UtilsInterface.dialogResult(this, view, window)
        dialogResult.setCancelable(false)
        bindingDialogResult.txtSerie.setText(correlativoTo.serie)
        bindingDialogResult.txtNumero.setText(correlativoTo.numero.toString())
        bindingDialogResult.btnSi.setOnClickListener { btnSiOnClickListener(preFactura, position) }
        bindingDialogResult.btnNo.setOnClickListener { btnNoOnClickListener(dialogResult) }
        dialogResult.show()
        /*val lstPreFacturaDet: List<FacturaDetTo> = viewModelCancelacionDocumento.obtenerPreFacturaDet(preFactura.idPreFactura)
        generarComprobantePDF.generarPDF(preFactura, lstPreFacturaDet)
        lanzarPdfView(preFactura)*/
    }

    private fun btnSiOnClickListener(preFactura: FacturaCabTo, position: Int) {
        val lstPreFacturaDet: List<FacturaDetTo> = viewModelCancelacionDocumento.obtenerPreFacturaDet(preFactura.idPreFactura)
        val resultSave = viewModelCancelacionDocumento.grabarCancelacionDocumentosFacturacion(
            preFactura,
            lstPreFacturaDet,
            this
        )
        if (resultSave) {
            deletePrefactura(position)
            val comprobantePdf = obtenerFormatoComprobante()
            comprobantePdf.generarPDF(preFactura, lstPreFacturaDet)
            this.preFactura = preFactura
        }
    }

    private fun deletePrefactura(position: Int) {
        prefacturaMutalbleList.removeAt(position)
        tableAdapter.notifyItemRemoved(position)
    }

    private fun btnNoOnClickListener(dialogResult: AlertDialog) {
        dialogResult.dismiss()
    }

    override fun success() {
        dialogResult.dismiss()
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_result_success, null, false)
        val bindingDialogResult = DialogResultSuccessBinding.bind(view)
        val dialogResultSucc = UtilsInterface.dialogResult(this, view, window)
        dialogResultSucc.setCancelable(false)
        bindingDialogResult.lblTitle.text = getString(R.string.successTitle)
        bindingDialogResult.lblMessage.text = getString(R.string.lblmessageSuccessCanFact)
        bindingDialogResult.btnContinuar.setOnClickListener { btnContinuarOnClick(dialogResultSucc) }
        dialogResultSucc.show()
    }

    override fun error() {
        dialogResult.dismiss()
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_result_error, null, false)
        val bindingDialogResult = DialogResultErrorBinding.bind(view)
        val dialogResultErr = UtilsInterface.dialogResult(this, view, window)
        dialogResultErr.setCancelable(false)
        bindingDialogResult.lblTitle.text = getString(R.string.errorTitle)
        bindingDialogResult.lblMessage.text = getString(R.string.lblmessageErrorCanFact)
        bindingDialogResult.btnContinuar.setOnClickListener { dialogResultErr.dismiss() }
        dialogResultErr.show()
    }

    private fun btnContinuarOnClick(dialogResult: AlertDialog) {
        dialogResult.dismiss()
        //> lanzarPdfView(preFactura)
    }

    private fun lanzarPdfView(preFactura: FacturaCabTo) {
        val gson = Gson()
        val intent = Intent(this, PdfView::class.java)
        intent.putExtra("PreFactura", gson.toJson(preFactura))
        startActivity(intent)
    }

    private fun obtenerFormatoComprobante(): IComprobante {
        val tipoComprobante = FORMATO_COMPROBANTE_PDF.TICKET
        return when (tipoComprobante) {
            FORMATO_COMPROBANTE_PDF.A4 -> GenerarComprobanteFacturaPDF(this)
            FORMATO_COMPROBANTE_PDF.TICKET -> ComprobanteTicket(this)
        }
    }
}