package com.example.website.consulta.ViewModel

import android.app.Dialog
import android.content.Context
import android.os.AsyncTask
import android.widget.TableLayout
import android.widget.TableRow
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.AlternanteFragmentObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.TableAdapter

class AlternanteFragmentViewModel(val context: Context): AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private val alternanterFragmentObserver = AlternanteFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var tableLayoutArticulo: TableLayout? = null
    private var progresDialog: Dialog? = null
    private val columns: Array<String> = arrayOf("Alternante", "Cpdnew", "CodBar", "Motor", "Saldo", "P.Venta")
    //> private lateinit var ITableAdapterListener: ITableAdapterListener

    private fun ObtenerArticulosXAlternante(alternante: String): ArrayList<Articulo>{
        return  alternanterFragmentObserver.ObtenerArticulosXAlternante(alternante)
    }

    private fun CargarDataArticulosXAlternante(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>){
        tableAdapter = TableAdapter(context, tableLayout
        )
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXAlternante(lstArticulo)
    }

    override fun onPreExecute() {
        StartLoadingDialog()
    }

    private fun StartLoadingDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
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

    private fun onClick(){
        val tableRow: TableRow? = null
        //> ITableAdapterListener.onClickArticulo(tableRow!!)
    }
}