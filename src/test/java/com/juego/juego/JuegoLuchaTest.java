package com.juego.juego;

import com.juego.model.Personaje;
import com.juego.patrones.strategy.EstrategiaAtaque;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JuegoLuchaTest {

    @Test
    @DisplayName("Debe crear juego con dos personajes")
    void testCrearJuego() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");

        JuegoLucha juego = new JuegoLucha(p1, p2);

        assertEquals(p1, juego.getJugador1());
        assertEquals(p2, juego.getJugador2());
        assertEquals(1, juego.getTurnoActual());
    }

    @Test
    @DisplayName("No debe permitir personajes null")
    void testPersonajesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new JuegoLucha(null, new Personaje("Loki"));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new JuegoLucha(new Personaje("Thor"), null);
        });
    }

    @Test
    @DisplayName("Primer turno debe ser del jugador 1")
    void testPrimerTurnoJugador1() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");

        EstrategiaAtaque mock = mock(EstrategiaAtaque.class);
        when(mock.calcularDano()).thenReturn(20);
        when(mock.getDescripcion()).thenReturn("Test");
        p1.setEstrategia(mock);

        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.ejecutarTurno();

        assertEquals(80, p2.getPuntosDeVida());
        assertEquals(100, p1.getPuntosDeVida());
    }

    @Test
    @DisplayName("Segundo turno debe ser del jugador 2")
    void testSegundoTurnoJugador2() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");

        EstrategiaAtaque mock1 = mock(EstrategiaAtaque.class);
        when(mock1.calcularDano()).thenReturn(10);
        when(mock1.getDescripcion()).thenReturn("Test");

        EstrategiaAtaque mock2 = mock(EstrategiaAtaque.class);
        when(mock2.calcularDano()).thenReturn(15);
        when(mock2.getDescripcion()).thenReturn("Test");

        p1.setEstrategia(mock1);
        p2.setEstrategia(mock2);

        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.ejecutarTurno();
        juego.ejecutarTurno();

        assertEquals(85, p1.getPuntosDeVida());
        assertEquals(90, p2.getPuntosDeVida());
    }

    @Test
    @DisplayName("El juego debe terminar cuando alguien muere")
    void testJuegoTermina() {
        Personaje p1 = new Personaje("Thor", 30);
        Personaje p2 = new Personaje("Loki", 30);

        EstrategiaAtaque mock = mock(EstrategiaAtaque.class);
        when(mock.calcularDano()).thenReturn(25);
        when(mock.getDescripcion()).thenReturn("Test");

        p1.setEstrategia(mock);
        p2.setEstrategia(mock);

        JuegoLucha juego = new JuegoLucha(p1, p2);

        assertTrue(juego.ejecutarTurno());
        assertTrue(juego.ejecutarTurno());
        assertFalse(juego.ejecutarTurno());
    }

    @Test
    @DisplayName("getGanador debe retornar al sobreviviente")
    void testGanador() {
        Personaje p1 = new Personaje("Thor", 50);
        Personaje p2 = new Personaje("Loki", 20);

        EstrategiaAtaque mock = mock(EstrategiaAtaque.class);
        when(mock.calcularDano()).thenReturn(25);
        when(mock.getDescripcion()).thenReturn("Test");
        p1.setEstrategia(mock);

        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.ejecutarTurno();

        assertEquals(p1, juego.getGanador());
    }

    @Test
    @DisplayName("getGanador retorna null si ambos viven")
    void testSinGanadorTodavia() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");
        JuegoLucha juego = new JuegoLucha(p1, p2);
        assertNull(juego.getGanador());
    }

    @Test
    @DisplayName("iniciarPelea debe retornar un ganador")
    void testIniciarPelea() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");

        JuegoLucha juego = new JuegoLucha(p1, p2);
        Personaje ganador = juego.iniciarPelea();

        assertNotNull(ganador);
        assertTrue(ganador.estaVivo());
    }

    @Test
    @DisplayName("No ejecutar turno si alguien ya murio")
    void testNoEjecutarSiMurio() {
        Personaje p1 = new Personaje("Thor");
        Personaje p2 = new Personaje("Loki");

        p2.recibirDano(100);
        JuegoLucha juego = new JuegoLucha(p1, p2);

        assertFalse(juego.ejecutarTurno());
    }
}
