package com.example.website.consulta.ViewModel

import android.content.Context
import android.widget.TableLayout
import com.example.website.consulta.Model.CodbarFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class CodbarFragmentViewModel {
    private val codbarFragmentObservable = CodbarFragmentObservable()
    private var tableAdapter:TableAdapter? = null
    fun ObtenerArticulosXCodbar(codbar: String): ArrayList<Articulo>{
        return codbarFragmentObservable.ObtenerArticuloXCodbar(codbar)
    }

    fun CargarAticulosXCobar(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>){
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXCodbar(lstArticulo)
    }
}