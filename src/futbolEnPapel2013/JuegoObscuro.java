package futbolEnPapel2013;

import futbolEnPapel2013.estructura.Posicion;
import futbolEnPapel2013.estructura.Tablero;
import futbolEnPapel2013.jugador.JugadorObscuro;
import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.jugador.controladores.alfaBeta.AlfaBeta;
import futbolEnPapel2013.jugador.controladores.alfaBeta.AlfaBetaEstatico;
import futbolEnPapel2013.jugador.controladores.alfaBeta.AlfaBetaSinCiclos;
import futbolEnPapel2013.jugador.controladores.hormigas.ColHFerDist;
import futbolEnPapel2013.jugador.controladores.hormigas.genetico.ColHFerDistGen;
import futbolEnPapel2013.jugador.controladores.hormigas.ColHFerDistRival;
import futbolEnPapel2013.jugador.controladores.hormigas.genetico.ColHFerDistRivalGen;
import futbolEnPapel2013.jugador.controladores.hormigas.ColoniaH;
import futbolEnPapel2013.jugador.controladores.hormigas.ColoniaHCostesMin;
import futbolEnPapel2013.jugador.controladores.hormigas.genetico.ColoniaHGen;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.ProgGen;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * Clase que se usa para iniciar partidas sin recursos gráficos y emitiendo
 * estadísticas en ficheros
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class JuegoObscuro extends Juego {

  
    /**
     * Variable que indica cuál es el jugador 1
     */
    private int tipoJug1;

    /**
     * Variable que indica cuál es el jugador 2
     */
    private int tipoJug2;

       

    /**
     * Constructor
     */
    public JuegoObscuro(int tipoJ1, int tipoJ2, int niv1, int niv2) throws Exception {

        setVisible(false);

        fin = false;


        ganaJug1 = false;
        minDistMetaJ1 = 9999;
        minDistMetaJ2 = 9999;
        turnosCampoJ1 = 0;
        turnosCampoJ2 = 0;


        tablero = new Tablero();

        nivel1 = niv1;
        nivel2 = niv2;
        tipoJug1 = tipoJ1;
        tipoJug2 = tipoJ2;

        /**
         * El juego comienza en el centro del campo
         */
        actual = new Posicion(5, 8);

    }


    /**
     * Método get del atributo del jugador 1
     * @return El tipo del jugador 1
     */
    public int getTipoJug1() {
        return tipoJug1;
    }

    /**
     * Método set del atributo del jugador 1
     * @param tipo
     */
    public void setTipoJug1(int tipo) {
        tipoJug1 = tipo;
    }

     /**
     * Método get del atributo del jugador 2
     * @return El tipo del jugador 2
     */
    public int getTipoJug2() {
        return tipoJug2;
    }

    /**
     * Método set del atributo del jugador 2
     * @param tipo
     */
    public void setTipoJug2(int tipo) {
        tipoJug2 = tipo;
    }

    /**
     * Método que inicia tantos partidos como se le envíen por parámetro y
     * devuelve e imprime en el fichero parámetro las estadísticas de las partidas.
     * Los partidos se hacen alternando el jugador que empieza primero pero las
     * estadísticas se rellenan como si hubiese empezado siempre el 1 para que
     * facilitar su comprensión
     * @param nPartidos El número de partidos a jugar
     * @param fSalida BufferedWriter donde se quiere imprimir los resultados
     * (sobrescribiendo el fichero)
     * @param individuo El individuo a evaluar en double[]
     * @param elemento El elemento del individuo dentro de la generación
     * @param intermedia Si la generación es intermedia
     * @return Las estadísticas de los partidos (en el último elemento la media
     * de todos los partidos)
     */
    public synchronized double[][] iniciar_partidos(int nPartidos, BufferedWriter fSalida,
            double[] individuo, int elemento, boolean intermedia) {
        /*
         * Tiene un elemento de más para guardar la media.
         * La segunda dimensión se refiere al número de estadísticas de cada
         * partido: ganaJug1, minDistMetaJ1, minDistanciaMetaJ2, turnosCampoJ1,
         * turnosCampoJ2, turnosTotales, evaluación
         */
        double[][] estadisticas = new double[nPartidos + 1][7];
        
        try {
            // Sólo se imprime esto si no se está evaluando un algoritmo genético
            if (individuo == null && elemento == -1) {
                fSalida.write("GanaJ1 (1/0)\tminDistMetaJ1\tminDistMetaJ2\t"
                    + "turnosCampoJ1\tturnosCampoJ2\tturnos totales\tevaluación"
                    + " (600*gana+40*minDistMetaJ1-40*minDistMetaJ2+2*porcentajeCampoJ2"
                    + "-5*turnosTotales si gana y +5*turnosTotales si pierde)");
                fSalida.flush();
            }


            // Se juegan todos los partidos y se almacenan las estadísticas
            for (int i = 0; i < nPartidos; i++) {
                // Si no es el primer partido se intercambian los niveles para
                // que en cada partido el primer jugador cambie (en los partidos
                // impares empezando en 1 el jugador 1 empieza primero y en los
                // pares empieza segundo)
                if (i > 0) {
                    int aux = nivel1;
                    nivel1 = nivel2;
                    nivel2 = aux;
                }


                // Se inicializan los valores para que el siguiente partido empiece
                // desde 0 (código del constructor)
                fin = false;


                ganaJug1 = false;
                minDistMetaJ1 = 9999;
                minDistMetaJ2 = 9999;
                turnosCampoJ1 = 0;
                turnosCampoJ2 = 0;


                tablero = new Tablero();


                /**
                 * El juego comienza en el centro del campo
                 */
                actual = new Posicion(5, 8);

                try {

                    // Creación de los jugadores
                    if (tipoJug1 == PERSONA) {
                        jug1 = new JugadorObscuro(this, null);
                    } else {
                        Controlador controlador;

                        // Del 1 al 10 es alfa-beta, en caso contrario es otro controlador
                        switch (nivel1) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                controlador = new AlfaBeta();
                                break;
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 50:
                                controlador = new AlfaBetaEstatico();
                                break;
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                            case 60:
                                controlador = new AlfaBetaSinCiclos();
                                break;
                            case C_HORMIGAS:
                                controlador = new ColoniaH(this);
                                break;
                            case C_HORMIGAS_COSTES_MIN:
                                controlador = new ColoniaHCostesMin(this);
                                break;
                            case C_HORMIGAS_GEN:
                                controlador = new ColoniaHGen(this);
                                break;
                            case C_HORMIGAS_GEN_EVAL:
                                controlador = new ColoniaHGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], elemento, intermedia);
                                break;
                            case C_HORMIGAS_FER_DIST:
                                controlador = new ColHFerDist(this);
                                break;
                            case C_HORMIGAS_FER_DIST_GEN:
                                controlador = new ColHFerDistGen(this);
                                break;
                            case C_HORMIGAS_FER_DIST_GEN_EVAL:
                                controlador = new ColHFerDistGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], individuo[6],
                                        individuo[7], individuo[8], elemento, intermedia);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL:
                                controlador = new ColHFerDistRival(this);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL_GEN:
                                controlador = new ColHFerDistRivalGen(this);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL_GEN_EVAL:
                                controlador = new ColHFerDistRivalGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], individuo[6],
                                        individuo[7], individuo[8], individuo[9], elemento, intermedia);
                                break;
                            case PROG_GEN:
                                controlador = new ProgGen(this);
                                break;
