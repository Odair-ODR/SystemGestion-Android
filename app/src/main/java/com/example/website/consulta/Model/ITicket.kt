package com.example.website.consulta.Model

interface ITicket {
    fun getEmpresa(): EmpresaTicket
    fun getDescTipoDocumento(): String
    fun getNroTipoDocumento(): String
    fun getFechaDocumento(): String
    fun getVendedor(): VendedorTicket
    fun getCliente(): ClienteTicket
    fun getItems(): List<ItemsTicket>
    fun getTotales(): TotalesTicket
}

class EmpresaTicket {
    var nombre = ""
    var descripcion = ""
    var direccion = ""
    var telefono = ""
    var correo = ""
    var ruc = ""
}

class ClienteTicket {
    var nombre = ""
    var direccion = ""
    var nroDocumento = ""
}

class VendedorTicket {
    var nombre = ""
    var codigo = ""
}

class ItemsTicket {
    var sec = ""
    var codigoItem = ""
    var descItem = ""
    var can = ""
    var preUni = ""
    var total = ""
}

class TotalesTicket {
    var simboloMoneda = "S/"
    var subTotal = "0.00"
    var igv = "0.00"
    var total = "0.00"
}
