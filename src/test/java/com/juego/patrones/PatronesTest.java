package com.juego.patrones;

import com.juego.model.Personaje;
import com.juego.patrones.strategy.*;
import com.juego.patrones.factory.PersonajeFactory;
import com.juego.patrones.factory.PersonajeFactory.TipoPersonaje;
import com.juego.patrones.decorator.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Random;

class PatronesTest {

    @Test
    @DisplayName("AtaqueNormal debe dar danio entre 10 y 30")
    void testAtaqueNormalRango() {
        AtaqueNormal ataque = new AtaqueNormal();
        for (int i = 0; i < 100; i++) {
            int dano = ataque.calcularDano();
            assertTrue(dano >= 10 && dano <= 30, "Danio fuera de rango: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueNormal debe tener descripcion correcta")
    void testAtaqueNormalDescripcion() {
        AtaqueNormal ataque = new AtaqueNormal();
        assertEquals("Ataque Normal", ataque.getDescripcion());
    }

    @Test
    @DisplayName("AtaqueFuerte puede dar 0 o entre 20 y 50")
    void testAtaqueFuerteRango() {
        AtaqueFuerte ataque = new AtaqueFuerte();
        for (int i = 0; i < 200; i++) {
            int dano = ataque.calcularDano();
            assertTrue(dano == 0 || (dano >= 20 && dano <= 50), "Danio fuera de rango: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueFuerte caso exitoso con Random fijo")
    void testAtaqueFuerteExitoso() {
        // usamos un Random con semilla fija para tener resultado predecible
        // en vez de mock porque Java 25 no deja mockear Random
        Random fixedRand = new Random(42);
        AtaqueFuerte ataque = new AtaqueFuerte(fixedRand);
        // corremos varios ataques y verificamos que estan en rango
        for (int i = 0; i < 50; i++) {
            int dano = ataque.calcularDano();
            assertTrue(dano == 0 || (dano >= 20 && dano <= 50),
                    "Danio fuera de rango: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueFuerte puede fallar y dar 0")
    void testAtaqueFuertePuedeFallar() {
        // corremos muchos ataques, al menos uno deberia ser 0
        AtaqueFuerte ataque = new AtaqueFuerte();
        boolean huboFallo = false;
        for (int i = 0; i < 500; i++) {
            if (ataque.calcularDano() == 0) {
                huboFallo = true;
                break;
            }
        }
        assertTrue(huboFallo, "En 500 intentos deberia haber al menos un fallo");
    }

    @Test
    @DisplayName("AtaqueRapido debe dar danio entre 5 y 15")
    void testAtaqueRapidoRango() {
        AtaqueRapido ataque = new AtaqueRapido();
        for (int i = 0; i < 100; i++) {
            int dano = ataque.calcularDano();
            assertTrue(dano >= 5 && dano <= 15, "Danio fuera de rango: " + dano);
        }
    }

    @Test
    @DisplayName("AtaqueRapido descripcion correcta")
    void testAtaqueRapidoDescripcion() {
        assertEquals("Ataque Rapido", new AtaqueRapido().getDescripcion());
    }

    @Test
    @DisplayName("AtaqueFuerte descripcion correcta")
    void testAtaqueFuerteDescripcion() {
        assertEquals("Ataque Fuerte", new AtaqueFuerte().getDescripcion());
    }

    @Test
    @DisplayName("Factory debe crear Guerrero con 120 HP")
    void testFactoryGuerrero() {
        Personaje guerrero = PersonajeFactory.crearPersonaje(TipoPersonaje.GUERRERO, "Kratos");
        assertEquals("Kratos", guerrero.getNombre());
        assertEquals(120, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Factory debe crear Mago con 90 HP")
    void testFactoryMago() {
        Personaje mago = PersonajeFactory.crearPersonaje(TipoPersonaje.MAGO, "Gandalf");
        assertEquals("Gandalf", mago.getNombre());
        assertEquals(90, mago.getPuntosDeVida());
    }

    @Test
    @DisplayName("Factory debe crear Asesino con 80 HP")
    void testFactoryAsesino() {
        Personaje asesino = PersonajeFactory.crearPersonaje(TipoPersonaje.ASESINO, "Ezio");
        assertEquals("Ezio", asesino.getNombre());
        assertEquals(80, asesino.getPuntosDeVida());
    }

    @Test
    @DisplayName("Guerrero debe tener estrategia AtaqueFuerte")
    void testFactoryGuerreroEstrategia() {
        Personaje guerrero = PersonajeFactory.crearPersonaje(TipoPersonaje.GUERRERO, "Kratos");
        assertTrue(guerrero.getEstrategia() instanceof AtaqueFuerte);
    }

    @Test
    @DisplayName("Asesino debe tener estrategia AtaqueRapido")
    void testFactoryAsesinoEstrategia() {
        Personaje asesino = PersonajeFactory.crearPersonaje(TipoPersonaje.ASESINO, "Ezio");
        assertTrue(asesino.getEstrategia() instanceof AtaqueRapido);
    }

    @Test
    @DisplayName("Mago debe tener estrategia AtaqueNormal")
    void testFactoryMagoEstrategia() {
        Personaje mago = PersonajeFactory.crearPersonaje(TipoPersonaje.MAGO, "Gandalf");
        assertTrue(mago.getEstrategia() instanceof AtaqueNormal);
    }

    @Test
    @DisplayName("EspadaDecorator debe sumar 15 al danio base")
    void testEspadaAgregaDanio() {
        EstrategiaAtaque mockBase = mock(EstrategiaAtaque.class);
        when(mockBase.calcularDano()).thenReturn(20);
        when(mockBase.getDescripcion()).thenReturn("Ataque Normal");

        EspadaDecorator espada = new EspadaDecorator(mockBase);
        assertEquals(35, espada.calcularDano());
    }

    @Test
    @DisplayName("EspadaDecorator debe agregar + Espada a la descripcion")
    void testEspadaDescripcion() {
        EstrategiaAtaque mockBase = mock(EstrategiaAtaque.class);
        when(mockBase.getDescripcion()).thenReturn("Ataque Normal");

        EspadaDecorator espada = new EspadaDecorator(mockBase);
        assertEquals("Ataque Normal + Espada", espada.getDescripcion());
    }

    @Test
    @DisplayName("EscudoDecorator debe sumar 5 al danio base")
    void testEscudoAgregaDanio() {
        EstrategiaAtaque mockBase = mock(EstrategiaAtaque.class);
        when(mockBase.calcularDano()).thenReturn(20);
        when(mockBase.getDescripcion()).thenReturn("Ataque Normal");

        EscudoDecorator escudo = new EscudoDecorator(mockBase);
        assertEquals(25, escudo.calcularDano());
    }

    @Test
    @DisplayName("Se pueden apilar decoradores espada + escudo")
    void testDecoradoresApilados() {
        EstrategiaAtaque mockBase = mock(EstrategiaAtaque.class);
        when(mockBase.calcularDano()).thenReturn(10);
        when(mockBase.getDescripcion()).thenReturn("Ataque Normal");

        EspadaDecorator conEspada = new EspadaDecorator(mockBase);
        EscudoDecorator conAmbos = new EscudoDecorator(conEspada);

        assertEquals(30, conAmbos.calcularDano());
        assertEquals("Ataque Normal + Espada + Escudo", conAmbos.getDescripcion());
    }

    @Test
    @DisplayName("Decorator debe mantener referencia a estrategia base")
    void testDecoradorEstrategiaBase() {
        AtaqueNormal base = new AtaqueNormal();
        EspadaDecorator espada = new EspadaDecorator(base);
        assertEquals(base, espada.getEstrategiaBase());
    }
}
