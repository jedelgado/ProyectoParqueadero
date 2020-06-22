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
    
    private final String usuario="root";
    private final String contraseña="";
    private final String url="jdbc:mysql://localhost:3306/parqueaderopopayan";
    private ResultSet rs;
    private Statement st = null;
    
    public ConexionMysql(){
        
    }
    /**
     *Accede a la conexion con Mysql o la base de datos
     * 
    *///desde aquí
//    public void conectar() throws ClassNotFoundException,SQLException{
//        
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            //Class.forName("com.mysql.jdbc.Driver");
//            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/parqueaderopopayan","root","");
//            st = conexion.createStatement();
//            JOptionPane.showMessageDialog(null, "Conectado a la BD");
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, "No se pudo conectar");
//        }    
//    }
    
    public Connection conectar(){
        
        try{
            
          Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
          conexion = DriverManager.getConnection("jdbc:mysql://localhost/parqueaderopopayan","root","");  
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
        return conexion;
    }
    
    

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
