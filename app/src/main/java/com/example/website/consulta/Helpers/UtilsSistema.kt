package com.example.website.consulta.Helpers

import com.example.website.consulta.Model.Entidad.UsuarioTo

class UtilsSistema {
    companion object {
        private lateinit var usuarioTo: UsuarioTo
        var setUsuario: UsuarioTo? = null
            set(value) {
                if (value != null) {
                    usuarioTo = value
                    field = value
                } else throw NullPointerException("Usuario logueado null")
            }
        val usuario: UsuarioTo
            get() = usuarioTo
    }
}