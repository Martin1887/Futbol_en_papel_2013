package futbolEnPapel2013;

import futbolEnPapel2013.jugador.controladores.Controlador;
import futbolEnPapel2013.jugador.*;
import futbolEnPapel2013.estructura.Tablero;
import futbolEnPapel2013.estructura.Posicion;
import futbolEnPapel2013.fondo.FondoLienzo;
import futbolEnPapel2013.jugador.controladores.alfaBeta.AlfaBetaEstatico;
import futbolEnPapel2013.jugador.controladores.alfaBeta.AlfaBeta;
import futbolEnPapel2013.jugador.controladores.hormigas.ColoniaH;
import futbolEnPapel2013.jugador.controladores.hormigas.genetico.ColoniaHGen;
import java.awt.Color;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 * Clase que implementa el juego en sí
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Juego extends javax.swing.JDialog {

    /*
     * Constantes que indican los posibles jugadores
     */
    /**
     * Jugador humano
     */
    public static final int PERSONA = 0;
    /**
     * Jugador CPU
     */
    public static final int CPU = 1;

    /*
     * Constantes de los niveles que se corresponden con los controladores
     */
    /**
     * Colonia de hormigas
     */
    public static final int C_HORMIGAS = 11;
    /**
     * Colonia de hormigas de costes mínimos
     */
    public static final int C_HORMIGAS_COSTES_MIN = 12;
    /**
     * Colonia de hormigas genéticas
     */
    public static final int C_HORMIGAS_GEN = 13;
    /**
     * Constante usada para las hormigas genéticas cuando se están evaluando por
     * el algoritmo genético
     */
    public static final int C_HORMIGAS_GEN_EVAL = 14;
    /**
     * Constante usada para las hormigas que usan la distancia para dejar feromona
     */
    public static final int C_HORMIGAS_FER_DIST = 15;
    /**
     * Constante usada para las hormigas que usan la distancia para dejar feromona
     * genéticas
     */
    public static final int C_HORMIGAS_FER_DIST_GEN = 16;
    /**
     * Constante usada para las hormigas que usan la distancia para dejar feromona
     * genéticas cuando se están evaluando por el algoritmo genético
     */
    public static final int C_HORMIGAS_FER_DIST_GEN_EVAL = 17;
    /**
     * Constante usada para las hormigas que usan la distancia y además el mínimo
     * de la distancia por el coste a la portería propia para dejar feromona
     */
    public static final int C_HORMIGAS_FER_DIST_RIVAL = 18;
    /**
     * Constante usada para las hormigas que usan la distancia y además el mínimo
     * de la distancia por el coste a la portería propia para dejar feromona
     * genéticas
     */
    public static final int C_HORMIGAS_FER_DIST_RIVAL_GEN = 19;
    /**
     * Constante usada para las hormigas que usan la distancia y además el mínimo
     * de la distancia por el coste a la portería propia para dejar feromona
     * genéticas cuando se están evaluando por el algoritmo genético
     */
    public static final int C_HORMIGAS_FER_DIST_RIVAL_GEN_EVAL = 20;
    /**
     * Constante usada para el agente generado mediante programación genética
     */
    public static final int PROG_GEN = 21;
    /**
     * Constante usada para el agente de programación genética cuando se está
     * evaluando
     */
    public static final int PROG_GEN_EVAL = 22;

    /**
     * Variables para estadísticas
     */
    protected boolean ganaJug1;
    protected double minDistMetaJ1;
    protected double minDistMetaJ2;
    protected int turnosCampoJ1;
    protected int turnosCampoJ2;


    /**
     * Bucle principal del juego (se pone como atributo para que se elimine al
     * terminar el juego)
     */
    private BuclePrincipal bucle;
    
    /**
     * Variable que representa el tablero de juego
     */
    protected Tablero tablero;

    /**
     * Variable que representa la posición actual del balón en el tablero
     */
    protected Posicion actual;

    /**
     * Variable que indica el turnoJug1 (si es true es del J1 y si no del 2)
     * y siempre empieza el jugador 1
     */
    protected boolean turnoJug1 = true;

    /**
     * Variable que indica que el nivel del jugador 1
     */
    protected int nivel1;

    /**
     * Variable que indica que el nivel del jugador 2
     */
    protected int nivel2;

    /**
     * Variable que indica cuál es el jugador 1
     */
    protected Jugador jug1;

    /**
     * Variable que indica cuál es el jugador 2
     */
    protected Jugador jug2;

    /**
     * Variable que indica que el juego ha terminado (ha ganado alguien)
     */
    protected boolean fin;
   
    /**
     * Variable que apunta al padre (clase principal)
     */
    private FutbolEnPapel2013 padre;


    /**
     * Constructor para la programación genética
     */
  /*  public Juego(java.awt.Frame pariente, boolean modal, int i) throws Exception {
        super(pariente, modal);
        fin = false;
        padre = (FutbolEnPapel2013) pariente;

        progGen = true;

        ganaJug1 = false;
        minDistMetaJ1 = 9999;
        minDistMetaJ2 = 9999;
        turnosCampoJ1 = 0;
        turnosCampoJ2 = 0;
        progGen = false;

        if (padre.getTableroNegro() == true) {
            contenedor.setImagen("/recursos/tablero_negro.png");
        } else {
            contenedor.setImagen("/recursos/tablero_blanco.png");
        }

        tablero = new Tablero();

        nivel1 = padre.getNivel1();
        nivel2 = padre.getNivel2();

        // Creación de los jugadores
        Controlador controlador;
        switch (i) {
            case 0:
                controlador = new ProgGen0();
                break;
            case 1:
                controlador = new ProgGen1();
                break;
            case 2:
                controlador = new ProgGen2();
                break;
            case 3:
                controlador = new ProgGen3();
                break;
            case 4:
                controlador = new ProgGen4();
                break;
            case 5:
                controlador = new ProgGen5();
                break;
            case 6:
                controlador = new ProgGen6();
                break;
            case 7:
                controlador = new ProgGen7();
                break;
            case 8:
                controlador = new ProgGen8();
                break;
            case 9:
                controlador = new ProgGen9();
                break;
            case 10:
                controlador = new ProgGen10();
                break;
            case 11:
                controlador = new ProgGen11();
                break;
            case 12:
                controlador = new ProgGen12();
                break;
            case 13:
                controlador = new ProgGen13();
                break;
            case 14:
                controlador = new ProgGen14();
                break;
            case 15:
                controlador = new ProgGen15();
                break;
            case 16:
                controlador = new ProgGen16();
                break;
            case 17:
                controlador = new ProgGen17();
                break;
            case 18:
                controlador = new ProgGen18();
                break;
            case 19:
                controlador = new ProgGen19();
                break;
            case 20:
                controlador = new ProgGen20();
                break;
            case 21:
                controlador = new ProgGen21();
                break;
            case 22:
                controlador = new ProgGen22();
                break;
            case 23:
                controlador = new ProgGen23();
                break;
        }

        jug1 = new Jugador(this, controlador);


        Controlador controlador2;

        controlador2 = new AlfaBetaConCostes();


        jug2 = new Jugador(this, controlador2);


        tablero = new Tablero();

        /**
         * El juego comienza en el centro del campo
         */
  /*      actual = new Posicion(5, 8);

        setContentPane(contenedor);
        initComponents();

        // Se le da el color del jugador 1 a los gráficos (el primero en mover)
        jug1.setColor(new Color(0, 0, 187, 255));

        /**
         * Establecimiento del texto y visibilidad de los distintos JTextField
         */
  /*      turnoJ1.setVisible(true);
        turnoJ2.setVisible(false);
        // Sólo se pone el nivel si el jugador en cuestión es CPU
        if (padre.getJug1() != PERSONA) {
            textoNivel1.setVisible(true);
            textoNivel1.setText("Nivel del jugador 1:" + nivel1);
        } else {
            textoNivel1.setVisible(false);
        }

        if (padre.getJug2() != PERSONA) {
            textoNivel2.setVisible(true);
            textoNivel2.setText("Nivel del jugador 2:" + nivel2);
        } else {
            textoNivel2.setVisible(false);
        }
    }
*/

    /**
     * Constructor por defecto (para que se pueda tener un constructor sin JFrame
     * en JuegoObscuro)
     */
    public Juego() {}

    /**
     * Constructor
     */
    public Juego(JFrame parent, boolean modal) throws Exception {
        super(parent, modal);
        fin = false;
        padre = (FutbolEnPapel2013) parent;

        initComponents();

        ganaJug1 = false;
        minDistMetaJ1 = 9999;
        minDistMetaJ2 = 9999;
        turnosCampoJ1 = 0;
        turnosCampoJ2 = 0;

        if (padre.getTableroNegro() == true) {
            contenedor.setImagen("/recursos/tablero_negro.png");
        } else {
            contenedor.setImagen("/recursos/tablero_blanco.png");
        }

        tablero = new Tablero();

        nivel1 = padre.getNivel1();
        nivel2 = padre.getNivel2();

        /**
         * El juego comienza en el centro del campo
         */
        actual = new Posicion(5, 8);

        // Creación de los jugadores
        if (padre.getJug1() == PERSONA) {
            jug1 = new Jugador(this, null);
        } else {
            Controlador controlador;

            // Del 1 al 10 es alfa-beta, en caso contrario es otro controlador
            switch(nivel1) {
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
                case C_HORMIGAS:
                    controlador = new ColoniaH(this);
                    break;
                case C_HORMIGAS_GEN:
                    controlador = new ColoniaHGen(this);
                    break;
                default:
                    throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
            }

            jug1 = new Jugador(this, controlador);
        }

        if (padre.getJug2() == PERSONA) {
            jug2 = new Jugador(this, null);
        } else {
            Controlador controlador;

            // Del 1 al 10 es alfa-beta, en caso contrario es otro controlador
            switch(nivel2) {
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
                case C_HORMIGAS:
                    controlador = new ColoniaH(this);
                    break;
                case C_HORMIGAS_GEN:
                    controlador = new ColoniaHGen(this);
                    break;
                default:
                    throw new Exception("¡¡¡¡El nivel es incorrecto!!!!");
            }

            jug2 = new Jugador(this, controlador);
        }

        tablero = new Tablero();
        
        setContentPane(contenedor);


        // Se le da el color del jugador 1 a los gráficos (el primero en mover)
        //jug1.setColor(new Color(0, 0, 187, 255));

        /**
         * Establecimiento del texto y visibilidad de los distintos JTextField
         */
//        turnoJ1.setVisible(true);
//        turnoJ2.setVisible(false);
        // Sólo se pone el nivel si el jugador en cuestión es CPU
        if (padre.getJug1() != PERSONA) {
            textoNivel1.setVisible(true);
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
                    textoNivel1.setText("Nivel del jugador 1: Alfabeta " + nivel1);
                    break;
                case C_HORMIGAS:
                    textoNivel1.setText("Nivel del jugador 1: Colonia de hormigas");
                    break;
                case C_HORMIGAS_GEN:
                    textoNivel1.setText("Nivel del jugador 1: Colonia de hormigas gen.");
                    break;
            }
        } else {
            textoNivel1.setVisible(false);
        }

        if (padre.getJug2() != PERSONA) {
            textoNivel2.setVisible(true);
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
                    textoNivel2.setText("Nivel del jugador 2: Alfabeta " + nivel2);
                    break;
                case C_HORMIGAS:
                    textoNivel2.setText("Nivel del jugador 2: Colonia de hormigas");
                    break;
                case C_HORMIGAS_GEN:
                    textoNivel1.setText("Nivel del jugador 1: Colonia de hormigas gen.");
                    break;
            }
        } else {
            textoNivel2.setVisible(false);
        }

        //getLayeredPane().moveToBack(contenedor);
        //getLayeredPane().moveToFront(contenedor.getPanel());
        //setGlassPane(contenedor.getPanel());
        //getGlassPane().setVisible(true);
        //getLayeredPane().add(contenedor.getPanel(), 100);

        // Antiguo, en el caso de poner nombres
