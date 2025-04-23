/* REALIZADO POR IGNACIO MORILLAS ROSELL, GRUPO ZI */
package org.mps;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.mps.selection.TournamentSelection;

public class TournamentSelectionTest {
    TournamentSelection tournamentSelection;
    
    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        tournamentSelection = new TournamentSelection(3); // Tamaño del torneo de 3
    }

    @Nested
    @DisplayName("En el constructor")
    class Constructor {
        @Test
        @DisplayName("Hacer un torneo de tamaño 0 o menos lanza una excepción")
        public void tournamentSelection_tamanyoCero_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                new TournamentSelection(0);
            });
        }
    
        @Test
        @DisplayName("Hacer un torneo de tamaño 1 o mayor funciona correctamente")
        public void tournamentSelection_tamanyoUnoOMayor_funcionaCorrectamente() throws EvolutionaryAlgorithmException {
            int size = 1;
    
            TournamentSelection ts = new TournamentSelection(size);
            
            assertNotNull(ts);
        }
    }

    @Nested
    @DisplayName("En el método select")
    class SelectTests {
        @Test
        @DisplayName("una población nula lanza excepción")
        public void select_poblacionNula_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(null);
            });
        }

        @Test
        @DisplayName("una población vacía lanza excepción")
        public void select_poblacionVacia_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(new int[0]);
            });
        }

        @Test
        @DisplayName("una población de tamaño menor al torneo lanza excepción")
        public void select_poblacionMenorAlTorneo_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                tournamentSelection.select(new int[2]);
            });
        }

        @Test
        @DisplayName("una población de tamaño mayor al torneo funciona correctamente")
        public void select_poblacionMayorAlTorneo_funcionaCorrectamente() throws EvolutionaryAlgorithmException {
            int[] population = {5, 3, 8, 1, 4};
            int[] selected;
            
            selected = tournamentSelection.select(population);
            
            assertNotNull(selected);
            assertEquals(population.length, selected.length);
        }
    }


}
