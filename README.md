# Implementación de Vectores

En esta librería he incluido todas las clases relacionadas con vectores que utilizo en mis proyectos personales Java.

Autor: Sergio Martí Torregrosa.

Fecha: 01/09/2023

Versión: 1.0v

## Introducción

Un vector en matemáticas y física es un ente matemático que se puede representar mediante un segmento de recta orientado dentro de un espacio euclídeo ([explicación en Wikipedia](https://es.wikipedia.org/wiki/Vector)).

Un vector se define por 3 propiedades:

- Magnitud o Módulo: el valor de la longitud del segmento que representa el vector
- Dirección: la línea donde se encuentra el vector. Se puede entender como el ángulo respecto a un eje hacia el que esta orientado el vector.
- Sentido: es cada una de las direcciones que puede tomar el vector, indicando origen y destino.

Java dispone de varios tipos de datos primitivos numéricos (short, byte, int, long, float, double). En esta librería, he definido clases que representan vectores cuyas componentes se almacenan con los tipos primitivos: int, float y double. Para poder trabajar con números enteros, números de coma flotante de precisión simple y de doble precisión.


## Operaciones con Vectores

Los vectores implementan la interfaz de java "comparable", para poder ser ordenados y comparados en una colección. Los vectores se ordenan primero en el eje X y a continuación en el eje Y.

Las clases que representan vectores cuentan con constructores y métodos que pueden ser útiles a la hora de inicializar o establecer sus valores.

- Constructores:
    - Vec(): constructor nulo, inicializa las coordenadas a 0.
    - Vec(value): inicializa todas las coordenadas con el mismo valor.
    - Vec(x, y): constructor por defecto para establecer los valores de las coordenadas
    - Vec(other_vector): constructor copia.
  
- Setters:
    - setX(x): establece el valor de x.
    - setY(y): establece el valor de y.
    - set(x, y): establece el valor de x e y.
    - set(other_vector): copia los valores de otro vector.

Las clases disponen de métodos para realizar las principales y más comunes operaciones matemáticas.

- Suma/Adición:
    - add(amount)
    - addToX(amount)
    - addToY(amount)
    - add(amount_x, amount_y)
    - add(other_vector)
    
- Resta:
    - sub(amount)
    - subToX(amount)
    - subToY(amount)
    - sub(amount_x, amount_y)
    - sub(other_vector)
    
- Multiplicación/Escalado:
    - mul(amount)
    - mulXBy(amount)
    - mulYBy(amount)
    - mul(amount_x, amount_y)
    - mul(other_vector)

- División:
    - div(amount)
    - divXBy(amount)
    - divYBy(amount)
    - div(amount_x, amount_y)
    - div(other_vector)
  
Además, los vectores disponen de operaciones propias como:

- Calcular la magnitud: mediante el teorema de Pitágoras se puede calcular la magnitud del vector (el segmento que ocupa en la línea de la dirección).
- Normalizar: dividir las coordenadas por el módulo del vector.
- Trasladar un determinado ángulo: "rotar" el vector dado un ángulo.
- Calcular el vector perpendicular respecto a otro.
- Calcular el vector normalizado y normalizar las componentes.

Los métodos para realizar operaciones con otros vectores devuelven la instancia del vector original. Por lo que se pueden realizar multiples operaciones concatenadas en una misma línea de código.

```java
public class OneLineVectorOperationShowcase {

  public static void main(String[] args) {
    Vec2df v1 = new Vec2df(0.5f, 0.75f);
    Vec2df v2 = new Vec2df(0.1f, 0.15f);
    // Múltiples operaciones con vectores concatenadas en una misma línea de código
    v2.add(v1).sub(new Vec2df(0.4f, 0.3f)).mul(new Vec2df(10)).norm();
    System.out.println(v2);
  }

}
```
    