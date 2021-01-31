package com.example.website.consulta.ViewModel

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.View.ConsultaItemsVenta
import com.example.website.consulta.View.Nventas
import com.example.website.consulta.View.TableAdapter

class ArticuloViewModel(val context: Context, val consultaItemsVenta: ConsultaItemsVenta) : AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private var articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private lateinit var progresDialog: ProgressDialog
    private lateinit var tableLayoutArticulo: TableLayout
    private val columnas = arrayOf("idArticulo", "Cpdold", "SuperArti", "Marca", "Alternante", "Descripción", "P.Venta")

    private fun AsignarDataArticulosEnTableAdapter(context: Context, tableLayout: TableLayout, columnas: Array<String>, lstArticulo: List<Articulo>) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeaderArticulo(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
    }

    private fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
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

    override fun doInBackground(vararg parameter: Any?): ArrayList<Articulo>? {
        val codbar = parameter[0].toString()
        val alternante = parameter[1].toString()
        tableLayoutArticulo = parameter[2] as TableLayout
        if(codbar.trim() != ""){
            val cad: Array<String> = codbar.split('.' ).toTypedArray()
            val marvehi: Int = cad[0].toInt()
            val num: Int = cad[1].toInt();
            val ini: Int = num - 30;
            val fin: Int = num + 30;
            return articuloObservable.ObtenerArticulosXCobar(marvehi, ini, fin)
        }
        else if (alternante.trim() != ""){
            return articuloObservable.ObtenerArticulosXAlternante( alternante)
        }
        return null
    }

    override fun onPostExecute(lstArticulo: ArrayList<Articulo>) {
        AsignarDataArticulosEnTableAdapter(context, tableLayoutArticulo, columnas, lstArticulo)
        RowArticuloFactura_OnClickListener()
        progresDialog.dismiss()
    }


    private fun RowArticuloFactura_OnClickListener() {
        var tableRow: TableRow
        for (oi in 1 until tableLayoutArticulo.childCount) {
            tableRow = tableLayoutArticulo.getChildAt(oi) as TableRow
            tableRow.isClickable = true
            tableRow.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    PasarDatosIntent(tableRow)
                    consultaItemsVenta.finish()
                    //> consultaItemsVenta.onBackPressed()
                }
            })
        }
    }

    private fun PasarDatosIntent(tableRow: TableRow) {
        val textCell = tableRow.getChildAt(0) as TextView
        val bundle: Bundle = Bundle().also {
            it.putInt("idArticulo", textCell.text.toString().toInt())
        }
        val intent: Intent = Intent().also {
            it.putExtra("articulo", bundle)
        }
        consultaItemsVenta.setResult(1, intent)
    }
}