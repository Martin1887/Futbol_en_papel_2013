package futbolEnPapel2013.jugador.controladores;

import futbolEnPapel2013.Juego;

/**
 * Interfaz que representa un controlador manejado por la CPU
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public abstract class Controlador {
     
    /**
     * Variable que indica que ha temrinado el turno, por lo que el controlador
     * debe parar
     */
    protected boolean finTurno;
    
    /**
     * Método que finaliza el turno del controlador
     */
    public void finalizarTurno() {
        finTurno = true;
    }
    
    /**
     * El único método del controlador es mover, el cual
     * mueve durante un turno a partir de la posición actual
     */
    abstract public void mover(Juego juego) throws Exception;
}
