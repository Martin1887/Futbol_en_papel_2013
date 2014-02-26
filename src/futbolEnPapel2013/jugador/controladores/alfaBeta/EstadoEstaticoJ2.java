package futbolEnPapel2013.jugador.controladores.alfaBeta;

import futbolEnPapel2013.estructura.Posicion;

/**
 * Clase que representa un estado del algoritmo alfa-beta en el caso de que
 * esté jugando actualmente el jugador 2
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class EstadoEstaticoJ2 extends EstadoEstatico {


    /**
     * Constructor
     * @param nueva
     */
    public EstadoEstaticoJ2(Posicion nueva) {
        valor = new double[8];
        for (int i = 0; i < valor.length; i++) {
            valor[i] = AlfaBetaEstatico.INFINITO;
        }
        posicion = nueva;
    }


    /*
     * Métodos get y set
     */

    /**
     * Método get del atributo valor
     * @param elemento El índice del valor a devolver
     * @return valor
     */
    @Override
    public double getValor(int elemento) {
        return valor[elemento];
    }
    /**
     * Método set del atributo valor
     * @param elemento El índice del valor a cambiar
     * @param v El nuevo valor
     */
    @Override
    public void setValor(int elemento, double v) {
        valor[elemento] = v;
    }

    /**
     * Método get del atributo hijos
     * @return hijos
     */
    @Override
    public EstadoEstatico[] getHijos() {
        return hijos;
    }
    /**
     * Método set del atributo hijos
     * @param nuevos Los nuevos hijos
     */
    @Override
    public void setHijos(EstadoEstatico[] nuevos) throws Exception {
        boolean algunoNoNull = false;
        for (int i = 0; i < nuevos.length; i++) {
            if (nuevos[i] != null) {
                if (nuevos[i].getClass() == this.getClass()) {
                    hijos = (EstadoEstaticoJ2[]) nuevos;
                    return;
                }
                algunoNoNull = true;
            }
        }
        if (algunoNoNull) {
            throw new Exception("¡Intentas poner como hijos de un EstadoJ2 Estados de otro tipo!");
        } else {
            hijos = null;
        }
    }

    /**
     * Método get del atributo posicion
     * @return posicion
     */
    @Override
    public Posicion getPosicion() {
        return posicion;
    }
    /**
     * Método set del atributo posicion
     * @param nueva La nueva posición
     */
    @Override
    public void setPosicion(Posicion nueva) {
        posicion = nueva;
    }
    
    /**
     * Método que devuelve el índice del enlace que conecta con el hijo
     * @param hijo
     * @return El índice del enlace que conecta con el hijo
     */
    @Override
    public int enlaceAHijo(int hijo) {
        /*
         * Array en el que se almacenan los 8 posibles estados hijos ordenados
         * de la forma en la que el algoritmo alfa-beta probablemente sea más
         * eficiente, es decir, con los movimientos más probables (hacia delante)
         * primero:
         * 0: 2 (abajo)
         * 1: 1
         * 2: 3
         * 3: 4
         * 4: 6
         * 5: 7
         * 6: 9
         * 7: 8
         */
        
        int enlace = 0;
        
        switch (hijo) {
            case 0:
                enlace = 3;
                break;
            case 1:
                enlace = 6;
                break;
            case 2:
                enlace = 7;
                break;
            case 3:
                enlace = 0;
                break;
            case 4:
                enlace = 1;
                break;
            case 5:
                enlace = 4;
                break;
            case 6:
                enlace = 5;
                break;
            case 7:
                enlace = 2;
                break;
            // Por defecto mueve hacia abajo, aunque si funciona
            // bien el algoritmo nunca va a pasar
            default:
                enlace = 3;
                break;
        }
        
        return enlace;
    }
    
        /**
     * Método clone
     * @return Un objeto cuyos atributos tienen el mismo valor que los de este
     * pero con posiciones de memoria distintas
     */
    @Override
    public EstadoEstatico clone() {
        double[] valorNuevo = new double[valor.length];
        System.arraycopy(valor, 0, valorNuevo, 0, valor.length);
        
        Posicion posicionNueva = new Posicion(posicion.getX(), posicion.getY());
        
        EstadoEstatico[] hijosNuevos = new EstadoEstatico[hijos.length];
        
        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] != null) {
                hijosNuevos[i] = hijos[i].clone();
            }
        }
        
        EstadoEstaticoJ2 estado = new EstadoEstaticoJ2(posicionNueva);
        for (int i = 0; i < valorNuevo.length; i++) {
            estado.setValor(i, valorNuevo[i]);
        }
        try {
            estado.setHijos(hijosNuevos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return estado;
    }
}