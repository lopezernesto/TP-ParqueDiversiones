package Atracciones;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Espectaculo {
    private Lock lock = new ReentrantLock();
    private Condition grupo, adentro, espera;
    private int tamañoActual, tamañoGrupo, visitantesEsperando;
    private final int tamañoMaximo = 20;
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
     * de ingreso. En caso de que el tamañoActual esta a tope, los visitantes antes
     * de formar el gurpo quedan bloqueados en el grupo de espera
     * 
     */
    public void ingresarEspectaculo() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName()
                            + " y quiero entrar en el espectaculo");
            visitantesEsperando++;
            while (!lleno) {
                espera.await();
            }
            visitantesEsperando--;
            if (tamañoGrupo == 5) {
                System.out.println(
                        "Soy " + Thread.currentThread().getName()
                                + " y soy el ultimo que entro al grupo de ingreso");
                grupo.signalAll();
                tamañoGrupo = 0;
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

    public void sentarse() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y entre en el espectaculo");
            tamañoActual++;
            adentro.await();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }
    }

    public void salir() {
        try {
            lock.lock();
            if (tamañoActual == 1) {
                tamañoActual--;
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

    public void terminarFuncion() {
        try {
            lock.lock();
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y voy a despertar a los que estan adentro");
            espera.signalAll();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            lock.unlock();
        }

    }
    // cuando termina la funcion un empleado despierta a los de adentro para que
    // salgan y cuando salga el ultimo vuelven a ingresar (despertando a los de
    // espera)
}
