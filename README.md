# Despertar_del_heroe · Pick me up

Prototipo de juego de aventura por turnos en la consola de Java. El jugador crea un héroe, supera un tutorial de tres stages contra enemigos elegidos al azar, mejora al héroe en los campamentos y obtiene una puntuación que se guarda en un ranking en XML. El ranking también se puede ver como página web, generada con XSLT y CSS.

Proyecto intermodular de 1.º de DAM (Programación, Entornos de Desarrollo y Lenguaje de Marcas) · XTART Formación Profesional · Jose Manuel Sáez García · 2026.

## Contenido del proyecto

| Archivo               | Función |
|---|---|
| `Game.java`           | Clase principal: `main`, menús, combate, puntuación y desarrollo de la partida |
| `Hero.java`           | El héroe: vida, mejoras, pociones y ataque |
| `Enemy.java`          | Un enemigo: CA y ataque según su dificultad |
| `IBasico.java`        | Interfaz con los métodos comunes de combate |
| `Bestiary.java`       | Catálogo de 9 enemigos y elección de los 3 de cada partida |
| `Dice.java`           | Tiradas de dados (`Dice.roll(3, Dice.D8)` = 3d8) |
| `Ranking.java`        | Las 10 mejores partidas; lectura y escritura de `ranking.xml` con DOM |
| `Color.java`          | Colores ANSI y formato de la consola |
| `Story.java`          | Textos del prólogo, las stages, los campamentos y los finales |
| `ranking.xml`         | Datos del ranking (10 elementos `rank`) |
| `ranking.dtd`         | Estructura válida del XML |
| `ranking.xslt`        | Transformación del XML en una tabla HTML |
| `styles.css`          | Estilos de la página del ranking |
| `ranking.html`        | Página del ranking generada con la transformación |
| `.vscode/tasks.json`  | Tarea de Visual Studio Code que lanza la transformación 

## Requisitos

- JDK 11 o superior (el código usa `String.repeat`). Probado con JDK [versión].
- Una consola que interprete los códigos de color ANSI: la terminal de Visual Studio Code o Windows Terminal.
- Para generar el HTML: Visual Studio Code con la extensión **XSLT/XPath for Visual Studio Code** y el procesador Saxon.

## Compilar y ejecutar

En Visual Studio Code también se puede abrir `Game.java` y pulsar **Run**.

`ranking.xml` y `ranking.dtd` deben estar en la carpeta desde la que se ejecuta el programa.

## Puntuación

Cada enemigo derrotado da `100 × dificultad`, con un 5 % menos por turno empleado y un descuento máximo del 40 %. El ranking guarda las 10 mejores puntuaciones; en caso de empate queda delante la partida más antigua.

## Cómo se juega

1. En el menú, elige **[1] Nueva partida** y escribe el nombre del héroe.
2. Elige una mejora: **+2 a la CA**, **+1 al bonificador de ataque** o **+1 dado de daño**.
3. En cada turno de combate elige una acción:
   - **[1] Atacar:** 1d20 + bonificador contra la CA del enemigo.
   - **[2] Poción:** cura 5d8. No se puede beber con la vida completa.
   - **[3] Inventario:** muestra las pociones que quedan. No gasta turno.
   - **[4] Huir:** termina la partida.
4. Tras cada victoria, salvo la última, el campamento ofrece otra mejora.
5. La partida acaba con una victoria (3 de 3 enemigos), una derrota (vida a 0) o una huida. La puntuación se registra en el ranking.

La opción **[3] Guía** del menú explica todas las reglas dentro del juego.

## Ranking en XML y HTML

- El programa lee `ranking.xml` al arrancar, lo ordena y lo vuelve a guardar, así que los cambios hechos a mano en el archivo se colocan en su sitio.
- `ranking.xml` debe existir y tener sus 10 elementos `rank`. Para empezar con el ranking vacío, deja los 10 elementos con el nombre vacío y 0 puntos:

  ```xml
  <rank position="01"><name></name><points>0</points></rank>
  ```

- Java solo actualiza el XML. Para regenerar `ranking.html`:
  1. Indica en la configuración de Visual Studio Code la ruta del `.jar` de Saxon (`XSLT.tasks.saxonJar`).
  2. Pulsa **Ctrl+Mayús+B** y elige la tarea **«ranking with ranking.xml»**.
  3. Selecciona `ranking.html` como archivo de salida.
