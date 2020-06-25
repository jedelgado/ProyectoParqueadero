package co.unicauca.clubPark.servidor.negocio;

import co.unicauca.clubPark.servidor.acceso.ConexionMysql;
import java.sql.SQLException;

/**
 *
 * @author javier
 */
public class GestorRegVehiculo {
    
    ConexionMysql cn = new ConexionMysql();
    
    //Metodo para insertar un nuevo registro de vehiculo al parqueadero en la base de datos
    public void ingresarVehiculo(String numFicha,String Placa, String TipoVehiculo,String HoraFechaEntrada,String EstadoVehiculo,String Llaves,String NumCascos,String NitParqueadero,String NumCasillero ,String Usuario, String HoraFechaSalida ) throws ClassNotFoundException, SQLException{
        GestorVehiculo gestorVehi = new GestorVehiculo();
        cn.conectar();
        String sql="INSERT INTO registrarvehiculo (regNumFicha,regPlacaVehiculo,regTipoVehiculo,regHoraYFechaEntrada,regEstadoVehiculo,regLlaves,regNumCascos,regNitParqueadero,regNumCasillero,regUsuario,regHoraYFechaSalida) VALUES("
                      + "'" + numFicha + "',"
                      + "'" + Placa + "',"
                      + "'" + TipoVehiculo + "',"
                      + "'" + HoraFechaEntrada + "',"
                      + "'" + EstadoVehiculo + "',"
                      + "'" + Llaves + "',"
                      + "'" + NumCascos + "',"
                      + "'" + NitParqueadero + "',"
                      + "'" + NumCasillero + "',"
                      + "'" + Usuario  + "',"
                      + "" + HoraFechaSalida + "" 
                      +  ")";
        System.out.println(sql);
        
        if (cn.actualizar(sql)==true) {
            if (gestorVehi.consultarVehiculo(Placa)== null) {
                gestorVehi.registarVehiculo(Placa, TipoVehiculo);
            }
        }
        cn.desconectar();
    }
    
    //Metodo para consultar los datos y sobretodo el estado de un vehiculo por el numero de ficha que se este ingresando desde el cliente
    public RegVehiculo consultarRegVehiculoFicha(String numFicha) throws ClassNotFoundException,SQLException {
        RegVehiculo reg = new RegVehiculo();
        cn.conectar();
        cn.crearConsulta("SELECT * FROM registrarvehiculo WHERE regNumFicha ='"+ numFicha +"' AND regEstadoVehiculo='Ingresado'");
        if (cn.getResultado().next()) {
            reg = new RegVehiculo();
            reg.setRegNumFicha(cn.getResultado().getString("regNumFicha"));
            reg.setRegPlacaVehiculo(cn.getResultado().getString("regPlacaVehiculo"));
            reg.setRegTipoVehiculo(cn.getResultado().getString("regTipoVehiculo"));
            reg.setRegHoraYFechaEntrada(cn.getResultado().getString("regHoraYFechaEntrada"));
            reg.setRegEstadoVehiculo(cn.getResultado().getString("regEstadoVehiculo"));
            reg.setRegLlaves(cn.getResultado().getString("regLlaves"));
            reg.setRegNumCascos(cn.getResultado().getString("regNumCascos"));
            reg.setRegNitParqueadero(cn.getResultado().getString("regNitParqueadero"));
            reg.setRegNumCasillero(cn.getResultado().getString("regNumCasillero"));
            reg.setRegUsuario(cn.getResultado().getString("regUsuario"));
            reg.setRegHoraYFechaSalida(cn.getResultado().getString("regHoraYFechaSalida"));
        
        }
        if(reg.getRegHoraFechaSalida()==null){
        reg.setRegHoraYFechaSalida("");
        }
        cn.desconectar();
        return reg;
    }
    
    
    //Metodo que se utiliza para actualizar los datos del ingreso( estado, fechaSalida)del vehiculo en la base de datos
    public void actualizarRegVehiculo (String Placa,String HoraFechaSalida)throws ClassNotFoundException,SQLException{
    cn.conectar();
    String sql="UPDATE registrarvehiculo SET regEstadoVehiculo='Retirado',regHoraYFechaSalida='"+HoraFechaSalida+"' WHERE regPlacaVehiculo= '"+Placa+"'";
    System.out.println(sql);
    cn.actualizar(sql);
    cn.desconectar();
    }
    
    //Metodo para consultar los datos de un vehiculo, buscando por la placa que se este ingresando desde el cliente
    public RegVehiculo consultarRegVehiculoPlaca(String Placa) throws ClassNotFoundException,SQLException {
        RegVehiculo reg = new RegVehiculo();
        cn.conectar();
        String sql ="SELECT * FROM registrarvehiculo WHERE regPlacaVehiculo ='"+ Placa +"' AND regEstadoVehiculo='Ingresado'";
        System.out.println(sql);
        cn.crearConsulta(sql);
        if (cn.getResultado().next()) {
            reg = new RegVehiculo();
            reg.setRegNumFicha(cn.getResultado().getString("regNumFicha"));
            reg.setRegPlacaVehiculo(cn.getResultado().getString("regPlacaVehiculo"));
            reg.setRegTipoVehiculo(cn.getResultado().getString("regTipoVehiculo"));
            reg.setRegHoraYFechaEntrada(cn.getResultado().getString("regHoraYFechaEntrada"));
            reg.setRegEstadoVehiculo(cn.getResultado().getString("regEstadoVehiculo"));
            reg.setRegLlaves(cn.getResultado().getString("regLlaves"));
            reg.setRegNumCascos(cn.getResultado().getString("regNumCascos"));
            reg.setRegNitParqueadero(cn.getResultado().getString("regNitParqueadero"));
            reg.setRegNumCasillero(cn.getResultado().getString("regNumCasillero"));
            reg.setRegUsuario(cn.getResultado().getString("regUsuario"));
            reg.setRegHoraYFechaSalida(cn.getResultado().getString("regHoraYFechaSalida"));
        }
        if(reg.getRegHoraFechaSalida()==null){
        reg.setRegHoraYFechaSalida("");
        }
        cn.desconectar();
        return reg;
    }
    
}
