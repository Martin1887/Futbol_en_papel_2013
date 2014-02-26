package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.finales;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.NodoA;

/**
 * Clase que representa el operando de mover abajo a la derecha
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class MoverAbD extends Final {

    /**
     * Lo que representa este nodo es la invocación del método padre.moverAbD()
     * @return "padre.moverAbD();"
     */
    @Override
    public String toString() {
        return "Posicion actual = padre.getActual();\n"
                + "boolean mueve = padre.moverAbD();\n"
                + "if (bloqueo()) {\n\treturn;\n}\n"
                + "// Si la actual es igual a la variable local ha hecho movimiento"
                + "ilegal y se mueve aleatoriamente\n// En caso contrario se "
                + "invoca a sí mismo para que compruebe donde mover\n"
                + "if (!actual.equals(padre.getActual()) {\n\tmover();\n} else {\n"
                + "while (!mueve) {\n"
                + "// Aleatoriamente\nint aleat = (int) (Math.random() * 8);\n"
                + "if (padre.getTurnoJug1()) {\n"
                + "switch (aleat) {\ncase 0:\nmueve = padre.moverIzq();\nbreak;\n"
                + "case 1:\nmueve = padre.getJug1().moverDer();\nbreak;\n"
                + "case 2:\nmueve = padre.getJug1().moverArr();\nbreak;\n"
                + "case 3:\nmueve = padre.getJug1().moverAba();\nbreak;\n"
                + "case 4:\nmueve = padre.getJug1().moverArI();\nbreak;\n"
                + "case 5:\nmueve = padre.getJug1().moverArD();\nbreak;\n"
                + "case 6:\nmueve = padre.getJug1().moverAbI();\nbreak;\n"
                + "case 7:\nmueve = padre.getJug1().moverAbD();\nbreak;\n}\n}"
                + "else {\n"
                + "switch (aleat) {\ncase 0:\nmueve = padre.moverIzq();\nbreak;\n"
                + "case 1:\nmueve = padre.getJug2().moverDer();\nbreak;\n"
                + "case 2:\nmueve = padre.getJug2().moverArr();\nbreak;\n"
                + "case 3:\nmueve = padre.getJug2().moverAba();\nbreak;\n"
                + "case 4:\nmueve = padre.getJug2().moverArI();\nbreak;\n"
                + "case 5:\nmueve = padre.getJug2().moverArD();\nbreak;\n"
                + "case 6:\nmueve = padre.getJug2().moverAbI();\nbreak;\n"
                + "case 7:\nmueve = padre.getJug2().moverAbD();\nbreak;\n}\n}\n}\n}";
    }

    /**
     * Como no tiene atributos siempre va a ser igual si es de la misma clase
     * @param otro
     * @return true si es de la misma clase y false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {
        if (otro.getClass().getName().equals(this.getClass().getName())) {
            return true;
        } else {
            return false;
        }
    }

   /**
    * Método que devuelve un objeto igual a éste
    * @return Un objeto igual a éste
    */
   @Override
   public MoverAbD clone() {
       return new MoverAbD();
   }
}
