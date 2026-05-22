package com.juego.patrones.strategy;

import java.util.Random;

public class AtaqueNormal implements EstrategiaAtaque {

    private Random rand;

    public AtaqueNormal() {
        this.rand = new Random();
    }

    public AtaqueNormal(Random rand) {
        this.rand = rand;
    }

    @Override
    public int calcularDano() {
        return rand.nextInt(21) + 10;
    }

    @Override
    public String getDescripcion() {
        return "Ataque Normal";
    }
}
