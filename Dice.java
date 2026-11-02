import java.util.Random;

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

    // Cantidad de lanzamientos de dados en una acción
    public static int[] rollEach(int cantidad, int caras) 
    {
        int[] tiradas = new int[cantidad];

        for (int i = 0; i < cantidad; i++) 
        {
            tiradas[i] = RANDOM.nextInt(caras) + 1; // nextInt da de 0 a caras-1; +1 lo pasa de 1 a caras
        }

        return tiradas;
    }

    // Acumulador del resultado de rollEach
    public static int sumaDados(int[] tiradas) 
    {
        int suma = 0;

        for (int i = 0; i < tiradas.length; i++) 
        {
            suma += tiradas[i];
        }

        return suma;
    }

    // Método de Lanzamiento
    public static int roll(int cantidad, int caras) {
        return sumaDados(rollEach(cantidad, caras));
    }
}