//                            case PROG_GEN_EVAL:
//                                switch (elemento) {
//                                    case 0:
//                                        controlador = new ProgGen0(this);
//                                        break;
//                                    case 1:
//                                        controlador = new ProgGen1(this);
//                                        break;
//                                    case 2:
//                                        controlador = new ProgGen2(this);
//                                        break;
//                                    case 3:
//                                        controlador = new ProgGen3(this);
//                                        break;
//                                    case 4:
//                                        controlador = new ProgGen4(this);
//                                        break;
//                                    case 5:
//                                        controlador = new ProgGen5(this);
//                                        break;
//                                    case 6:
//                                        controlador = new ProgGen6(this);
//                                        break;
//                                    case 7:
//                                        controlador = new ProgGen7(this);
//                                        break;
//                                    case 8:
//                                        controlador = new ProgGen8(this);
//                                        break;
//                                    case 9:
//                                        controlador = new ProgGen9(this);
//                                        break;
//                                    case 10:
//                                        controlador = new ProgGen10(this);
//                                        break;
//                                    case 11:
//                                        controlador = new ProgGen11(this);
//                                        break;
//                                    case 12:
//                                        controlador = new ProgGen12(this);
//                                        break;
//                                    case 13:
//                                        controlador = new ProgGen13(this);
//                                        break;
//                                    case 14:
//                                        controlador = new ProgGen14(this);
//                                        break;
//                                    case 15:
//                                        controlador = new ProgGen15(this);
//                                        break;
//                                    default:
//                                        // Esto nunca debería pasar
//                                        throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
//                                }
//                                break;
                            default:
                                throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
                        }

                        jug1 = new JugadorObscuro(this, controlador);
                    }

                    if (tipoJug2 == PERSONA) {
                        jug2 = new JugadorObscuro(this, null);
                    } else {
                        Controlador controlador;

                        // Del 1 al 10 es alfa-beta, en caso contrario es otro controlador
                        switch (nivel2) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                                controlador = new AlfaBeta();
                                break;
                            case 41:
                            case 42:
                            case 43:
                            case 44:
                            case 45:
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 50:
                                controlador = new AlfaBetaEstatico();
                                break;
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                            case 59:
                            case 60:
                                controlador = new AlfaBetaSinCiclos();
                                break;
                            case C_HORMIGAS:
                                controlador = new ColoniaH(this);
                                break;
                            case C_HORMIGAS_COSTES_MIN:
                                controlador = new ColoniaHCostesMin(this);
                                break;
                            case C_HORMIGAS_GEN:
                                controlador = new ColoniaHGen(this);
                                break;
                            case C_HORMIGAS_GEN_EVAL:
                                controlador = new ColoniaHGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], elemento, intermedia);
                                break;
                            case C_HORMIGAS_FER_DIST:
                                controlador = new ColHFerDist(this);
                                break;
                            case C_HORMIGAS_FER_DIST_GEN:
                                controlador = new ColHFerDistGen(this);
                                break;
                            case C_HORMIGAS_FER_DIST_GEN_EVAL:
                                controlador = new ColHFerDistGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], individuo[6],
                                        individuo[7], individuo[8], elemento, intermedia);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL:
                                controlador = new ColHFerDistRival(this);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL_GEN:
                                controlador = new ColHFerDistRivalGen(this);
                                break;
                            case C_HORMIGAS_FER_DIST_RIVAL_GEN_EVAL:
                                controlador = new ColHFerDistRivalGen(this, (int) individuo[0],
                                        (int) individuo[1], individuo[2], individuo[3],
                                        individuo[4], individuo[5], individuo[6],
                                        individuo[7], individuo[8], individuo[9], elemento, intermedia);
                                break;
                            case PROG_GEN:
                                controlador = new ProgGen(this);
                                break;
