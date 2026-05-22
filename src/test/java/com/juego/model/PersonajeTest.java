package com.juego.model;

import com.juego.patrones.strategy.EstrategiaAtaque;
import com.juego.patrones.strategy.AtaqueNormal;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonajeTest {

    private Personaje guerrero;

    @BeforeEach
    void setUp() {
        guerrero = new Personaje("Thor");
    }

    @Test
    @DisplayName("Debe crear personaje con 100 HP por defecto")
    void testCreacionPorDefecto() {
        assertEquals("Thor", guerrero.getNombre());
        assertEquals(100, guerrero.getPuntosDeVida());
        assertTrue(guerrero.estaVivo());
    }

    @Test
    @DisplayName("Debe crear personaje con HP personalizado")
    void testCreacionConHpCustom() {
        Personaje tanque = new Personaje("Hulk", 200);
        assertEquals(200, tanque.getPuntosDeVida());
        assertEquals(200, tanque.getMaxPuntosDeVida());
    }

    @Test
    @DisplayName("Debe reducir HP al recibir danio")
    void testRecibirDano() {
        guerrero.recibirDano(30);
        assertEquals(70, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("HP no puede quedar negativo")
    void testHpNoNegativo() {
        guerrero.recibirDano(150);
        assertEquals(0, guerrero.getPuntosDeVida());
        assertFalse(guerrero.estaVivo());
    }

    @Test
    @DisplayName("Danio negativo no debe afectar al personaje")
    void testDanoNegativoNoAfecta() {
        guerrero.recibirDano(-10);
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Danio de 0 no cambia la vida")
    void testDanoCero() {
        guerrero.recibirDano(0);
        assertEquals(100, guerrero.getPuntosDeVida());
    }

    @Test
    @DisplayName("Atacar debe usar la estrategia y causar danio")
    void testAtacarUsaEstrategia() {
        EstrategiaAtaque mockEstrategia = mock(EstrategiaAtaque.class);
        when(mockEstrategia.calcularDano()).thenReturn(25);
        when(mockEstrategia.getDescripcion()).thenReturn("Mock");

        guerrero.setEstrategia(mockEstrategia);
        Personaje oponente = new Personaje("Loki");

        guerrero.atacar(oponente);

        assertEquals(75, oponente.getPuntosDeVida());
        verify(mockEstrategia).calcularDano();
    }

    @Test
    @DisplayName("Atacar debe retornar el danio causado")
    void testAtacarRetornaDano() {
        EstrategiaAtaque mockEstrategia = mock(EstrategiaAtaque.class);
        when(mockEstrategia.calcularDano()).thenReturn(20);
        when(mockEstrategia.getDescripcion()).thenReturn("Mock");
        guerrero.setEstrategia(mockEstrategia);

        Personaje oponente = new Personaje("Loki");
        int dano = guerrero.atacar(oponente);

        assertEquals(20, dano);
    }

    @Test
    @DisplayName("Se puede cambiar la estrategia de ataque")
    void testCambiarEstrategia() {
        EstrategiaAtaque nueva = mock(EstrategiaAtaque.class);
        guerrero.setEstrategia(nueva);
        assertEquals(nueva, guerrero.getEstrategia());
    }

    @Test
    @DisplayName("toString debe mostrar nombre y HP")
    void testToString() {
        String resultado = guerrero.toString();
        assertTrue(resultado.contains("Thor"));
        assertTrue(resultado.contains("100"));
    }

    @Test
    @DisplayName("Personaje con 1 HP sigue vivo")
    void testConUnHpSigueVivo() {
        guerrero.recibirDano(99);
        assertEquals(1, guerrero.getPuntosDeVida());
        assertTrue(guerrero.estaVivo());
    }

    @Test
    @DisplayName("Personaje con exactamente 0 HP esta muerto")
    void testConCeroHpMuerto() {
        guerrero.recibirDano(100);
        assertEquals(0, guerrero.getPuntosDeVida());
        assertFalse(guerrero.estaVivo());
    }
}
