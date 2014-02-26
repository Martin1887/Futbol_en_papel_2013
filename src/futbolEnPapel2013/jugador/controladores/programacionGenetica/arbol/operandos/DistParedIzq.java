package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;

/**
 * Clase que representa la invocación al método distParedIzq()
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class DistParedIzq extends Operando {



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
     * Devuelve un String con la representación de la invocación a distParedIzq()
     * @return valor en String
     */
    @Override
    public String toString() {
        return "distParedIzq()";
    }

   /**
    * Método que devuelve un objeto igual a éste
    * @return Un objeto igual a éste
    */
   @Override
   public DistParedIzq clone() {
       return new DistParedIzq();
   }

}
