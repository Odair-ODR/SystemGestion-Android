package com.example.website.consulta.Entidad;

public class Articulos {
    private int campar;
    private String unimed, motor, alternante, codbar, cpdnew;
    private int totSaldo;

    public Articulos() {

    }

    public int getCampar() {
        return campar;
    }

    public void setCampar(int campar) {
        this.campar = campar;
    }

    public String getAlternante() {
        return alternante;
    }

    public void setAlternante(String alternante) {
        this.alternante = alternante;
    }

    public String getCodbar() {
        return codbar;
    }

    public void setCodbar(String codbar) {
        this.codbar = codbar;
    }

    public String getCpdnew() {
        return cpdnew;
    }

    public void setCpdnew(String cpdnew) {
        this.cpdnew = cpdnew;
    }

    public int getTotSaldo() {
        return totSaldo;
    }

    public void setTotSaldo(int totSaldo) {
        this.totSaldo = totSaldo;
    }

    public String getUnimed(){
        return unimed;
    }

    public void setUnimed(String unimed){
        this.unimed = unimed;
    }

    public String getMotor(){
        return motor;
    }

    public void setMotor(String motor){
        this.motor = motor;
    }
}