//                            case PROG_GEN_EVAL:
//                                switch (elemento) {
//                                    case 0:
//                                        controlador = new ProgGen0(this);
//                                        break;
//                                    case 1:
//                                        controlador = new ProgGen1(this);
//                                        break;
//                                    case 2:
//                                        controlador = new ProgGen2(this);
//                                        break;
//                                    case 3:
//                                        controlador = new ProgGen3(this);
//                                        break;
//                                    case 4:
//                                        controlador = new ProgGen4(this);
//                                        break;
//                                    case 5:
//                                        controlador = new ProgGen5(this);
//                                        break;
//                                    case 6:
//                                        controlador = new ProgGen6(this);
//                                        break;
//                                    case 7:
//                                        controlador = new ProgGen7(this);
//                                        break;
//                                    case 8:
//                                        controlador = new ProgGen8(this);
//                                        break;
//                                    case 9:
//                                        controlador = new ProgGen9(this);
//                                        break;
//                                    case 10:
//                                        controlador = new ProgGen10(this);
//                                        break;
//                                    case 11:
//                                        controlador = new ProgGen11(this);
//                                        break;
//                                    case 12:
//                                        controlador = new ProgGen12(this);
//                                        break;
//                                    case 13:
//                                        controlador = new ProgGen13(this);
//                                        break;
//                                    case 14:
//                                        controlador = new ProgGen14(this);
//                                        break;
//                                    case 15:
//                                        controlador = new ProgGen15(this);
//                                        break;
//                                    default:
//                                        // Esto nunca debería pasar
//                                        throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
//                                }
//                                break;
                            default:
                                throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
                        }

                        jug2 = new JugadorObscuro(this, controlador);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                turnoJug1 = true;

                // Se inicia el partido
                jugar();
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Si minDistMetaJ1 vale 9999 es que no se ha jugado bien
                if (minDistMetaJ1 == 9999) {
                    i--;
                    continue;
                }
                System.out.println("Terminado partido " + i);

                // Se almacenan las estadísticas en los elementos del array correspondientes
                // Si i es impar (partido par empezando en 1) el jugador 1 es el 2,
                // por lo cual hay que cambiar las estadísticas
                if (i % 2 == 0) {
                    estadisticas[i][0] = ganaJug1 ? 1 : 0;
                    estadisticas[i][1] = minDistMetaJ1;
                    estadisticas[i][2] = minDistMetaJ2;
                    estadisticas[i][3] = turnosCampoJ1;
                    estadisticas[i][4] = turnosCampoJ2;
                    estadisticas[i][5] = turnosCampoJ1 + turnosCampoJ2;
                    double evaluacion = 0;
                    if (ganaJug1) {
                        evaluacion += 600;
                    }

                    if (ganaJug1) {
                        evaluacion += minDistMetaJ1 * 40;
                    } else {
                        evaluacion -= minDistMetaJ2 * 40;
                    }

                    double porcentajeCampoJ2 = ((double) turnosCampoJ2
                            / ((double) turnosCampoJ1 + (double) turnosCampoJ2)) * 100;
                    evaluacion += porcentajeCampoJ2 * 2;

                    if (ganaJug1) {
                        evaluacion -= (turnosCampoJ1 + turnosCampoJ2) * 5;
                    } else {
                        evaluacion += (turnosCampoJ1 + turnosCampoJ2) * 5;
                    }
                    estadisticas[i][6] = evaluacion;
                } else {
                    estadisticas[i][0] = ganaJug1 ? 0 : 1;
                    estadisticas[i][1] = minDistMetaJ2;
                    estadisticas[i][2] = minDistMetaJ1;
                    estadisticas[i][3] = turnosCampoJ2;
                    estadisticas[i][4] = turnosCampoJ1;
                    estadisticas[i][5] = turnosCampoJ1 + turnosCampoJ2;
                    double evaluacion = 0;
                    if (!ganaJug1) {
                        evaluacion += 600;
                    }

                    if (!ganaJug1) {
                        evaluacion += minDistMetaJ2 * 40;
                    } else {
                        evaluacion -= minDistMetaJ1 * 40;
                    }

                    double porcentajeCampoJ1 = ((double) turnosCampoJ1
                            / ((double) turnosCampoJ1 + (double) turnosCampoJ2)) * 100;
                    evaluacion += porcentajeCampoJ1 * 2;

                    if (!ganaJug1) {
                        evaluacion -= (turnosCampoJ1 + turnosCampoJ2) * 5;
                    } else {
                        evaluacion += (turnosCampoJ1 + turnosCampoJ2) * 5;
                    }
                    estadisticas[i][6] = evaluacion;
                }

                // Se escribe la línea en el fichero
                // Sólo se imprimen las estadísticas si la evaluación no la hace
                // un algoritmo genético (si la hace un genético sólo se imprime
                // luego la media)
                if (individuo == null) {
                    fSalida.write("\n");
                    for (int j = 0; j < estadisticas[i].length; j++) {
                        fSalida.write(String.valueOf(estadisticas[i][j]) + "\t");
                    }
                    fSalida.flush();
                }
            }

            // Ahora se hace la media de los resultados y se pone en el último elemento
            double media0 = 0;
            double media1 = 0;
            double media2 = 0;
            double media3 = 0;
            double media4 = 0;
            double media5 = 0;
            double media6 = 0;
            for (int i = 0; i < estadisticas.length; i++) {
                media0 += estadisticas[i][0];
                media1 += estadisticas[i][1];
                media2 += estadisticas[i][2];
                media3 += estadisticas[i][3];
                media4 += estadisticas[i][4];
                media5 += estadisticas[i][5];
                media6 += estadisticas[i][6];
            }
            media0 /= nPartidos;
            media1 /= nPartidos;
            media2 /= nPartidos;
            media3 /= nPartidos;
            media4 /= nPartidos;
            media5 /= nPartidos;
            media6 /= nPartidos;

            estadisticas[nPartidos][0] = media0;
            estadisticas[nPartidos][1] = media1;
            estadisticas[nPartidos][2] = media2;
            estadisticas[nPartidos][3] = media3;
            estadisticas[nPartidos][4] = media4;
            estadisticas[nPartidos][5] = media5;
            estadisticas[nPartidos][6] = media6;

            // Ahora por último se imprime la media
            fSalida.write("\n");
            for (int j = 0; j < estadisticas[nPartidos].length; j++) {
                fSalida.write(String.valueOf(estadisticas[nPartidos][j]) + "\t");
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Sólo se cierra el fichero si la evaluación no la hace un
                // algoritmo genético
                if (individuo == null && elemento == -1) {
                    fSalida.close();
                } else {
                    fSalida.flush();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        return estadisticas;
    }
    
    /**
     * Método a partir del cual se instancia e inician partidas mediante esta
     * clase
     * @param args
     */
    public static void main(String[] args) throws Exception {
        int tipoJ1 = CPU;
        int tipoJ2 = CPU;
        int niv1 = C_HORMIGAS_FER_DIST_GEN;
        int niv2 = 2;

        JuegoObscuro juego = new JuegoObscuro(tipoJ1, tipoJ2, niv1, niv2);
        BufferedWriter escritor = null;
        try {
            escritor = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(new File("C:\\Users\\Martín\\Documents\\Martín"
                + "\\4º Grado en Ingeniería Informática Grupo 81"
                + "\\2º Cuatrimestre\\Trabajo Fin de Grado"
                + "\\evaluaciones\\algoritmos genéticos\\ferDist\\partidos_últimos\\HormigasFerDistGenVSAlfaBeta2.txt"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        juego.iniciar_partidos(100, escritor, null, -1, false);
    }

}
