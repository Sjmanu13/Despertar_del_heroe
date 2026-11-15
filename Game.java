import java.util.Scanner;

public class Game {
    // -------- CONSTANTES DE PUNTUACIÓN --------
    private static final int PUNTOS_BASE = 100;
    private static final int PENALIZACION_POR_TURNO = 5;
    private static final int DESCUENTO_MAXIMO = 40;

    // -------- CONSTANTES DE BARRA DE VIDA --------
    private static final int LARGO_BARRA = 20; // Nº casillas de la barra de vida

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Ranking ranking = new Ranking();
        ranking.reload();

        mainMenu(sc, ranking);
        
        sc.close();
    }

    // -=-=-=-=-=-=- METODOS -=-=-=-=-=-=-

    // -------- MENÚ --------
    private static void mainMenu(Scanner sc, Ranking ranking) {
        boolean finJuego = false;

        do {
            System.out.println("\n" + Color.title("PICK ME UP", Color.AMARILLO));

            System.out.println(Color.GRIS + Color.CURSIVA + Color.center("Bienvenido/a, héroe") + Color.RESET);

            System.out.println();

            System.out.println("   " + Color.option(1) + " Nueva partida");
            System.out.println("   " + Color.option(2) + " Mostrar ranking");
            System.out.println("   " + Color.option(3) + " Guía");
            System.out.println("   " + Color.option(4) + " Salir del juego");

            System.out.println(Color.separator());

            int option = readOption(sc, 1, 4);

            switch (option) {

                case 1:
                    startGame(sc, ranking);
                    break;

                case 2:
                    ranking.reload();
                    ranking.show();
                    break;

                case 3:
                    showHelp();
                    break;

                case 4:
                    System.out.println("Hasta la vista, héroe.");
                    finJuego = true;
                    break;

                default:
                    break;
            }

        } while (!finJuego);

    }

    private static void showHelp() 
    {
        System.out.println("\n" + Color.title("GUÍA", Color.AMARILLO));

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "DE QUÉ TRATA" + Color.RESET);

        System.out.println("Has sido invocado/a como héroe y debes superar el tutorial: tres stages,");
        System.out.println("cada una con un enemigo más fuerte que el anterior.");
        System.out.println("Los enemigos se eligen al azar, así que ninguna partida es igual.");
        System.out.println("Casi todo se decide con dados: 3d8 significa tirar tres dados de 8 caras y sumarlos.");


        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "TU HÉROE" + Color.RESET);

        System.out.println("  · Vida: se tira al crear el héroe (20d10, entre 20 y 200).");
        System.out.println("  · Daño: 3d8 por golpe.");
        System.out.println("  · Clase de armadura (CA): 12. Cuanto más alta, más difícil es que te golpeen.");
        System.out.println("  · Bonificador de ataque: +3. Se suma a tu tirada para acertar.");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "ENEMIGOS" + Color.RESET);

        System.out.println("  Prólogo » Stage 1 » Campamento » Stage 2 » Campamento » Stage 3");
        System.out.println("El primer enemigo es fácil, el segundo intermedio y el tercero difícil.");
        System.out.println("Cuanto más difícil, más vida, más CA y más puntería tiene.");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "COMBATE" + Color.RESET);

        System.out.println("El combate es por turnos. En cada turno eliges una acción:");
        System.out.println("  " + Color.option(1) + " Atacar: tiras 1d20 + tu bonificador. Si igualas o superas");
        System.out.println("      la CA del enemigo, le haces daño.");
        System.out.println("  " + Color.option(2) + " Poción: bebes una poción y recuperas vida.");
        System.out.println("  " + Color.option(3) + " Inventario: consultas las pociones que te quedan.");
        System.out.println("  " + Color.option(4) + " Huir: abandonas el tutorial y la partida termina.");

        System.out.println("Tras atacar o beber, el enemigo te devuelve el golpe si sigue en pie.");
        System.out.println("Ver el inventario, elegir una opción no válida o intentar curarte sin poder");
        System.out.println("no gastan turno: el enemigo no atacará.");

        System.out.println();

        System.out.println("La barra de vida cambia de color según lo que queda:");
        System.out.println("  " + lifeBar(16, 20) + "  más de la mitad");
        System.out.println("  " + lifeBar(8, 20) + "  entre la mitad y un cuarto");
        System.out.println("  " + lifeBar(3, 20) + "  menos de un cuarto: ¡cuidado!");

        System.out.println();

        System.out.println("Cada resultado lleva un símbolo delante:");
        System.out.println("  " + Color.AMARILLO + "»" + Color.RESET + " lo que haces tú     "
                                + Color.ROJO + "«" + Color.RESET + " lo que recibes     "
                                + Color.GRIS + "·" + Color.RESET + " un ataque fallado");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "POCIONES" + Color.RESET);

        System.out.println("  · Empiezas con 3 pociones. Cada una cura 5d8 de vida.");
        System.out.println("  · No se recuperan, así que úsalas con cabeza.");
        System.out.println("  · No puedes beber con la vida al máximo, y nunca superarás tu vida máxima.");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "CAMPAMENTO" + Color.RESET);

        System.out.println("El sistema te concede una mejora antes de la primera stage, y después");
        System.out.println("acampas tras cada victoria, salvo la última, para elegir otra:");
        System.out.println("  " + Color.option(1) + " +2 a la clase de armadura (máximo 18)");
        System.out.println("  " + Color.option(2) + " +1 al bonificador de ataque (máximo +6)");
        System.out.println("  " + Color.option(3) + " +1 dado de daño (máximo 5d8)");

        System.out.println("Si eliges una mejora que ya está al máximo, podrás elegir otra.");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "PUNTUACIÓN" + Color.RESET);

        System.out.println("  · Cada enemigo vale 100 puntos por su dificultad: 100, 200 o 300.");
        System.out.println("  · Cada turno que tardas en vencerlo resta un 5 %, hasta un máximo del 40 %.");
        System.out.println("    Ejemplo: vencer al enemigo intermedio en 5 turnos da 200 - 25 % = 150 puntos.");
        System.out.println("  · Toda partida terminada, incluso si caes o huyes, se guarda en el ranking.");
        System.out.println("  · El ranking guarda las 10 mejores. Si está lleno, solo entras superando a la décima.");

        System.out.println("\n" + Color.AMARILLO + Color.NEGRITA + "FIN DE PARTIDA" + Color.RESET);
        
        System.out.println("La aventura termina cuando derrotas al tercer enemigo (" + Color.VERDE + "VICTORIA" + Color.RESET + "),");
        System.out.println("cuando tu vida llega a 0 (" + Color.ROJO + "DERROTA" + Color.RESET + ") o cuando decides huir.");

        System.out.println(Color.separator() + "\n");
    }

    private static Hero newPlayer(Scanner sc) 
    {
        boolean nombreVacio = true;
        String nombre = "";

        sc.nextLine();                      // limpia el salto de línea que dejó readOption al elegir 

        do {
            System.out.print("\n" + Color.AMARILLO + Color.NEGRITA + "» " + Color.RESET + "¿Cómo se llama tu héroe? ");
            nombre = sc.nextLine().trim();   // .trim() quita los espacios de los extremos

            if (nombre.isEmpty())
            {
                System.out.println(Color.AMARILLO + "El nombre no puede estar vacío." + Color.RESET);
            }
            else
            {
                nombreVacio = false;
            }

        } while (nombreVacio);

        Hero heroe = new Hero(nombre);

        return heroe;
    }

    private static void upgradeStep(Scanner sc, Hero heroe)
    {
        boolean upgradeAplicado = false;
        do {
            System.out.println(Color.title("MEJORAS", Color.AMARILLO));
            System.out.println("   " + Color.option(1) + " +2 a la clase de armadura");
            System.out.println("   " + Color.option(2) + " +1 al bonificador de ataque");
            System.out.println("   " + Color.option(3) + " +1 dado de daño");
    
            int option = readOption(sc, 1, 3);
    
            switch (option) {
                case 1:
                    upgradeAplicado = heroe.upgradeClaseArmadura();
                break;
                case 2:
                    upgradeAplicado = heroe.upgradeBonificadorAtaque();
                break;
                case 3:
                    upgradeAplicado = heroe.upgradeDadosDanyo();
                break;
            
                default:
                break;
            }
        } while (!upgradeAplicado);

    }

    private static int readOption(Scanner sc, int numMin, int numMax) 
    {
        boolean numValido = false;
        int option = 0;

        do {
            System.out.print("\n" + Color.AMARILLO + Color.NEGRITA + "» " + Color.RESET + "¿Qué opción eliges? ");

            if (sc.hasNextInt()) {
                option = sc.nextInt();

                if (option >= numMin && option <= numMax) {
                    numValido = true;
                } else {
                    System.out.println(Color.AMARILLO + "Elige un número entre " + numMin + " y " + numMax + "." + Color.RESET);
                }
            } else {
                sc.next();
                System.out.println(Color.AMARILLO + "Escribe un número." + Color.RESET);
            }

        } while (!numValido);

        return option;
    }

    // -------- SIMULATE COMBAT --------
    private static int simulateCombat(Scanner sc, Hero heroe, Enemy enemigo)
    {
        boolean huida = false;
        int turnos = 0;

        while (heroe.isAlive() && enemigo.isAlive() && !huida) 
        {
            boolean endTurn = false;

            // Turno del Héroe
            do {

                showStatus(heroe, enemigo, turnos + 1);
                showCombatActions();

                int action = readOption(sc, 1, 4);
                System.out.println();
                
                switch (action) 
                {
                    case 1:
                        heroe.attack(enemigo);
                        endTurn = true;
                    break;

                    case 2:
                        int heal = heroe.usePotion();
                        if (heal != 0) 
                        {
                            System.out.println(Color.VERDE + "» El héroe toma una poción. Recuperas " + heal + " PV." + Color.RESET);
                            endTurn = true;
                        }
                    break;

                    case 3:
                        heroe.showInventory();
                    break;

                    case 4:
                        huida = true;
                        endTurn = true;
                    break;
                }
            // Fin turno heroe
            } while (!endTurn);

            turnos++;

            // Turno enemigo
            if (enemigo.isAlive() && !huida) {
                enemigo.attack(heroe);
            }
            else if (!enemigo.isAlive()) 
            {
                System.out.println(Color.ROJO + Color.NEGRITA + "■ " + enemigo.getNombre() + Color.RESET + " fue derrotado.");
            }
        }
        
        return turnos;
    }

    /**
     * Muestra el número de turno y la vida del héroe y del enemigo con sus barras.
     * @param heroe héroe del jugador
     * @param enemigo enemigo del combate actual
     * @param turno número del turno que se está jugando
     */
    private static void showStatus(Hero heroe, Enemy enemigo, int turno)
    {
        System.out.println("\n" + Color.title("TURNO " + turno, Color.AMARILLO));

        showLifeLine(heroe.getNombre(), Color.CIAN, heroe.getVida(), heroe.getVidaMaxima());
        showLifeLine(enemigo.getNombre(), Color.ROJO, enemigo.getVida(), enemigo.getVidaMaxima());

        System.out.println(Color.separator());
    }

    /**
    * Muestra las acciones disponibles en cada turno de combate.
    */
    private static void showCombatActions()
    {
        System.out.println(Color.option(1) + " Atacar    " + Color.option(2) + " Poción    "
                        + Color.option(3) + " Inventario    " + Color.option(4) + " Huir");
    }

    // -------- GAME --------
    private static void startGame(Scanner sc, Ranking ranking)
    {
        Hero heroe = newPlayer(sc);
        Enemy[] enemigos = Bestiary.generarEnemigos();

        int enemigosDerrotados = 0;
        int totalScore = 0;
        boolean haHuido = false;
        int i = 0;

        Story.showPrologue(heroe);
        upgradeStep(sc, heroe);

        while(i < enemigos.length && heroe.isAlive() && !haHuido)
        {
            Enemy enemigo = enemigos[i];

            Story.showStageIntro(i, enemigo.getNombre());
            
            int turnosUsados = simulateCombat(sc, heroe, enemigo);
            haHuido = heroe.isAlive() && enemigo.isAlive();

            if (!enemigo.isAlive()) 
            {
                // Cuando derrota al enemigo se abre la mejora
                enemigosDerrotados++;
                totalScore += calculateScore(enemigo.getDificultad(), turnosUsados);
                
                if (i < enemigos.length - 1) 
                {
                    Story.showCamp(i);
                    upgradeStep(sc, heroe);
                }
            }

            i++;
        }
        
        if (enemigosDerrotados == enemigos.length)
        {
            Story.showVictory();  
        }
        else if (haHuido)
        {
            Story.showEscape();
        }
        else
        {
            Story.showDefeat();
        }

        System.out.println("\n" + Color.title("RESUMEN", Color.AMARILLO));

        System.out.println(Color.leader("Enemigos derrotados", enemigosDerrotados + " de " + enemigos.length));
        System.out.println(Color.leader("Puntuación final", String.valueOf(totalScore)));
        
        ranking.add(heroe.getNombre(), totalScore); 
        ranking.save();

        System.out.println(Color.separator());
    }

    private static int calculateScore(int diffEnemigo, int turn)
    {
        int valorEnemigo = PUNTOS_BASE * diffEnemigo;
        int descuento = turn * PENALIZACION_POR_TURNO;

        if (descuento > DESCUENTO_MAXIMO)
        {
            descuento = DESCUENTO_MAXIMO;
        }
        return (valorEnemigo * (100 - descuento)) / 100;   // multiplicar antes de dividir
    }

    // -------- [ia] BARRA DE VIDA --------
    /**
    * [ia] Construye una barra de vida proporcional a la vida restante:
    * las casillas llenas (■) con el color de la vida y las vacías (·) en gris.
    * @param vida vida actual
    * @param vidaMaxima vida máxima
    * @return la barra, por ejemplo ■■■■■■■■■■■■····
    */
    private static String lifeBar(int vida, int vidaMaxima)
    {
        int llenos = (vida * LARGO_BARRA) / vidaMaxima;   // primero multiplicar: con enteros, dividir antes da 0

        if (vida > 0 && llenos == 0)
        {
            llenos = 1;                                   // si sigue vivo, que se vea al menos una casilla
        }

        String llenas = "";
        String vacias = "";
        for (int i = 0; i < LARGO_BARRA; i++)
        {
            if (i < llenos)
            {
                llenas += "■";
            }
            else
            {
                vacias += "·";
            }
        }

        return Color.lifeColor(vida, vidaMaxima) + llenas + Color.GRIS + vacias + Color.RESET;
    }

    /**
    * Muestra una línea del panel: nombre alineado y coloreado, barra de vida y cifras.
    * @param nombre nombre del personaje
    * @param colorNombre color del nombre (AZUL para el héroe, ROJO para el enemigo)
    * @param vida vida actual
    * @param vidaMaxima vida máxima
    */
    private static void showLifeLine(String nombre, String colorNombre, int vida, int vidaMaxima)
    {
        String nombreAlineado = String.format("%-15s", nombre);

        System.out.printf("%s %s %4d/%d%n",
                colorNombre + Color.NEGRITA + nombreAlineado + Color.RESET,
                lifeBar(vida, vidaMaxima),
                vida, vidaMaxima);
    }
}