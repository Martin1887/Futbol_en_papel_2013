package futbolEnPapel2013.jugador.controladores.hormigas.genetico;

import java.io.BufferedWriter;
import java.util.BitSet;
import java.util.concurrent.CyclicBarrier;

/**
 * Clase que ejecuta la función de evaluación del algoritmo genético que optimiza
 * la colonia de hormigas en un hilo aparte.
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class EvaluacionGen extends Thread {

    /**
     * Atributos (parámetros de las ejecuciones de la función de evaluación)
     */
    private BitSet[][] individuos;

    private double[] evaluaciones;

    private int primerElemento;

    private boolean intermedia;

    private BufferedWriter fSalida;

    private double[][] estadisticas;

    /**
     * Barrera en la que se tiene que quedar esperando
     */
    private CyclicBarrier barrera;

    /**
     * Constructor completo
     * @param ind
     * @param ev
     * @param primerEl
     * @param inter
     * @param fSal
     * @param est
     * @param bar
     */
    public EvaluacionGen(BitSet[][] ind, double[] ev, int primerEl, boolean inter,
            BufferedWriter fSal, double[][] est, CyclicBarrier bar) {
        super();
        individuos = ind;
        evaluaciones = ev;
        primerElemento = primerEl;
        intermedia = inter;
        fSalida = fSal;
        estadisticas = est;
        barrera = bar;
    }

    /**
     * Método que ejecuta la función de evaluación de los individuos del objeto
     */
    @Override
    public void run() {
        ejecutarEvaluaciones();
    }

    /**
     * Método que ejecuta las evaluaciones
     */
    public void ejecutarEvaluaciones() {

        // Se ejecuta la función de evaluación de un individuo detrás de otro
        // y al terminar se espera en la barrera
        for (int i = 0; i < individuos.length; i++) {
            evaluaciones[i + primerElemento] = GeneticoOptColH.fEvaluacion
                    (individuos[i], i + primerElemento, intermedia, fSalida,
                    estadisticas[i + primerElemento]);
        }
        try {
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
