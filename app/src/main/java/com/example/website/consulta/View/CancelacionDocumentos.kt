package com.example.website.consulta.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import android.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import com.example.website.consulta.Model.IVerificarCancelacionFacturacion
import com.example.website.consulta.R
import com.example.website.consulta.View.Adapter.CancelacionDocumentoAdapter
import com.example.website.consulta.ViewModel.CancelacionDocumentoViewModel
import com.example.website.consulta.ViewModel.ControlDocumentoViewModel
import com.example.website.consulta.databinding.*
import java.text.FieldPosition

class CancelacionDocumentos : AppCompatActivity(), IVerificarCancelacionFacturacion {

    private lateinit var binding: ActivityCancelacionDocumentosBinding
    private lateinit var viewModelCancelacionDocumento: CancelacionDocumentoViewModel
    private lateinit var viewModelControlDocumento: ControlDocumentoViewModel
    private lateinit var dialogResult: AlertDialog
    private lateinit var prefacturaMutalbleList: MutableList<FacturaCabTo>
    private lateinit var tableAdapter: CancelacionDocumentoAdapter

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
        preFactura.numFac = correlativoTo!!.numero
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_result_facturar, null, false)
        val bindingDialogResult = DialogResultFacturarBinding.bind(view)
        dialogResult = UtilsInterface.dialogResult(this, view, window)
        bindingDialogResult.txtSerie.setText(correlativoTo.serie)
        bindingDialogResult.txtNumero.setText(correlativoTo.numero.toString())
        bindingDialogResult.btnSi.setOnClickListener { btnSiOnClickListener(preFactura, position) }
        bindingDialogResult.btnNo.setOnClickListener { btnNoOnClickListener(dialogResult) }
        dialogResult.show()
    }

    private fun btnSiOnClickListener(preFactura: FacturaCabTo, position: Int) {
        val lstPreFacturaDet: List<FacturaDetTo> = viewModelCancelacionDocumento.obtenerPreFacturaDet(preFactura.idPreFactura)
        val resultSave = viewModelCancelacionDocumento.grabarCancelacionDocumentosFacturacion(
            preFactura,
            lstPreFacturaDet,
            this
        )
        if (resultSave) deletePrefactura(position)
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
        bindingDialogResult.btnContinuar.setOnClickListener { dialogResultSucc.dismiss() }
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

    private fun removePrefacturaTbl(index: Int) {
        for (i in 0 until binding.tableRecyclerView.childCount) {
            val tableRow = binding.tableRecyclerView.getChildAt(i)

        }
    }
}