package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.CuentasBancariasTo
import com.example.website.consulta.Model.Entidad.EmpresaTo
import com.example.website.consulta.Model.Entidad.FacturaCabTo
import com.example.website.consulta.Model.Entidad.TipoCambioTo

class MultipleObservable {
    var multipleRepository = MultipleRepository()

    fun listaCuentasBancarias(): List<CuentasBancariasTo> {
        return multipleRepository.listaCuentasBancarias()
    }

    fun obtenerTiposDeCambio(): List<TipoCambioTo>{
        return multipleRepository.obtenerTiposDeCambio()
    }

    fun obtenerLogoEmpresa(): ByteArray? {
        return multipleRepository.obtenerLogoEmpresa()
    }

    fun obtenerEmpresaSistema(): EmpresaTo? {
        return multipleRepository.obtenerEmpresaSistema()
    }
}