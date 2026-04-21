package solid.d;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import solid.d.domain.*;
import solid.d.infrastructure.*;
import solid.d.application.*;

public class DependencyInversionTest {

    @Test
    public void testInversionDeDependencias_DesacoplandoLaConsola() {
        // DIP (Dependency Inversion Principle):
        // Los módulos de alto nivel (nuestra calculadora) NO deben depender de los 
        // módulos de bajo nivel (como el Scanner del sistema o el System.out.println).
        // Ambos deben depender de abstracciones (interfaces).

        // Usamos TDD para pasar "Mocks" (simuladores) a nuestro motor, demostrando 
        // que invertimos la dependencia y ahora la Calculadora no está atada a la Consola de Windows.

        // 1. Configuramos nuestros "Simuladores" en lugar de un lector real de teclado
        LectorTecladoMock lectorMock = new LectorTecladoMock();
        lectorMock.simularEntradaUsuario("1"); // Elige Suma
        lectorMock.simularEntradaUsuario("10"); // Primer valor
        lectorMock.simularEntradaUsuario("20"); // Segundo valor

        ImpresorPantallaMock impresorMock = new ImpresorPantallaMock();

        // 2. INYECCIÓN DE DEPENDENCIAS (DIP puro)
        // El motor de la calculadora requiere una interfaz Lector y una interfaz Impresor.
        // Se los pasamos por constructor.
        CatalogoOperaciones catalogo = new CatalogoOperaciones();
        catalogo.registrar(new Suma()); // También inyectamos las operaciones!
        
        CalculadoraApp app = new CalculadoraApp(lectorMock, impresorMock, catalogo);

        // 3. Ejecutamos
        app.iniciar();

        // 4. Aseguramos que el resultado fue impreso sin haber tocado la consola real
        assertTrue(impresorMock.obtenerPantalla().contains("Resultado: 30.0"), 
            "La calculadora falló en imprimir el valor inyectado");
    }

    @Test
    public void testDDD_InversionDeDependenciasDominioBase() {
        // En DDD, la infraestructura de la base de datos o de los catálogos lógicos
        // debe depender del dominio, no al revés.
        
        // El repositorio de operaciones es una abstracción.
        RepositorioOperaciones repo = new RepositorioOperacionesEnMemoria();
        repo.guardar(new Division());
        
        // Comprobamos que podemos inyectar un repositorio abstracto al motor abstracto.
        ProcesadorCalculadora motor = new ProcesadorCalculadora(repo);
        
        // Verificamos que encontró la abstracción sin estar rígidamente acoplado
        assertNotNull(motor.obtenerOperacionRegistrada("Division"));
    }
}
