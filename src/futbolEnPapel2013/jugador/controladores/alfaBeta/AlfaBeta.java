package futbolEnPapel2013.jugador.controladores.alfaBeta;

import futbolEnPapel2013.Juego;
import java.util.LinkedList;
import futbolEnPapel2013.estructura.*;
import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.jugador.controladores.Grafo;

/**
 * Clase que implementa un controlador mediante el algoritmo
 * minimax con poda alfa-beta
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class AlfaBeta extends Controlador {

    /**
     * Constante usada en el algoritmo para simular el infinito
     */
    public static final int INFINITO = 2000000000;

    /**
     * Constantes para definir el algoritmo que llama a la función de evaluación
     */
    public static final int MIN_VALOR = 0;
    public static final int MAX_VALOR = 1;


    /**
     * Padre del que se obtienen todos los datos del juego
     */
    private Juego padre;

    /**
     * Profundidad en el momento actual (en la actual llamada recursiva)
     */
    private int profundidad;

    /**
     * Profundidad máxima del algoritmo alfa-beta
     */
    private int profMax;

    /**
     * Grafo que contiene los nodos factibles y con su coste asociado
     */
    private Grafo grafo;

    /**
     * Array bidimensional que contiene todos los estados. Sólo se usa para
     * obtener la raíz
     */
    private EstadoEstatico[][] tableroEstados;

    /**
     * Variable usada para parar al minuto de empezar minValor
     */
    private long t0;


    /**
     * Método que devuelve true si ningún hijo tiene valor v
     * @param estado
     * @param v
     * @return true si ningún hijo tiene valor v y false alias
     */
    public boolean ningunoIgualAV(Estado estado, double v) {
        boolean ninguno = false;

        if ((estado.getHijos()[0] == null || estado.getHijos()[0].getValor() != v)
                && (estado.getHijos()[1] == null || estado.getHijos()[1].getValor() != v)
                && (estado.getHijos()[2] == null || estado.getHijos()[2].getValor() != v)
                && (estado.getHijos()[3] == null || estado.getHijos()[3].getValor() != v)
                && (estado.getHijos()[4] == null || estado.getHijos()[4].getValor() != v)
                && (estado.getHijos()[5] == null || estado.getHijos()[5].getValor() != v)
                && (estado.getHijos()[6] == null || estado.getHijos()[6].getValor() != v)
                && (estado.getHijos()[7] == null || estado.getHijos()[7].getValor() != v)) {
                ninguno = true;
        }

        return ninguno;
    }

    /**
     * Método que mueve durante un turno mediante el algoritmo alfa-beta
     * @param juego
     */
    @Override
    public void mover(Juego juego) throws Exception {
        finTurno = false;
        Estado raiz;
        profundidad = 0;
        padre = juego;
        grafo = new Grafo(padre.getTablero());

        if (padre.getTurnoJug1()) {
            profMax = padre.getNivel1() % 10;
            raiz = new EstadoJ1(new Posicion(padre.getActual().getX(), padre.getActual().getY()));
        } else {
            profMax = padre.getNivel2() % 10;
            raiz = new EstadoJ2(new Posicion(padre.getActual().getX(), padre.getActual().getY()));
        }

        t0 = System.currentTimeMillis();

        // Se comienza con minValor en lugar de con maxValor como en el
        // algoritmo original porque lo que interesa es minimizar la fEvaluacion
        // en lugar de maximizarla
        double v = minValor(raiz, -INFINITO, INFINITO, 0);

        boolean fin = false;
        /*
         * Se realizan los movimientos (más de 1 si hay rebote) mientras el
         * método mover al que se invoque devuelva false (lo que quiere decir
         * que hay rebote porque desde este método no se va a posiciones
         * imposibles)
         */
        for (int j = 0; !fin && !finTurno && !Thread.interrupted() && j <= 15; j++) {
            boolean finInterno = false;
            if (j == 0) {   // si es el 1er mov. se va al que dice el alfa-beta
                // Se realiza el movimiento hacia el sucesor cuyo valor es v
                for (int i = 0; i < raiz.getHijos().length && !finInterno; i++) {
                    // Se selecciona el primer hijo cuyo valor sea v
                    if (raiz.getHijos()[i] != null
                            && raiz.getHijos()[i].getValor() == v) {
                        // Antes de mover se comprueba que el enlace existe y no
                        // está marcado (si todos los hijos tienen evaluación
                        // INFINITO hay que elegir alguno cuyo enlace no esté marcado)
                        int enlace = raiz.enlaceAHijo(i);

                        // Si el enlace es nulo o está marcado se sigue iterando
                        // para encontrar un enlace no marcado
                        if (padre.getTablero().getNodos()[padre.getActual().getX()]
                                [padre.getActual().getY()].getEnlaces()[enlace] == null
                                || padre.getTablero().getNodos()[padre.getActual().getX()]
                                [padre.getActual().getY()].getEnlaces()[enlace].getMarcado()) {
                            finInterno = false;
                            continue;
                        }

                        // La raíz va a pasar a ser el hijo seleccionado (para
                        // siguientes movimientos)
                        raiz = raiz.getHijos()[i];

                        finInterno = true;

                        
                        // Se selecciona el movimiento adecuado dependiendo de si es
                        // el jugador 1 o el 2
                        if (padre.getTurnoJug1()) {
                            switch (enlace) {
                                case 0:
                                    fin = padre.getJug1().moverIzq();
                                    break;
                                case 1:
                                    fin = padre.getJug1().moverDer();
                                    break;
                                case 2:
                                    fin = padre.getJug1().moverArr();
                                    break;
                                case 3:
                                    fin = padre.getJug1().moverAba();
                                    break;
                                case 4:
                                    fin = padre.getJug1().moverArI();
                                    break;
                                case 5:
                                    fin = padre.getJug1().moverArD();
                                    break;
                                case 6:
                                    fin = padre.getJug1().moverAbI();
                                    break;
                                case 7:
                                    fin = padre.getJug1().moverAbD();
                                    break;
                                // Por defecto mueve hacia arriba, aunque si funciona
                                // bien el algoritmo nunca va a pasar
                                default:
                                    fin = padre.getJug1().moverArr();
                                    break;
                            }
                        } else {
                            switch (raiz.enlaceAHijo(i)) {
                                case 0:
                                    fin = padre.getJug2().moverIzq();
                                    break;
                                case 1:
                                    fin = padre.getJug2().moverDer();
                                    break;
                                case 2:
                                    fin = padre.getJug2().moverArr();
                                    break;
                                case 3:
                                    fin = padre.getJug2().moverAba();
                                    break;
                                case 4:
                                    fin = padre.getJug2().moverArI();
                                    break;
                                case 5:
                                    fin = padre.getJug2().moverArD();
                                    break;
                                case 6:
                                    fin = padre.getJug2().moverAbI();
                                    break;
                                case 7:
                                    fin = padre.getJug2().moverAbD();
                                    break;
                                // Por defecto mueve hacia arriba, aunque si funciona
                                // bien el algoritmo nunca va a pasar
                                default:
                                    fin = padre.getJug2().moverArr();
                                    break;
                            }
                        }

                    } else if (ningunoIgualAV(raiz, v)) {
                        // Si ningún hijo tiene valor v se mueve aleatoriamente
                        int aleat = (int) (Math.random() * 8);
                        int enlace = raiz.enlaceAHijo(aleat);
                        while (padre.getTablero().getNodos()[padre.getActual().getX()]
                                [padre.getActual().getY()].getEnlaces()[enlace] == null
                                || padre.getTablero().getNodos()[padre.getActual().getX()]
                                [padre.getActual().getY()].getEnlaces()[enlace].getMarcado()) {
                            aleat = (int) (Math.random() * 8);
                            enlace = raiz.enlaceAHijo(aleat);
                        }

                        // Se selecciona el movimiento adecuado dependiendo de si es
                        // el jugador 1 o el 2
                        if (padre.getTurnoJug1()) {
                            switch (enlace) {
                                case 0:
                                    fin = padre.getJug1().moverIzq();
                                    break;
                                case 1:
                                    fin = padre.getJug1().moverDer();
                                    break;
                                case 2:
                                    fin = padre.getJug1().moverArr();
                                    break;
                                case 3:
                                    fin = padre.getJug1().moverAba();
                                    break;
                                case 4:
                                    fin = padre.getJug1().moverArI();
                                    break;
                                case 5:
                                    fin = padre.getJug1().moverArD();
                                    break;
                                case 6:
                                    fin = padre.getJug1().moverAbI();
                                    break;
                                case 7:
                                    fin = padre.getJug1().moverAbD();
                                    break;
                                // Por defecto mueve hacia arriba, aunque si funciona
                                // bien el algoritmo nunca va a pasar
                                default:
                                    fin = padre.getJug1().moverArr();
                                    break;
                            }
                        } else {
                            switch (raiz.enlaceAHijo(i)) {
                                case 0:
                                    fin = padre.getJug2().moverIzq();
                                    break;
                                case 1:
                                    fin = padre.getJug2().moverDer();
                                    break;
                                case 2:
                                    fin = padre.getJug2().moverArr();
                                    break;
                                case 3:
                                    fin = padre.getJug2().moverAba();
                                    break;
                                case 4:
                                    fin = padre.getJug2().moverArI();
                                    break;
                                case 5:
                                    fin = padre.getJug2().moverArD();
                                    break;
                                case 6:
                                    fin = padre.getJug2().moverAbI();
                                    break;
                                case 7:
                                    fin = padre.getJug2().moverAbD();
                                    break;
                                // Por defecto mueve hacia arriba, aunque si funciona
                                // bien el algoritmo nunca va a pasar
                                default:
                                    fin = padre.getJug2().moverArr();
                                    break;
                            }
                        }
                    }
                }
            } else {    // si no se va al hijo cuyo valor sea el menor
                // Se espera medio segundo para que se vean los rebotes
                // si el padre no es obscuro (si el jug 1 es obscuro el 2 y el
                // juego también)
                if (!padre.getJug1().isObscuro()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                }


                // Si hay bloqueo se termina el movimiento
                if (bloqueo()) {
                    return;
                }
                double min = INFINITO;
                for (int n = 0; n < raiz.getHijos().length; n++) {
                    if (raiz.getHijos()[n] != null
                            && raiz.getHijos()[n].getValor() < min
                            // No se coge como mínimo si el enlace está marcado
                            && !padre.getTablero().getNodos()[raiz.getPosicion().getX()]
                            [raiz.getPosicion().getY()].getEnlaces()[raiz.enlaceAHijo(n)].getMarcado()) {
                        min = raiz.getHijos()[n].getValor();
                    }
                }
                // Se realiza el movimiento hacia el sucesor con menor valor
                for (int i = 0; i < raiz.getHijos().length && !finInterno; i++) {
                    // Si todas las opciones conducen a bloqueo (la mejor
                    // conduce a bloqueo) termina el turno sin mover
                    if (min == INFINITO) {
                        return;
                    } else {
                        // Se selecciona el primer hijo cuyo valor sea la mejor
                        // evaluación (menor) de los hijos y el enlace no esté
                        // marcado
                        if (raiz.getHijos()[i] != null
                                && raiz.getHijos()[i].getValor() == min
                                && !padre.getTablero().getNodos()[raiz.getPosicion().getX()]
                            [raiz.getPosicion().getY()].getEnlaces()[raiz.enlaceAHijo(i)].getMarcado()) {
                            // La raíz va a pasar a ser el hijo seleccionado (para
                            // siguientes movimientos)
                            raiz = raiz.getHijos()[i];

                            finInterno = true;

                            // Se selecciona el movimiento adecuado dependiendo de si es
                            // el jugador 1 o el 2
                            if (padre.getTurnoJug1()) {
                                switch (raiz.enlaceAHijo(i)) {
                                    case 0:
                                        fin = padre.getJug1().moverIzq();
                                        break;
                                    case 1:
                                        fin = padre.getJug1().moverDer();
                                        break;
                                    case 2:
                                        fin = padre.getJug1().moverArr();
                                        break;
                                    case 3:
                                        fin = padre.getJug1().moverAba();
                                        break;
                                    case 4:
                                        fin = padre.getJug1().moverArI();
                                        break;
                                    case 5:
                                        fin = padre.getJug1().moverArD();
                                        break;
                                    case 6:
                                        fin = padre.getJug1().moverAbI();
                                        break;
                                    case 7:
                                        fin = padre.getJug1().moverAbD();
                                        break;
                                    // Por defecto mueve hacia arriba, aunque si funciona
                                    // bien el algoritmo nunca va a pasar
                                    default:
                                        fin = padre.getJug1().moverArr();
                                        break;
                                }
                            } else {
                                switch (raiz.enlaceAHijo(i)) {
                                    case 0:
                                        fin = padre.getJug2().moverIzq();
                                        break;
                                    case 1:
                                        fin = padre.getJug2().moverDer();
                                        break;
                                    case 2:
                                        fin = padre.getJug2().moverArr();
                                        break;
                                    case 3:
                                        fin = padre.getJug2().moverAba();
                                        break;
                                    case 4:
                                        fin = padre.getJug2().moverArI();
                                        break;
                                    case 5:
                                        fin = padre.getJug2().moverArD();
                                        break;
                                    case 6:
                                        fin = padre.getJug2().moverAbI();
                                        break;
                                    case 7:
                                        fin = padre.getJug2().moverAbD();
                                        break;
                                    // Por defecto mueve hacia arriba, aunque si funciona
                                    // bien el algoritmo nunca va a pasar
                                    default:
                                        fin = padre.getJug2().moverArr();
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Método que calcula el valor máximo de los hijos haciendo poda alfa-beta
     * @param estado
     * @param alfa
     * @param beta
     * @param profRebotes Indica el número de veces que se ha llamado al mismo método consecutivamente
     * @return El valor del mejor hijo
     */
    public double maxValor(Estado estado, double alfa, double beta, int profRebotes) {
        double v;


        if (testCorte(estado, profundidad)) {
            double ev = fEvaluacion(estado, MAX_VALOR);
            estado.setValor(ev);
            return ev;
        }

        profundidad++;

        v = -INFINITO;

        if (estado == null) {
            // Sale del método, por lo que vuelve a dejar la anterior profundidad
            profundidad--;
            return v;
        }

        // Se calcula el valor máximo de los sucesores, teniendo en cuenta
        // los rebotes y los movimientos ilegales
        // Se recorren los hijos en orden inverso porque para el jugador contrario
        // los movimientos más probables de ser mejores son los contrarios a los de este
        for (int i = estado.getHijos().length - 1; i >= 0 && (System.currentTimeMillis() - t0) < 60000; i--) {
            int enlace = estado.enlaceAHijo(i);

            Posicion posHijo = Grafo.obtenerPosNodo(estado.getPosicion(), enlace);

            // Antes de nada se comprueba si es un estado
            // de la portería y se puede acceder a él
            if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace] != null
                && !grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()
                && (posHijo.getX() == 5
                || posHijo.getX() == 4
                || posHijo.getX() == 6) && (padre.getTurnoJug1()
                && posHijo.getY() == -1 || !padre.getTurnoJug1()
                && posHijo.getY() == 17)) {
                v = Math.max(0, v);
                // Se crea el hijo y se le da valor 0                
                if (padre.getTurnoJug1()) {
                    try {
                        estado.setHijo(i, new EstadoJ1(posHijo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        estado.setHijo(i, new EstadoJ2(posHijo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                estado.getHijos()[i].setValor(0);
            } else if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace] != null
                && !grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()
                && (posHijo.getX() == 5
                || posHijo.getX() == 4
                || posHijo.getX() == 6) && (padre.getTurnoJug1()
                && posHijo.getY() == 17 || !padre.getTurnoJug1()
                && posHijo.getY() == -1)) {
                v = Math.max(-INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            } else if (posHijo.getX() >= 0
                    && posHijo.getX() < 11
                    && posHijo.getY() >= 0
                    && posHijo.getX() < 17) {
                if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                        [estado.getPosicion().getY()].getEnlaces()[enlace] == null
                        || grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                        [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()) {
                    // No existe el enlace al nodo o está marcado
                    v = Math.max(-INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (enlacesInaccesibles7(posHijo)) {
                    v = Math.max(-INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(-INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (grafo.getGrafo().getNodos()[posHijo.getX()]
                        [posHijo.getY()] != null
                        && grafo.getGrafo().getNodos()[posHijo.getX()]
                        [posHijo.getY()].getMarcado()
                        && profRebotes < 15) {
                    // Nodo hijo marcado con menos de 15 rebotes consecutivos. Rebota

                    if ((System.currentTimeMillis() - t0) < 60000) {
                        // Se crea el hijo
                        if (padre.getTurnoJug1()) {
                            try {
                                estado.setHijo(i, new EstadoJ1(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                estado.setHijo(i, new EstadoJ2(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Se disminuye en 1 la profundidad porque el turno no cambia (rebota)
                        profundidad--;

                        // Se marca el enlace en el grafo (ahora está marcado)
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(true);

                        double aux = Math.max(v, maxValor(estado.getHijos()[i],
                                alfa, beta, profRebotes + 1));

                        // Se vuelve a dejar la profundidad como estaba
                        profundidad++;

                        v = aux;

                        // No se marca definitivamente
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(false);
                    }
                } else {
                    // Es posible y no rebota

                    if ((System.currentTimeMillis() - t0) < 60000) {
                        // Se crea el hijo
                        if (padre.getTurnoJug1()) {
                            try {
                                estado.setHijo(i, new EstadoJ1(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                estado.setHijo(i, new EstadoJ2(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Se marca el enlace en el grafo (ahora está marcado)
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(true);

                        double aux = Math.max(v, minValor(estado.getHijos()[i],
                                alfa, beta, 0));

                        v = aux;

                        // No se marca definitivamente
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(false);
                    }
                }
            }

            if (v >= beta) {
                estado.setValor(v);
                // Sale del método, por lo que vuelve a dejar la anterior profundidad
                profundidad--;
                return v;
            }
            alfa = Math.max(alfa, v);
        }

        estado.setValor(v);

        // Sale del método, por lo que vuelve a dejar la anterior profundidad
        profundidad--;
        return v;
    }

     /**
     * Método que calcula el valor mínimo de los hijos haciendo poda alfa-beta
     * @param estado
     * @param alfa
     * @param beta
     * @param profRebotes Indica el número de veces que se ha llamado al mismo método consecutivamente
     * @return El valor del peor hijo (del mejor hijo para el contrario)
     */
    public double minValor(Estado estado, double alfa, double beta, int profRebotes) {
        double v;


        if (testCorte(estado, profundidad)) {
            double ev = fEvaluacion(estado, MIN_VALOR);
            estado.setValor(ev);
            return ev;
        }

        profundidad++;

        v = INFINITO;

        if (estado == null) {
            // Sale del método, por lo que vuelve a dejar la anterior profundidad
            profundidad--;
            return v;
        }

        // Se calcula el valor mínimo de los sucesores, teniendo en cuenta los
        // rebotes y los movimientos ilegales
        for (int i = 0; i < estado.getHijos().length && (System.currentTimeMillis() - t0) < 60000; i++) {
            int enlace = estado.enlaceAHijo(i);
            Posicion posHijo = Grafo.obtenerPosNodo(estado.getPosicion(), enlace);

            // Antes de nada se comprueba que el hijo exista y si es un estado
            // de la portería
            if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace] != null
                && !grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()
                && (posHijo.getX() == 5
                || posHijo.getX() == 4
                || posHijo.getX() == 6) && (padre.getTurnoJug1()
                && posHijo.getY() == -1 || !padre.getTurnoJug1()
                && posHijo.getY() == 17)) {
                v = Math.min(0, v);
                // Se crea el hijo y se le da valor 0
                if (padre.getTurnoJug1()) {
                    try {
                        estado.setHijo(i, new EstadoJ1(posHijo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        estado.setHijo(i, new EstadoJ2(posHijo));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                estado.getHijos()[i].setValor(0);
            } else if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace] != null
                && !grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()
                && (posHijo.getX() == 5
                || posHijo.getX() == 4
                || posHijo.getX() == 6) && (padre.getTurnoJug1()
                && posHijo.getY() == 17 || !padre.getTurnoJug1()
                && posHijo.getY() == -1)) {
                v = Math.min(INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            } else if (posHijo.getX() >= 0
                    && posHijo.getX() < 11
                    && posHijo.getY() >= 0
                    && posHijo.getX() < 17) {
                if (grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                        [estado.getPosicion().getY()].getEnlaces()[enlace] == null
                        || grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                        [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()) {
                    // No existe el enlace al nodo o está marcado
                    v = Math.min(INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (enlacesInaccesibles7(posHijo)) {
                    v = Math.min(INFINITO, v);
                    // Se crea el hijo
                    if (padre.getTurnoJug1()) {
                        try {
                            estado.setHijo(i, new EstadoJ1(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            estado.setHijo(i, new EstadoJ2(posHijo));
                            estado.getHijos()[i].setValor(INFINITO);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else if (grafo.getGrafo().getNodos()[posHijo.getX()]
                        [posHijo.getY()] != null
                        && grafo.getGrafo().getNodos()[posHijo.getX()]
                        [posHijo.getY()].getMarcado()
                        && profRebotes < 15) {
                    // Nodo hijo marcado con menos de 15 rebotes consecutivos. Rebota

                    if ((System.currentTimeMillis() - t0) < 60000) {
                        // Se crea el hijo
                        if (padre.getTurnoJug1()) {
                            try {
                                estado.setHijo(i, new EstadoJ1(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                estado.setHijo(i, new EstadoJ2(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Se disminuye en 1 la profundidad porque el turno no cambia (rebota)
                        profundidad--;

                        // Se marca el enlace en el grafo (ahora está marcado)
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(true);

                        double aux = Math.min(v, minValor(estado.getHijos()[i],
                                alfa, beta, profRebotes + 1));

                        // Se vuelve a dejar la profundidad como estaba
                        profundidad++;

                        v = aux;

                        // No se marca definitivamente
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(false);
                    }
                } else {
                    // Es posible y no rebota

                    if ((System.currentTimeMillis() - t0) < 60000) {
                        // Se crea el hijo
                        if (padre.getTurnoJug1()) {
                            try {
                                estado.setHijo(i, new EstadoJ1(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                estado.setHijo(i, new EstadoJ2(posHijo));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        // Se marca el enlace en el grafo (ahora está marcado)
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(true);

                        double aux = Math.min(v, maxValor(estado.getHijos()[i],
                                alfa, beta, 0));

                        v = aux;

                        // No se marca definitivamente
                        grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
                                [estado.getPosicion().getY()].getEnlaces()[enlace].setMarcado(false);
                    }
                }
            }
            
            if (v <= alfa) {
                estado.setValor( v);
                // Sale del método, por lo que vuelve a dejar la anterior profundidad
                profundidad--;
                return v;
            }
            beta = Math.min(beta, v);
        }

        estado.setValor(v);

        // Sale del método, por lo que vuelve a dejar la anterior profundidad
        profundidad--;
        return v;
    }

    /**
     * Método que comprueba en el grafo actual si el nodo cuya posición se pasa
     * por parámetro tiene 7 enlaces marcados (o null) (en ese caso al añadir
     * otro la situación sería de bloqueo)
     * @param pos
     * @return true si el nodo cuya posición es pos tiene 7 enlaces marcados (o null) y false alias
     */
    public boolean enlacesInaccesibles7(Posicion pos) {
        boolean inaccesibles7 = false;
        int inaccesibles = 0;

        Enlace[] enlaces = grafo.getGrafo().getNodos()[pos.getX()][pos.getY()].getEnlaces();

        for (int i = 0; i < enlaces.length; i++) {
            if (enlaces[i] == null || enlaces[i].getMarcado()) {
                inaccesibles++;
            }
        }

        if (inaccesibles == 7) {
            inaccesibles7 = true;
        }

        return inaccesibles7;
    }

    /**
     * Método que determina si se ha llegado al fin de la búsqueda (se ha
     * alcanzado la profundidad máxima o se ha metido gol en la portería
     * contraria (({4,5,6}, -1) para el jugador 1 y ({4,5,6}, 17) para el jugador 2)
     * @param estado El estado actual
     * @param profun La profundidad actual
     * @return true si se ha llegado al final de la búsqueda y false alias
     */
    public boolean testCorte(Estado estado, int profun) {
        if (((estado.getPosicion().getX() == 5 || estado.getPosicion().getX() == 4
                || estado.getPosicion().getX() == 6) && (padre.getTurnoJug1()
                && estado.getPosicion().getY() == -1 || !padre.getTurnoJug1()
                && estado.getPosicion().getY() == 17)) || profun == profMax) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Función de evaluación de sucesores. Se podría ejecutar (comentado) el
     * algoritmo A* sobre
     * el grafo del tablero con los nodos no ocupados y devuelve el coste en
     * turnos de llegar a la portería (g). La heurística que usa el algoritmo
     * es la distancia a la portería.
     * En lugar de eso se usa simplemente la distancia, pues es mucho más rápida
     * y tiene resultados parecidos sin costes (y con costes el A* fucniona peor)
     * @param actual El estado actual
     * @param metodo El método invocante (MIN_VALOR o MAX_VALOR)
     * @return coste en turnos a la portería contraria por el camino más corto
     */
    public double fEvaluacion(Estado actual, int metodo) {

        double evaluacion = 0;

        // Si hay bloqueo se devuelve 0.1 si llama MIN_VALOR (porque pierde el rival
        // y es preferible meter gol [evaluación 0] debido a que este algoritmo
        // considera siempre que se hacen todos los rebotes posibles) e INFINITO
        // si invoca MAX_VALOR
        if (bloqueoGrafo(actual.getPosicion())) {
            if (metodo == MIN_VALOR) {
                evaluacion = 0.1;
            } else {
                evaluacion = INFINITO;
            }
        } else {

//            Estado estado = actual;
//            // Listas donde se almacenan los g y h de cada nodo que se almacenaría en ABIERTA
//            LinkedList<Double> g = new LinkedList<Double>();
//            LinkedList<Double> h = new LinkedList<Double>();
//            // Lista donde se almacenan los estados de cada nodo de ABIERTA
//            LinkedList<Estado> estados = new LinkedList<Estado>();
//            // Listas iguales pero de CERRADA
//            LinkedList<Double> gC = new LinkedList<Double>();
//            LinkedList<Double> hC = new LinkedList<Double>();
//            // Lista de antecesores de todos los estados de cada nodo de CERRADA
//            LinkedList<Estado> antecesores = new LinkedList<Estado>();
//            // Sucesores no antecesores de estado
//            Estado[] sucesores = new Estado[8];
//            // Array de Enlace donde se almacenan los enlaces a los nodos
//            Enlace[] enlacesEstado = new Enlace[8];
//            boolean exito = false;
//
//            // Se añade en las listas el estado inicial
//            g.addLast(0.0);
//            h.addLast(distancia(estado));
//            estados.addLast(estado);
//
//            // Hasta que ABIERTA esté vacía (basta con que una esté vacía porque
//            // tienen el mismo tamaño) o exito
//            while (!g.isEmpty() && !exito) {
//                // Se quita el primer nodo de ABIERTA y se mete en CERRADA
//                gC.addLast(g.getFirst());
//                hC.addLast(h.getFirst());
//                g.removeFirst();
//                h.removeFirst();
//                estado = estados.getFirst();
//                antecesores.addLast(estados.getFirst());
//                estados.removeFirst();
//
//                // Ya no hace falta esto
//    //            // Se calculan los rebotes suponiendo que el nodo actual está marcado
//    //            // después se desmarca (no está marcado realmente, pero lo estaría
//    //            // si se fuese por ahí) si no es el estado de gol (el nodo no existe)
//    //            // Lo óptimo sería mantener nodos marcados por caminos, pero es mucho
//    //            // más costoso y no vale la pena, pues raramente va a volver a un nodo
//    //            // por el que ya ha pasado
//                int x = estado.getPosicion().getX();
//                int y = estado.getPosicion().getY();
//    //            if (x >= 0 && x < 11 && y >= 0 && y < 17
//    //                    && !grafo.getGrafo().getNodos()[x][y].getMarcado()) {
//    //                grafo.getGrafo().getNodos()[x][y].setMarcado(true);
//    //                grafo.calcularCostes();
//    //                grafo.getGrafo().getNodos()[x][y].setMarcado(false);
//    //            } else {
//    //                grafo.calcularCostes();
//    //            }
//
//                // SI este nodo es estado final entonces exito = true
//                if (hC.getLast() == 0) {
//                    exito = true;
//                } else {
//                    // SI NO Expandir N, generando el conjunto S de sucesores de N,
//                    // que no son antecesores de N en el grafo
//                    for (int i = 0; i < estado.getHijos().length; i++) {
//                        // Se determina cuál es el enlace
//                        int enlace = estado.enlaceAHijo(i);
//                        if (estado.getPosicion().getX() >= 0 && estado.getPosicion().getX() < 11
//                                && estado.getPosicion().getY() >= 0 && estado.getPosicion().getY() < 17
//                                && estado.getHijos()[i] != null
//                                && !antecesores.contains(estado.getHijos()[i])
//                                && grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
//                                [estado.getPosicion().getY()].getEnlaces()[enlace] != null
//                                && !grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
//                                [estado.getPosicion().getY()].getEnlaces()[enlace].getMarcado()) {
//                            // Si no es antecesor y existe y su enlace existe en el
//                            // grafo y no está marcado se mete en el array
//                            sucesores[i] = estado.getHijos()[i];
//                            enlacesEstado[i] = grafo.getGrafo().getNodos()[estado.getPosicion().getX()]
//                                    [estado.getPosicion().getY()].getEnlaces()[enlace];
//                        } else {
//                            // En caso contrario se pone el elemento i del array a null
//                            sucesores[i] = null;
//                        }
//                    }   // fin for
//
//                    // Añadirlos a ABIERTA
//                    for (int i = 0; i < sucesores.length; i++) {
//                        if (sucesores[i] != null) {
//                            // el g del estado es el g del último elemento de CERRADA
//                            // + el coste del enlace en turnos
//                            g.addLast(gC.getLast() + enlacesEstado[i].getCoste
//                                    (grafo.obtenerIndiceCoste(estado.getPosicion(),
//                                    sucesores[i].getPosicion())));
//                            h.addLast(distancia(sucesores[i]));
//                            estados.addLast(sucesores[i]);
//                        }
//                    }
//                    /*
//                     * Reordenar ABIERTA según f(n) de forma ascendente (los de
//                     * menor f [los mejores] primero)
//                     */
//                    // Primero se meten las evaluaciones de todos los sucesores en
//                    // un array y luego se ordena este array y ABIERTA mediante
//                    // quickSort
//                    double[] fs = new double[g.size()];
//                    if (!g.isEmpty()) {
//                        for (int i = 0; i < fs.length; i++) {
//                            fs[i] = g.get(i) + h.get(i);
//                        }
//                        quickSort(fs, g, h, estados, 0, fs.length - 1);
//                        // Ahora se ordenan las que tienen la misma f de menor a mayor g,
//                        // para que no se encuentre
//                        // una solución subóptima por no haber explotado todas las que
//                        // tienen la misma f pero menor g
//                        int izq = 0;
//                        int der = 0;
//                        double f = fs[0];
//                        while (der < g.size()) {
//                            if ((der + 1) < (g.size()) && f == fs[der + 1]) {
//                                der++;
//                            } else {
//                                quickSortG(g, h, estados, izq, der);
//                                der++;
//                                izq = der;
//                                // Si esto no se cumple es la última iteración
//                                if (izq < g.size()) {
//                                    f = fs[izq];
//                                }
//                            }
//                        }
//                    }
//
//                    // Si alguno de los sucesores estaba ya en ABIERTA
//                    // se elimina de la misma el de mayor (peor) f
//                    for (int i = 0; i < sucesores.length; i++) {
//                        int elemento = estados.indexOf(sucesores[i]);
//                        if (sucesores[i] != null && elemento != -1) {
//                            int elemento2 = estados.lastIndexOf(sucesores[i]);
//                            if (elemento != elemento2) {
//                                estados.remove(elemento2);
//                                g.remove(elemento2);
//                                h.remove(elemento2);
//                            }
//                        }
//                    }
//
//                }   // fin del else
//
//            }   // fin while
//
//            // Si exito se devuelve la suma del último elemento de gC y de hC,
//            // que son el g y el h (la suma es f) del estado actual, y en caso
//            // contrario se devuelve INFINITO porque no hay camino a la portería
//            if (exito) {
//                evaluacion = (gC.getLast() + hC.getLast());
//            } else {
//                evaluacion = INFINITO;
//            }

            evaluacion = distancia(actual);
        }
        return evaluacion;
    }

    /**
     * Método que devuelve la distancia a la portería, donde la distancia es
     * el máximo entre la distancia en X y la distancia en Y
     * @param estado El estado actual
     * @return La distancia a la portería
     */
    public double distancia(Estado estado) {
        double resultado = 0;

        // Posición en la que está la portería rival
        double x2 = 5, y2;
        if (padre.getTurnoJug1()) {
            y2 = -1;
        } else {
            y2 = 17;
        }

        // Posición del estado actual
        double x1 = estado.getPosicion().getX();
        double y1 = estado.getPosicion().getY();

        // Se calcula la distancia
        resultado = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));

        return resultado;
    }

    /**
     * Método que recibe un vector de double (f) y las listas g, h y estados y
     * lo ordena a la vez que ABIERTA en función de su valor de menor a mayor
     * @param vector Las fs de ABIERTA
     * @param g las gs de ABIERTA
     * @param h las hs de ABIERTA
     * @param estados los estados de ABIERTA
     * @param izq Primer elemento que se quiere ordenar del array
     * @param der Último elemento que se quiere ordenar del array
     */
    public static void quickSort(double[] vector, LinkedList<Double> g,
            LinkedList<Double> h, LinkedList<Estado> estados, int izq, int der) {
        // ordena por el algoritmo de quick sort un vector de double
        // comprendidos entre las posiciones izq y der
        // índices
        int i;
        int j;
        double pivote;
        double aux; // variable auxiliar para intercambio
        Estado auxE; // variable auxiliar para intercambio de estados
        pivote = vector[(izq + der) / 2];
        i = izq;
        j = der;
        do {
            while (vector[i] < pivote) {
                i++;
            }
            while (vector[j] > pivote) {
                j--;
            }
            if (i <= j) {
                aux = vector[i];
                vector[i] = vector[j];
                vector[j] = aux;
                // también se intercambian en las listas g, h y estados
                aux = g.get(i);
                g.set(i, g.get(j));
                g.set(j, aux);
                aux = h.get(i);
                h.set(i, h.get(j));
                h.set(j, aux);
                auxE = estados.get(i);
                estados.set(i, estados.get(j));
                estados.set(j, auxE);
                i++;
                j--;
            } // end del if
        } while (i <= j);
        // llamadas recursivas
        if (izq < j) {
            quickSort(vector, g, h, estados, izq, j);
        }
        if (i < der) {
            quickSort(vector, g, h, estados, i, der);
        }
    }

    /**
     * Método que recibe las listas g, h y estados
     * lo ordena a la vez que ABIERTA en función del valor de g de menor a mayor
     * @param g las gs de ABIERTA
     * @param h las hs de ABIERTA
     * @param estados los estados de ABIERTA
     * @param izq Primer elemento que se quiere ordenar del array
     * @param der Último elemento que se quiere ordenar del array
     */
    public static void quickSortG(LinkedList<Double> g,
            LinkedList<Double> h, LinkedList<Estado> estados, int izq, int der) {
        // ordena por el algoritmo de quick sort un vector de double
        // comprendidos entre las posiciones izq y der
        // índices
        int i;
        int j;
        double pivote;
        double aux; // variable auxiliar para intercambio
        Estado auxE; // variable auxiliar para intercambio de estados
        pivote = g.get((izq + der) / 2);
        i = izq;
        j = der;
        do {
            while (g.get(i) < pivote) {
                i++;
            }
            while (g.get(j) > pivote) {
                j--;
            }
            if (i <= j) {
                // se intercambian en las listas g, h y estados
                aux = g.get(i);
                g.set(i, g.get(j));
                g.set(j, aux);
                aux = h.get(i);
                h.set(i, h.get(j));
                h.set(j, aux);
                auxE = estados.get(i);
                estados.set(i, estados.get(j));
                estados.set(j, auxE);
                i++;
                j--;
            } // end del if
        } while (i <= j);
        // llamadas recursivas
        if (izq < j) {
            quickSortG(g, h, estados, izq, j);
        }
        if (i < der) {
            quickSortG(g, h, estados, i, der);
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

    /**
     * Método que comprueba si el jugador no se puede mover en ninguna posición
     * en el grafo desde una posición concreta
     * @return true si no hay movimiento posible y false en caso contrario
     */
    public boolean bloqueoGrafo(Posicion pos) {
        if ((grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[0] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[0].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[1] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[1].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[2] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[2].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[3] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[3].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[4] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[4].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[5] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[5].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[6] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[6].getMarcado())
                && (grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[7] == null
                || grafo.getGrafo().getNodos()[pos.getX()]
                [pos.getY()].getEnlaces()[7].getMarcado())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que crea el tablero de estados
     */
    private void creaTableroEstados() throws Exception {
        if (padre.getTurnoJug1()) {

            // Se asignan las posiciones reales en lugar de las de los índices
            // de tableroEstados (j - 1 en lugar de j)

            // Primero se crean todos los estados, y luego se asignan los hijos
            tableroEstados = new EstadoEstaticoJ1[11][19];
            // Todos los estados excepto las porterías
            for (int i = 0; i < 11; i++) {
                for (int j = 1; j < 18; j++) {
                    tableroEstados[i][j] = new EstadoEstaticoJ1(new Posicion(i, j - 1));
                }
            }
            // Estados de las porterías
            tableroEstados[4][0] = new EstadoEstaticoJ1(new Posicion(4, -1));
            tableroEstados[5][0] = new EstadoEstaticoJ1(new Posicion(5, -1));
            tableroEstados[6][0] = new EstadoEstaticoJ1(new Posicion(6, -1));
            tableroEstados[4][18] = new EstadoEstaticoJ1(new Posicion(4, 17));
            tableroEstados[5][18] = new EstadoEstaticoJ1(new Posicion(5, 17));
            tableroEstados[6][18] = new EstadoEstaticoJ1(new Posicion(6, 17));

            // Ahora se asignan los hijos
            // Estados interiores
            EstadoEstaticoJ1[] hijos;
            for (int i = 1; i < 10; i++) {
                for (int j = 2; j < 17; j++) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][j - 1];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][j - 1];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][j - 1];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][j];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][j];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i - 1][j + 1];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i + 1][j + 1];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i][j + 1];
                    tableroEstados[i][j].setHijos(hijos);
                }
            }
            // Pared de la izquierda (excepto esquinas)
            for (int j = 2; j < 17; j++) {
                hijos = new EstadoEstaticoJ1[8];
                hijos[0] = (EstadoEstaticoJ1) tableroEstados[0][j - 1];
                hijos[1] = null;
                hijos[2] = (EstadoEstaticoJ1) tableroEstados[1][j - 1];
                hijos[3] = null;
                hijos[4] = (EstadoEstaticoJ1) tableroEstados[1][j];
                hijos[5] = null;
                hijos[6] = (EstadoEstaticoJ1) tableroEstados[1][j + 1];
                hijos[7] = (EstadoEstaticoJ1) tableroEstados[0][j + 1];
                tableroEstados[0][j].setHijos(hijos);
            }
            // Esquinas superior izquierda e inferior izquierda
            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            hijos[3] = null;
            hijos[4] = (EstadoEstaticoJ1) tableroEstados[1][1];
            hijos[5] = null;
            hijos[6] = (EstadoEstaticoJ1) tableroEstados[1][2];
            hijos[7] = (EstadoEstaticoJ1) tableroEstados[0][2];
            tableroEstados[0][1].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = (EstadoEstaticoJ1) tableroEstados[0][16];
            hijos[1] = null;
            hijos[2] = (EstadoEstaticoJ1) tableroEstados[1][16];
            hijos[3] = null;
            hijos[4] = (EstadoEstaticoJ1) tableroEstados[1][17];
            hijos[5] = null;
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[0][17].setHijos(hijos);

            // Pared de la derecha
            for (int j = 2; j < 17; j++) {
                hijos = new EstadoEstaticoJ1[8];
                hijos[0] = (EstadoEstaticoJ1) tableroEstados[10][j - 1];
                hijos[1] = (EstadoEstaticoJ1) tableroEstados[9][j - 1];
                hijos[2] = null;
                hijos[3] = (EstadoEstaticoJ1) tableroEstados[9][j];
                hijos[4] = null;
                hijos[5] = (EstadoEstaticoJ1) tableroEstados[9][j + 1];
                hijos[6] = null;
                hijos[7] = (EstadoEstaticoJ1) tableroEstados[10][j + 1];
                tableroEstados[10][j].setHijos(hijos);
            }

            // Esquinas superior derecha e inferior derecha
            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            hijos[3] = (EstadoEstaticoJ1) tableroEstados[9][1];
            hijos[4] = null;
            hijos[5] = (EstadoEstaticoJ1) tableroEstados[9][2];
            hijos[6] = null;
            hijos[7] = (EstadoEstaticoJ1) tableroEstados[10][2];
            tableroEstados[10][1].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = (EstadoEstaticoJ1) tableroEstados[10][16];
            hijos[1] = (EstadoEstaticoJ1) tableroEstados[9][16];
            hijos[2] = null;
            hijos[3] = (EstadoEstaticoJ1) tableroEstados[9][17];
            hijos[4] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[10][17].setHijos(hijos);

            // Pared de arriba excepto esquinas (las esquinas ya están)
            // En los estados de las porterías que están unidos por enlaces
            // marcados no se pone hijo directamente
            for (int i = 1; i < 10; i++) {
                if (i == 4) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = null;
                    hijos[1] = null;
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][0];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][1];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i - 1][2];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i + 1][2];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else if (i == 5) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][0];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][0];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][0];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][1];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i - 1][2];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i + 1][2];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else if (i == 6) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = null;
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][0];
                    hijos[2] = null;
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][1];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i - 1][2];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i + 1][2];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = null;
                    hijos[1] = null;
                    hijos[2] = null;
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][1];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i - 1][2];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i + 1][2];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i][2];
                    tableroEstados[i][1].setHijos(hijos);
                }
            }

            // Pared de abajo excepto esquinas (las esquinas ya están)
            // En los estados de las porterías que están unidos por enlaces
            // marcados no se pone hijo directamente
            for (int i = 1; i < 10; i++) {
                if (i == 4) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][16];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][16];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][17];
                    hijos[5] = null;
                    hijos[6] = null;
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i + 1][18];
                    tableroEstados[i][17].setHijos(hijos);
                } else if (i == 5) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][16];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][16];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][17];
                    hijos[5] = (EstadoEstaticoJ1) tableroEstados[i][18];
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i - 1][18];
                    hijos[7] = (EstadoEstaticoJ1) tableroEstados[i + 1][18];
                    tableroEstados[i][17].setHijos(hijos);
                } else if (i == 6) {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][16];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][16];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][17];
                    hijos[5] = null;
                    hijos[6] = (EstadoEstaticoJ1) tableroEstados[i - 1][18];
                    hijos[7] = null;
                    tableroEstados[i][17].setHijos(hijos);
                } else {
                    hijos = new EstadoEstaticoJ1[8];
                    hijos[0] = (EstadoEstaticoJ1) tableroEstados[i][16];
                    hijos[1] = (EstadoEstaticoJ1) tableroEstados[i - 1][16];
                    hijos[2] = (EstadoEstaticoJ1) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ1) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ1) tableroEstados[i + 1][17];
                    hijos[5] = null;
                    hijos[6] = null;
                    hijos[7] = null;
                    tableroEstados[i][17].setHijos(hijos);
                }
            }

            // Por último se les pone hijos a las porterías
            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = null;
            hijos[6] = (EstadoEstaticoJ1) tableroEstados[5][1];
            hijos[7] = null;
            tableroEstados[4][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = (EstadoEstaticoJ1) tableroEstados[5][1];
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[6][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = (EstadoEstaticoJ1) tableroEstados[4][1];
            hijos[6] = (EstadoEstaticoJ1) tableroEstados[6][1];
            hijos[7] = (EstadoEstaticoJ1) tableroEstados[5][1];
            tableroEstados[5][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = (EstadoEstaticoJ1) tableroEstados[5][17];
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[4][18].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = null;
            hijos[1] = (EstadoEstaticoJ1) tableroEstados[5][17];
            hijos[2] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[6][18].setHijos(hijos);

            hijos = new EstadoEstaticoJ1[8];
            hijos[0] = (EstadoEstaticoJ1) tableroEstados[5][17];
            hijos[1] = (EstadoEstaticoJ1) tableroEstados[4][17];
            hijos[2] = (EstadoEstaticoJ1) tableroEstados[6][17];
            hijos[3] = null;
            hijos[4] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[7] = null;
            tableroEstados[5][18].setHijos(hijos);
        } else {

            // Se asignan las posiciones reales en lugar de las de los índices
            // de tableroEstados (j - 1 en lugar de j)

            // Primero se crean todos los estados, y luego se asignan los hijos
            tableroEstados = new EstadoEstaticoJ2[11][19];
            // Todos los estados excepto las porterías
            for (int i = 0; i < 11; i++) {
                for (int j = 1; j < 18; j++) {
                    tableroEstados[i][j] = new EstadoEstaticoJ2(new Posicion(i, j - 1));
                }
            }
            // Estados de las porterías
            tableroEstados[4][0] = new EstadoEstaticoJ2(new Posicion(4, -1));
            tableroEstados[5][0] = new EstadoEstaticoJ2(new Posicion(5, -1));
            tableroEstados[6][0] = new EstadoEstaticoJ2(new Posicion(6, -1));
            tableroEstados[4][18] = new EstadoEstaticoJ2(new Posicion(4, 17));
            tableroEstados[5][18] = new EstadoEstaticoJ2(new Posicion(5, 17));
            tableroEstados[6][18] = new EstadoEstaticoJ2(new Posicion(6, 17));

            // Ahora se asignan los hijos
            // Estados interiores
            EstadoEstaticoJ2[] hijos;
            for (int i = 1; i < 10; i++) {
                for (int j = 2; j < 17; j++) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][j - 1];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][j - 1];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][j - 1];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][j];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][j];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][j + 1];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][j + 1];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][j + 1];
                    tableroEstados[i][j].setHijos(hijos);
                }
            }
            // Pared de la izquierda (excepto esquinas)
            for (int j = 2; j < 17; j++) {
                hijos = new EstadoEstaticoJ2[8];
                hijos[7] = (EstadoEstaticoJ2) tableroEstados[0][j - 1];
                hijos[5] = null;
                hijos[6] = (EstadoEstaticoJ2) tableroEstados[1][j - 1];
                hijos[3] = null;
                hijos[4] = (EstadoEstaticoJ2) tableroEstados[1][j];
                hijos[0] = (EstadoEstaticoJ2) tableroEstados[0][j + 1];
                hijos[1] = null;
                hijos[2] = (EstadoEstaticoJ2) tableroEstados[1][j + 1];
                tableroEstados[0][j].setHijos(hijos);
            }
            // Esquinas superior izquierda e inferior izquierda
            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[3] = null;
            hijos[4] = (EstadoEstaticoJ2) tableroEstados[1][1];
            hijos[0] = (EstadoEstaticoJ2) tableroEstados[0][2];
            hijos[1] = null;
            hijos[2] = (EstadoEstaticoJ2) tableroEstados[1][2];
            tableroEstados[0][1].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = (EstadoEstaticoJ2) tableroEstados[0][16];
            hijos[5] = null;
            hijos[6] = (EstadoEstaticoJ2) tableroEstados[1][16];
            hijos[3] = null;
            hijos[4] = (EstadoEstaticoJ2) tableroEstados[1][17];
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            tableroEstados[0][17].setHijos(hijos);

            // Pared de la derecha
            for (int j = 2; j < 17; j++) {
                hijos = new EstadoEstaticoJ2[8];
                hijos[7] = (EstadoEstaticoJ2) tableroEstados[10][j - 1];
                hijos[5] = (EstadoEstaticoJ2) tableroEstados[9][j - 1];
                hijos[6] = null;
                hijos[3] = (EstadoEstaticoJ2) tableroEstados[9][j];
                hijos[4] = null;
                hijos[0] = (EstadoEstaticoJ2) tableroEstados[10][j + 1];
                hijos[1] = (EstadoEstaticoJ2) tableroEstados[9][j + 1];
                hijos[2] = null;
                tableroEstados[10][j].setHijos(hijos);
            }

            // Esquinas superior derecha e inferior derecha
            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[3] = (EstadoEstaticoJ2) tableroEstados[9][1];
            hijos[4] = null;
            hijos[0] = (EstadoEstaticoJ2) tableroEstados[10][2];
            hijos[1] = (EstadoEstaticoJ2) tableroEstados[9][2];
            hijos[2] = null;
            tableroEstados[10][1].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = (EstadoEstaticoJ2) tableroEstados[10][16];
            hijos[5] = (EstadoEstaticoJ2) tableroEstados[9][16];
            hijos[6] = null;
            hijos[3] = (EstadoEstaticoJ2) tableroEstados[9][17];
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            tableroEstados[10][17].setHijos(hijos);

            // Pared de arriba excepto esquinas (las esquinas ya están)
            // En los estados de las porterías que están unidos por enlaces
            // marcados no se pone hijo directamente
            for (int i = 1; i < 10; i++) {
                if (i == 4) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = null;
                    hijos[5] = null;
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][0];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][1];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][2];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][2];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else if (i == 5) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][0];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][0];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][0];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][1];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][2];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][2];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else if (i == 6) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = null;
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][0];
                    hijos[6] = null;
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][1];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][2];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][2];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][2];
                    tableroEstados[i][1].setHijos(hijos);
                } else {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = null;
                    hijos[5] = null;
                    hijos[6] = null;
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][1];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][1];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][2];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][2];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][2];
                    tableroEstados[i][1].setHijos(hijos);
                }
            }

            // Pared de abajo excepto esquinas (las esquinas ya están)
            // En los estados de las porterías que están unidos por enlaces
            // marcados no se pone hijo directamente
            for (int i = 1; i < 10; i++) {
                if (i == 4) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][16];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][16];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][17];
                    hijos[0] = null;
                    hijos[1] = null;
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][18];
                    tableroEstados[i][17].setHijos(hijos);
                } else if (i == 5) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][16];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][16];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][17];
                    hijos[0] = (EstadoEstaticoJ2) tableroEstados[i][18];
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][18];
                    hijos[2] = (EstadoEstaticoJ2) tableroEstados[i + 1][18];
                    tableroEstados[i][17].setHijos(hijos);
                } else if (i == 6) {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][16];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][16];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][17];
                    hijos[0] = null;
                    hijos[1] = (EstadoEstaticoJ2) tableroEstados[i - 1][18];
                    hijos[2] = null;
                    tableroEstados[i][17].setHijos(hijos);
                } else {
                    hijos = new EstadoEstaticoJ2[8];
                    hijos[7] = (EstadoEstaticoJ2) tableroEstados[i][16];
                    hijos[5] = (EstadoEstaticoJ2) tableroEstados[i - 1][16];
                    hijos[6] = (EstadoEstaticoJ2) tableroEstados[i + 1][16];
                    hijos[3] = (EstadoEstaticoJ2) tableroEstados[i - 1][17];
                    hijos[4] = (EstadoEstaticoJ2) tableroEstados[i + 1][17];
                    hijos[0] = null;
                    hijos[1] = null;
                    hijos[2] = null;
                    tableroEstados[i][17].setHijos(hijos);
                }
            }

            // Por último se les pone hijos a las porterías
            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = (EstadoEstaticoJ2) tableroEstados[5][1];
            tableroEstados[4][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = (EstadoEstaticoJ2) tableroEstados[5][1];
            hijos[2] = null;
            tableroEstados[6][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = (EstadoEstaticoJ2) tableroEstados[5][1];
            hijos[1] = (EstadoEstaticoJ2) tableroEstados[4][1];
            hijos[2] = (EstadoEstaticoJ2) tableroEstados[6][1];
            tableroEstados[5][0].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = null;
            hijos[6] = (EstadoEstaticoJ2) tableroEstados[5][17];
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            tableroEstados[4][18].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = null;
            hijos[5] = (EstadoEstaticoJ2) tableroEstados[5][17];
            hijos[6] = null;
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            tableroEstados[6][18].setHijos(hijos);

            hijos = new EstadoEstaticoJ2[8];
            hijos[7] = (EstadoEstaticoJ2) tableroEstados[5][17];
            hijos[5] = (EstadoEstaticoJ2) tableroEstados[4][17];
            hijos[6] = (EstadoEstaticoJ2) tableroEstados[6][17];
            hijos[3] = null;
            hijos[4] = null;
            hijos[0] = null;
            hijos[1] = null;
            hijos[2] = null;
            tableroEstados[5][18].setHijos(hijos);
        }

        // Ahora se clonan todos los estados para que tegan los mismos valores
        // pero sus hijos sean distintos
//        for (int i = 0; i < tableroEstados.length; i++) {
//            for (int j = 0; j < tableroEstados[i].length; j++) {
//                if (tableroEstados[i][j] != null) {
//                    for (int k = 0; k < tableroEstados[i][j].getHijos().length; k++) {
//                        if (tableroEstados[i][j].getHijos()[k] != null) {
//                            tableroEstados[i][j].getHijos()[k] =
//                                    tableroEstados[i][j].getHijos()[k].clone();
//                        }
//                    }
//                }
//            }
//        }
    }

}
