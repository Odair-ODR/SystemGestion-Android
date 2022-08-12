package com.example.website.consulta.ViewModel

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.Model.MotorFragmentObservable
import com.example.website.consulta.R
import com.example.website.consulta.View.TableAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MotorFragmentViewModel(val context: Context) {
    private val motorFragmentObservable: MotorFragmentObservable = MotorFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var progresDialog: Dialog? = null
    private var alertDialog: AlertDialog? = null
    private lateinit var tblMotorHead: TableLayout
    private lateinit var tblMotorDetail: TableLayout
    private val headersMotor = arrayOf("Marca", "Motor", "Cili1")
    private val headersArticulo =
        arrayOf("CpdNew", "Alternante", "CodBar", "UM", "Can", "Sal", "P.Venta")
    private var codProd: String = ""
    private var motor: String = ""

    lateinit var tblArticuloHead: TableLayout
    lateinit var tblArticuloDetail: TableLayout
    lateinit var horizontalScrollViewHead: HorizontalScrollView
    lateinit var horizontalScrollViewDetail: HorizontalScrollView

    private fun obtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo> {
        return motorFragmentObservable.ObtenerArticulosXMotorCodProd(codProd, motor)
    }

    fun startLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    fun closeLoadingDialog() {
        progresDialog?.dismiss()
    }

    fun startAlerDialogMotores() {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle("Vista de Motores")
        alertBuilder.setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })
        val view: View = LayoutInflater.from(context).inflate(R.layout.motor_alert_dialog, null)
        alertBuilder.setView(view)
        alertDialog = alertBuilder.create()
        alertDialog?.setOnDismissListener(AlertDialogOnDismissListener)
        alertDialog?.show()
        inicializarTableLayoutsMotor(view)
    }

    private fun inicializarTableLayoutsMotor(view: View) {
        tblMotorHead = view.findViewById(R.id.tblMotorHead)
        tblMotorDetail = view.findViewById(R.id.tblMotorDetail)
    }

    fun obtenerMotores(codProd: String, motor: String): ArrayList<Motor> {
        this.codProd = codProd
        this.motor = motor
        return motorFragmentObservable.ObtenerMotores(codProd, motor)
    }

    fun cargarDataMotor(
        lstMotor: ArrayList<Motor>
    ) {
        inicializarTableAdapter(tblMotorHead, tblMotorDetail)
        tableAdapter?.addHead(headersMotor)
        tableAdapter?.addDataMotor(lstMotor)
        tableAdapter?.setOnRowClickListener(implementOnRowClickListener)
    }

    private fun inicializarTableAdapter(
        tablelayoutHead: TableLayout,
        tableLayoutDetail: TableLayout
    ) {
        tableAdapter = TableAdapter(context, tablelayoutHead, tableLayoutDetail)
    }

    private val implementOnRowClickListener = object : TableAdapter.OnClickCallBackRow {
        override fun onClickRow(view: View?) {
            val tablerow = view as TableRow
            val textCell = tablerow.getChildAt(1) as TextView
            motor = textCell.text.toString()
            alertDialog?.dismiss()
        }
    }

    private var AlertDialogOnDismissListener = DialogInterface.OnDismissListener {
        GlobalScope.launch(Dispatchers.Main) {
            startLoadingDialog()
            val lstArticulo = withContext(Dispatchers.IO) {
                obtenerArticulosXMotorCodProd(codProd, motor)
            }
            cargarDataLayaoutArticulos(lstArticulo)
            closeLoadingDialog()
        }
    }

    private fun cargarDataLayaoutArticulos(lstArticulo: ArrayList<Articulo>) {
        inicializarTableAdapter(tblArticuloHead, tblArticuloDetail)
        tableAdapter?.addHead(headersArticulo)
        tableAdapter?.AddDataArticuloXMotorCodProd(lstArticulo)
    }
}