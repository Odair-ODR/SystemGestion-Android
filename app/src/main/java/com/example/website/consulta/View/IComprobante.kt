package com.example.website.consulta.View

import android.graphics.Canvas
import android.graphics.Paint
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.FacturaDetTo

interface IComprobante {
    fun generarPDF(preFactura: FacturaCabTo, lstPreFacturaDet: List<FacturaDetTo>)
    fun createHead(paint: Paint, canvas: Canvas)
    fun createBody(paint: Paint, canvas: Canvas)
    fun createFooter(paint: Paint, canvas: Canvas)
}