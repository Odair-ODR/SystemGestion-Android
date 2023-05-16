package com.example.website.consulta.View

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.print.*
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.website.consulta.Helpers.Constants
import com.example.website.consulta.Helpers.UtilsMethod
import com.example.website.consulta.Model.Entidad.*
import com.example.website.consulta.Model.FacturaTicket
import com.example.website.consulta.Model.MultipleObservable
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.ui.ScanningActivity
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback
import java.io.*
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList


class ComprobanteTicket(val context: Context) : IComprobante {
    private lateinit var lstPreFacturaDet: List<FacturaDetTo>
    private lateinit var preFactura: FacturaCabTo
    private var pageWidth: Int = 0
    private var pageHeight: Int = 0
    private var top: Float = 0f
    private var left: Float = 0f
    private var right: Float = 0f
    private var lstCuentasBancarias: List<CuentasBancariasTo>
    private var multipleObs: MultipleObservable = MultipleObservable()
    private var logoBinary: ByteArray? = null
    private var y: Float = 0f
    private var printing: Printing? = null
    init {
        lstCuentasBancarias = multipleObs.listaCuentasBancarias()
        logoBinary = multipleObs.obtenerLogoEmpresa()
    }

    override fun generarPDF(preFactura: FacturaCabTo, lstPreFacturaDet: List<FacturaDetTo>) {
        this.lstPreFacturaDet = lstPreFacturaDet
        this.preFactura = preFactura
        // Define el tamaño de la página en puntos
        pageWidth = (7.8 * 28.3464567).toInt()
        pageHeight = (25 * 28.3464567).toInt()
        top = 10f
        left = 5f
        right = 5f
        val pdfDocument = PdfDocument()
        val paint = Paint()
        val pdfInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pdfInfo)
        val canvas = page.canvas
        createHead(paint, canvas)
        createBody(paint, canvas)
        createFooter(paint, canvas)
        pdfDocument.finishPage(page)
        val myDir = File(context.getExternalFilesDir(null), Constants.nameFolerInovice)
        if (!myDir.exists()) {
            myDir.mkdir()
        }
        val filePdf = File(
            context.getExternalFilesDir(Constants.directoryInvoices),
            preFactura.nombreArchivoPdf
        )
        //> pdfDocument.writeTo(FileOutputStream(filePdf))

        // Obtener el adaptador Bluetooth
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        // Verificar si el Bluetooth está habilitado
        val permission = Manifest.permission.BLUETOOTH_CONNECT
        val requestCode = 1
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)
            }
            (context as Activity).startActivityForResult(enableBtIntent, requestCode)
        }


        val deviceAddress = "04:7F:0E:98:2E:D1" // Reemplazar con la dirección MAC de la impresora

        //> val device = getBluetoothDeviceByName("A30E-2ED1")//> bluetoothAdapter.getRemoteDevice(deviceAddress)
        //> connectBT(device!!.address)
        printTicket()
    }

