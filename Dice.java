import java.util.Random;

/**
* Tiradas de dados con la notación XdY de los juegos de rol.
* Las constantes D4…D100 indican las caras de cada tipo dedado.
*/
public class Dice 
{
    public static final int D4 = 4;
    public static final int D6 = 6;
    public static final int D8 = 8;
    public static final int D10 = 10;
    public static final int D12 = 12;
    public static final int D20 = 20;
    public static final int D100 = 100;

    private static final Random RANDOM = new Random();

    /**
    * Lanza varios dados y guarda el resultado de cada uno.
    * @param cantidad número de dados que se lanzan
    * @param caras caras de cada dado
    * @return array con el resultado de cada dado, entre 1 y caras
    */
    public static int[] rollEach(int cantidad, int caras) 
    {
        int[] tiradas = new int[cantidad];

        for (int i = 0; i < cantidad; i++) 
        {
            tiradas[i] = RANDOM.nextInt(caras) + 1; // nextInt da de 0 a caras-1; +1 lo pasa de 1 a caras
        }

        return tiradas;
    }

    /**
    * Suma los resultados de una tirada.
    * @param tiradas resultados de cada dado
    * @return suma total
    */
    public static int sumaDados(int[] tiradas) 
    {
        int suma = 0;

        for (int i = 0; i < tiradas.length; i++) 
        {
            suma += tiradas[i];
        }

        return suma;
    }

    /**
    * Lanza varios dados y devuelve la suma (tirada XdY).
    * @param cantidad número de dados
    * @param caras caras de cada dado
    * @return suma de los dados
    */
    public static int roll(int cantidad, int caras) {
        return sumaDados(rollEach(cantidad, caras));
    }
}
