package com.juego.model;

import com.juego.patrones.strategy.EstrategiaAtaque;
import com.juego.patrones.strategy.AtaqueNormal;

public class Personaje {
    private String nombre;
    private int puntosDeVida;
    private int maxPuntosDeVida;
    private EstrategiaAtaque estrategia;

    public Personaje(String nombre, int puntosDeVida) {
        this.nombre = nombre;
        this.puntosDeVida = puntosDeVida;
        this.maxPuntosDeVida = puntosDeVida;
        this.estrategia = new AtaqueNormal();
    }

    // constructor simple con 100 HP
    public Personaje(String nombre) {
        this(nombre, 100);
    }

    // el personaje ataca usando su estrategia actual
    public int atacar(Personaje oponente) {
        int dano = estrategia.calcularDano();
        oponente.recibirDano(dano);
        System.out.println(this.nombre + " ataca a " + oponente.getNombre()
                + " causando " + dano + " de danio. [" + estrategia.getDescripcion() + "]");
        return dano;
    }

    public void recibirDano(int dano) {
        if (dano < 0) return;
        this.puntosDeVida -= dano;
        if (this.puntosDeVida < 0) {
            this.puntosDeVida = 0;
        }
    }

    public boolean estaVivo() {
        return this.puntosDeVida > 0;
    }

    public void setEstrategia(EstrategiaAtaque estrategia) {
        this.estrategia = estrategia;
    }

    public EstrategiaAtaque getEstrategia() {
        return this.estrategia;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntosDeVida() {
        return puntosDeVida;
    }

    public int getMaxPuntosDeVida() {
        return maxPuntosDeVida;
    }

    @Override
    public String toString() {
        return nombre + " [HP: " + puntosDeVida + "/" + maxPuntosDeVida + "]";
    }
}