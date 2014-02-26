package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol;

/**
 * Clase que representa un nodo en un árbol de programación genética
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public abstract class NodoA {

    /**
     * Constructor por defecto
     */
    public NodoA(){}

    /**
     * Método que compara este con el objeto pasado por parámetro
     * @param otro El objeto con que comparar
     * @return true si son iguales y false en caso contrario
     */
    @Override
    public abstract boolean equals(Object otro);
    

    /**
     * Método que devuelve una cadena que representa al nodo tal y como debe
     * escribirse en el código
     * @return El código que representa esta cadena
     */
    @Override
    public abstract String toString();

    /**
     * Método que devuelve un objeto igual a éste
     * @return Un objeto igual a éste
     */
    @Override
    public abstract NodoA clone();

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
