# Calculadora SOLID en Java

Una aplicación de consola implementada en Java que funciona como una calculadora interactiva con operaciones aritméticas.

## Respuestas al Cuestionario Teórico

**1. ¿Qué responsabilidades tiene exactamente esta clase inicial?**
Considerando nuestro diseño con principios SOLID, las responsabilidades no están aglomeradas, sino segregadas internamente:
* **Lógica matemática pura:** Cada clase (ej. `Suma`, `RaizCuadrada`) tiene la única y exclusiva responsabilidad de calcular el resultado de su función, y nada más.
* **Motor de Desacoplamiento:** La clase `ProcesadorCalculadora` recibe números y una "operación abstracta" para invocar su ejecución, aislando el menú de las matemáticas reales.
* **Capa de Presentación:** El método `main()` asume una sola responsabilidad: pintar el menú, recibir textos del escáner y mostrar la información, separando la interfaz de la lógica de negocio.

**2. Si quiero tener 2 menús distintos, ¿qué debería modificar?**
A diferencia de un código espagueti donde habría que romper todo, en nuestro diseño **NO se modifica ni una sola ley de negocio** (`ProcesadorCalculadora`, `Suma`, etc.). Sólo deberíamos extender la Capa de Presentación abstrayendo los textos que hoy viven en el `main` hacia nuevas interfaces gráficas (`MenuAvanzado` o `MenuBasico`).

**3. ¿Cómo adiciono operaciones nuevas (ej. Potencia)? ¿Rompe algo más?**
Solo se debe crear y añadir una nueva clase interna estática `Potencia` que herede e implemente la interfaz `OperacionBinaria`, ubicando en ella un `Math.pow()`.
**NO rompe nada más en el sistema:** Gracias a que aplicamos el Principio Abierto/Cerrado (OCP) y el Principio de Sustitución de Liskov (LSP), el Motor Central procesará la "Potencia" con naturalidad como una operación abstracta válida, sin la necesidad de modificar el código previo ni de interrumpir procesos pasados.

**4. ¿Qué pasa si ingreso validaciones de dominio? ¿Dónde irían?**
Aplicando DDD (Domain-Driven Design), las validaciones no se ensucian metiéndolas en condicionales masivos en el menú visual. Éstas se ubican en la misma capa de dominio mediante la inyección estructurada o aislando las reglas (Value Objects). Las matemáticas dictan que una división entre 0 no existe, por tanto la validación recae en control del ambiente matemático y nunca en el menú del Scanner.

