package futbolEnPapel2013.jugador.controladores.alfaBeta;

import futbolEnPapel2013.estructura.Posicion;

/**
 * Clase que representa un estado del algoritmo alfa-beta en el caso de que
 * esté jugando actualmente el jugador 1
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class EstadoJ1 extends Estado {

    /**
     * Constructor
     * @param nueva
     */
    public EstadoJ1(Posicion nueva) {
        valor = AlfaBetaEstatico.INFINITO;
        posicion = nueva;
        hijos = new EstadoJ1[8];
    }

    /*
     * Métodos get y set
     */

    /**
     * Método get del atributo valor
     * @return valor
     */
    @Override
    public double getValor() {
        return valor;
    }
    /**
     * Método set del atributo valor
     * @param v El nuevo valor
     */
    @Override
    public void setValor(double v) {
        valor = v;
    }

    /**
     * Método get del atributo hijos
     * @return hijos
     */
    @Override
    public Estado[] getHijos() {
        return hijos;
    }
    /**
     * Método set del atributo hijos
     * @param nuevos Los nuevos hijos
     */
    @Override
    public void setHijos(Estado[] nuevos) throws Exception {
        boolean algunoNoNull = false;
        for (int i = 0; i < nuevos.length; i++) {
            if (nuevos[i] != null) {
                if (nuevos[i].getClass() == this.getClass()) {
                    hijos = (EstadoJ1[]) nuevos;
                    return;
                }
                algunoNoNull = true;
            }
        }
        if (algunoNoNull) {
            throw new Exception("¡Intentas poner como hijos de un EstadoJ1 Estados de otro tipo!");
        }
    }

    /**
     * Método set de un elemento del atributo hijos
     * @param elemento
     * @param hijo
     * @throws Exception
     */
    @Override
    public void setHijo(int elemento, Estado hijo) throws Exception {
        if (hijo != null) {
            if (hijo.getClass() == this.getClass()) {
                hijos[elemento] = hijo.clone();
                return;
            } else {
                throw new Exception("¡Intentas poner como hijo de un EstadoDinJ1 Estados de otro tipo!");
            }
        } else {
            hijo = null;
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
     * 0: 8 (arriba)
     * 1: 7
     * 2: 9
     * 3: 4
     * 4: 6
     * 5: 1
     * 6: 3
     * 7: 2
     */

        int enlace = 0;

        switch (hijo) {
            case 0:
                enlace = 2;
                break;
            case 1:
                enlace = 4;
                break;
            case 2:
                enlace = 5;
                break;
            case 3:
                enlace = 0;
                break;
            case 4:
                enlace = 1;
                break;
            case 5:
                enlace = 6;
                break;
            case 6:
                enlace = 7;
                break;
            case 7:
                enlace = 3;
                break;
            // Por defecto mueve hacia arriba, aunque si funciona
            // bien el algoritmo nunca va a pasar
            default:
                enlace = 2;
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
    public Estado clone() {
        double valorNuevo = valor;

        Posicion posicionNueva = new Posicion(posicion.getX(), posicion.getY());

        Estado[] hijosNuevos = new Estado[hijos.length];

        for (int i = 0; i < hijos.length; i++) {
            if (hijos[i] != null) {
                hijosNuevos[i] = hijos[i].clone();
            }
        }

        EstadoJ1 estado = new EstadoJ1(posicionNueva);
        estado.setValor(valorNuevo);
        try {
            estado.setHijos(hijosNuevos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return estado;
    }
}