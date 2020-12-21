package com.example.website.consulta.ViewModel

import android.content.Context
import android.widget.TableLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.View.MasterAdapter
import com.example.website.consulta.View.TableAdapter

class ArticuloViewModel: ViewModel() {
    private var articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter ? = null

    fun ObtenerArticulosFactura(codbar: String, alternante: String): MutableLiveData<List<Articulo>>{
        return articuloObservable.obtenerArticulosFactura(codbar, alternante)
    }

    fun AsignarDataArticulosEnTableAdapter(context: Context, tableLayout: TableLayout, columnas: Array<String>, lstArticulo: List<Articulo>) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeader(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
        //> articuloAdapter = MasterAdapter(this, R.layout.row)
        //> articuloAdapter?.setListArticulo(lstArticulo)
        //> articuloTableAdapter?.notifyDataSetChanged()
    }

    fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
        articuloTableAdapter = TableAdapter(context, tableLayout)
    }

    fun GetArtitculoAt(position: Int, codbar: String, alternante: String): Articulo? {
        var lstArticulo:List<Articulo>? = articuloObservable.obtenerArticulosFactura(codbar, alternante).value
        return lstArticulo?.get(position)
    }
}