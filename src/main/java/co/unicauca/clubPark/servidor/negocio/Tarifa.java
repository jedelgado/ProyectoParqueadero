package co.unicauca.clubPark.servidor.negocio;

/**
 *
 * @author javier
 */
public class Tarifa {
    
    private String idTarifa;
    private String tarifaHora;
    private String tarifaMedioDia;
    private String tarifaDia;
    private String ValorTotal;
    
    //Utilizamos diferentes valores para carro y moto.
    //Carro( Dia: 30.000, medio dia: 15.000, Hora: 1.750)
    //Moto ( Dia: 24.000, medio dia: 12.000, Hora: 1.500)
    //Constructor parametrizado para la clase Tarifa
    public Tarifa(String idTarifa, String tarifaHora, String tarifaMedioDia, String tarifaDia, String ValorTotal) {
        this.idTarifa = idTarifa;
        this.tarifaHora = tarifaHora;
        this.tarifaMedioDia = tarifaMedioDia;
        this.tarifaDia = tarifaDia;
        this.ValorTotal = ValorTotal;
    }
    
    //Constructor sin parametros para la clase Tarifa
    public Tarifa(){
        idTarifa ="";
        tarifaHora = "";
        tarifaMedioDia = "";
        tarifaDia = "";
        ValorTotal = "";
    }

    //Getters y Setters para los atributos de la clase tarifa
    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getTarifaHora() {
        return tarifaHora;
    }

    public void setTarifaHora(String tarifaHora) {
        this.tarifaHora = tarifaHora;
    }

    public String getTarifaMedioDia() {
        return tarifaMedioDia;
    }

    public void setTarifaMedioDia(String tarifaMedioDia) {
        this.tarifaMedioDia = tarifaMedioDia;
    }

    public String getTarifaDia() {
        return tarifaDia;
    }

    public void setTarifaDia(String tarifaDia) {
        this.tarifaDia = tarifaDia;
    }

    public String getValorTotal() {
        return ValorTotal;
    }

    public void setValorTotal(String ValorTotal) {
        this.ValorTotal = ValorTotal;
    }
    
}
