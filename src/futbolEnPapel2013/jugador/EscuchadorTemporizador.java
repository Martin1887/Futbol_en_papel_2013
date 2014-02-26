package futbolEnPapel2013.jugador;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Clase que implementa el escuchador asociado al evento de
 * fin del temporizador
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class EscuchadorTemporizador implements ActionListener {

    private Jugador jugador;
    
    /**
     * Constructor
     * @param padre
     */
    public EscuchadorTemporizador(Jugador padre) {
        jugador = padre;
    }
    
    /**
     * Tratamiento del evento de fin del temporizador.
     * Si no se ha entrado a ningún método de mover se finaliza
     * el movimiento, en caso contrario se deja terminar activando
     * el fin de temporizador
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * Si se ha modificado la posición se activa el fin de temporizador
         * y se espera a terminar el movimiento, en caso contrario se
         * termina el bucle de Mover
         */
        if (jugador.getPosModificada() == false) {
            if (jugador.getControlador() == null) {
                jugador.getMover().avisoTecla();
            } else {
                jugador.getControlador().finalizarTurno();
            }
        } else {
            jugador.activarFinTemporizador();
        }
    }
}
