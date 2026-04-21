import java.util.Scanner;

public class CalculadoraSolid {

    // --- Interfaces (Segregación de Interfaces e Inversión de Dependencias) ---
    // Separamos las operaciones por la cantidad de parámetros requeridos
    
    public interface OperacionBinaria {
        double ejecutar(int a, int b);
    }

    public interface OperacionUnaria {
        double ejecutar(int a);
    }

    // --- Implementaciones (Responsabilidad Única y Abierto/Cerrado) ---
    // Cada operación tiene su propia clase y lógica. Si queremos nuevas
    // operaciones, creamos nuevas clases sin tocar las existentes.

    public static class Suma implements OperacionBinaria {
        @Override
        public double ejecutar(int a, int b) {
            return (double) a + b;
        }
    }

    public static class Resta implements OperacionBinaria {
        @Override
        public double ejecutar(int a, int b) {
            return (double) a - b;
        }
    }
    
    public static class Multiplicacion implements OperacionBinaria {
        @Override
        public double ejecutar(int a, int b) {
            return (double) a * b;
        }
    }

    public static class Division implements OperacionBinaria {
        @Override
        public double ejecutar(int a, int b) {
            if (b == 0) {
                throw new ArithmeticException("No se puede dividir por cero");
            }
            return (double) a / b;
        }
    }

    public static class RaizCuadrada implements OperacionUnaria {
        @Override
        public double ejecutar(int a) {
            if (a < 0) {
                throw new ArithmeticException("No se puede calcular la raíz cuadrada de un número negativo");
            }
            return Math.sqrt(a);
        }
    }

    public static class LogaritmoNatural implements OperacionUnaria {
        @Override
        public double ejecutar(int a) {
            if (a <= 0) {
                throw new ArithmeticException("El logaritmo natural solo está definido para números positivos");
            }
            return Math.log(a);
        }
    }

    // --- Motor de la Calculadora (Inversión de Dependencias y Sustitución de Liskov) ---
    // Esta clase procesa las operaciones dependiendo solo de interfaces, lo que
    // permite sustituir cualquier implementacion concreta.

    public static class ProcesadorCalculadora {
        public double calcularBinaria(OperacionBinaria operacion, int a, int b) {
            return operacion.ejecutar(a, b);
        }

        public double calcularUnaria(OperacionUnaria operacion, int a) {
            return operacion.ejecutar(a);
        }
    }

    // --- Interfaz de Consola ---
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProcesadorCalculadora procesador = new ProcesadorCalculadora();

        System.out.println("=== Calculadora SOLID ===");
        System.out.println("1. Suma");
        System.out.println("2. Resta");
        System.out.println("3. Multiplicación");
        System.out.println("4. División");
        System.out.println("5. Raíz Cuadrada");
        System.out.println("6. Logaritmo Natural");
        System.out.println("0. Salir");
        
        while (true) {
            System.out.print("\nSeleccione una operación (0-6): ");
            int opcion;
            try {
                 opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                 System.out.println("Por favor ingrese un número válido.");
                 continue;
            }

            if (opcion == 0) {
                System.out.println("Saliendo...");
                break;
            }

            try {
                if (opcion >= 1 && opcion <= 4) {
                    System.out.print("Ingrese el primer número entero: ");
                    int a = Integer.parseInt(scanner.nextLine());
                    System.out.print("Ingrese el segundo número entero: ");
                    int b = Integer.parseInt(scanner.nextLine());

                    OperacionBinaria operacion = null;
                    switch (opcion) {
                        case 1: operacion = new Suma(); break;
                        case 2: operacion = new Resta(); break;
                        case 3: operacion = new Multiplicacion(); break;
                        case 4: operacion = new Division(); break;
                    }
                    
                    double resultado = procesador.calcularBinaria(operacion, a, b);
                    System.out.println("Resultado: " + resultado);

                } else if (opcion == 5 || opcion == 6) {
                    System.out.print("Ingrese el número entero: ");
                    int a = Integer.parseInt(scanner.nextLine());

                    OperacionUnaria operacion = null;
                    switch (opcion) {
                        case 5: operacion = new RaizCuadrada(); break;
                        case 6: operacion = new LogaritmoNatural(); break;
                    }

                    double resultado = procesador.calcularUnaria(operacion, a);
                    System.out.println("Resultado: " + resultado);
                } else {
                    System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato de número incorrecto. Debe ser entero.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
