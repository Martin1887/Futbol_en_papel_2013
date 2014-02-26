package futbolEnPapel2013.jugador.controladores.programacionGenetica.evolucion;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.Arbol;
import java.io.BufferedWriter;
import java.util.BitSet;
import java.util.concurrent.CyclicBarrier;

/**
 * Clase que ejecuta la función de evaluación de la programación genética
 * en un hilo aparte.
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Evaluacion extends Thread {

    /**
     * Atributos (parámetros de las ejecuciones de la función de evaluación)
     */
    private Arbol[] individuos;

    private double[] evaluaciones;

    private int primerElemento;

    private int generacion;

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
     * @param fSal
     * @param est
     * @param bar
     */
    public Evaluacion(Arbol[] ind, double[] ev, int primerEl, int gen,
            double[][] est, BufferedWriter fSal, CyclicBarrier bar) {
        super();
        individuos = ind;
        evaluaciones = ev;
        primerElemento = primerEl;
        generacion = gen;
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
            evaluaciones[i + primerElemento] = ProgramacionGen.fEvaluacion
                    (individuos[i], i + primerElemento, generacion,
                    estadisticas[i + primerElemento], fSalida);
        }
        try {
            barrera.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
