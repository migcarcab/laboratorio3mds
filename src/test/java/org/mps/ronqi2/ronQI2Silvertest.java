package org.mps.ronqi2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Nested;
import org.mps.dispositivo.DispositivoSilver;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ronQI2Silvertest {

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */
    @Nested
    @DisplayName("Test de inicializar")
    class InicializarTest {
        @Test
        @DisplayName("Inicializar un RonQI2Silver Con todo posible de conectar y configurar funciona correctamente")
        public void InicializarCorrectoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.inicializar();

            assertTrue(resultado,
                    "El método inicializar debería devolver true si ambos sensores se pueden conectar y configurar.");
        }
        @Test
        @DisplayName("Inicializar un RonQI2Silver Con sensor de presion no conectado falla")
        public void InicializarSensorPresionNoConectadoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.inicializar();

            assertTrue(!resultado,
                    "El método inicializar debería devolver false si el sensor de presión no se puede conectar.");
        }
        @Test
        @DisplayName("Inicializar un RonQI2Silver Con sensor de sonido no conectado falla")
        public void InicializarSensorSonidoNoConectadoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.inicializar();

            assertTrue(!resultado,
                    "El método inicializar debería devolver false si el sensor de sonido no se puede conectar.");
        }
        @Test
        @DisplayName("Inicializar un RonQI2Silver Con sensor de presion no configurado falla")
        public void InicializarSensorPresionNoConfiguradoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.inicializar();

            assertTrue(!resultado,
                    "El método inicializar debería devolver false si el sensor de presión no se puede configurar.");
        }
        @Test
        @DisplayName("Inicializar un RonQI2Silver Con sensor de sonido no configurado falla")
        public void InicializarSensorSonidoNoConfiguradoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.inicializar();

            assertTrue(!resultado,
                    "El método inicializar debería devolver false si el sensor de sonido no se puede configurar.");
        }
    }
    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */
    @Test
    @DisplayName("Se debe llamar una sola vez a cada sensor cuando se inicializa de forma correcta")
    public void InicializaCasteaSensoresTest(){
        
        DispositivoSilver disp = mock(DispositivoSilver.class);
        when(disp.conectarSensorPresion()).thenReturn(true);
        when(disp.configurarSensorPresion()).thenReturn(true);
        when(disp.conectarSensorSonido()).thenReturn(true);
        when(disp.configurarSensorSonido()).thenReturn(true);
        when(disp.estaConectado()).thenReturn(true);
        RonQI2Silver ron = new RonQI2Silver();


        ron.anyadirDispositivo(disp);
        ron.inicializar();

        assertTrue(ron.estaConectado());
        verify(disp, times(1)).configurarSensorPresion();
        verify(disp, times(1)).configurarSensorSonido();
        
    }

    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */
    @Nested
    @DisplayName("Test de reconectar")
    class ReconectarTest {
        @Test
        @DisplayName("Reconectar un RonQI2Silver conecta ambos sensores correctamente")
        public void ReconectarCorrectoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.reconectar();

            assertTrue(resultado,
                    "El método reconectar debería devolver true si ambos sensores se conectan.");
            verify(disp).conectarSensorPresion();
            verify(disp).conectarSensorSonido();
            verify(disp).estaConectado();
        }

        @Test
        @DisplayName("Reconectar un RonQI2Silver ya esta conectado")
        public void ReconectarYaConectadoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.reconectar();

            assertTrue(!resultado,
                    "El método reconectar debería devolver false si el dispositivo ya está conectado.");
            verify(disp, times(0)).conectarSensorPresion();
            verify(disp, times(0)).conectarSensorSonido();
            verify(disp).estaConectado();
        }

        @Test
        @DisplayName("Reconectar un RonQI2Silver no conecta el sensor de presion")
        public void ReconectarSensorPresionNoConectadoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(false);
            when(disp.conectarSensorPresion()).thenReturn(false);
            when(disp.conectarSensorSonido()).thenReturn(true);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.reconectar();

            assertTrue(!resultado,
                    "El método reconectar debería devolver false si el sensor de presión no se conecta.");
            verify(disp).conectarSensorPresion();
            verify(disp,times(0)).conectarSensorSonido();
            verify(disp).estaConectado();
        }

        @Test
        @DisplayName("Reconectar un RonQI2Silver no conecta el sensor de sonido")
        public void ReconectarSensorSonidoNoConectadoTest() {
            RonQI2 ronQI2 = new RonQI2Silver();
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.estaConectado()).thenReturn(false);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(false);

            ronQI2.anyadirDispositivo(disp);
            boolean resultado = ronQI2.reconectar();

            assertTrue(!resultado,
                    "El método reconectar debería devolver false si el sensor de sonido no se conecta.");
            verify(disp).conectarSensorSonido();
            verify(disp).conectarSensorPresion();
            verify(disp).estaConectado();
        }
    }
        /*
         * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
         * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
         * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
         */

        @Test
        @DisplayName("Se debe devolver true en caso de que las últimas lecturas estén por encima del umbral aunque sean menos de cinco")
        public void ApneaSuenyoTrueConMenosDeCincoTest() {

            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(30.5f);
            when(disp.leerSensorSonido()).thenReturn(30.5f);
            RonQI2Silver ron = new RonQI2Silver();

            ron.anyadirDispositivo(disp);
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();

            assertTrue(ron.evaluarApneaSuenyo());

        }
        
        @Test
        @DisplayName("Se debe devolver true en caso de que las últimas lecturas estén por encima del umbral aunque sean mas de cinco")
        public void ApneaSuenyoTrueConMasDeCincoTest() {

            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(30.5f);
            when(disp.leerSensorSonido()).thenReturn(30.5f);
            RonQI2Silver ron = new RonQI2Silver();

            ron.anyadirDispositivo(disp);
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();

            assertTrue(ron.evaluarApneaSuenyo());

        }

        @Test
        @DisplayName("Cuando las lecturas de sonido no alcanzan el umbral, debe devolver false")
        public void ApneaSuenyoFalseSonidoTest(){
            
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(30.5f);
            when(disp.leerSensorSonido()).thenReturn(25.5f);
            RonQI2Silver ron = new RonQI2Silver();

            ron.anyadirDispositivo(disp);
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();

            assertFalse(ron.evaluarApneaSuenyo());

        }

        @Test
        @DisplayName("Cuando las lecturas de sonido no alcanzan el umbral, debe devolver false")
        public void ApneaSuenyoFalsePreesionTest(){
            
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(15.5f);
            when(disp.leerSensorSonido()).thenReturn(32.5f);
            RonQI2Silver ron = new RonQI2Silver();

            ron.anyadirDispositivo(disp);
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();
            ron.obtenerNuevaLectura();

            assertFalse(ron.evaluarApneaSuenyo());

        }
         
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
        @ParameterizedTest
        @DisplayName("Obtener 10 lecturas de RonQI2Silver")
        @ValueSource(ints = { 4,5,10 })
        public void ObtenerLecturasTest(int cantidadDeLecturas) {
            DispositivoSilver disp = mock(DispositivoSilver.class);
            when(disp.conectarSensorPresion()).thenReturn(true);
            when(disp.configurarSensorPresion()).thenReturn(true);
            when(disp.conectarSensorSonido()).thenReturn(true);
            when(disp.configurarSensorSonido()).thenReturn(true);
            when(disp.estaConectado()).thenReturn(true);
            when(disp.leerSensorPresion()).thenReturn(30.5f);
            when(disp.leerSensorSonido()).thenReturn(30.5f);
            RonQI2Silver ron = new RonQI2Silver();

            ron.anyadirDispositivo(disp);

            for (int i = 0; i < cantidadDeLecturas; i++) {
                ron.obtenerNuevaLectura();
            }

            assertTrue(ron.evaluarApneaSuenyo());
        }
}
