public class Enemy 
{
    // Nivel de dificultad
    public static final int FACIL = 1;
    public static final int INTERMEDIO = 2;
    public static final int DIFICIL = 3;

    // Dados para Enemigos
    private static final int DADOS_VIDA = Dice.D20; // Vida usa Xd20
    private static final int DADOS_DANYO = Dice.D6;   // Daño usa Xd6

    // Rango de clase de armadura y bonificador de ataque por dificultad
    private static final int[] CA_MINIMA = {8, 10, 12};
    private static final int[] CA_MAXIMA = {10, 12, 14};
    private static final int[] BONO_ATAQUE = {2, 3, 4};

    // Atributos: lo que cambia de un enemigo a otro
    private String nombre;
    private int dificultad;      // FACIL, INTERMEDIO o DIFICIL
    private int dadosVida;       // cuántos d20 tira para calcular su vida
    private int dadosDanyo;      // cuántos d6 tira al atacar
    private int vida;            // vida actual: la que baja con cada golpe
    private int claseArmadura;   // lo difícil que es golpearlo
}
