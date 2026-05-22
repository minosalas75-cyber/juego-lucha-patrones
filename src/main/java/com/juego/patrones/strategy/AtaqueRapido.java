package com.juego.patrones.strategy;

import java.util.Random;

public class AtaqueRapido implements EstrategiaAtaque {

    private Random rand;

    public AtaqueRapido() {
        this.rand = new Random();
    }

    public AtaqueRapido(Random rand) {
        this.rand = rand;
    }

    @Override
    public int calcularDano() {
        return rand.nextInt(11) + 5;
    }

    @Override
    public String getDescripcion() {
        return "Ataque Rapido";
    }
}
