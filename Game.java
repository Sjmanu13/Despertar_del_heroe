import java.util.Scanner;

public class Game {
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

    private static boolean simulateCombat(Scanner sc, Hero heroe, Enemy enemigo)
    {
        boolean huida = false;

        while (heroe.isAlive() && enemigo.isAlive() && !huida ) {

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
                        heroe.usePotion();
                        endTurn = true; // Preguntar al profesor
                    break;

                    case 3:
                        System.out.println("El heroe esta viendo el inventario");
                    break;

                    case 4:
                        huida = true;
                        endTurn = true;
                    break;

                    default:
                    break;
                }

            } while (!endTurn);
            if (enemigo.isAlive()) {
                enemigo.attack(heroe);
            }
            else{
                System.out.println(enemigo.getNombre() + " fue derrtoado.\n");
            }
        } 
        if (huida) 
        {
            System.out.println("\nEl heroe se vió superado por este mundo y decidió escapar");
            return huida;
        }
        

        return false;
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
        boolean haHuido = false;
        int i = 0;

        while(i < enemigos.length && heroe.isAlive() && !haHuido)
            {
                Enemy enemigo = enemigos[i];

                System.out.println("\n=== Combate " + (i + 1) + " de " + enemigos.length + ": aparece " + enemigo.getNombre() + " ===");

                haHuido = simulateCombat(sc, heroe, enemigo);

                if (!enemigo.isAlive()) {
                    enemigosDerrotados++;
                }

                i++;
            }
            if (haHuido) {
                System.out.println("El heroe huyó tras esa fatídica derrota y decidio retirarse de este mundo");
                System.out.println("TRY AGAIN");
            }
            if (!heroe.isAlive()) {
                System.out.println("El heroe cayó en combate y fue guiado por la diosa");
                System.out.println("TRY AGAIN");
            }
            if (enemigosDerrotados == enemigos.length) {
                System.out.println("El heroe superó las pruebas y pudo regresar a lo que podía llamar hogar");
                System.out.println("VICTORIA");
            }
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


    private static boolean endGame() 
    {
        return false;
    }
}