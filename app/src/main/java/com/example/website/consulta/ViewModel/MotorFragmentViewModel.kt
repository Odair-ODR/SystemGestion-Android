package com.example.website.consulta.ViewModel

import android.content.Context
import android.widget.TableLayout
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.Model.MotorFragmentObservable
import com.example.website.consulta.View.TableAdapter

class MotorFragmentViewModel {
    private val motorFragmentObservable: MotorFragmentObservable = MotorFragmentObservable()
    private var tableAdapter: TableAdapter? = null

    fun ObtenerMotores(codProd: String, motor: String): ArrayList<Motor> {
        return motorFragmentObservable.ObtenerMotores(codProd, motor)
    }

    fun CargarDataShowAlert(context: Context, tableLayout: TableLayout, headers: Array<String>, lstMotor: ArrayList<Motor>) {
        tableAdapter = TableAdapter(context, tableLayout)
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataMotor(lstMotor)
    }

    fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo> {
        return motorFragmentObservable.ObtenerArticulosXMotorCodProd(codProd, motor)
    }

    fun CargarDataLayaoutArticulos(context: Context, tableLayout: TableLayout, headers: Array<String>, lstArticulo: ArrayList<Articulo>) {
        if(tableAdapter == null){
            tableAdapter = TableAdapter(context, tableLayout)
        }
        tableAdapter?.AddHeader(headers)
        tableAdapter?.AddDataArticuloXMotorCodProd(lstArticulo)
    }
}