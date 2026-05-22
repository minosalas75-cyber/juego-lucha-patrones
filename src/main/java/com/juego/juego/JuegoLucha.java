package com.juego.juego;

import com.juego.model.Personaje;

public class JuegoLucha {

    private Personaje jugador1;
    private Personaje jugador2;
    private int turnoActual;

    public JuegoLucha(Personaje jugador1, Personaje jugador2) {
        if (jugador1 == null || jugador2 == null) {
            throw new IllegalArgumentException("Los personajes no pueden ser null");
        }
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.turnoActual = 1;
    }

    public boolean ejecutarTurno() {
        if (!jugador1.estaVivo() || !jugador2.estaVivo()) {
            return false;
        }

        Personaje atacante;
        Personaje defensor;

        if (turnoActual % 2 != 0) {
            atacante = jugador1;
            defensor = jugador2;
        } else {
            atacante = jugador2;
            defensor = jugador1;
        }

        System.out.println("--- Turno " + turnoActual + " ---");
        atacante.atacar(defensor);
        System.out.println(defensor.getNombre() + " queda con " + defensor.getPuntosDeVida() + " HP");
        System.out.println();

        turnoActual++;

        return jugador1.estaVivo() && jugador2.estaVivo();
    }

    public Personaje iniciarPelea() {
        System.out.println("=== PELEA: " + jugador1.getNombre() + " vs " + jugador2.getNombre() + " ===");
        System.out.println(jugador1);
        System.out.println(jugador2);
        System.out.println();

        while (ejecutarTurno()) {
        }

        Personaje ganador = getGanador();
        if (ganador != null) {
            System.out.println("*** GANADOR: " + ganador.getNombre()
                    + " con " + ganador.getPuntosDeVida() + " HP restantes ***");
        }
        return ganador;
    }

    public Personaje getGanador() {
        if (!jugador1.estaVivo()) return jugador2;
        if (!jugador2.estaVivo()) return jugador1;
        return null;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public Personaje getJugador1() {
        return jugador1;
    }

    public Personaje getJugador2() {
        return jugador2;
    }
}
