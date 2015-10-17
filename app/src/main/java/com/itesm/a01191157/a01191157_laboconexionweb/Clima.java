package com.itesm.a01191157.a01191157_laboconexionweb;

/**
 * Created by alfredo_altamirano on 10/16/15.
 */
public class Clima {

    private double tempActual;
    private double tempMinima;
    private double tempMaxima;

    public Clima(double tempActual, double tempMinima, double tempMaxima) {
        this.tempActual = tempActual;
        this.tempMinima = tempMinima;
        this.tempMaxima = tempMaxima;
    }

    public double getTempActual() {
        return tempActual;
    }

    public void setTempActual(double tempActual) {
        this.tempActual = tempActual;
    }

    public double getTempMinima() {
        return tempMinima;
    }

    public void setTempMinima(double tempMinima) {
        this.tempMinima = tempMinima;
    }

    public double getTempMaxima() {
        return tempMaxima;
    }

    public void setTempMaxima(double tempMaxima) {
        this.tempMaxima = tempMaxima;
    }
}
