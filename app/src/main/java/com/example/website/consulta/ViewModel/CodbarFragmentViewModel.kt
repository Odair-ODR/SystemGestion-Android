package com.example.website.consulta.ViewModel

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
import android.widget.TableLayout
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.CodbarFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class CodbarFragmentViewModel(val context: Context) : AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private val codbarFragmentObservable = CodbarFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var progresDialog: Dialog? = null
    private var tableLayoutArticulo: TableLayout? = null
    private val columns: Array<String> = arrayOf("Alternante", "Campar", "Cpdnew", "Unimed", "Motor", "Saldo", "P.Venta")

    private fun ObtenerArticulosXCodbar(codbar: String): ArrayList<Articulo> {
        return codbarFragmentObservable.ObtenerArticuloXCodbar(codbar)
    }

    private fun CargarArticulosXCobar(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>) {
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXCodbar(lstArticulo)
    }

    override fun onPreExecute() {
        StartLoadingDialog()
    }

    private fun StartLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    override fun doInBackground(vararg params: Any?): ArrayList<Articulo> {
        val codbar = params[0].toString()
        tableLayoutArticulo = params[1] as TableLayout
        return  ObtenerArticulosXCodbar(codbar)
    }

    override fun onProgressUpdate(vararg values: Any?) {

    }

    override fun onPostExecute(lstArticulo: ArrayList<Articulo>) {
        CargarArticulosXCobar(context, tableLayoutArticulo!!, columns, lstArticulo)
        progresDialog?.dismiss()
    }
}