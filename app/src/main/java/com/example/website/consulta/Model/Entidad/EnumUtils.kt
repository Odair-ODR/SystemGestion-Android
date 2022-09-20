package com.example.website.consulta.Model.Entidad

enum class TIPO_DOCUMENTO(val id: Int, val descripcion: String) {
    FACTURA(24, "Factura"),
    BOLETA(28, "Boleta"),
    NOTA_CREDITO(3, "Nota de Crédito"),
    NOTA_VENTA(4, "Nota de Venta"),
    PRE_FACTURA(55, "Pre-Factura");

    companion object {
        fun fromInt(id: Int) = values().first { it.id == id }
    }
}

enum class TIPO_DOCUMENTO_IDENTIDAD(val id: Int, val length: Int, val descripcion: String) {
    NONE(0, 0, "NONE"),
    RUC(1, 11, "RUC"),
    DNI(2, 8, "DNI");

    companion object {
        fun fromInt(id: Int) = values().first { it.id == id }
    }
}

enum class FORMA_DE_PAGO(val id: Int, val descripcion: String) {
    CONTADO(1, "CONTADO"),
    CREDITO(2, "CREDITO");

    companion object {
        fun fromInt(id: Int) = values().first { it.id == id }
    }
}

enum class TIPO_PROCESO_VENTA(val id: Int, val descripcion: String) {
    CANCELACION(0, "Cancelación");

    companion object {
        fun fromInt(id: Int) = values().first { it.id == id }
    }
}

enum class DialogResult(id: Int, descripcion: String) {
    NO(0, "NO"),
    SI(1, "SI")
}