package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;

/**
 * Clase que representa la invocación al método distCentro()
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class DistCentro extends Operando {



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
     * Devuelve un String con la representación de la invocación a distCentro()
     * @return valor en String
     */
    @Override
    public String toString() {
        return "distCentro()";
    }

   /**
    * Método que devuelve un objeto igual a éste
    * @return Un objeto igual a éste
    */
   @Override
   public DistCentro clone() {
       return new DistCentro();
   }

}
