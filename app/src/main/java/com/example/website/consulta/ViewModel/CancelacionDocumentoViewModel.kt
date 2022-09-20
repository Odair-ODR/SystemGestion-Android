package com.example.website.consulta.ViewModel

import android.content.Context
import android.view.View
import android.widget.TableLayout
import com.example.website.consulta.Model.CancelacionVentasObservable
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo
import com.example.website.consulta.Model.IVerificarCancelacionFacturacion
import com.example.website.consulta.View.Adapter.CancelacionDocumentoAdapter
import com.example.website.consulta.View.TableAdapter
import com.example.website.consulta.databinding.ActivityCancelacionDocumentosBinding

class CancelacionDocumentoViewModel(view: View) {
    private val binding = ActivityCancelacionDocumentosBinding.bind(view)
    private val obsCancelacionDocumento = CancelacionVentasObservable()

    fun listarPreFactura(preFactura: FacturaCabTo): List<FacturaCabTo> {
        return obsCancelacionDocumento.listarPrefactura(preFactura)
    }

    fun grabarCancelacionDocumentosFacturacion(
        preFactura: FacturaCabTo,
        lstPreFactura: List<FacturaDetTo>,
        verificarCancelacionFacturacion: IVerificarCancelacionFacturacion
    ): Boolean {
        return obsCancelacionDocumento.grabarCancelacionDocumentosFacturacion(
            preFactura,
            lstPreFactura,
            verificarCancelacionFacturacion
        )
    }

    fun obtenerPreFacturaDet(idPreFacturaCab: Int): List<FacturaDetTo> {
        return obsCancelacionDocumento.obtenerPreFacturaDet(idPreFacturaCab)
    }
}