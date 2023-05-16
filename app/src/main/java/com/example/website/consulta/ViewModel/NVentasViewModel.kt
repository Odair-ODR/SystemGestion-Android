package com.example.website.consulta.ViewModel

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.Model.MultipleObservable
import com.example.website.consulta.Model.NVentasObservable
import com.example.website.consulta.View.SwipeDismissTableLayoutTouchListener
import com.example.website.consulta.View.TableAdapter
import com.example.website.consulta.dummy.Tienda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode

class NVentasViewModel() : ViewModel() {

    private val nVentasObservable: NVentasObservable = NVentasObservable()
    private val articuloObservable: ArticuloObservable = ArticuloObservable()
    private val multipleObservable = MultipleObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private val columnas =
        arrayOf("idArticulo", "Cpdold", "Alternante", "Descripción", "P.Venta", "Cant")

    private lateinit var tableReferences: TableReferences

    fun initializeTableReferences(tableReferences: TableReferences) {
        this.tableReferences = tableReferences
    }

    fun initializeEvents() {
        tableReferences.horizontalScrollViewHead.setOnScrollChangeListener(implementHorizontalScrollChangedListener)
        tableReferences.horizontalScrollViewDetail.setOnScrollChangeListener(
            implementHorizontalScrollChangedListener
        )
    }

    fun startControls() {
        tableReferences.horizontalScrollViewHead.tag = "horizontalScrollViewHead"
        tableReferences.horizontalScrollViewDetail.tag = "horizontalScrollViewDetail"
    }

    fun cargarDataTableLayout(lstArticulo: List<Articulo>) {
        inicializarTableAdapter(tableReferences.tblArticuloHead, tableReferences.tblArticuloDetail)
        articuloTableAdapter?.addHeaderArticulo(columnas)
        articuloTableAdapter?.addDataArticuloVentaReg(lstArticulo)
    }

    private fun inicializarTableAdapter(
        tableLayoutHead: TableLayout,
        tableLayoutDetail: TableLayout
    ) {
        articuloTableAdapter = TableAdapter(tableReferences.tblArticuloDetail.context, tableLayoutHead, tableLayoutDetail)
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

    fun obtenerIGV(): Double {
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
            tableReferences.tblArticuloDetail,
            swipeDismissTableLayoutTouchListenerOnDismissCallback
        )
        tableReferences.tblArticuloDetail.setOnTouchListener(touchListener)
    }

    private val swipeDismissTableLayoutTouchListenerOnDismissCallback =
        object : SwipeDismissTableLayoutTouchListener.OnDismissCallback {
            override fun onDismiss(tableLayout: TableLayout?, reverseSortedPositions: IntArray?) {
                if (reverseSortedPositions != null && tableLayout != null) {
                    for (position in reverseSortedPositions) {
                        val child: View = tableLayout.getChildAt(position)
                        if (child is TableRow) {
                            (child as ViewGroup).removeAllViews()
                            tableLayout.removeView(child)
                        }
                    }
                    calcularTotalTabla(tableReferences.tblArticuloDetail)
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
                tableReferences.horizontalScrollViewDetail.scrollTo(scrollX, 0)
            } else {
                tableReferences.horizontalScrollViewHead.scrollTo(scrollX, 0)
            }
        }
    }

    fun obtenerTiposDeCambio(): List<TipoCambioTo> {
        return multipleObservable.obtenerTiposDeCambio()
    }

    private val _totalVentaTabla = MutableLiveData<DoubleArray>()
    val totalVentaTabla: LiveData<DoubleArray> = _totalVentaTabla
    fun calcularTotalTabla(tabla: TableLayout) {
        var total = 0.0

        for (i in 0 until tabla.childCount) {
            val fila = tabla.getChildAt(i) as TableRow
            val tvPrecioVenta = fila.getChildAt(4) as TextView
            val precioVenta = java.lang.Double.parseDouble(tvPrecioVenta.text.toString())
            total += precioVenta
        }
        val valVen = BigDecimal(total / obtenerIGV()).setScale(2, RoundingMode.HALF_UP).toDouble()
        val valBru = BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toDouble()
        val valIGV = BigDecimal(valBru - valVen).setScale(2, RoundingMode.HALF_UP).toDouble()
        val totalesVenta = doubleArrayOf(valVen, valIGV, valBru)
        _totalVentaTabla.value = totalesVenta
    }
}