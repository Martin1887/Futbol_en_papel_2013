package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.flujo;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.Operador;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import java.util.Arrays;

/**
 * Clase que representa un bloque de código
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Bloque extends Operador {

    /**
    * Método que modifica los hijos sólo si cumplen con los requisitos
    * (los primeros hijos son de flujo y el último es un operando final).
    * Si el tamaño del array es mayor que la cardinalidad sólo se asignan los
    * primeros elementos
    * @param nodos El array con los nuevos hijos
    * @return true si se ha realizado con éxito y false en caso contrario
    */
   @Override
   public boolean setHijos(NodoA[] nodos) {
       if (nodos.length >= getCardinalidad()) {
           for (int i = 0; i < getCardinalidad() - 1; i++) {
               if (nodos[i] == null) {
                   return false;
               } else {
                   if (!(nodos[i].getClass().getSimpleName().equals("If")
                           || nodos[i].getClass().getSimpleName().equals("IfElse"))) {
                       return false;
                   }
               }
           }

           if (nodos[getCardinalidad() - 1] == null
                   || !nodos[getCardinalidad() - 1].getClass().getSuperclass()
                   .getSimpleName().equals("Final")) {
               return false;
           } else {
               super.setHijos(nodos);
               return true;
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
    * Devuelve la representación del bloque (los hijos uno a continuación de otro)
    * @return La representación del bloque en String
    */
   @Override
   public String toString() {
       String res = "";

       for (int i = 0; i < getCardinalidad(); i++) {
           res += getHijos()[i].toString();
       }

       return res;
   }

   /**
    * Método que devuelve un objeto igual a éste
    * @return b Un objeto igual a éste
    */
   @Override
   public Bloque clone() {
       Bloque b = new Bloque();
       b.setCardinalidad(getCardinalidad());
       b.setHijos(getHijos());
       return b;
   }
}
