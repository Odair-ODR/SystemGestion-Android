package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.AlternanteFragmentViewModel
import kotlinx.android.synthetic.main.fragment_alternante.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlternanteFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtAlternante: EditText? = null
    private var btnConsultar: Button? = null
    private lateinit var alternanteFragmentViewModel: AlternanteFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString("")
            mParam2 = arguments!!.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_alternante, container, false)
        initializeComponents(view)
        initializeEvents()
        return view
    }

    private fun initializeComponents(view: View) {
        txtAlternante = view.findViewById(R.id.txtAlternante)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        initializeComponentsMotorFragmentViewModel(view)
    }

    private fun initializeEvents() {
        btnConsultar!!.setOnClickListener(btnConsultar_OnClickListener)
    }

    private fun initializeComponentsMotorFragmentViewModel(view: View) {
        alternanteFragmentViewModel = AlternanteFragmentViewModel(context!!)
        alternanteFragmentViewModel.horizontalScrollViewHead =
            view.findViewById(R.id.horizontalScrollViewHead)
        alternanteFragmentViewModel.tblArticuloHead = view.findViewById(R.id.tblArticuloHead)
        alternanteFragmentViewModel.horizontalScrollViewDetail =
            view.findViewById(R.id.horizontalScrollViewDetail)
        alternanteFragmentViewModel.tblArticuloDetail = view.findViewById(R.id.tblArticuloDetail)
        alternanteFragmentViewModel.initEvents()
        alternanteFragmentViewModel.startControls()
    }

    private var btnConsultar_OnClickListener = View.OnClickListener {
        try {
            lifecycleScope.launch(Dispatchers.Main) {
                alternanteFragmentViewModel.startLoadingDialog()
                val lstArticulos = withContext(Dispatchers.IO) {
                    alternanteFragmentViewModel.obtenerArticulosXAlternante(txtAlternante?.text.toString())
                }
                alternanteFragmentViewModel.cargarDataArticulosXAlternante(lstArticulos)
                alternanteFragmentViewModel.closeLoadingDialog()
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "error al consultar", Toast.LENGTH_SHORT).show()
        }
    }
}