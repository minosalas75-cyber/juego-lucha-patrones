package com.juego.patrones.decorator;

import com.juego.patrones.strategy.EstrategiaAtaque;

public abstract class ArmaDecorator implements EstrategiaAtaque {

    protected EstrategiaAtaque estrategiaBase;

    public ArmaDecorator(EstrategiaAtaque estrategiaBase) {
        this.estrategiaBase = estrategiaBase;
    }

    public EstrategiaAtaque getEstrategiaBase() {
        return estrategiaBase;
    }
}
