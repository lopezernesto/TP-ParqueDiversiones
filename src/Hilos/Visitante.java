package Hilos;

import Dominio.ParqueDiversiones;

public class Visitante implements Runnable {

    private ParqueDiversiones parque;

    public Visitante(ParqueDiversiones pd) {
        parque = pd;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }
}