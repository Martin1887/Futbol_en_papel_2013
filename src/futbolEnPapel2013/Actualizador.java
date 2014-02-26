package futbolEnPapel2013;

/**
 * Clase que actualiza constantemente el tablero de juego
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Actualizador implements Runnable {

    /**
     * Padre
     */
    Juego padre;

    /**
     * Constructor completo
     * @param juego 
     */
    public Actualizador(Juego juego) {
        padre = juego;
        run();
    }

    /**
     * Método que actualiza constantemente el tablero de juego
     */
    @Override
    public void run() {
        while (true) {
            padre.repaint();
        }
    }
}
