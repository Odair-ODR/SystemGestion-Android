package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor

class MotorFragmentRepositoryImp: IMotorFragmentRepository {

    override fun ObtenerMotores(codProd: String, motor: String): ArrayList<Motor> {
        val lstMotor: ArrayList<Motor> = ArrayList()
        try {
            val procedure = "{call usp_AndroidSelectObtenerMotoresporMantMotores(?,?)}"
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            pc.setString(1, codProd)
            pc.setString(2, motor)
            val rs = pc.executeQuery()
            var motor: Motor
            while (rs.next()){
                motor = Motor()
                motor.marca = rs.getString("marcavehi")
                motor.motor = rs.getString("motor")
                motor.cili1 = rs.getString("cili1")
                lstMotor.add(motor)
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
        return lstMotor
    }

    override fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo> {
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        try {
            val procedure = "{call usp_AndroidObtenerArticulosporProdMotor (?,?)}"
            val connection = ConnectionDB.Conexion()
            val pc = connection.prepareCall(procedure)
            pc.setString(1, codProd)
            pc.setString(2, motor)
            val rs = pc.executeQuery()
            var articulo: Articulo
            while (rs.next()){
                articulo = Articulo()
                articulo.codbar = rs.getString("codBar")
                articulo.campar = rs.getString("campar").toInt()
                articulo.unimed = rs.getString("unimed")
                articulo.alternante = rs.getString("alternante")
                articulo.cpdnew = rs.getString("cpdnew")
                articulo.totSaldo = rs.getInt("totSaldo")
                lstArticulo.add(articulo)
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
        return lstArticulo
    }
}