package com.example.interfaceorowan.modele;
public class Data {
    private final double time;
    private final double value;
    public Data(double time, double value) {
        this.time = time;
        this.value = value;
    }
    public double getTime() {
        return time;
    }
    public double getValue() {
        return value;
    }
}
