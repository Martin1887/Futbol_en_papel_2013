package futbolEnPapel2013.jugador;

import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.*;
import futbolEnPapel2013.estructura.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

/**
 * Clase que implementa la funcionalidad de un jugador, sea humano o no
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Jugador {

    /**
     * Constantes que representan las posiciones donde comienzan los nodos
     * y la distancia entre nodos
     */

    public static final int ANCHO_INI = 120;
    public static final int ALTO_INI = 95;
    public static final int DIST_NODOS = 24;
    public static final int ALTO_GOL = 13;


    /**
     * Constantes que definen los posibles objetivos de la distancia euclídea
     */
    public static final int JUG1 = 1;
    public static final int JUG2 = 2;



    /**
     * Padre
     */
    protected Juego padre;

    /**
     * Padre en el caso de que el juego sea obscuro (aquí se inicializa a null)
     */
    protected JuegoObscuro padreObscuro;

    /**
     * Booleano que indica si el juego es obscuro o no
     */
    protected boolean obscuro;

    /**
     * Controlador que realiza el movimiento. Si es null el jugador es una
     * persona
     */
    protected Controlador controlador;



    /**
     * Atributo auxiliar para modificar más fácilmente el valor marcado de los
     * enlaces de los nodos
     */
    protected Enlace[] enlacesAux;

    /**
     * Variable usada sólo para el improbable caso de que termine el
     * temporizador mientras se está tratando un movimiento, caso en el cual
     * se deja terminar el movimiento
     */
    private boolean posModificada;

    /**
     * Temporizador usado para los rebotes. Es necesario que sea un atributo
     * para que Mover lo pueda parar en caso de que se realice un movimiento
     * correcto antes de que salte el temporizador
     */
    protected Timer temporizador;

    /**
     * Variable usada para que cuando haya saltado el temporizador aunque
     * no se produzca movimiento porque éste sea incorrecto se devuelva true
     * para que se termine el turno
     */
    protected boolean finTemporizador;

    /**
     * Objeto de la clase Mover
     */
    protected Mover mover;
    
    /**
     * Variable que indica si hay que matar al abuelo
     */
    protected boolean dispose;

    /**
     * Para poder tener un constructor en JugadorObscuro
     */
    public Jugador() {}

    /**
     * Constructor completo
     * @param parent
     * @param controla El controlador de este jugador
     */
    public Jugador(javax.swing.JDialog parent, Controlador controla) {
        padre = (futbolEnPapel2013.Juego) parent;
        padreObscuro = null;
        obscuro = false;
        finTemporizador = false;
        setPosModificada(false);
        controlador = controla;
    }


    /**
     * Método get del padre
     * @return padre
     */
    public Juego getPadre() {
        return padre;
    }

    /**
     * Método set del padre
     * @param pariente
     */
    public void setPadre(Juego pariente) {
        padre = pariente;
    }

    /**
     * Método get del padre obscuro
     * @return padreObscuro
     */
    public JuegoObscuro getPadreObscuro() {
        return padreObscuro;
    }

    /**
     * Método set del padre obscuro
     * @param pariente
     */
    public void setPadreObscuro(JuegoObscuro pariente) {
        padreObscuro = pariente;
    }

    /**
     * Devuelve true si es obscuro y false en caso contrario
     * @return obscuro
     */
    public boolean isObscuro() {
        return obscuro;
    }

    /**
     * Método get del controlador
     * @return controlador
     */
    public Controlador getControlador() {
        return controlador;
    }

    /**
     * Método set del controlador
     * @param controla
     */
    public void setControlador(Controlador controla) {
        controlador = controla;
    }

    /**
     * Método get de posModificada
     * @return posModificada
     */
    public synchronized boolean getPosModificada() {
        return posModificada;
    }

    /**
     * Método set del atributo posModificada. Sólo lo usa la propia clase
     * (es private), pero se pone para que al modificar la variable se haga
     * de forma synchronized
     * @param nuevo
     */
    protected synchronized void setPosModificada(boolean nuevo) {
        posModificada = nuevo;
    }

    /**
     * Método get del atributo temporizador
     * @return temporizador
     */
    public synchronized Timer getTemporizador() {
        return temporizador;
    }

    /**
     * Método set del atributo temporizador
     * @param nuevo
     */
    public synchronized void setTemporizador(Timer nuevo) {
        temporizador = nuevo;
    }

    /**
     * Mëtodo set del atributo temporizador. Necesario para el hilo Mover
     * @param nuevo
     */
    public synchronized void setFinTemporizador(boolean nuevo) {
        finTemporizador = nuevo;
    }

    /**
     * Método que activa el fin del temporizador (invocado por el temporizador)
     */
    public synchronized void activarFinTemporizador() {
        finTemporizador = true;
    }

    /**
     * Método get del atributo mover
     * @return mover
     */
    public Mover getMover() {
        return mover;
    }

    /**
     * Método que pone que mata el hilo mover para que muera al cerrar el juego
     */
    public void matarMover() {
        mover.interrupt();
        mover = null;
    }


    /**
     * Método que mueve durante un turno
     */
    public void mover() throws Exception {
        mover = new Mover(padre, this);
        mover.start();
        mover.join();
//        if (dispose) {
//            padre.dispose();
//        }
    }

    /**
     * Método que devuelve la distancia de la posición actual
     * a la portería rival o propia o el centro o pared izquierda o derecha
     * (dependiendo del parámetro), donde la distancia es el máximo de la
     * distancia en X y la distancia en Y
     * @param objetivo El objetivo (portería rival o propia o centro del campo o pared izquierda o derecha)
     * @return La distancia al objetivo requerido
     */
    public double distancia(int objetivo) {
        double resultado = 0;

        // Posición del estado actual
        double x1 = padre.getActual().getX();
        double y1 = padre.getActual().getY();


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

    /*
     *
     * Métodos genéricos para moverse hacia un nodo adyacente
     *
     */

    /**
     * Mueve a la izquierda
     * @return true si ha terminado el turno y false en caso contrario
     */
    public boolean moverIzq() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[0] != null) {

            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[0].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() - 1,
                        padre.getActual().getY()));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() + 1, padre.getActual().getY()));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[1].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                        // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[1].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverDer() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[1] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[1].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() + 1,
                        padre.getActual().getY()));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100)
                                ;padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                                Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() - 1, padre.getActual().getY()));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[0].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                        // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");
                        
                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                        
                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                        
                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[0].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY()) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverArr() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[2] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[2].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX(),
                        padre.getActual().getY() - 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior de gol
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior de gol
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX(), padre.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[3].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea superior
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                        // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();                    
                    enlacesAux[3].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea superior
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverAba() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[3] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[3].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX(),
                        padre.getActual().getY() + 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior de gol
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior de gol
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de golpadre.getContenedor().getGraphics()
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX(), padre.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[2].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea inferior
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                       // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[2].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea inferior
                    int x1 = ((padre.getActual().getX()) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverArI() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[4] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[4].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() - 1,
                        padre.getActual().getY() - 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() + 1, padre.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[7].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal superior izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                        // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[7].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal superior izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverArD() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[5] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[5].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() + 1,
                        padre.getActual().getY() - 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea superior derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * ALTO_GOL + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() - 1, padre.getActual().getY() + 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[6].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal superior derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                        // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[6].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal superior derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() + 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverAbI() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[6] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[6].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() - 1,
                        padre.getActual().getY() + 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior izquierda de gol
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() + 1, padre.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[5].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal inferior izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                       // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[5].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal inferior izquierda
                    int x1 = ((padre.getActual().getX() + 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
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
    public boolean moverAbD() {
        /*
         * Sólo se procede a comprobar si el movimiento es válido y realizarlo
         * si el enlace existe (no está fuera del tablero)
         */
        if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[7] != null) {
            
            if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces()[7].getMarcado() == false) {

                // Ya ha entrado en el método, por lo que se pone posModificada a true
                setPosModificada(true);

                padre.setActual(new Posicion(padre.getActual().getX() + 1,
                        padre.getActual().getY() + 1));

                
                
                /*
                 * Si el valor de actual es este se ha marcado gol
                 */
                if ((padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1() == false
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha marcado gol
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado este jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        } else {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if // Se mete gol en propia puerta
                        ((!padre.getTurnoJug1() && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == -1)
                        || (padre.getTurnoJug1()
                        && (padre.getActual().getX() == 5 || padre.getActual().getX() == 4 || padre.getActual().getX() == 6)
                        && padre.getActual().getY() == 17)) {
                    // Dibujar línea inferior derecha de gol
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = y1 + ALTO_GOL;   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());

                    // Se ha metido gol en propia puerta
                    // Se dibuja la imagen de gol
                    padre.getContenedor().setImagenArriba("/recursos/gol.png");
                            
                    try {
                        // Se deja 2 segundos
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        // Se dibuja la imagen de que ha ganado el otro jugador
                        if (padre.getTurnoJug1()) {
                            // gana jug 2
                            padre.getContenedor().setImagenAbajo("/recursos/jug2.png");
                    
                        } else {
                            // gana jug 1
                            padre.getContenedor().setImagenAbajo("/recursos/jug1.png");
                    
                        }
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(100);
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        Thread.sleep(2000);
                        
                        
                        padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    } catch (java.lang.InterruptedException e) {}
                    padre.finalizar();
                    dispose = true;
                    setPosModificada(false);
                    return true;
                } else if (padre.getActual().getX() < 0
                        || padre.getActual().getX() > 10
                        || padre.getActual().getY() < 0
                        || padre.getActual().getY() > 16) {
                    // Movimiento fuera del campo (no válido) (no debería entrar aquí, el enlace vale null)
                    // Se vuelve a la posición antes del movimiento
                    padre.setActual(new Posicion(padre.getActual().getX() - 1, padre.getActual().getY() - 1));
                    /*
                     * Si ha saltado el temporizador se devuelve true para que
                     * finalice el turno, en caso contrario se devuelve false
                     */
                    if (finTemporizador) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getMarcado()) {
                    // Se marca el enlace (el nodo ya está)
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[4].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal inferior derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
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
                       // Se dibuja la imagen de bloqueo
                        padre.getContenedor().setImagenArriba("/recursos/bloqueo.png");

                        try {
                            // Se deja 2 segundos
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            // Se dibuja la imagen de que ha ganado el otro jugador
                            if (padre.getTurnoJug1()) {
                                // gana jug 2
                                padre.getContenedor().setImagenAbajo("/recursos/jug2.png");

                            } else {
                                // gana jug 1
                                padre.getContenedor().setImagenAbajo("/recursos/jug1.png");

                            }

                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(100);
                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                            Thread.sleep(2000);


                            padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                        } catch (java.lang.InterruptedException e) {}
                        finally {
                            if (temporizador != null) {
                                temporizador.stop();
                                temporizador.removeActionListener(temporizador.getActionListeners()[0]);
                                temporizador = null;
                            }
                        }
                        padre.finalizar();
                        dispose = true;
                        setPosModificada(false);
                        return true;
                    }
                    return false;   // No se ha terminado de mover, queda el posible rebote
                } else {
                    // Se marca el nodo y el enlace
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setMarcado(true);
                    enlacesAux = padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].getEnlaces();
                    enlacesAux[4].setMarcado(true);
                    padre.getTablero().getNodos()[padre.getActual().getX()][padre.getActual().getY()].setEnlaces(enlacesAux);
                    // Dibujar línea diagonal inferior derecha
                    int x1 = ((padre.getActual().getX() - 1) * DIST_NODOS + ANCHO_INI);  // ancho anterior
                    int y1 = ((padre.getActual().getY() - 1) * DIST_NODOS + ALTO_INI);   // alto anterior
                    int x2 = (padre.getActual().getX() * DIST_NODOS + ANCHO_INI);  // ancho actual
                    int y2 = (padre.getActual().getY() * DIST_NODOS + ALTO_INI);   // alto actual
                    Color color = null;
                    if (padre.getTurnoJug1()) {
                        color = new Color(40, 40, 255, 255);
                    } else {
                        color = new Color(187, 0, 0, 255);
                    }
                    padre.getContenedor().anyadeLinea(x1, y1, x2, y2, color);
                    padre.getContenedor().paintImmediately(0, 0, padre.getContenedor().getWidth(), padre.getContenedor().getHeight());
                    setPosModificada(false);
                    return true;
                }
            } else {    // Enlace marcado
                setPosModificada(false);

                // Se reproduce sonido de error para que el usuario se dé cuenta
                // de que el movimiento no es válido
                if (controlador == null) {
                    try {
                        // Se obtiene un Clip de sonido
                        Clip sonido = AudioSystem.getClip();

                        // Se carga con un fichero wav
                        sonido.open(AudioSystem.getAudioInputStream
                                (getClass().getResource("/recursos/Windows Error.wav")));

                        // Comienza la reproducción
                        sonido.start();

                        // Espera mientras se esté reproduciendo.
                        while (sonido.isRunning())
                            Thread.sleep(10);

                        // Se cierra el clip.
                        sonido.close();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
                if (finTemporizador) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            // No se ha realizado el movimiento

            // Se reproduce sonido de error para que el usuario se dé cuenta
            // de que el movimiento no es válido
            if (controlador == null) {
                try {
                    // Se obtiene un Clip de sonido
                    Clip sonido = AudioSystem.getClip();

                    // Se carga con un fichero wav
                    sonido.open(AudioSystem.getAudioInputStream
                            (getClass().getResource("/recursos/Windows Error.wav")));

                    // Comienza la reproducción
                    sonido.start();

                    // Espera mientras se esté reproduciendo.
                    while (sonido.isRunning())
                        Thread.sleep(10);

                    // Se cierra el clip.
                    sonido.close();
                } catch (Exception e) {
                   e.printStackTrace();
                }
            }
            if (finTemporizador) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Método que comprueba si el jugador no se puede mover en ninguna posición
     * @return true si no hay movimiento posible y false en caso contrario
     */
    public boolean bloqueo() {
        if ((padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[0] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[0].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[1] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[1].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[2] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[2].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[3] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[3].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[4] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[4].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[5] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[5].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[6] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[6].getMarcado())
                && (padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[7] == null
                || padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()].getEnlaces()[7].getMarcado())) {
            return true;
        } else {
            return false;
        }
    }
}
