/**
* Enemigo del combate. Su vida y su CA se tiran al crearlo
* según la dificultad (FACIL, INTERMEDIO o DIFICIL).
*/
public class Enemy implements IBasico
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
    private int vida;            // Vida actual
    private int vidaMaxima;      // Vida máxima
    private int claseArmadura;   // Confrontación para hacer el daño

    // ---------------- CONSTRUCTOR ----------------
    /**
    * Crea un enemigo y tira su vida (Xd20) y su clase de armadura.
    * @param nombre nombre del enemigo
    * @param dificultad FACIL, INTERMEDIO o DIFICIL
    * @param dadosVida cuántos d20 tira para la vida
    * @param dadosDanyo cuántos d6 tira al atacar
    */
    public Enemy (String nombre, int dificultad, int dadosVida, int dadosDanyo)
    {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.dadosVida = dadosVida;
        this.dadosDanyo = dadosDanyo;
        this.vida = Dice.roll(dadosVida, DADOS_VIDA);
        this.vidaMaxima = vida;
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

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getClaseArmadura() {
        return claseArmadura;
    }

    public int getBonoAtaque() {
        return BONO_ATAQUE[dificultad - 1];
    }

    // ---------------- METODOS ----------------

    /**
    * Calcula una CA aleatoria dentro del rango de su dificultad.
    * @param dificultad FACIL, INTERMEDIO o DIFICIL
    * @return CA entre CA_MINIMA y CA_MAXIMA de esa dificultad
    */
    private int registrarClaseArmadura(int dificultad) 
    {

        // Para determinar el array de 0 a 2, no de 1 a 3
        int posicion = dificultad - 1;

        int min = CA_MINIMA[posicion]; 
        int max = CA_MAXIMA[posicion];

        // Devuelve random entre número min y max
        return min + Dice.roll(1, max - min + 1) - 1;   
    }

    /**
    * Ataca al objetivo: si acierta, tira el daño y se lo resta.
    * @param objetivo combatiente que recibe el ataque
    */
    public void attack(IBasico objetivo)
    {

        if(!rollToHit(objetivo.getClaseArmadura()) )
        {
            System.out.println(Color.GRIS + "· ¡Falló!" + Color.RESET);
        }
        else
        {
            int danyo = rollDamage();
            objetivo.takeDamage(danyo);
            System.out.println(Color.ROJO + "« Recibiste " + danyo + " de daño." + Color.RESET);
        }
        
    }

    /**
    * Tira 1d20 + bonificador de ataque contra la CA del  objetivo.
    * @param caObjetivo clase de armadura del objetivo
    * @return true si la tirada iguala o supera la CA
    */
    public boolean rollToHit(int caObjetivo)
    {
        int tirada = Dice.roll(1, Dice.D20) + getBonoAtaque();
        return tirada >= caObjetivo;
    }

    /**
    * Resta el daño a la vida actual, que nunca baja de 0.
    * @param cantidadDanyo daño recibido
    */
    public void takeDamage(int cantidadDanyo) 
    {
        vida -= cantidadDanyo;
        if (vida < 0) {
            vida = 0;
        }
    }

    /**
    * @return true si al enemigo le queda vida
    */
    public boolean isAlive() 
    {
        return vida > 0;
    }

    /**
    * Tira el daño del enemigo (Xd6).
    * @return daño total
    */
    public int rollDamage() {
        return Dice.roll(dadosDanyo, DADOS_DANYO);
    }
}
