package clubdeportivo;

/* Realizado por Ignacio Morillas Rosell 
 * 
 * TESTS:
 * 1. Crear grupo con valores correctos
 * 2. Crear grupo con actividad nula
 * 3. Crear grupo con actividad vacía
 * 4. Crear grupo con nplazas negativo o 0
 * 5. Crear grupo con nmatriculados negativo
 * 6. Crear grupo con tarifa negativa o 0
 * 
 * 7. Obtener código de grupo
 * 
 * 8. Obtener actividad de grupo
 * 
 * 9. Obtener número de plazas de grupo
 * 
 * 10. Obtener número de matriculados de grupo
 * 
 * 11. Obtener tarifa de grupo
 * 
 * 12. Obtener plazas libres de grupo
 * 
 * 13. Actualizar plazas de grupo con valor correcto
 * 14. Actualizar plazas de grupo con valor negativo
 * 15. Actualizar plazas de grupo con valor menor que nmatriculados
 * 
 * 16. Matricular en grupo con plazas libres
 * 17. Matricular en grupo con plazas libres insuficientes
 * 18. Matricular en grupo con n negativo
 * 
 * 19. Convertir grupo a cadena
 * 
 * 20. Comparar grupo con otro grupo de mismo codigo y actividad
 * 21. Comparar grupo con otro grupo de mismo codigo y distinta actividad
 * 22. Comparar grupo con otro grupo de distinto codigo y misma actividad
 * 23. Comparar grupo con otro grupo de distinto codigo y actividad
 * 
*/

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class GrupoTest {
    Grupo grupo;

    @BeforeEach
    void init() throws ClubException {
        grupo = new Grupo("456B","Pilates",8,5,50.0);
    }

//================================================= GRUPO ===================================================
    @Test
    @DisplayName("Test 1. Crear grupo con valores correctos")
    void grupo_valoresCorrectos_creaGrupo() throws ClubException {
        // Arrange & Act
        Grupo grupo = new Grupo("456B","    Pilates    ",8,5,50.0);
        // Assert
        assertEquals("456B", grupo.getCodigo());
        assertEquals("Pilates", grupo.getActividad());
        assertEquals(8, grupo.getPlazas());
        assertEquals(5, grupo.getMatriculados());
        assertEquals(50.0, grupo.getTarifa());
    }

    @Test
    @DisplayName("Test 2. Crear grupo con actividad nula")
    void grupo_actividadNula_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", null, 8, 5, 50.0));
    }

    @Test
    @DisplayName("Test 2. Crear grupo con actividad vacía")
    void grupo_actividadVacia_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", "      ", 8, 5, 50.0));
    }

    @Test
    @DisplayName("Test 3. Crear grupo con nplazas negativo o 0")
    void grupo_nplazasNegativoOCero_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", "Pilates", 0, 5, 50.0));
    }

    @Test
    @DisplayName("Test 4. Crear grupo con nmatriculados negativo")
    void grupo_nmatriculadosNegativo_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", "Pilates", 8, -3, 50.0));
    }

    @Test
    @DisplayName("Test 5. Crear grupo con tarifa negativa o 0")
    void grupo_tarifaNegativaOCero_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", "Pilates", 8, 5, -50.0));
    }

    @Test
    @DisplayName("Test 6. Crear grupo con nmatriculados mayor que nplazas")
    void grupo_nmatriculadosMayorQueNplazas_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> new Grupo("456B", "Pilates", 8, 10, 50.0));
    }

//================================================= GET CODIGO ===================================================
    @Test
    @DisplayName("Test 7. Obtener código de grupo")
    void get_codigo_obtieneCodigo() throws ClubException {
        // Arrange & Act & Act
        String codigo = grupo.getCodigo();
        // Assert
        assertEquals("456B", codigo);
    }

//================================================= GET ACTIVIDAD ===================================================
    @Test
    @DisplayName("Test 8. Obtener actividad de grupo")
    void get_actividad_obtieneActividad() throws ClubException {
        // Arrange & Act & Act
        String actividad = grupo.getActividad();
        // Assert
        assertEquals("Pilates", actividad);
    }

//================================================= GET PLAZAS ===================================================
    @Test
    @DisplayName("Test 9. Obtener número de plazas de grupo")
    void get_plazas_obtienePlazas() throws ClubException {
        // Arrange & Act
        int plazas = grupo.getPlazas();
        // Assert
        assertEquals(8, plazas);
    }

//================================================= GET MATRICULADOS ===================================================
    @Test
    @DisplayName("Test 10. Obtener número de matriculados de grupo")
    void get_matriculados_obtieneMatriculados() throws ClubException {
        // Arrange & Act
        int matriculados = grupo.getMatriculados();
        // Assert
        assertEquals(5, matriculados);
    }

//================================================= GET TARIFA ===================================================
    @Test
    @DisplayName("Test 11. Obtener tarifa de grupo")
    void get_tarifa_obtieneTarifa() throws ClubException {
        // Arrange & Act
        double tarifa = grupo.getTarifa();
        // Assert
        assertEquals(50.0, tarifa);
    }

//================================================= PLAZAS LIBRES ===================================================
    @Test
    @DisplayName("Test 12. Obtener plazas libres de grupo")
    void plazasLibres_obtienePlazasLibres() throws ClubException {
        // Arrange & Act
        int plazasLibresEsperadas = grupo.getPlazas() - grupo.getMatriculados();
        int plazasLibres = grupo.plazasLibres();
        // Assert
        assertEquals(plazasLibresEsperadas, plazasLibres);
    }

