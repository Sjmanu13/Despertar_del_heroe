public class Story {
    /**
    * Textos narrativos del tutorial: prólogo, entrada a cada stage,
    * campamentos entre stages y finales de la partida.
    * Solo muestra texto; no modifica ningún dato del juego.
    */

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
    public static void showPrologue(String nombreHeroe)
    {
        System.out.println("\nAbres los ojos sobre una piedra fría. No recuerdas cómo has llegado hasta aquí.");
        System.out.println("Una ventana azul se despliega frente a ti.\n");
        System.out.println("[SISTEMA] Bienvenido/a, " + nombreHeroe + ". Has sido invocado/a como héroe.");
        System.out.println("[SISTEMA] Tutorial iniciado: supera tres stages para despertar tu verdadero poder.");
        System.out.println("[SISTEMA] Antes de empezar, elige tu primera mejora.\n");
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
            System.out.println("\n========== STAGE " + (stage + 1) + " · " + NOMBRES_STAGE[stage] + " ==========");
            System.out.println(INTRO_STAGE[stage]);
        }
        else
        {
            System.out.println("\n========== STAGE " + (stage + 1) + " ==========");
        }

        System.out.println("¡Aparece " + nombreEnemigo + "!");
    }

    /**
    * Muestra el campamento que hay después de superar una stage.
    * @param stage posición de la stage superada, empezando en 0
    */
    public static void showCamp(int stage)
    {
        if (stage >= 0 && stage < CAMPAMENTO.length)
        {
            System.out.println("\n" + CAMPAMENTO[stage]);
        }

        System.out.println("[SISTEMA] Stage " + (stage + 1) + " superada. Elige una mejora.\n");
    }

    /**
    * Final de la partida cuando el héroe derrota a los tres enemigos.
    */
    public static void showVictory()
    {
        System.out.println("\n[SISTEMA] Tutorial completado.");
        System.out.println("La ventana se apaga y, por primera vez, el silencio no da miedo.");
        System.out.println("Esto solo es el principio.");
        System.out.println("VICTORIA");
    }

    /**
    * Final de la partida cuando la vida del héroe llega a 0.
    */
    public static void showDefeat()
    {
        System.out.println("\nTodo se vuelve negro.");
        System.out.println("[SISTEMA] El héroe ha caído. Reiniciando tutorial...");
        System.out.println("DERROTA");
    }

    /**
    * Final de la partida cuando el jugador huye.
    */
    public static void showEscape()
    {
        System.out.println("\n[SISTEMA] El héroe ha abandonado el tutorial.");
        System.out.println("Las puertas se cierran a su espalda.");
        System.out.println("FIN DE LA AVENTURA");
    }
}