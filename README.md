# Calculadora SOLID en Java

Una aplicación interactiva de consola implementada en Java que funciona como una calculadora con operaciones aritméticas entre números enteros, tanto de tipo binarias (suma, resta, multiplicación, división) como unarias (raíz cuadrada, logaritmo natural).

## Objetivo del Proyecto

El principal enfoque de este desarrollo es demostrar la aplicación de los principios **SOLID** en la arquitectura del software, resolviendo un reto adicional: **mantener todo el código fuente organizado y funcionalmente segregado pero dentro de un único archivo de clase (`.java`)** aprovechando clases estáticas anidadas e interfaces internas.

## Principios SOLID aplicados:

1. **Single Responsibility (SRP)**: Cada operación matemática concreta (como la `Suma` o `RaizCuadrada`) vive dentro de su propia clase cuya única razón para cambiar o editarse es si cambia la fórmula de dicha operación.
2. **Open-Closed (OCP)**: El diseño de software está abierto a la extensión. Se pueden extender o soportar nuevas operaciones (ej. Potenciación o Seno/Coseno) simplemente añadiendo nuevas clases que implementen las interfaces, sin necesidad imperativa de modificar las clases ya existentes que funcionan bien.
3. **Liskov Substitution (LSP)**: La calculadora (`ProcesadorCalculadora`) espera simplemente una operación general. De esta manera podemos reemplazar y sustituir cualquier requerimiento de cálculo por un cálculo diferente y el procesador funcionará sin excepciones extrañas o flujos inesperados en el proceso.
4. **Interface Segregation (ISP)**: Los contratos se dividieron en dos distintas interfaces exclusivas: `OperacionBinaria` y `OperacionUnaria`. De este modo evitamos "interfaces gordas" que forzarían a un Logaritmo a implementar parámetros innecesarios si tuviéramos un solo contrato `ejecutar(a, b)`.
5. **Dependency Inversion (DIP)**: Los módulos principales y de interacción del usuario (el menú principal y el procesador de ejecución) nunca dependen de los algoritmos concretos (como `Multiplicacion`), sino que están sujetos a abstracciones de alto nivel orientadas a interfaz, bajando notablemente el grado de acoplamiento.

## Instrucciones de Ejecución

Debes contar con el Java Development Kit (JDK) instalado en tu equipo u ordenador.

1. Abre tu terminal de preferencia o línea de comandos.
2. Compila el archivo en código de bytes ejecutando:
   ```bash
   javac CalculadoraSolid.java
   ```
3. Inicia la aplicación escribiendo:
   ```bash
   java CalculadoraSolid
   ```
