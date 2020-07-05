package co.unicauca.clubPark.servidor.negocio;

import co.unicauca.clubPark.servidor.acceso.ConexionMysql;
import java.sql.SQLException;

/**
 *
 * @author javier
 */
public class GestorPersona {
    ConexionMysql cn = new ConexionMysql();
    
    /**
     * Metodo para consultar la existencia de una persona con el usuario y contraseña que ingresa el cliente en el login 
     * @param Usuario
     * @param Contraseña
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Persona consultarPersona(String Usuario,String Contraseña)throws ClassNotFoundException,SQLException{
        Persona per = null;
        cn.conectar();
        String sql = "SELECT * FROM persona WHERE perUsuario='" + Usuario + "' AND perContraseña='" + Contraseña + "'";
        System.out.println(sql);
        cn.crearConsulta(sql);
        if (cn.getResultado().next()) {
            per = new Persona();
            per.setPerID(cn.getResultado().getString("perID"));
            per.setPerNombres(cn.getResultado().getString("perNombres"));
            per.setPerApellidos(cn.getResultado().getString("perApellidos"));
            per.setPerRol(cn.getResultado().getString("perRol"));
            per.setPerTelefono(cn.getResultado().getString("perTelefono"));
            per.setPerUsuario(cn.getResultado().getString("perUsuario"));
            per.setPerContraseña(cn.getResultado().getString("perContraseña"));
        }
        cn.desconectar();
        return per;
    }
}
