package com.example.website.consulta.ViewModel

import android.content.Context
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.Model.NVentasObservable
import com.example.website.consulta.View.TableAdapter
import com.example.website.consulta.dummy.Tienda
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NVentasViewModel(val context: Context) : ViewModel() {

    private val nVentasObservable: NVentasObservable = NVentasObservable()
    private val articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private val columnas = arrayOf("idArticulo", "Cpdold", "Alternante", "Descripción", "P.Venta")

    fun ObtenerArticuloXIdArticulo(idArticulo: Int): Articulo? {
        return articuloObservable.obtenerArticuloXIdArticulo(idArticulo)
    }

    fun CargarDataTableLayout(tableLayout: TableLayout, lstArticulo: List<Articulo>) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeaderArticulo(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
    }

    private fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
        articuloTableAdapter = TableAdapter(context, tableLayout)
    }

    fun ObtenerTiendas(): ArrayList<Tienda> {
        return nVentasObservable.ObtnerTiendas()
    }

    fun ObtenerMoneda(): ArrayList<Moneda> {
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

    fun registrarPreFacturaCabDet(facturaTo: FacturaCabTo, lstFacturaTo: ArrayList<FacturaDetTo>): Boolean {
     return nVentasObservable.registrarPreFacturaCabDet(facturaTo, lstFacturaTo)
    }

    fun obtenerArticuloXIdArticulo(idArticulo: Int): Articulo? {
        return articuloObservable.obtenerArticuloXIdArticulo(idArticulo)
    }
}