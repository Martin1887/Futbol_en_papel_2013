package futbolEnPapel2013.jugador.controladores.hormigas;

import futbolEnPapel2013.Juego;
import futbolEnPapel2013.estructura.Posicion;
import futbolEnPapel2013.estructura.Enlace;
import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.jugador.controladores.Grafo;

/**
 * Controlador de la colonia de hormigas que usa la distancia
 * y el mínimo de la distancia a la propia por el coste para la feromona
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class ColHFerDistRival extends Controlador {

    /**
     * Hormigas
     */
    private HormigaFerDistRival[] hormigas;

    /**
     * Grafo que contiene los nodos factibles y con su coste asociado
     * común a todas las hormigas
     */
    private Grafo grafo;

    /**
     * Padre
     */
    private Juego padre;


    /**
     * Constructor (sólo con el padre como parámetro)
     */
    public ColHFerDistRival(Juego juego) throws CloneNotSupportedException {
        padre = juego;
        hormigas = new HormigaFerDistRival[87];
        grafo = new Grafo(padre.getTablero());
        for (int i = 0; i < hormigas.length; i++) {
            if (padre.getTurnoJug1()) {
                hormigas[i] = new HormigaFerDistRival(grafo, padre.getActual(), new Posicion(5, -1));
            } else {
                hormigas[i] = new HormigaFerDistRival(grafo, padre.getActual(), new Posicion(5, 17));
            }
        }
    }

    /**
     * Constructor con el número de hormigas como parámetro
     */
    public ColHFerDistRival(Juego juego, int nHormigas) throws CloneNotSupportedException {
        padre = juego;
        hormigas = new HormigaFerDistRival[nHormigas];
        grafo = new Grafo(padre.getTablero());
        for (int i = 0; i < hormigas.length; i++) {
            if (padre.getTurnoJug1()) {
                hormigas[i] = new HormigaFerDistRival(grafo, padre.getActual(), new Posicion(5, -1));
            } else {
                hormigas[i] = new HormigaFerDistRival(grafo, padre.getActual(), new Posicion(5, 17));
            }
        }
    }

    /**
     * Método mover
     * @param juego Aunque no se usa hay que ponerlo porque la superclase lo tiene
     */
    @Override
    public void mover(Juego juego) throws CloneNotSupportedException {
        finTurno = false;

        // Se copia al grafo el tablero actual
        grafo = new Grafo(padre.getTablero());

        Posicion meta = null;
        if (padre.getTurnoJug1()) {
            meta = new Posicion(5, -1);
        } else {
            meta = new Posicion(5, 17);
        }

        for (int i = 0; i < hormigas.length; i++) {
            hormigas[i].setGrafo(grafo);
            hormigas[i].setMeta(meta);
            hormigas[i].setHormiguero(padre.getActual());
        }


        /*
         * Primero se limpia la feromona de todos los enlaces y después se
         * mueven las hormigas hasta que se cumpla el criterio de parada
         * (en este caso 15 iteraciones)
         */
        limpiaFeromona();



        // Durante 15 iteraciones se invoca a los métodos necesarios y se mueve
        // a las hormigas hasta que lleguen a la meta (u 100 movimientos)
        // (para que en las siguientes iteraciones usen la
        // feromona de las anteriores)
        for (int n = 0; n < 15 && !Thread.interrupted() && !finTurno; n++) {
            // Se limpia el camino de cada hormiga (es una nueva iteración)
            for (int j = 0; j < hormigas.length; j++) {
                hormigas[j].limpiaCamino();
            }

            // Variable auxiliar para marcar las hormigas que han llegado
            boolean[] llegado = new boolean[hormigas.length];

            for (int i = 0; i < 100 && !todasLlegado(llegado) && !Thread.interrupted() && !finTurno; i++) {
                Hormiga.actualizaTablasDec(grafo);

                // Ahora empieza el movimiento: se crea la tabla de decisión y se
                // mueve cada hormiga que no ha llegado a la meta hasta que lleguen
                // todas u 100 movimientos (se estima que éstos son demasiados, y que
                // si no llegan todas en estos es que hay bloqueo y no pueden llegar)
                for (int j = 0; j < hormigas.length; j++) {
                    // Si no ha llegado se mueve
                    if (!llegado[j]) {
                        if (hormigas[j].mover()) {
                            llegado[j] = true;
                        }
                    }
                }
            }

            // Cuando han llegado todas vuelven todas, y la que tiene el camino
            // más corto vuelve dos veces
            for (int j = 0; j < hormigas.length; j++) {
                // Aunque ponga que ha llegado puede ser que haya devuelto true
                // porque no puede llegar a la portería rival
                if (llegado[j] && hormigas[j].getCamino().size() > 0) {
                    hormigas[j].volver();
                }
            }
            // Sólo vuelve la mejor si ha llegado a la meta
            if (hormigas[mejorHormiga(llegado)].getCamino().size() > 0) {
                hormigas[mejorHormiga(llegado)].volver();
            }


            // Ahora se termina la iteración disipando la feromona
            Hormiga.disiparFeromona(grafo);
        }

        // Ya se tienen todos los enlaces con feromona, ahora se mueve por
        // el enlace que más tiene hasta que acabe el turno
        boolean fin = false;
        // Variable para saber si puede elegir no mover
        boolean primerMov = true;
        while (!fin && !Thread.interrupted() && !finTurno) {
            Enlace[] enlaces = grafo.getGrafo().getNodos()[padre.getActual().getX()]
                    [padre.getActual().getY()].getEnlaces();
            // Se necesitan los enlaces del tablero para saber si está marcado
            // el enlace, pues al mover sólo se marca en el tablero y no en el grafo
            Enlace[] enlacesTablero = padre.getTablero().getNodos()[padre.getActual().getX()]
                    [padre.getActual().getY()].getEnlaces();

            int enlace = mejorEnlace(enlaces);

            while (enlaces[enlace] == null
                    || enlacesTablero[enlace].getMarcado() && !finTurno) {
                // Se pone la feromona de ese enlace a 0 porque no es válido
                // y se busca el siguiente enlace con más feromona
                if (enlaces[enlace] != null) {
                    enlaces[enlace].setFeromona(0);
                }
                enlace = mejorEnlace(enlaces);
            }

            // Si no es el primer movimiento y la feromona del enlace es menor
            // que 0.1 no mueve
            if (!primerMov && enlaces[enlace].getFeromona() < 0.01) {
                return;
            }

            boolean paro = false;

            // Se elige el movimiento dependiendo del enlace que es mejor
            if (padre.getTurnoJug1()) {
                switch (enlace) {
                    case 0:
                        paro = padre.getJug1().moverIzq();
                        break;
                    case 1:
                        paro = padre.getJug1().moverDer();
                        break;
                    case 2:
                        paro = padre.getJug1().moverArr();
                        break;
                    case 3:
                        paro = padre.getJug1().moverAba();
                        break;
                    case 4:
                        paro = padre.getJug1().moverArI();
                        break;
                    case 5:
                        paro = padre.getJug1().moverArD();
                        break;
                    case 6:
                        paro = padre.getJug1().moverAbI();
                        break;
                    case 7:
                        paro = padre.getJug1().moverAbD();
                        break;
                }
            } else {
                switch (enlace) {
                    case 0:
                        paro = padre.getJug2().moverIzq();
                        break;
                    case 1:
                        paro = padre.getJug2().moverDer();
                        break;
                    case 2:
                        paro = padre.getJug2().moverArr();
                        break;
                    case 3:
                        paro = padre.getJug2().moverAba();
                        break;
                    case 4:
                        paro = padre.getJug2().moverArI();
                        break;
                    case 5:
                        paro = padre.getJug2().moverArD();
                        break;
                    case 6:
                        paro = padre.getJug2().moverAbI();
                        break;
                    case 7:
                        paro = padre.getJug2().moverAbD();
                        break;
                }
            }

            // Ahora se comprueba si hay rebote
            if (!paro) {
                primerMov = false;
                // Hay rebote, se deja fin a false y se comprueba si hay bloqueo
                if (bloqueo()) {
                    return;
                }
                // Se espera medio segundo para que dé tiempo a ver los movimientos
                // si el padre no es  (si el jug1 es obscuro el 2 y el juego también)
                try {
                    if (!padre.getJug1().isObscuro()) {
                        Thread.sleep(500);
                    }
                } catch (java.lang.InterruptedException e) {}
            } else {
                // No hay rebote, se ha terminado el movimiento
                fin = true;
            }
        }

    }

    /**
     * Método que comprueba si todas las hormigas han llegado a la meta
     * @param hormigas Array de booleanos con las hormigas que han llegado
     * @return true si han llegado todas las hormigas y false alias
     */
    public boolean todasLlegado(boolean[] hormigas) {
        // Variable que indica el número de hormigas que han llegado
        int llegadas = 0;

        for (int i = 0; i < hormigas.length; i++) {
            if (hormigas[i]) {
                llegadas++;
            }
        }

        if (llegadas == hormigas.length) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Método que devuelve el índice de la hormiga que tiene el camino de
     * menor coste y ha llegado a la portería
     * @param llegado Las hormigas que han llegado o su camino está vacío
     * @return El índice de la hormiga con camino de menor coste
     */
    public int mejorHormiga(boolean[] llegado) {
        int mejor = 0;

        for (int i = 1; i < hormigas.length; i++) {
            if (hormigas[i].getCosteTotal() < hormigas[mejor].getCosteTotal()
                    && llegado[i] && hormigas[i].getCamino().size() > 0) {
                mejor = i;
            }
        }

        return mejor;
    }


    /**
     * Método que devuelve el número del mejor enlace
     * @param enlaces
     * @return mejor. El número del mejor enlace o uno aleatorio si todos tienen
     * feromona 0 o están marcados o nulos
     */
    public int mejorEnlace(Enlace[] enlaces) {
        boolean hayMejor = false;
        int mejor = 0;
        int ceros = 0;
        for (int i = 0; i < enlaces.length; i++) {
            if (enlaces[i] != null && !enlaces[i].getMarcado()) {
                if (hayMejor) {
                    if (enlaces[i].getFeromona() > enlaces[mejor].getFeromona()) {
                        mejor = i;
                    }
                } else {
                    hayMejor = true;
                    mejor = i;
                }
            } else {
                ceros++;
                continue;
            }
            if (enlaces[i].getFeromona() == 0) {
                ceros++;
            }
        }

        // Si todos los enlaces tienen feromona 0 o están marcados o nulos
        // se elige uno aleatoriamente
        if (ceros == enlaces.length) {
            mejor = (int) (Math.random() * enlaces.length);
        }

        return mejor;
    }

    /**
     * Método que limpia la feromona de todos los enlaces del grafo
     */
    public void limpiaFeromona() {
        // Se recorren todos los nodos del grafo y a continuación todos sus
        // enlaces, para después poner su feromona y ferNueva a 0
        for (int i = 0; i < grafo.getGrafo().getNodos().length; i++) {
            for (int j = 0; j < grafo.getGrafo().getNodos()[i].length; j++) {
                for (int k = 0; k < grafo.getGrafo().getNodos()[i][j].getEnlaces().length; k++) {
                    // Para hacer menos get
                    Enlace aux = grafo.getGrafo().getNodos()[i][j].getEnlaces()[k];
                    if (aux != null) {
                        aux.setFeromona(0);
                        aux.setFerNueva(0);
                    }
                }
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
