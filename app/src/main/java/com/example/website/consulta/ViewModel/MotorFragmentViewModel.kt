package com.example.website.consulta.ViewModel

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TableLayout
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.Model.MotorFragmentObservable
import com.example.website.consulta.R
import com.example.website.consulta.View.TableAdapter

class MotorFragmentViewModel(val context: Context) : AsyncTask<Any, Any, ArrayList<Motor>>() {
    private val motorFragmentObservable: MotorFragmentObservable = MotorFragmentObservable()
    private var tableAdapter: TableAdapter? = null
    private var progresDialog: ProgressDialog? = null
    private var alertDialog: AlertDialog? = null
    private var tableLayoutMotor: TableLayout? = null
    private val headersMotor = arrayOf("Marca", "Motor", "Cili1")
    private val headersArticulo = arrayOf("CpdNew", "Alternante", "CodBar", "UM", "Can", "Sal", "P.Venta")
    private var codProd: String = ""

    var tableLayoutArticulo: TableLayout? = null
    var txtMotor: EditText? = null

    private fun ObtenerMotores(codProd: String, motor: String): ArrayList<Motor> {
        return motorFragmentObservable.ObtenerMotores(codProd, motor)
    }

    private fun CargarDataShowAlert(alertDialog: AlertDialog, tableLayout: TableLayout, headers: Array<String>, lstMotor: ArrayList<Motor>) {
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataMotor(lstMotor, alertDialog, txtMotor!!)
    }

    private fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo> {
        return motorFragmentObservable.ObtenerArticulosXMotorCodProd(codProd, motor)
    }

    fun CargarDataLayaoutArticulos(tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>) {
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXMotorCodProd(lstArticulo)
    }

    override fun onPreExecute() {
        StartLoadingDialog()
        StartAlerDialog()
    }

    private fun StartLoadingDialog() {
        progresDialog = ProgressDialog(context)
        progresDialog?.setCancelable(false)
        progresDialog?.show()
        progresDialog?.setContentView(R.layout.loading_dialog)
    }

    private fun StartAlerDialog() {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setTitle("Vista de Motores")
        alertBuilder.setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })
        val view: View = LayoutInflater.from(context).inflate(R.layout.motor_alert_dialog, null)
        alertBuilder.setView(view)
        alertDialog = alertBuilder.create()
        alertDialog?.setOnDismissListener(AlertDialog_OnDismissListener)
        tableLayoutMotor = view.findViewById(R.id.tableLayoutMotores)
    }

    override fun doInBackground(vararg params: Any?): ArrayList<Motor> {
        codProd = params[0].toString()
        val motor = params[1].toString()
        return ObtenerMotores(codProd, motor)
    }

    override fun onPostExecute(result: ArrayList<Motor>) {
        try {
            CargarDataShowAlert(alertDialog!!, tableLayoutMotor!!, headersMotor, result)
            alertDialog?.show()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        progresDialog?.dismiss()
    }

    private var AlertDialog_OnDismissListener = DialogInterface.OnDismissListener {
        val lstArticulo = ObtenerArticulosXMotorCodProd(codProd, txtMotor?.text.toString())
        CargarDataLayaoutArticulos(tableLayoutArticulo!!, headersArticulo, lstArticulo)
    }
}