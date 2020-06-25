package co.unicauca.clubPark.servidor.servidor;
import co.unicauca.clubPark.servidor.negocio.GestorParqueadero;
import co.unicauca.clubPark.servidor.negocio.GestorPersona;
import co.unicauca.clubPark.servidor.negocio.GestorRegVehiculo;
import co.unicauca.clubPark.servidor.negocio.GestorTarifa;
import co.unicauca.clubPark.servidor.negocio.GestorVehiculo;
import co.unicauca.clubPark.servidor.negocio.Parqueadero;
import co.unicauca.clubPark.servidor.negocio.Persona;
import co.unicauca.clubPark.servidor.negocio.RegVehiculo;
import co.unicauca.clubPark.servidor.negocio.Tarifa;
import co.unicauca.clubPark.servidor.negocio.Vehiculo;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.JsonObject;
import java.sql.SQLException;
import java.util.Properties;
/**
 *
 * @author javier
 */
public class parqueaderosPopServer implements Runnable{
        
    private final GestorRegVehiculo gestorReg;
    private final GestorVehiculo gestorVehi;
    private final GestorTarifa gestorTa;
    private final GestorParqueadero gestorPa;
    private final GestorPersona gestorPer;
    
    private static ServerSocket ssock;
    private static Socket socket;
    
    private Scanner entrada;
    private PrintStream salida;
    private static final int PUERTO = 5000;
    private static int cambio;
    
    //Constructor
    public parqueaderosPopServer(int c){
        gestorReg = new GestorRegVehiculo();
        gestorVehi = new GestorVehiculo();
        gestorTa = new GestorTarifa();
        gestorPa = new GestorParqueadero();
        gestorPer = new GestorPersona();
        cambio = c;
    }
    //Metodo 
    public void iniciar(){
        abrirPuerto();
        
        while(true){
            esperarAlCliente();
            lanzarHilo();
        }
    }
    
    //Lanzar el hilo del servidor
    private static void lanzarHilo(){
        new Thread(new parqueaderosPopServer(cambio)).start();
    }
    
