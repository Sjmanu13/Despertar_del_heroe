// Interfaz para añadir métodos comunes de Hero y Enemy
public interface IBasico 
{
    public String getNombre();
    public int getClaseArmadura();
    public void takeDamage(int cantidadDanyo);
    public boolean rollToHit(int caObjetivo);
    public int rollDamage();
    public void attack(IBasico objetivo);
    public boolean isAlive();
}
