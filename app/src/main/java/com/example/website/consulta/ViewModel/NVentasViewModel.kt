package com.example.website.consulta.ViewModel

import android.app.ProgressDialog
import android.content.Context
import android.widget.TableLayout
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.NVentasObservable
import com.example.website.consulta.View.TableAdapter
import com.example.website.consulta.dummy.Tienda

class NVentasViewModel(val context: Context) {

    private val nVentasObservable: NVentasObservable = NVentasObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private val columnas = arrayOf("idArticulo", "Cpdold", "SuperArti", "Marca", "Alternante", "Descripción", "P.Venta")

    fun ObtenerTiendas(): ArrayList<String> {
        return nVentasObservable.ObtnerTiendas()
    }

    fun ObtenerArticuloXIdArticulo(idArticulo: Int) : Articulo?{
        return nVentasObservable.ObtenerArticuloXIdArticulo(idArticulo)
    }

    fun CargarDataTableLayout(tableLayout: TableLayout, lstArticulo: List<Articulo>) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeaderArticulo(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
    }

    private fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
        articuloTableAdapter = TableAdapter(context, tableLayout)
    }
}