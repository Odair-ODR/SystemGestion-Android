package com.example.website.consulta.View

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.MotorFragmentViewModel

class MotorFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtMotor: EditText? = null
    private var txtCodProd: EditText? = null
    private var btnConsultar: Button? = null
    private var tableLayout: TableLayout? = null
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
    }

    private fun InitializeEvents() {
        btnConsultar?.setOnClickListener(btnConsultar_OnClickListener)
    }

    private var btnConsultar_OnClickListener = View.OnClickListener {
        motorFragmentViewModel = MotorFragmentViewModel(context!!)
        motorFragmentViewModel.tableLayoutArticulo = tableLayout
        motorFragmentViewModel.txtMotor = txtMotor
        motorFragmentViewModel.execute(txtCodProd?.text.toString(), txtMotor?.text.toString())
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
                    //> ConsultarArticulo()
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