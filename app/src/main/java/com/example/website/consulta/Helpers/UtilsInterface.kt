package com.example.website.consulta.Helpers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.ArticuloViewModel

class UtilsInterface() {
    interface OnClickCallBack {
        fun onClick()
    }

    companion object {
        fun progressDialog(context: Context): Dialog {
            val inflate = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null)
            val progressDialog = Dialog(context)
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(inflate)
            progressDialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return progressDialog
        }

        fun alertDialog1(
            context: Context,
            view: View,
            windowMain: Window,
            title: String
        ): AlertDialog {
            view.findViewById<TextView>(R.id._titulo)?.setText(title)
            val alertBuilder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
            //> alertBuilder.setTitle("Vista de Motores")
            alertBuilder.setNegativeButton("Cancelar", AlertDialog_OnClickListener)
            alertBuilder.setView(view)
            val alertDialog = alertBuilder.create()
            alertDialog.show()
            val window = alertDialog.getWindow()
            window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
            val lp: WindowManager.LayoutParams = window.getAttributes()
            val displayRectangle = Rect()
            val windowScreen = windowMain
            windowScreen.getDecorView().getWindowVisibleDisplayFrame(displayRectangle)
            lp.width = (displayRectangle.width() * 0.7f).toInt()
            window.attributes = lp
            return alertDialog
        }

        private var AlertDialog_OnClickListener = object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                dialog.dismiss()
            }
        }

        fun alertDialog2(
            context: Context,
            view: View,
            windowMain: Window,
            title: String,
            clickListener: DialogInterface.OnClickListener
        ): AlertDialog {
            view.findViewById<TextView>(R.id._titulo)?.setText(title)
            val alertBuilder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
            //> alertBuilder.setTitle("Vista de Motores")
            alertBuilder.setNeutralButton("Aceptar", clickListener)
            alertBuilder.setNegativeButton("Cancelar", AlertDialog_OnClickListener)
            alertBuilder.setView(view)
            val alertDialog = alertBuilder.create()
            alertDialog.show()
            val window = alertDialog.getWindow()
            window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
            val lp: WindowManager.LayoutParams = window.getAttributes()
            val displayRectangle = Rect()
            val windowScreen = windowMain
            windowScreen.getDecorView().getWindowVisibleDisplayFrame(displayRectangle)
            lp.width = (displayRectangle.width() * 0.7f).toInt()
            window.attributes = lp
            return alertDialog
        }

        private lateinit var callBack: OnClickCallBack
        fun alertDialog3(
            context: Context,
            view: View,
            windowMain: Window,
            title: String,
            callBack: OnClickCallBack
        ): AlertDialog {
            this.callBack = callBack
            view.findViewById<TextView>(R.id._titulo)?.setText(title)
            val alertBuilder = AlertDialog.Builder(context, R.style.AlertDialogCustom)
            //> alertBuilder.setTitle("Vista de Motores")
            alertBuilder.setNeutralButton("Aceptar", click)
            alertBuilder.setNegativeButton("Cancelar", AlertDialog_OnClickListener)
            alertBuilder.setView(view)
            val alertDialog = alertBuilder.create()
            alertDialog.show()
            val window = alertDialog.getWindow()
            window!!.setBackgroundDrawableResource(R.drawable.dialog_bg)
            val lp: WindowManager.LayoutParams = window.getAttributes()
            val displayRectangle = Rect()
            val windowScreen = windowMain
            windowScreen.getDecorView().getWindowVisibleDisplayFrame(displayRectangle)
            lp.width = (displayRectangle.width() * 0.7f).toInt()
            window.attributes = lp
            return alertDialog
        }

        private val click = object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                callBack.onClick()
            }
        }
    }
}