package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;

/**
 * Clase que representa a un nodo operando
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public abstract class Operando extends NodoA {

    /**
     * Método que devuelve un objeto igual a éste
     * @return Un objeto igual a éste
     */
    @Override
    public abstract Operando clone();
}
