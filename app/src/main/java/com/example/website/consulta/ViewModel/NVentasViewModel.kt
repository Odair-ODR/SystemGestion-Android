package com.example.website.consulta.ViewModel

import android.content.Context
import com.example.website.consulta.Model.NVentasObservable
import com.example.website.consulta.dummy.Tienda

class NVentasViewModel(val context: Context) {

    private val nVentasObservable: NVentasObservable = NVentasObservable()

    fun ObtenerTiendas(): ArrayList<String> {
        return nVentasObservable.ObtnerTiendas()
    }
}