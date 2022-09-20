package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.TIPO_DOCUMENTO
import com.example.website.consulta.Model.Entidad.TIPO_PROCESO_VENTA
import com.example.website.consulta.Model.Entidad.VentasProceso
import com.example.website.consulta.R

class VentasProcesoProvider {
    companion object {
        val lstProcesosVentas = listOf(
            VentasProceso(TIPO_DOCUMENTO.FACTURA,TIPO_DOCUMENTO.FACTURA.descripcion, R.drawable.ic_docs_foreground),
            VentasProceso(TIPO_DOCUMENTO.BOLETA,TIPO_DOCUMENTO.BOLETA.descripcion, R.drawable.ic_docs_foreground),
            VentasProceso(TIPO_DOCUMENTO.NOTA_CREDITO,TIPO_DOCUMENTO.NOTA_CREDITO.descripcion, R.drawable.ic_docs_foreground),
            VentasProceso(TIPO_DOCUMENTO.NOTA_VENTA,TIPO_DOCUMENTO.NOTA_VENTA.descripcion, R.drawable.ic_docs_foreground),
            VentasProceso(TIPO_PROCESO_VENTA.CANCELACION,TIPO_PROCESO_VENTA.CANCELACION.descripcion, R.drawable.ic_docs_foreground)
        )
    }
}