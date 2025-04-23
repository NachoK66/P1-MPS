/* REALIZADO POR IGNACIO MORILLAS ROSELL, GRUPO ZI */
package org.mps;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mps.mutation.GaussianMutation;

public class GaussianMutationTest {
    GaussianMutation gaussianMutation;

    @BeforeEach
    public void setUp() throws Exception {
        gaussianMutation = new GaussianMutation(0.5, 1.0);
    }

    @Test
    @DisplayName("Al pasar un array nulo, se lanza una excepción")
    public void mutate_arrayNulo_lanzaExcepcion() {
        int[] individual = null;

        Assertions.assertThrows(EvolutionaryAlgorithmException.class, () -> {
            gaussianMutation.mutate(individual);
        });
    }

    @Test
    @DisplayName("Al pasar un array vacío, se lanza una excepción")
    public void mutate_arrayVacio_lanzaExcepcion() {
        int[] individual = new int[0];

        Assertions.assertThrows(EvolutionaryAlgorithmException.class, () -> {
            gaussianMutation.mutate(individual);
        });
    }

    @Test
    @DisplayName("Al pasar un array con al menos un elemento, funciona correctamente")
    public void mutate_arrayConElementos_todoOk() throws EvolutionaryAlgorithmException {
        int[] individual = {1, 2, 3, 4, 5};
        int[] mutatedIndividual = gaussianMutation.mutate(individual);

        assertNotNull(mutatedIndividual);
        assertEquals(individual.length, mutatedIndividual.length);
    }

}
