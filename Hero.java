public class Hero {

    // ATRIBUTOS FIJOS
    private static final int CARAS_VIDA = Dice.D10;
    private static final int DADOS_VIDA = 20; 
    
    private static final int CARAS_DANYO = Dice.D8;
    private static final int DADOS_DANYO_INICIAL = 3;
    private static final int DADOS_DANYO_MAXIMO = 5;

    private static final int BONIFICADOR_INICIAL = 3;
    private static final int CA_BASE = 12;

    private static final int NUM_POCIONES = 3;
    private static final int CARAS_CURACION = Dice.D8;
    private static final int DADOS_CURACION = 5;
    
    // ATRIBUTOS VARIABLES
    private String nombre;

    private int vida;
    private int vidaMaxima;

    private int dadosDanyo;
    private int claseArmadura;
    private int bonificadorAtaque;

    private int[] potions;
    private int potionRemaining;
    
    // ---------------- CONSTRUCTOR ----------------
    public Hero (String nombre, int vida, int dadosDanyo)
    {
        this.nombre = nombre;
        this.vida = vida;
        this.dadosDanyo  = dadosDanyo;
        this.claseArmadura = CA_BASE;
    }

    // ---------------- METODOS ----------------
    public int rollDamage()
    {
        return Dice.roll(dadosDanyo, CARAS_DANYO);
    }

    // (setVida), baja vida actual
    public void takeDamage(int cantidadDanyo) 
    {
        vida -= cantidadDanyo;
        if (vida < 0) {
            vida = 0;
        }
    }

    public boolean isAlive()
    {
        return vida > 0;
    }
}
