/* Realizado por Ignacio Morillas Rosell, Grupo ZI */
package org.mps.ronqi2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.mockito.Mockito.*;
import org.mockito.stubbing.Answer;
import org.mockito.invocation.InvocationOnMock;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.mps.dispositivo.Dispositivo;

public class ronQI2SilverTest {
    RonQI2Silver ronQI2Silver;
    Dispositivo dispositivoMock;

    @BeforeEach
    public void setUp() {
        ronQI2Silver = new RonQI2Silver();
        dispositivoMock = mock(Dispositivo.class);
        ronQI2Silver.anyadirDispositivo(dispositivoMock);
    }

    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar
     * que al inicializar funciona como debe ser.
     * El funcionamiento correcto es que si es posible conectar ambos sensores y
     * configurarlos,
     * el método inicializar de ronQI2 o sus subclases,
     * debería devolver true. En cualquier otro caso false. Se deja programado un
     * ejemplo.
     */
    @Nested
    @DisplayName("al inicializar")
    class InicializarTest {

        @Test
        @DisplayName("si el dispositivo es nulo, devuelve false")
        void inicializar_dispositivoNulo_devuelveFalse() {
            ronQI2Silver.anyadirDispositivo(null);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si se pueden conectar y configurar ambos sensores, devuelve true")
        void inicializar_sensoresConectadosYConfigurables_devuelveTrue() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertTrue(result);
        }

        @Test
        @DisplayName("si falla al conectar un sensor, devuelve false")
        void inicializar_sensorFalla_devuelveFalse() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(false);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si falla al conectar ambos sensores, devuelve false")
        void inicializar_sensoresDesconectados_devuelveFalse() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(false);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(false);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si falla al configurar un sensor, devuelve false")
        void inicializar_sensorNoConfigurado_devuelveFalse() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(false);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si falla al configurar ambos sensores, devuelve false")
        void inicializar_sensoresNoConfigurados_devuelveFalse() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(false);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(false);

            boolean result = ronQI2Silver.inicializar();

            Assertions.assertFalse(result);
        }

        /*
         * Un inicializar debe configurar ambos sensores, comprueba que cuando se
         * inicializa de forma correcta (el conectar es true),
         * se llama una sola vez al configurar de cada sensor.
         */
        @Test
        @DisplayName("si se inicializa correctamente, llama a configurar una vez por sensor")
        void inicializar_sensoresCorrectos_llamaConfigurarUnaVezPorSensor() {
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
            when(dispositivoMock.configurarSensorSonido()).thenReturn(true);

            ronQI2Silver.inicializar();

            verify(dispositivoMock, times(1)).configurarSensorPresion();
            verify(dispositivoMock, times(1)).configurarSensorSonido();
        }
    }

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta
     * ambos y devuelve true si ambos han sido conectados.
     * Genera las pruebas que estimes oportunas para comprobar su correcto
     * funcionamiento.
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que
     * deben ser llamados.
     */
    @Nested
    @DisplayName("al reconectar")
    class ReconectarTest {

        @Test
        @DisplayName("si el dispositivo es nulo, devuelve false")
        void reconectar_dispositivoNulo_devuelveFalse() {
            ronQI2Silver.anyadirDispositivo(null);

            boolean result = ronQI2Silver.reconectar();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si el dispositivo está desconectado, conecta ambos sensores y devuelve true")
        void reconectar_dispositivoDesconectado_conectaAmbosSensores_devuelveTrue() {
            when(dispositivoMock.estaConectado()).thenReturn(false);
            when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
            when(dispositivoMock.conectarSensorSonido()).thenReturn(true);

            boolean result = ronQI2Silver.reconectar();

            Assertions.assertTrue(result);
            verify(dispositivoMock, times(1)).estaConectado();
            verify(dispositivoMock, times(1)).conectarSensorPresion();
            verify(dispositivoMock, times(1)).conectarSensorSonido();
        }

        @Test
        @DisplayName("si el dispositivo está conectado, no conecta nada y devuelve false")
        void reconectar_dispositivoConectado_noConectaNada_devuelveFalse() {
            when(dispositivoMock.estaConectado()).thenReturn(true);

            boolean result = ronQI2Silver.reconectar();

            Assertions.assertFalse(result);
            verify(dispositivoMock, times(1)).estaConectado();
            verify(dispositivoMock, never()).conectarSensorPresion();
            verify(dispositivoMock, never()).conectarSensorSonido();
        }
    }

    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con
     * obtenerNuevaLectura(),
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP
     * = 20.0f y thresholdS = 30.0f;,
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también
     * debería realizar la media.
     */
    @Nested
    @DisplayName("al evaluar apnea")
    class EvaluarApneaTest {

        @Test
        @DisplayName("si el dispositivo es nulo, devuelve false")
        void evaluarApnea_dispositivoNulo_devuelveFalse() {
            ronQI2Silver.anyadirDispositivo(null);
            for (int i = 0; i < 5; i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertFalse(result);
        }

        @Test
        @DisplayName("si no hay lecturas, devuelve false")
        void evaluarApnea_noHayLecturas_devuelveFalse() {

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertFalse(result);
        }

        @ParameterizedTest
        @MethodSource("org.mps.ronqi2.ronQI2SilverTest#valoresSobreLimites")
        @DisplayName("si el promedio de las lecturas de presión y sonido es mayor a los límites establecidos, devuelve true")
        void evaluarApnea_promedioMayorAUmbrales_devuelveTrue(List<Float> presion, List<Float> sonido) {
            when(dispositivoMock.leerSensorPresion()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return presion.get(index++).floatValue();
                }
            });
            when(dispositivoMock.leerSensorSonido()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return sonido.get(index++).floatValue();
                }
            });
            for (int i = 0; i < presion.size(); i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertTrue(result);
        }

        @ParameterizedTest
        @MethodSource("org.mps.ronqi2.ronQI2SilverTest#valoresEnLimites")
        @DisplayName("si el promedio de las lecturas de presión y sonido es igual a los límites establecidos, devuelve true")
        void evaluarApnea_promedioIgualAUmbrales_devuelveTrue(List<Float> presion, List<Float> sonido) {
            when(dispositivoMock.leerSensorPresion()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return presion.get(index++).floatValue();
                }
            });
            when(dispositivoMock.leerSensorSonido()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return sonido.get(index++).floatValue();
                }
            });
            for (int i = 0; i < presion.size(); i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertTrue(result);
        }

        @ParameterizedTest
        @MethodSource("org.mps.ronqi2.ronQI2SilverTest#valoresDeUnoEnOSobreLimites")
        @DisplayName("si sólo el promedio de las lecturas de un sensor es igual o mayor a los límites establecidos, devuelve false")
        void evaluarApnea_promedioUnoMayorAUmbrales_devuelveFalse(List<Float> presion, List<Float> sonido) {
            when(dispositivoMock.leerSensorPresion()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return presion.get(index++).floatValue();
                }
            });
            when(dispositivoMock.leerSensorSonido()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return sonido.get(index++).floatValue();
                }
            });
            for (int i = 0; i < presion.size(); i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertFalse(result);
        }

        @ParameterizedTest
        @MethodSource("org.mps.ronqi2.ronQI2SilverTest#valoresBajoLimites")
        @DisplayName("si el promedio de las lecturas de presión y sonido es menor a los límites establecidos, devuelve false")
        void evaluarApnea_promedioMenorAUmbrales_devuelveFalse(List<Float> presion, List<Float> sonido) {
            when(dispositivoMock.leerSensorPresion()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return presion.get(index++).floatValue();
                }
            });
            when(dispositivoMock.leerSensorSonido()).thenAnswer(new Answer<Float>() {
                int index = 0;

                public Float answer(InvocationOnMock invocation) {
                    return sonido.get(index++).floatValue();
                }
            });
            for (int i = 0; i < presion.size(); i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();

            Assertions.assertFalse(result);
        }

    }
    
