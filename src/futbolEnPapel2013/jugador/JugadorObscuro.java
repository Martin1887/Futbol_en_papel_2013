package futbolEnPapel2013.jugador;

import futbolEnPapel2013.JuegoObscuro;
import futbolEnPapel2013.estructura.Posicion;
import futbolEnPapel2013.jugador.controladores.Controlador;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * Clase que represente a un jugador de un juego obscuro
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class JugadorObscuro extends Jugador {

    /**
     * Constructor completo
     * @param parent
     * @param controla El controlador de este jugador
     */
    public JugadorObscuro(javax.swing.JDialog parent, Controlador controla) {
        padre = null;
        padreObscuro = (JuegoObscuro) parent;
        obscuro = true;
        controlador = controla;
    }

    /**
     * Método que mueve durante un turno. Lo único que cambia respecto al del
     * padre es que el padre de este es padreObscuro en vez de padre
     */
    @Override
    public void mover() throws Exception {
        mover = new Mover(padreObscuro, this);
        setPosModificada(false);
        mover.start();
        mover.join();
    }
    
    /**
     * Método que devuelve la distancia de la posición actual
     * a la portería rival o propia o el centro o pared izquierda o derecha
     * (dependiendo del parámetro), donde la distancia es el máximo de la
     * distancia en X y la distancia en Y
     * Es necesaria la sobrescritura para usar padreObscuro en vez de padre (que vale null)
     * @param objetivo El objetivo (portería rival o propia o centro del campo o pared izquierda o derecha)
     * @return La distancia al objetivo requerido
     */
    @Override
    public double distancia(int objetivo) {
        double resultado = 0;

        // Posición del estado actual
        double x1 = padreObscuro.getActual().getX();
        double y1 = padreObscuro.getActual().getY();


        // Posición en la que está el objetivo
        double x2 = 5, y2 = y1;



        // Si el objetivo es alguna portería o el centro
        if (objetivo == JUG1) {
            y2 = 17;
        } else if (objetivo == JUG2) {
            y2 = -1;
        }

        // Se calcula la distancia
        resultado = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));

        return resultado;
    }

    /**
     * Método que comprueba si el jugador no se puede mover en ninguna posición.
     * Es necesario sobrescribirlo para usar padreObscuro en vez de padre
     * @return true si no hay movimiento posible y false en caso contrario
     */
    @Override
    public boolean bloqueo() {
        if ((padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[0] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[0].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[1] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[1].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[2] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[2].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[3] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[3].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[4] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[4].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[5] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[5].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[6] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[6].getMarcado())
                && (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[7] == null
                || padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()]
                [padreObscuro.getActual().getY()].getEnlaces()[7].getMarcado())) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Ahora se sobrescriben los métodos de mover simplemente quitando las
     * instrucciones de dibujar, el audio y los sleeps
     */

    /**
     * Mueve a la izquierda
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverIzq() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[0] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[0].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1,
                        padreObscuro.getActual().getY()));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1, padreObscuro.getActual().getY()));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[1].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[1].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve a la derecha
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverDer() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[1] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[1].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1,
                        padreObscuro.getActual().getY()));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1, padreObscuro.getActual().getY()));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[0].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) { 
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }

                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[0].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve hacia arriba
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverArr() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[2] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[2].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX(),
                        padreObscuro.getActual().getY() - 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX(), padreObscuro.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[3].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[3].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);


                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve hacia abajo
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverAba() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[3] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[3].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX(),
                        padreObscuro.getActual().getY() + 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX(), padreObscuro.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[2].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }

                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[2].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve en la diagonal de arriba a la izquierda
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverArI() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[4] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[4].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1,
                        padreObscuro.getActual().getY() - 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {
 
                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1, padreObscuro.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[7].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {
 
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[7].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve en la diagonal de arriba a la derecha
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverArD() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[5] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[5].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1,
                        padreObscuro.getActual().getY() - 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1, padreObscuro.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[6].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {

                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[6].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve en la diagonal de abajo a la izquierda
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverAbI() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[6] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[6].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1,
                        padreObscuro.getActual().getY() + 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1, padreObscuro.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[5].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {

                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[5].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Mueve en la diagonal de abajo a la derecha
     * @return true si ha terminado el turno y false en caso contrario
     */
    @Override
    public boolean moverAbD() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[7] != null) {

            if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces()[7].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() + 1,
                        padreObscuro.getActual().getY() + 1));



                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1() == false
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {

                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padreObscuro.getTurnoJug1() && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == -1)
                        || (padreObscuro.getTurnoJug1()
                        && (padreObscuro.getActual().getX() == 5 || padreObscuro.getActual().getX() == 4 || padreObscuro.getActual().getX() == 6)
                        && padreObscuro.getActual().getY() == 17)) {
 
                    padreObscuro.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padreObscuro.getActual().getX() < 0
                        || padreObscuro.getActual().getX() > 10
                        || padreObscuro.getActual().getY() < 0
                        || padreObscuro.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padreObscuro.setActual(new Posicion(padreObscuro.getActual().getX() - 1, padreObscuro.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[4].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);

                    /*
                     * Rebota
                     */
                    // Se espera otra tecla sólo durante 4 segundos
                    if (temporizador != null) {
                        temporizador.stop();
                        temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                    }
                    temporizador = new Timer(4000, (ActionListener) new EscuchadorTemporizador(this));
                    temporizador.start();
                    // Hay un nuevo temporizador
                    finTemporizador = false;

                    setPosModificada(false);
                    // Si hay bloqueo termina el juego.
                    // Se debe comprobar aquí porque puede que no haya posible
                    // movimiento tras el rebote, y en ese caso pierde el jugador
                    // actual
                    if (bloqueo()) {
 
                        if (temporizador != null) {
                            temporizador.stop();
                            temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                            temporizador = null;
                        }
                        
                        padreObscuro.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setMarcado(true);
                    enlacesAux = padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].getEnlaces();
                    enlacesAux[4].setMarcado(true);
                    padreObscuro.getTablero().getNodos()[padreObscuro.getActual().getX()][padreObscuro.getActual().getY()].setEnlaces(enlacesAux);
 
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

}
