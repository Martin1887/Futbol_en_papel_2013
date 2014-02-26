package futbolEnPapel2013.jugador.controladores;

import futbolEnPapel2013.estructura.*;
import java.util.ArrayList;

/**
 * Clase que representa un grafo que contiene sólo los enlaces válidos con su
 * mínimo coste en turnos (sería más efectivo tener el coste real para un
 * determinado camino, pero es mucho más costoso)
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Grafo {

    /**
     * Grafo de la clase. Es de la clase tablero porque tiene la misma estructura
     */
    private Tablero grafo;
    
    /**
     * Constructor completo.
     * Primero se clona el tablero que es pasa por parámetro y luego se procede
     * a eliminar (poner a null) los enlaces marcados y luego se cambia el coste
     * de los enlaces que pueden producir rebotes
     * @param tablero
     */
    public Grafo(Tablero tablero) throws java.lang.CloneNotSupportedException {

        grafo = tablero.clone();

        // No se ponen bien los enlaces a null y además ya no hace falta
/*
        for (int i = 0; i < grafo.getNodos().length; i++) {
            for (int j = 0; j < grafo.getNodos()[i].length; j++) {
                for (int m = 0; m < grafo.getNodos()[i][j].getEnlaces().length; m++) {
                    if (grafo.getNodos()[i][j].getEnlaces()[m] == null
                            || grafo.getNodos()[i][j].getEnlaces()[m].getMarcado() == true) {
                        // Se ponen a null los enlaces marcados
                        Enlace[] enlaces = grafo.getNodos()[i][j].getEnlaces();
                        enlaces[m] = null;
                        grafo.getNodos()[i][j].setEnlaces(enlaces);
                    }
                }
            }
        }
*/

        calcularCostes();


        // Ahora se asigna el coste en turnos a cada enlace
//        for (int i = 0; i < grafo.getNodos().length; i++) {
//            for (int j = 0; j < grafo.getNodos()[i].length; j++) {
//                for (int m = 0; m < grafo.getNodos()[i][j].getEnlaces().length; m++) {
//                    if (grafo.getNodos()[i][j].getEnlaces()[m] != null
//                            && !grafo.getNodos()[i][j].getEnlaces()[m].getMarcado()) {
//                        int rebotes = 0;
//                        /*
//                         * Si ambos extremos del enlace están marcados hay rebote,
//                         * por lo que se incrementa en 1 la variable local rebotes
//                         */
//                        // Variables para identificar al otro extremo del enlace
//                        int x = i, y = j;
//                        // Enlace del otro extremo que lo conecta con éste
//                        int l = 0;
//                        // Se da valor a estas variables viendo el enlace que es
//                        switch (m) {
//                            case 0:
//                                // izquierda
//                                x = i - 1;
//                                y = j;
//                                l = 1;
//                                break;
//                            case 1:
//                                // derecha
//                                x = i + 1;
//                                y = j;
//                                l = 0;
//                                break;
//                            case 2:
//                                // arriba
//                                x = i;
//                                y = j - 1;
//                                l = 3;
//                                break;
//                            case 3:
//                                // abajo
//                                x = i;
//                                y = j + 1;
//                                l = 2;
//                                break;
//                            case 4:
//                                // arriba-izquierda
//                                x = i - 1;
//                                y = j - 1;
//                                l = 7;
//                                break;
//                            case 5:
//                                // arriba-derecha
//                                x = i + 1;
//                                y = j - 1;
//                                l = 6;
//                                break;
//                            case 6:
//                                // abajo-izquierda
//                                x = i - 1;
//                                y = j + 1;
//                                l = 5;
//                                break;
//                            case 7:
//                                // abajo-derecha
//                                x = i + 1;
//                                y = j + 1;
//                                l = 4;
//                                break;
//                        }
//                        // Ahora es cuando se comprueba si ambos están marcados
//                        if (j > -1 && j < 17 && y > -1 && y < 17
//                                && grafo.getNodos()[i][j] != null
//                                && grafo.getNodos()[x][y] != null
//                                && grafo.getNodos()[i][j].getMarcado()
//                                && grafo.getNodos()[x][y].getMarcado()) {
//                            rebotes++;
//
//                            int[] todosRebotes = new int[8];
//
//                            // Array con las listas de los caminos con rebotes
//                            LinkedList[] enlacesCamino = new LinkedList[8];
//
//
//                            /*
//                             * Hay rebote, se pasa a comprobar si hay más rebotes
//                             * (cualquiera de los nodos adyacentes cuyo enlace
//                             * a este existe está marcado)
//                             */
//                            for (int m = 0; m < grafo.getNodos()[x][y].getEnlaces().length; m++) {
//                                // Si no es el enlace cuyo rebote ya se ha contado
//                                if (m != l) {
//                                    // Si el enlace existe en el grafo
//                                    if (grafo.getNodos()[x][y].getEnlaces()[m] != null
//                                            && !grafo.getNodos()[x][y].getEnlaces()[m].getMarcado()) {
//                                        enlacesCamino[m] = new LinkedList<Enlace>();
//                                        // Se calculan los rebotes de esta alternativa
//                                        // La lista se pasa por referencia, por lo que es modificada
//                                        todosRebotes[m] = calcularRebotes(x, y, m, rebotes, enlacesCamino[m]);
//                                    }
//                                }
//                            }
//                            // El número de rebotes es el máximo de todas las alternativas
//                            int masLargo = todosRebotes[0];
//                            for (int n = 0; n < todosRebotes.length; n++) {
//                                if (todosRebotes[n] > masLargo) {
//                                    masLargo = todosRebotes[n];
//                                }
//                            }
//                            rebotes = todosRebotes[masLargo];
//
//                            // Si no hay rebotes coste 2, si hay un rebote coste
//                            // 1 y si hay más de uno coste entre 0 y 1
//                            double coste = 2;
//
//                            if (rebotes > 0) {
//                                coste = 1 / rebotes;
//                            }
//
//                            // Por último, se asigna el coste a todos los enlaces del camino
//                            // con más rebotes siempre que éste sea menor que el que ya tienen
//                            if (rebotes > 0) {
//                                for (int n = 0; n < enlacesCamino[masLargo].size(); n++) {
//                                    Enlace enlace = (Enlace) enlacesCamino[masLargo].get(n);
//                                    if (enlace.getCoste() > coste) {
//                                        enlace.setCoste(coste);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    /**
     * Método get del atributo grafo
     * @return El atributo grafo
     */
    public Tablero getGrafo() {
        return grafo;
    }

    /**
     * Método set del atributo grafo
     * @param tablero El tablero que reemplaza al grafo
     */
    public void setGrafo(Tablero tablero) {
        grafo = tablero;
    }

    /**
     * Método que devuelve true si las posiciones pasadas por parámetro son
     * adyacentes
     * @param ini
     * @param fin
     * @return true si las posiciones son adyacentes y false alias
     */
    public static boolean adyacentes(Posicion ini, Posicion fin) {
        boolean adyacentes = false;

        if ((ini.getX() == fin.getX() && Math.abs(ini.getY() - fin.getY()) == 1)
                || (ini.getY() == fin.getY() && Math.abs(ini.getX() - fin.getX()) == 1)
                || (Math.abs(ini.getX() - fin.getX()) == 1 && Math.abs(ini.getY() - fin.getY()) == 1)) {
            adyacentes = true;
        }

        return adyacentes;
    }

    /**
     * Método que calcula los costes de los enlaces
     */
    public void calcularCostes() {
        /*
         * Ahora se asigna el coste en turnos a cada enlace (1 / (1D + long. camino más largo))
         * Para ello se recorren todos los nodos del grafo y se almacenan aquellos
         * que están marcados. A continuación se compara
         * la distancia entre todos los nodos marcados par a par y si es 1 y el
         * enlace que los conecta no es nulo ni está marcado se marcan como
         * conectados. Por último, cuando ya se
         * han comparado todos los nodos, se calcula la long. del camino más largo
         * de entre cada conjunto de enlaces conectados, asignando a sus
         * enlaces correspondientes el coste 1 / (1D + este valor)
         */
        ArrayList<Nodo> marcados = new ArrayList<Nodo>();
        // Posiciones de los nodos de la anterior lista
        ArrayList<Posicion> posMarcados = new ArrayList<Posicion>();
        // Índices de los nodos de las listas anteriores conectados
        ArrayList<Posicion[]> conectados = new ArrayList<Posicion[]>();
        // Rebotes de los enlaces de los nodos de marcados
//        ArrayList<int[]> rebotes = new ArrayList<int[]>();
        for (int i = 0; i < grafo.getNodos().length; i++) {
            for (int j = 0; j < grafo.getNodos()[i].length; j++) {
                Nodo nodo = grafo.getNodos()[i][j];
                if(nodo != null && nodo.getMarcado()) {
                    marcados.add(nodo);
                    posMarcados.add(new Posicion(i, j));
//                    int[] reb = new int[nodo.getEnlaces().length];
//                    for (int m = 0; m < nodo.getEnlaces().length; m++) {
//                        if (nodo.getEnlaces()[m] != null
//                                && !nodo.getEnlaces()[m].getMarcado()) {
//                            reb[m] = 1;
//                        }
//                    }
//                    rebotes.add(reb.clone());
                }
            }
        }

        for (int i = 0; i < marcados.size(); i++) {
            Posicion[] adyacentes = obtenerAdyacentes(posMarcados.get(i));
            for (int j = 0; j < adyacentes.length; j++) {
                // Se buscan los nodos adyacentes no necesariamente marcados
                // conectados por enlaces no marcados
                Posicion adyacente = adyacentes[j];
                Enlace conector = obtenerEnlace(posMarcados.get(i), adyacente);
                if (adyacente != null && conector != null && !conector.getMarcado()) {
                    // Se incrementa en 1 el número de rebotes de todos los
                    // enlaces no nulos ni marcados
//                    Nodo nodo = grafo.getNodos()[posMarcados.get(i).getX()][posMarcados.get(i).getY()];
//                    int[] reb = rebotes.get(i);
//                    for (int m = 0; m < nodo.getEnlaces().length; m++) {
//                        if (nodo.getEnlaces()[m] != null
//                                && !nodo.getEnlaces()[m].getMarcado()
//                                && nodo.getEnlaces()[m].equals
//                                (obtenerEnlace
//                                (this, posMarcados.get(i), posMarcados.get(j)))) {
//                            reb[m] += 1;
//                        }
//                    }
//                    rebotes.set(i, reb);

//                    nodo = grafo.getNodos()[posMarcados.get(j).getX()][posMarcados.get(j).getY()];
//                    int[] reb2 = rebotes.get(j);
//                    for (int m = 0; m < nodo.getEnlaces().length; m++) {
//                        if (nodo.getEnlaces()[m] != null && !nodo.getEnlaces()[m].getMarcado()) {
//                            reb2[m] += 1;
//                        }
//                    }
//                    rebotes.set(j, reb2);


                    // Se buscan i y j en conectados para meterlos en la misma posición
                    // (siempre que no estén ya los dos en esa posición)
                    if (!conectados.isEmpty()) {
                        boolean encontrado = false;
                        boolean encontradoI = false;
                        boolean encontradoJ = false;
                        for (int m = 0; m < conectados.size() && !encontrado; m++) {
                            encontradoI = false;
                            encontradoJ = false;
                            Posicion[] conecM = conectados.get(m);
                            // Se mira si no están ya los dos nodos en la entrada de conectado
                            for (int n = 0; n < conecM.length && !(encontradoI && encontradoJ); n++) {
                                if (conecM[n].equals(posMarcados.get(i))) {
                                    encontradoI = true;
                                }
                                if (conecM[n].equals(adyacente)) {
                                    encontradoJ = true;
                                }
                            }
                            if (encontradoI && encontradoJ) {
                                encontrado = true;
                            }
                            for (int n = 0; n < conecM.length && !encontrado; n++) {
                                if (conecM[n].equals(posMarcados.get(i))) {
                                    // Se crea un array igual a conecM añadiendo j
                                    Posicion[] con = new Posicion[conecM.length + 1];
                                    System.arraycopy(conecM, 0, con, 0, conecM.length);
                                    con[con.length - 1] = adyacente;
                                    conectados.set(m, con.clone());
                                    encontrado = true;
                                } else if (conecM[n].equals(adyacente)
                                        // Sólo se debe meter en el mismo elemento
                                        // de conectados que adyacente si adyacente
                                        // está marcado
                                        && grafo.getNodos()[adyacente.getX()]
                                        [adyacente.getY()].getMarcado()) {
                                    // Se crea un array igual a conecM añadiendo i
                                    Posicion[] con = new Posicion[conecM.length + 1];
                                    System.arraycopy(conecM, 0, con, 0, conecM.length);
                                    con[con.length - 1] = posMarcados.get(i);
                                    conectados.set(m, con.clone());
                                    encontrado = true;
                                }
                            }
                        }

                        // Si ninguno de los índices se encuentra en conectados
                        // se añade una nueva entrada
                        if (!encontrado) {
                            Posicion[] con = new Posicion[2];
                            con[0] = posMarcados.get(i);
                            con[1] = adyacente;
                            conectados.add(con.clone());
                        }
                    } else {
                        Posicion[] con = new Posicion[2];
                        con[0] = posMarcados.get(i);
                        con[1] = adyacente;
                        conectados.add(con.clone());
                    }
                }
            }
        }
        

        // Por fin se busca en cada elemento de conectados el máximo número de
        // rebotes y se asigna a todos los enlaces no nulos ni marcados de
        // cada elemento el coste 1 / (1 + este valor)
        for (int i = 0; i < conectados.size(); i++) {
            // Se busca el máximo número de rebotes (al menos es 1 porque
            // para que esté conectado tiene que estar marcado)
//            int max = 1;

            Posicion[] conec = conectados.get(i);

            // Se asigna el coste a cada enlace de cada nodo de conec
            for (int j = 0; j < conec.length; j++) {
                // Se buscan los nodos adyacentes a este para encontrar la cadena
                // de enlaces más larga y asignar al enlace el coste
                // 1D / (1D + enlaces_camino_más_largo)
                Posicion pos = conec[j];
                for (int k = 0; k < conec.length; k++) {
                    if (k != j && adyacentes(pos, conec[k])) {
                        // Se busca el enlace que los une
                        Enlace conector = obtenerEnlace(pos, conec[k]);
                        if (conector != null && !conector.getMarcado()) {
                            if (grafo.getNodos()[conec[k].getX()][conec[k].getY()].getMarcado()) {
                                ArrayList<Posicion> anteriores = new ArrayList<Posicion>();
                                anteriores.add(pos);
                                double coste = 1D / (1D + longCaminoMasLargo
                                        (conec[k], anteriores, conec, 1));
//                            if (conector.getCoste() > coste) {
                                    conector.setCoste(obtenerIndiceCoste(pos, conec[k]), coste);
//                            }
                            } else {
                                conector.setCoste(obtenerIndiceCoste(pos, conec[k]), 1);
                            }
                        }
                    }
                }
            }


            // Para cada nodo de conectados se busca el nodo conectado más alejado,
            // para asignar al enlace que lo conecta con esa cadena de nodos el
            // coste 1D / (1D + nº nodos)

//            for (int j = 0; j < conec.length; j++) {
//                for (int m = 0; m < rebotes.get(conec[j]).length; m++) {
//                    if (rebotes.get(conec[j])[m] > max) {
//                        max = rebotes.get(conec[j])[m];
//                    }
//                }
//            }

            // Ahora que se tiene el máximo se buscan todos los enlaces no nulos
            // ni marcados y se les asigna el coste 1 / (max + 1)
//            for (int j = 0; j < conectados.get(i).length; j++) {
//                Nodo nodo = marcados.get(conectados.get(i)[j]);
//                Posicion pos = posMarcados.get(conectados.get(i)[j]);
//                for (int m = 0; m < nodo.getEnlaces().length; m++) {
//                    for (int n = 0; n < conectados.get(i).length; n++) {
//                        if (n != j) {
//                            Posicion otro = posMarcados.get(conectados.get(i)[n]);
//                            if (nodo.getEnlaces()[m] != null
//                                    && !nodo.getEnlaces()[m].getMarcado()
//                                    && nodo.getEnlaces()[m].equals
//                                    (obtenerEnlace(this, pos, otro))) {
//                                nodo.getEnlaces()[m].setCoste(1D / (1D + max));
//                            }
//                        }
//                    }
//                }
//            }
        }
    }

    /**
     * Método recursivo que devuelve el número de enlaces del camino más largo
     * @param pos Posición de la que se quiere saber el camino más largo
     * @param anteriores Posiciones anteriores
     * @param conec posiciones conectadas
     * @param actual longitud en la actual llamada
     * @return número de enlaces del camino más largo
     */
    public int longCaminoMasLargo(Posicion pos, ArrayList<Posicion> anteriores,  Posicion[] conec,
            int actual) {

        boolean algunoAdyacente = false;
        ArrayList<Posicion> adyacentes = new ArrayList<Posicion>();
        for (int i = 0; i < conec.length; i++) {
            Posicion siguiente = conec[i];
            Enlace conector = obtenerEnlace(pos, siguiente);
            if (!siguiente.equals(pos) && !anteriores.contains(siguiente)
                    && conector != null && !conector.getMarcado()
                    && grafo.getNodos()[siguiente.getX()][siguiente.getY()].getMarcado()) {
                algunoAdyacente = true;
                adyacentes.add(conec[i]);
            }
        }
        // Caso base: no hay ningún nodo adyacente al actual que no sea el anterior
        if (!algunoAdyacente) {
            return actual;
        } else {
            // Caso recursivo: se devuelve el máximo de las posibles llamadas
            int max = 0;

            for (int i = 0; i < adyacentes.size(); i++) {
                anteriores.add(pos);
                int aux = longCaminoMasLargo(adyacentes.get(i), anteriores, conec, actual + 1);
                if (aux > max) {
                    max = aux;
                }
            }

            return max;
        }
    }

    /**
     * Método que devuelve las posiciones adyacentes (incluyendo las diagonales)
     * @param pos
     * @return Un Posicion[] con las posiciones adyacentes (incluyendo diagonales)
     */
    public static Posicion[] obtenerAdyacentes(Posicion pos) {
        Posicion[] adyacentes = new Posicion[8];

        int x = pos.getX();
        int y = pos.getY();

        int indice = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    if ((x + i) >= 0 && (x + i) < 11
                            && (y + j) >= 0 && (y + j) < 17) {
                        adyacentes[indice] = new Posicion(x + i, y + j);
                        indice++;
                    }
                }
            }
        }

        return adyacentes;
    }


    /**
     * Método que devuelve el enlace entre dos nodos
     * @param ant Primer nodo
     * @param sig Segundo nodo
     * @return El enlace que conecta ambos nodos o null si no son adyacentes
     */
    public Enlace obtenerEnlace(Posicion ant, Posicion sig) {
        Enlace conector = null;

        if (ant == null || sig == null) {
            return null;
        }

        // Se comprueban todas las posibilidades entre nodos adyacentes
        if (sig.getX() == ant.getX() - 1 && sig.getY() == ant.getY()) {
            // Izquierda
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[0];
        } else if (sig.getX() == ant.getX() + 1 && sig.getY() == ant.getY()) {
            // Derecha
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[1];
        } else if (sig.getX() == ant.getX() && sig.getY() == ant.getY() - 1) {
            // Arriba
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[2];
        } else if (sig.getX() == ant.getX() && sig.getY() == ant.getY() + 1) {
            // Abajo
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[3];
        } else if (sig.getX() == ant.getX() - 1 && sig.getY() == ant.getY() - 1) {
            // Arriba-Izquierda
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[4];
        } else if (sig.getX() == ant.getX() + 1 && sig.getY() == ant.getY() - 1) {
            // Arriba-Derecha
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[5];
        } else if (sig.getX() == ant.getX() - 1 && sig.getY() == ant.getY() + 1) {
            // Abajo-Izquierda
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[6];
        } else if (sig.getX() == ant.getX() + 1 && sig.getY() == ant.getY() + 1) {
            // Abajo-Derecha
            conector = grafo.getNodos()[ant.getX()][ant.getY()].getEnlaces()[7];
        }

        return conector;
    }

    /**
     * Método que devuelve la posición nodo al que conecta un enlace
     * @param nodo Posición del nodo
     * @param enlace Número que representa al enlace en el array del nodo
     * @return La posición del nodo conectado
     */
    public static Posicion obtenerPosNodo(Posicion nodo, int enlace) {
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
     * Método que devuelve el índice del coste del enlace que une ambas posiciones
     * (es decir, el extremo del enlace correspondiente)
     * @param ini
     * @param fin
     * @return El índice del coste del enlace que une ambas posiciones
     */
    public int obtenerIndiceCoste(Posicion ini, Posicion fin) {
        int indice = 1;

        int difX = fin.getX() - ini.getX();
        int difY = fin.getY() - ini.getY();

        if ((difX == 0 && difY == 1) || (difX == 1)) {
            indice = 0;
        }

        return indice;
    }


//    /**
//     * Método que calcula cuántos rebotes hay en profundidad (sin contar los que
//     * proceden de un mismo nodo)
//     * @param x La primera dimensión del nodo
//     * @param y La segunda dimensión del nodo
//     * @param m El enlace
//     * @param reb El número de rebotes hasta esta invocación
//     * @return rebotes El número de rebotes desde el nodo al extremo del enlace
//     */
//    private int calcularRebotes(int x, int y, int m, int reb, LinkedList<Enlace> enlaces) {
//        int rebotes = reb;
//
//        // Índices del nodo que está en el otro extremo del enlace
//        int i = x, j = y, opuesto = 0;
//
//        // Se calcula cuál es el extremo del enlace y su opuesto
//        switch (m) {
//            case 0:
//                // Izquierda
//                i = x - 1;
//                j = y;
//                opuesto = 1;
//                break;
//            case 1:
//                // Derecha
//                i = x + 1;
//                j = y;
//                opuesto = 0;
//                break;
//            case 2:
//                // Arriba
//                i = x;
//                j = y - 1;
//                opuesto = 3;
//                break;
//            case 3:
//                // Abajo
//                i = x;
//                j = y + 1;
//                opuesto = 2;
//                break;
//            case 4:
//                // Arriba-izquierda
//                i = x - 1;
//                j = y - 1;
//                opuesto = 7;
//                break;
//            case 5:
//                // Arriba-derecha
//                i = x + 1;
//                j = y - 1;
//                opuesto = 6;
//                break;
//            case 6:
//                // Abajo-izquierda
//                i = x - 1;
//                j = y + 1;
//                opuesto = 5;
//                break;
//            case 7:
//                // Abajo-derecha
//                i = x + 1;
//                j = y + 1;
//                opuesto = 4;
//                break;
//        }
//
//        // Si el extremo del enlace está marcado se aumenta en 1 rebotes y se
//        // comprueba si hay más rebotes
//        if (grafo.getNodos()[i][j]!= null
//                && grafo.getNodos()[i][j].getMarcado()) {
//            rebotes++;
//
//            int[] todosRebotes = new int[8];
//
//            // Array con las listas de los caminos con rebotes
//            LinkedList[] enlacesCamino = new LinkedList[8];
//
//
//            /*
//             * Hay rebote, se pasa a comprobar si hay más rebotes
//             * (cualquiera de los nodos adyacentes cuyo enlace
//             * a este existe está marcado)
//             */
//            for (int l = 0; l < grafo.getNodos()[i][j].getEnlaces().length; l++) {
//                // Si no es el enlace cuyo rebote ya se ha contado
//                if (l != opuesto) {
//                    // Si el enlace existe en el grafo
//                    if (grafo.getNodos()[i][j].getEnlaces()[l] != null
//                            && !grafo.getNodos()[i][j].getEnlaces()[l].getMarcado()) {
//                        enlacesCamino[l] = new LinkedList<Enlace>();
//                        // Se calculan los rebotes de esta alternativa
//                        // La lista se pasa por referencia, por lo que es modificada
//                        todosRebotes[l] = calcularRebotes(i, j, l, rebotes, enlacesCamino[l]);
//                    }
//                }
//            }
//            // El número de rebotes es el máximo de todas las alternativas
//            int masLargo = todosRebotes[0];
//            for (int n = 0; n < todosRebotes.length; n++) {
//                if (todosRebotes[n] > masLargo) {
//                    masLargo = todosRebotes[n];
//                }
//            }
//
//            // La lista es la lista del camino más largo
//            enlaces = enlacesCamino[masLargo];
//        }
//
//        return rebotes;
//    }
}
