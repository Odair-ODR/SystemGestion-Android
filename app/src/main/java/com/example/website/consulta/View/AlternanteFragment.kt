package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.ViewModel.AlternanteFragmentViewModel
import com.example.website.consulta.databinding.FragmentAlternanteBinding
import com.example.website.consulta.databinding.MergeButtonConsultarBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlternanteFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var alternanteFragmentViewModel: AlternanteFragmentViewModel

    private var _binding:FragmentAlternanteBinding? = null
    private val binding get() = _binding!!
    private lateinit var mergeBinding: MergeButtonConsultarBinding

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
        _binding = FragmentAlternanteBinding.inflate(inflater, container, false)
        mergeBinding = MergeButtonConsultarBinding.bind(binding.root)
        initializeComponents()
        initializeEvents()
        return binding.root
    }

    private fun initializeComponents() {
        initializeComponentsMotorFragmentViewModel()
    }

    private fun initializeEvents() {
        mergeBinding.btnConsultarFra.setOnClickListener(btnConsultar_OnClickListener)
    }

    private fun initializeComponentsMotorFragmentViewModel() {
        alternanteFragmentViewModel = AlternanteFragmentViewModel(context!!)
        alternanteFragmentViewModel.horizontalScrollViewHead = binding.horizontalScrollViewHead
        alternanteFragmentViewModel.tblArticuloHead = binding.tblArticuloHead
        alternanteFragmentViewModel.horizontalScrollViewDetail = binding.horizontalScrollViewDetail
        alternanteFragmentViewModel.tblArticuloDetail = binding.tblArticuloDetail
        alternanteFragmentViewModel.initEvents()
        alternanteFragmentViewModel.startControls()
    }

    private var btnConsultar_OnClickListener = View.OnClickListener {
        try {
            lifecycleScope.launch(Dispatchers.Main) {
                alternanteFragmentViewModel.startLoadingDialog()
                val lstArticulos = withContext(Dispatchers.IO) {
                    alternanteFragmentViewModel.obtenerArticulosXAlternante(binding.txtAlternante.text.toString())
                }
                alternanteFragmentViewModel.cargarDataArticulosXAlternante(lstArticulos)
                alternanteFragmentViewModel.closeLoadingDialog()
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "error al consultar", Toast.LENGTH_SHORT).show()
        }
    }
}