    private static void abrirPuerto(){
        try {
            ssock = new ServerSocket(PUERTO);
            System.out.println("Escuchando por el puerto " + PUERTO);
        } catch (IOException ex) {
            Logger.getLogger(parqueaderosPopServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Metodo para esperar al cliente y conectarse
     */
    private static void esperarAlCliente(){
        try {
            socket = ssock.accept();
            System.out.println("Cliente conectado Parqueadero");
        } catch (IOException ex) {
            Logger.getLogger(parqueaderosPopServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Cuerpo para correr el hilo
     */
    @Override
    public void run(){
        try {
            crearFlujos();
            leerFlujos();
            cerrarFlujos();

        }catch(IOException e) {
            System.out.println(e);
        } catch(ClassNotFoundException ex){
            Logger.getLogger(parqueaderosPopServer.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(parqueaderosPopServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Crea los flujos del socket
    private void crearFlujos() throws IOException {
        salida = new PrintStream(socket.getOutputStream());
        entrada = new Scanner(socket.getInputStream());
    }
    
    //Lee los flujos del socket
    private void leerFlujos() throws ClassNotFoundException,SQLException{
        if (entrada.hasNextLine()) {
            // Extrae el flujo que envía el cliente
            String peticion = entrada.nextLine();
             System.out.println(peticion);
            decodificarPeticion(peticion);

        } else {
            salida.flush();
            salida.println("NO_ENCONTRADO");
        }
    }
    
    //Extrae la peticion y los parametros que le llegan
    private void decodificarPeticion(String peticion) throws ClassNotFoundException,SQLException{
        System.out.println(peticion);
        StringTokenizer tokens = new StringTokenizer(peticion, ",");
        String parametros[] = new String[12];

        int i = 0;
        while (tokens.hasMoreTokens()) {
            parametros[i++] = tokens.nextToken();
        }
        String accion = parametros[0];
        procesarAccion(accion, parametros);
    }
    
    //Ejecuta la accion dependiendo de lo que pida el cliente
    private void procesarAccion(String accion, String parametros[]) throws ClassNotFoundException,SQLException {
        String resultado;
        switch (accion) {
            case "ingresarRegVehiculo":
                try {
                    gestorReg.ingresarVehiculo(parametros[1], parametros[2], parametros[3], parametros[4], parametros[5], parametros[6], parametros[7], parametros[8], parametros[9], parametros[10], parametros[11]);
                    resultado = " BIEN";
                } catch (Exception e) {
                    System.out.println(e);
                    resultado = "FALLO";
                }
                salida.println(resultado);
                break;
            case "ingresarParqueadero":
                try {
                    gestorPa.agregarParqueadero(parametros[1], parametros[2], parametros[3], parametros[4], parametros[5], parametros[6], parametros[7]);
                    resultado = "BIEN";
                } catch (Exception e) {
                    System.out.println(e);
                    resultado = "FALLO";
                }
                
            case "actualizarIngreso":
                try {
                    gestorPa.actualizarIngreso(parametros[1]);
                    resultado = "BIEN";
            } catch (Exception e) {
                    System.out.println(e);
                    resultado = "FALLO";
            }
                
                
            case "consultarRegVehiculoFicha":

                RegVehiculo reg = gestorReg.consultarRegVehiculoFicha(parametros[1]);
                if (reg == null) {
                    salida.println("NO_ENCONTRADO");
                } else {
                    salida.println(parseToJSONRegVehiculo(reg));
                }
                break;
                
            case "consultarRegVehiculoPlaca":

                RegVehiculo regVehi = gestorReg.consultarRegVehiculoPlaca(parametros[1]);
                if (regVehi == null) {
                    salida.println("NO_ENCONTRADO");
                } else {
                    salida.println(parseToJSONRegVehiculo(regVehi));
                }
                break;
            case "consultarVehiculo":

                Vehiculo vehi = gestorVehi.consultarVehiculo(parametros[1]);
                if (vehi == null) {
                    salida.println("NO_ENCONTRADO");
                } else {
                    salida.println(parseToJSONVehiculo(vehi));
                }
                break;
                
            case "actualizarSalida":
                try {
                    gestorPa.actualizarSalida(parametros[1]);
                    resultado = "BIEN";
            } catch (Exception e) {
                    System.out.println(e);
                    resultado = "FALLO";
            }    
                
            case "actualizarRegVehiculo":
                try {
                    gestorReg.actualizarRegVehiculo(parametros[1], parametros[2]);
                    resultado = "BIEN";
                } catch (Exception e) {
                    System.out.println(e);
                    resultado = "FALLO";
                }
                break;
                
            case "consultarTarifa":
                Tarifa tarifa = gestorTa.consultarTarifa(parametros[1],parametros[2]);
                if (tarifa == null) {
                    salida.println("NO_ENCONTRADO");
                }else{
                    salida.println(parseToJSONTarifa(tarifa));
                }
                break;
                
            case "consultarParqueadero":
                Parqueadero parque = gestorPa.consultarParqueadero(parametros[1]);
                if (parque == null) {
                    salida.println("NO_ENCONTRADO");
                }else{
                    salida.println(parseToJSONParqueadero(parque));
                }
                break;
                
            case "consultarTarifaMotos":
                Tarifa tari = gestorTa.consultarTarifaMotos(parametros[1], parametros[2]);
                if (tari == null) {
                    salida.println("NO_ENCONTRADO");
                }else{
                    salida.println(parseToJSONTarifa(tari));
                }
                break;
                
            case "consultarPersona":
                Persona per = gestorPer.consultarPersona(parametros[1], parametros[2]);
                if (per == null) {
                    salida.println("NO_ENCONTRADO");
                }else{
                    salida.println(parseToJSONPersona(per));
                }
                
                break;
        }
    }
    
    //Cierra los flujos de entrada y salida
    private void cerrarFlujos() throws IOException {
        salida.close();
        entrada.close();
        socket.close();
    }
    
    //Convierte el objeto Json a Vehiculo
    private void parseToVehiculo(String Json){
        
        Vehiculo vehi = new Vehiculo();
        Gson gson = new Gson();
        Properties prop = gson.fromJson(Json, Properties.class);
        
        vehi.setPlacaVehiculo(prop.getProperty("placaVehiculo"));
        vehi.setTipoVehiculo(prop.getProperty("tipoVehiculo"));   
    }
    
    //Convierte el objeto Json a RegVehiculo
    private void parseToRegVehiculo(String Json){
        
        RegVehiculo reg = new RegVehiculo();
        Gson gson = new Gson();
        Properties prop = gson.fromJson(Json, Properties.class);
        
        reg.setRegNumFicha(prop.getProperty("regNumFicha"));
        reg.setRegPlacaVehiculo(prop.getProperty("regPlacaVehiculo"));
        reg.setRegTipoVehiculo(prop.getProperty("regTipoVehiculo"));
        reg.setRegHoraYFechaEntrada(prop.getProperty("regHoraYFechaEntrada"));
        reg.setRegEstadoVehiculo(prop.getProperty("regEstadoVehiculo"));
        reg.setRegLlaves(prop.getProperty("regLlaves"));
        reg.setRegNumCascos(prop.getProperty("regNumCascos"));
        reg.setRegNitParqueadero(prop.getProperty("regNitParqueadero"));
        reg.setRegNumCasillero(prop.getProperty("regNumCasillero"));
        reg.setRegUsuario(prop.getProperty("regUsuario"));
        reg.setRegHoraYFechaSalida(prop.getProperty("regHoraYFechaSalida"));
    }
    
    private void parseToRegParqueadero(String Json){
        
        Parqueadero p = new Parqueadero();
        Gson gson = new Gson();
        Properties prop = gson.fromJson(Json, Properties.class);
        
        p.setNitParqueadero(prop.getProperty("nitParqueadero"));
        p.setNomParqueadero(prop.getProperty("nomParqueadero"));
        p.setDirecParqueadero(prop.getProperty("dirParqueadero"));
        p.setTelParqueadero(prop.getProperty("telParqueadero"));
        p.setUsuarioPar(prop.getProperty("usuarioPar"));
        p.setLibres(prop.getProperty("puestosLibres"));
        p.setOcupados(prop.getProperty("puestosOcupados"));    
    } 
    
    //Convierte el objeto Vehiculo a Json
    private String parseToJSONVehiculo(Vehiculo vehi){
        JsonObject jsonobj = new JsonObject();
        
        jsonobj.addProperty("placaVehiculo", vehi.getPlacaVehiculo());
        jsonobj.addProperty("tipoVehiculo", vehi.getTipoVehiculo());
        return jsonobj.toString();
    }
    
    //Convierte el objeto Persona a Json
    private String parseToJSONPersona(Persona per){
        JsonObject jsonObj = new JsonObject();
        
        jsonObj.addProperty("perID", per.getPerID());
        jsonObj.addProperty("perNombres", per.getPerNombres());
        jsonObj.addProperty("perApellidos", per.getPerApellidos());
        jsonObj.addProperty("perRol", per.getPerRol());
        jsonObj.addProperty("perTelefono", per.getPerTelefono());
        jsonObj.addProperty("perUsuario", per.getPerUsuario());
        jsonObj.addProperty("perContraseña", per.getPerContraseña());
        return jsonObj.toString();
    }
    
    //Convierte el objeto Parqueadero a Json
    private String parseToJSONParqueadero(Parqueadero par){
        JsonObject jsonObj = new JsonObject();
        
        jsonObj.addProperty("nitParqueadero", par.getNitParqueadero());
        jsonObj.addProperty("nomParqueadero", par.getNomParqueadero());
        jsonObj.addProperty("direcParqueadero", par.getDirecParqueadero());
        jsonObj.addProperty("telParqueadero", par.getTelParqueadero());
        jsonObj.addProperty("usuarioPar", par.getUsuarioPar());
        jsonObj.addProperty("puestosLibres", par.getLibres());
        jsonObj.addProperty("puestosOcupados", par.getOcupados());
        return jsonObj.toString();
    }
    
    
    //Convierte el objeto RegVehiculo a Json
    private String parseToJSONRegVehiculo(RegVehiculo reg){
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("regNumFicha", reg.getRegNumFicha());
        jsonObject.addProperty("regPlacaVehiculo", reg.getRegPlacaVehiculo());
        jsonObject.addProperty("regTipoVehiculo", reg.getRegTipoVehiculo());
        jsonObject.addProperty("regHoraYFechaEntrada", reg.getRegHoraYFechaEntrada());
        jsonObject.addProperty("regEstadoVehiculo", reg.getRegEstadoVehiculo());
        jsonObject.addProperty("regLlaves", reg.getRegLlaves());
        jsonObject.addProperty("regNumCascos", reg.getRegNumCascos());
        jsonObject.addProperty("regNitParqueadero", reg.getRegNitParqueadero());
        jsonObject.addProperty("regNumCasillero", reg.getRegNumCasillero());
        jsonObject.addProperty("regUsuario", reg.getRegUsuario());
        jsonObject.addProperty("regHoraYFechaSalida", reg.getRegHoraFechaSalida());
        return jsonObject.toString();
    }
    
    //Convierte el objeto Tarifa a Json
    private String parseToJSONTarifa(Tarifa tari){
        JsonObject jsonobj = new JsonObject();
        
        jsonobj.addProperty("idTarifa", tari.getIdTarifa());
        jsonobj.addProperty("tarifaHora", tari.getTarifaHora());
        jsonobj.addProperty("tarifaMedioDia", tari.getTarifaMedioDia());
        jsonobj.addProperty("tarifaDia", tari.getTarifaDia());
        jsonobj.addProperty("ValorTotal", tari.getValorTotal());
        return jsonobj.toString();
    }
    
}
