package Atracciones;

import java.util.concurrent.CyclicBarrier;

public class Mesa {
    private boolean ocupada;
    private CyclicBarrier ocuparMesa = new CyclicBarrier(4);
    private CyclicBarrier desocuparMesa = new CyclicBarrier(4);

    public Mesa() {
        ocupada = false;
    }

    public void sentarse() {
        try {
            ocuparMesa.await();
            ocupada = true;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void levantarse() {
        try {
            desocuparMesa.await();
            ocupada = false;
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public boolean isOcupada() {
        return ocupada;
    }
}
