package Dominio;

public class AutosChocadores {
    private int capacidad, cantidadActual;
    private boolean puedeEntrar = true;

    public AutosChocadores() {
        capacidad = 20;
        cantidadActual = 0;
    }

    /*
     * Retorno T/F porque en caso de no ingresar, no entra al metodo de SalirAuto()
     */
    public synchronized boolean ingresarAuto() {
        boolean exito = false;
        System.out.println(
                "Soy " + Thread.currentThread().getName() + " y quiero entrar al autito");
        try {
            if (puedeEntrar) {
                exito = true;
                if (cantidadActual == capacidad) {
                    System.out.println(
                            "Soy " + Thread.currentThread().getName() + " y soy el ultimo, despierto a todos");
                    this.notifyAll();
                } else {
                    cantidadActual++;
                    System.out.println(
                            "Soy " + Thread.currentThread().getName() + " entre al autito");
                    this.wait();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return exito;
    }

    public synchronized void salirAuto() {
        if (cantidadActual == 0) {
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y soy el ultimo e n salir, libero");
            puedeEntrar = true;
        } else {
            System.out.println(
                    "Soy " + Thread.currentThread().getName() + " y sali del autito");
            cantidadActual--;
        }
    }
}
