package futbolEnPapel2013.jugador;

import futbolEnPapel2013.Juego;
import java.awt.event.KeyListener;

/**
 * Clase que implementa el tratamiento de las señales del usuario en su turno
 * durante el juego
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class TrataTecla implements KeyListener {


    /**
     * Padre
     */
    private Juego padre;

    /**
     * Turno (para hacer tantas llamadas al padre)
     */
    boolean turnoJug1;

    /**
     * Constructor
     * @param juego
     */
    public TrataTecla(Juego juego) {
        padre = juego;
        turnoJug1 = padre.getTurnoJug1();
        //start();
    }
//
//    @Override
//    public void run() {
//        while (!fin) {
//            if (Thread.currentThread() != this) {
//                if (turnoJug1) {
//                        if (padre.getJug1().moverAbI() == true) {
//                            //padre.getContenedor().removeKeyListener(this);
//                            padre.getJug1().getMover().avisoTecla();
//                            
//                        }
//                    } else {
//                        if (padre.getJug2().moverAbI() == true) {
//                            //padre.getContenedor().removeKeyListener(this);
//                            padre.getJug2().getMover().avisoTecla();
//                            
//                        }
//                    }
//            }
//        }
//    }

    /*
     * Sólo interesa el keyTyped, por lo que los demás no hacen nada
     */

    /**
     * No hace nada (todo se hace en el keyTyped)
     * @param evento
     */
    @Override
    public void keyReleased(java.awt.event.KeyEvent evento) {

    }
    /**
     * No hace nada (todo se hace en el keyTyped)
     * @param evento
     */
    @Override
    public void keyPressed(java.awt.event.KeyEvent evento) {

    }

    /**
     * Método que trata las pulsaciones de las teclas
     * @param evento
     */
    @Override
    public void keyTyped(java.awt.event.KeyEvent evento) {
        /*
         * Mediante un 'switch' se determina la acción que el usuario quiere
         * realizar
         */
        try {
            switch (Integer.parseInt(String.valueOf(evento.getKeyChar()))) {
                case 1:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverAbI() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();
                        }
                    } else {
                        if (padre.getJug2().moverAbI() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();
                        }
                    }

                    break;
                case 2:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverAba() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverAba() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 3:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverAbD() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverAbD() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 4:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverIzq() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverIzq() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 6:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverDer() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverDer() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 7:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverArI() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverArI() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 8:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverArr() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverArr() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                case 9:
                    // Se llama al método del jugador que tiene el turno
                    if (turnoJug1) {
                        if (padre.getJug1().moverArD() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug1().getMover().avisoTecla();                            
                        }
                    } else {
                        if (padre.getJug2().moverArD() == true) {
                            padre.getContenedor().removeKeyListener(this);
                            padre.getJug2().getMover().avisoTecla();                            
                        }
                    }

                    break;

                default:
                    break;
            }
        } catch (NumberFormatException e) {}
    }
}
