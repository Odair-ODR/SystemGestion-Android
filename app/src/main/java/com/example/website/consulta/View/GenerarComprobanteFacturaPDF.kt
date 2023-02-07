package com.example.website.consulta.View

import android.content.Context
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.example.website.consulta.Helpers.UtilsMethod
import com.example.website.consulta.Helpers.UtilsMethod.Companion.getDateFromSqlFormat
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.Model.MultipleObservable
import com.example.website.consulta.R
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.math.RoundingMode

class GenerarComprobanteFacturaPDF(val context: Context) {
    private lateinit var lstPreFacturaDet: List<FacturaDetTo>
    private lateinit var preFactura: FacturaCabTo
    private var pageWidth: Int = 0
    private var pageHeight: Int = 0
    private var top: Float = 0f
    private var left: Float = 0f
    private var right: Float = 0f
    private var lstCuentasBancarias: List<CuentasBancariasTo>
    private var multipleObs: MultipleObservable = MultipleObservable()

    init {
        lstCuentasBancarias = multipleObs.listaCuentasBancarias()
    }

    fun generarPDF(preFactura: FacturaCabTo, lstPreFacturaDet: List<FacturaDetTo>) {
        try{
            this.lstPreFacturaDet = lstPreFacturaDet
            this.preFactura = preFactura
            pageWidth = 612
            pageHeight = 792
            top = 15f
            left = 20f
            right = 20f
            val pdfDocument = PdfDocument()
            val paint = Paint()
            val pdfInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = pdfDocument.startPage(pdfInfo)
            val canvas = page.canvas
            createHead(paint, canvas)
            createBody(paint, canvas)
            createFooter(paint, canvas)
            pdfDocument.finishPage(page)
            val file = File(Environment.getExternalStorageDirectory(), preFactura.nombreArchivoPdf)
            pdfDocument.writeTo(FileOutputStream(file))
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
    }

    private fun createHead(paint: Paint, canvas: Canvas) {
        agregarLogo(canvas, paint)
        agregarTitulos(paint, canvas)
        agregarDatosGeneralesCabecera(paint, canvas)
    }

    private fun createBody(paint: Paint, canvas: Canvas) {
        agregarDetalleComprobante(paint, canvas)
    }

    private fun createFooter(paint: Paint, canvas: Canvas) {
        agregarTotales(paint, canvas)
        agregarCuentasBanco(paint, canvas)
    }

    private fun agregarLogo(canvas: Canvas, paint: Paint) {
        val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_factura)
        val bitmapScale: Bitmap = Bitmap.createScaledBitmap(bitmap, 430, 90, true)
        canvas.drawBitmap(bitmapScale, left, top, paint)
    }

    private fun agregarTitulos(paint: Paint, canvas: Canvas) {
        val titlePaint = Paint()
        //> start -> Rectangulo
        val normal = Typeface.create("Arial", Typeface.NORMAL)
        paint.setTypeface(normal)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f

        //> Recatangulo para datos -> ruc, num ruc, nro factura
        val x = 455f
        val y = 30f
        val x2 = pageWidth - right
        val y2 = 100f
        canvas.drawLine(x, y, x2, y, paint) //> Horizontales
        canvas.drawLine(x, y2, x2, y2, paint) //> Horizontales
        canvas.drawLine(x, y2, x, y, paint) //> Verticales
        canvas.drawLine(x2, y2, x2, y, paint) //> Verticales
        //> Tres lineas horizontales
        val y3 = (y + ((y2 - y) / 3) - 2)
        val y4 = (y3 + ((y2 - y) / 3) + 2)
        canvas.drawLine(x, y3, x2, y3, paint) //> Horizontales
        canvas.drawLine(x, y4, x2, y4, paint) //> Horizontales
        val a = (((y2 - y) / 3) / 2 ) + 3
        //> Título derecha
        val x3 = x + (x2 - x) / 2
        paint.color = Color.rgb(0, 0, 0)
        paint.textSize = 10f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("R.U.C. 20601766486", x3, y + a , paint)

        val bold = Typeface.create("Arial", Typeface.BOLD)
        paint.setTypeface(bold)
        paint.color = Color.LTGRAY
        paint.textSize = 10f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawRect(x, y3, x2, y4, paint)
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawRect(x, y3, x2, y4, paint)
        paint.style = Paint.Style.FILL
        canvas.drawText("FACTURA ELECTRONICA", x3, y3 + a + 2, paint)

        paint.setTypeface(normal)
        paint.color = Color.rgb(0, 0, 0)
        paint.textSize = 10f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("F001-00000914", x3, y4 + a, paint)
    }

