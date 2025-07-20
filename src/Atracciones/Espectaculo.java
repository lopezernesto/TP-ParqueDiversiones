package Atracciones;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Espectaculo {
    /*
     * Logica planteada: El espectalo comienza cuando la sala esta llena, cuando la
     * funcion termina un empleado da el aviso para que los visitantes que estan
     * adentro se retiren
     */
    private Lock lock = new ReentrantLock();
    private Condition grupo, adentro, espera;
    private int tamañoActual, tamañoGrupo, visitantesEsperando;
    private final int capacidad = 20;
    boolean lleno = false;

    public Espectaculo() {
        grupo = lock.newCondition();
        adentro = lock.newCondition();
        espera = lock.newCondition();
        tamañoActual = 0;
        tamañoGrupo = 0;
        visitantesEsperando = 0;
    }

    /*
     * Formo los grupos: a medida que van ingresando se van bloqueando en el grupo
     * de ingreso. El ultimo en ingresar al grupo da el aviso para que puedan
     * entrar. Solo se pueden formar grupos mientras la sala no este llena.
     * En caso de que la sala este llena, los visitantes antes
     * de formar el grupo quedan bloqueados en el la cola de espera de la condicion
     * 'espera'.
     */
    public void ingresarEspectaculo() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName()
                            + " y quiero entrar en el espectaculo");
            visitantesEsperando++;
            while (lleno) {
                espera.await();
            }
            visitantesEsperando--;
            if (tamañoGrupo == 5) {
                System.out.println(
                        "Soy " + Thread.currentThread().getName()
                                + " y soy el ultimo que entro al grupo de ingreso");
                tamañoGrupo = 0;
                grupo.signalAll();
            } else {
                System.out.println(
                        "Soy " + Thread.currentThread().getName() + " y entre al grupo de ingreso");
                tamañoGrupo++;
                grupo.await();
                // aca desde el visitante quiero ingresar al espectaculo pero solo lo va a hacer
                // cuando lo despierte el ultimo
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }

    }

    /*
     * Simula la entrada al espectaculo: los visitantes van ingresando incrementando
     * la variable 'tamañoActual' y bloqueandose en la cola de espera de 'adentro'
     * cuando tamañoActual llega al tamaño de la capacidad(20), se modifica el
     * booleano de 'lleno' para que no puedan seguir ingresando a la sala
     */
    public void sentarse() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y entre en el espectaculo");
            tamañoActual++;
            if (tamañoActual == capacidad)// capacidad = 20
                lleno = true;
            adentro.await();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    /*
     * Simula la salida del espectaculo: cuando el empleado avisa que pueden salir,
     * los visitantes van decrementando la variable 'tamañoActual'.
     * Recien cuando sale el ultimo se modifica la variable 'lleno' para indicar que
     * puedan entrar y en caso de que haya gente esperando para formar su grupo, los
     * despierta.
     */
    public void salir() {
        try {
            lock.lock();
            if (tamañoActual == 1) {
                tamañoActual--;
                lleno = false;
                System.out.println(
                        "Soy " + Thread.currentThread().getName() + " y soy el ultimo en salir");
                if (visitantesEsperando > 0) {
                    espera.signalAll();
                }
            } else {
                tamañoActual--;
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    /*
     * La funcion termino: Un empleado despierta a los que estan en la cola de
     * espera de 'adentro' para que vayan saliendo del espectaculo
     */
    public void terminarFuncion() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y voy a despertar a los que estan adentro");
            adentro.signalAll();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }

    }
}
