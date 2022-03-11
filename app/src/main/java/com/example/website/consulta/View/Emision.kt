package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.website.consulta.Model.Entidad.TIPO_DOCUMENTO
import com.example.website.consulta.R

class Emision : AppCompatActivity() {
    var nuevo: Button? = null
    var consulta: Button? = null
    var anular: Button? = null
    var modificar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emision)
        nuevo = findViewById(R.id.Nueva_v)
        consulta = findViewById(R.id.Consultar_v)
        anular = findViewById(R.id.Anular_V)
        modificar = findViewById(R.id.Modificar_v)
        nuevo?.setOnClickListener(View.OnClickListener {
            val n = Intent(this@Emision, Nventas::class.java)
            n.putExtra("TipoDocumento", TIPO_DOCUMENTO.FACTURA)
            startActivity(n)
        })
        consulta?.setOnClickListener(View.OnClickListener {
            val c = Intent(this@Emision, Nventas::class.java)
            c.putExtra("TipoDocumento", TIPO_DOCUMENTO.BOLETA)
            startActivity(c)
        })
        anular?.setOnClickListener(View.OnClickListener {
            val a = Intent(this@Emision, Nventas::class.java)
            a.putExtra("TipoDocumento", TIPO_DOCUMENTO.NOTA_CREDITO)
            startActivity(a)
        })
        modificar?.setOnClickListener(View.OnClickListener {
            val m = Intent(this@Emision, Nventas::class.java)
            m.putExtra("TipoDocumento", TIPO_DOCUMENTO.NOTA_VENTA)
            startActivity(m)
        })
    }
}