    private fun agregarDatosGeneralesCabecera(paint: Paint, canvas: Canvas) {
        //> Rectangulo para datos generales
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1f
        val top2 = top + 100;
        val bottom2 = top + 200
        canvas.drawRect(left, top2, pageWidth-right, bottom2, paint)
        //> Datos que se muestran
        paint.style = Paint.Style.FILL
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 8f
        paint.color = Color.BLACK
        //> Labels
        val y = top2 + 15f
        val y2 = y + 15
        val y3 = y2 + 20
        val y4 = y3 + 20
        val y5 = y4 + 15
        val x = left + 15f
        canvas.drawText("Fecha:", x , y, paint)
        canvas.drawText("Cliente: ", x, y2, paint)
        canvas.drawText("Dirección: ", x, y3, paint)
        canvas.drawText("Documento: ", x, y4, paint)
        canvas.drawText("Número: ", x, y5, paint)
        //> Values
        val x2 = left + 85f
        canvas.drawText(preFactura.fectra.getDateFromSqlFormat(), x2, y, paint)
        val textPaint = TextPaint()
        textPaint.isAntiAlias = true
        textPaint.textSize = 8f
        textPaint.color = -0x1000000
        val width = 240
        var staticLayout = StaticLayout(
            preFactura.nombres, textPaint,
            width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false
        )
        canvas.save()
        canvas.translate(x2, y2 - 10)
        staticLayout.draw(canvas)
        canvas.restore()

        staticLayout = StaticLayout(
            preFactura.direccion, textPaint,
            width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0f, false
        )
        canvas.save()
        canvas.translate(x2, y3 - 10)
        staticLayout.draw(canvas)
        canvas.restore()

        canvas.drawText(preFactura.tipoDocIdentidadTo!!.descTipIdentidad, x2, y4, paint)
        canvas.drawText(preFactura.nroDocIdenti, x2, y5, paint)

        //> Labels
        val x3 = left + 350
        canvas.drawText("Cod. Ven.:", x3, y, paint)
        canvas.drawText("Condición: ", x3, y2, paint)
        canvas.drawText("Placa: ", x3, y3, paint)
        canvas.drawText("Guia: ", x3, y4, paint)
        //> Values
        val x4 = left + 410
        canvas.drawText(preFactura.codVen, x4, y, paint)
        canvas.drawText(preFactura.conpag.descripcion, x4, y2, paint)
        canvas.drawText(preFactura.placa, x4, y3, paint)
        val fecgui = if (preFactura.fecgui != null) preFactura.fecgui.toString().getDateFromSqlFormat() else ""
        canvas.drawText(preFactura.sergui + " - " + preFactura.numgui + " - " + fecgui, x4, y4, paint)
    }

    private fun agregarDetalleComprobante(paint: Paint, canvas: Canvas) {
        //> Lista de artículos
        paint.style = Paint.Style.FILL
        paint.color = Color.LTGRAY
        paint.strokeWidth = 1f
        val top2 = top + 210
        val bottom2 = top2 + 15
        canvas.drawRect(left, top2, pageWidth-right, bottom2, paint)
        //> Delineado
        /*canvas.drawLine(50f, 240f, 50f, 255f, paint)
        canvas.drawLine(680f, 790f, 680f, 840f, paint)
        canvas.drawLine(880f, 790f, 880f, 840f, paint)
        canvas.drawLine(1030f, 790f, 1030f, 840f, paint)*/
        //> Header
        val x = left + 10f
        val x2 = x + 35f
        val x3 = x2 + 65f
        val x4 = x3 + 205f
        val x5 = x4 + 45F
        val x6 = x5 + 65F
        val x7 = x6 + 85F
        val y = top2 + ((bottom2 - top2) / 2) + 3
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText("SEC", x, y, paint)
        canvas.drawText("CODIGO", x2, y, paint)
        canvas.drawText("DESCRIPCION", x3, y, paint)
        canvas.drawText("UM", x4, y, paint)
        canvas.drawText("CANTI", x5, y, paint)
        canvas.drawText("UNITARIO", x6, y, paint)
        canvas.drawText("TOTAL", x7, y, paint)
        agregarItemsComprobante(paint, canvas, lstPreFacturaDet ,bottom2)
    }

