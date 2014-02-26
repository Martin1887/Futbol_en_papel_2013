package futbolEnPapel2013.estructura;


/**
 * Clase que representa el tablero de juebo del FIFA Paper 2010.
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Tablero {

    /**
     * Array que representa al conjunto de nodos del tablero.
     * La primera dimensión se corresponde con la coordenada
     * x y la segunda con la coordenada y del nodo en cuestión
     */
    private Nodo[][] nodos;

    /**
     * Array que representa al conjunto de enlaces del tablero.
     * La primera dimensión se corresponde con la coordenada x, la segunda
     * con la coordenada y, y la tercera representa el sentido de la siguiente
     * forma:
     *
     * 0: horizontal (hacia la izquierda) (O)
     * 1: vertical (hacia arriba) (N)
     * 2: diagonal izquierda arriba (NO)
     * 3: diagonal derecha arriba (NE)
     */
    private Enlace[][][] enlaces;



    /**
     * Array auxiliar usado para asignar enlaces a los nodos
     */
    private Enlace[] aux = new Enlace[8];

    /**
     * Constructor por defecto (tablero de 10 x 16 cuadrados, 11 x 17 nodos
     * y enlaces)
     */
    public Tablero() {

        /**
         * Creación del array de nodos
         */
        nodos = new Nodo[11][17];

        /**
         * Creación del array de enlaces. Hay una dimensión más en altura para
         * los enlaces que van a la portería de abajo (el nodo no es necesario
         * crearlo, pero el enlace sí)
         */
        enlaces = new Enlace[11][18][4];

        /**
         * Creación de cada nodo individual asignando valor
         * a su atributo marcado
         */

        // Nodos interiores (todos menos las paredes y el centro de las
        // porterías
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 16; j++) {
                /*
                 * La línea central tiene todos los nodos marcados y
                 * se encuentra en j = 8
                 */
                if (j != 8) {
                    nodos[i][j] = new Nodo();
                } else {
                    nodos[i][j] = new Nodo(true);
                }
            }
        }

        /*
         * Todos los nodos con alguna coordenada 0 menos el centro de las
         * porterías (5,0) son pared, y por ende están marcados al inicio
         */

        // Pared de la izquierda
        for (int j = 0; j < nodos[0].length; j++) {
            nodos[0][j] = new Nodo(true);
        }

        // Pared de arriba (el centro de la portería no está marcado)
        for (int i = 0; i < nodos.length; i++) {
            if (i != 5) {
                nodos[i][0] = new Nodo(true);
            }
        }

        /*
         * Creación del nodo de la portería sin marcar
         */
        nodos[5][0] = new Nodo();

        /*
         * Lo mismo ocurre con los nodos con coordenada x = 10 y coordenada
         * y = 16 (excepto el centro de la portería (5,16))
         */

        // Pared de la derecha
        for (int j = 0; j < nodos[0].length; j++) {
            nodos[10][j] = new Nodo(true);
        }

        // Pared de abajo (el centro de la portería no está marcado)
        for (int i = 0; i < nodos.length; i++) {
            if (i != 5) {
                nodos[i][16] = new Nodo(true);
            }
        }

        /*
         * Creación del nodo de la portería sin marcar
         */
        nodos[5][16] = new Nodo();

        /**
         * Fin de creación de nodos
         */

        /**
         * Creación de cada enlace individual asignando valor a su atributo
         * marcado
         */

        // Enlaces interiores (todos excepto paredes)
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 16; j++) {
                /*
                 * Los enlaces izquierdos de la línea central (j = 8) están
                 * marcados
                 */
                if (j != 8) {
                    enlaces[i][j][0] = new Enlace();
                    enlaces[i][j][1] = new Enlace();
                    enlaces[i][j][2] = new Enlace();
                    enlaces[i][j][3] = new Enlace();
                } else {
                    enlaces[i][j][0] = new Enlace(true);
                    enlaces[i][j][1] = new Enlace();
                    enlaces[i][j][2] = new Enlace();
                    enlaces[i][j][3] = new Enlace();
                }
            }
        }

        // Pared de abajo

        /*
         * Se crean todos los de la izquierda marcados y los demás sin marcar
         * excepto los de los dos nodos de las esquinas, que se crean luego
         */
        for (int i = 1; i < 10; i++) {
            // Los que están encima de la portería también tienen el de la
            // izquierda sin marcar
            if (i != 5 && i != 6) {
                enlaces[i][16][0] = new Enlace(true);
                enlaces[i][16][1] = new Enlace();
                enlaces[i][16][2] = new Enlace();
                enlaces[i][16][3] = new Enlace();
            } else {
                enlaces[i][16][0] = new Enlace();
                enlaces[i][16][1] = new Enlace();
                enlaces[i][16][2] = new Enlace();
                enlaces[i][16][3] = new Enlace();
            }
        }

        // Enlaces a la portería de abajo
        // El vertical
        enlaces[5][17][1] = new Enlace();
        // El diagonal derecha (izquierda desde abajo)
        enlaces[5][17][2] = new Enlace();
        // El diagonal izquierda (derecha desde abajo)
        enlaces[5][17][3] = new Enlace();
        // El que va a la esquina izquierda de la portería
        enlaces[4][17][3] = new Enlace();
        // El que va a la esqina derecha de la portería
        enlaces[6][17][2] = new Enlace();

        // Pared de la derecha

        /*
         * Se crean todos los de arriba marcados, y los demás sin marcar
         * (excepto los de la diagonal derecha, que no existen)
         * excepto los de los dos nodos de las esquinas
         */
        for (int j = 1; j < 16; j++) {
            /*
             * Queda por marcar el enlace izquierdo de la pared derecha
             * en j = 8 para terminar de marcar los enlaces de la línea
             * central
             */
            if (j != 8) {
                enlaces[10][j][0] = new Enlace();
                enlaces[10][j][1] = new Enlace(true);
                enlaces[10][j][2] = new Enlace();
            } else {
                enlaces[10][j][0] = new Enlace(true);
                enlaces[10][j][1] = new Enlace(true);
                enlaces[10][j][2] = new Enlace();
            }
        }

        // Enlaces del nodo de la esquina superior derecha (sólo el de la
        // izquierda y marcado)
        enlaces[10][0][0] = new Enlace(true);

        // Enlaces del nodo de la esquina inferior derecha (izquieda y
        // arriba marcados y diagonal izquierda sin marcar y diagonal derecha
        // no existe)
        enlaces[10][16][0] = new Enlace(true);
        enlaces[10][16][1] = new Enlace(true);
        enlaces[10][16][2] = new Enlace();

        // Pared de arriba

        /*
         * Sólo se crean los de la izquierda marcados y excepto los de las
         * esquinas (el de la esquina derecha ya está creado y el de la
         * esquina izquierda no tiene ningún enlace)
         */
        for (int i = 1; i < 10; i++) {
            // Los que están justo encima de la portería no están marcados
            if (i != 5 && i != 6) {
                enlaces[i][0][0] = new Enlace(true);
            } else {
                enlaces[i][0][0] = new Enlace();
            }
        }

        // Enlaces hacia la portería de arriba
        enlaces[5][0][1] = new Enlace();
        enlaces[5][0][2] = new Enlace();
        enlaces[5][0][3] = new Enlace();
        enlaces[4][0][3] = new Enlace();
        enlaces[6][0][2] = new Enlace();


        // Pared izquierda

        /*
         * Sólo se crean el de arriba marcado y el de la diagonal derecha
         * sin marcar y sin el de la esquina superior
         */
        for (int j = 1; j < 17; j++) {
            enlaces[0][j][1] = new Enlace(true);
            enlaces[0][j][3] = new Enlace();
        }

        /*
         *
         * Fin de creación de enlaces
         *
         */

        /*
         *
         * Asignación de enlaces a cada nodo
         *
         */

        /*
         * Se usa el array auxiliar aux para asignar los enlaces a cada nodo
         */

        // Nodos interiores

        /*
         * Cada nodo interior tiene los enlaces de las posiciones 0, 2, 4 y 5
         * de su misma posición (respectivamente, 0, 1, 2 y 3 de los enlaces),
         * el 1 se corresponde con el 0 de la posición i + 1, el 3 se
         * corresponde con el 1 de la posición j + 1, el 6 se corresponde con
         * el 3 de la posición i - 1 j + 1, y el 7 se corresponde con el 2
         * de la posición i + 1 j + 1
         */
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 16; j++) {
                // Vaciado del array auxiliar aux
                for (int m = 0; m < aux.length; m++) {
                    aux[m] = null;
                }

                aux[0] = enlaces[i][j][0];
                aux[1] = enlaces[i + 1][j][0];
                aux[2] = enlaces[i][j][1];
                aux[3] = enlaces[i][j + 1][1];
                aux[4] = enlaces[i][j][2];
                aux[5] = enlaces[i][j][3];
                aux[6] = enlaces[i - 1][j + 1][3];
                aux[7] = enlaces[i + 1][j + 1][2];

                nodos[i][j].setEnlaces(aux);
            }
        }

        // Pared de abajo

        /*
         * La pared de abajo (excepto las esquinas) no tiene los tres enlaces
         * inferiores excepto los nodos justo encima de la portería
         */
        for (int i = 1; i < 10; i++) {
            // Vaciado del array auxiliar aux
            for (int m = 0; m < aux.length; m++) {
                aux[m] = null;
            }

            // Nodos justo encima de la portería
            if (i == 4) {
                aux[0] = enlaces[i][16][0];
                aux[1] = enlaces[i + 1][16][0];
                aux[2] = enlaces[i][16][1];
                aux[3] = null;
                aux[4] = enlaces[i][16][2];
                aux[5] = enlaces[i][16][3];
                aux[6] = null;
                aux[7] = enlaces[5][17][2];
            } else if (i == 5) {
                aux[0] = enlaces[i][16][0];
                aux[1] = enlaces[i + 1][16][0];
                aux[2] = enlaces[i][16][1];
                aux[3] = enlaces[5][17][1];
                aux[4] = enlaces[i][16][2];
                aux[5] = enlaces[i][16][3];
                aux[6] = enlaces[4][17][3];
                aux[7] = enlaces[6][17][2];
            } else if (i == 6) {
                aux[0] = enlaces[i][16][0];
                aux[1] = enlaces[i + 1][16][0];
                aux[2] = enlaces[i][16][1];
                aux[3] = null;
                aux[4] = enlaces[i][16][2];
                aux[5] = enlaces[i][16][3];
                aux[6] = enlaces[5][17][3];
                aux[7] = null;
            } else {
                // Los otros
                aux[0] = enlaces[i][16][0];
                aux[1] = enlaces[i + 1][16][0];
                aux[2] = enlaces[i][16][1];
                aux[3] = null;
                aux[4] = enlaces[i][16][2];
                aux[5] = enlaces[i][16][3];
                aux[6] = null;
                aux[7] = null;
            }

            nodos[i][16].setEnlaces(aux);

        }


        // Pared de la derecha

        /*
         * La pared de la derecha (excepto las esquinas) no tiene los tres
         * enlaces de la derecha
         */
        for (int j = 1; j < 16; j++) {
            // Vaciado del array auxiliar aux
            for (int m = 0; m < aux.length; m++) {
                aux[m] = null;
            }

            aux[0] = enlaces[10][j][0];
            aux[1] = null;
            aux[2] = enlaces[10][j][1];
            aux[3] = enlaces[10][j + 1][1];
            aux[4] = enlaces[10][j][2];
            aux[5] = null;
            aux[6] = enlaces[9][j + 1][3];
            aux[7] = null;

            nodos[10][j].setEnlaces(aux);
        }

        // Esquina inferior derecha (no tiene ni los de abajo ni los de la
        // derecha)

        // Vaciado del array auxiliar aux
        for (int m = 0; m < aux.length; m++) {
            aux[m] = null;
        }

        aux[0] = enlaces[10][16][0];
        aux[1] = null;
        aux[2] = enlaces[10][16][1];
        aux[3] = null;
        aux[4] = enlaces[10][16][2];
        aux[5] = null;
        aux[6] = null;
        aux[7] = null;

        nodos[10][16].setEnlaces(aux);

        // Esquina superior derecha (no tiene ni los de arriba ni los de la
        // derecha)

         // Vaciado del array auxiliar aux
        for (int m = 0; m < aux.length; m++) {
            aux[m] = null;
        }

        aux[0] = enlaces[10][0][0];
        aux[1] = null;
        aux[2] = null;
        aux[3] = enlaces[10][1][1];
        aux[4] = null;
        aux[5] = null;
        aux[6] = enlaces[9][1][3];
        aux[7] = null;

        nodos[10][0].setEnlaces(aux);

        // Pared de arriba

        /*
         * Excepto las esquinas, todos tienen todos menos los de arriba, excepto
         * los que están justo debajo de la portería
         */
        for (int i = 1; i < 10; i++) {
            // Vaciado del array auxiliar aux
            for (int m = 0; m < aux.length; m++) {
                aux[m] = null;
            }

            // Nodos justo debajo de la portería
            if (i == 4) {
                aux[0] = enlaces[i][0][0];
                aux[1] = enlaces[i + 1][0][0];
                aux[2] = null;
                aux[3] = enlaces[i][1][1];
                aux[4] = null;
                aux[5] = enlaces[4][0][3];
                aux[6] = enlaces[i - 1][1][3];
                aux[7] = enlaces[i + 1][1][2];
            } else if (i == 5) {
                aux[0] = enlaces[i][0][0];
                aux[1] = enlaces[i + 1][0][0];
                aux[2] = enlaces[5][0][1];
                aux[3] = enlaces[i][1][1];
                aux[4] = enlaces[5][0][2];
                aux[5] = enlaces[5][0][3];
                aux[6] = enlaces[i - 1][1][3];
                aux[7] = enlaces[i + 1][1][2];
            } else if (i == 6) {
                aux[0] = enlaces[i][0][0];
                aux[1] = enlaces[i + 1][0][0];
                aux[2] = null;
                aux[3] = enlaces[i][1][1];
                aux[4] = enlaces[6][0][2];
                aux[5] = null;
                aux[6] = enlaces[i - 1][1][3];
                aux[7] = enlaces[i + 1][1][2];
            } else {
                // Los otros
                aux[0] = enlaces[i][0][0];
                aux[1] = enlaces[i + 1][0][0];
                aux[2] = null;
                aux[3] = enlaces[i][1][1];
                aux[4] = null;
                aux[5] = null;
                aux[6] = enlaces[i - 1][1][3];
                aux[7] = enlaces[i + 1][1][2];
            }

            nodos[i][0].setEnlaces(aux);
        }

        // Pared de la izquierda

        /*
         * Excepto las esquinas, todos tienen todos menos los de la izquierda
         */
        for (int j = 1; j < 16; j++) {
            // Vaciado del array auxiliar aux
            for (int m = 0; m < aux.length; m++) {
                aux[m] = null;
            }

            aux[0] = null;
            aux[1] = enlaces[1][j][0];
            aux[2] = enlaces[0][j][1];
            aux[3] = enlaces[0][j + 1][1];
            aux[4] = null;
            aux[5] = enlaces[0][j][3];
            aux[6] = null;
            aux[7] = enlaces[1][j + 1][2];

            nodos[0][j].setEnlaces(aux);
        }

        // Esquina superior izquierda (no tiene ni los de la izquierda ni los
        // de arriba)

        // Vaciado del array auxiliar aux
        for (int m = 0; m < aux.length; m++) {
            aux[m] = null;
        }

        aux[0] = null;
        aux[1] = enlaces[1][0][0];
        aux[2] = null;
        aux[3] = enlaces[0][1][1];
        aux[4] = null;
        aux[5] = null;
        aux[6] = null;
        aux[7] = enlaces[1][1][2];

        nodos[0][0].setEnlaces(aux);


        // Esquina inferior izquierda (no tiene ni los de la izquierda ni los
        // de abajo)

        // Vaciado del array auxiliar aux
        for (int m = 0; m < aux.length; m++) {
            aux[m] = null;
        }

        aux[0] = null;
        aux[1] = enlaces[1][16][0];
        aux[2] = enlaces[0][16][1];
        aux[3] = null;
        aux[4] = null;
        aux[5] = enlaces[0][16][3];
        aux[6] = null;
        aux[7] = null;

        nodos[0][16].setEnlaces(aux);

        /*
         *
         * Fin de asignación de enlaces a cada nodo
         * 
         */
    }

    /**
     * Método get del atributo nodos
     * @return nodos
     */
    public Nodo[][] getNodos() {
        return nodos;
    }

    /**
     * Método set del atributo nodos
     * @param nuevo El nuevo array de nodos
     */
    public void setNodos(Nodo[][] nuevo) {
        System.arraycopy(nuevo, 0, nodos, 0, nuevo.length);
    }

    /**
     * Método get del atributo enlaces
     * @return enlaces
     */
    public Enlace[][][] getEnlaces() {
        return enlaces;
    }

    /**
     * Método set del atributo enlaces
     * @param nuevo El nuevo array de enlaces
     */
    public void setEnlaces(Enlace[][][] nuevo)  {
        System.arraycopy(nuevo, 0, enlaces, 0, nuevo.length);
    }

    /**
     * Método que devuelve un tablero igual a éste
     * @return tablero Una instancia igual a ésta
     */
    @Override
    public Tablero clone() {
        Tablero tablero = new Tablero();

        for (int i = 0; i < tablero.getEnlaces().length; i++) {
            for (int j = 0; j < tablero.getEnlaces()[i].length; j++) {
                for (int k = 0; k < tablero.getEnlaces()[i][j].length; k++) {
                    if (enlaces[i][j][k] != null) {
                        tablero.getEnlaces()[i][j][k] = enlaces[i][j][k].clone();
                    }
                }
            }
        }

        for (int i = 0; i < tablero.getNodos().length; i++) {
            for (int j = 0; j < tablero.getNodos()[i].length; j++) {
                tablero.getNodos()[i][j] = nodos[i][j].clone();
            }
        }

        // Ahora los enlaces de los nodos son instancias iguales a los enlaces
        // correspondientes pero no las mismas instancias, por lo que ahora
        // se asigna a cada nodo sus enlaces para que sean las mismas instancias
        /*
         *
         * Asignación de enlaces a cada nodo
         *
         */

        /*
         * Se usa el array auxiliar aux para asignar los enlaces a cada nodo
         */
        Enlace[] auxiliar = new Enlace[8];

        // Nodos interiores

        /*
         * Cada nodo interior tiene los enlaces de las posiciones 0, 2, 4 y 5
         * de su misma posición (respectivamente, 0, 1, 2 y 3 de los enlaces),
         * el 1 se corresponde con el 0 de la posición i + 1, el 3 se
         * corresponde con el 1 de la posición j + 1, el 6 se corresponde con
         * el 3 de la posición i - 1 j + 1, y el 7 se corresponde con el 2
         * de la posición i + 1 j + 1
         */
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 16; j++) {
                // Vaciado del array auxiliariliar auxiliar
                for (int m = 0; m < auxiliar.length; m++) {
                    auxiliar[m] = null;
                }

                auxiliar[0] = tablero.getEnlaces()[i][j][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][j][0];
                auxiliar[2] = tablero.getEnlaces()[i][j][1];
                auxiliar[3] = tablero.getEnlaces()[i][j + 1][1];
                auxiliar[4] = tablero.getEnlaces()[i][j][2];
                auxiliar[5] = tablero.getEnlaces()[i][j][3];
                auxiliar[6] = tablero.getEnlaces()[i - 1][j + 1][3];
                auxiliar[7] = tablero.getEnlaces()[i + 1][j + 1][2];

                tablero.getNodos()[i][j].setEnlaces(auxiliar);
            }
        }

        // Pared de abajo

        /*
         * La pared de abajo (excepto las esquinas) no tiene los tres tablero.getEnlaces()
         * inferiores excepto los tablero.getNodos() justo encima de la portería
         */
        for (int i = 1; i < 10; i++) {
            // Vaciado del array auxiliariliar auxiliar
            for (int m = 0; m < auxiliar.length; m++) {
                auxiliar[m] = null;
            }

            // Nodos justo encima de la portería
            if (i == 4) {
                auxiliar[0] = tablero.getEnlaces()[i][16][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][16][0];
                auxiliar[2] = tablero.getEnlaces()[i][16][1];
                auxiliar[3] = null;
                auxiliar[4] = tablero.getEnlaces()[i][16][2];
                auxiliar[5] = tablero.getEnlaces()[i][16][3];
                auxiliar[6] = null;
                auxiliar[7] = tablero.getEnlaces()[5][17][2];
            } else if (i == 5) {
                auxiliar[0] = tablero.getEnlaces()[i][16][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][16][0];
                auxiliar[2] = tablero.getEnlaces()[i][16][1];
                auxiliar[3] = tablero.getEnlaces()[5][17][1];
                auxiliar[4] = tablero.getEnlaces()[i][16][2];
                auxiliar[5] = tablero.getEnlaces()[i][16][3];
                auxiliar[6] = tablero.getEnlaces()[4][17][3];
                auxiliar[7] = tablero.getEnlaces()[6][17][2];
            } else if (i == 6) {
                auxiliar[0] = tablero.getEnlaces()[i][16][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][16][0];
                auxiliar[2] = tablero.getEnlaces()[i][16][1];
                auxiliar[3] = null;
                auxiliar[4] = tablero.getEnlaces()[i][16][2];
                auxiliar[5] = tablero.getEnlaces()[i][16][3];
                auxiliar[6] = tablero.getEnlaces()[5][17][3];
                auxiliar[7] = null;
            } else {
                // Los otros
                auxiliar[0] = tablero.getEnlaces()[i][16][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][16][0];
                auxiliar[2] = tablero.getEnlaces()[i][16][1];
                auxiliar[3] = null;
                auxiliar[4] = tablero.getEnlaces()[i][16][2];
                auxiliar[5] = tablero.getEnlaces()[i][16][3];
                auxiliar[6] = null;
                auxiliar[7] = null;
            }

            tablero.getNodos()[i][16].setEnlaces(auxiliar);

        }


        // Pared de la derecha

        /*
         * La pared de la derecha (excepto las esquinas) no tiene los tres
         * tablero.getEnlaces() de la derecha
         */
        for (int j = 1; j < 16; j++) {
            // Vaciado del array auxiliariliar auxiliar
            for (int m = 0; m < auxiliar.length; m++) {
                auxiliar[m] = null;
            }

            auxiliar[0] = tablero.getEnlaces()[10][j][0];
            auxiliar[1] = null;
            auxiliar[2] = tablero.getEnlaces()[10][j][1];
            auxiliar[3] = tablero.getEnlaces()[10][j + 1][1];
            auxiliar[4] = tablero.getEnlaces()[10][j][2];
            auxiliar[5] = null;
            auxiliar[6] = tablero.getEnlaces()[9][j + 1][3];
            auxiliar[7] = null;

            tablero.getNodos()[10][j].setEnlaces(auxiliar);
        }

        // Esquina inferior derecha (no tiene ni los de abajo ni los de la
        // derecha)

        // Vaciado del array auxiliariliar auxiliar
        for (int m = 0; m < auxiliar.length; m++) {
            auxiliar[m] = null;
        }

        auxiliar[0] = tablero.getEnlaces()[10][16][0];
        auxiliar[1] = null;
        auxiliar[2] = tablero.getEnlaces()[10][16][1];
        auxiliar[3] = null;
        auxiliar[4] = tablero.getEnlaces()[10][16][2];
        auxiliar[5] = null;
        auxiliar[6] = null;
        auxiliar[7] = null;

        tablero.getNodos()[10][16].setEnlaces(auxiliar);

        // Esquina superior derecha (no tiene ni los de arriba ni los de la
        // derecha)

         // Vaciado del array auxiliariliar auxiliar
        for (int m = 0; m < auxiliar.length; m++) {
            auxiliar[m] = null;
        }

        auxiliar[0] = tablero.getEnlaces()[10][0][0];
        auxiliar[1] = null;
        auxiliar[2] = null;
        auxiliar[3] = tablero.getEnlaces()[10][1][1];
        auxiliar[4] = null;
        auxiliar[5] = null;
        auxiliar[6] = tablero.getEnlaces()[9][1][3];
        auxiliar[7] = null;

        tablero.getNodos()[10][0].setEnlaces(auxiliar);

        // Pared de arriba

        /*
         * Excepto las esquinas, todos tienen todos menos los de arriba, excepto
         * los que están justo debajo de la portería
         */
        for (int i = 1; i < 10; i++) {
            // Vaciado del array auxiliariliar auxiliar
            for (int m = 0; m < auxiliar.length; m++) {
                auxiliar[m] = null;
            }

            // Nodos justo debajo de la portería
            if (i == 4) {
                auxiliar[0] = tablero.getEnlaces()[i][0][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][0][0];
                auxiliar[2] = null;
                auxiliar[3] = tablero.getEnlaces()[i][1][1];
                auxiliar[4] = null;
                auxiliar[5] = tablero.getEnlaces()[4][0][3];
                auxiliar[6] = tablero.getEnlaces()[i - 1][1][3];
                auxiliar[7] = tablero.getEnlaces()[i + 1][1][2];
            } else if (i == 5) {
                auxiliar[0] = tablero.getEnlaces()[i][0][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][0][0];
                auxiliar[2] = tablero.getEnlaces()[5][0][1];
                auxiliar[3] = tablero.getEnlaces()[i][1][1];
                auxiliar[4] = tablero.getEnlaces()[5][0][2];
                auxiliar[5] = tablero.getEnlaces()[5][0][3];
                auxiliar[6] = tablero.getEnlaces()[i - 1][1][3];
                auxiliar[7] = tablero.getEnlaces()[i + 1][1][2];
            } else if (i == 6) {
                auxiliar[0] = tablero.getEnlaces()[i][0][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][0][0];
                auxiliar[2] = null;
                auxiliar[3] = tablero.getEnlaces()[i][1][1];
                auxiliar[4] = tablero.getEnlaces()[6][0][2];
                auxiliar[5] = null;
                auxiliar[6] = tablero.getEnlaces()[i - 1][1][3];
                auxiliar[7] = tablero.getEnlaces()[i + 1][1][2];
            } else {
                // Los otros
                auxiliar[0] = tablero.getEnlaces()[i][0][0];
                auxiliar[1] = tablero.getEnlaces()[i + 1][0][0];
                auxiliar[2] = null;
                auxiliar[3] = tablero.getEnlaces()[i][1][1];
                auxiliar[4] = null;
                auxiliar[5] = null;
                auxiliar[6] = tablero.getEnlaces()[i - 1][1][3];
                auxiliar[7] = tablero.getEnlaces()[i + 1][1][2];
            }

            tablero.getNodos()[i][0].setEnlaces(auxiliar);
        }

        // Pared de la izquierda

        /*
         * Excepto las esquinas, todos tienen todos menos los de la izquierda
         */
        for (int j = 1; j < 16; j++) {
            // Vaciado del array auxiliariliar auxiliar
            for (int m = 0; m < auxiliar.length; m++) {
                auxiliar[m] = null;
            }

            auxiliar[0] = null;
            auxiliar[1] = tablero.getEnlaces()[1][j][0];
            auxiliar[2] = tablero.getEnlaces()[0][j][1];
            auxiliar[3] = tablero.getEnlaces()[0][j + 1][1];
            auxiliar[4] = null;
            auxiliar[5] = tablero.getEnlaces()[0][j][3];
            auxiliar[6] = null;
            auxiliar[7] = tablero.getEnlaces()[1][j + 1][2];

            tablero.getNodos()[0][j].setEnlaces(auxiliar);
        }

        // Esquina superior izquierda (no tiene ni los de la izquierda ni los
        // de arriba)

        // Vaciado del array auxiliariliar auxiliar
        for (int m = 0; m < auxiliar.length; m++) {
            auxiliar[m] = null;
        }

        auxiliar[0] = null;
        auxiliar[1] = tablero.getEnlaces()[1][0][0];
        auxiliar[2] = null;
        auxiliar[3] = tablero.getEnlaces()[0][1][1];
        auxiliar[4] = null;
        auxiliar[5] = null;
        auxiliar[6] = null;
        auxiliar[7] = tablero.getEnlaces()[1][1][2];

        tablero.getNodos()[0][0].setEnlaces(auxiliar);


        // Esquina inferior izquierda (no tiene ni los de la izquierda ni los
        // de abajo)

        // Vaciado del array auxiliariliar auxiliar
        for (int m = 0; m < auxiliar.length; m++) {
            auxiliar[m] = null;
        }

        auxiliar[0] = null;
        auxiliar[1] = tablero.getEnlaces()[1][16][0];
        auxiliar[2] = tablero.getEnlaces()[0][16][1];
        auxiliar[3] = null;
        auxiliar[4] = null;
        auxiliar[5] = tablero.getEnlaces()[0][16][3];
        auxiliar[6] = null;
        auxiliar[7] = null;

        tablero.getNodos()[0][16].setEnlaces(auxiliar);

        return tablero;
    }
}
