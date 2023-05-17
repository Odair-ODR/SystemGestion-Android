package com.example.website.consulta.View

import android.content.Context
import android.graphics.Paint
import android.widget.Toast
import com.example.website.consulta.Model.ITicket
import com.mazenrashed.printooth.Printooth
import com.mazenrashed.printooth.data.printable.Printable
import com.mazenrashed.printooth.data.printable.RawPrintable
import com.mazenrashed.printooth.data.printable.TextPrintable
import com.mazenrashed.printooth.data.printer.DefaultPrinter
import com.mazenrashed.printooth.utilities.Printing
import com.mazenrashed.printooth.utilities.PrintingCallback

data class TicketPrint(val context: Context, val ticket: ITicket) : PrintingCallback {
    private var printing: Printing
    private var printable: ArrayList<Printable> = ArrayList()

    init {
        Printooth.init(context)
        Printooth.setPrinter("A30E-2ED1", "04:7F:0E:98:2E:D1")
        printing = Printooth.printer()
        printing.printingCallback = this
    }

    fun generateTicket() {
        headerPage()
        body()
        footer()
    }

    private fun headerPage() {
        //> printable.add(RawPrintable.Builder(byteArrayOf(27, 100, 4)).build())
        val empresa = ticket.getEmpresa()
        val arrDataEmpresa = arrayOf(
            empresa.nombre,
            empresa.descripcion,
            empresa.direccion,
            empresa.ruc,
            empresa.telefono,
            empresa.correo
        )
        for (item in arrDataEmpresa) {
            printable.add(
                TextPrintable.Builder()
                    .setText(item)
                    .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                    .setAlignment(DefaultPrinter.Companion.ALIGNMENT_CENTER)
                    .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_BOLD)
                    .setNewLinesAfter(1)
                    .build()
            )
        }

        printable.add(
            TextPrintable.Builder()
                .setText("=".repeat(47))
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
        printable.add(
            TextPrintable.Builder()
                .setText(ticket.getDescTipoDocumento())
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
        printable.add(
            TextPrintable.Builder()
                .setText(ticket.getNroTipoDocumento())
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
        printable.add(
            TextPrintable.Builder()
                .setText("=".repeat(47))
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_CENTER)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_BOLD)
                .setNewLinesAfter(1)
                .build()
        )
    }

    private fun body() {
        val items = arrayOf(ticket.getFechaDocumento(), ticket.getVendedor().nombre)
        for (item in items) {
            printable.add(
                TextPrintable.Builder()
                    .setText(item)
                    .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                    .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                    .setNewLinesAfter(1)
                    .build()
            )
        }

        printable.add(
            TextPrintable.Builder()
                .setText("=".repeat(47))
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )

        val cliente = arrayOf(
            ticket.getCliente().nombre,
            ticket.getCliente().nroDocumento
        )
        for (item in cliente) {
            printable.add(
                TextPrintable.Builder()
                    .setText(item)
                    .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                    .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                    .setNewLinesAfter(1)
                    .build()
            )
        }

        printable.add(
            TextPrintable.Builder()
                .setText("=".repeat(47))
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )

        detailItems()
    }

    private fun detailItems() {
        val formattedTableHead = String.format(
            "#  %-20s  %-4s  %-7s  %-7s\n%s",
            "Descrip.", "Cant", "P.Uni", "Total",
            "=".repeat(47)
        )

        printable.add(
            TextPrintable.Builder()
                .setText(formattedTableHead)
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )

        var formattedTableDetail: String
        val items = ticket.getItems()
        var descItemAjuste: List<String> = mutableListOf()
        items.forEach {
            descItemAjuste = obtenerTextoAjuste(it.descItem, 20)
            formattedTableDetail = String.format(
                "%-2s  %-20s  %-4s  %-7s  %-7s",
                it.sec, descItemAjuste[0], it.can, it.preUni, it.total
            )
            printable.add(
                TextPrintable.Builder()
                    .setText(formattedTableDetail)
                    .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                    .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                    .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                    .setNewLinesAfter(1)
                    .build()
            )

            if (descItemAjuste.size > 1) {
                for (desc in descItemAjuste.drop(1)) {
                    formattedTableDetail = String.format(
                        "%-2s  %-20s  %-4s  %-7s  %-7s",
                        "", desc, "", "", ""
                    )
                    printable.add(
                        TextPrintable.Builder()
                            .setText(formattedTableDetail)
                            .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                            .setAlignment(DefaultPrinter.Companion.ALIGNMENT_LEFT)
                            .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                            .setNewLinesAfter(1)
                            .build()
                    )
                }
            }
        }
    }