/*    private fun getBluetoothDeviceByName(deviceName: String): BluetoothDevice? {
        val permission = Manifest.permission.BLUETOOTH_CONNECT
        val requestCode = 1
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)
        }
        val pairedDevices = BluetoothAdapter.getDefaultAdapter().bondedDevices
        for (device in pairedDevices) {
            if (device.name == deviceName) {
                return device
            }
        }
        return null
    }*/

    private fun connectBT(selectedBDAddress: String) {
        if (TextUtils.isEmpty(selectedBDAddress)) return
        try {
            val result: Int = 0 //> PrinterHelper.portOpenBT(context, selectedBDAddress)
            if (result == 0) Toast.makeText(context, "conectado", Toast.LENGTH_SHORT).show() else Toast.makeText(context, "error al conectar ${result}", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun agregarLogo(canvas: Canvas, paint: Paint) {
        if (logoBinary?.isNotEmpty() == true) {
            val bitmap = UtilsMethod.getBitmapImgFromSqlDB(logoBinary!!)
            val bitmapScale: Bitmap =
                Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
            canvas.drawBitmap(bitmapScale, left, top, paint)
        }
    }

    private fun agregarTitulos(paint: Paint, canvas: Canvas) {
        //> start -> Rectangulo
        val normal = Typeface.create("Arial", Typeface.NORMAL)
        paint.setTypeface(normal)
        paint.setTextSize(8f)

        // Define los datos a imprimir
        val nombre = "Edira del Pilar Tello Aranda"
        val direccion = "Oficina calle las prensas 149 Urb. Industrial Naranal"
        val telefono = "717484/983748579"
        val email = "repuestos_chinos_tello@hotmail.com"
        val ruc = "1083945840934"
        val numeroDocVenta = when (preFactura.tipoDoc) {
            TIPO_DOCUMENTO.FACTURA -> "F ${preFactura.serDoc}-${preFactura.numDoc}"
            TIPO_DOCUMENTO.BOLETA -> "B ${preFactura.serDoc}-${preFactura.numDoc}"
            else -> "${preFactura.serDoc}-${preFactura.numDoc}"
        }
        val factura = "Factura Electrónica: $numeroDocVenta"

        // Define la posición de los elementos
        y = 30f
        val separacion = 10

        // Calcula la posición x del centro de la página
        var anchoTexto = paint.measureText(nombre)
        var x = ((pageWidth - anchoTexto) / 2)

        // Dibuja los elementos en la página
        canvas.drawText(nombre, x.toFloat(), y.toFloat(), paint)
        y += separacion
        anchoTexto = paint.measureText(direccion)
        x = ((pageWidth - anchoTexto) / 2)
        canvas.drawText(direccion, x.toFloat(), y.toFloat(), paint)
        y += separacion
        anchoTexto = paint.measureText("Teléfono: $telefono")
        x = ((pageWidth - anchoTexto) / 2)
        canvas.drawText("Teléfono: $telefono", x.toFloat(), y.toFloat(), paint)
        y += separacion
        anchoTexto = paint.measureText("Email: $email")
        x = ((pageWidth - anchoTexto) / 2)
        canvas.drawText("Email: $email", x.toFloat(), y.toFloat(), paint)
        y += separacion
        anchoTexto = paint.measureText("RUC: $ruc")
        x = ((pageWidth - anchoTexto) / 2)
        canvas.drawText("RUC: $ruc", x.toFloat(), y.toFloat(), paint)
        y += separacion
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        anchoTexto = paint.measureText(factura)
        x = ((pageWidth - anchoTexto) / 2)
        canvas.drawText(factura, x.toFloat(), y.toFloat(), paint)
    }

    private fun agregarDatosGeneralesCabecera(paint: Paint, canvas: Canvas) {
        //> Rectangulo para datos generales
        paint.setStyle(Paint.Style.STROKE)
        paint.setStrokeWidth(1f)
        y += 5
        val startY = y
        val startX = left
        val endY = y + 75f
        val endX = pageWidth - right
        canvas.drawRect(startX, startY.toFloat(), endX, endY, paint)

        paint.setStyle(Paint.Style.FILL)
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        // Define los datos a imprimir
        val fecha = "Fecha: ${preFactura.fectra}"
        val cliente = "Cliente: ${preFactura.nombres}"
        val direccion = "Dirección: ${preFactura.direccion}"
        val numero = "Nro.Doc: ${preFactura.nroFactura}"
        val placa = "Placa: ${preFactura.placa}"
        val guia = "Guia: ${preFactura.numgui}"
        val condicion = "Condición: ${preFactura.conpag.descripcion}"
        val codVend = "Codven: ${preFactura.idUs}"

        val separacion = 10
        val x = left + 2
        var y2 = y + separacion
        canvas.drawText(fecha, x, y2, paint)
        y2 += separacion

        canvas.drawText(cliente, x, y2, paint)
        y2 += separacion

        canvas.drawText(direccion, x, y2, paint)
        y2 += separacion

        canvas.drawText(numero, x, y2, paint)
        y2 += separacion

        canvas.drawText(placa, x, y2, paint)
        //> Textos a la derecha
        val centerX = (pageWidth / 2).toFloat()
        canvas.drawText(condicion, centerX, y2, paint)
        y2 += separacion

        canvas.drawText(guia, x, y2, paint)
        //> Textos a la derecha
        canvas.drawText(codVend, centerX, y2, paint)
        y = endY
    }

    override fun createHead(paint: Paint, canvas: Canvas) {
        //> agregarLogo(canvas, paint)
        agregarTitulos(paint, canvas)
        agregarDatosGeneralesCabecera(paint, canvas)
    }

    private fun obtenerTextoAjuste(
        paint: Paint,
        text: String?,
        anchoMaximoTexto: Int
    ): List<String> {
        // Verificar si la descripción es demasiado larga
        val descripcion = text ?: ""
        val descripcionWidth = paint.measureText(descripcion)
        val availableWidth = anchoMaximoTexto
        val lineCount = if (descripcionWidth > availableWidth) 2 else 1

        // Dividir la descripción en dos líneas si es necesario
        val lines = when (lineCount) {
            1 -> arrayOf(descripcion)
            2 -> {
                val midIndex = descripcion.length / 2
                val firstLineEnd = descripcion.lastIndexOf(' ', availableWidth)
                    .coerceAtLeast(midIndex)
                arrayOf(
                    descripcion.substring(0, firstLineEnd),
                    descripcion.substring(firstLineEnd).trimStart()
                )
            }
            else -> emptyArray()
        }

        // Ajustar las líneas para que encajen en el espacio disponible
        val adjustedLines = lines.map { line ->
            if (paint.measureText(line) > availableWidth) {
                var endIndex = line.length
                while (paint.measureText(line.substring(0, endIndex)) > availableWidth) {
                    endIndex--
                }
                line.substring(0, endIndex) + ""
            } else {
                line
            }
        }
        return adjustedLines
    }

    private fun agregarItemsComprobante(
        paint: Paint,
        canvas: Canvas,
        lstPreFacturaDet: List<FacturaDetTo>,
        bottom2: Float
    ) {
        var index = 1
        var ySize = bottom2 + 10
        val x = left + 2f
        val x2 = x + 10f
        val x3 = x2 + 40f
        val x5 = x3 + 80f
        val x6 = x5 + 22f
        val x7 = x6 + 30f
        val anchoMaximoTexto = x5 - x3
        lstPreFacturaDet.forEach {
            canvas.drawText(index.toString(), x, ySize, paint)
            canvas.drawText(it.al32Codbar, x2, ySize, paint)
            //> canvas.drawText(it.al32DescriArti!!, x3, ySize, paint)
            val ajustedLines =
                obtenerTextoAjuste(paint, it.al32DescriArti, anchoMaximoTexto.toInt())
            // Imprimir las líneas ajustadas en la posición adecuada
            var lineY = 0f
            ajustedLines.forEach { line ->
                canvas.drawText(line, x3, ySize + lineY, paint)
                lineY += 10f
            }
            canvas.drawText(it.al32Totcan.toString(), x5 + 4, ySize, paint)
            canvas.drawText(it.al32Preuni.toString(), x6, ySize, paint)
            canvas.drawText(it.al32pretot.toString(), x7, ySize, paint)
            ySize += lineY
            index++
        }
    }

    private fun agregarDetalleComprobante(paint: Paint, canvas: Canvas) {
        //> Lista de artículos
        paint.style = Paint.Style.FILL
        paint.color = Color.LTGRAY
        paint.strokeWidth = 1f
        val startX = left
        val endX = pageWidth - right
        val startY = y + 10
        val endY = startY + 15
        canvas.drawRect(startX, startY, endX, endY, paint)

        val x = left + 2f
        val x2 = x + 10f
        val x3 = x2 + 40f
        val x5 = x3 + 80f
        val x6 = x5 + 22f
        val x7 = x6 + 30f
        val y = startY + ((endY - startY) / 2) + 3
        paint.color = Color.BLACK
        paint.textAlign = Paint.Align.LEFT
        paint.textSize = 7f
        canvas.drawText("#", x, y, paint)
        canvas.drawText("CÓDIGO", x2, y, paint)
        canvas.drawText("DESCRIPCIÓN", x3, y, paint)
        canvas.drawText("CAN", x5, y, paint)
        canvas.drawText("P.UNI", x6, y, paint)
        canvas.drawText("TOTAL", x7, y, paint)
        agregarItemsComprobante(paint, canvas, lstPreFacturaDet, endY)
    }

    override fun createBody(paint: Paint, canvas: Canvas) {
        agregarDetalleComprobante(paint, canvas)
    }

    private fun agregarTotales(paint: Paint, canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 1f
        y += 300
        val startX = left
        val endX = pageWidth - right
        val startY = y + 10
        val endY = startY + 15
        val x = left + 2f
        canvas.drawRect(startX, startY, endX, endY, paint)

        val y = startY + ((endY - startY) / 2) + 3
        val numLet = "Son: ${preFactura.numlet}"
        paint.style = Paint.Style.FILL
        paint.color = Color.BLACK
        canvas.drawText(numLet, x, y, paint)

        //> Totales
        //> Rectangulos
        val startX2 = left + 50
        val startY2 = endY + 5
        val xL = (endX + startX2) / 2
        val matriz = Array(4) { Array(2) { "" } }
        val columnWidth = arrayOf(floatArrayOf(startX2, xL), floatArrayOf(xL, endX))
        createTableTotales(paint, canvas, matriz, columnWidth, startY2)
    }

    private fun createTableTotales(
        paint: Paint,
        canvas: Canvas,
        matriz: Array<Array<String>>,
        columnWidth: Array<FloatArray>,
        startY: Float
    ) {
        val cellsColumns1 = arrayListOf("Sub Total:", "Descuento:", "IGV: 18%", "Total:")
        val cellsColumns2 = arrayListOf(
            UtilsMethod.getDecimalthousandFormatted(
                preFactura.valven.toBigDecimal().setScale(2, RoundingMode.UP).toString()
            ),
            UtilsMethod.getDecimalthousandFormatted(
                preFactura.oDescuento.toBigDecimal().setScale(2, RoundingMode.UP).toString()
            ),
            UtilsMethod.getDecimalthousandFormatted(
                preFactura.valigv.toBigDecimal().setScale(2, RoundingMode.UP).toString()
            ),
            UtilsMethod.getDecimalthousandFormatted(
                preFactura.valbru.toBigDecimal().setScale(2, RoundingMode.UP).toString()
            )
        )

        var startY2 = startY
        var endY2 = startY2 + 15
        for (row in matriz.indices) {
            for (column in matriz[row].indices) {
                val yText = startY2 + ((endY2 - startY2) / 2) + 3
                if (column == 0) {
                    paint.color = Color.LTGRAY
                    paint.style = Paint.Style.FILL
                    canvas.drawRect(
                        columnWidth[column][0],
                        startY2,
                        columnWidth[column][1],
                        endY2,
                        paint
                    )
                    paint.color = Color.BLACK
                    paint.style = Paint.Style.STROKE
                    canvas.drawRect(
                        columnWidth[column][0],
                        startY2,
                        columnWidth[column][1],
                        endY2,
                        paint
                    )
                    paint.style = Paint.Style.FILL
                    paint.textAlign = Paint.Align.LEFT
                    canvas.drawText(cellsColumns1[row], columnWidth[column][0] + 2, yText, paint)
                } else {
                    paint.color = Color.BLACK
                    paint.style = Paint.Style.STROKE
                    canvas.drawRect(
                        columnWidth[column][0],
                        startY2,
                        columnWidth[column][1],
                        endY2,
                        paint
                    )
                    canvas.drawRect(
                        columnWidth[column][0],
                        startY2,
                        columnWidth[column][0] + 20f,
                        endY2,
                        paint
                    )
                    paint.style = Paint.Style.FILL
                    paint.textAlign = Paint.Align.RIGHT
                    canvas.drawText(
                        cellsColumns2[row].toString(),
                        columnWidth[column][1],
                        yText,
                        paint
                    )
                    val tipoMoneda = when (preFactura.idMoneda) {
                        1 -> "S/"
                        2 -> "$"
                        else -> ""
                    }
                    paint.textAlign = Paint.Align.RIGHT
                    canvas.drawText(tipoMoneda, columnWidth[column][0] + 17f, yText, paint)
                }
            }
            startY2 = endY2
            endY2 = startY2 + 15
        }
        y = endY2
    }

    private fun othersData(paint: Paint, canvas: Canvas) {
        paint.textAlign = Paint.Align.LEFT
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        val startX = left
        val codigoHass = "Código Hash: aPa5bFDvlx8OGBRroPJZJeDn5CM="
        canvas.drawText(codigoHass, startX, y, paint)
    }

    override fun createFooter(paint: Paint, canvas: Canvas) {
        agregarTotales(paint, canvas)
        othersData(paint, canvas)
    }

    private fun printTicket() {
        try{
            /*if(printing != null) {
                printing!!.printingCallback = this
            }*/
            val ticketPrint = TicketPrint(context, FacturaTicket(preFactura, lstPreFacturaDet))
            ticketPrint.generateTicket()
            ticketPrint.print()
        }catch (ex: Exception){
            print(ex.message)
        }
    }
}