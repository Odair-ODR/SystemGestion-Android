package com.example.website.consulta.View

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TableAdapter(var context: Context, var tableLayout: TableLayout) {
    //> val iTableAdapterListener: ITableAdapterListener
    private var header: Array<String>? = null
    private var data: List<Articulo>? = null
    private lateinit var row: TableRow
    private var indexC: Int = 0
    protected var cells: MutableMap<View, Rect> = HashMap<View, Rect>()

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

    fun AddHeaderArticulo(header: Array<String>) {
        this.header = header
        CreateHeaderArticulo()
    }

    fun AddDataArticuloVenta(data: List<Articulo>?) {
        this.data = data
        LoadDataTableArticulo()
    }

    private fun NewRow(): TableRow {
        return TableRow(context)
    }

    private fun NewCell(): TextView {
        val textCell = TextView(context)
        textCell.gravity = Gravity.CENTER
        textCell.setPadding(2, 0, 2, 0)
        textCell.textSize = 13F
        return textCell
    }

    private fun CreateHeader() {
        if (tableLayout.childCount == 0) {
            val tableRow = NewRow()
            var textCell: TextView?
            while (indexC < header?.size ?: 0) {
                textCell = NewCell()
                textCell.setText(header?.get(indexC++))
                HeaderCellStyle(textCell)
                tableRow.addView(textCell, NewTableRowParams())
            }
            tableLayout.addView(tableRow)
        }
    }

    private fun CreateHeaderArticulo() {
        if (tableLayout.childCount == 0) {
            val tableRow = NewRow()
            var textCell: TextView?
            while (indexC < header?.size ?: 0) {
                textCell = NewCell()
                textCell.visibility = if (indexC == 0 || indexC == 5) View.GONE else View.VISIBLE
                textCell.setText(header?.get(indexC++))
                HeaderCellStyle(textCell)
                tableRow.addView(textCell, NewTableRowParams())
            }
            tableLayout.addView(tableRow)
        }
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
        viewHolder.item5?.setPadding(0, 8, 0, 8)
        viewHolder.item5?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item6?.setPadding(0, 8, 0, 8)
        viewHolder.item6?.setBackgroundResource(R.color.colorLight1)
        viewHolder.item7?.setPadding(0, 8, 0, 8)
        viewHolder.item7?.setBackgroundResource(R.color.colorLight1)
    }

    private fun LoadDataTableArticulo() {
        var viewHolder: ViewHolder? = null
        var tableRow: TableRow
        for (row in data!!) {
            tableRow = NewRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = NewCell()
            viewHolder.item1?.visibility = View.GONE
            viewHolder.item2 = NewCell()
            viewHolder.item5 = NewCell()
            viewHolder.item6 = NewCell()
            viewHolder.item7 = NewCell()
            viewHolder.item3 = NewCell()
            viewHolder.item3?.visibility = View.GONE

            viewHolder.item1?.setText(row.idArticulo.toString())
            viewHolder.item2?.setText(row.cpdold)
            viewHolder.item5?.setText(row.alternante)
            viewHolder.item6?.setText(row.descriArti)
            viewHolder.item7?.setText(row.precioVenta.toString())
            viewHolder.item3?.setText(row.totCan.toString())
            val view: View = viewHolder.item7 as View
            val rect: Rect = getRawCoordinatesRect(view)
            cells.put(view, rect)

            RowCellStyle(viewHolder)

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item5, NewTableRowParams())
            tableRow.addView(viewHolder.item6, NewTableRowParams())
            tableRow.addView(viewHolder.item7, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableLayout.addView(tableRow)
        }
    }

    private fun getRawCoordinatesRect(view: View): Rect {
        val coords = IntArray(2)
        view.getLocationOnScreen(coords)
        val rect = Rect()
        rect.left = coords[0]
        rect.top = coords[1]
        rect.right = rect.left + view.width
        rect.bottom = rect.top + view.height
        return rect
    }

    private fun NewTableRowParams(): TableRow.LayoutParams {
        val params = TableRow.LayoutParams()
        params.setMargins(1, 2, 1, 2)
        params.weight = 1F
        return params
    }

    fun AddDataArticuloXCodbar(data: List<Articulo>) {
        BorrarFilas()
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
            viewHolder.item7 = NewCell()

            RowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.alternante)
            viewHolder.item2?.setText(item.campar.toString())
            viewHolder.item3?.setText(item.cpdnew)
            viewHolder.item4?.setText(item.unimed)
            viewHolder.item5?.setText(item.motor)
            viewHolder.item6?.setText(item.totSaldo.toString())
            viewHolder.item7?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableRow.addView(viewHolder.item4, NewTableRowParams())
            tableRow.addView(viewHolder.item5, NewTableRowParams())
            tableRow.addView(viewHolder.item6, NewTableRowParams())
            tableRow.addView(viewHolder.item7, NewTableRowParams())

            //> iTableAdapterListener.onClickArticulo(tableRow)

            tableLayout.addView(tableRow)
        }
    }

    fun AddDataArticuloXAlternante(data: List<Articulo>) {
        BorrarFilas()
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
            viewHolder.item2?.setText(item.cpdnew)
            viewHolder.item3?.setText(item.codbar)
            viewHolder.item4?.setText(item.motor)
            viewHolder.item5?.setText(item.totSaldo.toString())
            viewHolder.item6?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableRow.addView(viewHolder.item4, NewTableRowParams())
            tableRow.addView(viewHolder.item5, NewTableRowParams())
            tableRow.addView(viewHolder.item6, NewTableRowParams())
            tableLayout.addView(tableRow)
        }
    }

    fun AddDataMotor(data: ArrayList<Motor>, alertDialog: AlertDialog, txtMotor: EditText) {
        BorrarFilas()
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = NewRow()
            Row_OnClickListenerMotor(tableRow, alertDialog, txtMotor)
            viewHolder = ViewHolder()
            viewHolder.item1 = NewCell()
            viewHolder.item2 = NewCell()
            viewHolder.item3 = NewCell()

            RowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.marca)
            viewHolder.item2?.setText(item.motor)
            viewHolder.item3?.setText(item.cili1)

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableLayout.addView(tableRow)
        }
    }

    private fun Row_OnClickListenerMotor(
        tableRow: TableRow,
        alertDialog: AlertDialog,
        txtMotor: EditText
    ) {
        tableRow.isClickable = true
        tableRow.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val tablerow = view as TableRow
                val textCell = tablerow.getChildAt(1) as TextView
                val motor = textCell.text.toString()
                txtMotor.setText(motor)
                alertDialog.dismiss()
            }
        })
    }

    fun AddDataArticuloXMotorCodProd(data: List<Articulo>) {
        BorrarFilas()
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
            viewHolder.item7 = NewCell()

            RowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.cpdnew)
            viewHolder.item2?.setText(item.alternante)
            viewHolder.item3?.setText(item.codbar)
            viewHolder.item4?.setText(item.unimed)
            viewHolder.item5?.setText(item.campar.toString())
            viewHolder.item6?.setText(item.totSaldo.toString())
            viewHolder.item7?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, NewTableRowParams())
            tableRow.addView(viewHolder.item2, NewTableRowParams())
            tableRow.addView(viewHolder.item3, NewTableRowParams())
            tableRow.addView(viewHolder.item4, NewTableRowParams())
            tableRow.addView(viewHolder.item5, NewTableRowParams())
            tableRow.addView(viewHolder.item6, NewTableRowParams())
            tableRow.addView(viewHolder.item7, NewTableRowParams())
            tableLayout.addView(tableRow)
        }
    }

    private fun BorrarFilas() {
        val count: Int = tableLayout.getChildCount()
        for (i in 1 until count) {
            val child: View = tableLayout.getChildAt(i)
            if (child is TableRow) (child as ViewGroup).removeAllViews()
        }
    }
}