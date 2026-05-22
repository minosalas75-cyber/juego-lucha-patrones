package com.juego.patrones.factory;

import com.juego.model.Personaje;
import com.juego.patrones.strategy.AtaqueNormal;
import com.juego.patrones.strategy.AtaqueFuerte;
import com.juego.patrones.strategy.AtaqueRapido;

public class PersonajeFactory {

    public enum TipoPersonaje {
        GUERRERO, MAGO, ASESINO
    }

    public static Personaje crearPersonaje(TipoPersonaje tipo, String nombre) {
        switch (tipo) {
            case GUERRERO:
                Personaje guerrero = new Personaje(nombre, 120);
                guerrero.setEstrategia(new AtaqueFuerte());
                return guerrero;

            case MAGO:
                Personaje mago = new Personaje(nombre, 90);
                mago.setEstrategia(new AtaqueNormal());
                return mago;

            case ASESINO:
                Personaje asesino = new Personaje(nombre, 80);
                asesino.setEstrategia(new AtaqueRapido());
                return asesino;

            default:
                throw new IllegalArgumentException("Tipo no valido: " + tipo);
        }
    }
}
