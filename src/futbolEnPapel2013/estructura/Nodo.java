package futbolEnPapel2013.estructura;

import java.util.Arrays;

/**
 * Clase que representa los nodos del tablero, es decir, los vértices de
 * los cuadrados.
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Nodo {

    /**
     * Variable booleana que almacena si el nodo ha sido accedido por algún
     * jugador o si es línea central o pared (true, en caso contrario, false)
     */
    private boolean marcado;

    /**
     * Array de enlaces del nodo, distribuidos de la siguiente forma:
     *
     * 0: izquierda
     * 1: derecha
     * 2: arriba
     * 3: abajo
     * 4: diagonal arriba izquierda
     * 5: diagonal arriba derecha
     * 6: diagonal abajo izquierda
     * 7: diagonal abajo derecha
     */
    private Enlace[] enlaces;

    /**
     * Array que representa la tabla de decisión de un nodo (para las hormigas).
     * Cada elemento es el valor del enlace de la misma posición
     */
    private double[] tablaDeDecision;

    /**
     * Constructor por defecto que pone marcado a false
     */
    public Nodo() {
        marcado = false;
        enlaces = new Enlace[8];
        tablaDeDecision = new double[8];
    }

    /**
     * Constructor que asigna valor a marcado
     * @param valor El valor que se asignará a marcado
     */
    public Nodo(boolean valor) {
        marcado = valor;
        enlaces = new Enlace[8];
        tablaDeDecision = new double[8];
    }

    /**
     * Método get del atributo marcado
     * @return marcado
     */
    public boolean getMarcado() {
        return marcado;
    }

    /**
     * Método set del atributo marcado
     * @param nuevo el valor nuevo que se asignará a marcado
     */
    public void setMarcado(boolean nuevo) {
        marcado = nuevo;
    }

    /**
     * Método get del atributo enlaces
     * @return enlaces
     */
    public Enlace[] getEnlaces() {
        return enlaces;
    }

    /**
     * Método set del atributo enlaces
     * @param nuevo el valor nuevo que se asignará a enlaces
     */
    public void setEnlaces(Enlace[] nuevo) {
        System.arraycopy(nuevo, 0, enlaces, 0, nuevo.length);
    }

    /**
     * Método get del atributo tablaDeDecision
     * @return tablaDeDecision
     */
    public double[] getTablaDec() {
        return tablaDeDecision;
    }

    /**
     * Método set del atributo tablaDeDecision
     * @param nuevo La nueva tabla de decisión
     */
    public void setTablaDec(double[] nuevo) {
        System.arraycopy(nuevo, 0, tablaDeDecision, 0, tablaDeDecision.length);
    }

    /**
     * Método equals, que compara si otro objeto Nodo es igual a este
     * @param otro El otro nodo a comparar
     * @return true si son iguales y false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {

        boolean resultado = true;
        
        if (otro == null) {
            return false;
        }

        if (otro.getClass() != this.getClass()) {
            return false;
        }

        Nodo otroNodo = (Nodo) otro;

        if (otroNodo.getMarcado() != marcado) {
            return false;
        }

        for (int i = 0; i < enlaces.length; i++) {
            if (!otroNodo.getEnlaces()[i].equals(enlaces[i])) {
                resultado = false;
            }
        }

        for (int i = 0; i < tablaDeDecision.length; i++) {
            if (otroNodo.getTablaDec()[i] != tablaDeDecision[i]) {
                resultado = false;
            }
        }

        return resultado;
    }

    /**
     *
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.marcado ? 1 : 0);
        hash = 37 * hash + Arrays.deepHashCode(this.enlaces);
        hash = 37 * hash + Arrays.hashCode(this.tablaDeDecision);
        return hash;
    }

    /**
     * Método que devuelve una instancia igual a la actual
     * @return nodo Una instancia igual a la actual
     */
    @Override
    public Nodo clone() {
        Nodo nodo = new Nodo(marcado);

        Enlace[] enl = new Enlace[enlaces.length];

        for (int i = 0; i < enl.length; i++) {
            if (enlaces[i] != null) {
                enl[i] = enlaces[i].clone();
            }
        }

        nodo.setEnlaces(enl);
        
        nodo.setTablaDec(tablaDeDecision);

        return nodo;
    }
}
