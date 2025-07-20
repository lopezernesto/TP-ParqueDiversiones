package Atracciones;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrenTuristico {
    private BlockingQueue<Integer> tren, colaEspera;
    private int cantidadActual;
    private boolean empezoRecorrido;

    public TrenTuristico() {
        tren = new LinkedBlockingQueue<>(10);
        colaEspera = new LinkedBlockingQueue<>();
        cantidadActual = 0;
        empezoRecorrido = false;
    }

    // aca entran los visitantes
    public synchronized void subirseTren() {
        try {
            // El visitante se mete en la cola de espera
            colaEspera.put(0);
            if (tren.remainingCapacity() > 0 && !empezoRecorrido) {
                // El visitante se queda bloqueado hasta tomar el asiento del tren
                tren.take();
                System.out.println("me subi al tren");
                // Libera un cupo en la cola de espera
                colaEspera.take();
            }
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // aca entra el empleado
    public void venderPasaje() {
        try {
            // el empleado agrega un asiento al tren
            tren.put(0);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public synchronized void empezarRecorrido() {
        empezoRecorrido = true;
    }

    public synchronized void terminarRecorrido() {
        empezoRecorrido = false;
    }


}
