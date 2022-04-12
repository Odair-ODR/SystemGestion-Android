package com.example.website.consulta.ViewModel

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.ArticuloObservable
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.View.ConsultaItemsVenta
import com.example.website.consulta.View.TableAdapter
import android.view.LayoutInflater
import android.widget.*
import com.example.website.consulta.R

class ArticuloViewModel(val context: Context, val consultaItemsVenta: ConsultaItemsVenta) :
    AsyncTask<Any, Any, ArrayList<Articulo>>() {
    private var articuloObservable: ArticuloObservable = ArticuloObservable()
    private var articuloTableAdapter: TableAdapter? = null
    private lateinit var progresDialog: Dialog
    private lateinit var tableLayoutArticulo: TableLayout
    private val columnas =
        arrayOf("idArticulo", "Cpdold", "Alternante", "Descripción", "P.Venta", "Cant")

    private fun ObtenerTableAdapter(context: Context, tableLayout: TableLayout) {
        articuloTableAdapter = TableAdapter(context, tableLayout)
    }

    fun GetArtitculoAt(position: Int, codbar: String, alternante: String): Articulo? {
        val lstArticulo: List<Articulo>? =
            articuloObservable.obtenerArticulosFactura(codbar, alternante).value
        return lstArticulo?.get(position)
    }

    override fun onPreExecute() {
        StartAlertDialog()
    }

    private fun StartAlertDialog() {
        progresDialog = UtilsInterface.progressDialog(context)
    }

    override fun doInBackground(vararg parameter: Any?): ArrayList<Articulo>? {
        val codbar = parameter[0].toString()
        val alternante = parameter[1].toString()
        tableLayoutArticulo = parameter[2] as TableLayout
        if (codbar.trim().length != 0) {
            val cad: Array<String> = codbar.split('.').toTypedArray()
            val marvehi: Int = cad[0].toInt()
            val num: Int = cad[1].toInt();
            val ini: Int = num - 30;
            val fin: Int = num + 30;
            return articuloObservable.ObtenerArticulosXCobar(marvehi, ini, fin)
        } else if (alternante.trim().length != 0) {
            return articuloObservable.ObtenerArticulosXAlternante(alternante)
        }
        return null
    }

    override fun onPostExecute(lstArticulo: ArrayList<Articulo>?) {
        if (lstArticulo != null) {
            AsignarDataArticulosEnTableAdapter(context, tableLayoutArticulo, columnas, lstArticulo)
            RowArticuloFactura_OnClickListener()
        }
        progresDialog.dismiss()
    }

    private fun AsignarDataArticulosEnTableAdapter(
        context: Context,
        tableLayout: TableLayout,
        columnas: Array<String>,
        lstArticulo: List<Articulo>
    ) {
        ObtenerTableAdapter(context, tableLayout)
        articuloTableAdapter?.AddHeaderArticulo(columnas)
        articuloTableAdapter?.AddDataArticuloVenta(lstArticulo)
    }

    private fun RowArticuloFactura_OnClickListener() {
        for (oi in 1 until tableLayoutArticulo.childCount) {
            val tableRow = tableLayoutArticulo.getChildAt(oi) as TableRow
            tableRow.isClickable = true
            tableRow.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    mostrarDialogCantidad(tableRow)
                }
            })
        }
    }

    private fun PasarDatosIntent(tableRow: TableRow, txtCantidad: EditText) {
        val textCell = tableRow.getChildAt(0) as TextView
        val bundle: Bundle = Bundle().also {
            it.putInt("idArticulo", textCell.text.toString().toInt())
            it.putInt("cantidad", txtCantidad.text.toString().toInt())
        }
        val intent: Intent = Intent().also {
            it.putExtra("articulo", bundle)
        }
        consultaItemsVenta.setResult(1, intent)
    }

    private fun mostrarDialogCantidad(tableRow: TableRow) {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.alert_dialog_cantidad_item, null)
        val txtCantidad = view.findViewById<EditText>(R.id._txtCantidad)
        val alertDialog =
            UtilsInterface.alertDialog3(
                context,
                view,
                consultaItemsVenta.window,
                "Cantidad item",
                onClickCallBack(tableRow, txtCantidad)
            )
        alertDialog.setOnDismissListener(alertDialog_OnDismissListener)
    }

    private fun onClickCallBack(
        tableRow: TableRow,
        txtCantidad: EditText
    ): UtilsInterface.OnClickCallBack {
        val onClickCallBack = object : UtilsInterface.OnClickCallBack {
            override fun onClick() {
                PasarDatosIntent(tableRow, txtCantidad)
                consultaItemsVenta.finish()
            }
        }
        return onClickCallBack
    }

    //> tabmién funcona con UtilsInterface.alertDialog2
    private fun alertDialog_OnClick(
        tableRow: TableRow,
        txtCantidad: EditText
    ): DialogInterface.OnClickListener {
        val dialogInterface = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                PasarDatosIntent(tableRow, txtCantidad)
                consultaItemsVenta.finish()
            }
        }
        return dialogInterface
    }

    private val alertDialog_OnDismissListener = DialogInterface.OnDismissListener {
        Toast.makeText(context, "Cancelado", Toast.LENGTH_LONG).show()
    }
}