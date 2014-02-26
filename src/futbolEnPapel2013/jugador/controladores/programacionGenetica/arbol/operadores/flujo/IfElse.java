package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.flujo;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.Operador;
import java.util.Arrays;

/**
 * Clase que representa al operador if-else
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class IfElse extends Operador {

   /**
    * Constructor por defecto
    */
   public IfElse() {
       setCardinalidad(3);
   }



   /**
    * Método que modifica los hijos sólo si cumplen con los requisitos
    * (el primer hijo es una comparación, el segundo un bloque de código y el
    * tercero es otro bloque de código).
    * Si el tamaño del array es mayor que tres sólo se asignan los dos primeros
    * elementos
    * @param nodos El array con los nuevos hijos
    * @return true si se ha realizado con éxito y false en caso contrario
    */
   @Override
   public boolean setHijos(NodoA[] nodos) {
       if (nodos.length >= 3 && nodos[0] != null && nodos[1] != null && nodos[2] != null) {
           if ((nodos[0].getClass().getSimpleName().equals("Igual")
                || nodos[0].getClass().getSimpleName().equals("NoIgual")
                || nodos[0].getClass().getSimpleName().equals("Menor")
                || nodos[0].getClass().getSimpleName().equals("Mayor"))
                   && nodos[1].getClass().getSimpleName().equals("Bloque")
                   && nodos[2].getClass().getSimpleName().equals("Bloque")) {
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
    * Devuelve la representación del if-else (siempre con llaves):
    * if (comparacion) {
    * bloque
    * } else {
    * bloque
    * }
    * 
    * @return La representación del if-else en String
    */
   @Override
   public String toString() {
       return "if (" + getHijos()[0].toString() + ") {\n"
               + getHijos()[1].toString() + "\n} else {\n"
               + getHijos()[2].toString() + "\n}\n";
   }

   /**
    * Método que devuelve un objeto igual a éste
    * @return iE Un objeto igual a éste
    */
   @Override
   public IfElse clone() {
       IfElse iE = new IfElse();
       iE.setHijos(getHijos());
       return iE;
   }

}
