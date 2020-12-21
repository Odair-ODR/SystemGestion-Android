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
import com.example.website.consulta.R

class ConsultaActivity : AppCompatActivity() {
    private var spFiltro: Spinner? = null
    private var frManager: FragmentTransaction? = null
    private var frMotor: Fragment? = null
    private var frAlternante: Fragment? = null
    private var frCodBar: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consulta)
        frMotor = MotorFragment()
        frAlternante = AlternanteFragment()
        frCodBar = CodbarFragment()
        spFiltro = findViewById(R.id.spFiltro)
        spFiltro?.setAdapter(ListaFiltros())
        spFiltro?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                frManager = supportFragmentManager.beginTransaction()
                if (i == 0) {
                    //>  frManager.addToBackStack("");
                    frManager!!.replace(R.id.frContent, frAlternante as AlternanteFragment)
                    frManager!!.addToBackStack(null)
                } else if (i == 1) {
                    frManager!!.replace(R.id.frContent, frCodBar as CodbarFragment)
                    frManager!!.addToBackStack(null)
                } else {
                    frManager!!.replace(R.id.frContent, frMotor as MotorFragment)
                    frManager!!.addToBackStack(null)
                }
                frManager!!.commit()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        //> getSupportFragmentManager().beginTransaction().add(R.id.frContent, frMotor);
    }

    private fun ListaFiltros(): ArrayAdapter<CharSequence> {
        //> String[] opciones = {"Alternante", "codigo.bar", "Nombre", "Motores"};

        //> ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_editor, opciones);
        val adapter = ArrayAdapter.createFromResource(this, R.array.consulta_opcion, R.layout.support_simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        return adapter
    }
}