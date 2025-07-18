package Dominio;

import java.util.Random;

public class Comedor {
    private boolean estaLleno;
    private Mesa[] mesas;

    public Comedor(int cantMesas) {
        mesas = new Mesa[cantMesas];
        estaLleno = false;

    }

    /*
     * entrarComedor retorna -1 si el visitante decide no esperar e irse, en caso de
     * que haya podido agarrar una mesa, verifica cual mesa esta disponible para
     * sentarse y lo hace, retornando la posicion de dicha mesa para despues
     * levantarse de la misma
     */
    public synchronized int entrarComedor() {
        int pos = -1;
        try {
            if (estaLleno) {
                boolean espera = new Random().nextBoolean();
                if (espera) {
                    // en caso de querer esperar se queda bloqueado, luego vuelve a entrar
                    this.wait();
                    entrarComedor();
                }
            } else {
                int aux = 0;
                for (Mesa mesa : mesas) {
                    if (mesa.isOcupada()) {
                        aux++;
                        continue;
                    } else {
                        // si ya se pudo sentar salgo del forech guardando la pos de la mesa
                        pos = aux;
                        mesa.sentarse();
                        break;
                    }
                }
            }
            // Si el hilo llego hasta aca entonces significa que no hay lugar y se debe
            // cambiar la variable de que esta lleno
            if (pos == -1) {
                estaLleno = true;
                // como estuvo lleno y no pudo entrar, vuelvo a intentar pero ahora con la
                // posibilidad de elegir si desea quedarse o irse
                entrarComedor();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return pos;
    }

    public synchronized void salirComedor(int posMesa) {
        try {
            mesas[posMesa].levantarse();
            if (!estaLleno) {
                estaLleno = true;
                this.notifyAll();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