//        if (!dosJugadores) {
//            turnoJ1.setVisible(false);
//            turnoJ2.setVisible(false);
//            nombreJ1.setVisible(true);
//            nombreJ2.setVisible(true);
//            nombreJ2.setText("CPU");
//            turno1.setVisible(true);
//            // ******* Aparecen los dos turnos a la vez, para probar *********
//            turnoCPU.setVisible(true);
//            if (padre.getJug1() != null && padre.getJug1().length() > 0) {
//                nombreJ1.setText(String.valueOf(padre.getJug1().charAt(0)));
//            } else {
//                nombreJ1.setText("J1");
//            }
//            textoNivel1.setVisible(true);
//            textoNivel1.setText("Nivel: " + nivel1);
//        } else {
//            turnoJ1.setVisible(true);
//            if (padre.getJug1() != null && padre.getJug1().length() > 0) {
//                turnoJ1.setText("Turno de " + padre.getJug1());
//            } else {
//                turnoJ1.setText("Turno de J1");
//            }
//            // ******* Aparecen los dos turnos a la vez, para probar *********
//            turnoJ2.setVisible(true);
//            if (padre.getJug2() != null && padre.getJug2().length() > 0) {
//                turnoJ2.setText("Turno de " + padre.getJug2());
//            } else {
//                turnoJ2.setText("Turno de J2");
//            }
//            turno1.setVisible(false);
//            turnoCPU.setVisible(false);
//            nombreJ1.setVisible(true);
//            nombreJ2.setVisible(true);
//
//            if (padre.getJug2() != null && padre.getJug2().length() > 0) {
//                nombreJ2.setText(String.valueOf(padre.getJug2().charAt(0)));
//            } else {
//                nombreJ2.setText("J2");
//            }
//            if (padre.getJug1() != null && padre.getJug1().length() > 0) {
//                nombreJ1.setText(String.valueOf(padre.getJug1().charAt(0)));
//            } else {
//                nombreJ1.setText("J1");
//            }
//            textoNivel1.setVisible(false);
//        }
    }

    /**
     * Método is del atributo ganaJug1
     * @return ganaJug1
     */
    public boolean isGanaJug1() {
        return ganaJug1;
    }

    /**
     * Método set del atributo ganaJug1
     * @param ganaJug1
     */
    public void setGanaJug1(boolean ganaJug1) {
        this.ganaJug1 = ganaJug1;
    }

    /**
     * Método get del atributo minDistMetaJ1
     * @return minDistMetaJ1
     */
    public double getMinDistMetaJ1() {
        return minDistMetaJ1;
    }

    /**
     * Método set del atributo minDistMetaJ1
     * @param minDistMetaJ1
     */
    public void setMinDistMetaJ1(double minDistMetaJ1) {
        this.minDistMetaJ1 = minDistMetaJ1;
    }

    /**
     * Método get del atributo minDistMetaJ2
     * @return minDistMetaJ2
     */
    public double getMinDistMetaJ2() {
        return minDistMetaJ2;
    }

    /**
     * Método set del atributo minDistMetaJ2
     * @param minDistMetaJ2
     */
    public void setMinDistMetaJ2(double minDistMetaJ2) {
        this.minDistMetaJ2 = minDistMetaJ2;
    }

    /**
     * Método get del atributo turnosCampoJ1
     * @return turnosCampoJ1
     */
    public int getTurnosCampoJ1() {
        return turnosCampoJ1;
    }

    /**
     * Método set del atributo turnosCampoJ1
     * @param turnosCampoJ1
     */
    public void setTurnosCampoJ1(int turnosCampoJ1) {
        this.turnosCampoJ1 = turnosCampoJ1;
    }

    /**
     * Método get del atributo turnosCampoJ2
     * @return turnosCampoJ2
     */
    public int getTurnosCampoJ2() {
        return turnosCampoJ2;
    }

    /**
     * Método set del atributo turnosCampoJ2
     * @param turnosCampoJ2
     */
    public void setTurnosCampoJ2(int turnosCampoJ2) {
        this.turnosCampoJ2 = turnosCampoJ2;
    }

    /**
     * Método get del atributo contenedor
     * @return contenedor
     */
    public FondoLienzo getContenedor() {
        return contenedor;
    }

    /**
     * Método get del atributo tablero
     * @return tablero
     */
    public Tablero getTablero() {
        return tablero;
    }

    /**
     * Método get del atributo actual
     * @return actual
     */
    public Posicion getActual() {
        return actual;
    }

    /**
     * Método set del atributo actual
     * @param pos El nuevo valor de actual
     */
    public void setActual(Posicion pos) {
        actual = pos;
    }

    /**
     * Método get del atributo padre
     * @return padre
     */
    public FutbolEnPapel2013 getPadre() {
        return padre;
    }

    /**
     * Método get del atributo turnoJug1
     * @return turnoJug1
     */
    public boolean getTurnoJug1() {
        return turnoJug1;
    }
    
    /**
     * Método set del atributo turnoJug1. Necesario para BuclePrincipal
     * @param nuevo 
     */
    public void setTurnoJug1(boolean nuevo) {
        turnoJug1 = nuevo;
    }

    /**
     * Método set con la ruta como parámetro
     * @param nombreImagen
     */
    public void setImagen(String nombreImagen) {
        contenedor.setImagen(nombreImagen);
    }

    /**
     * Método set con la imagen como parámetro
     * @param nuevaImagen
     */
    public void setImagen(Image nuevaImagen) {
        contenedor.setImagen(nuevaImagen);
    }

    /**
     * Método get del jugador 1
     * @return jug1
     */
    public Jugador getJug1() {
        return jug1;
    }

    /**
     * Método set del jugador 1
     * @param nuevo
     */
    public void setJug1(Jugador nuevo) {
        jug1 = nuevo;
    }

    /**
     * Método get del jugador 2
     * @return jug2
     */
    public Jugador getJug2() {
        return jug2;
    }

    /**
     * Método set del jugador 2
     * @param nuevo
     */
    public void setJug2(Jugador nuevo) {
        jug2 = nuevo;
    }

    /**
     * Método get del nivel del jugador 1
     * @return nivel1
     */
    public int getNivel1() {
        return nivel1;
    }

    /**
     * Método set del nivel del jugador 1
     * @param nuevo
     */
    public void setNivel1(int nuevo) {
        nivel1 = nuevo;
    }

    /**
     * Método get del nivel del jugador 2
     * @return nivel2
     */
    public int getNivel2() {
        return nivel2;
    }

    /**
     * Método set del nivel del jugador 2
     * @param nuevo
     */
    public void setNivel2(int nuevo) {
        nivel2 = nuevo;
    }

    /**
     * Método get de la etiqueta del turno del jugador 1
     * @return turnoJ1
     */
    public JTextField getTurnoJ1() {
        return turnoJ1;
    }

    /**
     * Método get de la etiqueta del turno del jugador 2
     * @return turnoJ2
     */
    public JTextField getTurnoJ2() {
        return turnoJ2;
    }

    /**
     * Método get del atributo fin
     * @return fin
     */
    public boolean getFin() {
        return fin;
    }
    
    /**
     * Método que pone fin a true para terminar el juego
     */
    public void finalizar() {
        fin = true;
    }

    /**
     * Método que hace notifyAll para despertar al juego obscuro cuando está
     * en el wait
     */
    public synchronized void despertar() {
        try {
            notifyAll();
        } catch (Exception e){}
    }

    /**
     * Método que inicia el bucle principal
     */
    public void jugar() {
        bucle = new BuclePrincipal(this);
    }
    
    /**
     * Método que actualiza el tablero sin borrarlo
     * @param g
     */
 /*   @Override
    public void update(Graphics g) {
        getGraphics().drawLine(x1, y1, x2, y2);
    }
*/
    /**
     * Método que repinta dibujando la línea que hay que pintar
     */
