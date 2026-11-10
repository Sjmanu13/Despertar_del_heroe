public class Hero implements IBasico{

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
    public Hero (String nombre)
    {
        this.nombre = nombre;

        this.vidaMaxima = Dice.roll(DADOS_VIDA, CARAS_VIDA);
        this.vida = vidaMaxima;

        this.dadosDanyo  = DADOS_DANYO_INICIAL;
        this.claseArmadura = CA_BASE;
        this.bonificadorAtaque = BONIFICADOR_INICIAL;

        this.prepareInventory();
    }


    
    // ---------------- GETTER ----------------
    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getDadosDanyo() {
        return dadosDanyo;
    }

    public int getClaseArmadura() {
        return claseArmadura;
    }

    public int getBonificadorAtaque() {
        return bonificadorAtaque;
    }

    public int[] getPotions() {
        return potions;
    }
    
    // ---------------- SETTER ----------------
    /**
    * (setDadosDanyo) aumenta la cantidad de dados al atacar
    * Suma +1 a dadosDanyo
    */
    public void upgradeDadosDanyo(int dadosDanyo) {
        this.dadosDanyo = dadosDanyo;
    }

    /**
    * (setClaseArmadura) aumenta la CA
    * Suma +1 a claseArmadura
    */
    public void upgradeClaseArmadura(int claseArmadura) {
        this.claseArmadura = claseArmadura;
    }

    /**
    * (setBonificadorAtaque) aumenta el bonif. de ataque
    * Suma +1 a bonificadorAtaque
    */
    public void upgradeBonificadorAtaque(int bonificadorAtaque) {
        this.bonificadorAtaque = bonificadorAtaque;
    }

    /**
    * (setVida) baja la vida actual
    * Recibe daño del enemigo y se le resta a su vida actual
    * Si el daño es letal o negativo, su vida se queda en 0
    */
    public void takeDamage(int cantidadDanyo) 
    {
        vida -= cantidadDanyo;
        if (vida < 0) {
            vida = 0;
        }
    }

    /**
    * (setVida) sube la vida actual
    * Bebe la última poción disponible: cura sin superar la vida máxima y la retira del inventario.
    * Antes de llamarlo hay que comprobar tienePociones() y vidaLlena().
    * @return vida recuperada
    */
    public int usePotion(int[] potions)
    {
    int posicion = this.potionRemaining - 1;                  // la última poción ocupada
    int curacion = Dice.roll(this.potions[posicion], CARAS_CURACION);

    int vidaAntes = this.vida;
    this.vida += curacion;
    if (this.vida > this.vidaMaxima) {
        this.vida = this.vidaMaxima;                               // nunca supera el máximo
    }

    this.potions[posicion] = 0;                               // la posición queda vacía
    this.potionRemaining--;

    return this.vida - vidaAntes;
    }

// ---------------- METODOS ----------------

    // Rellenar pociones iniciales
    public void prepareInventory()
    {
        potions = new int[NUM_POCIONES];

        for (int i = 0; i < NUM_POCIONES; i++) 
        {
        potions[i] = DADOS_CURACION; // Añado cuántos dados cura cada poción
        }

        potionRemaining = NUM_POCIONES;
    }

    public int rollDamage()
    {
        return Dice.roll(dadosDanyo, CARAS_DANYO);
    }

    // ---------- CHECKS ----------
    /**
    * Indica si se acierta el goolpe (D20 + bon.atak > CA del objetivo)
    * @return true si supera el CA
    */
    public boolean acierta(int caObjetivo) 
    {
        int tirada = Dice.roll(1, Dice.D20) + getBonificadorAtaque();
        return tirada >= caObjetivo;
    }

    /**
    * Indica si al héroe le queda alguna poción.
    * @return true si queda al menos una
    */
    public boolean tienePociones() {
    return potionRemaining > 0;
    }

    /**
    * Indica si el héroe tiene la vida al máximo.
    * @return true si la vida actual es igual a la máxima
    */
    public boolean vidaLlena() {
    return vida == vidaMaxima;
    }


    public boolean isAlive()
    {
        return vida > 0;
    }
}
