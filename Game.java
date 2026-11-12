import java.util.Scanner;

public class Game {
    // Atributos para puntuación
    private static final int PUNTOS_BASE = 100;
    private static final int PENALIZACION_POR_TURNO = 5;
    private static final int DESCUENTO_MAXIMO = 40;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        mainMenu(sc);
        
        sc.close();
    }

    // ----------- Métodos -----------

    private static void mainMenu(Scanner sc) {
        boolean finJuego = false;

        do {
            System.out.println("================================");
            System.out.println("Bienvenido/a a Pick me up");
            System.out.println("\n1.    Nueva Partida");
            System.out.println("2.    Mostrar Ranking");
            System.out.println("3.    Guía");
            System.out.println("4.    Salir Juego\n");
            System.out.println("================================");


            int option = readOption(sc, 1, 4);

            switch (option) {

                case 1:
                    startGame(sc);
                    break;

                case 2:
                    System.out.println("Ranking Proximamente");
                    break;

                case 3:
                    showHelp();
                    break;

                case 4:
                    System.out.println("Hasta la vista heroe");
                    finJuego = true;
                    break;

                default:
                    break;
            }

        } while (!finJuego);

    }

    private static Hero newPlayer(Scanner sc) 
    {
        boolean nombreVacio = true;
        String nombre = "";

        sc.nextLine();                      // limpia el salto de línea que dejó readOption al elegir 

        do {
            System.out.print("¿Cómo se llama tu héroe? ");
            nombre = sc.nextLine().trim();   // .trim() quita los espacios de los extremos

            if (nombre.isEmpty())
            {
                System.out.println("El nombre no puede estar vacío.");
            }
            else
            {
                nombreVacio = false;
            }

        } while (nombreVacio);

        Hero heroe = new Hero(nombre);
        System.out.println("\n¡Bienvenido/a, " + heroe.getNombre() + "!");
        System.out.println("Vida: " + heroe.getVida() + " | CA: " + heroe.getClaseArmadura());

        return heroe;
    }

    private static int simulateCombat(Scanner sc, Hero heroe, Enemy enemigo)
    {
        boolean huida = false;
        int turnos = 0;

        while (heroe.isAlive() && enemigo.isAlive() && !huida ) 
        {
            boolean endTurn = false;

            // Turno Heroe
            do {

                showStatus(heroe, enemigo);
                showCombatActions();

                int action = readOption(sc, 1, 4);
                
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
                            System.out.println("El heroe toma una poción. Recuperas " + heal + " PV.");
                            endTurn = true; // Preguntar al profesor
                        }
                    break;

                    case 3:
                        System.out.println("El heroe esta viendo el inventario");
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
            else if (!enemigo.isAlive()) {
            System.out.println(enemigo.getNombre() + " fue derrotado.\n");
            }
        }
        
        return turnos;
    }

    /**
    * Muestra la vida del héroe y del enemigo al inicio de cada turno.
    * @param heroe héroe del jugador
    * @param enemigo enemigo del combate actual
    */
    private static void showStatus(Hero heroe, Enemy enemigo)
    {
        System.out.println("\n--------------------------------");
        System.out.println(heroe.getNombre() + ": " + heroe.getVida() + "/" + heroe.getVidaMaxima() + " PV");
        System.out.println(enemigo.getNombre() + ": " + enemigo.getVida() + " PV");
        System.out.println("--------------------------------");
    }

    private static void showCombatActions()
    {
        System.out.println("\n¿Qué es  lo que hará el heroe?");
        System.out.println("\n--------------------------------");
        System.out.println("1. Atacar");
        System.out.println("2. Usar Poción");
        System.out.println("3. Ver Inventario");
        System.out.println("4. Huir");
        System.out.println("--------------------------------");

    }

    private static void startGame(Scanner sc)
    {
        Hero heroe = newPlayer(sc);
        Enemy[] enemigos = Bestiary.generarEnemigos();

        int enemigosDerrotados = 0;
        int totalScore = 0;
        boolean haHuido = false;
        int i = 0;

        System.out.println(" ");
        upgradeStep(sc, heroe);

        while(i < enemigos.length && heroe.isAlive() && !haHuido)
        {
            Enemy enemigo = enemigos[i];

            System.out.println("\n=== Combate " + (i + 1) + " de " + enemigos.length + ": aparece " + enemigo.getNombre() + " ===");
            
            int turnosUsados = simulateCombat(sc, heroe, enemigo);
            haHuido = heroe.isAlive() && enemigo.isAlive();

            if (!enemigo.isAlive()) 
            {
                // Cuando derrota al enemigo se abre la mejora
                enemigosDerrotados++;
                totalScore += calculateScore(enemigo.getDificultad(), turnosUsados);
                
                if (i < enemigos.length -1) 
                {
                    System.out.println("Subiste de piso y te sientes con más fuerzas después de haber derrotado a aquel monstruo.");
                    upgradeStep(sc, heroe);
                }
            }

            i++;
        }
        
        if (enemigosDerrotados == enemigos.length)
        {
            System.out.println("\nEl héroe superó las pruebas y pudo regresar a lo que podía llamar hogar.");
            System.out.println("VICTORIA");
        }
        else if (haHuido)
        {
            System.out.println("\nEl héroe huyó tras esa fatídica derrota y decidió retirarse de este mundo.");
            System.out.println("TRY AGAIN");
        }
        else
        {
            System.out.println("\nEl héroe cayó en combate y fue guiado por la diosa.");
            System.out.println("TRY AGAIN");
        }

        System.out.println("Enemigos derrotados: " + enemigosDerrotados + " de " + enemigos.length);
        System.out.println("Puntuación final: " + totalScore);
    }

    private static void upgradeStep(Scanner sc, Hero heroe)
    {
        boolean upgradeAplicado = false;
        do {
            System.out.println("Tu heroe se ha fortalecido, elige su mejora: ");
            System.out.println("1. +2 en Clase de Armadura");
            System.out.println("2. +1 Bonificador de Ataque");
            System.out.println("3. +1 Dado de ataque");
    
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

    private static void showHelp() 
    {
        System.out.println("\n============== GUÍA ==============");

        System.out.println("\nDE QUÉ TRATA");
        System.out.println("Eres un héroe que se adentra en tierras peligrosas.");
        System.out.println("En tu camino te esperan tres enemigos, cada uno más fuerte que el anterior.");
        System.out.println("Los enemigos se eligen al azar en cada partida, así que ninguna aventura es igual.");

        System.out.println("\nTU HÉROE");
        System.out.println("- Vida: se tira al empezar la partida (20d10).");
        System.out.println("- Daño: 3d8 por golpe.");
        System.out.println("- Clase de armadura (CA): 12. Cuanto más alta, más difícil es que te golpeen.");
        System.out.println("- Bonificador de ataque: +3.");

        System.out.println("\nEL COMBATE");
        System.out.println("El combate es por turnos. En cada turno puedes:");
        System.out.println(
                "  1. Atacar: tiras 1d20 + tu bonificador. Si igualas o superas la CA del enemigo, le haces daño.");
        System.out.println("  2. Beber una poción: recuperas vida.");
        System.out.println("  3. Ver el inventario: consultas las pociones que te quedan.");
        System.out.println("  4. Abandonar: terminas la aventura.");
        System.out.println("Tras atacar o beber una poción, el enemigo te devuelve el golpe si sigue en pie.");
        System.out.println("Ver el inventario, elegir una opción no válida o intentar curarte sin poder");
        System.out.println("no gastan turno: el enemigo no atacará.");

        System.out.println("\nLAS POCIONES");
        System.out.println("- Empiezas con 3 pociones. Cada una cura 5d8 de vida.");
        System.out.println("- No se recuperan: así que se deben utilizar con cabeza.");
        System.out.println("- No puedes beber si tienes la vida al máximo, y nunca superarás tu vida máxima.");

        System.out.println("\nEL CAMPAMENTO");
        System.out.println("Después de cada victoria, salvo la última, acampas y eliges:");
        System.out.println("  - Descanso: recuperas parte de tu vida.");
        System.out.println("  - Mejora: aumentas tu daño (+1d8), tu bonificador de ataque (+2) o tu CA (+2).");

        System.out.println("\nPUNTUACIÓN");
        System.out.println("- Cada enemigo derrotado te da 100 puntos.");
        System.out.println("- Si derrotas a los tres, sumas también la vida que te quede.");
        System.out.println("- Toda partida terminada, incluso si abandonas, se guarda en el ranking.");

        System.out.println("\nFIN DE LA PARTIDA");
        System.out.println("La aventura termina cuando derrotas al tercer enemigo, cuando tu vida llega a 0");
        System.out.println("o cuando decides abandonar.");

        System.out.println("==================================\n");
    }

    // ----------- CHECK -----------
    private static int readOption(Scanner sc, int numMin, int numMax) 
    {
        boolean numValido = false;
        int option = 0;

        do {
            System.out.print("\n¿Que opción eliges? ");

            if (sc.hasNextInt()) {
                option = sc.nextInt();

                if (option >= numMin && option <= numMax) {
                    numValido = true;
                } else {
                    System.out.println("Elige una número entre " + numMin + " y " + numMax + ".");
                }
            } else {
                sc.next();
                System.out.println("Escribe un número");
            }

        } while (!numValido);

        return option;
    }

    private static int calculateScore( int diffEnemigo, int turn)
    {
        int valorEnemigo = PUNTOS_BASE * diffEnemigo;
        int descuento = turn * PENALIZACION_POR_TURNO;

        if (descuento > DESCUENTO_MAXIMO)
        {
            descuento = DESCUENTO_MAXIMO;
        }
        return valorEnemigo * (100 - descuento) / 100;   // multiplicar antes de dividir
    }
}