package com.example.website.consulta.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.MotorFragmentViewModel
import kotlinx.android.synthetic.main.fragment_motor.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MotorFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtMotor: EditText? = null
    private var txtCodProd: EditText? = null
    private var btnConsultar: Button? = null
    private lateinit var motorFragmentViewModel: MotorFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_motor, container, false)
        initializeComponents(view)
        initializeEvents()
        return view
    }

    private fun initializeComponents(view: View) {
        txtMotor = view.findViewById(R.id.txtMotor)
        txtCodProd = view.findViewById(R.id.txtCodProd)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        initializeComponentsMotorFragmentViewModel(view)
    }

    private fun initializeEvents() {
        btnConsultar?.setOnClickListener(btnConsultar_OnClickListener)
    }

    private fun initializeComponentsMotorFragmentViewModel(view: View) {
        motorFragmentViewModel = MotorFragmentViewModel(context!!)
        motorFragmentViewModel.horizontalScrollViewHead =
            view.findViewById(R.id.horizontalScrollViewHead)
        motorFragmentViewModel.tblArticuloHead = view.findViewById(R.id.tblArticuloHead)
        motorFragmentViewModel.horizontalScrollViewDetail =
            view.findViewById(R.id.horizontalScrollViewDetail)
        motorFragmentViewModel.tblArticuloDetail = view.findViewById(R.id.tblArticuloDetail)
    }

    private var btnConsultar_OnClickListener = View.OnClickListener {
        try {
            lifecycleScope.launch(Dispatchers.Main) {
                motorFragmentViewModel.startLoadingDialog()
                val lstMotor = withContext(Dispatchers.IO) {
                    motorFragmentViewModel.obtenerMotores(
                        txtCodProd?.text.toString(),
                        txtMotor?.text.toString()
                    )
                }
                motorFragmentViewModel.startAlerDialogMotores()
                motorFragmentViewModel.cargarDataMotor(lstMotor)
                motorFragmentViewModel.closeLoadingDialog()
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "error al consultar", Toast.LENGTH_SHORT).show()
        }
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