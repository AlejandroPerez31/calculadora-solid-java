# Calculadora SOLID en Java

Una aplicación de consola implementada en Java que funciona como una calculadora interactiva con operaciones aritméticas.

## Respuestas al Cuestionario Teórico

**1. ¿Qué responsabilidades tiene exactamente esta clase inicial?**
Las responsabilidades están bien distribuidas: cada clase como Suma solo calcula su operación, ProcesadorCalculadora conecta el menú con las matemáticas, y el main() solo se encarga de la interfaz con el usuario. No hay una sola clase cargando con todo.

**2. Si quiero tener 2 menús distintos, ¿qué debería modificar?**
 En esta parte del codigo no se toca nada de la lógica. Solo se crean nuevas clases de presentación que usen el ProcesadorCalculadora que ya existe, sin romper nada.
**3. ¿Cómo adiciono operaciones nuevas (ej. Potencia)? ¿Rompe algo más?**
Se crea una clase Potencia que implementa la OperacionBinaria y se registra.El OCP procesa sin necesidad de modificar nada existente.

**4. ¿Qué pasa si ingreso validaciones de dominio? ¿Dónde irían?**
Se meten en los Value Objects de la capa de dominio, no en el menú. Así los datos llegan validados antes de que la operación se ejecute, siguiendo el enfoque DDD.

