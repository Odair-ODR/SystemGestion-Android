package com.example.website.consulta.ViewModel

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.ConsultaItemsVenta
import com.example.website.consulta.View.TableAdapter
import android.view.LayoutInflater
import android.widget.*
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.Model.MotorFragmentObservable
import com.example.website.consulta.R
import kotlinx.android.synthetic.main.fragment_motor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class ArticuloViewModel(val context: Context, val consultaItemsVenta: ConsultaItemsVenta) {
    lateinit var articuloViewModel: ArticuloViewModel
    private var articuloObservable: ArticuloObservable = ArticuloObservable()
    private var motorFragmentObservable: MotorFragmentObservable = MotorFragmentObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private lateinit var progresDialog: Dialog
    lateinit var tblArticuloHead: TableLayout
    lateinit var tblArticuloDetail: TableLayout
    private lateinit var tblMotorHead: TableLayout
    private lateinit var tblMotorDetail: TableLayout
    lateinit var horizontalScrollViewHead: HorizontalScrollView
    lateinit var horizontalScrollViewDetail: HorizontalScrollView
    lateinit var alertDialogMotor: AlertDialog

    lateinit var parameters: Parameters
    private val columnas =
        arrayOf("idArticulo", "Cpdold", "Alternante", "Descripción", "P.Venta", "Cant")

    fun getArtitculoAt(position: Int, codbar: String, alternante: String): Articulo? {
        val lstArticulo: List<Articulo>? =
            articuloObservable.obtenerArticulosFactura(codbar, alternante).value
        return lstArticulo?.get(position)
    }

    fun startLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    fun closeLoadingDialog() {
        progresDialog.dismiss()
    }

    fun obtenerArticulos(): ArrayList<Articulo>? {
        return when {
            parameters.codbar.trim().isNotEmpty() -> {
                obtenerArticulosXCodbarBackground(parameters.codbar.trim())
            }
            parameters.alternante.trim().isNotEmpty() -> {
                articuloObservable.ObtenerArticulosXAlternante(parameters.alternante.trim())
            }
            parameters.motor.trim().isNotEmpty() -> {
                obtenerArticulosXMotorCodProdBackground(
                    parameters.codProd.trim(),
                    parameters.motor.trim()
                )
            }
            else -> null
        }
    }

    private fun obtenerArticulosXCodbarBackground(codbar: String): ArrayList<Articulo> {
        val cad: Array<String> = codbar.split('.').toTypedArray()
        val marvehi: Int = cad[0].toInt()
        val num: Int = if (cad.size >= 2) cad[1].toInt() else 0
        val ini: Int = num - 30;
        val fin: Int = num + 30;
        return articuloObservable.ObtenerArticulosXCobar(marvehi, ini, fin)
    }

    private fun obtenerArticulosXMotorCodProdBackground(
        codProd: String,
        motor: String
    ): ArrayList<Articulo> {
        return motorFragmentObservable.ObtenerArticulosXMotorCodProd(codProd, motor)
    }

    fun cargarArticulos(lstArticulo: List<Articulo>) {
        instanciarTableAdapter(tblArticuloHead, tblArticuloDetail)
        articuloTableAdapter?.addHeaderArticulo(columnas)
        articuloTableAdapter?.addDataArticuloVenta(lstArticulo)
        articuloTableAdapter?.setOnRowClickListener(tblArticuloOnClickRowListener)
    }

    private fun instanciarTableAdapter(
        tableLayoutHead: TableLayout,
        tableLayoutDetail: TableLayout
    ) {
        articuloTableAdapter = TableAdapter(context, tableLayoutHead, tableLayoutDetail)
    }

    private val tblArticuloOnClickRowListener = object : TableAdapter.OnClickCallBackRow {
        override fun onClickRow(view: View?) {
            val tableRow = view as TableRow
            mostrarDialogCantidad(tableRow)
        }
    }

    private fun PasarDatosIntent(tableRow: TableRow, txtCantidad: EditText) {
        val textCell = tableRow.getChildAt(0) as TextView
        val bundle: Bundle = Bundle().also {
            it.putInt("idArticulo", textCell.text.toString().toInt())
            it.putInt("cantidad", txtCantidad.text.toString().toInt())
        }
        val intent: Intent = Intent().also {
            it.putExtra("articulo", bundle)
        }
        consultaItemsVenta.setResult(1, intent)
    }

    private fun mostrarDialogCantidad(tableRow: TableRow) {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.alert_dialog_cantidad_item, null)
        val txtCantidad = view.findViewById<EditText>(R.id._txtCantidad)
        val alertDialog =
            UtilsInterface.alertDialog3(
                context,
                view,
                consultaItemsVenta.window,
                "Cantidad item",
                onClickCallBack(tableRow, txtCantidad)
            )
        alertDialog.setOnDismissListener(alertDialogOnDismissListener)
    }

    private fun onClickCallBack(
        tableRow: TableRow,
        txtCantidad: EditText
    ): UtilsInterface.OnClickCallBack {
        val onClickCallBack = object : UtilsInterface.OnClickCallBack {
            override fun onClick() {
                PasarDatosIntent(tableRow, txtCantidad)
                consultaItemsVenta.finish()
            }
        }
        return onClickCallBack
    }

    //> tabmién funcona con UtilsInterface.alertDialog2
    private fun alertDialog_OnClick(
        tableRow: TableRow,
        txtCantidad: EditText
    ): DialogInterface.OnClickListener {
        val dialogInterface = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                PasarDatosIntent(tableRow, txtCantidad)
                consultaItemsVenta.finish()
            }
        }
        return dialogInterface
    }

    private val alertDialogOnDismissListener = DialogInterface.OnDismissListener {
        Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show()
    }

    fun initEvents() {
        horizontalScrollViewHead.setOnScrollChangeListener(
            implementHorizontalScrollViewOnScrollChangeListener
        )
        horizontalScrollViewDetail.setOnScrollChangeListener(
            implementHorizontalScrollViewOnScrollChangeListener
        )
    }

    private var implementHorizontalScrollViewOnScrollChangeListener =
        object : View.OnScrollChangeListener {
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (v?.tag == "horizontalScrollViewHead") {
                    horizontalScrollViewDetail.scrollTo(scrollX, 0)
                } else {
                    horizontalScrollViewHead.scrollTo(scrollX, 0)
                }
            }
        }

    fun mostrarAlertDialogMotores() {
        val view: View = LayoutInflater.from(context).inflate(R.layout.motor_alert_dialog, null)
        alertDialogMotor = UtilsInterface.alertDialog4(
            context,
            view,
            consultaItemsVenta.window,
            "Vista de Motores"
        )
        alertDialogMotor.show()
        inicializarControlesViewMotor(view)
    }

    private fun inicializarControlesViewMotor(view: View) {
        tblMotorHead = view.findViewById(R.id.tblMotorHead)
        tblMotorDetail = view.findViewById(R.id.tblMotorDetail)
    }

    fun obtenerMotores(codProd: String, motor: String): ArrayList<Motor> {
        return motorFragmentObservable.ObtenerMotores(codProd, motor)
    }

    fun cargarDataShowAlert(lstMotor: ArrayList<Motor>) {
        instanciarTableAdapter(tblMotorHead, tblMotorDetail)
        val headers = arrayOf("Marca", "Motor", "Cili1")
        articuloTableAdapter?.addHead(headers)
        articuloTableAdapter?.addDataMotor(lstMotor)
        articuloTableAdapter?.setOnRowClickListener(implementOnRowClickListener)
    }

    private val implementOnRowClickListener = object : TableAdapter.OnClickCallBackRow {
        override fun onClickRow(view: View?) {
            val tablerow = view as TableRow
            val textCell = tablerow.getChildAt(1) as TextView
            val motor = textCell.text.toString()
            parameters.motor = motor
            GlobalScope.launch(Dispatchers.Main) {
                startLoadingDialog()
                alertDialogMotor.dismiss()
                val lstArticulos = withContext(Dispatchers.IO) {
                    obtenerArticulos()
                }
                if (lstArticulos != null) cargarArticulos(lstArticulos)
                closeLoadingDialog()
            }
        }
    }

    class Parameters {
        var codbar = ""
        var alternante = ""
        var motor = ""
        var codProd = ""
    }
}