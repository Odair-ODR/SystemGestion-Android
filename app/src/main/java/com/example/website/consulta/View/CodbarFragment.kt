package com.example.website.consulta.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.CodbarFragmentViewModel
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.fragment_codbar.horizontalScrollViewDetail
import kotlinx.android.synthetic.main.fragment_codbar.horizontalScrollViewHead
import kotlinx.android.synthetic.main.fragment_codbar.tblArticuloDetail
import kotlinx.android.synthetic.main.fragment_codbar.tblArticuloHead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CodbarFragment : Fragment() {
    private var txtCodba: EditText? = null
    private var txtMotor: EditText? = null
    private var btnConsultar: Button? = null
    private lateinit var codBarFragmentViewModel: CodbarFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_codbar, container, false)
        initializeComponents(view)
        initializeEvents()
        return view
    }

    private fun initializeComponents(view: View) {
        txtCodba = view.findViewById(R.id.txtCodBar)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        txtMotor = view.findViewById(R.id.txtMotor)
        initializeComponentsCodbarFragmentViewModel(view)
    }

    private fun initializeEvents() {
        btnConsultar!!.setOnClickListener(btnConsular_OnClickListerner)
    }

    private fun initializeComponentsCodbarFragmentViewModel(view: View) {
        codBarFragmentViewModel = CodbarFragmentViewModel(context!!)
        codBarFragmentViewModel.horizontalScrollViewHead = view.findViewById(R.id.horizontalScrollViewHead)
        codBarFragmentViewModel.tblArticuloHead = view.findViewById(R.id.tblArticuloHead)
        codBarFragmentViewModel.horizontalScrollViewDetail = view.findViewById(R.id.horizontalScrollViewDetail)
        codBarFragmentViewModel.tblArticuloDetail = view.findViewById(R.id.tblArticuloDetail)
        codBarFragmentViewModel.initializeEvents()
    }

    private var btnConsular_OnClickListerner = View.OnClickListener {
        lifecycleScope.launch(Dispatchers.Main){
            codBarFragmentViewModel.startLoadingDialog()
            val lstArticulos = withContext(Dispatchers.IO){
                codBarFragmentViewModel.obtenerArticulosXCodbar(txtCodba?.text.toString())
            }
            codBarFragmentViewModel.cargarArticulosXCobar(lstArticulos)
            codBarFragmentViewModel.closeLoadingDialog()
        }
    }
}