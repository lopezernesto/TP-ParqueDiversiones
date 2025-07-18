package Dominio;

import java.util.concurrent.Semaphore;

public class RealidadVirtual {
    private Semaphore visor, manopla, base, tieneEquipo, encargado, visitante;

    public RealidadVirtual(int cantVisores, int cantManoplas, int cantBases) {
        tieneEquipo = new Semaphore(0);
        visor = new Semaphore(cantVisores);
        manopla = new Semaphore(cantManoplas);
        base = new Semaphore(cantBases);
        encargado = new Semaphore(0);
        visitante = new Semaphore(1);
    }

    public void ingresarVR() {
        try {
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y quiero ingresar a la RV");
            visitante.acquire();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y estoy pidiendo el equipo");
            // "Despierto" al encargado
            encargado.release();

            // me quedo bloqueado hasta tener el equipo necesario
            tieneEquipo.acquire();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + "y tengo el equipo");

            // cuando ya tengo el equipo libero para que otro visitante pida equipo
            visitante.release();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /*
     * Cuando sale de la actividad, el visitante devuelve el equipo
     */
    public void salirVR() {
        try {
            visor.release();
            manopla.release(2);
            base.release();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void darEquipo() {
        try {
            encargado.acquire();
            visor.acquire(); // le doy 1 visor
            manopla.acquire(2); // le doy 2 manoplas
            base.acquire(); // le doy 1 base
            tieneEquipo.release(); // libero para que el hilo pueda seguir su ejecucion
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
