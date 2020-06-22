package co.unicauca.clubPark.servidor.negocio;

import co.unicauca.clubPark.servidor.acceso.ConexionMysql;
import java.sql.SQLException;
/**
 *
 * @author javier
 */

public class GestorVehiculo {
   
    ConexionMysql cn = new ConexionMysql();
    
    //Metodo para insertar un nuevo vehiculo en la base de datos
    public void registarVehiculo(String placa,String TipoVehiculo)throws ClassNotFoundException,SQLException{
        cn.conectar();
        cn.actualizar("INSERT INTO vehiculo (placaVehiculo,tipoVehiculo) VALUES("
                + "'" + placa + "',"
                + "'" + TipoVehiculo + "'"
                + ")");
        cn.desconectar();
    }
    
    //Metodo para consultar si existe un vehiculo en la base de datos
    public Vehiculo consultarVehiculo(String placa)throws ClassNotFoundException,SQLException{
        Vehiculo vehi = null;
        cn.conectar();
        
        cn.crearConsulta("SELECT * FROM vehiculo WHERE placaVehiculo ='"+ placa +"'");
        if (cn.getResultado().next()) {
            vehi = new Vehiculo();
            vehi.setPlacaVehiculo(cn.getResultado().getString("placaVehiculo"));
            vehi.setTipoVehiculo(cn.getResultado().getString("tipoVehiculo"));
        }
        cn.desconectar();
        return vehi;
    }
}