/*    @Override
    public void repaint() {
        getGraphics().drawLine(x1, y1, x2, y2);
    }
*/
  

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contenedor = new futbolEnPapel2013.fondo.FondoLienzo(this);
        boton1 = new javax.swing.JButton();
        textoNivel1 = new javax.swing.JTextField();
        turnoJ1 = new javax.swing.JTextField();
        textoNivel2 = new javax.swing.JTextField();
        turnoJ2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Partido de Fútbol en papel 2013");
        setName("juego"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        contenedor.setPreferredSize(new java.awt.Dimension(468, 600));

        boton1.setFont(new java.awt.Font("Papyrus", 1, 24));
        boton1.setText("Volver al menú principal");
        boton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boton1MouseClicked(evt);
            }
        });

        textoNivel1.setBackground(new Color(0, 0, 0, 0));
        textoNivel1.setEditable(false);
        textoNivel1.setFont(new java.awt.Font("Comic Sans MS", 1, 15));
        textoNivel1.setForeground(new java.awt.Color(225, 187, 0));
        textoNivel1.setText("Nivel del jugador 1: ");
        textoNivel1.setBorder(null);

        turnoJ1.setBackground(new Color(0, 0, 0, 0));
        turnoJ1.setEditable(false);
        turnoJ1.setFont(new java.awt.Font("Comic Sans MS", 1, 15));
        turnoJ1.setForeground(new java.awt.Color(40, 40, 255));
        turnoJ1.setText("Turno del jugador 1");
        turnoJ1.setBorder(null);

        textoNivel2.setBackground(new Color(0, 0, 0, 0));
        textoNivel2.setEditable(false);
        textoNivel2.setFont(new java.awt.Font("Comic Sans MS", 1, 15));
        textoNivel2.setForeground(new java.awt.Color(0, 87, 0));
        textoNivel2.setText("Nivel del jugador 2: ");
        textoNivel2.setBorder(null);

        turnoJ2.setBackground(new Color(0, 0, 0, 0));
        turnoJ2.setEditable(false);
        turnoJ2.setFont(new java.awt.Font("Comic Sans MS", 1, 15));
        turnoJ2.setForeground(new java.awt.Color(187, 0, 0));
        turnoJ2.setText("Turno del jugador 2");
        turnoJ2.setBorder(null);

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorLayout.createSequentialGroup()
                .addContainerGap(91, Short.MAX_VALUE)
                .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(textoNivel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textoNivel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
            .addGroup(contenedorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(turnoJ1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
                .addComponent(turnoJ2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(boton1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contenedorLayout.createSequentialGroup()
                .addComponent(boton1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 440, Short.MAX_VALUE)
                .addComponent(textoNivel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textoNivel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turnoJ1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(turnoJ2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void boton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boton1MouseClicked
        // TODO add your handling code here:
        bucle.interrupt();
        bucle = null;
        if (jug1.getMover() != null) {
            jug1.matarMover();
        }
        if (jug2.getMover() != null) {
            jug2.matarMover();
        }
        System.gc();
        this.dispose();
}//GEN-LAST:event_boton1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        bucle.interrupt();
        bucle = null;
        if (jug1.getMover() != null) {
            jug1.matarMover();
        }
        if (jug2.getMover() != null) {
            jug2.matarMover();
        }
        System.gc();
    }//GEN-LAST:event_formWindowClosing



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton boton1;
    private futbolEnPapel2013.fondo.FondoLienzo contenedor;
    private javax.swing.JTextField textoNivel1;
    private javax.swing.JTextField textoNivel2;
    private javax.swing.JTextField turnoJ1;
    private javax.swing.JTextField turnoJ2;
    // End of variables declaration//GEN-END:variables

}
