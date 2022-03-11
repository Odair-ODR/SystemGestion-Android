package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.AlternanteFragmentViewModel

class AlternanteFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtAlternante: EditText? = null
    private var tableLayoutArticulo: TableLayout? = null
    private var btnConsultar: Button? = null
    private lateinit var alternanteFragmentViewModel: AlternanteFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString("")
            mParam2 = arguments!!.getString("")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_alternante, container, false)
        InitializeComponents(view)
        InitializeEvents()
        return view
    }

    private fun InitializeComponents(view: View){
        txtAlternante = view.findViewById(R.id.txtAlternante)
        tableLayoutArticulo = view.findViewById(R.id.tableLayoutArticulos)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
    }

    private fun InitializeEvents(){
        btnConsultar!!.setOnClickListener(btnConsultar_OnClickListener)
    }

     private var btnConsultar_OnClickListener = View.OnClickListener {
        /*val lstArticulos = alternanteFragmentViewModel.ObtenerArticulosXAlternante(txtAlternante?.text.toString())
        if(context != null && tableLayout != null){
            alternanteFragmentViewModel.CargarDataArticulosXAlternante(context!!, tableLayout!!, ObtenerColumnas(), lstArticulos)
        }*/
         alternanteFragmentViewModel = AlternanteFragmentViewModel(context!!)
         alternanteFragmentViewModel.execute(txtAlternante?.text.toString(), tableLayoutArticulo)
    }
}