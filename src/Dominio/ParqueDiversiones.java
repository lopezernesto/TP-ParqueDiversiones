package Dominio;

import Atracciones.*;

public class ParqueDiversiones {
    private AutosChocadores autos;
    private Comedor comedor;
    private Espectaculo espectaculo;
    private JuegosDePremios juegosDePremios;
    private RealidadVirtual realidadVirtual;
    private TrenTuristico trenTuristico;
    private boolean abierto;// indica el estado del parque (T:abierto/F:cerrado)

    public ParqueDiversiones(int mesas, int cantVisores, int cantManoplas, int cantBases) {
        autos = new AutosChocadores();
        comedor = new Comedor(mesas);
        espectaculo = new Espectaculo();
        juegosDePremios = new JuegosDePremios();
        realidadVirtual = new RealidadVirtual(cantVisores, cantManoplas, cantBases);
        abierto = true;
    }

    public boolean isAbierto() {
        return abierto;
    }
}
