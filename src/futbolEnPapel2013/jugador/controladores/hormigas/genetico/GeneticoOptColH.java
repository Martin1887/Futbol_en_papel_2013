package futbolEnPapel2013.jugador.controladores.hormigas.genetico;

import futbolEnPapel2013.Juego;
import futbolEnPapel2013.JuegoObscuro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * Clase que implementa el algoritmo genético que optimiza las hormigas.
 * La codificación usada es 6 números en binario de 8 bits codificados en
 * código Gray para evitar epístasis. 8 bits son adecuados para todos los pará-
 * metros a optimizar, por eso todos tienen el mismo número de bits. Los pará-
 * metros, en orden, son los siguientes (los -1 son para que nunca valgan 0):
 * Número de hormigas - 1
 * Número de ciclos en los que todas las hormigas llegan a la meta - 1
 * alfa * 100 - 1
 * beta * 100 - 1
 * ro * 100 - 1
 * restaPorDistancia * 100 - 1
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class GeneticoOptColH {

    /**
     * Tiempo total en el movimiento de las hormigas de un individuo (cada
     * elemento del array es el tiempo de un individuo)
     */
    public static long[] tiempoTotal;
    /**
     * Número de turnos que ha movido un individuo (cada elemento del array es
     * el número de turnos de un individuo)
     */
    public static int[] nTurnos;
    /**
     * Tiempo total en el movimiento de las hormigas de un individuo de la
     * generación intermedia (cada elemento del array es el tiempo de un individuo)
     */
    public static long[] tiempoTotalInter;
    /**
     * Número de turnos que ha movido un individuo de la generación intermedia
     * (cada elemento del array es el nº de turnos de un individuo)
     */
    public static int[] nTurnosInter;

    /**
     * Generador aleatorio
     */
    public static Random genAleat = new Random(System.currentTimeMillis());


    /**
     * Función de evaluación. Juega 4 partidos contra el alfa-beta de profundidad
     * 1 y devuelve la evaluación media menos 2 elevado al número de segundos
     * menos 2 si tarda más de 2 segundos de media por movimiento
     * y la evaluación media si tarda menos
     * @param individuo
     * @param elemento El elemento dentro de la generación
     * @param intermedia Si es la intermedia o la generación entera
     * @param fSalida
     * @param estadisticas Las estadísticas del individuo
     * @return La evaluación del individuo
     */
    public static double fEvaluacion(BitSet[] individuo, int elemento, boolean intermedia, BufferedWriter fSalida,
            double[] estadisticas) {
        double evaluacion = 0;

        // Se almacenan los valores del fenotipo
        double[] fenotipo = new double[individuo.length];

        for (int i = 0; i < fenotipo.length; i++) {
            fenotipo[i] = grayAInt(individuo[i]);
            // Se le suma 1 para que nunca valga 0
            fenotipo[i]++;
            if (i > 1) {
                fenotipo[i] /= 100D;
            }
        }

        // Se inicializan las estadísticas de este individuo
        if (intermedia) {
            nTurnosInter[elemento] = 0;
            tiempoTotalInter[elemento] = 0;
        } else {
            nTurnos[elemento] = 0;
            tiempoTotal[elemento] = 0;
        }

        // Se ejecutan los 20 partidos copiando a la vez las estadísticas medias
        // en estadisticas
        int tipoJ1 = Juego.CPU;
        int tipoJ2 = Juego.CPU;
        int niv1 = Juego.C_HORMIGAS_GEN_EVAL;
        int niv2 = 1;

        
        JuegoObscuro juego = null;
        try {
            juego = new JuegoObscuro(tipoJ1, tipoJ2, niv1, niv2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        double[][] res = juego.iniciar_partidos
                (20, fSalida, fenotipo, elemento, intermedia);
        double[] est = res[res.length - 1];
        System.arraycopy(est, 0, estadisticas,
                0, est.length);

        // Se asigna la evaluación como la evaluación media y se procede a restar
        // por tiempo si el tiempo medio de cada movimiento ha sido mayor a 2 segundos
        evaluacion = estadisticas[estadisticas.length - 1];

        double tiempoMedio = 0;
        if (intermedia) {
            tiempoMedio = tiempoTotalInter[elemento] / nTurnosInter[elemento];
        } else {
            tiempoMedio = tiempoTotal[elemento] / nTurnos[elemento];
        }

        if (tiempoMedio > 2000) {
            evaluacion -= Math.pow(2, (tiempoMedio - 2000) / 1000);
        }
        try {
            fSalida.write(String.valueOf(evaluacion));
            fSalida.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return evaluacion;
    }

    /**
     * Método que pasa un BitSet
     * codificado en Gray a entero
     * @param gen
     * @return El entero que representa ese BitSet
     */
    public static int grayAInt(BitSet gen) {
        int resultado = 0;

        /**
         * Array en el que se almacena el valor en binario normal
         */
        BitSet bin = new BitSet(gen.length());

        /*
         * Transformación a binario normal
         */
        if (gen.length() > 0) {
            bin.set(gen.length() - 1, gen.get(gen.length() - 1));
            for (int i = gen.length() - 2; i >= 0; i--) {
                bin.set(i, gen.get(i) ^ (bin.get(i + 1)));
            }
        } else {
            // el gen del parámetro vale 0
            bin.set(0, false);
        }

        /*
         * Transformación de los bits a entero
         */
        for (int i = 0; i < bin.length(); i++) {
            if (bin.get(i) == true) {
                resultado += Math.pow(2, i);
            }
        }

        return resultado;
    }

    /**
     * Método que devuelve el índice del indMejor individuo
     * de la generación
     * @param generacion
     * @param evaluaciones
     * @return El índice del mejor individuo de la generación
     */
    public static int mejor(BitSet[][] generacion, double[] evaluaciones) {
        int mejor = 0;

        for (int i = 1; i < generacion.length; i++) {
            if (evaluaciones[i] > evaluaciones[mejor]) {
                mejor = i;
            }
        }

        return mejor;
    }

    /**
     * Método que implementa la optimización de unos parámetros para una
     * colonia de hormigas
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        /**
         * Tolerancia usada en el criterio de parada
         */
        final int TOL = 50;


        /**
         * Generación inicial sin inicializar
         * (Tamaño de la generación 16 cromosomas)
         * (6 parámetros)
         */
        BitSet[][] generacion = new BitSet[16][6];

        /**
         * Tamaño a reemplazar. Se inicializa a n/2 para que los individuos
         * correctos no se pierdan en la siguiente generación
         */
        int tamano = generacion.length / 2;

        // Se inicializan los atributos estáticos
        nTurnos = new int[generacion.length];
        nTurnosInter = new int[tamano];
        tiempoTotal = new long[generacion.length];
        tiempoTotalInter = new long[tamano];

        /**
         * Número de generación
         */
        int nGeneracion = 0;

        /**
         * Tasa de mutación. Inicializada a 0,25
         */
        double tmut = 0.25;

        /**
         * Tamaño de torneo. Usado para regular la presión selectiva
         */
        int tamTorneo = 2;

        /**
         * Variable usada para implementar el criterio de parada
         */
        boolean fin = false;

        /**
         * Mejor individuo de la generación anterior
         */
        BitSet[] mejorAnt = new BitSet[generacion[0].length];

        /**
         * Evaluación del indMejor individuo de la generación anterior
         */
        double evMejorAnt = 0;

        /**
         * Barrera que sirve para esperar a que se ejecuten todas las evaluaciones
         */
        CyclicBarrier barrera;

        /**
         * Número de hilos que ejecutarán la función de evaluación
         */
        int nHilos = 16;


        /**
         * Variables para imprimir en el fichero cuya ruta se introduzca
         * por pantalla la evaluación del indMejor individuo de
         * cada generación, para así después poder hacer una gráfica
         */
        File f;
        FileOutputStream f1;
        OutputStreamWriter f2;
        BufferedWriter linea;
//        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        String ruta = "evMejor.txt";

//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar las evaluaciones del mejor");

//        ruta = teclado.readLine();

        f = new File(ruta);
        f1 = new FileOutputStream(f);
        f2 = new OutputStreamWriter(f1);
        linea = new BufferedWriter(f2);

        linea.write("GanaJ1 (1/0)\tminDistMetaJ1\tminDistMetaJ2\t"
                + "turnosCampoJ1\tturnosCampoJ2\tturnos totales\tevaluación"
                + " (600*gana+40*minDistMetaJ1-40*minDistMetaJ2+2*porcentajeCampoJ2"
                + "-5*turnosTotales si gana y +5*turnosTotales si pierde)\tevaluación-2^(tM-2s)");


        /**
         * Variables para imprimir en el fichero cuya ruta se introduzca
         * por pantalla los valores del indMejor individuo de
         * cada generación, para sacar conclusiones
         */
        File fM;
        FileOutputStream fM1;
        OutputStreamWriter fM2;
        BufferedWriter lineaM;
//        teclado = new BufferedReader(new InputStreamReader(System.in));


//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar los parámetros del mejor");

//        ruta = teclado.readLine();

        ruta = "mejor.txt";

        fM = new File(ruta);
        fM1 = new FileOutputStream(fM);
        fM2 = new OutputStreamWriter(fM1);
        lineaM = new BufferedWriter(fM2);

        /**
         * Variables para imprimir en el fichero cuya ruta se introduzca
         * por pantalla la evaluación de todos los individuos de
         * cada generación, para así después poder hacer una gráfica
         */
        File fT;
        FileOutputStream f1T;
        OutputStreamWriter f2T;
        BufferedWriter lineaT;
//        teclado = new BufferedReader(new InputStreamReader(System.in));

//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar las evaluaciones de todos");

//        ruta = teclado.readLine();

        ruta = "evTodos.txt";

        fT = new File(ruta);
        f1T = new FileOutputStream(fT);
        f2T = new OutputStreamWriter(f1T);
        lineaT = new BufferedWriter(f2T);

        lineaT.write("GanaJ1 (1/0)\tminDistMetaJ1\tminDistMetaJ2\t"
                + "turnosCampoJ1\tturnosCampoJ2\tturnos totales\tevaluación"
                + " (600*gana+40*minDistMetaJ1-40*minDistMetaJ2+2*porcentajeCampoJ2"
                + "-5*turnosTotales si gana y +5*turnosTotales si pierde)\tevaluación-2^(tM-2s)");

        /**
         * Variables para imprimir en el fichero cuya ruta se introduzca
         * por pantalla las estadísticas y evaluación de cada individuo de la
         * generación, para comprobar errores
         */
        File fP;
        FileOutputStream f1P;
        OutputStreamWriter f2P;
        BufferedWriter lineaP;
//        teclado = new BufferedReader(new InputStreamReader(System.in));

//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar las evaluaciones de todos");

//        ruta = teclado.readLine();

        ruta = "generacion.txt";

        fP = new File(ruta);
        f1P = new FileOutputStream(fP);
        f2P = new OutputStreamWriter(f1P);
        lineaP = new BufferedWriter(f2P);

        /*
         * Se inicializa la generación
         */
        for (int i = 0; i < generacion.length; i++) {
            for (int j = 0; j < generacion[0].length; j++) {

                /*
                 * Se dan valores aleatorios a los bits del gen
                 */
                generacion[i][j] = new BitSet(8);

                for (int k = 0; k < 8; k++) {
                    double aleat = genAleat.nextDouble();
                    boolean valor = false;
                    if (aleat > 0.5) {
                        valor = true;
                    }

                    generacion[i][j].set(k, valor);
                }
            }
        }

        /*
         * Se asignan las evaluaciones para no tener que llamar varias
         * veces a la función de evaluación y así tarde menos y además
         * no varíen las evaluaciones de los individuos en la misma
         * generación. No es necesaria la asignación dentro del bucle porque la
         * de la siguiente generación se hace al evaluar la intermedia y después
         * reemplazar
         */
        double[] evaluaciones = new double[generacion.length];
        double[] evaluacionesIntermedia = new double[tamano];

        /**
         * Estadísticas medias (que rellena la función de evaluación)
         */
        double[][] estadisticas = new double[generacion.length][7];
        double[][] estadisticasIntermedia = new double[tamano][7];

//        for (int i = 0; i < evaluaciones.length; i++) {
//            evaluaciones[i] = fEvaluacion(generacion[i], i, false, lineaT, estadisticas[i]);
//        }

        // El tamaño de la barrera es el número de hilos a ejecutar más este hilo
        barrera = new CyclicBarrier(nHilos + 1);
        int tamTrozo = generacion.length / nHilos;
        int primerElemento = 0;
        for (int i = 0; i < nHilos; i++) {
            BitSet[][] individuos = new BitSet[tamTrozo][generacion[0].length];
            System.arraycopy(generacion, primerElemento, individuos, 0, tamTrozo);
            EvaluacionGen evaluador = new EvaluacionGen(individuos, evaluaciones,
                    primerElemento, false, lineaT, estadisticas, barrera);
            evaluador.start();
            primerElemento += tamTrozo;
        }
        barrera.await();

        // Se escribe en el fichero de comprobación la evaluación de cada individuo
        lineaP.write("Generación inicial");
        lineaP.newLine();
        for (int i = 0; i < generacion.length; i++) {
            lineaP.write("Individuo " + i + ": ");
            for (int j = 0; j < generacion[i].length; j++) {
                lineaP.write(grayAInt(generacion[i][j]) + ", ");
            }
            lineaP.write("\tEstadísticas: ");
            for (int j = 0; j < estadisticas[i].length; j++) {
                lineaP.write(estadisticas[i][j] + "\t");
            }
            lineaP.write(String.valueOf(evaluaciones[i]));
            lineaP.newLine();            
            lineaP.flush();
        }

        lineaP.write("________________________________________________");
        lineaP.newLine();
        lineaP.newLine();
        lineaP.flush();

        /*
         * CRITERIO DE PARADA: la mejora del indMejor gen es menor que TOL
         * y la evaluación es al menos 600
         */
        while (fin == false) {

            /*
             * Limpieza de la generación intermedia (incluyendo el tamaño)
             */
            /**
             * Generación intermedia
             */
            BitSet[][] generacionIntermedia = new BitSet[tamano][generacion[0].length];
            for (int i = 0; i < tamano; i++) {
                for (int j = 0; j < generacion[0].length; j++) {
                    generacionIntermedia[i][j] = new BitSet(8);
                }
            }

            /*
             *
             * Aplicación de operadores
             *
             */

            /*
             * Aplicación de la selección.
             */
            generacionIntermedia = OperadoresGen.seleccionar(generacion, evaluaciones, tamano, 8,
                    OperadoresGen.TORNEOS, tamTorneo, genAleat);

            // Se escribe en el fichero de corrección cada individuo
            lineaP.write("Generación intermedia " + nGeneracion + " tras seleccionar");
            lineaP.newLine();
            for (int i = 0; i < generacionIntermedia.length; i++) {
                lineaP.write("Individuo " + i + ": ");
                for (int j = 0; j < generacionIntermedia[i].length; j++) {
                    lineaP.write(grayAInt(generacionIntermedia[i][j]) + ", ");
                }
                lineaP.newLine();
                lineaP.flush();
            }
            lineaP.write("------------------------------------------------");
            lineaP.newLine();
            lineaP.flush();


            /*
             * Aplicación del cruce simple
             */
            generacionIntermedia = OperadoresGen.cruzar(generacionIntermedia, 8, genAleat);

            // Se escribe en el fichero de corrección cada individuo
            lineaP.write("Generación intermedia " + nGeneracion + " tras cruzar");
            lineaP.newLine();
            for (int i = 0; i < generacionIntermedia.length; i++) {
                lineaP.write("Individuo " + i + ": ");
                for (int j = 0; j < generacionIntermedia[i].length; j++) {
                    lineaP.write(grayAInt(generacionIntermedia[i][j]) + ", ");
                }
                lineaP.newLine();
                lineaP.flush();
            }
            lineaP.write("------------------------------------------------");
            lineaP.newLine();
            lineaP.flush();

            /*
             * Aplicación de la mutación
             */
            generacionIntermedia = OperadoresGen.mutar(generacionIntermedia, tmut, 8, genAleat);

            // Se escribe en el fichero de corrección cada individuo
            lineaP.write("Generación intermedia " + nGeneracion + " tras mutar");
            lineaP.newLine();
            for (int i = 0; i < generacionIntermedia.length; i++) {
                lineaP.write("Individuo " + i + ": ");
                for (int j = 0; j < generacionIntermedia[i].length; j++) {
                    lineaP.write(grayAInt(generacionIntermedia[i][j]) + ", ");
                }
                lineaP.newLine();
                lineaP.flush();
            }
            lineaP.write("------------------------------------------------");
            lineaP.newLine();
            lineaP.flush();


            // Se da valor a las evaluaciones de la intermedia una vez cruzada y mutada
//            for (int i = 0; i < evaluacionesIntermedia.length; i++) {
//                evaluacionesIntermedia[i] = fEvaluacion(generacionIntermedia[i],
//                        i, true, lineaT, estadisticasIntermedia[i]);
//            }

            // El tamaño de la barrera es el número de hilos a ejecutar más este hilo
            barrera = new CyclicBarrier(tamano + 1);
            tamTrozo = 1;
            primerElemento = 0;
            for (int i = 0; i < tamano; i++) {
                BitSet[][] individuos = new BitSet[tamTrozo][generacionIntermedia[0].length];
                System.arraycopy(generacionIntermedia, primerElemento, individuos, 0, tamTrozo);
                EvaluacionGen evaluador = new EvaluacionGen(individuos, evaluacionesIntermedia,
                        primerElemento, true, lineaT, estadisticasIntermedia, barrera);
                evaluador.start();
                primerElemento += tamTrozo;
            }
            barrera.await();

            // Se escribe en el fichero de corrección la evaluación de cada individuo
            lineaP.write("Generación intermedia " + nGeneracion);
            lineaP.newLine();
            for (int i = 0; i < generacionIntermedia.length; i++) {
                lineaP.write("Individuo " + i + ": ");
                for (int j = 0; j < generacionIntermedia[i].length; j++) {
                    lineaP.write(grayAInt(generacionIntermedia[i][j]) + ", ");
                }
                lineaP.write("\tEstadísticas: ");
                for (int j = 0; j < estadisticasIntermedia[i].length; j++) {
                    lineaP.write(estadisticasIntermedia[i][j] + "\t");
                }
                lineaP.write(String.valueOf(evaluacionesIntermedia[i]));
                lineaP.newLine();
                lineaP.flush();
            }
            lineaP.write("------------------------------------------------");
            lineaP.newLine();
            lineaP.flush();


            /*
             * Aplicación del reemplazo estacionario. Los valores del array evaluaciones
             * son modificados en esta función
             */
            generacion = OperadoresGen.reemplazar(generacion, evaluaciones, estadisticas,
                    generacionIntermedia, evaluacionesIntermedia, estadisticasIntermedia, OperadoresGen.ESTACIONARIO);

            nGeneracion++;

            // Se escribe en el fichero de comprobación la evaluación de cada individuo
            lineaP.write("Generación " + nGeneracion);
            lineaP.newLine();
            for (int i = 0; i < generacion.length; i++) {
                lineaP.write("Individuo " + i + ": ");
                for (int j = 0; j < generacion[i].length; j++) {
                    lineaP.write(grayAInt(generacion[i][j]) + ", ");
                }
                lineaP.write("\tEstadísticas: ");
                for (int j = 0; j < estadisticas[i].length; j++) {
                    lineaP.write(estadisticas[i][j] + "\t");
                }
                lineaP.write(String.valueOf(evaluaciones[i]));
                lineaP.newLine();
                lineaP.flush();
            }
            lineaP.newLine();

            /*
             * Disminución de la variabilidad genética a medida que avanzan las generaciones
             * mediante la disminución de la tasa de mutación
             */
            if (nGeneracion == 150) {
                tmut = 0.01;
            }
            if (nGeneracion == 100) {
                tmut = 0.05;
            }
            if (nGeneracion == 70) {
                tmut = 0.1;
            }
            if (nGeneracion == 40) {
                tmut = 0.15;
            }
            if (nGeneracion == 20) {
                tmut = 0.2;
            }

            /*
             * Aumento de la presión selectiva a medida que avanzan las generaciones
             * mediante el aumento del tamaño de los torneos
             */
            if (nGeneracion == 150) {
                tamTorneo = 7;
            }
            if (nGeneracion == 100) {
                tamTorneo = 6;
            }
            if (nGeneracion == 70) {
                tamTorneo = 5;
            }
            if (nGeneracion == 40) {
                tamTorneo = 4;
            }
            if (nGeneracion == 20) {
                tamTorneo = 3;
            }

            /*
             * Se comprueba si se ha alcanzado el criterio de parada
             * (el indMejor individuo de esta generación consigue más de 50
             * puntos de media menos que el de la anterior y son al menos
             * 600 puntos de media))
             */
            if ((evMejorAnt - evaluaciones[mejor(generacion, evaluaciones)] < TOL)
                    && evaluaciones[mejor(generacion, evaluaciones)] >= 600) {
                fin = true;
            }

            int indMejor = mejor(generacion, evaluaciones);

            mejorAnt = generacion[indMejor];
            evMejorAnt = evaluaciones[indMejor];

            // Se escribe en el fichero de comprobación
            lineaP.write("Mejor individuo: " + indMejor);
            lineaP.newLine();
            for (int i = 0; i < mejorAnt.length; i++) {
                lineaP.write(String.valueOf(grayAInt(mejorAnt[i])) + ", ");
            }
            lineaP.newLine();
            lineaP.write("Estadísticas: ");
            for (int i = 0; i < estadisticas[indMejor].length; i++) {
                lineaP.write(estadisticas[indMejor][i] + "\t");
            }
            lineaP.newLine();
            lineaP.write("Evaluación: " + evMejorAnt);
            lineaP.newLine();
            lineaP.write("________________________________________________");
            lineaP.newLine();
            lineaP.newLine();
            lineaP.flush();

            /* impresión del indMejor y de su puntuación */
            for (int i = 0; i < 10; i++) {
                System.out.println("********************");
            }

            System.out.println("Mejor puntuación: " + evMejorAnt);
            System.out.println();
            System.out.println("Mejor individuo: ");
            for (int i = 0; i < mejorAnt.length; i++) {
                System.out.print(String.valueOf(grayAInt(mejorAnt[i])) + ", ");
            }
            System.out.println();

            /*
             * Se imprimen las estadísticas del indMejor y un salto de línea en el fichero
             */
            linea.newLine();
            for (int i = 0; i < estadisticas[indMejor].length; i++) {
                linea.write(estadisticas[indMejor][i] + "\t");
            }
            linea.write(String.valueOf(evMejorAnt));
            linea.flush();

            /*
             * Se imprimen los valores del indMejor y un salto de línea en el fichero
             */
            for (int i = 0; i < mejorAnt.length; i++) {
                lineaM.write(String.valueOf(grayAInt(mejorAnt[i])) + ", ");
            }
            lineaM.newLine();
            lineaM.flush();

            lineaT.newLine();
            lineaT.newLine();
            lineaT.flush();
        }

        System.out.println("El número de generaciones ha sido: " + nGeneracion);
        System.out.println("El nº de individuos de cada generación intermedia es de " + tamano);
        System.out.println("El nº total de selecciones realizadas ha sido " + nGeneracion + " * "
                + tamano + " = " + nGeneracion * tamano);

        System.out.println("******************");
        System.out.println("Mejor individuo: ");
        for (int i = 0; i < mejorAnt.length; i++) {
            System.out.print(String.valueOf(grayAInt(mejorAnt[i])) + ", ");
        }
        System.out.println();
        System.out.println("Mejor puntuación: " + evMejorAnt);

        /*
         * Se cierran los ficheros
         */
        linea.close();
        lineaM.close();
        lineaT.close();
        lineaP.close();
    }
}
