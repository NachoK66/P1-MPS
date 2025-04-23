/* REALIZADO POR IGNACIO MORILLAS ROSELL, GRUPO ZI */
package org.mps;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.*;
import org.mps.crossover.TwoPointCrossover;

public class TwoPointCrossoverTest {
    TwoPointCrossover crossoverOperator;
    
    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        crossoverOperator = new TwoPointCrossover();
    }

    @Test
    @DisplayName("Cuando el primer padre es nulo, lanza excepción")
    public void crossover_primerPadreNulo_lanzaExcepcion() {
        int [] parent1 = null;
        int [] parent2 = {1, 2, 3, 4, 5};

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            crossoverOperator.crossover(parent1, parent2);
        });
    }

    @Test
    @DisplayName("Cuando el segundo padre es nulo, lanza excepción")
    public void crossover_segundoPadreNulo_lanzaExcepcion() {
        int [] parent1 = {1, 2, 3, 4, 5};
        int [] parent2 = null;

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            crossoverOperator.crossover(parent1, parent2);
        });
    }

    @Test
    @DisplayName("Cuando los padres son de diferente tamaño, lanza excepción")
    public void crossover_padresDiferenteTamano_lanzaExcepcion() {
        int [] parent1 = {1, 2, 3, 4, 5};
        int [] parent2 = {1, 2, 3};

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            crossoverOperator.crossover(parent1, parent2);
        });
    }

    @Test
    @DisplayName("Cuando los padres son de tamaño 1, lanza excepción")
    public void crossover_padresTamanoUno_lanzaExcepcion() {
        int [] parent1 = {1};
        int [] parent2 = {2};

        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            crossoverOperator.crossover(parent1, parent2);
        });
    }

    @Test
    @DisplayName("Cuando los padres son válidos, devuelve dos descendientes modificados")
    public void crossover_padresValidos_devuelveDescendientes() throws EvolutionaryAlgorithmException {
        int [] parent1 = {1, 2, 3, 4, 5};
        int [] parent2 = {6, 7, 8, 9, 10};

        int[][] offspring = crossoverOperator.crossover(parent1, parent2);

        assertNotNull(offspring);
        assertEquals(2, offspring.length);
        assertEquals(parent1.length, offspring[0].length);
        assertEquals(parent1.length, offspring[1].length);
        
        assertFalse(Arrays.equals(parent1, offspring[0]));      // Los descendientes no deben ser iguales a los padres
        assertFalse(Arrays.equals(parent2, offspring[1]));
    }
}
