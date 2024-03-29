package com.example.website.consulta.Model

import com.example.website.consulta.Model.Entidad.Articulo
import com.example.website.consulta.Model.Entidad.Motor as MotorTo

class MotorFragmentRepositoryImp: IMotorFragmentRepository {

    override fun ObtenerMotores(codProd: String, motor: String): ArrayList<MotorTo> {
        val lstMotor: ArrayList<MotorTo> = ArrayList()
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "{call usp_AndroidSelectObtenerMotoresporMantMotores(?,?)}"
            val pc = connection.prepareCall(procedure)
            pc.setString(1, codProd)
            pc.setString(2, motor)
            val rs = pc.executeQuery()
            var motor: MotorTo
            while (rs.next()){
                motor = MotorTo()
                motor.marca = rs.getString("marcavehi")
                motor.motor = rs.getString("motor")
                motor.cili1 = rs.getString("cili1")
                lstMotor.add(motor)
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
        }
        finally {
            connection.close()
        }
        return lstMotor
    }

    override fun ObtenerArticulosXMotorCodProd(codProd: String, motor: String): ArrayList<Articulo> {
        val lstArticulo: ArrayList<Articulo> = ArrayList()
        val connection = ConnectionDB.Conexion()
        try {
            val procedure = "{call usp_AndroidObtenerArticulosporProdMotor (?,?)}"
            val pc = connection.prepareCall(procedure)
            pc.setString(1, codProd)
            pc.setString(2, motor)
            val rs = pc.executeQuery()
            var articulo: Articulo
            while (rs.next()){
                articulo = Articulo()
                articulo.idArticulo = rs.getString("idArticulo").toInt()
                articulo.codbar = rs.getString("codBar")
                articulo.campar = rs.getString("campar").toInt()
                articulo.unimed = rs.getString("unimed")
                articulo.alternante = rs.getString("alternante")
                articulo.cpdnew = rs.getString("cpdnew")
                articulo.cpdold = rs.getString("cpdold")
                articulo.descriArti = rs.getString("descriArti")
                articulo.totSaldo = rs.getInt("totSaldo")
                articulo.precioVenta = rs.getDouble("precioVenta")
                lstArticulo.add(articulo)
            }
        }
        catch (ex: Exception){
            ex.printStackTrace()
        } finally {
            connection.close()
        }
        return lstArticulo
    }
}