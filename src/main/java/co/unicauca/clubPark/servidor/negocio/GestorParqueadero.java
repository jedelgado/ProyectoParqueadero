package co.unicauca.clubPark.servidor.negocio;

import co.unicauca.clubPark.servidor.acceso.ConexionMysql;
import java.sql.SQLException;

/**
 *
 * @author javier
 */

public class GestorParqueadero {
    
    ConexionMysql cn = new ConexionMysql();
    
    /**
     * Metodo encargado de agregar un nuevo parqueadero a la base de datos.
     * @param nit
     * @param nombre
     * @param direccion
     * @param telefono
     * @param usuario
     * @param libres
     * @param ocupados
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void agregarParqueadero(String nit, String nombre, String direccion, String telefono, String usuario, int libres, int ocupados)throws ClassNotFoundException, SQLException{
        
        cn.conectar();
        String sql ="INSERT INTO parqueadero(nitParqueadero, nomParqueadero, direcParqueadero, telParqueadero, usuarioPar, puestosLibres, puestosOcupados) values ("
                      + "'" + nit + "',"
                      + "'" + nombre + "'," 
                      + "'" + direccion + "',"
                      + "'" + telefono + "',"
                      + "'" + usuario + "',"
                      + "" + libres + ","
                      + "" + ocupados + ""
                      + ")";
        System.out.println(sql);
        cn.actualizar(sql);
       cn.desconectar();
    }
    
    /**
     * Metodo encargado de cosultar la existencia de un parqueadero en la BD, filtrando por un usuario exitente
     * @param Usuario
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
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
            par.setLibres(cn.getResultado().getString("puestosLibres"));
            par.setOcupados(cn.getResultado().getString("puestosOcupados"));
        }
        cn.desconectar();
        return par;
    }
    
    /**
     * Metodo para actualizar los puestos libres y ocupados del parqueadero, dependiendo del usuario que se ingresó en el login
     * @param nit
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void actualizarIngreso(String nit) throws ClassNotFoundException,SQLException{
       
        cn.conectar();
        String sql = "UPDATE parqueadero SET puestosLibres= puestosLibres -1,puestosOcupados= puestosOcupados +1 WHERE nitParqueadero='"+nit+"'";
        System.out.println(sql);
        cn.actualizar(sql);
        cn.desconectar();  
    }
    
    
    /**
     * Metodo para actualizar los puestos libres y ocupados del parqueadero, dependiendo del usuario que se ingresó en el login
     * @param nit
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void actualizarSalida(String nit) throws ClassNotFoundException,SQLException{
       
        cn.conectar();
        String sql="UPDATE parqueadero SET puestosLibres= puestosLibres +1,puestosOcupados= puestosOcupados -1 WHERE nitParqueadero='"+nit+"'";
        System.out.println(sql);
        cn.actualizar(sql);
        cn.desconectar();
    }
    
    public void consultarPuestos(String nomParqueadero) throws ClassCastException, SQLException{
        
        cn.conectar();
        String sql = "SELECT puestosLibres, puestosOcupados FROM parqueadero WHERE nomParqueadero= '"+ nomParqueadero + "'";
        System.out.println(sql);
        cn.actualizar(sql);
        cn.desconectar();
    }
    
    
    
}
