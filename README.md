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

Las clases disponen de métodos para realizar las principales y más comunes operaciones matemáticas.

- Suma/Adición:
    - add(amount)
    - addToX(amount)
    - addToY(amount)
    - add(amount_x, amount_y)
    
- Resta:
    - sub(amount)
    - subToX(amount)
    - subToY(amount)
    - sub(amount_x, amount_y)
    
- Multiplicación/Escalado:
    - mul(amount)
    - mulXBy(amount)
    - mulYBy(amount)
    - mul(amount_x, amount_y)

- División:
    - div(amount)
    - divXBy(amount)
    - divYBy(amount)
    - div(amount_x, amount_y)
  
Además, los vectores disponen de operaciones propias como:

- Calcular la magnitud: mediante el teorema de Pitágoras se puede calcular la magnitud del vector (el segmento que ocupa en la línea de la dirección).
- Normalizar: dividir las coordenadas por el módulo del vector.
- Trasladar un determinado ángulo: "rotar" el vector dado un ángulo.
- Calcular el vector perpendicular respecto a otro.
- Calcular el vector normal respecto a otro.
    