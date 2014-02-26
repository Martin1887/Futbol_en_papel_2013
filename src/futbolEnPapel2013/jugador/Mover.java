package futbolEnPapel2013.jugador;

import futbolEnPapel2013.Juego;
import futbolEnPapel2013.estructura.Posicion;

/**
 * Hilo en el que se ejecuta el método mover, el cual controla qué jugador
 * mueve y realiza las esperas necesarias
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Mover extends Thread {

    private Jugador padre;
    private Juego abuelo;
    
    /**
     * Constructor completo
     * @param juego
     */
    public Mover(Juego juego, Jugador jugador) {
        padre = jugador;
        abuelo = juego;
    }

    /**
     * Mëtodo get del atributo padre
     * @return padre
     */
    public Jugador getPadre() {
        return padre;
    }

    /**
     * Método get del atributo abuelo
     * @return abuelo
     */
    public Juego getAbuelo() {
        return abuelo;
    }
    
    /**
     * Método que despierta al hilo
     * pulsada es correcta
     */
    public synchronized void avisoTecla() {
        try {
            notifyAll();
        } catch (Exception e) {}
    }

    /**
     * Método en el que se mueve durante un turno
     */
    @Override
    public synchronized void run() {
        if (padre.getControlador() == null) {
            try {
                // Creación de un objeto de la clase TrataTecla para que ésta
                // trate las interacciones del jugador y realice los movimientos
                // hasta que termina el turno por el fin de un movimiento válido
                TrataTecla escuchador = new TrataTecla(this.abuelo);
                abuelo.getContenedor().addKeyListener(escuchador);
                abuelo.getContenedor().requestFocus();
                wait();
                //escuchador.join();

                // Se elimina el KeyListener por si acaso no se ha eliminado
                abuelo.getContenedor().removeKeyListener(escuchador);

                // Se para y elimina el temporizador para que no siga en el siguiente turno
                if (padre.getTemporizador() != null) {
                    padre.getTemporizador().stop();
                    padre.getTemporizador().removeActionListener(padre.getTemporizador().getActionListeners()[0]);
                    padre.setTemporizador(null);
                }

                // Se termina el turno, ya se ha tratado el fin del temporizador
                padre.setFinTemporizador(false);
                escuchador = null;
                // Si juegan dos jugadores se esperan 2 segundos para que un
                // jugador no interfiera en el turno del otro si el jugador no
                // es obscuro
                if (abuelo.getPadre().getJug1() == Juego.PERSONA
                        && abuelo.getPadre().getJug2() == Juego.PERSONA
                        && !padre.isObscuro()) {
                   try {
                        Thread.sleep(2000);
                   } catch (InterruptedException e) {}
                } else {
                    // Si no se espera 1 segundo para que el jugador no mueva
                    // en el siguiente turno sin darse cuenta por creer que puede
                    // rebotar si el jugador no es obscuro
                    if (!padre.isObscuro()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {}
                    }
                }

                // Estadísticas
                if (padre.distancia(Jugador.JUG1) < abuelo.getMinDistMetaJ1()) {
                    abuelo.setMinDistMetaJ1(padre.distancia(Jugador.JUG1));
                }
                if (padre.distancia(Jugador.JUG2) < abuelo.getMinDistMetaJ2()) {
                    abuelo.setMinDistMetaJ2(padre.distancia(Jugador.JUG2));
                }
                if (abuelo.getActual().getY() < 8) {
                    abuelo.setTurnosCampoJ2(abuelo.getTurnosCampoJ2() + 1);
                } else {
                    abuelo.setTurnosCampoJ1(abuelo.getTurnosCampoJ1() + 1);
                }
                if (abuelo.getActual().equals(new Posicion(5, -1))
                        || abuelo.getActual().equals(new Posicion(4, -1))
                        || abuelo.getActual().equals(new Posicion(6, -1))) {
                    // gol del jugador 1
                    abuelo.setGanaJug1(true);
                } else if (abuelo.getActual().equals(new Posicion(5, 17))
                        || abuelo.getActual().equals(new Posicion(4, 17))
                        || abuelo.getActual().equals(new Posicion(6, 17))) {
                    // gol del jugador 2
                    abuelo.setGanaJug1(false);
                } else if (padre.bloqueo()) {
                    // bloqueo
                    if (abuelo.getTurnoJug1()) {
                        abuelo.setGanaJug1(false);
                    } else {
                        abuelo.setGanaJug1(true);
                    }
                }
            } catch (Exception e) {}
            finally {
                if (padre.getTemporizador() != null) {
                    padre.getTemporizador().stop();
                    padre.getTemporizador().removeActionListener(padre.getTemporizador().getActionListeners()[0]);
                    padre.setTemporizador(null);
                }
            }
        } else {
            try {
                padre.getControlador().mover(abuelo);
                
                // Se para y elimina el temporizador para que no siga en el siguiente turno
                if (padre.getTemporizador() != null) {
                    padre.getTemporizador().stop();
                    padre.getTemporizador().removeActionListener(padre.getTemporizador().getActionListeners()[0]);
                    padre.setTemporizador(null);
                }

                // Para ver traza, quitar alias ******************************************
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {}

                // Si juegan dos controladores se espera 1 segundo para que se
                // pueda ver el juego si el padre no es obscuro
                if (!padre.isObscuro() && abuelo.getPadre().getJug1() != Juego.PERSONA
                        && abuelo.getPadre().getJug2() != Juego.PERSONA) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }

                // Estadísticas
                if (padre.distancia(Jugador.JUG1) < abuelo.getMinDistMetaJ1()) {
                    abuelo.setMinDistMetaJ1(padre.distancia(Jugador.JUG1));
                }
                if (padre.distancia(Jugador.JUG2) < abuelo.getMinDistMetaJ2()) {
                    abuelo.setMinDistMetaJ2(padre.distancia(Jugador.JUG2));
                }
                if (abuelo.getActual().getY() < 8) {
                    abuelo.setTurnosCampoJ2(abuelo.getTurnosCampoJ2() + 1);
                } else {
                    abuelo.setTurnosCampoJ1(abuelo.getTurnosCampoJ1() + 1);
                }
                if (abuelo.getActual().equals(new Posicion(5, -1))
                        || abuelo.getActual().equals(new Posicion(4, -1))
                        || abuelo.getActual().equals(new Posicion(6, -1))) {
                    // gol del jugador 1
                    abuelo.setGanaJug1(true);
                } else if (abuelo.getActual().equals(new Posicion(5, 17))
                        || abuelo.getActual().equals(new Posicion(4, 17))
                        || abuelo.getActual().equals(new Posicion(6, 17))) {
                    // gol del jugador 2
                    abuelo.setGanaJug1(false);
                } else if (padre.bloqueo()) {
                    // bloqueo
                    if (abuelo.getTurnoJug1()) {
                        abuelo.setGanaJug1(false);
                    } else {
                        abuelo.setGanaJug1(true);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (padre.getTemporizador() != null) {
                    padre.getTemporizador().stop();
                    padre.getTemporizador().removeActionListener(padre.getTemporizador().getActionListeners()[0]);
                    padre.setTemporizador(null);
                }
            }
        }
    }
}
