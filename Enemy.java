public class Enemy 
{
    // Nivel de dificultad
    public static final int FACIL = 1;
    public static final int INTERMEDIO = 2;
    public static final int DIFICIL = 3;

    // Dados principales de enemigo (Vida Xd20 || Daño Xd6)
    private static final int DADOS_VIDA = Dice.D20; 
    private static final int DADOS_DANYO = Dice.D6;   

    // Rango de clase de armadura y bonificador de ataque por dificultad
    // Utilizo arrays para determinar mejor según su dificultad
    private static final int[] CA_MINIMA = {8, 10, 12};
    private static final int[] CA_MAXIMA = {10, 12, 14};
    private static final int[] BONO_ATAQUE = {2, 3, 4};

    // Atributos variables: lo que cambia de un enemigo a otro
    private String nombre;
    private int dificultad;      // FACIL, INTERMEDIO o DIFICIL
    private int dadosVida;       // Cuántos d20 tira para calcular su vida
    private int dadosDanyo;      // Cuántos d6 tira al atacar
    private int vida;            // Vida actual: la que baja con cada golpe
    private int claseArmadura;   // Confrontación para hacer el daño

    // ---------------- CONSTRUCTOR ----------------
    public Enemy (String nombre, int dificultad, int dadosVida, int dadosDanyo)
    {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.dadosVida = dadosVida;
        this.dadosDanyo = dadosDanyo;
        this.vida = Dice.roll(dadosVida, DADOS_VIDA);
        this.claseArmadura = registrarClaseArmadura(dificultad);
    }

    // ---------------- GETTER ----------------
    public String getNombre() {
        return nombre;
    }

    public int getDificultad() {
        return dificultad;
    }

    public int getDadosVida() {
        return dadosVida;
    }

    public int getDadosDanyo() {
        return dadosDanyo;
    }

    public int getVida() {
        return vida;
    }

    public int getClaseArmadura() {
        return claseArmadura;
    }

    public int getBonoAtaque() {
        return BONO_ATAQUE[dificultad - 1];
    }

    // ---------------- METODOS ----------------

    // Registrar CA aleatorio según la dificultad del enemigo
    private int registrarClaseArmadura(int dificultad) {

        // Para determinar el array de 0 a 2, no de 1 a 3
        int posicion = dificultad - 1;

        int min = CA_MINIMA[posicion]; 
        int max = CA_MAXIMA[posicion];

        // Devuelve random entre número min y max
        return min + Dice.roll(1, max - min + 1) - 1;   
    }

    // Comprobación si el golpe acierta (D20 + mod. ataque > clase_armadura)
    // @return true si el total iguala o supera esa clase de armadura
    public boolean acierta(int caObjetivo) {
        int tirada = Dice.roll(1, Dice.D20) + getBonoAtaque();
        return tirada >= caObjetivo;
    }

    // (setVida), baja vida actual del enemigo
    public void takeDamage(int cantidadDanyo) {
        vida -= cantidadDanyo;
        if (vida < 0) {
            vida = 0;
        }
    }

    // Comprobación de si el enemigo sigue vivo
    // @return "true" si aún le queda vida
    public boolean isAlive() {
        return vida > 0;
    }

    // Ataque del enemigo (cantidad de dados, daño de cada dado)
    // @return suma el daño total
    public int rollDamage() {
        return Dice.roll(dadosDanyo, DADOS_DANYO);
    }
}
