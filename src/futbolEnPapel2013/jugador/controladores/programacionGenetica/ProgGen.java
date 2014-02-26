package futbolEnPapel2013.jugador.controladores.programacionGenetica;

import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.Juego;
import futbolEnPapel2013.JuegoObscuro;
import futbolEnPapel2013.estructura.*;
import futbolEnPapel2013.jugador.controladores.Grafo;

/**
 * Clase que representa el controlador generado por la programación genética
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class ProgGen extends Controlador {

    /**
     * Constantes que definen los posibles objetivos de la distancia
     */
    public static final int RIVAL = 0;
    public static final int PROPIA = 1;
    public static final int CENTRO = 2;
    public static final int PAREDIZQ = 3;
    public static final int PAREDDER = 4;


    Juego padre;

    /**
     * Constructor completo
     * @param juego
     */
    public ProgGen(Juego juego) {
        padre = juego;
    }


    /**
     * Método mover del controlador de programación genética
     * @param juego El padre
     */
    @Override
    public void mover(Juego juego) {
        padre = (JuegoObscuro) juego;
        
    }


    /**
     * Método que devuelve la distancia a la meta rival en int
     * @return (int) distancia(RIVAL);
     */
    public int distMeta() {
        return (int) distancia(RIVAL);
    }

    /**
     * Método que devuelve la distancia a la meta propia en int
     * @return (int) distancia(PROPIA);
     */
    public int distMiMeta() {
        return (int) distancia(PROPIA);
    }

    /**
     * Método que devuelve la distancia al centro del campo en int
     * @return (int) distancia(CENTRO);
     */
    public int distCentro() {
        return (int) distancia(CENTRO);
    }
    
    /**
     * Método que devuelve la distancia a la pared izquierda en la
     * misma y en int
     * @return (int) distancia(PAREDIZQ);
     */
    public int distParedIzq() {
        return (int) distancia(PAREDIZQ);
    }

    /**
     * Método que devuelve la distancia a la pared derecha en la
     * misma y en int
     * @return (int) distancia(PAREDDER);
     */
    public int distParedDer() {
        return (int) distancia(PAREDDER);
    }

    /**
     * Método que devuelve el número de enlaces del nodo actual marcados
     * @return El número de enlaces del nodo actual marcados
     */
    public int nEnlacesM() {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()];

        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < actual.getEnlaces().length; i++) {
            if (actual.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        return marcados;
    }

    /**
     * Método que devuelve el número de enlaces del nodo pasado por
     * parámetro marcados
     * @param nodo La posición del nodo del que obtener los enlaces marcados
     * @return El número de enlaces del nodo pasado por parámetro marcados
     */
    public int nEnlacesM(Posicion nodo) {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[nodo.getX()][nodo.getY()];

        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < actual.getEnlaces().length; i++) {
            if (actual.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        return marcados;
    }

    /**
     * Método que devuelve el número de enlaces marcados que hay por la
     * izquierda (los de los enlaces de la izquierda y todos los enlaces de
     * los nodos que conectan estos)
     * @return marcados El número de enlaces marcados por la izquierda
     */
    public int nEnlacesMIzq() {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()];

        /**
         * Nodo conectado de alguno de los enlaces de la izquierda (auxiliar)
         */
        Nodo aux;

        /**
         * Posición del nodo anterior (auxiliar)
         */
        Posicion pos;

        /*
         * Primero se miran los enlaces de la izquierda (0, 4 y 6)
         */
        if (actual.getEnlaces()[0].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[4].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[6].getMarcado()) {
            marcados++;
        }

        /*
         * Ahora se miran todos los enlaces de los nodos conectados por los
         * enlaces anteriores
         */
        pos = obtenerNodo(padre.getActual(), 0);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 4);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 6);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }


        return marcados;
    }

    /**
     * Método que devuelve el número de enlaces marcados que hay por la
     * derecha (los de los enlaces de la derecha y todos los enlaces de
     * los nodos que conectan estos)
     * @return marcados El número de enlaces marcados por la derecha
     */
    public int nEnlacesMDer() {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()];

        /**
         * Nodo conectado de alguno de los enlaces de la derecha (auxiliar)
         */
        Nodo aux;

        /**
         * Posición del nodo anterior (auxiliar)
         */
        Posicion pos;

        /*
         * Primero se miran los enlaces de la derecha (1, 5 y 7)
         */
        if (actual.getEnlaces()[1].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[5].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[7].getMarcado()) {
            marcados++;
        }

        /*
         * Ahora se miran todos los enlaces de los nodos conectados por los
         * enlaces anteriores
         */
        pos = obtenerNodo(padre.getActual(), 1);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 5);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 7);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }


        return marcados;
    }

    /**
     * Método que devuelve el número de enlaces marcados que hay por
     * arriba (los de los enlaces de arriba y todos los enlaces de
     * los nodos que conectan estos)
     * @return marcados El número de enlaces marcados por arriba
     */
    public int nEnlacesMArr() {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()];

        /**
         * Nodo conectado de alguno de los enlaces de arriba (auxiliar)
         */
        Nodo aux;

        /**
         * Posición del nodo anterior (auxiliar)
         */
        Posicion pos;

        /*
         * Primero se miran los enlaces de arriba (2, 4 y 5)
         */
        if (actual.getEnlaces()[2].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[4].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[5].getMarcado()) {
            marcados++;
        }

        /*
         * Ahora se miran todos los enlaces de los nodos conectados por los
         * enlaces anteriores
         */
        pos = obtenerNodo(padre.getActual(), 2);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 4);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 5);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }


        return marcados;
    }

    /**
     * Método que devuelve el número de enlaces marcados que hay por
     * abajo (los de los enlaces de abajo y todos los enlaces de
     * los nodos que conectan estos)
     * @return marcados El número de enlaces marcados por abajo
     */
    public int nEnlacesMAba() {
        /**
         * Número de enlaces del nodo actual marcados
         */
        int marcados = 0;

        /**
         * Nodo de la posición actual
         */
        Nodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]
                [padre.getActual().getY()];

        /**
         * Nodo conectado de alguno de los enlaces de abajo (auxiliar)
         */
        Nodo aux;

        /**
         * Posición del nodo anterior (auxiliar)
         */
        Posicion pos;

        /*
         * Primero se miran los enlaces de abajo (3, 6 y 7)
         */
        if (actual.getEnlaces()[3].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[6].getMarcado()) {
            marcados++;
        }
        if (actual.getEnlaces()[7].getMarcado()) {
            marcados++;
        }

        /*
         * Ahora se miran todos los enlaces de los nodos conectados por los
         * enlaces anteriores
         */
        pos = obtenerNodo(padre.getActual(), 3);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 6);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }

        pos = obtenerNodo(padre.getActual(), 7);
        aux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];
        // Por cada enlace marcado se aumenta marcados en 1
        for (int i = 0; i < aux.getEnlaces().length; i++) {
            if (aux.getEnlaces()[i].getMarcado()) {
                marcados++;
            }
        }


        return marcados;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia la izquierda haciendo Math.ceil a 1 / el coste del nodo de la izquierda
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia la izquierda
     */
    public int longCaminoMasLargoIzq() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() - 1, actual.getY());
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia la derecha haciendo Math.ceil a 1 / el coste del nodo de la derecha
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia la derecha
     */
    public int longCaminoMasLargoDer() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() + 1, actual.getY());
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia arriba haciendo Math.ceil a 1 / el coste del nodo de arriba
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia arriba
     */
    public int longCaminoMasLargoArr() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX(), actual.getY() - 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia abajo haciendo Math.ceil a 1 / el coste del nodo de abajo
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia abajo
     */
    public int longCaminoMasLargoAba() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX(), actual.getY() + 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia arriba-izquierda haciendo Math.ceil a 1 / el coste del nodo de arriba-izquierda
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia arriba-izquierda
     */
    public int longCaminoMasLargoArI() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() - 1, actual.getY() - 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia arriba-derecha haciendo Math.ceil a 1 / el coste del nodo de arriba-derecha
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia arriba-derecha
     */
    public int longCaminoMasLargoArD() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() + 1, actual.getY() - 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia abajo-izquierda haciendo Math.ceil a 1 / el coste del nodo de abajo-izquierda
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia abajo-izquierda
     */
    public int longCaminoMasLargoAbI() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() - 1, actual.getY() + 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve la longitud del camino más largo que hay moviendo
     * hacia abajo-derecha haciendo Math.ceil a 1 / el coste del nodo de abajo-derecha
     * en un grafo que se crea en el método a partir del tablero actual
     * @return El camino más largo que hay hacia abajo-derecha
     */
    public int longCaminoMasLargoAbD() {
        int longitud = 0;
        try {
            Posicion actual = padre.getActual();
            Posicion siguiente = new Posicion(actual.getX() + 1, actual.getY() + 1);
            Grafo g = new Grafo(padre.getTablero());
            double coste = g.obtenerEnlace(actual, siguiente)
                    .getCoste(g.obtenerIndiceCoste(actual, siguiente));
            double longitudDouble = 1D / coste;
            longitudDouble = Math.ceil(longitudDouble);
            longitud = (int) longitudDouble;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return longitud;
    }

    /**
     * Método que devuelve el nodo al que conecta un enlace
     * @param nodo Posición del nodo
     * @param enlace Número que representa al enlace en el array del nodo
     * @return La posición del nodo conectado
     */
    public static Posicion obtenerNodo(Posicion nodo, int enlace) {
        Posicion conectado = null;

        // Se comprueban todas las posibilidades entre nodos adyacentes
        switch (enlace) {
            case 0:
                // Izquierda
                conectado = new Posicion(nodo.getX() - 1, nodo.getY());
                break;
            case 1:
                // Derecha
                conectado = new Posicion(nodo.getX() + 1, nodo.getY());
                break;
            case 2:
                // Arriba
                conectado = new Posicion(nodo.getX(), nodo.getY() - 1);
                break;
            case 3:
                // Abajo
                conectado = new Posicion(nodo.getX(), nodo.getY() + 1);
                break;
            case 4:
                // Arriba-Izquierda
                conectado = new Posicion(nodo.getX() - 1, nodo.getY() - 1);
                break;
            case 5:
                // Arriba-Derecha
                conectado = new Posicion(nodo.getX() + 1, nodo.getY() - 1);
                break;
            case 6:
                // Abajo-Izquierda
                conectado = new Posicion(nodo.getX() - 1, nodo.getY() + 1);
                break;
            case 7:
                // Abajo-Derecha
                conectado = new Posicion(nodo.getX() + 1, nodo.getY() + 1);
                break;
        }

        return conectado;
    }

    /**
     * Método que devuelve la distancia de la posición actual
     * a la portería rival o propia o el centro o pared izquierda o derecha
     * (dependiendo del parámetro), donde la distancia es el máximo entre la
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

        // Si el objetivo es alguna pared
        if (objetivo == PAREDIZQ) {
            x2 = 10;
        } else if (objetivo == PAREDDER) {
            x2 = 0;
        }

        // Si el objetivo es alguna portería o el centro
        if (objetivo == RIVAL) {
            if (padre.getTurnoJug1()) {
                y2 = -1;
            } else {
                y2 = 17;
            }
        } else if (objetivo == PROPIA) {
            if (padre.getTurnoJug1()) {
                y2 = 17;
            } else {
                y2 = -1;
            }
        } else if (objetivo == CENTRO) {   
            y2 = 8;
        }

        // Se calcula la distancia
        resultado = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));

        return resultado;
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
