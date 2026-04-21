package solid.o;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import solid.o.domain.*;

public class OperacionesOpenClosedTest {

    @Test
    public void testSumaBasica() {
        ProcesadorCalculadora procesador = new ProcesadorCalculadora();
        // Probamos una Suma normal usando Value Objects
        Operacion suma = new Suma();
        double resultado = procesador.ejecutar(suma, new NumeroEstandar(5), new NumeroEstandar(3));
        assertEquals(8.0, resultado);
    }

    @Test
    public void testAgregarNuevaOperacionSinModificarProcesador() {
        // DEMOSTRANDO OPEN/CLOSED PRINCIPLE: 
        // Creamos una operación totalmente nueva (Potencia) 
        // y demostramos que el Procesador original la procesa perfectamente
        // sin haber alterado su código interno.
        ProcesadorCalculadora procesador = new ProcesadorCalculadora();
        Operacion potencia = new Potencia(); // ¡Nueva Extensión!
        
        double resultado = procesador.ejecutar(potencia, new NumeroEstandar(2), new NumeroEstandar(3));
        assertEquals(8.0, resultado);
    }

    @Test
    public void testDomainDrivenDesign_ValidarDenominadorCero() {
        // DEMOSTRANDO DDD (Value Objects):
        // La validación matemática pura viaja al propio objeto de valor 
        // en vez de ensuciar las clases de la calculadora.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Denominador(0);
        });
        assertEquals("El denominador no puede ser cero", exception.getMessage());
    }

    @Test
    public void testDivisionUsandoValidacionesDeDominio() {
        ProcesadorCalculadora procesador = new ProcesadorCalculadora();
        Operacion division = new Division();
        
        // Ejecutamos división pasando el Value Object correcto
        double resultado = procesador.ejecutar(division, new NumeroEstandar(10), new Denominador(2));
        assertEquals(5.0, resultado);
    }
}
