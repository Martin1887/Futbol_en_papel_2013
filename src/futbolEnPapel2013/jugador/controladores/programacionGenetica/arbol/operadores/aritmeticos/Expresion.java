package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.aritmeticos;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.Operador;
import java.util.Arrays;

/**
 * Clase que representa al operador *
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Expresion extends Operador {

   /**
    * Constructor por defecto
    */
   public Expresion() {
       setCardinalidad(1);
   }

   /**
    * Método que modifica los hijos sólo si cumplen con los requisitos
    * (operación aritmética u operando).
    * Si el tamaño del array es mayor que dos sólo se asignan los dos primeros
    * elementos
    * @param nodos El array con los nuevos hijos
    * @return true si se ha realizado con éxito y false en caso contrario
    */
   @Override
   public boolean setHijos(NodoA[] nodos) {
       if (nodos.length >= 1 && nodos[0] != null) {
           if (nodos[0].getClass().getSuperclass().getSimpleName().equals("Operando")
                   || nodos[0].getClass().getSimpleName().equals("Suma")
                   || nodos[0].getClass().getSimpleName().equals("Resta")
                   || nodos[0].getClass().getSimpleName().equals("Producto")) {
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
    * Devuelve la representación de la expresión (la representación del hijo)
    * @return La representación de la expresión (la del hijo) en String
    */
   @Override
   public String toString() {
       return getHijos()[0].toString();
   }

   /**
    * Método que devuelve un objeto igual a éste
    * @return ex Un objeto igual a éste
    */
   @Override
   public Expresion clone() {
       Expresion ex = new Expresion();
       ex.setHijos(getHijos());
       return ex;
   }

}
