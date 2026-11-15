/**
* [ia] Textos narrativos del juego
* Solo muestra texto;
*/
public class Story {

    // Nombre de cada stage, en el mismo orden que los encuentros de Bestiary
    private static final String[] NOMBRES_STAGE = 
    {
        "Las ruinas de la entrada",
        "El bosque hundido",
        "La sala del guardián"
    };

    // Texto de entrada a cada stage
    private static final String[] INTRO_STAGE = 
    {
        "El pasillo de piedra desemboca en una sala derrumbada.\n"
            + "Algo se mueve entre los escombros...",
        "Al otro lado del campamento se abre un portal. Lo cruzas y el aire se vuelve húmedo y pesado.\n"
            + "Entre los árboles, unos ojos te siguen...",
        "El último portal te deja frente a una puerta enorme que se abre sola.\n"
            + "Al fondo, algo respira despacio..."
    };

    // Texto de cada campamento
    private static final String[] CAMPAMENTO = 
    {
        "El enemigo cae y el eco de la lucha se apaga.\n"
            + "Junto a la salida encuentras una hoguera aún caliente: alguien acampó aquí antes que tú.",
        "Llegas al segundo campamento con las manos todavía temblando.\n"
            + "En la pared, alguien dejó marcas contando los días."
    };

    // -=-=-=-=-=-=- METODOS -=-=-=-=-=-=-

    /**
    * Muestra el despertar del héroe al empezar la partida.
    * @param nombreHeroe nombre elegido por el jugador
    */
    public static void showPrologue(Hero heroe)
    {
        System.out.println("\n" + Color.CURSIVA + "Abres los ojos sobre una piedra fría. No recuerdas cómo has llegado hasta aquí." + Color.RESET);
        System.out.println(Color.CURSIVA + "Una ventana azul se despliega frente a ti." + Color.RESET + "\n");
        System.out.println(Color.SISTEMA + " Bienvenido/a, " + Color.CIAN + Color.NEGRITA + heroe.getNombre() 
                                                            + Color.RESET + ". Has sido invocado/a como héroe.");

        System.out.println(Color.SISTEMA + " " + Color.ROJO + "■" + Color.RESET + " Vida: " + heroe.getVida()
                                                                                + " | CA: " + heroe.getClaseArmadura()
                                                                                + " | Ataque: +" + heroe.getBonificadorAtaque());

        System.out.println(Color.SISTEMA + " Tutorial iniciado: supera tres stages para despertar tu verdadero poder.");
        System.out.println(Color.SISTEMA + " Antes de empezar, elige tu primera mejora.\n");
    }

    /**
    * Muestra el título y la entrada de una stage, y presenta al enemigo.
    * @param stage posición de la stage, empezando en 0 (la misma i del bucle de startGame)
    * @param nombreEnemigo nombre del enemigo que aparece
    */
    public static void showStageIntro(int stage, String nombreEnemigo)
    {
        if (stage >= 0 && stage < INTRO_STAGE.length)
        {
            System.out.println("\n" + Color.title("STAGE " + (stage + 1), Color.AMARILLO));

            System.out.println(Color.AMARILLO + Color.CURSIVA + Color.center(NOMBRES_STAGE[stage]) + Color.RESET);

            System.out.println();

            System.out.println(Color.CURSIVA + INTRO_STAGE[stage] + Color.RESET);
        }
        else
        {
            System.out.println("\n" + Color.title("STAGE " + (stage + 1), Color.AMARILLO));
        }

        System.out.println("¡Aparece " + Color.ROJO + Color.NEGRITA + nombreEnemigo + Color.RESET + "!");
    }

    /**
    * Muestra el campamento que hay después de superar una stage.
    * @param stage posición de la stage superada, empezando en 0
    */
    public static void showCamp(int stage)
    {
        System.out.println("\n" + Color.title("CAMPAMENTO", Color.AMARILLO));
        if (stage >= 0 && stage < CAMPAMENTO.length)
        {
            System.out.println("\n" + Color.CURSIVA + CAMPAMENTO[stage] + Color.RESET);
        }

        System.out.println("\n" + Color.SISTEMA + " Stage " + (stage + 1) + " superada. Elige una mejora.\n");
    }

    /**
    * Final de la partida cuando el héroe derrota a los tres enemigos.
    */
    public static void showVictory()
    {
        System.out.println("\n" + Color.SISTEMA + " Tutorial completado.");
        System.out.println(Color.CURSIVA + "La ventana se apaga y, por primera vez, el silencio no da miedo." + Color.RESET);
        System.out.println(Color.CURSIVA + "Esto solo es el principio." + Color.RESET);
        System.out.println("\n" + Color.title("VICTORIA", Color.VERDE));
    }

    /**
    * Final de la partida cuando la vida del héroe llega a 0.
    */
    public static void showDefeat()
    {
        System.out.println("\n" + Color.CURSIVA + "Todo se vuelve negro." + Color.RESET);
        System.out.println(Color.SISTEMA + " El héroe ha caído. Reiniciando tutorial...");
        System.out.println("\n" + Color.title("DERROTA", Color.ROJO));
    }

    /**
    * Final de la partida cuando el jugador huye.
    */
    public static void showEscape()
    {
        System.out.println("\n" + Color.SISTEMA + " El héroe ha abandonado el tutorial.");
        System.out.println(Color.CURSIVA + "Las puertas se cierran a su espalda." + Color.RESET);
        System.out.println("\n" + Color.title("FIN DE LA AVENTURA", Color.ROJO));
    }
}