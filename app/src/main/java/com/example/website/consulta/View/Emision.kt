package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.website.consulta.Model.Entidad.TIPO_DOCUMENTO
import com.example.website.consulta.Model.Entidad.TIPO_PROCESO_VENTA
import com.example.website.consulta.Model.Entidad.VentasProceso
import com.example.website.consulta.Model.VentasProcesoProvider
import com.example.website.consulta.View.Adapter.VentasProcesoAdapter
import com.example.website.consulta.databinding.ActivityEmisionBinding

class Emision : AppCompatActivity() {
    private lateinit var binding: ActivityEmisionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmisionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycleView()
    }

    private fun initRecycleView() {
        binding.recycleVentasProces.layoutManager = GridLayoutManager(this, 2)
        binding.recycleVentasProces.adapter =
            VentasProcesoAdapter(VentasProcesoProvider.lstProcesosVentas) { ventasProceso ->
                onItemSelected(
                    ventasProceso
                )
            }
    }

    private fun onItemSelected(ventasProceso: VentasProceso) {
        if (ventasProceso.tipoProcesoVenta is TIPO_DOCUMENTO) {
            val c = Intent(this@Emision, Nventas::class.java)
            c.putExtra("TipoDocumento", ventasProceso.tipoProcesoVenta)
            startActivity(c)
        } else if (ventasProceso.tipoProcesoVenta is TIPO_PROCESO_VENTA) {
            val c = Intent(this@Emision, CancelacionDocumentos::class.java)
            startActivity(c)
        }
    }
}