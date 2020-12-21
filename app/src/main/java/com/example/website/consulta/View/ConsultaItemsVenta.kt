package com.example.website.consulta.View

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.website.consulta.Helpers.InitEventsControls
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.ArticuloViewModel
import kotlin.collections.List

class ConsultaItemsVenta : AppCompatActivity(), InitEventsControls {

    private lateinit var tlArticulos: TableLayout
    private lateinit var txtCodbar: EditText
    private lateinit var txtAlternante: EditText
    private lateinit var btnBuscar: Button

    private lateinit var articuloViewModel: ArticuloViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_items_venta)

        InitializeComponents()
        InitializeEvents()
    }

    override fun InitializeComponents() {
        tlArticulos = findViewById(R.id.tlArticulos)
        txtCodbar = findViewById(R.id.txtCodbar)
        txtAlternante = findViewById(R.id.txtAlternante)
        btnBuscar = findViewById(R.id.btnBuscar)
    }

    override fun InitializeEvents() {
        btnBuscar.setOnClickListener(btnBuscar_OnClickListener)
    }

    private var btnBuscar_OnClickListener = View.OnClickListener {
        CargarArticulosFactura()
    }

    fun setUpBindingListarArticuloFactura() {
        /*> var activityMainBinding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_consulta_items_venta)
        articuloViewModel = ViewModelProviders.of(this).get(ArticuloViewModel::class.java)
        activityMainBinding.setModel(articuloViewModel)
        CargarArticulosFactura()*/
    }

    private fun CargarArticulosFactura() {
        articuloViewModel = ViewModelProviders.of(this).get(ArticuloViewModel::class.java)
        articuloViewModel.ObtenerArticulosFactura(txtCodbar.text.toString(), txtAlternante.text.toString())
                .observe(this, Observer { lstArticulo: List<Articulo> ->
                    Log.w("Codbar : ", lstArticulo.get(0).codbar)
                    articuloViewModel.AsignarDataArticulosEnTableAdapter(this@ConsultaItemsVenta, tlArticulos, ObtenerColumnas(), lstArticulo)
                })
    }

    private fun ObtenerColumnas(): Array<String>{
        val columnas = arrayOf("Codbar", "Alternante", "Descripción", "Saldo")
        return columnas
    }
}