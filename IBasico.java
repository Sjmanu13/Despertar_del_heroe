/**
* Métodos comunes de los combatientes (Hero y Enemy),
* para que uno pueda atacar al otro sin conocer su clase.
*/
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
