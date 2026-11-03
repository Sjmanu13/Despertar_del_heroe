public class Bestiary {
    private static final int[] ENCUENTROS_PARTIDA = {Enemy.FACIL, Enemy.INTERMEDIO, Enemy.DIFICIL};

    // Predefinición de las fichas de los enemigos
    private static final Enemy[] FICHAS = 
    {
        new Enemy("Goblin",       Enemy.FACIL,      4, 2),
        new Enemy("Kobold",       Enemy.FACIL,      4, 2),
        new Enemy("Rata gigante", Enemy.FACIL,      3, 3),
        new Enemy("Orco",         Enemy.INTERMEDIO, 6, 3),
        new Enemy("Gnoll",        Enemy.INTERMEDIO, 6, 3),
        new Enemy("Bruto",        Enemy.INTERMEDIO, 5, 4),
        new Enemy("Ogro",         Enemy.DIFICIL,    8, 4),
        new Enemy("Troll",        Enemy.DIFICIL,    9, 3),
        new Enemy("Minotauro",    Enemy.DIFICIL,    7, 5)
    };

    // -------------------- MÉTODOS ----------------------

    // Recojo los enemigos aleatorios por nivel y lo asigno a un array para enviar a Game.java
    public static Enemy[] generarEnemigos() {
        Enemy[] enemigos = new Enemy[ENCUENTROS_PARTIDA.length];

        for (int i = 0; i < ENCUENTROS_PARTIDA.length; i++) 
        {
            Enemy fichaEnemy = elegirFicha(ENCUENTROS_PARTIDA[i]);
            enemigos[i] = copiarFicha(fichaEnemy);
        }

        return enemigos;
    }

    /**
    * Crea y asigna el enemigo según su dificultad, si no es de la dificultad pedida, 
    * repite el proceso hasta que lo sea
     */
    private static Enemy elegirFicha(int dificultad) 
    {
        Enemy ficha;

        do 
        {
            int posicionRandom = Dice.roll(1, FICHAS.length) - 1; // Randomiza entre 0 y 8 (9)-1)
            ficha = FICHAS[posicionRandom];
        } while (ficha.getDificultad() != dificultad); // Asegurar que el enemigo es de la dificultad pedida

        return ficha; // Te devuelve la clase enemigo
    }

    // Copia la ficha predefinida dentro del array que se enviará a Game.java
    private static Enemy copiarFicha(Enemy fichaEnemy) 
    {
        return new Enemy(
                        fichaEnemy.getNombre(), 
                        fichaEnemy.getDificultad(),
                        fichaEnemy.getDadosVida(), 
                        fichaEnemy.getDadosDanyo()
                        );
    }
}
