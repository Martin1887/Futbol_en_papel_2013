package futbolEnPapel2013.jugador.controladores.hormigas;

import futbolEnPapel2013.jugador.controladores.Grafo;
import futbolEnPapel2013.estructura.*;
import java.util.ArrayList;

/**
 * Clase que representa una hormiga usando los costes mínimos en lugar de los
 * reales
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class HormigaCostesMin {

    /**
     * Grafo del juego en el que se mueve la hormiga
     */
    private Grafo grafo;

    /**
     * Nodo en el que se encuentra el hormiguero en el grafo
     */
    private Posicion hormiguero;

    /**
     * Lista de las posiciones por las que ha pasado hasta llegar a la portería
     * contraria
     */
    private ArrayList<Posicion> camino;

    /**
     * Variable que almacena el coste de todo el camino
     */
    private double costeTotal;

    /**
     * Lista tabú de la hormiga (posiciones que ha comprobado que no son buenas)
     */
    private ArrayList<Posicion> tabu;

    /**
     * Nodo meta
     */
    private Posicion meta;


    /**
     * Constructor completo
     * @param gr El grafo donde se mueven lsa hormigas
     * @param base Posición del hormiguero
     * @param porteria La posición de la portería rival
     */
    public HormigaCostesMin(Grafo gr, Posicion base, Posicion porteria) {
        grafo = gr;
        hormiguero = base;
        meta = porteria;
        camino = new ArrayList<Posicion>();
        tabu = new ArrayList<Posicion>();
    }


    /**
     * Método get del atributo grafo
     * @return grafo
     */
    public Grafo getGrafo() {
        return grafo;
    }

    /**
     * Método set del atributo grafo
     * @param g El nuevo grafo
     */
    public void setGrafo(Grafo g) {
        grafo = g;
    }

    /**
     * Método get del atributo hormiguero
     * @return hormiguero
     */
    public Posicion getHormiguero() {
        return hormiguero;
    }

    /**
     * Método set del atributo hormiguero
     * @param nuevo El nuevo hormiguero
     */
    public void setHormiguero(Posicion nuevo) {
        hormiguero = nuevo;
    }

    /**
     * Método get del atributo camino
     * @return camino
     */
    public ArrayList<Posicion> getCamino() {
        return camino;
    }

    /**
     * Método set del atributo camino
     * @param nuevo El nuevo camino
     */
    public void setCamino(ArrayList<Posicion> nuevo) {
        camino = nuevo;
    }

    /**
     * Método get del atributo costeTotal
     * @return costeTotal
     */
    public double getCosteTotal() {
        return costeTotal;
    }

    /**
     * Método get del atributo tabu
     * @return tabu
     */
    public ArrayList<Posicion> getTabu() {
        return tabu;
    }

    /**
     * Método set del atributo tabu
     * @param nueva La nueva lista tabú
     */
    public void setTabu(ArrayList<Posicion> nueva) {
        tabu = nueva;
    }

    /**
     * Método get del atributo meta
     * @return meta
     */
    public Posicion getMeta() {
        return meta;
    }

    /**
     * Método set del atributo meta
     * @param nueva
     */
    public void setMeta(Posicion nueva) {
        meta = nueva;
    }
    
    /**
     * Método que actualiza la tabla de decisión de todos los nodos del grafo
     * @param g El grafo en el que se mueven las hormigas
     */
    public static void actualizaTablasDec(Grafo g) {
        /*
         * Alfa y beta. Con estos valores se favorece un poco la feromona
         */
        double alfa = 1.15, beta = 0.87;

        // Se recorren todos los nodos del grafo
        for (int i = 0; i < g.getGrafo().getNodos().length; i++) {
            for (int j = 0; j < g.getGrafo().getNodos()[i].length; j++) {
                // Se calcula el sumatorio de fer^alfa * (1 / coste)^beta
                // (aquí y después fer = feromona + ferNueva)
                double sum = 0;
                // tabla de decisión
                double[] tabla = new double[8];
                for (int k = 0; k < g.getGrafo().getNodos()[i][j].getEnlaces().length; k++) {
                    if (g.getGrafo().getNodos()[i][j].getEnlaces()[k] != null
                            && !g.getGrafo().getNodos()[i][j].getEnlaces()[k].getMarcado()) {
                        sum += Math.pow(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getFeromona()
                            + g.getGrafo().getNodos()[i][j].getEnlaces()[k].getFerNueva()
                            , alfa)
//                            * Math.pow(g.getGrafo().getNodos()[i][j].getEnlaces()[k]
//                            .getCoste(g.obtenerIndiceCoste(new Posicion(i, j),
//                            Grafo.obtenerPosNodo(new Posicion(i, j), k)))
                            * Math.pow(Math.min(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(0),
                            g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(1))
                            , -beta);
                    }
                }
                // Si sum es 0 o negativo (todos los enlaces tienen fermona 0) se hace sólo con los enlaces
                double sum2 = 0;
                if (sum == 0) {
                    for (int k = 0; k < g.getGrafo().getNodos()[i][j].getEnlaces().length; k++) {
                        if (g.getGrafo().getNodos()[i][j].getEnlaces()[k] != null
                                && !g.getGrafo().getNodos()[i][j].getEnlaces()[k].getMarcado()) {
//                            sum2 += Math.pow(g.getGrafo().getNodos()[i][j]
//                                    .getEnlaces()[k].getCoste(g.obtenerIndiceCoste(new Posicion(i, j),
//                                    Grafo.obtenerPosNodo(new Posicion(i, j), k)))
                              sum2 += Math.pow(Math.min(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(0),
                                    g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(1))
                                    , -beta);
                        }
                    }
                }
                // Se recorre la tabla de decisión del nodo
                for (int k = 0; k < g.getGrafo().getNodos()[i][j].getTablaDec().length; k++) {
                    // El valor del elemento de la tabla es fer^alfa * (1/coste)^beta /
                    //                                      sum("")
                    if (g.getGrafo().getNodos()[i][j].getEnlaces()[k] != null
                            && !g.getGrafo().getNodos()[i][j].getEnlaces()[k].getMarcado()) {
                        if (sum > 0) {  // Necesario porque la cantidad de fermona inicial es 0
                            tabla[k] = (Math.pow(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getFeromona()
                                + g.getGrafo().getNodos()[i][j].getEnlaces()[k].getFerNueva()
                                , alfa)
//                                * Math.pow(g.getGrafo().getNodos()[i][j].getEnlaces()[k]
//                                .getCoste(g.obtenerIndiceCoste(new Posicion(i, j),
//                                Grafo.obtenerPosNodo(new Posicion(i, j), k)))
                                * Math.pow(Math.min(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(0),
                                g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(1))
                                , -beta)) / sum;
                        } else {
                            tabla[k] =
//                                    Math.pow(g.getGrafo().getNodos()[i][j]
//                                    .getEnlaces()[k].getCoste(g.obtenerIndiceCoste
//                                    (new Posicion(i, j),
//                                    Grafo.obtenerPosNodo(new Posicion(i, j), k)))
                                    Math.pow(Math.min(g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(0),
                                    g.getGrafo().getNodos()[i][j].getEnlaces()[k].getCoste(1))
                                    , -beta) / sum2;
                        }
                    } else {
                        tabla[k] = 0;
                    }
                }
                // Se asigna la tabla calculada al nodo
                g.getGrafo().getNodos()[i][j].setTablaDec(tabla);
            }
        }
    }


    /**
     * Método que disipa la feromona de todos los enlaces del grafo
     * @param g Grafo en el que se mueven las hormigas
     */
    public static void disiparFeromona(Grafo g) {
        double ro = 0.4;
        // Se recorren todos los nodos del grafo
        for (int i = 0; i < g.getGrafo().getNodos().length; i++) {
            for (int j = 0; j < g.getGrafo().getNodos()[i].length; j++) {
                // Se recorren todos los enlaces del nodo distintos de null
                // y no marcados
                // y se disipa su feromona multiplicando feromona por 1 - ro,
                // sumando ferNueva y poniendo ésta a 0
                for (int k = 0; k < g.getGrafo().getNodos()[i][j].getEnlaces().length; k++) {
                    // Para hacer menos get
                    Enlace actual = g.getGrafo().getNodos()[i][j].getEnlaces()[k];
                    if (actual != null && !actual.getMarcado()) {
                        actual.setFeromona((1 - ro) * actual.getFeromona()
                                + actual.getFerNueva());
                        actual.setFerNueva(0);
                    }
                }
            }
        }
    }

    /**
     * Método que limpia el camino de enlaces para la siguiente iteración
     * (y pone el costeTotal a 0)
     */
    public void limpiaCamino() {
        camino.clear();
        costeTotal = 0;
    }


    /**
     * Método que mueve a la hormiga en un nodo
     * @return true si ha llegado a la meta y false en caso contrario
     */
    public boolean mover() {
        // Tabla de decisión
        double[] tabla;
        // Nodo actual
        Posicion actual;
        // Nodo anterior
        Posicion anterior = null;

        // Si el hormiguero es tabú se devuelve true porque no se puede llegar
        // a la portería rival
        if (tabu.contains(hormiguero)) {
            return true;
        }

        /*
         * Si el camino está vacío el nodo actual es el hormiguero, si no es
         * el último nodo del camino
         */
        if (camino.isEmpty()) {
            tabla = grafo.getGrafo().getNodos()[hormiguero.getX()]
                    [hormiguero.getY()].getTablaDec();
            actual = hormiguero;
        } else {
            if (camino.get(camino.size() - 1).getY() < 17  && camino.get(camino.size() - 1).getY() > -1) {
                tabla = grafo.getGrafo().getNodos()[camino.get(camino.size() - 1).getX()]
                        [camino.get(camino.size() - 1).getY()].getTablaDec();
                actual = camino.get(camino.size() - 1);
                if (camino.size() > 1) {
                    anterior = camino.get(camino.size() - 2);
                } else {
                    anterior = hormiguero;
                }
            } else {
                // Si ya ha llegado a la portería no hacen falta más movimientos
                if (camino.get(camino.size() - 1).getY() == meta.getY()) {
                    // Nunca debería entrar por aquí
                    return true;
                } else {
                    // Se borra el anterior nodo porque es la portería rival
                    // (y no interesa) y se sigue moviendo por si va a otro sitio
                    // distinto marcando este como tabú
                    if (camino.size() > 1) {
                        costeTotal -= grafo.obtenerEnlace
                            (camino.get(camino.size() - 2)
                            , camino.get(camino.size() - 1)).getCoste
                            (grafo.obtenerIndiceCoste(camino.get(camino.size() - 2),
                            camino.get(camino.size() - 1)));
                        if (!tabu.contains(camino.get(camino.size() - 1))) {
                            tabu.add(camino.get(camino.size() - 1));
                        }
                        camino.remove(camino.size() - 1);
                        actual = camino.get(camino.size() - 1);
                        if (camino.size() > 1) {
                            anterior = camino.get(camino.size() - 2);
                        } else {
                            anterior = hormiguero;
                        }
                    } else {
                        costeTotal -= grafo.obtenerEnlace
                            (hormiguero
                            , camino.get(camino.size() - 1)).getCoste
                            (grafo.obtenerIndiceCoste(hormiguero,
                            camino.get(camino.size() - 1)));
                        if (!tabu.contains(camino.get(camino.size() - 1))) {
                            tabu.add(camino.get(camino.size() - 1));
                        }
                        camino.remove(camino.size() - 1);                        
                        actual = hormiguero;
                        anterior = null;
                    }
                    if (camino.size() > 1) {
                        tabla = grafo.getGrafo().getNodos()[camino.get(camino.size() - 1).getX()]
                            [camino.get(camino.size() - 1).getY()].getTablaDec();
                    } else {
                        tabla = grafo.getGrafo().getNodos()[hormiguero.getX()]
                            [hormiguero.getY()].getTablaDec();
                    }
                }

            }
        }

        // Para hacer menos get
        Nodo actNodo = grafo.getGrafo().getNodos()[actual.getX()]
                        [actual.getY()];

        /*
         * Si todos los enlaces del nodo excepto el anterior son null o están
         * marcados o los nodos conectados son tabú o pertenecen al camino
         * se añade este nodo a la lista tabú y se mueve al nodo
         * anterior sin dejar feromona y eliminando la feromona del enlace
         */
        if (!actual.equals(hormiguero)) {
            int nulles = 0;
            for (int i = 0; i < grafo.getGrafo().getNodos()[actual.getX()]
                    [actual.getY()].getEnlaces().length; i++) {
                // Para hacer menos get
                Enlace aux = grafo.getGrafo().getNodos()[actual.getX()]
                        [actual.getY()].getEnlaces()[i];


                if (!anterior.equals(Grafo.obtenerPosNodo(actual, i))) {
                    if (aux == null || aux.getMarcado()
                            || tabu.contains(Grafo.obtenerPosNodo(actual, i))
                            || camino.contains(Grafo.obtenerPosNodo(actual, i))
                            || hormiguero.equals(Grafo.obtenerPosNodo(actual, i))) {
                        nulles++;
                    }
                }
            }
            if (nulles == 7) {
                if (camino.size() > 1) {
                    Enlace aBorrar = grafo.obtenerEnlace
                                (camino.get(camino.size() - 2)
                                , camino.get(camino.size() - 1));
//                    costeTotal -= aBorrar.getCoste(grafo.obtenerIndiceCoste
//                            (camino.get(camino.size() - 2)
//                                , camino.get(camino.size() - 1)));
                    costeTotal -= Math.min(aBorrar.getCoste(0), aBorrar.getCoste(1));
                    aBorrar.setFerNueva(0);
                    aBorrar.setFeromona(0);
                    camino.remove(camino.size() - 1);
                    if (!tabu.contains(actual)) {
                        tabu.add(actual);
                    }
                    return false;
                } else {
                    Enlace aBorrar = grafo.obtenerEnlace
                                (hormiguero
                                , camino.get(camino.size() - 1));
//                    costeTotal -= aBorrar.getCoste(grafo.obtenerIndiceCoste(hormiguero
//                                , camino.get(camino.size() - 1)));
                    costeTotal -= Math.min(aBorrar.getCoste(0), aBorrar.getCoste(1));
                    aBorrar.setFerNueva(0);
                    aBorrar.setFeromona(0);
                    camino.remove(camino.size() - 1);
                    if (!tabu.contains(actual)) {
                        tabu.add(actual);
                    }
                    return false;
                }
            }
        }

        // Variable para saber si algún nodo está en la lista tabú
        boolean algunoTabu = false;

        // tabla de probabilidades (inicialmente igual a la de decisión)
        double[] tablaProb = new double[tabla.length];
        System.arraycopy(tabla, 0, tablaProb, 0, tabla.length);

        // Si alguno de los nodos adyacentes es tabú se pone a 0 su entrada de
        // la tabla de probabilidades y después se normaliza ésta.
        // Se hace lo mismo si el nodo pertenece al camino para que no vuelva
        // a donde ya estaba y no haya ciclos
        for (int i = 0; i < actNodo.getEnlaces().length; i++) {
            Posicion aux = Grafo.obtenerPosNodo(actual, i);
            if (tabu.contains(aux)
                    || camino.contains(aux)
                    || hormiguero.equals(aux)) {
                algunoTabu = true;
                tablaProb[i] = 0;
            }
        }
        if (algunoTabu) {
            // Normalización de la tabla de probabilidades
            double sum = 0;
            for (int i = 0; i < tablaProb.length; i++) {
                sum += tablaProb[i];
            }

            if (sum > 0) {
                for (int i = 0; i < tablaProb.length; i++) {
                    tablaProb[i] /= sum;
                }
            } else {
                for (int i = 0; i < tablaProb.length; i++) {
                    tablaProb[i] = 0.125;
                }
            }
        }

        /*
         * Se guía un poco a las hormigas
         * aumentando la probabilidad de los nodos más cercanos a la meta
         */

        // Se resta a la probabilidad de cada nodo 0.87 * distancia
        // a la meta restando al final el mínimo si es menor que 0 y normalizando
        for (int i = 0; i < tablaProb.length; i++) {
            if (tablaProb[i] != 0) {
                tablaProb[i] -= 0.87 * distancia(Grafo.obtenerPosNodo(actual, i));
            }
        }
        double minimo = Math.min(tablaProb[0], Math.min(tablaProb[1]
                , Math.min(tablaProb[2], Math.min(tablaProb[3]
                , Math.min(tablaProb[4], Math.min(tablaProb[5]
                , Math.min(tablaProb[6], tablaProb[7])))))));

        if (minimo < 0) {
            for (int i = 0; i < tablaProb.length; i++) {
                if (tablaProb[i] != 0) {
                    tablaProb[i] -= minimo;
                }
            }
        }

        // Ahora se normaliza la tabla de probabilidades
        double sum = 0;
        for (int i = 0; i < tablaProb.length; i++) {
            sum += tablaProb[i];
        }

        if (sum > 0) {
            for (int i = 0; i < tablaProb.length; i++) {
                tablaProb[i] /= sum;
            }
        } else {
            for (int i = 0; i < tablaProb.length; i++) {
                tablaProb[i] = 0.125;
            }
        }


        /*
         * Ya se tiene la tabla de probabilidades completa, ahora mueve
         * la hormiga por el método de la ruleta acumulado y se deja
         * feromona en el enlace al que se mueva (1 / coste del enlace)
         */

        /*
         * Porcentajes acumulados
         */
        double[] vectorAcum = new double[tablaProb.length];

        /*
         * Se calcula el vector de acumulados
         */
        for (int i = 0; i < tablaProb.length; i++) {
            if (i == 0) {
                vectorAcum[i] = tablaProb[i];
            } else {
                vectorAcum[i] = vectorAcum[i - 1] + tablaProb[i];
            }
        }

        // Mejor enlace
        int mejor = 0;

        /*
         * Método acumulado de la ruleta en sí mismo
         */
        double aleat = Math.random();

        /*
         * Si es el primer valor
         */
        if (aleat < vectorAcum[0] && actNodo.getEnlaces()[0] != null
                && !actNodo.getEnlaces()[0].getMarcado()) {
            mejor = 0;
        } else { // Si es uno de los valores intermedios o el último
            for (int j = 0; j < tablaProb.length - 1; j++) {
                if (actNodo.getEnlaces()[j + 1] != null
                        && !actNodo.getEnlaces()[j + 1].getMarcado() &&
                        aleat >= vectorAcum[j] && aleat < vectorAcum[j + 1]) {
                    mejor = j + 1;
                    break;
                }
            }
        }

        // Si el enlace que ha tocado está marcado o es null
        // (poco probable) se elige otro válido aleatoriamente
        while (actNodo.getEnlaces()[mejor] == null
                || actNodo.getEnlaces()[mejor].getMarcado()) {
            mejor = (int) (Math.random() * 8);
        }

        // Ya se tiene el mejor enlace (mejor). Ahora se deja feromona en él
        // y se mueve al nodo conectado (se mete éste al final de camino)
        actNodo.getEnlaces()[mejor]
                .sumaFerNueva(1D / actNodo.getEnlaces()[mejor].getCoste
                (grafo.obtenerIndiceCoste(actual, Grafo.obtenerPosNodo(actual, mejor))));
        camino.add(Grafo.obtenerPosNodo(actual, mejor));
//        costeTotal += actNodo.getEnlaces()[mejor].getCoste
//                ((grafo.obtenerIndiceCoste(actual, Grafo.obtenerPosNodo(actual, mejor))));
        costeTotal += Math.min(actNodo.getEnlaces()[mejor].getCoste(0), actNodo.getEnlaces()[mejor].getCoste(1));

        // Si se ha llegado a la meta se devuelve true para no mover más
        // hasta la siguiente iteración y en caso contrario se devuelve false
        if (camino.get(camino.size() - 1).equals(meta)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * Método que hace volver a la hormiga al hormiguero desde el último nodo
     * del camino dejando feromona (se invoca dos veces en la hormiga que ha
     * recorrido el camino más corto a la portería rival)
     */
    public void volver() {
        // Se recorren todos los nodos del camino
        for (int i = camino.size() - 1; i > 0; i--) {
            // Se obtiene el enlace mirando el enlace anterior y se aumenta su
            // ferNueva
            Enlace aux = grafo.obtenerEnlace(camino.get(i - 1), camino.get(i));
//            aux.sumaFerNueva(1D / aux.getCoste
//                    (grafo.obtenerIndiceCoste(camino.get(i - 1), camino.get(i))));
            aux.sumaFerNueva(1D / Math.min(aux.getCoste(0), aux.getCoste(1)));
        }
        // Falta el enlace entre el hormiguero y el primer nodo del camino
        Enlace aux = grafo.obtenerEnlace(hormiguero, camino.get(0));
//        aux.sumaFerNueva(1D / aux.getCoste
//                (grafo.obtenerIndiceCoste(hormiguero, camino.get(0))));
        aux.sumaFerNueva(1D / Math.min(aux.getCoste(0), aux.getCoste(1)));
    }



    /**
     * Método que devuelve true si la lista indicada por parámetro tiene un objeto
     * de la clase Posicion igual al segundo parámetro
     * @param lista
     * @param pos
     * @return true si contiene un elemento igual a Pos y false alias
     */
    public static boolean contiene(ArrayList<Posicion> lista, Posicion pos) {
        boolean contiene = false;

        // Se recorre comenzando por el final porque es más probable que esté por el final
        for (int i = lista.size() - 1; i >= 0 && !contiene; i--) {
            if (lista.get(i).equals(pos)) {
                contiene = true;
            }
        }

        return contiene;
    }

    /**
     * Método que compara los elementos de un array de double secuencialmente
     * y devuelve true si al menos length/4 elementos del array son muy parecidos
     * secuencialmente (su diferencia es menor que 0.1)
     * @param tablaProb
     * @return true si los elementos son muy parecidos y false alias
     */
    public static boolean muyParecidos(double[] tablaProb) {
        boolean muyParecidos = false;

        double epsilon = 0.1;

        double anterior = tablaProb[0];

        boolean[] parecidos = new boolean[tablaProb.length - 1];

        for (int i = 1; i < tablaProb.length; i++) {
            if (Math.abs(anterior - tablaProb[i]) < epsilon) {
                parecidos[i - 1] = true;
            }
            anterior = tablaProb[i];
        }

        // Si hay al menos tablaProb.lenght / 4 probabilidades muy parecidas
        // se considera al array muy parecido
        int contador = 0;
        for (int i = 0; i < parecidos.length; i++) {
            if (parecidos[i]) {
                contador++;
            }
        }

        if (contador >= tablaProb.length / 4) {
            muyParecidos = true;
        }

        return muyParecidos;
    }

    /**
     * Método que devuelve la distancia a la meta de un objeto Posicion, donde
     * la distancia es el máximo entre la distancia en X y la distancia en Y
     * @param pos Objeto de la clase Posicion
     * @return La distancia a la meta
     */
    public double distancia(Posicion pos) {
        double resultado = 0;

        double x1 = pos.getX();
        double y1 = pos.getY();

        double x2 = meta.getX();
        double y2 = meta.getY();

        // Se calcula la distancia euclídea
        resultado = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));

        return resultado;
    }

}
