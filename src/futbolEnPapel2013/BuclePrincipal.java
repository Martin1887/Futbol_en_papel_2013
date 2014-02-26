package futbolEnPapel2013;


/**
 * Clase en la que se implementa el bucle principal del juiego
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class BuclePrincipal extends Thread {
    
    /**
     * Padre
     */
    private Juego padre;
    
    /**
     * Constructor completo
     */
    public BuclePrincipal(Juego juego) {
        super();
        padre = juego;
        start();
    }
    
    /**
     * Método que contiene el bucle principal del juego
     */
    @Override
    public void run() {
        try {
            jugar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Método que controla el desarrollo del juego
     */
    public synchronized void jugar() throws Exception {

        // Se hace un pequeño sleep para que se dibujen ambos textos de turno
        // si no es obscuro
        if (padre.getClass().toString().equals("class futbolEnPapel2013.Juego")) {
            padre.getTurnoJ1().setVisible(true);
            padre.getTurnoJ2().setVisible(true);
            padre.repaint();
            Thread.sleep(87);
        }
        
        /**
         * Bucle infinito que no se acaba hasta que se acaba el juego
         */
        while (padre != null && !padre.getFin() && padre.isEnabled()) {
            /**
             * Se crea una instacia del hilo que repinta el tablero
             * constantemente
             */
            //Actualizador actualiza = new Actualizador(padre);
            try {
                /*
                 * Si el turno es del jugador 1
                 */
                if (padre.getTurnoJug1() == true) {
                    if (padre.getClass().toString().equals("class futbolEnPapel2013.Juego")) {
                        padre.getTurnoJ2().setVisible(false);
                        padre.getTurnoJ1().setVisible(true);
                        padre.repaint();
                        padre.getContenedor().repaint();
                    }
                    padre.getJug1().mover();
                    padre.setTurnoJug1(false);
                    // Ha terminado el jugador 1, se da el color del jugador 2
                    //padre.getJug2().setColor(new Color(187, 0, 0, 255));
                } else {    // si el turno es del jugador 2
                    if (padre.getClass().toString().equals("class futbolEnPapel2013.Juego")) {
                        padre.getTurnoJ1().setVisible(false);
                        padre.getTurnoJ2().setVisible(true);
                        padre.repaint();
                        padre.getContenedor().repaint();
                    }
                    padre.getJug2().mover();
                    padre.setTurnoJug1(true);
                    // Ha terminado el jugador 2, se da el color del jugador 1
                    //padre.getJug1().setColor(new Color(0, 0, 187, 255));
                }
            } catch (InterruptedException e) {}
        }

        
        // Si el padre es obscuro se le despierta
        if (!padre.getClass().toString().equals("class futbolEnPapel2013.Juego")) {
            padre.despertar();
        }
        return;
    }
}
