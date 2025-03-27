package clubdeportivo;
/*
 * Realizado por Ignacio Morillas Rosell
 * 
 * TESTS:
 * 1. Creación de un club con nombre vacío
 * 2. Creación de un club con nombre nulo
 * 3. Creación de un club con nombre solo espacios
 * 4. Creación de un club con nombre correcto
 * 5. Creación de un club con número de grupos negativo
 * 6. Creación de un club con número de grupos 0
 * 7. Creación de un club con número de grupos correcto
 * 
 * 8. Añadir actividad con datos incorrectos
 * 9. Añadir actividad con datos faltantes
 * 10. Añadir actividad con datos correctos
 * 11. Añadir actividad con grupo nulo
 * 12. Añadir actividad con grupo correcto
 * 13. Añadir actividad con grupo ya existente
 * 14. Añadir actividad con grupo ya existente y diferente número de plazas
 * 15. Añadir actividad con club lleno
 * 
 * 16. Plazas libres de una actividad que no existe en el club
 * 17. Plazas libres de una actividad que existe en el club
 * 18. Plazas libres de una actividad que existe en el club en varios grupos
 * 
 * 19. Matricular en una actividad que no existe en el club
 * 20. Matricular en una actividad que existe en el club con plazas libres
 * 21. Matricular en una actividad que existe en el club sin plazas libres
 * 22. Matricular en una actividad que existe en el club con plazas libres en varios grupos
 * 23. Matricular en una actividad que existe en el club con plazas libres en varios grupos
 * 
 * 24. Ingresos de un club sin actividades
 * 25. Ingresos de un club con una actividad
 * 26. Ingresos de un club con varias actividades
 * 27. Ingresos de un club con varias actividades y sin matriculados
 * 28. Ingresos de un club con varias actividades tras nuevas matriculas
 * 
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ClubDeportivoTest {
    ClubDeportivo club;

    @BeforeEach
    void init() throws ClubException {
        club = new ClubDeportivo("Club", 2);
    }

//==============================================CREACION DE CLUB===================================================//
    @Test
    @DisplayName("Creación de un club con nombre vacío")
    void clubDeportivo_crearConNombreVacio_lanzaExcepcion() {
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivo(""));
    }

    @Test
    @DisplayName("Creación de un club con nombre nulo")
    void clubDeportivo_crearConNombreNulo_lanzaExcepcion() {
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivo(null));
    }

    @Test
    @DisplayName("Creación de un club con nombre solo espacios")
    void clubDeportivo_crearConNombreSoloEspacios_lanzaExcepcion() {
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivo("   "));
    }

    @Test
    @DisplayName("Creación de un club con nombre correcto")
    void clubDeportivo_crearConNombreCorrecto_noLanzaExcepcion() {
        // Act & Assert
        assertDoesNotThrow(() -> new ClubDeportivo("Club"));
    }

    @Test
    @DisplayName("Creación de un club con número de grupos negativo")
    void clubDeportivo_crearConNumeroGruposNegativo_lanzaExcepcion() {
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivo("Club", -1));
    }

    @Test
    @DisplayName("Creación de un club con número de grupos 0")
    void clubDeportivo_crearConNumeroGruposCero_lanzaExcepcion() {
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivo("Club", 0));
    }

    @Test
    @DisplayName("Creación de un club con número de grupos correcto")
    void clubDeportivo_crearConNumeroGruposCorrecto_noLanzaExcepcion() {
        // Act & Assert
        assertDoesNotThrow(() -> new ClubDeportivo("Club", 2));
    }
    
//============================================== AÑADIR ACTIVIDAD ===================================================//
    @Test
    @DisplayName("Añadir actividad con datos incorrectos")
    void anyadirActividad_datosIncorrectos_lanzaExcepcion() {
        // Arrange
        String[] datos = {"123A", "Kizomba", "a", "10.5", "25"};
        // Act & Assert
        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Añadir actividad con datos faltantes")
    void anyadirActividad_datosFaltantes_lanzaExcepcion() {
        // Arrange
        String[] datos = {"123A", "Kizomba", "10", "10"};
        // Act & Assert
        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }

    @Test
    @DisplayName("Añadir actividad con datos correctos")
    void anyadirActividad_datosCorrectos_noLanzaExcepcion() {
        // Arrange
        String[] datos = {"123A", "Kizomba", "10", "10", "25"};
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10) ]";
        // Act & Assert
        assertDoesNotThrow(() -> club.anyadirActividad(datos));
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Añadir actividad con grupo nulo")
    void anyadirActividad_grupoNulo_lanzaExcepcion() {
        // Arrange
        Grupo grupo = null;
        // Act & Assert
        assertThrows(ClubException.class, () -> club.anyadirActividad(grupo));
    }

    @Test
    @DisplayName("Añadir actividad con grupo correcto")
    void anyadirActividad_grupoCorrecto_noLanzaExcepcion() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        // Act & Assert
        assertDoesNotThrow(() -> club.anyadirActividad(grupo));
    }

    @Test
    @DisplayName("Añadir actividad con grupo ya existente")
    void anyadirActividad_grupoExistente_noLanzaExcepcionNiRepiteGrupo() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10) ]";
        // Act
        club.anyadirActividad(grupo);
        // Assert
        assertDoesNotThrow(() -> club.anyadirActividad(grupo));
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Añadir actividad con grupo ya existente y diferente número de plazas")
    void anyadirActividad_grupoExistenteDistintosDatos_actualizaDatosGrupo() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        Grupo grupo2 = new Grupo("123A", "Kizomba", 15, 10, 25);
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:15 - M:10) ]"; // Se actualizan las plazas para el grupo de mimso código y actividad
        // Act
        club.anyadirActividad(grupo);
        // Assert
        assertDoesNotThrow(() -> club.anyadirActividad(grupo2));
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Añadir actividad con club lleno")
    void anyadirActividad_clubLleno_lanzaExcepcion() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 5, 50);
        Grupo grupo3 = new Grupo("789C", "Zumba", 12, 10, 30);
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        // Assert
        assertThrows(ClubException.class, () -> club.anyadirActividad(grupo3)); // Recordar que el club tiene un tamaño maximo de 2 grupos
    }

//============================================== PLAZAS LIBRES ===================================================//
    @Test
    @DisplayName("Plazas libres de una actividad que no existe en el club")
    void plazasLibres_actividadNoExistente_devuelveCero() {
        // Arrange
        String actividad = "Kizomba";
        // Act
        int plazasLibres = club.plazasLibres(actividad);
        // Assert
        assertEquals(0, plazasLibres);
    }

    @Test
    @DisplayName("Plazas libres de una actividad que existe en el club")
    void plazasLibres_actividadExistenteLlena_devuelveCero() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        String actividad = "Kizomba";
        // Act
        club.anyadirActividad(grupo);
        int plazasLibres = club.plazasLibres(actividad);
        // Assert
        assertEquals(0, plazasLibres);
    }

    @Test
    @DisplayName("Plazas libres de una actividad que existe en el club")
    void plazasLibres_actividadExistenteNoLlena_devuelvePlazasLibres() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        String actividad = "Kizomba";
        // Act
        club.anyadirActividad(grupo);
        int plazasLibres = club.plazasLibres(actividad);
        // Assert
        assertEquals(5, plazasLibres);
    }

    @Test
    @DisplayName("Plazas libres de una actividad que existe en el club en varios grupos")
    void plazasLibres_actividadExistenteVariosGrupos_devuelvePlazasLibres() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 5, 5, 25);
        Grupo grupo2 = new Grupo("456B", "Kizomba", 15, 10, 25);
        String actividad = "Kizomba";
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        int plazasLibres = club.plazasLibres(actividad);
        // Assert
        assertEquals(5, plazasLibres);
    }

//============================================== MATRICULAR ===================================================//
    @Test
    @DisplayName("Matricular en una actividad que no existe en el club")
    void matricular_actividadNoExistente_lanzaExcepcion() {
        // Arrange
        String actividad = "Kizomba";
        int personas = 5;
        // Act & Assert
        assertThrows(ClubException.class, () -> club.matricular(actividad, personas));
    }

    @Test
    @DisplayName("Matricular en una actividad que existe en el club con plazas libres")
    void matricular_actividadExistentePlazasLibres_matriculaCorrectamente() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        String actividad = "Kizomba";
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10) ]";
        int personas = 5;
        // Act
        club.anyadirActividad(grupo);
        club.matricular(actividad, personas);
        // Assert
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Matricular en una actividad que existe en el club sin plazas libres")
    void matricular_actividadExistenteSinPlazasLibres_lanzaExcepcion() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25);
        String actividad = "Kizomba";
        int personas = 5;
        // Act
        club.anyadirActividad(grupo);
        // Assert
        assertThrows(ClubException.class, () -> club.matricular(actividad, personas));
    }

    @Test
    @DisplayName("Matricular en una actividad que existe en el club con plazas libres en un grupo")
    void matricular_actividadExistenteUnGrupoLibre_matriculaCorrectamente() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 5, 5, 25);
        Grupo grupo2 = new Grupo("456B", "Kizomba", 15, 10, 25);
        String actividad = "Kizomba";
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:5 - M:5), (456B - Kizomba - 25.0 euros - P:15 - M:15) ]";
        int personas = 5;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        club.matricular(actividad, personas);
        // Assert
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Matricular en una actividad que existe en el club con plazas libres en varios grupos")
    void matricular_actividadExistenteVariosGruposLibres_matriculaCorrectamente() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 2, 25);
        Grupo grupo2 = new Grupo("456B", "Kizomba", 15, 10, 25);
        String actividad = "Kizomba";
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:10 - M:8), (456B - Kizomba - 25.0 euros - P:15 - M:10) ]";
        int personas = 6;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        club.matricular(actividad, personas);
        // Assert
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Matricular en una actividad que existe en el club con plazas libres en varios grupos repartidos")
    void matricular_actividadExistenteVariosGruposLibresRepartidos_matriculaCorrectamente() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 2, 25);
        Grupo grupo2 = new Grupo("456B", "Kizomba", 15, 10, 25);
        String actividad = "Kizomba";
        String salidaEsperada = "Club --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10), (456B - Kizomba - 25.0 euros - P:15 - M:14) ]";
        int personas = 12;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        club.matricular(actividad, personas);
        // Assert
        assertEquals(salidaEsperada, club.toString());
    }

//============================================== INGRESOS ===================================================//

    @Test
    @DisplayName("Ingresos de un club sin actividades")
    void ingresos_clubSinActividades_devuelveCero() {
        // Act
        double ingresos = club.ingresos();
        // Assert
        assertEquals(0, ingresos);
    }

    @Test
    @DisplayName("Ingresos de un club con una actividad")
    void ingresos_clubConUnaActividad_devuelveIngresosCorrectos() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        double ingresosEsperados = 25 * 5;
        // Act
        club.anyadirActividad(grupo);
        double ingresos = club.ingresos();
        // Assert
        assertEquals(ingresosEsperados, ingresos);
    }

    @Test
    @DisplayName("Ingresos de un club con varias actividades")
    void ingresos_clubConVariasActividades_devuelveIngresosCorrectos() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 5, 50);
        double ingresosEsperados = 25 * 5 + 50 * 5;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        double ingresos = club.ingresos();
        // Assert
        assertEquals(ingresosEsperados, ingresos);
    }

    @Test
    @DisplayName("Ingresos de un club con varias actividades y sin matriculados")
    void ingresos_clubConVariasActividadesSinMatriculados_devuelveCero() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 0, 25);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 0, 50);
        double ingresosEsperados = 0;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        double ingresos = club.ingresos();
        // Assert
        assertEquals(ingresosEsperados, ingresos);
    }

    @Test
    @DisplayName("Ingresos de un club con varias actividades tras nuevas matriculas")
    void ingresos_clubConVariasActividadesConMatriculadosNuevos_devuelveIngresosCorrectos() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 0, 50);
        double ingresosEsperados = 25 * 5 + 50 * 3;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        club.matricular("Pilates", 3);
        double ingresos = club.ingresos();
        // Assert
        assertEquals(ingresosEsperados, ingresos);
    }
}