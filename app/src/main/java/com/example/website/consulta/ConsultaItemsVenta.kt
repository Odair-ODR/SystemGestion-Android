package com.example.website.consulta

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TableLayout
import com.example.website.consulta.Helpers.InitEventsControls

class ConsultaItemsVenta : AppCompatActivity(),  InitEventsControls{

    private lateinit var tlArticulos: TableLayout
    private lateinit var txtCodbar: EditText
    private lateinit var txtAlternante: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta_items_venta)

        InitializeComponents()
        InitializeEvents()
    }

    override fun InitializeComponents() {
        tlArticulos = findViewById(R.id.tlArticulos)
        txtCodbar = findViewById(R.id.txtCodBar)
        txtAlternante = findViewById(R.id.txtAlternante)
    }

    override fun InitializeEvents() {

    }
}