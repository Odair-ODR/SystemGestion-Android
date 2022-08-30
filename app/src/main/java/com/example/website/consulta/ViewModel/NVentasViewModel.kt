package com.example.website.consulta.ViewModel

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.Model.NVentasObservable
import com.example.website.consulta.View.SwipeDismissTableLayoutTouchListener
import com.example.website.consulta.View.TableAdapter
import com.example.website.consulta.dummy.Tienda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NVentasViewModel (val context: Context) : ViewModel() {

    private val nVentasObservable: NVentasObservable = NVentasObservable()
    private val articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private val columnas =
        arrayOf("idArticulo", "Cpdold", "Alternante", "Descripción", "P.Venta", "Cant")

    lateinit var tblArticuloHead: TableLayout
    lateinit var tblArticuloDetail: TableLayout
    lateinit var horizontalScrollViewHead: HorizontalScrollView
    lateinit var horizontalScrollViewDetail: HorizontalScrollView

    fun initializeEvents() {
        horizontalScrollViewHead.setOnScrollChangeListener(implementHorizontalScrollChangedListener)
        horizontalScrollViewDetail.setOnScrollChangeListener(implementHorizontalScrollChangedListener)
    }

    fun startControls(){
        horizontalScrollViewHead.tag = "horizontalScrollViewHead"
        horizontalScrollViewDetail.tag = "horizontalScrollViewDetail"
    }

    fun cargarDataTableLayout(lstArticulo: List<Articulo>) {
        inicializarTableAdapter(tblArticuloHead, tblArticuloDetail)
        articuloTableAdapter?.addHeaderArticulo(columnas)
        articuloTableAdapter?.addDataArticuloVentaReg(lstArticulo)
    }

    private fun inicializarTableAdapter(
        tableLayoutHead: TableLayout,
        tableLayoutDetail: TableLayout
    ) {
        articuloTableAdapter = TableAdapter(context, tableLayoutHead, tableLayoutDetail)
    }

    fun obtenerTiendas(): ArrayList<Tienda> {
        return nVentasObservable.ObtnerTiendas()
    }

    fun obtenerMoneda(): ArrayList<Moneda> {
        return nVentasObservable.ObtenerMoneda()
    }

    fun llamarEntidadToXRucApi(ruc: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                nVentasObservable.llamarEntidadToXRucApi(ruc)
            }
        }
    }

    fun llamarEntidadToXDniApi(dni: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                nVentasObservable.llamarEntidadToXDniApi(dni)
            }
        }
    }

    fun obtenerEntidadToApi(): LiveData<EntidadTo?> {
        return nVentasObservable.obtenerEntidadToApi()
    }

    fun obtenerIGV(): Double{
        return nVentasObservable.obtenerIGV()
    }

    fun registrarPreFacturaCabDet(
        facturaTo: FacturaCabTo,
        lstFacturaTo: ArrayList<FacturaDetTo>
    ): Boolean {
        return nVentasObservable.registrarPreFacturaCabDet(facturaTo, lstFacturaTo)
    }

    fun obtenerArticuloXIdArticulo(idArticulo: Int): Articulo? {
        return articuloObservable.obtenerArticuloXIdArticulo(idArticulo)
    }

    fun swipeDismissTouchTableAdapter() {
        val touchListener = SwipeDismissTableLayoutTouchListener(
            tblArticuloDetail,
            swipeDismissTableLayoutTouchListenerOnDismissCallback
        )
        tblArticuloDetail.setOnTouchListener(touchListener)
    }
    private val swipeDismissTableLayoutTouchListenerOnDismissCallback =
        object : SwipeDismissTableLayoutTouchListener.OnDismissCallback {
            override fun onDismiss(tableLayout: TableLayout?, reverseSortedPositions: IntArray?) {
                if (reverseSortedPositions != null && tableLayout != null) {
                    for (position in reverseSortedPositions) {
                        val child: View = tableLayout.getChildAt(position)
                        if (child is TableRow) (child as ViewGroup).removeAllViews()
                    }
                }
            }
        }

    private val implementHorizontalScrollChangedListener = object : View.OnScrollChangeListener {
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