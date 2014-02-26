package futbolEnPapel2013.jugador.controladores.alfaBeta;

import futbolEnPapel2013.estructura.Posicion;

/**
 * Clase que representa un estado del algoritmo alfa-beta
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public abstract class EstadoEstatico {

    /**
     * Valores minimax del estado. Se necesita un valor para cada posible estado
     * desde el cual se pueda acceder. Por ejemplo, el estado cuyo hijo 0 es
     * este accede a valor[0]
     */
    protected double[] valor;

    /**
     * Array en el que se almacenan los 8 posibles estados hijos ordenados
     * de la forma en la que el algoritmo alfa-beta probablemente sea más
     * eficiente, es decir, con los movimientos más probables (hacia delante)
     * primero
     */
    protected EstadoEstatico[] hijos;

    /**
     * Posición del estado
     */
    protected Posicion posicion;

    /*
     * Métodos get y set
     */

    /**
     * Método get del atributo valor
     * @param elemento El índice del array
     * @return valor
     */
    public abstract double getValor(int elemento);
    /**
     * Método set del atributo valor
     * @param elemento El índice del array al que dar valor
     * @param v El nuevo valor
     */
    public abstract void setValor(int elemento, double v);

    /**
     * Método get del atributo hijos
     * @return hijos
     */
    public abstract EstadoEstatico[] getHijos();

    /**
     * Método set del atributo hijos
     * @param hijos
     */
    public abstract void setHijos(EstadoEstatico[] hijos) throws Exception;

    /**
     * Método get del atributo posicion
     * @return posicion
     */
    public abstract Posicion getPosicion();
    /**
     * Método set del atributo posicion
     * @param nueva La nueva posición
     */
    public abstract void setPosicion(Posicion nueva);
    
    /**
     * Método que devuelve el índice del enlace que conecta con el hijo
     * @param hijo
     * @return El índice del enlace que conecta con el hijo
     */
    public abstract int enlaceAHijo(int hijo);
    
    /**
     * Método clone
     * @return Un objeto con atributos iguales a los de este objeto, pero con
     * posiciones de memoria distintas
     */
    @Override
    public abstract EstadoEstatico clone();
}
