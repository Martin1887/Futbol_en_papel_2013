package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.finales;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.Operando;

/**
 * Clase que representa un operando final (de movimiento)
 * @author Marcos Martín Pozo Delgado
 */
public abstract class Final extends Operando {

    /**
     * Método que devuelve un objeto igual a éste
     * @return Un objeto igual a éste
     */
    @Override
    public abstract Operando clone();
}
