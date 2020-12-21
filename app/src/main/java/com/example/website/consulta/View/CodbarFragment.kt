package com.example.website.consulta.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.website.consulta.Model.ConnectionDB
import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.R
import java.sql.CallableStatement
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class CodbarFragment : Fragment() {
    private var txtCodba: EditText? = null
    private var txtMotor: EditText? = null
    private var listAlternante: ListView? = null
    private var btnConsultar: Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_codbar, container, false)
        txtCodba = view.findViewById<EditText>(R.id.txtCodBar)
        listAlternante = view.findViewById(R.id.lstAlternante)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        txtMotor = view.findViewById<EditText>(R.id.txtMotor)
        btnConsultar!!.setOnClickListener(View.OnClickListener {
            try {
                ConsultarXCodBar()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        return view
    }

    @Throws(Exception::class)
    private fun ConsultarXCodBar() {
        val procedure = "{call usp_AndroidSelectObtenerArticulosporCodBar (?)}"
        val callStatment: CallableStatement = ConnectionDB.Conexion().prepareCall(procedure)
        callStatment.setString(1, txtCodba?.getText().toString())
        val rs = callStatment.executeQuery()
        val lstArtculos: ArrayList<Articulo?> = ArrayList()
        var art: Articulo? = null
        while (rs.next()) {
            art = Articulo()
            art.alternante = rs.getString("alternante")
            art.campar = rs.getString("campar").toInt()
            art.cpdnew = rs.getString("cpdnew")
            art.unimed = rs.getString("unimed")
            art.motor = rs.getString("Motor")
            art.totSaldo = rs.getString("totsaldo").toInt()
            lstArtculos.add(art)
        }
        if (art != null) {
            txtCodba?.setText(txtCodba?.getText())
            txtMotor?.setText(art.motor)
        }
        CargarDataListView(lstArtculos)
    }

    private fun CargarDataListView(articulos: ArrayList<Articulo?>?) {
        val adpater: MasterAdapter<*> = MasterAdapter(context, articulos, 2)
        listAlternante!!.adapter = adpater
    }
}