/* ============================== Generadores para los tests parametrizados ======================================*/

    static Stream<Arguments> valoresSobreLimites() {
        return Stream.of(
                Arguments.of(
                        List.of(25.0f, 20.0f, 19.0f),
                        List.of(35.0f, 32.0f, 29.0f)),
                Arguments.of(
                        List.of(25.0f, 23.0f, 21.0f, 20.0f, 19.0f),
                        List.of(35.0f, 28.0f, 30.0f, 34.0f, 29.0f)),
                Arguments.of(
                        new Random().doubles(10, 20.1f, 25.0f)
                                .boxed()
                                .map(Double::floatValue)
                                .toList(),
                        new Random().doubles(10, 30.1f, 40.0f)
                                .boxed()
                                .map(Double::floatValue)
                                .toList())
        );
    }

    static Stream<Arguments> valoresEnLimites() {
        return Stream.of(
                Arguments.of(
                        List.of(19.0f, 20.0f, 21.0f),
                        List.of(30.0f, 29.0f, 31.0f)),
                Arguments.of(
                        List.of(20.0f, 18.0f, 20.0f, 22.0f, 20.0f),
                        List.of(30.0f, 30.0f, 30.0f, 30.0f, 30.0f)),
                Arguments.of(
                        List.of(14.0f, 15.0f, 20.0f, 20.0f, 20.0f, 20.0f, 21.0f, 20.0f, 19.0f, 20.0f),
                        List.of(28.0f, 29.0f, 30.0f, 30.0f, 30.0f, 30.0f, 31.0f, 30.0f, 29.0f, 30.0f))
        );
    }

    static Stream<Arguments> valoresDeUnoEnOSobreLimites() {
        return Stream.of(
                Arguments.of(
                        List.of(20.0f, 20.0f, 19.0f),
                        List.of(35.0f, 28.0f, 29.0f)),
                Arguments.of(
                        List.of(20.0f, 20.0f, 21.0f, 20.0f, 18.0f),
                        List.of(35.0f, 28.0f, 30.0f, 34.0f, 29.0f)),
                Arguments.of(
                        List.of(14.0f, 15.0f, 20.0f, 20.0f, 20.0f, 20.0f, 21.0f, 20.0f, 15.0f, 20.0f),
                        List.of(28.0f, 29.0f, 30.0f, 30.0f, 30.0f, 30.0f, 31.0f, 30.0f, 31.0f, 30.0f))
        );
    }

    static Stream<Arguments> valoresBajoLimites() {
        return Stream.of(
                Arguments.of(
                        List.of(20.0f, 20.0f, 19.0f),
                        List.of(30.0f, 31.0f, 25.0f)),
                Arguments.of(
                        List.of(20.0f, 23.0f, 18.0f, 20.0f, 16.0f),
                        List.of(30.0f, 28.0f, 30.0f, 32.0f, 29.0f)),
                Arguments.of(
                        List.of(14.0f, 15.0f, 20.0f, 20.0f, 20.0f, 20.0f, 21.0f, 20.0f, 15.0f, 20.0f),
                        List.of(28.0f, 29.0f, 30.0f, 30.0f, 30.0f, 30.0f, 29.0f, 30.0f, 28.0f, 30.0f))
        );
    }
}
