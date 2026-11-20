1. Selección de campos
Pregunta: ¿qué héroes hay en el ranking?
Expresión: /ranking/rank/name
Esperado: Baylen, Galadriel, Benethor, Gandalf, Morgh, Eoxis, Idrial, Elegost
Obtenido: Baylen, Galadriel, Benethor, Gandalf, Morgh, Eoxis, Idrial, Elegost

2. Filtro por puntuación
Pregunta: ¿quién ha conseguido 450 puntos o más?
Expresión: /ranking/rank[points >= 450]/name
Esperado: Baylen, Galadriel, Benethor, Gandalf
Obtenido: Baylen, Galadriel, Benethor, Gandalf

3. Filtro por jugador
Pregunta: ¿cuántos puntos tiene Idrial?
Expresión: /ranking/rank[name = 'Idrial']/points
Esperado: 430
Obtenido: 430

4. Condición combinada
Pregunta: ¿quién está entre 440 y 459 puntos?
Expresión: /ranking/rank[points >= 440 and points < 460]/name
Esperado: Gandalf, Morgh, Eoxis
Obtenido: Gandalf, Morgh, Eoxis

5. Función de recuento
Pregunta: ¿cuántos héroes empatan a 430 puntos?
Expresión: count(/ranking/rank[points = 430])
Esperado: 2
Obtenido: 2