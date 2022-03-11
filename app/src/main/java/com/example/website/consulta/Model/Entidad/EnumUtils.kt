package com.example.website.consulta.Model.Entidad

enum class TIPO_DOCUMENTO(val id: Int, val descripcion: String) {
    FACTURA(24, "FACTURA"),
    BOLETA(28, "BOLETA"),
    NOTA_CREDITO(3, "NOTA DE CREDITO"),
    NOTA_VENTA(4, "NOTA DE VENTA")
}

enum class TIPO_DOCUMENTO_IDENTIDAD (val id: Int, val length: Int, val descripcion: String) {
    NONE(0, 0,"NONE"),
    RUC(1, 11,"RUC"),
    DNI(2, 8,"DNI")
}