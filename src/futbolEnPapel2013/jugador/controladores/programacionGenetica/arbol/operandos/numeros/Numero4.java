package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.numeros;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.Operando;

/**
 * Clase que representa el número 4
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Numero4 extends Operando {


    /**
     * Método equals. Devuelve true si el objeto es igual a éste y false en caso
     * contrario
     * @param otro
     * @return true si el objeto es igual a este y false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {
        if (!otro.getClass().getName().equals(this.getClass().getName())) {
           return false;
       } else {
           return true;
       }
    }

    /**
     * Devuelve un String con la representación del 4
     * @return "4"
     */
    @Override
    public String toString() {
        return "4";
    }

   /**
    * Método que devuelve un objeto igual a éste
    * @return Un objeto igual a éste
    */
   @Override
   public Numero4 clone() {
       return new Numero4();
   }

}
