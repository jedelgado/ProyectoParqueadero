package co.unicauca.clubPark.servidor.negocio;

import co.unicauca.clubPark.servidor.acceso.ConexionMysql;
import java.sql.SQLException;

/**
 *
 * @author javier
 */

//Metodo para consultar los datos de un parqueadero dependiendo del usuario que se este ingresando desde el login
public class GestorParqueadero {
    ConexionMysql cn = new ConexionMysql();
    public Parqueadero consultarParqueadero(String Usuario)throws ClassNotFoundException,SQLException{
        Parqueadero par = null;
        cn.conectar();
        String sql="SELECT * FROM parqueadero WHERE usuarioPar='"+Usuario+"'";
        System.out.println(sql);
        cn.crearConsulta(sql);
        if (cn.getResultado().next()) {
            par = new Parqueadero();
            par.setNitParqueadero(cn.getResultado().getString("nitParqueadero"));
            par.setNomParqueadero(cn.getResultado().getString("nomParqueadero"));
            par.setDirecParqueadero(cn.getResultado().getString("direcParqueadero"));
            par.setTelParqueadero(cn.getResultado().getString("telParqueadero"));
            par.setUsuarioPar(cn.getResultado().getString("usuarioPar"));
        }
        cn.desconectar();
        return par;
    }
}
