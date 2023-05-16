package com.example.website.consulta.View

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.website.consulta.Helpers.Constants
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.TIPO_DOCUMENTO
import com.example.website.consulta.R
import com.example.website.consulta.databinding.PdfViewBinding
import com.google.gson.Gson
import java.io.File
import java.lang.StringBuilder

class PdfView:  AppCompatActivity() {
    private lateinit var binding: PdfViewBinding
    private lateinit var preFactura:FacturaCabTo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeComponents()
        initializeEvents()
        startViewPdf()
    }

    private fun initializeComponents() {
        val gson = Gson()
        preFactura = gson.fromJson(intent.getStringExtra("PreFactura"), FacturaCabTo::class.java)
    }

    private fun initializeEvents() {
        binding.btnCompartir.setOnClickListener(compartirArchivoOnClick)
    }

    private fun startViewPdf() {
        val file = File(this.getExternalFilesDir(Constants.directoryInvoices), preFactura.nombreArchivoPdf)
        binding.pdfView.fromFile(file)
        binding.pdfView.isZoomEnabled
        binding.pdfView.show()
    }

    private val compartirArchivoOnClick = View.OnClickListener {
        val file = File(this.getExternalFilesDir(Constants.directoryInvoices), preFactura.nombreArchivoPdf)
        if (file.exists()) {
            val uri = Uri.parse(file.path)
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "application/pdf"
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_TITLE, resources.getText(R.string.facturaTitle))
            }
            val intentChooser = Intent.createChooser(shareIntent, null)
            startActivity(intentChooser)
        } else {
            Toast.makeText(this, "El archivo pdf no existe", Toast.LENGTH_SHORT).show()
        }
    }
}