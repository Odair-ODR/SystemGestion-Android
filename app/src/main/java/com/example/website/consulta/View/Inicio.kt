package com.example.website.consulta.View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.example.website.consulta.R

class Inicio : AppCompatActivity() {
    var btnconsu: Button? = null
    var btnmoto: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_inicio)
        btnconsu = findViewById(R.id.btnconsu)
        btnmoto = findViewById(R.id.btnmoto)
        btnconsu?.setOnClickListener(View.OnClickListener { startActivity(Intent(this@Inicio, Principal::class.java)) })
    }
}