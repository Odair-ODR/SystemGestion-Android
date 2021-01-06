package com.example.website.consulta.ViewModel

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.TableLayout
import com.example.website.consulta.Model.AlternanteFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.View.TableAdapter

class AlternanteFragmentViewModel(val context: Context): AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private val alternanterFragmentObserver = AlternanteFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var tableLayoutArticulo: TableLayout? = null
    private var progresDialog: ProgressDialog? = null
    private val columns: Array<String> = arrayOf("Alternante", "Cpdnew", "CodBar", "Motor", "Saldo")

    private fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>{
        return  alternanterFragmentObserver.ObtenerArticulosXAlternante(alternante)
    }

    private fun CargarDataArticulosXAlternante(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>){
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXAlternante(lstArticulo)
    }

    override fun onPreExecute() {
        StartLoadingDialog()
    }

    private fun StartLoadingDialog() {
        progresDialog = ProgressDialog(context)
        progresDialog?.setCancelable(false)
        progresDialog?.show()
        progresDialog?.setContentView(R.layout.loading_dialog)
    }

    override fun doInBackground(vararg parameter: Any?): ArrayList<Articulo> {
        val alternante = parameter[0].toString()
        tableLayoutArticulo = parameter[1] as TableLayout
        return ObtenerArticulosXAlternante(alternante)
    }

    override fun onProgressUpdate(vararg values: Any?) {

    }

    override fun onPostExecute(lstArticulo: ArrayList<Articulo>) {
        CargarDataArticulosXAlternante(context, tableLayoutArticulo!!, columns, lstArticulo)
        progresDialog?.dismiss()
    }
}