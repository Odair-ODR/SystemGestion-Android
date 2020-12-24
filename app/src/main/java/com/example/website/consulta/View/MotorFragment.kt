package com.example.website.consulta.View

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.MotorFragmentViewModel
import java.sql.CallableStatement
import java.util.*
import kotlin.collections.ArrayList

class MotorFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtMotor: EditText? = null
    private var txtCodProd: EditText? = null
    private var btnConsultar: Button? = null
    private var tableLayout: TableLayout? = null
    private var tableLayoutMotor: TableLayout? = null
    private lateinit var motorFragmentViewModel: MotorFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_motor, container, false)
        InitializeComponents(view)
        InitializeEvents()
        return view
    }

    private fun InitializeComponents(view: View) {
        txtMotor = view.findViewById(R.id.txtMotor)
        txtCodProd = view.findViewById(R.id.txtCodProd)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        tableLayout = view.findViewById(R.id.tableLayoutArticulos)
        tableLayoutMotor = view.findViewById(R.id.tableLayoutMotores)

        motorFragmentViewModel = MotorFragmentViewModel()
    }

    private fun InitializeEvents() {
        btnConsultar?.setOnClickListener(btnConsultar_OnClickListener)
    }

    private var btnConsultar_OnClickListener = View.OnClickListener {
        val lstMotor = motorFragmentViewModel.ObtenerMotores(txtCodProd?.text.toString(), txtMotor?.text.toString())
        motorFragmentViewModel.CargarDataShowAlert(context!!, tableLayoutMotor!!, ObtenerColumnasMotor(), lstMotor)

        val alert = AlertDialog.Builder(context)
        val your_view: View = LayoutInflater.from(context).inflate(R.layout.motor_alert_dialog, null)
        alert.setView(your_view)
    }

    private fun ObtenerColumnasMotor(): Array<String> {
        return arrayOf("Marca", "Motor", "Cili1")
    }

    private fun ConsultarArticulo() {
        val lstArticulo = motorFragmentViewModel.ObtenerArticulosXMotorCodProd(txtCodProd?.text.toString(), txtMotor?.text.toString())
        motorFragmentViewModel.CargarDataLayaoutArticulos(context!!, tableLayout!!, ObtenerColumnasArticulos(), lstArticulo)
    }

    private fun ObtenerColumnasArticulos(): Array<String> {
        return arrayOf("CpdNew", "Alternante", "CodBar", "UM", "CAN", "Sal")
    }

    private fun ConsultaMotor() {
        try {
            val procedure = "{call usp_AndroidSelectObtenerMotoresporMantMotores(?,?)}"
            val callStatment: CallableStatement = ConnectionDB.Conexion().prepareCall(procedure)
            callStatment.setString(1, txtCodProd?.getText().toString())
            callStatment.setString(2, txtMotor?.getText().toString())
            val rs = callStatment.executeQuery()
            val lstMotor: ArrayList<Motor?> = ArrayList<Motor?>()
            var motor = Motor()
            motor.marca = "Marca"
            motor.motor = "Motor"
            motor.cili1 = "Cili1"
            lstMotor.add(motor)
            while (rs.next()) {
                motor = Motor()
                motor.marca = rs.getString("marcavehi")
                motor.motor = rs.getString("motor")
                motor.cili1 = rs.getString("cili1")
                lstMotor.add(motor)
            }
            CargarDataListView(ArrayList(), lstMotor, 2)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    private fun CargarDataListView(lstArticulo: ArrayList<Articulo?>, lstMotor: ArrayList<Motor?>, acces: Int) {
        val adapter: MasterAdapter<*>
        if (acces == 1) {
            adapter = MasterAdapter(context!!, lstArticulo, 3)
            //> listMotores!!.adapter = adapter
        } else {
            adapter = MasterAdapter(context!!, lstMotor, 4)
            showAlertDialog(adapter)
        }
    }

    fun showAlertDialog(adapter: ArrayAdapter<*>) {
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogCustom))
        alertDialog.setTitle("Vista de Motores")
        alertDialog.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        })
        alertDialog.setAdapter(adapter, object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, position: Int) {
                val motor: Motor = adapter.getItem(position) as Motor
                try {
                    dialog.dismiss()
                    txtMotor?.setText(motor.motor)
                    ConsultarArticulo()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
        alertDialog.show()
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): MotorFragment {
            val fragment = MotorFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}