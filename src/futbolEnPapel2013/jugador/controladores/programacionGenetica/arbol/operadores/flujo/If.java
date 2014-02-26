package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.flujo;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.Operador;
import java.util.Arrays;

/**
 * Clase que representa al operador if
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class If extends Operador {

  /**
   * Constructor por defecto
   */
   public If() {
       setCardinalidad(2);
   }


   /**
    * Método que modifica los hijos sólo si cumplen con los requisitos
    * (el primer hijo es una comparación y el segundo un bloque de código).
    * Si el tamaño del array es mayor que dos sólo se asignan los dos primeros
    * elementos
    * @param nodos El array con los nuevos hijos
    * @return true si se ha realizado con éxito y false en caso contrario
    */
   @Override
   public boolean setHijos(NodoA[] nodos) {
       if (nodos.length >= 2 && nodos[0] != null && nodos[1] != null) {
           if ((nodos[0].getClass().getSimpleName().equals("Igual")
                || nodos[0].getClass().getSimpleName().equals("NoIgual")
                || nodos[0].getClass().getSimpleName().equals("Menor")
                || nodos[0].getClass().getSimpleName().equals("Mayor"))
                   && nodos[1].getClass().getSimpleName().equals("Bloque")) {
               super.setHijos(nodos);
               return true;
           } else {
               return false;
           }
       } else {
           return false;
       }
   }

   /**
    * Devuelve true si el NodoA pasado como parámetro es igual que este
    * objeto y false en caso contrario
    * @param otro El NodoA a comparar
    * @return true si son iguales y false en caso contrario
    */
   @Override
   public boolean equals(Object otro) {
       if (!otro.getClass().getName().equals(this.getClass().getName())) {
           return false;
       } else {
           try {
                if (Integer.parseInt((String) otro.getClass().getDeclaredMethod
                        ("getCardinalidad()", (Class) null).invoke
                        (otro, (Class) null)) == this.getCardinalidad()
                        && Arrays.equals((NodoA[])(otro.getClass().getDeclaredMethod
                        ("getHijos()", (Class) null).invoke
                        (otro, (Class) null)), this.getHijos())) {
                             return true;
                }
           } catch (Exception e) {return false;}
           return false;
       }
   }

   /**
    * Devuelve la representación del if (siempre con llaves):
    * if (comparacion) {
    * bloque
    * }
    * 
    * @return La representación del if en String
    */
   @Override
   public String toString() {
       return "if (" + getHijos()[0].toString() + ") {\n"
               + getHijos()[1].toString() + "\n}\n";
   }

   /**
    * Método que devuelve un objeto igual a éste
    * @return i Un objeto igual a éste
    */
   @Override
   public If clone() {
       If i = new If();
       i.setHijos(getHijos());
       return i;
   }

}
