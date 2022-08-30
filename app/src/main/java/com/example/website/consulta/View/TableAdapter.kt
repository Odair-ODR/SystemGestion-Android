package com.example.website.consulta.View

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor
import com.example.website.consulta.R
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class TableAdapter(
    var context: Context,
    var tableLayoutHead: TableLayout,
    var tableLayoutDetail: TableLayout
) {
    //> val iTableAdapterListener: ITableAdapterListener
    private var header: Array<String>? = null
    private var data: List<Articulo>? = null
    private lateinit var row: TableRow
    private var indexC: Int = 0

    lateinit var headerCellsWidth: IntArray

    internal class ViewHolder {
        var item1: TextView? = null
        var item2: TextView? = null
        var item3: TextView? = null
        var item4: TextView? = null
        var item5: TextView? = null
        var item6: TextView? = null
        var item7: TextView? = null
        var item8: TextView? = null
        var item9: TextView? = null
        var item10: TextView? = null
        var item11: TextView? = null
        var item12: TextView? = null
        var item13: TextView? = null
    }

    interface OnClickCallBackRow {
        fun onClickRow(view: View?)
    }

    fun addHead(header: Array<String>) {
        this.header = header
        headerCellsWidth = IntArray(header.size)
        createHead()
        getWidthCelssOfTableDatil()
    }

    fun addHeaderArticulo(header: Array<String>) {
        this.header = header
        headerCellsWidth = IntArray(header.size)
        createHeaderArticulo()
        getWidthCelssOfTableDatil()
    }

    fun addDataArticuloVenta(data: List<Articulo>?) {
        this.data = data
        loadDataTableArticulo()
    }

    fun addDataArticuloVentaReg(data: List<Articulo>?) {
        this.data = data
        loadDataTableArticulo2()
    }

    private fun newRow(): TableRow {
        return TableRow(context)
    }

    private fun newCell(): TextView {
        val textCell = TextView(context)
        textCell.gravity = Gravity.CENTER
        textCell.setPadding(2, 0, 2, 0)
        textCell.textSize = 13F
        return textCell
    }

    private fun createHead() {
        if (tableLayoutHead.childCount == 0) {
            val tableRow = newRow()
            var textCell: TextView?
            indexC = 0
            while (indexC < header?.size ?: 0) {
                textCell = newCell()
                textCell.setText(header?.get(indexC++))
                headerCellStyle(textCell)
                tableRow.addView(textCell, newTableRowParams())
            }
            tableLayoutHead.addView(tableRow)
        }
    }

    private fun getWidthCelssOfTableDatil() {
        val tableBChildCount = (tableLayoutHead.getChildAt(0) as TableRow).childCount
        for (x in 0 until tableBChildCount) {
            headerCellsWidth[x] =
                viewWidth((tableLayoutHead.getChildAt(0) as TableRow).getChildAt(x))
        }
    }

    private fun viewWidth(view: View): Int {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        return view.getMeasuredWidth()
    }

    private fun createHeaderArticulo() {
        if (tableLayoutHead.childCount == 0) {
            val tableRow = newRow()
            var textCell: TextView?
            while (indexC < header?.size ?: 0) {
                textCell = newCell()
                textCell.visibility = if (indexC == 0 || indexC == 5) View.GONE else View.VISIBLE
                textCell.setText(header?.get(indexC++))
                headerCellStyle(textCell)
                tableRow.addView(textCell, newTableRowParams())
            }
            tableLayoutHead.addView(tableRow)
        }
    }

    private fun headerCellStyle(textCell: TextView?) {
        textCell?.setBackgroundResource(R.color.colorGreenLight)
        textCell?.setPadding(0, 12, 0, 12)
    }

    private fun rowCellStyle(viewHolder: ViewHolder) {
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

    private fun loadDataTableArticulo() {
        var viewHolder: ViewHolder?
        var tableRow: TableRow
        for (row in data!!) {
            tableRow = newRow()
            setOnRowClickListener(tableRow)

            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item1?.visibility = View.GONE
            viewHolder.item2 = newCell()
            viewHolder.item5 = newCell()
            viewHolder.item6 = newCell()
            viewHolder.item7 = newCell()
            viewHolder.item3 = newCell()
            viewHolder.item3?.visibility = View.GONE

            viewHolder.item1?.setText(row.idArticulo.toString())
            viewHolder.item2?.setText(row.cpdold)
            viewHolder.item5?.setText(row.alternante)
            viewHolder.item6?.setText(row.descriArti)
            viewHolder.item7?.setText(row.precioVenta.toString())
            viewHolder.item3?.setText(row.campar.toString())

            rowCellStyle(viewHolder)

            tableRow.addView(viewHolder.item1, newTableRowParams(headerCellsWidth[0]))
            tableRow.addView(viewHolder.item2, newTableRowParams(headerCellsWidth[1]))
            tableRow.addView(viewHolder.item5, newTableRowParams(headerCellsWidth[2]))
            tableRow.addView(viewHolder.item6, newTableRowParams(headerCellsWidth[3]))
            tableRow.addView(viewHolder.item7, newTableRowParams(headerCellsWidth[4]))
            tableRow.addView(viewHolder.item3, newTableRowParams(headerCellsWidth[5]))
            tableLayoutDetail.addView(tableRow)
        }
    }

    private fun loadDataTableArticulo2() {
        var viewHolder: ViewHolder?
        var tableRow: TableRow
        for (row in data!!) {
            tableRow = newRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item1?.visibility = View.GONE
            viewHolder.item2 = newCell()
            viewHolder.item5 = newCell()
            viewHolder.item6 = newCell()
            viewHolder.item7 = newCell()
            viewHolder.item3 = newCell()
            viewHolder.item3?.visibility = View.GONE

            viewHolder.item8 = newCell()
            viewHolder.item9 = newCell()
            viewHolder.item10 = newCell()
            viewHolder.item11 = newCell()
            viewHolder.item12 = newCell()
            viewHolder.item13 = newCell()

            viewHolder.item1?.setText(row.idArticulo.toString())
            viewHolder.item2?.setText(row.cpdold)
            viewHolder.item5?.setText(row.alternante)
            viewHolder.item6?.setText(row.descriArti)
            viewHolder.item7?.setText(row.precioVenta.toString())
            viewHolder.item3?.setText(row.totCan.toString())

            rowCellStyle(viewHolder)

            tableRow.addView(viewHolder.item1, newTableRowParams(headerCellsWidth[0]))
            tableRow.addView(viewHolder.item2, newTableRowParams(headerCellsWidth[1]))
            tableRow.addView(viewHolder.item5, newTableRowParams(headerCellsWidth[2]))
            tableRow.addView(viewHolder.item6, newTableRowParams(headerCellsWidth[3]))
            tableRow.addView(viewHolder.item7, newTableRowParams(headerCellsWidth[4]))
            tableRow.addView(viewHolder.item3, newTableRowParams(headerCellsWidth[5]))

            tableLayoutDetail.addView(tableRow)
        }
    }

    private fun setOnRowClickListener(tableRow: TableRow) {
        tableRow.isClickable = true
        tableRow.setOnClickListener(implementOnRowClickListener)
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

    private fun newTableRowParams(): TableRow.LayoutParams {
        val params = TableRow.LayoutParams()
        params.setMargins(1, 2, 1, 2)
        params.weight = 1F
        return params
    }

    private fun newTableRowParams(width: Int): TableRow.LayoutParams {
        val params = TableRow.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(1, 2, 1, 2)
        params.weight = 1F
        return params
    }

    fun AddDataArticuloXCodbar(data: List<Articulo>) {
        borrarFilas()
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = newRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item2 = newCell()
            viewHolder.item3 = newCell()
            viewHolder.item4 = newCell()
            viewHolder.item5 = newCell()
            viewHolder.item6 = newCell()
            viewHolder.item7 = newCell()

            rowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.alternante)
            viewHolder.item2?.setText(item.campar.toString())
            viewHolder.item3?.setText(item.cpdnew)
            viewHolder.item4?.setText(item.unimed)
            viewHolder.item5?.setText(item.motor)
            viewHolder.item6?.setText(item.totSaldo.toString())
            viewHolder.item7?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, newTableRowParams(headerCellsWidth[0]))
            tableRow.addView(viewHolder.item2, newTableRowParams(headerCellsWidth[1]))
            tableRow.addView(viewHolder.item3, newTableRowParams(headerCellsWidth[2]))
            tableRow.addView(viewHolder.item4, newTableRowParams(headerCellsWidth[3]))
            tableRow.addView(viewHolder.item5, newTableRowParams(headerCellsWidth[4]))
            tableRow.addView(viewHolder.item6, newTableRowParams(headerCellsWidth[5]))
            tableRow.addView(viewHolder.item7, newTableRowParams(headerCellsWidth[6]))

            tableLayoutDetail.addView(tableRow)
        }
    }

    fun addDataArticuloXAlternante(data: List<Articulo>) {
        borrarFilas()
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = newRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item2 = newCell()
            viewHolder.item3 = newCell()
            viewHolder.item4 = newCell()
            viewHolder.item5 = newCell()
            viewHolder.item6 = newCell()

            rowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.alternante)
            viewHolder.item2?.setText(item.cpdnew)
            viewHolder.item3?.setText(item.codbar)
            viewHolder.item4?.setText(item.motor)
            viewHolder.item5?.setText(item.totSaldo.toString())
            viewHolder.item6?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, newTableRowParams(headerCellsWidth[0]))
            tableRow.addView(viewHolder.item2, newTableRowParams(headerCellsWidth[1]))
            tableRow.addView(viewHolder.item3, newTableRowParams(headerCellsWidth[2]))
            tableRow.addView(viewHolder.item4, newTableRowParams(headerCellsWidth[3]))
            tableRow.addView(viewHolder.item5, newTableRowParams(headerCellsWidth[4]))
            tableRow.addView(viewHolder.item6, newTableRowParams(headerCellsWidth[5]))

            tableLayoutDetail.addView(tableRow)
        }
    }

    fun addDataMotor(data: ArrayList<Motor>) {
        borrarFilas()
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = newRow()
            setOnRowClickListener(tableRow)

            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item2 = newCell()
            viewHolder.item3 = newCell()

            rowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.marca)
            viewHolder.item2?.setText(item.motor)
            viewHolder.item3?.setText(item.cili1)

            tableRow.addView(viewHolder.item1, newTableRowParams(headerCellsWidth[0]))
            tableRow.addView(viewHolder.item2, newTableRowParams(headerCellsWidth[1]))
            tableRow.addView(viewHolder.item3, newTableRowParams(headerCellsWidth[2]))
            tableLayoutDetail.addView(tableRow)
        }
    }

    private var callBack: OnClickCallBackRow? = null
    fun setOnRowClickListener(callBack: OnClickCallBackRow) {
        this.callBack = callBack
    }

    private val implementOnRowClickListener = object : View.OnClickListener {
        override fun onClick(view: View?) {
            callBack?.onClickRow(view)
        }
    }

    fun AddDataArticuloXMotorCodProd(data: List<Articulo>) {
        borrarFilas()
        var viewHolder: ViewHolder
        var tableRow: TableRow
        data.forEach { item ->
            tableRow = newRow()
            viewHolder = ViewHolder()
            viewHolder.item1 = newCell()
            viewHolder.item2 = newCell()
            viewHolder.item3 = newCell()
            viewHolder.item4 = newCell()
            viewHolder.item5 = newCell()
            viewHolder.item6 = newCell()
            viewHolder.item7 = newCell()

            rowCellStyle(viewHolder)
            viewHolder.item1?.setText(item.cpdnew)
            viewHolder.item2?.setText(item.alternante)
            viewHolder.item3?.setText(item.codbar)
            viewHolder.item4?.setText(item.unimed)
            viewHolder.item5?.setText(item.campar.toString())
            viewHolder.item6?.setText(item.totSaldo.toString())
            viewHolder.item7?.setText(item.precioVenta.toString())

            tableRow.addView(viewHolder.item1, newTableRowParams())
            tableRow.addView(viewHolder.item2, newTableRowParams())
            tableRow.addView(viewHolder.item3, newTableRowParams())
            tableRow.addView(viewHolder.item4, newTableRowParams())
            tableRow.addView(viewHolder.item5, newTableRowParams())
            tableRow.addView(viewHolder.item6, newTableRowParams())
            tableRow.addView(viewHolder.item7, newTableRowParams())
            tableLayoutDetail.addView(tableRow)
        }
    }

    fun borrarFilas() {
        tableLayoutDetail.removeAllViews()
    }
}