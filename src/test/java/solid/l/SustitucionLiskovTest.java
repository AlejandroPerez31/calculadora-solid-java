package solid.l;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import solid.l.domain.*;

public class SustitucionLiskovTest {

    @Test
    public void testLiskovSubstitution_OperacionesIntercambiablesSinRomperContrato() {
        // LISKOV (LSP): Cualquier subclase que herede de "Operacion" 
        // debe poder reemplazar a su abstracción padre sin corromper el motor.
        
        // El contrato arquitectónico dicta que toda Operacion ejecuta y retorna un 
        // Value Object de dominio llamado "Resultado".
        
        ProcesadorCalculadora procesador = new ProcesadorCalculadora();
        Operacion operacion; // La abstracción base

        // Sustitución 1: Asignamos una Suma
        operacion = new Suma();
        Resultado resSuma = procesador.ejecutar(operacion, new NumeroEstandar(10), new NumeroEstandar(5));
        assertTrue(resSuma.esValido(), "La sustitución por Suma respetó el contrato de integridad");

        // Sustitución 2: Reemplazamos violentamente por una División (Liskov puro)
        // El procesador ni se entera del cambio subyacente y todo sigue funcionando.
        operacion = new Division();
        Resultado resDiv = procesador.ejecutar(operacion, new NumeroEstandar(10), new Denominador(5));
        assertTrue(resDiv.esValido(), "La sustitución por División respetó el contrato de integridad");
    }

    @Test
    public void testLiskovSubstitution_EvitandoAntiPatrones() {
        // En un código "Desastre" (anti-patrón Liskov), alguien forzaría a una "RaizCuadrada" 
        // a pertenecer a OperacionBinaria, haciendo que tenga que lanzar una 
        // UnsupportedActionException por accidente si intentas usar su "segundo" parámetro.
        
        // Demostramos el principio: nuestra jerarquía no miente, no debilita postcondiciones
        // ni lanza excepciones fuera del contrato acordado.
        
        CatalogoOperaciones catalogo = new CatalogoOperaciones();
        
        for (Operacion op : catalogo.obtenerLista()) {
            assertDoesNotThrow(() -> {
                // Aseguramos dinámicamente que NINGUNA clase hija rompe su promesa base 
                // con métodos no implementados o trampas.
                op.verificarPromesaDeDiseno();
            }, "La clase " + op.getClass().getSimpleName() + " violó el Principio de Liskov.");
        }
    }
}
