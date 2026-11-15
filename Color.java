/**
 * [ia] Códigos de color ANSI y formatos de la consola (estilo pergamino).
 * Solo usa caracteres de la página de códigos 850 para que se vean en cualquier consola.
 * Cada constante cambia el color del texto que viene detrás; RESET lo devuelve al normal.
 */
public class Color
{
    public static final String RESET    = "\u001B[0m";   // vuelve al color normal
    public static final String NEGRITA  = "\u001B[1m";
    public static final String CURSIVA  = "\u001B[3m";
    public static final String ROJO     = "\u001B[31m";
    public static final String VERDE    = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL     = "\u001B[34m";
    public static final String MAGENTA  = "\u001B[35m";
    public static final String CIAN     = "\u001B[36m";
    public static final String GRIS     = "\u001B[90m";

    // Ancho de títulos y separadores, y de las líneas de puntos
    private static final int ANCHO = 60;
    private static final int ANCHO_RESUMEN = 44;

    // Prefijo de los mensajes del sistema
    public static final String SISTEMA = MAGENTA + NEGRITA + "[SISTEMA]" + RESET;

    /**
     * Elige el color de la vida según el porcentaje que queda.
     * @param vida vida actual
     * @param vidaMaxima vida máxima
     * @return VERDE por encima del 50 %, AMARILLO hasta el 25 %, ROJO por debajo
     */
    public static String lifeColor(int vida, int vidaMaxima)
    {
        int porcentaje = (vida * 100) / vidaMaxima;

        String color;
        if (porcentaje > 50)
        {
            color = VERDE;
        }
        else if (porcentaje > 25)
        {
            color = AMARILLO;
        }
        else
        {
            color = ROJO;
        }
        return color;
    }

    /**
     * Título centrado entre adornos: ■═════ TEXTO ═════■
     * @param texto texto del título
     * @param color color del título
     * @return el título ya coloreado
     */
    public static String title(String texto, String color)
    {
        String centro = " " + texto + " ";
        int relleno = ANCHO - centro.length() - 2;      // los 2 cuadrados de los extremos
        int izquierda = relleno / 2;
        int derecha = relleno - izquierda;              // si es impar, el sobrante va a la derecha

        return color + NEGRITA + "■" + "═".repeat(izquierda) + centro + "═".repeat(derecha) + "■" + RESET;
    }

    /**
     * Línea de cierre del mismo ancho que los títulos.
     * @return la línea dorada
     */
    public static String separator()
    {
        return AMARILLO + "═".repeat(ANCHO) + RESET;
    }

    /**
     * Número de opción entre corchetes, en dorado: [1]
     * @param numero número de la opción
     * @return la opción coloreada
     */
    public static String option(int numero)
    {
        return AMARILLO + NEGRITA + "[" + numero + "]" + RESET;
    }

    /**
     * Centra un texto dentro del ancho de los títulos.
     * @param texto texto a centrar
     * @return el texto con los espacios delante
     */
    public static String center(String texto)
    {
        return " ".repeat((ANCHO - texto.length()) / 2) + texto;
    }

    /**
     * Línea con puntos entre la etiqueta y el valor: Puntuación final ....... 425
     * @param etiqueta texto de la izquierda
     * @param valor texto de la derecha (sin colores, para que la cuenta salga bien)
     * @return la línea completa
     */
    public static String leader(String etiqueta, String valor)
    {
        int puntos = ANCHO_RESUMEN - etiqueta.length() - valor.length() - 2;
        return etiqueta + " " + GRIS + ".".repeat(puntos) + RESET + " " + valor;
    }
}