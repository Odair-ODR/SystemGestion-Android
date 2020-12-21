package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.example.website.consulta.R
import com.example.website.consulta.View.Emision

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
            startActivity(n)
        })
        consulta?.setOnClickListener(View.OnClickListener {
            val c = Intent(this@Emision, Consultarve::class.java)
            startActivity(c)
        })
        anular?.setOnClickListener(View.OnClickListener {
            val a = Intent(this@Emision, Anularve::class.java)
            startActivity(a)
        })
        modificar?.setOnClickListener(View.OnClickListener {
            val m = Intent(this@Emision, Modificarve::class.java)
            startActivity(m)
        })
    }
}