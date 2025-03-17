package clubdeportivo;
/*
 * Realizado por Ignacio Morillas Rosell
 * 
 * TESTS:
 * 1. Creación de un club de alto rendimiento con valores correctos
 * 2. Creación de un club de alto rendimiento con maximo incorrecto
 * 3. Creación de un club de alto rendimiento con incremento incorrecto
 * 4. Creación de un club de alto rendimiento con maximo incorrecto de tamaño recibido por parametro
 * 5. Creación de un club de alto rendimiento con incremento incorrecto  de tamaño recibido por parametro
 * 6. Creación de un club de alto rendimiento con nombre nulo
 * 
 * 7. Añadir actividad con datos incorrectos
 * 8. Añadir actividad con datos faltantes
 * 9. Añadir actividad con demasiadas plazas
 * 10. Añadir actividad con datos correctos
 * 
 * 11. Ingresos de un club sin actividades
 * 12. Ingresos de un club con varias actividades
 * 
 */

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ClubDeportivoAltoRendimientoTest {
    ClubDeportivoAltoRendimiento club;

    @BeforeEach
    void init() throws ClubException {
        club = new ClubDeportivoAltoRendimiento("ClubAR", 2, 10,20);
    }

//==============================================CREACION DE CLUB===================================================//
    @Test
    @DisplayName("Creación de un club de alto rendimiento con valores correctos")
    void clubDeportivoAltoRendimiento_creacionConValoresCorrectos_generaClub() throws ClubException {
        // Arrange
        String nombre = "ClubAR";
        int maximo = 10;
        double incremento = 20;
        String stringEsperado = nombre + " --> [  ]";
        // Act
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        // Assert
        assertEquals(stringEsperado, club.toString());
    }

    @Test
    @DisplayName("Creación de un club de alto rendimiento con maximo incorrecto")
    void clubDeportivoAltoRendimiento_creacionConMaximoIncorrecto_lanzaExcepcion() {
        // Arrange
        String nombre = "ClubAR";
        int maximo = 0;
        double incremento = 20;
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, maximo, incremento));
    }

    @Test
    @DisplayName("Creación de un club de alto rendimiento con incremento incorrecto")
    void clubDeportivoAltoRendimiento_creacionConIncrementoIncorrecto_lanzaExcepcion() {
        // Arrange
        String nombre = "ClubAR";
        int maximo = 10;
        double incremento = 0;
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, maximo, incremento));
    }

    @Test
    @DisplayName("Creación de un club de alto rendimiento con maximo incorrecto de tamaño recibido por parametro")
    void clubDeportivoAltoRendimientoCuatroParametros_creacionConMaximoIncorrecto_lanzaExcepcion() {
        // Arrange
        String nombre = "ClubAR";
        int grupos = 2;
        int maximo = 0;
        double incremento = 20;
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, grupos, maximo, incremento));
    }

    @Test
    @DisplayName("Creación de un club de alto rendimiento con incremento incorrecto  de tamaño recibido por parametro")
    void clubDeportivoAltoRendimientoCuatroParametros_creacionConIncrementoIncorrecto_lanzaExcepcion() {
        // Arrange
        String nombre = "ClubAR";
        int grupos = 2;
        int maximo = 10;
        double incremento = 0;
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, grupos, maximo, incremento));
    }

    @Test
    @DisplayName("Creación de un club de alto rendimiento con nombre nulo")
    void clubDeportivoAltoRendimiento_creacionConNombreNulo_lanzaExcepcion() {
        // Arrange
        String nombre = null;
        int maximo = 10;
        double incremento = 20;
        // Act & Assert
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento(nombre, maximo, incremento));
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
    @DisplayName("Añadir actividad con demasiadas plazas")
    void anyadirActividad_demasiadasPlazas_corrigeYCrea() {
        // Arrange
        String[] datos = {"123A", "Kizomba", "50", "10", "25"};  // 50 plazas
        String salidaEsperada = "ClubAR --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10) ]";   // Se corrige a crear una actividad con 10 plazas
        // Act & Assert
        assertDoesNotThrow(() -> club.anyadirActividad(datos));
        assertEquals(salidaEsperada, club.toString());
    }

    @Test
    @DisplayName("Añadir actividad con datos correctos")
    void anyadirActividad_datosCorrectos_noLanzaExcepcion() {
        // Arrange
        String[] datos = {"123A", "Kizomba", "10", "10", "25"};
        String salidaEsperada = "ClubAR --> [ (123A - Kizomba - 25.0 euros - P:10 - M:10) ]";
        // Act & Assert
        assertDoesNotThrow(() -> club.anyadirActividad(datos));
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
    @DisplayName("Ingresos de un club con varias actividades")
    void ingresos_clubConVariasActividades_devuelveIngresosCorrectos() throws ClubException {
        // Arrange
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 5, 50);
        double ingresosEsperados = (25 * 5 + 50 * 5) * 1.2;
        // Act
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        double ingresos = club.ingresos();
        // Assert
        assertEquals(ingresosEsperados, ingresos);
    }
}