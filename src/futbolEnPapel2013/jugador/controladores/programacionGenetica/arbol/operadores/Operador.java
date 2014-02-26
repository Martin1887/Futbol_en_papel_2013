package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;

/**
 * Clase que representa a un nodo operador
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public abstract class Operador extends NodoA {

    /**
     * Cardinalidad del nodo (número de hijos)
     */
    private int cardinalidad;

    /**
     * Hijos del nodo
     */
    private NodoA[] hijos;


    /**
     * Constructor por defecto
     */
    public Operador(){}

    /**
     * Constructor frecuente
     * @param card
     */
    public Operador(int card) {
        cardinalidad = card;
        hijos = new NodoA[card];
    }

    /**
     * Constructor completo
     * @param card La cardinalidad
     * @param nodos Los hijos
     */
    public Operador(int card, NodoA[] nodos) {
        cardinalidad = card;
        System.arraycopy(nodos, 0, hijos, 0, hijos.length);
    }


    /**
     * Método get del atributo cardinalidad
     * @return cardinalidad
     */
    public int getCardinalidad() {
        return cardinalidad;
    }

    /**
     * Método set del atributo cardinalidad
     * @param card La nueva cardinalidad
     */
    public void setCardinalidad(int card) {
        cardinalidad = card;
    }

    /**
     * Método get del atributo hijos
     * @return hijos
     */
    public NodoA[] getHijos() {
        return hijos;
    }

    /**
     * Método set del atributo hijos
     * @param nodos Los nuevos hijos
     * @return true (siempre se realiza)
     */
    public boolean setHijos(NodoA[] nodos) {
        System.arraycopy(nodos, 0, hijos, 0, hijos.length);
        return true;
    }


    /**
     * Método que compara este con el objeto pasado por parámetro
     * @param otro El objeto con que comparar
     * @return true si son iguales y false en caso contrario
     */
    public boolean equals(Operador otro) {
        if (otro.getCardinalidad() == cardinalidad && otro.getHijos() == hijos) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que devuelve un objeto igual a éste
     * @return Un objeto igual a éste
     */
    @Override
    public abstract Operador clone();

}
