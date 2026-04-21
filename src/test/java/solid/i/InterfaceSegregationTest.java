package solid.i;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import solid.i.domain.*;

import java.lang.reflect.Method;

public class InterfaceSegregationTest {

    @Test
    public void testSegregacion_ClaseSumaNoDebeDependerDeMetodosUnarios() {
        // ISP (Interface Segregation Principle):
        // Ningún cliente (como la clase Suma) debe verse obligado a depender 
        // de métodos que no usa (como calcular raíces o trabajar con un solo número).
        
        // Usamos TDD Arquitectónico (Reflection) para garantizar que la interfaz
        // está segregada y la clase Suma no tiene "métodos basura" forzados.
        
        Method[] metodosSuma = Suma.class.getDeclaredMethods();
        boolean tieneMetodoUnario = false;
        
        for (Method metodo : metodosSuma) {
            if (metodo.getName().equals("calcularUnario")) {
                tieneMetodoUnario = true;
            }
        }
        
        assertFalse(tieneMetodoUnario, 
            "La clase Suma rompió el principio ISP al implementar un método unario que no necesita");
    }

    @Test
    public void testSegregacion_ClaseRaizCuadradaNodependeDeMetodosBinarios() {
        // Comprobamos el caso inverso con la Raíz Cuadrada (Operación de 1 parámetro)
        Method[] metodosRaiz = RaizCuadrada.class.getDeclaredMethods();
        boolean tieneMetodoBinario = false;
        
        for (Method metodo : metodosRaiz) {
            // Buscamos si fue forzada a recibir dos parámetros
            if (metodo.getParameterCount() == 2) {
                tieneMetodoBinario = true;
            }
        }
        
        assertFalse(tieneMetodoBinario, 
            "La clase RaizCuadrada rompió el principio ISP: se le obligó a depender de un método que pide 2 parámetros");
    }

    @Test
    public void testDDD_SoporteExpresionAritmeticaSegregada() {
        // DEMOSTRANDO DDD: 
        // Construimos un Value Object "ExpresionBinaria" que solo acepta objetos
        // del contrato "OperacionBinaria", impidiendo de raíz en tiempo de compilación
        // que alguien meta una interfaz gorda o errónea.
        
        OperacionBinaria suma = new Suma();
        NumeroEstandar val1 = new NumeroEstandar(4);
        NumeroEstandar val2 = new NumeroEstandar(4);

        // La validación vive en el dominio: La Expresión solo se construye si
        // los contratos de interface segregada son exactos.
        ExpresionBinaria expresion = new ExpresionBinaria(suma, val1, val2);
        
        assertEquals(8.0, expresion.resolver().getValor());
    }
}
