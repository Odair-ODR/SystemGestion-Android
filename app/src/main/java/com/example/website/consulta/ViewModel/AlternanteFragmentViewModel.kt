package com.example.website.consulta.ViewModel

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TableLayout
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.AlternanteFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class AlternanteFragmentViewModel(val context: Context) {
    private val alternanterFragmentObserver = AlternanteFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var progresDialog: Dialog? = null
    lateinit var tblArticuloHead: TableLayout
    lateinit var tblArticuloDetail: TableLayout
    lateinit var horizontalScrollViewHead: HorizontalScrollView
    lateinit var horizontalScrollViewDetail: HorizontalScrollView

    private val columns: Array<String> =
        arrayOf("Alternante", "Cpdnew", "CodBar", "Motor", "Saldo", "P.Venta")
    //> private lateinit var ITableAdapterListener: ITableAdapterListener

    fun startLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    fun closeLoadingDialog() {
        progresDialog?.dismiss()
    }

    fun obtenerArticulosXAlternante(alternante: String): ArrayList<Articulo> {
        return alternanterFragmentObserver.ObtenerArticulosXAlternante(alternante)
    }

    fun cargarDataArticulosXAlternante(lstArticulo: ArrayList<Articulo>) {
        tableAdapter = TableAdapter(context, tblArticuloHead, tblArticuloDetail)
        tableAdapter?.addHead(columns)
        tableAdapter?.addDataArticuloXAlternante(lstArticulo)
    }

    fun initEvents() {
        horizontalScrollViewHead.setOnScrollChangeListener(
            horizontalScrollViewOnScrollChangeListener
        )
        horizontalScrollViewDetail.setOnScrollChangeListener(
            horizontalScrollViewOnScrollChangeListener
        )
    }

    fun startControls(){
        horizontalScrollViewHead.tag = "horizontalScrollViewHead"
        horizontalScrollViewDetail.tag = "horizontalScrollViewDetail"
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