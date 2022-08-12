package com.example.website.consulta.ViewModel

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TableLayout
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.CodbarFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class CodbarFragmentViewModel(val context: Context) {
    private val codbarFragmentObservable = CodbarFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var progresDialog: Dialog? = null
    lateinit var tblArticuloHead: TableLayout
    lateinit var tblArticuloDetail: TableLayout
    lateinit var horizontalScrollViewHead: HorizontalScrollView
    lateinit var horizontalScrollViewDetail: HorizontalScrollView
    private val columns: Array<String> =
        arrayOf("Alternante", "Campar", "Cpdnew", "Unimed", "Motor", "Saldo", "P.Venta")

    fun initializeEvents() {
        initEventsScrollView()
    }

    fun startLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    fun closeLoadingDialog(){
        progresDialog?.dismiss()
    }

    fun obtenerArticulosXCodbar(codbar: String): ArrayList<Articulo> {
        return codbarFragmentObservable.ObtenerArticuloXCodbar(codbar)
    }

    fun cargarArticulosXCobar(lstArticulo: ArrayList<Articulo>) {
        inicializarTableAdapter(tblArticuloHead, tblArticuloDetail)
        tableAdapter?.addHead(columns)
        tableAdapter?.AddDataArticuloXCodbar(lstArticulo)
    }

    private fun inicializarTableAdapter(
        tableLayoutHead: TableLayout,
        tableLayoutDetail: TableLayout
    ) {
        tableAdapter = TableAdapter(context, tableLayoutHead, tableLayoutDetail)
    }

    private fun initEventsScrollView() {
        horizontalScrollViewHead.setOnScrollChangeListener(
            horizontalScrollViewOnScrollChangeListener
        )
        horizontalScrollViewDetail.setOnScrollChangeListener(
            horizontalScrollViewOnScrollChangeListener
        )
    }

    private var horizontalScrollViewOnScrollChangeListener = object : View.OnScrollChangeListener {
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
}