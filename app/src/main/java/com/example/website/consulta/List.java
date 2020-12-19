package com.example.website.consulta;

public class List {

    private int    codbar;
    private int    alternante;
    private String descripcion;
    private int    saldo;

    public List(int codbar,int alternante,String descripcion,int saldo)
    {
        this.codbar=codbar;
        this.alternante=alternante;
        this.descripcion=descripcion;
        this.saldo=saldo;
    }

    public int getCodbar() {
        return codbar;
    }

    public int getAlternante() {
        return alternante;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
