package com.example.website.consulta.View

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.website.consulta.Helpers.InitEventsControls
import com.example.website.consulta.Helpers.UtilsInterface
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.ArticuloViewModel
import com.example.website.consulta.ViewModel.MotorFragmentViewModel
import kotlinx.android.synthetic.main.activity_consulta_items_venta.*
import kotlinx.android.synthetic.main.fragment_motor.txtCodProd
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsultaItemsVenta : AppCompatActivity(), InitEventsControls {

    private lateinit var txtCodbar: EditText
    private lateinit var txtAlternante: EditText
    private lateinit var btnBuscar: Button
    private lateinit var btnBuscarMotor: Button
    private lateinit var txtMotor: EditText

    private lateinit var articuloViewModel: ArticuloViewModel
    private lateinit var motorFragmentViewModel: MotorFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_items_venta)

        InitializeComponents()
        InitializeEvents()
        startOptions()
    }

    override fun InitializeComponents() {
        txtCodbar = findViewById(R.id.txtCodbar)
        txtAlternante = findViewById(R.id.txtAlternante)
        txtMotor = findViewById(R.id.txtMotor)
        btnBuscar = findViewById(R.id.btnBuscar)
        btnBuscarMotor = findViewById(R.id.btnBuscarMotor)

        motorFragmentViewModel = MotorFragmentViewModel(this@ConsultaItemsVenta)
    }

    override fun InitializeEvents() {
        btnBuscar.setOnClickListener(btnBuscarOnClickListener)
        btnBuscarMotor.setOnClickListener(btnBuscarMotorOnClickListener)
    }

    private fun startOptions() {

    }

    private var btnBuscarOnClickListener = View.OnClickListener {
        try {
            cargarArticulosFactura()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun setUpBindingListarArticuloFactura() {
        /*> var activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_consulta_items_venta)
        articuloViewModel = ViewModelProviders.of(this).get(ArticuloViewModel::class.java)
        activityMainBinding.setModel(articuloViewModel)
        CargarArticulosFactura()*/
    }

    private fun initializeComponentsConsultaItemsVenta() {
        articuloViewModel = ArticuloViewModel(this@ConsultaItemsVenta, this)
        articuloViewModel.articuloViewModel = articuloViewModel
        articuloViewModel.horizontalScrollViewHead = horizontalScrollViewHead
        articuloViewModel.tblArticuloHead = tblArticuloHead
        articuloViewModel.horizontalScrollViewDetail = horizontalScrollViewDetail
        articuloViewModel.tblArticuloDetail = tblArticuloDetail
        articuloViewModel.initEvents()
    }

    private fun cargarArticulosFactura() {
        /*articuloViewModel = ViewModelProviders.of(this).get(ArticuloViewModel::class.java)
        articuloViewModel.ObtenerArticulosFactura(txtCodbar.text.toString(), txtAlternante.text.toString())
                .observe(this, Observer { lstArticulo: List<Articulo> ->
                    Log.w("Codbar : ", lstArticulo.get(0).codbar)
                    articuloViewModel.AsignarDataArticulosEnTableAdapter(this@ConsultaItemsVenta, tlArticulos, ObtenerColumnas(), lstArticulo)
                })*/
        initializeComponentsConsultaItemsVenta()
        articuloViewModel.parameters = ArticuloViewModel.Parameters().also { it ->
            it.codbar = txtCodbar.text.toString()
            it.alternante = txtAlternante.text.toString()
        }
        lifecycleScope.launch(Dispatchers.Main) {
            articuloViewModel.startLoadingDialog()
            val lstArticulo = withContext(Dispatchers.IO) {
                articuloViewModel.obtenerArticulos()
            }
            if (lstArticulo != null) articuloViewModel.cargarArticulos(lstArticulo)
            articuloViewModel.closeLoadingDialog()
        }
    }

    private var btnBuscarMotorOnClickListener = View.OnClickListener {
        try {
            initializeComponentsConsultaItemsVenta()
            articuloViewModel.parameters = ArticuloViewModel.Parameters().also {
                it.codbar = txtCodbar.text.toString()
                it.alternante = txtAlternante.text.toString()
                it.codProd = txtCodProd.text.toString()
            }
            lifecycleScope.launch(Dispatchers.Main) {
                articuloViewModel.startLoadingDialog()
                val lstMotor = withContext(Dispatchers.IO) {
                    articuloViewModel.obtenerMotores(
                        txtCodProd.text.toString(),
                        txtMotor.text.toString()
                    )
                }
                articuloViewModel.mostrarAlertDialogMotores()
                articuloViewModel.cargarDataShowAlert(lstMotor)
                articuloViewModel.closeLoadingDialog()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(0, null)
    }
}