// Interfaz para añadir métodos comunes de Hero y Enemy
public interface IBasico 
{
    public void takeDamage(int cantidadDanyo);
    public boolean acierta(int caObjetivo);
    public int rollDamage();
    public boolean isAlive();
}
