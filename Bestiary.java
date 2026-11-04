public class Bestiary {
    // Array para ordenar los encuentros según la dificultad
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

    /**
    * Definicion de los enemigos para partida nueva
    * Registra los enemigos seleccionados
    * @return  Array con los enemigos
    */
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
    * Elige aleatoriamente una ficha del catálogo con la dificultad indicada.
    * Repite el proceso hasta encontrar una de esa dificultad
    * @param dificultad FACIL, INTERMEDIO o DIFICIL
    * @return  clase enemigo que cumple lo pedido
    */
    private static Enemy elegirFicha(int dificultad) 
    {
        Enemy fichaCandidata;

        do 
        {
            int posicionRandom = Dice.roll(1, FICHAS.length) - 1; // Randomiza entre 0 y 8 (9)-1)
            fichaCandidata = FICHAS[posicionRandom];
        } while (fichaCandidata.getDificultad() != dificultad); // Asegurar que el enemigo es de la dificultad pedida

        return fichaCandidata; // Te devuelve la clase enemigo que corresponde
    }

    /**
    * Recoge los atributos de la clase del catálogo
    * Redefine dentro de una nueva clase Enemy para almacenarlo
    * @param fichaEnemy Enemigo elegido en método (elegirFicha())
    * @return la ficha elegida (el objeto del catálogo, no una copia)
    */
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
