package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.numeros;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.Operando;

/**
 * Clase que representa el número 2
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Numero2 extends Operando {


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
     * Devuelve un String con la representación del 2
     * @return "2"
     */
    @Override
    public String toString() {
        return "2";
    }

   /**
    * Método que devuelve un objeto igual a éste
    * @return Un objeto igual a éste
    */
   @Override
   public Numero2 clone() {
       return new Numero2();
   }

}
