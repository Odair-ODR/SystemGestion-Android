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
import kotlin.collections.ArrayList

class AlternanteFragment : Fragment() {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private var txtAlternante: EditText? = null
    private var listAlternante: ListView? = null
    private var btnConsultar: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_alternante, container, false)
        txtAlternante = view.findViewById<EditText>(R.id.txtAlternante)
        listAlternante = view.findViewById(R.id.lstAlternante)
        btnConsultar = view.findViewById(R.id.btnConsultarFra)
        btnConsultar!!.setOnClickListener(View.OnClickListener {
            try {
                ConsultarAlternante()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        return view
    }

    @Throws(Exception::class)
    private fun ConsultarAlternante() {
        val procedure = "{call AdroidSelectObtenerArticulosporAlternante (?)}"
        val callStatment: CallableStatement = ConnectionDB.Conexion().prepareCall(procedure)
        callStatment.setString(1, txtAlternante?.getText().toString())
        val rs = callStatment.executeQuery()
        val lstArtculos: ArrayList<Articulo?>? = ArrayList()
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

    private fun CargarDataListView(articulos: ArrayList<Articulo?>?) {
        val adpater: MasterAdapter<Articulo> = MasterAdapter(context, articulos, 1)
        listAlternante!!.adapter = adpater
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): AlternanteFragment {
            val fragment = AlternanteFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}