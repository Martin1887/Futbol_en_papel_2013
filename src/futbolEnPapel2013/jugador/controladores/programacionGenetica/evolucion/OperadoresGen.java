package futbolEnPapel2013.jugador.controladores.programacionGenetica.evolucion;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.Arbol;

/**
 * Clase de operadores genéticos para programación genética
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class OperadoresGen {

    /**
     * Atributos constantes que representan las posibilidades de operadores
     */
    /**
     * Método acumulado de la ruleta
     */
    static final int RULETA = 0;
    /**
     * Método de torneos
     */
    static final int TORNEOS = 1;
    /**
     * Reemplazo generacional
     */
    static final int GENERACIONAL = 2;
    /**
     * Reemplazo estacionario
     */
    static final int ESTACIONARIO = 3;

    /**
     * Método que implementa el operador de selección de la ruleta acumulada o torneos
     * @param generacion
     * @param evaluaciones
     * @param tam
     * @param modo RULETA o TORNEOS
     * @param tamTorneo Si se elige la ruleta este parámetro no se usa
     * @return Los individuos seleccionados
     */
    public static Arbol[] seleccionar(Arbol[] generacion, double[] evaluaciones, int tam,
            int modo, int tamTorneo) {
        Arbol[] generacionIntermedia = new Arbol[tam];



        switch (modo) {
            case RULETA:
                /*
                 * Fitness de cada individuo
                 */
                double[] fitness = new double[tam];

                /*
                 * Suma de los fitness de todos los individuos
                 */
                int sumaFit = 0;

                /*
                 * Fitness relativos
                 */
                double[] fRelav = new double[tam];

                /*
                 * Fitness acumulados
                 */
                double[] vectorAcum = new double[tam];

                /*
                 * Se calcula el fitness de cada individuo y la suma de todos ellos
                 */
                for (int i = 0; i < generacionIntermedia.length; i++) {
                    fitness[i] = evaluaciones[i];
                    sumaFit += fitness[i];
                }

                /*
                 * Se calculan los fitness relativos y el vector de acumulados
                 */
                for (int i = 0; i < tam; i++) {
                    if (sumaFit != 0) {
                        fRelav[i] = fitness[i] / sumaFit;
                    } else {
                        fRelav[i] = 0;
                    }
                    if (i == 0) {
                        vectorAcum[i] = fitness[i];
                    } else {
                        vectorAcum[i] = vectorAcum[i - 1] + fitness[i];
                    }
                }

                /*
                 * Método acumulado de la ruleta en sí mismo
                 */
                for (int i = 0; i < tam; i++) {
                    double aleat = Math.random();

                    /*
                     * Si es el primer valor
                     */
                    if (aleat < vectorAcum[0]) {
                        generacionIntermedia[i] = generacion[0];
                    } else { // Si es uno de los valores intermedios o el último
                        for (int j = 0; j < tam - 1; j++) {
                            if (vectorAcum[j] >= aleat && aleat < vectorAcum[j + 1]) {
                                generacionIntermedia[i] = generacion[j + 1];
                            }
                        }
                    }
                }
                break;

            case TORNEOS:
                /**
                 * Array en el que se almacenan los competidores
                 */
                Arbol[] torneo = new Arbol[tamTorneo];

                /**
                 * Array para almacenar la evaluación de cada competidor del torneo
                 */
                double[] evalTorneo = new double[tamTorneo];

                /**
                 * Variable auxiliar para seleccionar al ganador del torneo
                 */
                int mejor = 0;

                /**
                 * Variable aleatoria auxiliar usada para seleccionar
                 * individuos de generacion (competidores del torneo) aleatoriamente
                 */
                int aleat = 0;


                /*
                 * Se realizan tam torneos de tamaño tamTorneo
                 */
                for (int i = 0; i < tam; i++) {
                    // Se selecciona a los competidores
                    for (int j = 0; j < tamTorneo; j++) {
                        aleat = (int) (Math.random() * generacion.length);
                        torneo[j] = generacion[aleat];
                        evalTorneo[j] = evaluaciones[aleat];
                    }
                    // Competición de los competidores (sólo gana el mejor)
                    mejor = 0;
                    for (int j = 1; j < tamTorneo; j++) {
                        if (evaluaciones[j] > evaluaciones[mejor]) {
                            mejor = j;
                        }
                    }
                    // Se mete al ganador en generacionIntermedia
                    generacionIntermedia[i] = generacion[mejor];
                }
        }


        return generacionIntermedia;
    }


    /**
     * Método que implementa el reemplazo. Para que sea generacional los tamaños de las generaciones tienen
     * que ser iguales y para que sea estacinario diferentes. En caso de que sea estacionario, el método
     * empleado es el de reemplazo de los n (tamaño de la intermedia) peores
     * @param generacion
     * @param evaluaciones
     * @param generacionIntermedia
     * @param evIntermedia
     * @param modo
     * @return La generación tras el reemplazo
     */
    public static Arbol[] reemplazar(Arbol[] generacion, double[] evaluaciones, Arbol[] generacionIntermedia, double[] evIntermedia, int modo) {

        switch (modo) {
            case GENERACIONAL:
                if (generacion.length != generacionIntermedia.length) {
                    System.out.println("ERROR FATAL. El tamaño de las generaciones es distinto y se ha elegido reemplazo generacional");
                    System.exit(-1);
                } else {
                    /*
                     * Se copia generacionIntermedia en generacion
                     */
                    System.arraycopy(generacionIntermedia, 0, generacion, 0, generacion.length);
                }
                break;

            case ESTACIONARIO:
                if (generacion.length == generacionIntermedia.length) {
                    System.out.println("ERROR FATAL. El tamaño de las gneraciones es igual y se ha elegido reemplazo estacionario");
                    System.exit(-1);
                } else {
                    /*
                     * Se reemplazan los generacionIntermedia.length peores
                     * individuos de generacion por generacionIntermedia
                     */

                    // Se ordena generacion de menor a mayor fEvaluacion
                    quickSort(generacion, evaluaciones, 0, generacion.length - 1);

                    // Se copia generacionIntermedia al inicio de generacion (donde están los peores)
                    System.arraycopy(generacionIntermedia, 0, generacion, 0, generacionIntermedia.length);

                    // Se copian las evaluaciones de la generación intermedia al principio de las de generacion (las reemplazadas)
                    System.arraycopy(evIntermedia, 0, evaluaciones, 0, evIntermedia.length);
                }
                break;
        }

        return generacion;
    }

    /**
     * Método que recibe un vector de Arbol (una generación) y
     * lo ordena en función de la función de evaluación (vector evaluaciones)
     * @param vector
     * @param izq Primer elemento que se quiere ordenar del array
     * @param der Último elemento que se quiere ordenar del array
     */
    public static void quickSort(Arbol[] vector, double[] evaluaciones, int izq, int der) {
        // ordena por el algoritmo de quick sort un vector de int
        // comprendidos entre las posiciones izq y der
        // indices
        int i;
        int j;
        int pivote;
        Arbol aux; // variable auxiliar para intercambio
        double auxE;	// variable auxiliar para intercambio
        pivote = (izq + der) / 2;
        i = izq;
        j = der;
        do {
            while (evaluaciones[i] < evaluaciones[pivote]) {
                i++;
            }
            while (evaluaciones[j] > evaluaciones[pivote]) {
                j--;
            }
            if (i <= j) {
                aux = vector[i];
                vector[i] = vector[j];
                vector[j] = aux;
                // también se intercambian las evaluaciones, porque si no están cambiadas
                auxE = evaluaciones[i];
                evaluaciones[i] = evaluaciones[j];
                evaluaciones[j] = auxE;
                i++;
                j--;
            } // end del if
        } while (i <= j);
        // llamadas recursivas
        if (izq < j) {
            quickSort(vector, evaluaciones, izq, j);
        }
        if (i < der) {
            quickSort(vector, evaluaciones, i, der);
        }
    }

}