    private fun agregarItemsComprobante(paint: Paint, canvas: Canvas, lstPreFacturaDet: List<FacturaDetTo>, bottom2: Float) {
        var index = 1
        var ySize = bottom2 + 10
        val x = left + 10f
        val x2 = x + 35f
        val x3 = x2 + 65f
        val x4 = x3 + 205f
        val x5 = x4 + 45F
        val x6 = x5 + 65F
        val x7 = x6 + 85F
        lstPreFacturaDet.forEach{
            canvas.drawText(it.al32flag, x, ySize, paint)
            canvas.drawText(index.toString(), x + 5, ySize, paint)
            canvas.drawText(it.al32Codbar, x2, ySize, paint)
            canvas.drawText(it.al32DescriArti!!, x3, ySize, paint)
            canvas.drawText(it.al32Unimed!!, x4 + 1, ySize, paint)
            canvas.drawText(it.al32Totcan.toString(), x5 + 4, ySize, paint)
            canvas.drawText(it.al32Preuni.toString(), x6, ySize, paint)
            canvas.drawText(it.al32pretot.toString(), x7, ySize, paint)
            ySize += 10f
            index ++
        }
    }

    private fun agregarTotales(paint: Paint, canvas: Canvas) {
        //> Otros datos
        paint.style = Paint.Style.STROKE
        val x = left
        val y = pageHeight - 250f
        val x2 = pageWidth - right
        val y2 = y + 15
        val y3 = y2 + 15
        val y4 = y3 + 15
        val y5 = y4 + 15
        val y6 = y5 + 15
        canvas.drawRect(x, y, x2, y2, paint)
        canvas.drawRect(x, y2, x2, y3, paint)
        canvas.drawRect(x, y3, x2, y4, paint)
        canvas.drawRect(x, y4, x2, y5, paint)
        canvas.drawRect(x, y5, x2, y6, paint)
        canvas.drawLine(left + 30f, y, left + 30, y2, paint)
        canvas.drawLine(left + 70f, y4, left + 70, y5, paint)
        canvas.drawLine(left + 70f, y5, left + 70, y6, paint)
        paint.style = Paint.Style.FILL
        val x3 = x + 4
        val n = 10
        canvas.drawText("Son:", x3, y + n, paint)
        canvas.drawText(preFactura.numlet, left + 34f, y + n, paint)
        canvas.drawText("Condición de pago factura al crédito", x3, y2 + n, paint)
        val cuotasCredito = if(preFactura.conpag == FORMA_DE_PAGO.CREDITO) "Cuotas credito : " + preFactura.credito_d else ""
        canvas.drawText(cuotasCredito, x3, y3 + n, paint)
        canvas.drawText("Observación:", x3, y4 + n, paint)
        canvas.drawText(preFactura.observacion, left + 74f, y4 + n, paint)
        canvas.drawText("Código Hash:", x3, y5 + n, paint)
        canvas.drawText("qSQTa9ilfjA4SEEjCqRCNkmMxOc=", left + 74f, y5 + n, paint)

        val y7 = y6 + 15
        val x4 = pageWidth - right - 180
        // Totales
        val x5 = x4 + 20
        val y10 = y7 + 75
        val x6 = x5 + 60
        val x7 = x6 + 20
        paint.color = Color.GRAY
        canvas.drawRect(x5, y7, x6, y10, paint)
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawRect(x5, y7, x6, y10, paint)

        canvas.drawRect(x6, y7, x7, y10, paint)
        canvas.drawRect(x7, y7, x2, y10, paint)
        val a = (y10 - y7) / 5
        val y11 = y7 + a
        val y12 = y11 + a
        val y13 = y12 + a
        val y14 = y13 + a
        canvas.drawLine(x5, y11, x2, y11, paint)
        canvas.drawLine(x5, y12, x2, y12, paint)
        canvas.drawLine(x5, y13, x2, y13, paint)
        canvas.drawLine(x5, y14, x2, y14, paint)

        val impValven = preFactura.valven.toBigDecimal().setScale(2, RoundingMode.UP)
        val impValIGV = preFactura.valigv.toBigDecimal().setScale(2, RoundingMode.UP)
        val impValbru = preFactura.valbru.toBigDecimal().setScale(2, RoundingMode.UP)
        val impAnticipo = preFactura.al31TotAnticipo.toBigDecimal().setScale(2, RoundingMode.UP)
        val impDescuento = preFactura.oDescuento.toBigDecimal().setScale(2, RoundingMode.UP)
        val textValven = UtilsMethod.getDecimalthousandFormatted(impValven.toString())
        val textValIGV = UtilsMethod.getDecimalthousandFormatted(impValIGV.toString())
        val textValbru = UtilsMethod.getDecimalthousandFormatted(impValbru.toString())
        val textAnticipo = UtilsMethod.getDecimalthousandFormatted(impAnticipo.toString())
        val textDescuento = UtilsMethod.getDecimalthousandFormatted(impDescuento.toString())
        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawText("Sub Total:", x5 + 3, y7 + n, paint)
        canvas.drawText("Anticipos:", x5 + 3, y11 + n, paint)
        canvas.drawText("Descuento:", x5 + 3, y12 + n, paint)
        canvas.drawText("Igv 18%:", x5 + 3, y13 + n, paint)
        canvas.drawText("Total:", x5 + 3, y14 + n, paint)

        paint.color = Color.BLACK
        canvas.drawText("S/", x6 + 3, y7 + n, paint)
        canvas.drawText("S/", x6 + 3, y11 + n, paint)
        canvas.drawText("S/", x6 + 3, y12 + n, paint)
        canvas.drawText("S/", x6 + 3, y13 + n, paint)
        canvas.drawText("S/", x6 + 3, y14 + n, paint)

        //> Importes
        paint.textAlign = Paint.Align.RIGHT
        canvas.drawText(textValven, x2 - 2, y7 + n, paint)
        canvas.drawText(textAnticipo, x2 - 2, y11 + n, paint)
        canvas.drawText(textDescuento, x2 - 2, y12 + n, paint)
        canvas.drawText(textValIGV, x2 - 2, y13 + n, paint)
        canvas.drawText(textValbru, x2 - 2, y14 + n, paint)

        //> Fecha del día
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(UtilsMethod.getDateNow(), pageWidth / 2f, y10 + 15, paint)
    }

