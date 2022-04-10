package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import com.example.website.consulta.ViewModel.NVentasViewModel
import kotlinx.android.synthetic.main.activity_factura.*

class ConsultaActivity : AppCompatActivity() {
    private var spFiltro: Spinner? = null
    private lateinit var frManager: FragmentTransaction
    private var frMotor: Fragment? = null
    private var frAlternante: Fragment? = null
    private var frCodBar: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)
        initializeComponents()
        initializeEvents()
    }

    private fun initializeComponents() {
        frMotor = MotorFragment()
        frAlternante = AlternanteFragment()
        frCodBar = CodbarFragment()
        spFiltro = findViewById(R.id.spFiltro)
    }

    private fun initializeEvents() {
        spFiltro?.setAdapter(ListaFiltros())
        spFiltro?.setOnItemSelectedListener(spFiltroOnItemSelectedListener)
    }

    private fun ListaFiltros(): ArrayAdapter<CharSequence> {
        //> String[] opciones = {"Alternante", "codigo.bar", "Nombre", "Motores"};

        //> ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_editor, opciones);
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.consulta_opcion,
            R.layout.support_simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        return adapter
    }

    private val spFiltroOnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, i: Int, p3: Long) {
            frManager = supportFragmentManager.beginTransaction()
            frManager.disallowAddToBackStack()
            when (i) {
                0 -> {
                    frManager.replace(R.id.frContent, frAlternante as AlternanteFragment)
                    //> frManager!!.addToBackStack(null)
                }
                1 -> {
                    frManager.replace(R.id.frContent, frCodBar as CodbarFragment)
                    //> frManager!!.addToBackStack(null)
                }
                else -> {
                    frManager.replace(R.id.frContent, frMotor as MotorFragment)
                    //> frManager!!.addToBackStack(null)
                }
            }
            frManager.commit()
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {

        }
    }
}