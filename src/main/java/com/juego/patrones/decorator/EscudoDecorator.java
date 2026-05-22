package com.juego.patrones.decorator;

import com.juego.patrones.strategy.EstrategiaAtaque;

public class EscudoDecorator extends ArmaDecorator {

    private static final int DANIO_EXTRA = 5;

    public EscudoDecorator(EstrategiaAtaque estrategiaBase) {
        super(estrategiaBase);
    }

    @Override
    public int calcularDano() {
        return estrategiaBase.calcularDano() + DANIO_EXTRA;
    }

    @Override
    public String getDescripcion() {
        return estrategiaBase.getDescripcion() + " + Escudo";
    }
}