    private fun agregarCuentasBanco(paint: Paint, canvas: Canvas){
        val x = left
        val y = pageHeight - 250f
        val y2 = y + 15
        val y3 = y2 + 15
        val y4 = y3 + 15
        val y5 = y4 + 15
        val y6 = y5 + 15
        val y7 = y6 + 15
        val x4 = pageWidth - right - 180
        val y8 = y7 + 15
        paint.textAlign = Paint.Align.LEFT
        paint.color = Color.LTGRAY
        canvas.drawRect(x, y7, x4, y8, paint)
        paint.color = Color.BLACK
        var n = 10
        val x3 = x + 4
        val x9 = x + (x4 - x) / 3
        val x10 = x9 + (x4 - x) / 3
        canvas.drawLine(x9, y7, x9, y8, paint)
        canvas.drawLine(x10, y7, x10, y8, paint)
        canvas.drawText("Banco", x3 + 3, y7 + n, paint)
        canvas.drawText("Cuenta Corriente", x9 +  3, y7 + n, paint)
        canvas.drawText("Cunta C.C.I.", x10 + 3, y7 + n, paint)

        lstCuentasBancarias.forEach{ it->
            canvas.drawText(it.nombreBanco, x3 + 3, y8 + n, paint)
            canvas.drawText(it.nroCuetaCorriente, x9 + 3, y8 + n, paint)
            canvas.drawText(it.nroCuentaCCI, x10 + 3, y8 + n, paint)
            n += 10
        }
    }
}