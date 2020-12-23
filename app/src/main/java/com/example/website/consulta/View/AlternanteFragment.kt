package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TableLayout
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import java.sql.CallableStatement
import kotlin.collections.ArrayList

class AlternanteFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtAlternante: EditText? = null
    private var tableLayout: TableLayout? = null
    private var btnConsultar: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString("")
            mParam2 = arguments!!.getString("")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_alternante, container, false)
        InitializeComponents(view)
        InitializeEvents()
        return view
    }

    private fun InitializeComponents(view: View){
        txtAlternante = view.findViewById<EditText>(R.id.txtAlternante)
        tableLayout = view.findViewById(R.id.tableLayoutArticulos)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
    }

    private fun InitializeEvents(){
        btnConsultar!!.setOnClickListener(View.OnClickListener {
            try {
                ConsultarAlternante()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    @Throws(Exception::class)
    private fun ConsultarAlternante() {
        val procedure = "{call AdroidSelectObtenerArticulosporAlternante (?)}"
        val callStatment: CallableStatement = ConnectionDB.Conexion().prepareCall(procedure)
        callStatment.setString(1, txtAlternante?.getText().toString())
        val rs = callStatment.executeQuery()
        val lstArtculos: ArrayList<Articulo?> = ArrayList()
        var art: Articulo
        while (rs.next()) {
            art = Articulo()
            art.alternante = rs.getString("alternante")
            art.campar = rs.getString("campar").toInt()
            art.codbar = rs.getString("codbar")
            art.cpdnew = rs.getString("cpdnew")
            art.unimed = rs.getString("unimed")
            art.motor = rs.getString("Motor")
            art.totSaldo = rs.getString("totsaldo").toInt()
            lstArtculos?.add(art)
        }
        CargarDataListView(lstArtculos)
    }

    private fun CargarDataListView(articulos: ArrayList<Articulo?>) {
        val adpater: MasterAdapter<Articulo> = MasterAdapter(context!!, articulos, 1)
        //> listAlternante!!.adapter = adpater
    }
}