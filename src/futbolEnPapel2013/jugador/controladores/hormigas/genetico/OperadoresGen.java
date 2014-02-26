package futbolEnPapel2013.jugador.controladores.hormigas.genetico;

import java.util.BitSet;
import java.util.Random;

/**
 * Clase de operadores genéticos
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
    public static final int RULETA = 0;
    /**
     * Método de torneos
     */
    public static final int TORNEOS = 1;
    /**
     * Reemplazo generacional
     */
    public static final int GENERACIONAL = 2;
    /**
     * Reemplazo estacionario
     */
    public static final int ESTACIONARIO = 3;

    /**
     * Método que implementa el operador de selección de la ruleta acumulada o torneos
     * @param generacion
     * @param evaluaciones
     * @param tam Tamaño de la generación intermedia
     * @param tamGen
     * @param modo RULETA o TORNEOS
     * @param tamTorneo Si se elige la ruleta este parámetro no se usa
     * @return Los tam individuos seleccionados para cruce
     */
    public static BitSet[][] seleccionar(BitSet[][] generacion, double[] evaluaciones,
            int tam, int tamGen, int modo, int tamTorneo, Random genAleat) {
        BitSet[][] generacionIntermedia = new BitSet[tam][generacion[0].length];

        /*
         * Inicialización de generacionIntermedia
         */
        for (int i = 0; i < generacionIntermedia.length; i++) {
            for (int j = 0; j < generacionIntermedia[0].length; j++) {
                generacionIntermedia[i][j] = new BitSet(tamGen);
            }
        }

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
                    double aleat = genAleat.nextDouble();

                    /*
                     * Si es el primer valor
                     */
                    if (aleat < vectorAcum[0]) {
                        //generacionIntermedia[i] = generacion[0];
                        //System.arraycopy(generacion[0].clone(), 0, generacionIntermedia[i], 0, generacion[0].length);
                        for (int m = 0; m < generacion[0].length; m++) {
                            generacionIntermedia[i][m] = (BitSet) generacion[0][m].clone();
                        }
                    } else { // Si es uno de los valores intermedios o el último
                        for (int j = 0; j < tam - 1; j++) {
                            if (vectorAcum[j] >= aleat && aleat < vectorAcum[j + 1]) {
                                //generacionIntermedia[i] = generacion[j + 1];
                                //System.arraycopy(generacion[j + 1].clone(), 0,
                                  //      generacionIntermedia[i], 0, generacion[j + 1].length);
                                for (int m = 0; m < generacion[j + 1].length; m++) {
                                    generacionIntermedia[i][m] = (BitSet) generacion[j + 1][m].clone();
                                }
                            }
                        }
                    }
                }
                break;

            case TORNEOS:
                /**
                 * Array en el que se almacenan los competidores
                 */
                BitSet[][] torneo = new BitSet[tamTorneo][generacion[0].length];

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
                 * individuos de generacion (competidores del torneo)  aleatoriamente
                 */
                int aleat = 0;

                /*
                 * Se incializa torneo
                 */
                for (int i = 0; i < torneo.length; i++) {
                    for (int j = 0; j < torneo[i].length; j++) {
                        torneo[i][j] = new BitSet(tamGen);
                    }
                }

                /*
                 * Se realizan tam torneos de tamaño tamTorneo
                 */
                for (int i = 0; i < tam; i++) {
                    // Se selecciona a los competidores
                    for (int j = 0; j < tamTorneo; j++) {
                        aleat = genAleat.nextInt(generacion.length);
                        //System.arraycopy(generacion[aleat].clone(), 0, torneo[j], 0, generacion[aleat].length);
                        for (int m = 0; m < generacion[aleat].length; m++) {
                            torneo[j][m] = (BitSet) generacion[aleat][m].clone();
                        }
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
                    //System.arraycopy(generacion[mejor].clone(), 0, generacionIntermedia[i], 0, torneo[mejor].length);
                    for (int m = 0; m < generacion[mejor].length; m++) {
                        generacionIntermedia[i][m] = (BitSet) torneo[mejor][m].clone();
                    }
                }
        }


        return generacionIntermedia;
    }

    /**
     * Cruce simple. Si el número de individuos es impar el último se queda sin cruzar
     * @param generacion
     * @param tamGen
     * @return Todos los hijos resultantes del cruce
     */
    public static BitSet[][] cruzar(BitSet[][] generacion, int tamGen, Random genAleat) {
        BitSet[][] generacionIntermedia = new BitSet[generacion.length][generacion[0].length];

        /**
         * Hijos que se generan
         */
        BitSet[] hijo1 = new BitSet[generacion[0].length];
        BitSet[] hijo2 = new BitSet[generacion[0].length];
        /*
         * Se inicializan los hijos y generacionIntermedia
         */
        for (int i = 0; i < generacion[0].length; i++) {
            hijo1[i] = new BitSet(tamGen);
            hijo2[i] = new BitSet(tamGen);
        }

        for (int i = 0; i < generacionIntermedia.length; i++) {
            for (int j = 0; j < generacionIntermedia[0].length; j++) {
                generacionIntermedia[i][j] = new BitSet(tamGen);
            }
        }

        //System.arraycopy(generacion.clone(), 0, generacionIntermedia, 0, generacion.length);
        for (int m = 0; m < generacion.length; m++) {
            for (int n = 0; n < generacion[m].length; n++) {
                generacionIntermedia[m][n] = (BitSet) generacion[m][n].clone();
            }
        }

        /**
         * Valor aleatorio que indica la barrera de cruce. Un ejemplo con aleat = 1 es el siguiente:
         * |  |||  |  |  | (las tres barras indican que es la barrera de cruce)
         */
        int aleat = 0;


        /*
         * Se van realizando cruces por parejas de individuos
         */
        for (int i = 0; i < generacionIntermedia.length - 1; i = i + 2) {
            /*
             * Se genera un número aleatorio (que es el corte) entre 0 y el número de genes que no
             * sea ni 0 ni el número de genes (porque en ese caso no hay cruce, se copia el
             * individuo entero)
             */
            do {
                aleat = genAleat.nextInt(generacion[0].length);
            } while (aleat == 0 || aleat == generacion[0].length);

            /*
             * Proceso de cruce
             */

            // Primero se copia el primer trozo del primer individuo de la pareja en hijo1
            //System.arraycopy(generacionIntermedia[i].clone(), 0, hijo1, 0, aleat);
            for (int m = 0; m < aleat; m++) {
                hijo1[m] = (BitSet) generacionIntermedia[i][m].clone();
            }
            // Ahora se copia el segundo trozo del 2º individuo de la pareja en hijo1
            //System.arraycopy(generacionIntermedia[i + 1].clone(), aleat, hijo1, aleat, hijo1.length - aleat);
            for (int m = aleat; m < generacionIntermedia[i + 1].length; m++) {
                hijo1[m] = (BitSet) generacionIntermedia[i + 1][m].clone();
            }

            // Ahora se copia el primer trozo del 2º individuo de la pareja en hijo2
            //System.arraycopy(generacionIntermedia[i + 1].clone(), 0, hijo2, 0, aleat);
            for (int m = 0; m < aleat; m++) {
                hijo2[m] = (BitSet) generacionIntermedia[i + 1][m].clone();
            }

            // Ahora se copia el 2º trozo del primer individuo de la pareja en hijo2
            //System.arraycopy(generacionIntermedia[i].clone(), aleat, hijo2, aleat, hijo2.length - aleat);
            for (int m = aleat; m < generacionIntermedia[i].length; m++) {
                hijo2[m] = (BitSet) generacionIntermedia[i][m].clone();
            }

            // Por último se copian hijo1 e hijo2 en cada individuo de la pareja
            //System.arraycopy(hijo1, 0, generacionIntermedia[i], 0, hijo1.length);
            for (int m = 0; m < hijo1.length; m++) {
                generacionIntermedia[i][m] = (BitSet) hijo1[m].clone();
            }
            //System.arraycopy(hijo2, 0, generacionIntermedia[i + 1], 0, hijo2.length);
            for (int m = 0; m < hijo2.length; m++) {
                generacionIntermedia[i + 1][m] = (BitSet) hijo2[m].clone();
            }
        }

        return generacionIntermedia;
    }

    /**
     * Implementación de la mutación mutando cada bit con una probabilidad tmut
     * @param generacion
     * @param tmut
     * @param tamGen
     * @return La generación mutada
     */
    public static BitSet[][] mutar(BitSet[][] generacion, double tmut, int tamGen, Random genAleat) {
        BitSet[][] generacionIntermedia = new BitSet[generacion.length][generacion[0].length];

        /*
         * Incialización de generacionIntermedia
         */
        for (int i = 0; i < generacionIntermedia.length; i++) {
            for (int j = 0; j < generacionIntermedia[0].length; j++) {
                generacionIntermedia[i][j] = new BitSet(tamGen);
            }
        }

        //System.arraycopy(generacion.clone(), 0, generacionIntermedia, 0, generacion.length);
        for (int m = 0; m < generacion.length; m++) {
            for (int n = 0; n < generacion[m].length; n++) {
                generacionIntermedia[m][n] = (BitSet) generacion[m][n].clone();
            }
        }

        /*
         * Se mutan los bits uno a uno con una probabilidad tmut
         */
        for (int i = 0; i < generacionIntermedia.length; i++) {
            for (int j = 0; j < generacionIntermedia[0].length; j++) {
                for (int k = 0; k < tamGen; k++) {
                    if (genAleat.nextDouble() < tmut) {
                        generacionIntermedia[i][j].flip(k);
                    }
                }
            }
        }
        return generacionIntermedia;
    }

    /**
     * Método que implementa el reemplazo. Para que sea generacional los tama�os de las generaciones tienen
     * que ser iguales y para que sea estacinario diferentes. En caso de que sea estacionario, el m�todo
     * empleado es el de reemplazo de los n (tama�o de la intermedia) peores
     * @param generacion
     * @param evaluaciones
     * @param estadisticas
     * @param generacionIntermedia
     * @param evIntermedia
     * @param estIntermedia 
     * @param modo
     * @return La generación tras el reemplazo
     */
    public static BitSet[][] reemplazar(BitSet[][] generacion, double[] evaluaciones, double[][] estadisticas,
            BitSet[][] generacionIntermedia, double[] evIntermedia, double[][] estIntermedia, int modo) {

        switch (modo) {
            case GENERACIONAL:
                if (generacion.length != generacionIntermedia.length) {
                    System.out.println("ERROR FATAL. El tamaño de las gneraciones es distinto y se ha elegido reemplazo generacional");
                    System.exit(-1);
                } else {
                    /*
                     * Se copia generacionIntermedia en generacion
                     */
                    //System.arraycopy(generacionIntermedia.clone(), 0, generacion, 0, generacion.length);
                    for (int m = 0; m < generacionIntermedia.length; m++) {
                        for (int n = 0; n < generacionIntermedia[m].length; n++) {
                            generacion[m][n] = (BitSet) generacionIntermedia[m][n].clone();
                        }
                    }
                    //System.arraycopy(estIntermedia.clone(), 0, estadisticas, 0, estIntermedia.length);
                    for (int m = 0; m < estIntermedia.length; m++) {
                        System.arraycopy(estIntermedia[m], 0, estadisticas[m], 0, estIntermedia[m].length);
                    }
                    System.arraycopy(evIntermedia, 0, evaluaciones, 0, evIntermedia.length);
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
                    quickSort(generacion, evaluaciones, estadisticas, 0, generacion.length - 1);

                    // Se copia generacionIntermedia al inicio de generacion (donde están los peores)
                    //System.arraycopy(generacionIntermedia.clone(), 0, generacion, 0, generacionIntermedia.length);
                    for (int m = 0; m < generacionIntermedia.length; m++) {
                        for (int n = 0; n < generacionIntermedia[m].length; n++) {
                            generacion[m][n] = (BitSet) generacionIntermedia[m][n].clone();
                        }
                    }

                    // Se copian las evaluaciones de la generación intermedia al
                    // principio de las de generacion (las reemplazadas)
                    System.arraycopy(evIntermedia, 0, evaluaciones, 0, evIntermedia.length);

                    // Se copian las estadísticas de la generación intermedia al
                    // principio de las de generacion (las reemplazadas)
                    //System.arraycopy(estIntermedia.clone(), 0, estadisticas, 0, estIntermedia.length);
                    for (int m = 0; m < estIntermedia.length; m++) {
                        System.arraycopy(estIntermedia[m], 0, estadisticas[m], 0, estIntermedia[m].length);
                    }
                }
                break;
        }

        return generacion;
    }

    /**
     * Método que recibe un vector de dos dimensiones de BitSet (una generación) y
     * lo ordena en función de evaluaciones
     * @param vector
     * @param evaluaciones
     * @param estadisticas
     * @param izq Primer elemento que se quiere ordenar del array
     * @param der Último elemento que se quiere ordenar del array
     */
    public static void quickSort(BitSet[][] vector, double[] evaluaciones,
            double[][] estadisticas, int izq, int der) {
        // ordena por el algoritmo de quick sort un vector de BitSet[]
        // comprendidos entre las posiciones izq y der
        // indices
        int i;
        int j;
        int pivote;
        BitSet[] aux; // variable auxiliar para intercambio
        double auxE;	// variable auxiliar para intercambio
        double[] auxEs = new double[estadisticas[0].length]; // variable auxiliar para intercambio
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
                // también se intercambian las estadísticas, porque si no están cambiadas
                System.arraycopy(estadisticas[i], 0, auxEs, 0, estadisticas[i].length);
                System.arraycopy(estadisticas[j], 0, estadisticas[i], 0, estadisticas[j].length);
                System.arraycopy(auxEs, 0, estadisticas[j], 0, auxEs.length);
                i++;
                j--;
            } // end del if
        } while (i <= j);
        // llamadas recursivas
        if (izq < j) {
            quickSort(vector, evaluaciones, estadisticas, izq, j);
        }
        if (i < der) {
            quickSort(vector, evaluaciones, estadisticas, i, der);
        }
    }
}