//================================================= ACTUALIZAR PLAZAS ===================================================

    @Test
    @DisplayName("Test 13. Actualizar plazas de grupo con valor correcto")
    void actualizarPlazas_valorCorrecto_actualizaPlazas() throws ClubException {
        // Arrange & Act
        grupo.actualizarPlazas(10);         // Previamente 8 plazas
        // Assert
        assertEquals(10, grupo.getPlazas());
    }

    @Test
    @DisplayName("Test 14. Actualizar plazas de grupo con valor negativo")
    void actualizarPlazas_valorNegativo_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> grupo.actualizarPlazas(-10));
    }

    @Test
    @DisplayName("Test 15. Actualizar plazas de grupo con valor menor que nmatriculados")
    void actualizarPlazas_valorMenorQueNmatriculados_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> grupo.actualizarPlazas(4));
    }

//================================================= MATRICULAR ===================================================
    @Test
    @DisplayName("Test 16. Matricular en grupo con plazas libres")
    void matricular_plazasLibres_matricula() throws ClubException {
        // Arrange & Act
        grupo.matricular(3);        // Previamente 5 matriculados
        // Assert
        assertEquals(8, grupo.getMatriculados());
    }

    @Test
    @DisplayName("Test 17. Matricular en grupo con plazas libres insuficientes")
    void matricular_plazasLibresInsuficientes_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> grupo.matricular(4));           // Previamente 5 matriculados, 8 plazas totales, 5+4=9 > 8
    }

    @Test
    @DisplayName("Test 18. Matricular en grupo con n negativo")
    void matricular_nNegativo_lanzaExcepcion() throws ClubException {
        // Arrange & Act & Assert
        assertThrows(ClubException.class, () -> grupo.matricular(-4));
    }

//================================================= TO STRING ===================================================
    @Test
    @DisplayName("Test 19. Convertir grupo a cadena")
    void toString_convierteGrupoACadena_devuelveCadena() throws ClubException {
        // Arrange
        String codigo = grupo.getCodigo();
        String actividad = grupo.getActividad();
        int nplazas = grupo.getPlazas();
        int nmatriculados = grupo.getMatriculados();
        double tarifa = grupo.getTarifa();
        String cadenaEsperada = "(" + codigo + " - " + actividad + " - " + tarifa + " euros " + "- P:" + nplazas + " - M:" + nmatriculados + ")";
        // Act
        String cadenaRecibida = grupo.toString();
        // Assert
        assertEquals(cadenaEsperada, cadenaRecibida);
    }

//================================================= EQUALS ===================================================
    @Test
    @DisplayName("Test 20. Comparar grupo con otro grupo de mismo codigo y actividad")
    void equals_grupoIgualCodigoYActividad_devuelveTrue() throws ClubException {
        // Arrange
        Grupo otroGrupo = new Grupo("456B", "Pilates", 15, 5, 20.0);
        // Act
        boolean iguales = grupo.equals(otroGrupo);
        // Assert
        assertTrue(iguales);
    }

    @Test
    @DisplayName("Test 21. Comparar grupo con otro grupo de mismo codigo y distinta actividad")
    void equals_grupoIgualCodigoYDistintaActividad_devuelveFalse() throws ClubException {
        // Arrange
        Grupo otroGrupo = new Grupo("456B", "Pilatos", 18, 5, 30.0);
        // Act
        boolean iguales = grupo.equals(otroGrupo);
        // Assert
        assertFalse(iguales);
    }

    @Test
    @DisplayName("Test 22. Comparar grupo con otro grupo de distinto codigo y misma actividad")
    void equals_grupoDistintoCodigoYMismaActividad_devuelveFalse() throws ClubException {
        // Arrange
        Grupo otroGrupo = new Grupo("456C", "Pilates", 18, 5, 30.0);
        // Act
        boolean iguales = grupo.equals(otroGrupo);
        // Assert
        assertFalse(iguales);
    }

    @Test
    @DisplayName("Test 23. Comparar grupo con otro grupo de distinto codigo y actividad")
    void equals_grupoDistintoCodigoYActividad_devuelveFalse() throws ClubException {
        // Arrange
        Grupo otroGrupo = new Grupo("456C", "Pilatos", 18, 5, 30.0);
        // Act
        boolean iguales = grupo.equals(otroGrupo);
        // Assert
        assertFalse(iguales);
    }

    @Test
    @DisplayName("Test 24. Comparar grupo con otro objeto")
    void equals_otroObjeto_devuelveFalse() throws ClubException {
        // Arrange
        Object otroObjeto = new Object();
        // Act
        boolean iguales = grupo.equals(otroObjeto);
        // Assert
        assertFalse(iguales);
    }

//================================================= HASH CODE ===================================================
    @Test
    @DisplayName("Test 25. Obtener hash code de grupo")
    void hashCode_obtieneHashCode() throws ClubException {
        // Arrange
        int hashEsperado = grupo.getCodigo().toUpperCase().hashCode() + grupo.getActividad().toUpperCase().hashCode();
        // Act
        int hashRecibido = grupo.hashCode();
        // Assert
        assertEquals(hashEsperado, hashRecibido);
    }
}