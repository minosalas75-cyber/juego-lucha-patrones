package com.juego.patrones.strategy;

import java.util.Random;

public class AtaqueFuerte implements EstrategiaAtaque {

    private Random rand;

    public AtaqueFuerte() {
        this.rand = new Random();
    }

    public AtaqueFuerte(Random rand) {
        this.rand = rand;
    }

    @Override
    public int calcularDano() {
        if (rand.nextInt(100) < 20) {
            return 0;
        }
        return rand.nextInt(31) + 20;
    }

    @Override
    public String getDescripcion() {
        return "Ataque Fuerte";
    }
}