    private fun obtenerTextoAjuste(
        text: String,
        textMaxWidth: Int
    ): List<String> {
        val lines = mutableListOf<String>()
        val textWidth = text.length
        val lineCount = if (textWidth > textMaxWidth) 2 else 1

        when (lineCount) {
            1 -> lines.add(text)
            2 -> {
                var remainingText = text.trim()
                while (remainingText.isNotEmpty()) {
                    val endIndex = if (remainingText.length > textMaxWidth) {
                        val lastSpaceIndex = remainingText.lastIndexOf(' ', textMaxWidth)
                        if (lastSpaceIndex >= 0) {
                            lastSpaceIndex
                        } else {
                            textMaxWidth
                        }
                    } else {
                        remainingText.length
                    }
                    val line = remainingText.substring(0, endIndex).trim()
                    lines.add(line)
                    remainingText = remainingText.substring(endIndex).trimStart().trimEnd()
                }
            }
        }
        return lines
    }

    private fun footer() {
        totales()
    }

    private fun totales() {

        printable.add(
            TextPrintable.Builder()
                .setText("")
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )

        val desTotal = arrayOf("Sub Total: ", "I.G.V.: ", "Total: ")
        val totales = arrayOf(ticket.getTotales().subTotal, ticket.getTotales().igv, ticket.getTotales().total)
        var formattedTableDetail = ""
        for ((index, item) in totales.withIndex()) {
            formattedTableDetail = String.format(
                "%-30s  %-5s  %-20s",
                desTotal[index], ticket.getTotales().simboloMoneda, item
            )
            printable.add(
                TextPrintable.Builder()
                    .setText(formattedTableDetail)
                    .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_30)
                    .setAlignment(DefaultPrinter.Companion.ALIGNMENT_RIGHT)
                    .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                    .setNewLinesAfter(1)
                    .build()
            )
        }

        printable.add(
            TextPrintable.Builder()
                .setText("")
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )

        printable.add(
            TextPrintable.Builder()
                .setText("")
                .setLineSpacing(DefaultPrinter.Companion.LINE_SPACING_60)
                .setAlignment(DefaultPrinter.Companion.ALIGNMENT_RIGHT)
                .setEmphasizedMode(DefaultPrinter.Companion.EMPHASIZED_MODE_NORMAL)
                .setNewLinesAfter(1)
                .build()
        )
    }

    fun print() {
        printing.print(printable)
    }

    private fun changePairAndUnpair() {
        if (Printooth.hasPairedPrinter()) {
            Toast.makeText(
                context,
                "Printing" + Printooth.getPairedPrinter()?.name,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(context, "Fair to wait for printer", Toast.LENGTH_SHORT).show()
        }
    }

    override fun connectingWithPrinter() {
        Toast.makeText(context, "Connecting to printer", Toast.LENGTH_SHORT).show()
    }

    override fun connectionFailed(error: String) {
        Toast.makeText(context, "Connection failed", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: String) {
        Toast.makeText(context, "Error" + error, Toast.LENGTH_SHORT).show()

    }

    override fun onMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    override fun printingOrderSentSuccessfully() {
        Toast.makeText(context, "Orderd sent to printer", Toast.LENGTH_SHORT).show()
    }
}
