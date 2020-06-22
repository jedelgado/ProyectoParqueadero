package co.unicauca.clubPark.servidor.servidor;
/**
 *
 * @author javier
 */

//Aplicacion principal para lanzar el servidor
public class Main {
    
    public static void main(String args[]){
        parqueaderosPopServer parSer = new parqueaderosPopServer(0);
        parSer.iniciar();
    }
    
}
