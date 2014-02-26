package futbolEnPapel2013.estructura;

/**
 * Clase que representa una posición en el tablero
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Posicion {

    private int x;
    private int y;

    /**
     * Constructor completo
     * @param posX El valor por defecto de x
     * @param posY El valor por defecto de y
     */
    public Posicion(int posX, int posY) {
        x = posX;
        y = posY;
    }

    /**
     * Métodos get y set
     */

    /**
     * Método get del atributo x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Método set del atributo x
     * @param posX El nuevo valor de x
     */
    public void setX(int posX) {
        x = posX;
    }

    /**
     * Método get del atributo y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Método set del atributo y
     * @param posY El nuevo valor de y
     */
    public void setY(int posY) {
        y = posY;
    }

    /**
     * Método equals
     * @param otro
     * @return true si el otro objeto es igual a este y false alias
     */
    @Override
    public boolean equals(Object otro) {
        
        if (otro == null) {
            return false;
        }
        
        if (otro.getClass() != this.getClass()) {
            return false;
        }

        Posicion otraPos = (Posicion) otro;

        if (x == otraPos.getX() && y == otraPos.getY()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.x;
        hash = 37 * hash + this.y;
        return hash;
    }
}
