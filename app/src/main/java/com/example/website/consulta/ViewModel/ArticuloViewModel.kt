package com.example.website.consulta.ViewModel

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.widget.TableLayout
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.View.ConsultaItemsVenta
import com.example.website.consulta.View.TableAdapter

class ArticuloViewModel(val context: Context) : AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private var articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private lateinit var progresDialog: ProgressDialog
    private lateinit var tableLayoutArticulo: TableLayout
    val columnas = arrayOf("Codbar", "Alternante", "Descripción", "Saldo")

    fun ObtenerArticulosFactura(codbar: String, alternante: String): MutableLiveData<List<Articulo>> {
        return articuloObservable.obtenerArticulosFactura(codbar, alternante)
    }

    private fun AsignarDataArticulosEnTableAdapter(context: Context, tableLayout: TableLayout, columnas: Array<String>, lstArticulo: List<Articulo>) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeader(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
    }

    fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
        articuloTableAdapter = TableAdapter(context, tableLayout)
    }

    fun GetArtitculoAt(position: Int, codbar: String, alternante: String): Articulo? {
        val lstArticulo: List<Articulo>? = articuloObservable.obtenerArticulosFactura(codbar, alternante).value
        return lstArticulo?.get(position)
    }

    override fun onPreExecute() {
        StartAlertDialog()
    }

    private fun StartAlertDialog() {
        progresDialog = ProgressDialog(context)
        progresDialog.setCancelable(false)
        progresDialog.show()
        progresDialog.setContentView(R.layout.loading_dialog)
    }

    override fun doInBackground(vararg parameter: Any?): ArrayList<Articulo> {
        val codbar = parameter[0].toString()
        val alternante = parameter[1].toString()
        tableLayoutArticulo = parameter[2] as TableLayout
        return articuloObservable.ObtenerArticulosFactura(codbar, alternante)
    }

    override fun onPostExecute(lstArticulo: ArrayList<Articulo>) {
        AsignarDataArticulosEnTableAdapter(context, tableLayoutArticulo, columnas, lstArticulo)
        progresDialog.dismiss()
    }
}