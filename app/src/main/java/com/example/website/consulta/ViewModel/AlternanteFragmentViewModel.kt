package com.example.website.consulta.ViewModel

import android.content.Context
import android.widget.TableLayout
import com.example.website.consulta.Model.AlternanteFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class AlternanteFragmentViewModel {
    private val alternanterFragmentObserver = AlternanteFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>{
        return  alternanterFragmentObserver.ObtenerArticulosXAlternante(alternante)
    }

    fun CargarDataArticulosXAlternante(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>){
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXAlternante(lstArticulo)
    }
}