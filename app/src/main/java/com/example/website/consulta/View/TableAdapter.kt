package com.example.website.consulta.View

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import kotlin.collections.List

class TableAdapter(var context: Context, var tableLayout: TableLayout) {
    private var header: Array<String>? = null
    private var data: List<Articulo>? = null
    private var indexC: Int = 0

    internal class ViewHolder {
        var item1: TextView? = null
        var item2: TextView? = null
        var item3: TextView? = null
        var item4: TextView? = null
        var item5: TextView? = null
        var item6: TextView? = null
        var item7: TextView? = null
    }

    fun AddHeader(header: Array<String>) {
        this.header = header
        CreateHeader()
    }

    fun AddDataArticuloVenta(data: List<Articulo>?) {
        this.data = data
        LoadDataTableArticulo()
    }

    private fun NewRow(): TableRow {
        return TableRow(context)
    }

    private fun NewCell(): TextView? {
        val textCell = TextView(context)
        textCell?.gravity = Gravity.CENTER
        textCell?.textSize = 13F
        return textCell
    }

    private fun CreateHeader() {
        val tableRow = NewRow()
        var textCell: TextView?
        while (indexC < header?.size ?: 0) {
            textCell = NewCell()
            textCell?.setText(header?.get(indexC++))
            HeaderCellStyle(textCell)
            tableRow?.addView(textCell, NewTableRowParams())
        }
        tableLayout.addView(tableRow)
    }

    private fun HeaderCellStyle(textCell: TextView?) {
        textCell?.setBackgroundResource(R.color.colorGreenLight)
        textCell?.setPadding(0, 12, 0, 12)
    }

    private fun RowCellStyle(viewHolder: ViewHolder) {
        viewHolder.item1?.setPadding(0, 8, 0, 8)
        viewHolder.item1?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item2?.setPadding(0, 8, 0, 8)
        viewHolder.item2?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item3?.setPadding(0, 8, 0, 8)
        viewHolder.item3?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item4?.setPadding(0, 8, 0, 8)
        viewHolder.item4?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item5?.setPadding(0,8, 0, 8)
        viewHolder.item5?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item6?.setPadding(0,8,0,8)
        viewHolder.item6?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item7?.setPadding(0,8,0, 8)
        viewHolder.item7?.setBackgroundResource(R.color.colorLight1)
    }

    private fun LoadDataTableArticulo() {
        var viewHolder: ViewHolder? = null
        var tableRow: TableRow
        for (row in data!!) {
            tableRow = NewRow()

            viewHolder = ViewHolder()
            viewHolder.item1 = NewCell()
            viewHolder.item2 = NewCell()
            viewHolder.item3 = NewCell()
            viewHolder.item4 = NewCell()

            viewHolder.item1?.setText(row.codbar)
            viewHolder.item2?.setText(row.alternante)
            viewHolder.item3?.setText(row.descriArti)
            viewHolder.item4?.setText(row.totSaldo.toString())

            RowCellStyle(viewHolder)

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableRow.addView(viewHolder.item4, NewTableRowParams())

            tableLayout.addView(tableRow)
        }
    }

    private fun NewTableRowParams(): TableRow.LayoutParams {
        var params = TableRow.LayoutParams()
        params.setMargins(1, 1, 1, 1)
        params.weight = 1F
        return params
    }

    fun AddDataArticuloXCodbar(data: List<Articulo>) {
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = NewRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = NewCell()
            viewHolder.item2 = NewCell()
            viewHolder.item3 = NewCell()
            viewHolder.item4 = NewCell()
            viewHolder.item5 = NewCell()
            viewHolder.item6 = NewCell()

            RowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.alternante)
            viewHolder.item2?.setText(item.campar.toString())
            viewHolder.item3?.setText(item.cpdnew)
            viewHolder.item4?.setText(item.unimed)
            viewHolder.item5?.setText(item.motor)
            viewHolder.item6?.setText(item.totSaldo.toString())

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableRow.addView(viewHolder.item4, NewTableRowParams())
            tableRow.addView(viewHolder.item5, NewTableRowParams())
            tableRow.addView(viewHolder.item6, NewTableRowParams())
            tableLayout.addView(tableRow)
        }
    }

    fun AddDataArticuloXAlternante(data: List<Articulo>){

    }
}