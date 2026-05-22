# Juego de Lucha por Turnos

![Java CI with Maven](https://github.com/minosalas75-cyber/juego-lucha-patrones/actions/workflows/ci.yml/badge.svg)

## Descripcion

Juego de lucha por turnos entre dos personajes hecho en Java 17 con Maven. Se aplicaron patrones de diseño para mejorar la arquitectura del codigo original.

## Patrones Implementados

### Factory Method (PersonajeFactory)
Se usa para crear los diferentes tipos de personajes (Guerrero, Mago, Asesino) sin usar new Personaje() directo. Cada tipo viene con sus stats y estrategia ya configurados.

### Strategy (EstrategiaAtaque)
Permite cambiar la forma en que un personaje ataca sin modificar la clase Personaje. Hay tres estrategias:
- AtaqueNormal: danio entre 10-30
- AtaqueFuerte: danio entre 20-50 pero puede fallar (20%)
- AtaqueRapido: danio entre 5-15 pero siempre conecta

### Decorator (ArmaDecorator)
Agrega armas al personaje sin modificar las clases originales. Envuelve la estrategia de ataque y le suma danio extra:
- EspadaDecorator: +15 de danio
- EscudoDecorator: +5 de danio
- Se pueden apilar (espada + escudo)

## Como Ejecutar

```bash
# Compilar
mvn clean compile

# Ejecutar pruebas
mvn test

# Generar reporte de cobertura
mvn jacoco:report
```

## Pruebas

40 pruebas unitarias con JUnit 5 y Mockito cubriendo:
- Creacion de personajes y validacion de atributos
- Estrategias de ataque y rangos de danio
- Factory con los tres tipos de personajes
- Decoradores apilables
- Logica de turnos del juego
- Casos borde (HP negativo, danio negativo, personajes null)
