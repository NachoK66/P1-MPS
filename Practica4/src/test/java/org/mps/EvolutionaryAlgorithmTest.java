/* REALIZADO POR IGNACIO MORILLAS ROSELL, GRUPO ZI */
package org.mps;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.*;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.selection.TournamentSelection;

public class EvolutionaryAlgorithmTest {
    EvolutionaryAlgorithm ea;
    TournamentSelection selectionOperator;
    GaussianMutation mutationOperator;
    TwoPointCrossover crossoverOperator;

    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        selectionOperator = new TournamentSelection(5);
        mutationOperator = new GaussianMutation(0.05, 0.15);
        crossoverOperator = new TwoPointCrossover();
        ea = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
    }

    @Nested
    @DisplayName("El constructor")
    class ConstructorTests {
        @Test
        @DisplayName("con parametros válidos funciona")
        public void evolutionaryAlgorithm_parametrosValidos_todoOk() throws EvolutionaryAlgorithmException {
            assertNotNull(ea);
        }

        @Nested
        @DisplayName("cuando tiene parámetros inválidos")
        class InvalidConstructorTests {
            @Test
            @DisplayName("con seleccion nula lanza excepción")
            public void evolutionaryAlgorithm_seleccionNula_lanzaExcepcion() {
                assertThrows(EvolutionaryAlgorithmException.class, () -> {
                    new EvolutionaryAlgorithm(null, mutationOperator, crossoverOperator);
                });
            }

            @Test
            @DisplayName("con mutación nula lanza excepción")
            public void evolutionaryAlgorithm_mutacionNula_lanzaExcepcion() {
                assertThrows(EvolutionaryAlgorithmException.class, () -> {
                    new EvolutionaryAlgorithm(selectionOperator, null, crossoverOperator);
                });
            }

            @Test
            @DisplayName("con cruce nulo lanza excepción")
            public void evolutionaryAlgorithm_cruceNulo_lanzaExcepcion() {
                assertThrows(EvolutionaryAlgorithmException.class, () -> {
                    new EvolutionaryAlgorithm(selectionOperator, mutationOperator, null);
                });
            }
        }
    }

    @Nested
    @DisplayName("El método optimize")
    class OptimizeTests {
        @Test
        @DisplayName("con población válida devuelve una población optimizada")
        public void optimize_poblacionValida_todoOk() throws EvolutionaryAlgorithmException {
            int[][] population = {
                    { 1, 2, 3, 4, 5, 6 }, // suma = 21
                    { 2, 3, 4, 5, 6, 7 }, // suma = 27
                    { 7, 6, 5, 4, 3, 2 }, // suma = 27
                    { 6, 5, 4, 3, 2, 1 } // suma = 21
            };
            int[][] initialPopulation = population.clone(); // Copia de la población inicial para verificar que ha
                                                            // cambiado

            ea.optimize(population);

            assertNotNull(population);
            assertEquals(population.length, initialPopulation.length);
            assertFalse(Arrays.equals(population, initialPopulation)); // Verifica que la población ha cambiado. No
                                                                       // siempre debe cambiar, pero en este ejemplo sí
        }

        @Test
        @DisplayName("con población de todo ceros devuelve la misma población")
        public void optimize_poblacionCeros_todoOk() throws EvolutionaryAlgorithmException {
            int[][] population = {
                    { 0, 0, 0, 0, 0, 0 }, // suma = 0
                    { 0, 0, 0, 0, 0, 0 } // suma = 0
            };
            int[][] initialPopulation = population.clone(); // Copia de la población inicial para verificar que ha
                                                            // cambiado

            ea.optimize(population);

            assertNotNull(initialPopulation);
            assertEquals(population.length, initialPopulation.length);
            assertArrayEquals(population, initialPopulation);
        }

        @Test
        @DisplayName("con población de diferente tamaño lanza excepción")
        public void optimize_poblacionDiferenteTamano_lanzaExcepcion() throws EvolutionaryAlgorithmException {
            int[][] population = {
                    { 1, 2, 3, 4, 5, 6, 2 }, // suma = 23
                    { 2, 3, 4, 5, 6, 7 }, // suma = 27
                    { 7, 6, 5, 4, 3 }, // suma = 25
            };

            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                ea.optimize(population);
            });
        }

        @Nested
        @DisplayName("tests extra para la cobertura de ramas obligatoria en better. De forma normal es imposible que todas las ramas del if se cumplan, se lanzan excepciones antes de llegar")
        class ExtraTests {
            @Test
            @DisplayName("con primera poblacion nula devuelve false")
            public void better_poblacionPrimeraNulaReflect_lanzaExcepcion() throws Exception {
                int[] population1 = null;
                int[] population2 = {5};
                Method method = ea.getClass().getDeclaredMethod("better", int[].class, int[].class);
                method.setAccessible(true); // Permitir el acceso a métodos privados
            
                assertFalse((Boolean) method.invoke(ea, population1, population2));
            }

            @Test
            @DisplayName("con segunda poblacion nula devuelve false")
            public void better_poblacionSegundaNulaReflect_lanzaExcepcion() throws Exception {
                int[] population1 = {1, 2, 3};
                int[] population2 = null;
                Method method = ea.getClass().getDeclaredMethod("better", int[].class, int[].class);
                method.setAccessible(true); // Permitir el acceso a métodos privados
            
                assertFalse((Boolean) method.invoke(ea, population1, population2));
            }

            @Test
            @DisplayName("con poblaciones de diferente tamaño devuelve false")
            public void better_poblacionesDiferenteTamano_lanzaExcepcion() throws Exception {
                int[] population1 = { 1, 2, 3 };
                int[] population2 = { 1, 1 };

                Method method = ea.getClass().getDeclaredMethod("better", int[].class, int[].class);
                method.setAccessible(true); // Permitir el acceso a métodos privados
            
                assertFalse((Boolean) method.invoke(ea, population1, population2));
            }
        }

        @Test
        @DisplayName("con población nula lanza excepción")
        public void optimize_poblacionNula_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                ea.optimize(null);
            });
        }

        @Test
        @DisplayName("con población vacía lanza excepción")
        public void optimize_poblacionVacia_lanzaExcepcion() {
            assertThrows(EvolutionaryAlgorithmException.class, () -> {
                ea.optimize(new int[0][]);
            });
        }
    }
}
