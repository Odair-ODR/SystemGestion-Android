package com.example.website.consulta.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.CodbarFragmentViewModel

class CodbarFragment : Fragment() {
    private var txtCodba: EditText? = null
    private var txtMotor: EditText? = null
    private var btnConsultar: Button? = null
    private lateinit var tableLayout: TableLayout
    private lateinit var codBarFragmentViewModel: CodbarFragmentViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_codbar, container, false)
        InitializeComponents(view)
        InitializeEvents()
        return view
    }

    private fun InitializeComponents(view: View) {
        txtCodba = view.findViewById(R.id.txtCodBar)
        tableLayout = view.findViewById(R.id.tableLayoutArticulos)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        txtMotor = view.findViewById(R.id.txtMotor)
        tableLayout = view.findViewById(R.id.tableLayoutArticulos)
    }

    private fun InitializeEvents() {
        btnConsultar!!.setOnClickListener(btnConsular_OnClickListerner)
    }

    private var btnConsular_OnClickListerner = View.OnClickListener {
        /*val lstArticulo = codBarFragmentViewModel.ObtenerArticulosXCodbar(txtCodba?.text.toString())
        context?.let { it1 -> codBarFragmentViewModel.CargarAticulosXCobar(it1, tableLayout, ObtenerColumns(), lstArticulo) }*/
        codBarFragmentViewModel = CodbarFragmentViewModel(context!!)
        codBarFragmentViewModel.execute(txtCodba?.text.toString(), tableLayout)
    }
}