package co.unicauca.clubPark.servidor.negocio;

/**
 *
 * @author javier
 */
public class Persona {
    private String perID;
    private String perNombres;
    private String perApellidos;
    private String perRol;
    private String perTelefono;
    private String perUsuario;
    private String perContraseña;

    //Contructor parametrizado de la clase persona
    public Persona(String perID, String perNombres, String perApellidos, String perRol, String perTelefono, String perUsuario, String perContraseña) {
        this.perID = perID;
        this.perNombres = perNombres;
        this.perApellidos = perApellidos;
        this.perRol = perRol;
        this.perTelefono = perTelefono;
        this.perUsuario = perUsuario;
        this.perContraseña = perContraseña;
    }
    
    //Contructor sin parametros de la clase persona
    public Persona(){
        perID="";
        perNombres="";
        perApellidos="";
        perRol="";
        perTelefono="";
        perUsuario="";
        perContraseña="";
    }
    
    //Getters y Setters de los atibutos de la clase Persona
    public String getPerID() {
        return perID;
    }

    public void setPerID(String perID) {
        this.perID = perID;
    }

    public String getPerNombres() {
        return perNombres;
    }

    public void setPerNombres(String perNombres) {
        this.perNombres = perNombres;
    }

    public String getPerApellidos() {
        return perApellidos;
    }

    public void setPerApellidos(String perApellidos) {
        this.perApellidos = perApellidos;
    }

    public String getPerRol() {
        return perRol;
    }

    public void setPerRol(String perRol) {
        this.perRol = perRol;
    }

    public String getPerTelefono() {
        return perTelefono;
    }

    public void setPerTelefono(String perTelefono) {
        this.perTelefono = perTelefono;
    }

    public String getPerUsuario() {
        return perUsuario;
    }

    public void setPerUsuario(String perUsuario) {
        this.perUsuario = perUsuario;
    }

    public String getPerContraseña() {
        return perContraseña;
    }

    public void setPerContraseña(String perContraseña) {
        this.perContraseña = perContraseña;
    }
}
