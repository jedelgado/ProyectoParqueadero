package co.unicauca.clubPark.servidor.acceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author javier
 */
public class ConexionMysql {
    
    private Connection conexion = null;
    private ResultSet rs;
    private Statement st = null;
    
    public ConexionMysql(){    
    }
    
    /**
     * 
     * Accede a la conexión con MySql o la base de datos
     */
    public Connection conectar(){
        
        try{
            
          Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
          conexion = DriverManager.getConnection("jdbc:mysql://localhost/parqueaderopopayan","root","");  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return conexion;
    }

    /**
     * Metodo que crea una consulta sql
     * @param sql
     * @return 
     */
     public boolean crearConsulta(String sql) {
        try{
            st = conexion.createStatement();
            rs = st.executeQuery(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }  
    }
    
     /**
      * metodo paracatualizar una o varias columnas de una tabla en la base de datos
      * @param sql
      * @return 
      */
    public boolean actualizar(String sql){
        try{
            st = conexion.createStatement();
            st.executeUpdate(sql);
            return true;
        }catch(SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    /**
     * Cierra la conexion de la base de datos
     * 
     */
    public void desconectar() throws SQLException{
        if (rs != null) {
            rs.close();
        }
        conexion.close();
    }
    
    /**
     * Retorna el obejto de la conexión con la base de datos
     */
    
    public ResultSet getResultado(){
        return rs;
    }
    
    
}
