public class Hero implements IBasico{

    // -=-=-=-=-=-=- ATRIBUTOS FIJOS -=-=-=-=-=-=-
    private static final int CARAS_VIDA = Dice.D10;
    private static final int DADOS_VIDA = 20; 
    
    private static final int CARAS_DANYO = Dice.D8;
    private static final int DADOS_DANYO_INICIAL = 3;
    private static final int DADOS_DANYO_MAXIMO = 5;

    private static final int BONIFICADOR_INICIAL = 3;
    private static final int BONIFICADOR_ATAQUE_MAXIMO = 6;

    private static final int MEJORA_CA = 2;
    private static final int CA_BASE = 12;
    private static final int CA_MAXIMO = 18;

    private static final int NUM_POCIONES = 3;
    private static final int CARAS_CURACION = Dice.D8;
    private static final int DADOS_CURACION = 5;
    
    // -=-=-=-=-=-=- ATRIBUTOS VARIABLES -=-=-=-=-=-=-
    private String nombre;

    private int vida;
    private int vidaMaxima;

    private int dadosDanyo;
    private int claseArmadura;
    private int bonificadorAtaque;

    private int[] potions;
    private int potionRemaining;
    
    // -=-=-=-=-=-=- CONSTRUCTOR -=-=-=-=-=-=-
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
    
    // -=-=-=-=-=-=- GETTER -=-=-=-=-=-=-
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

    // -=-=-=-=-=-=- METODOS -=-=-=-=-=-=-

    // -------- UPGRADES --------
    /**
    * Aumenta la cantidad de dados al atacar
    * Añade +1 a dadosDanyo
    */
    public boolean upgradeDadosDanyo() 
    {
        if (getDadosDanyo() < DADOS_DANYO_MAXIMO) 
        {
            dadosDanyo++;
            return true;
        }
        else
        {
            System.out.println("Tu daño ya está al máximo");
        }
        return false;
    }

    /**
    * Mejora la CA +2, sin superar el máximo.
    * @return true si se aplicó; false si ya estaba al máximo
    */
    public boolean upgradeClaseArmadura() 
    {
        if (getClaseArmadura() < CA_MAXIMO) 
        {
            claseArmadura = claseArmadura + MEJORA_CA;
            if (getClaseArmadura() > CA_MAXIMO) 
            {
                claseArmadura = CA_MAXIMO;
            }
            return true;
        }
        else
        {
            System.out.println("Ya llegó al límite");
        }
        return false;
    }

    /**
    * Mejora el bonificador de ataque +1, sin superar el máximo.
    * @return true si se aplicó; false si ya estaba al máximo
    */
    public boolean upgradeBonificadorAtaque() 
    {
        if (getBonificadorAtaque() < BONIFICADOR_ATAQUE_MAXIMO) 
        {
            bonificadorAtaque++;
            return true;
        }
        else
        {
            System.out.println("Ya llegó al límite");
        }
        return false;
    }

    // -------- START GAME --------
    private void prepareInventory()
    {
        potions = new int[NUM_POCIONES];

        for (int i = 0; i < NUM_POCIONES; i++) 
        {
        potions[i] = DADOS_CURACION; // Añado cuántos dados cura cada poción
        }

        potionRemaining = NUM_POCIONES;
    }

    /**
    * Muestra las pociones que le quedan al héroe, recorriendo solo las posiciones no usadas.
    * Si no queda ninguna, muestra un aviso.
    */
    public void showInventory()
    {
        System.out.println("\n--- INVENTARIO ---");

        if (potionRemaining == 0)
        {
            System.out.println("Tu inventario está vacío.");
        }
        else
        {
            for (int i = 0; i < potionRemaining; i++)
            {
                System.out.println("Poción " + (i + 1) + ": cura " + potions[i] + "d" + CARAS_CURACION);
            }
        }

        System.out.println("Pociones restantes: " + potionRemaining + " de " + NUM_POCIONES);
    }

    // -------- COMBAT --------

    public int rollDamage()
    {
        return Dice.roll(dadosDanyo, CARAS_DANYO);
    }

    public void attack(IBasico objetivo)
    {

        if(!rollToHit(objetivo.getClaseArmadura()) )
        {
            System.out.println("Fallaste! ");
        }
        else
        {
            int danyo = rollDamage();
            objetivo.takeDamage(danyo);
            System.out.println("Inflingiste " + danyo + " de daño");
        }
    }

    /**
    * (setVida) sube la vida actual
    * Bebe la última poción disponible: cura sin superar la vida máxima y la retira del inventario.
    * Antes de llamarlo hay que comprobar tienePociones() y vidaLlena().
    * @return vida recuperada
    */
    public int usePotion()
    {
        if (!hasPotions())
    {
        System.out.println("No te quedan pociones.");
        return 0;
    }

    if (isFullHealth())
    {
        System.out.println("Ya tienes la vida al máximo. No necesitas beber.");
        return 0;
    }

    int posicion = potionRemaining - 1;                  // la última poción ocupada
    int curacion = Dice.roll(potions[posicion], CARAS_CURACION);

    int vidaAntes = vida;
    vida += curacion;
    if (vida > vidaMaxima) {
        vida = vidaMaxima;                               // nunca supera el máximo
    }

    potions[posicion] = 0;                               // la posición queda vacía
    potionRemaining--;

    return vida - vidaAntes;
    }

    /**
    * Baja la vida actual
    * Recibe daño del enemigo y se le resta a su vida actual
    * Si el daño es letal, su vida se queda en 0
    */
    public void takeDamage(int cantidadDanyo) 
    {
        vida -= cantidadDanyo;
        if (vida < 0) {
            vida = 0;
        }
    }

    // ---------- CHECKS ----------
    /**
    * Indica si se acierta el golpe (D20 + bon.attack >= CA del objetivo)
    * @param caObjetivo
    * @return true si supera el CA
    */
    public boolean rollToHit(int caObjetivo) 
    {
        int tirada = Dice.roll(1, Dice.D20) + getBonificadorAtaque();
        return tirada >= caObjetivo;
    }

    /**
    * Indica si al héroe le queda alguna poción.
    * @return true si queda al menos una
    */
    public boolean hasPotions() {
    return potionRemaining > 0;
    }

    /**
    * Indica si el héroe tiene la vida al máximo.
    * @return true si la vida actual es igual a la máxima
    */
    public boolean isFullHealth() {
    return vida == vidaMaxima;
    }

    public boolean isAlive()
    {
        return vida > 0;
    }
}
