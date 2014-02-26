package futbolEnPapel2013;

import futbolEnPapel2013.fondo.Fondo;
import java.awt.*;


/**
 * Clase que implementa la ventana de opciones y sus acciones
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Opciones extends javax.swing.JDialog {

    /**
     * Variable que apunta al padre (clase principal)
     */
    private FutbolEnPapel2013 padre;

    /**
     * Contenedor con la imagen que se quiere poner como fondo
     */
    private final Fondo contenedor
            = new Fondo("/recursos/fondo.png");

    /** Constructor */
    public Opciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        padre = (FutbolEnPapel2013) parent;

        setContentPane(contenedor);
        initComponents();

         /*
         * A continuación se establecen los valores por defecto de los
         * jTextField y jRadioButton
         */

        // Si el color del tablero es blanco se selecciona este botón, y si no
        // se selecciona el negro
        if (padre.getTableroNegro() == false) {
            tableroBlanco.setSelected(true);
        } else {
            tableroNegro.setSelected(true);
        }

        // Se desactivan los radioButton de nivel si el controlador es persona
        // y se activan en caso contrario
        if (padre.getJug1() == Juego.PERSONA) {
            persona1.setSelected(true);
            
            nivel1.setEnabled(false);
            nivel2.setEnabled(false);
            nivelHormigas1.setEnabled(false);
            nivelHormigas2.setEnabled(false);
            nivelHormigas3.setEnabled(false);
//            nivel1.setForeground(new Color(187, 187, 187, 255)); Los 3 primeros parámetros a 87
//            nivel2.setForeground(new Color(187, 187, 187, 255));
//            nivel3.setForeground(new Color(187, 187, 187, 255));
//            nivel4.setForeground(new Color(187, 187, 187, 255));
//            nivel5.setForeground(new Color(187, 187, 187, 255));
//            nivel6.setForeground(new Color(187, 187, 187, 255));
//            nivel7.setForeground(new Color(187, 187, 187, 255));
//            nivel8.setForeground(new Color(187, 187, 187, 255));
//            nivel9.setForeground(new Color(187, 187, 187, 255));
//            nivel10.setForeground(new Color(187, 187, 187, 255));
            // Se selecciona el jRadioButton del nivel correspondiente
            switch (padre.getNivel1()) {
                case 1:
                    nivel1.setSelected(true);
                    break;
                case 2:
                    nivel2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_GEN:
                    nivelHormigas1.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_GEN:
                    nivelHormigas2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN:
                    nivelHormigas3.setSelected(true);
                    break;
            }
  
        } else {
            cpu1.setSelected(true);
 
            nivel1.setEnabled(true);
            nivel2.setEnabled(true);
            nivelHormigas1.setEnabled(true);
            nivelHormigas2.setEnabled(true);
            nivelHormigas3.setEnabled(true);
//            nivel1.setForeground(new Color(87, 87, 87, 255)); Los 3 primeros parámetros a 187
//            nivel2.setForeground(new Color(87, 87, 87, 255));
//            nivel3.setForeground(new Color(87, 87, 87, 255));
//            nivel4.setForeground(new Color(87, 87, 87, 255));
//            nivel5.setForeground(new Color(87, 87, 87, 255));
//            nivel6.setForeground(new Color(87, 87, 87, 255));
//            nivel7.setForeground(new Color(87, 87, 87, 255));
//            nivel8.setForeground(new Color(87, 87, 87, 255));
//            nivel9.setForeground(new Color(87, 87, 87, 255));
//            nivel10.setForeground(new Color(87, 87, 87, 255));
             // Se selecciona el jRadioButton del nivel correspondiente
            switch (padre.getNivel1()) {
                case 1:
                    nivel1.setSelected(true);
                    break;
                case 2:
                    nivel2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_GEN:
                    nivelHormigas1.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_GEN:
                    nivelHormigas2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN:
                    nivelHormigas3.setSelected(true);
                    break;
            }
  
        }

        // Se desactivan los radioButton de nivel si el controlador es persona
        // y se activan en caso contrario
        if (padre.getJug2() == Juego.PERSONA) {
            persona2.setSelected(true);

            nivelJ21.setEnabled(false);
            nivelJ22.setEnabled(false);
            nivelJ2Hormigas1.setEnabled(false);
            nivelJ2Hormigas2.setEnabled(false);
            nivelJ2Hormigas3.setEnabled(false);
//            nivel11.setForeground(new Color(187, 187, 187, 255)); Los 3 primeros parámetros a 87
//            nivel12.setForeground(new Color(187, 187, 187, 255));
//            nivel13.setForeground(new Color(187, 187, 187, 255));
//            nivel14.setForeground(new Color(187, 187, 187, 255));
//            nivel15.setForeground(new Color(187, 187, 187, 255));
//            nivel16.setForeground(new Color(187, 187, 187, 255));
//            nivel17.setForeground(new Color(187, 187, 187, 255));
//            nivel18.setForeground(new Color(187, 187, 187, 255));
//            nivel19.setForeground(new Color(187, 187, 187, 255));
//            nivel20.setForeground(new Color(187, 187, 187, 255));
            // Se selecciona el jRadioButton del nivel correspondiente
            switch (padre.getNivel2()) {
                case 1:
                    nivelJ21.setSelected(true);
                    break;
                case 2:
                    nivelJ22.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_GEN:
                    nivelJ2Hormigas1.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_GEN:
                    nivelJ2Hormigas2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN:
                    nivelJ2Hormigas3.setSelected(true);
                    break;
            }

        } else {
            cpu2.setSelected(true);

            nivelJ21.setEnabled(true);
            nivelJ22.setEnabled(true);
            nivelJ2Hormigas1.setEnabled(true);
            nivelJ2Hormigas2.setEnabled(true);
            nivelJ2Hormigas3.setEnabled(true);
//            nivel11.setForeground(new Color(87, 87, 87, 255)); Los 3 primeros parámetros a 187
//            nivel12.setForeground(new Color(87, 87, 87, 255));
//            nivel13.setForeground(new Color(87, 87, 87, 255));
//            nivel14.setForeground(new Color(87, 87, 87, 255));
//            nivel15.setForeground(new Color(87, 87, 87, 255));
//            nivel16.setForeground(new Color(87, 87, 87, 255));
//            nivel17.setForeground(new Color(87, 87, 87, 255));
//            nivel18.setForeground(new Color(87, 87, 87, 255));
//            nivel19.setForeground(new Color(87, 87, 87, 255));
//            nivel20.setForeground(new Color(87, 87, 87, 255));
             // Se selecciona el jRadioButton del nivel correspondiente
            switch (padre.getNivel2()) {
                case 1:
                    nivelJ21.setSelected(true);
                    break;
                case 2:
                    nivelJ22.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_GEN:
                    nivelJ2Hormigas1.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_GEN:
                    nivelJ2Hormigas2.setSelected(true);
                    break;
                case Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN:
                    nivelJ2Hormigas3.setSelected(true);
                    break;
            }

        }

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

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colorTablero = new javax.swing.ButtonGroup();
        nJugadores = new javax.swing.ButtonGroup();
        nivel = new javax.swing.ButtonGroup();
        nJugadores2 = new javax.swing.ButtonGroup();
        nivelJ2 = new javax.swing.ButtonGroup();
        tableroNegro = new javax.swing.JRadioButton();
        tableroBlanco = new javax.swing.JRadioButton();
        textoColorTablero = new javax.swing.JTextField();
        textoJugador1 = new javax.swing.JTextField();
        persona1 = new javax.swing.JRadioButton();
        cpu1 = new javax.swing.JRadioButton();
        textoNivel2 = new javax.swing.JTextField();
        nivel1 = new javax.swing.JRadioButton();
        nivel2 = new javax.swing.JRadioButton();
        botonCancelar = new javax.swing.JButton();
        botonAceptar = new javax.swing.JButton();
        nivelJ21 = new javax.swing.JRadioButton();
        nivelJ22 = new javax.swing.JRadioButton();
        textoNivel1 = new javax.swing.JTextField();
        persona2 = new javax.swing.JRadioButton();
        textoJugador2 = new javax.swing.JTextField();
        cpu2 = new javax.swing.JRadioButton();
        nivelHormigas1 = new javax.swing.JRadioButton();
        nivelJ2Hormigas1 = new javax.swing.JRadioButton();
        nivelHormigas2 = new javax.swing.JRadioButton();
        nivelHormigas3 = new javax.swing.JRadioButton();
        nivelJ2Hormigas2 = new javax.swing.JRadioButton();
        nivelJ2Hormigas3 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Opciones");
        setName("opciones"); // NOI18N
        setResizable(false);

        tableroNegro.setBackground(new Color(0, 0, 0, 0));
        colorTablero.add(tableroNegro);
        tableroNegro.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
        tableroNegro.setForeground(new java.awt.Color(187, 187, 187));
        tableroNegro.setText("Negro");
        tableroNegro.setBorder(null);
        tableroNegro.setContentAreaFilled(false);
        tableroNegro.setFocusPainted(false);

        tableroBlanco.setBackground(new Color(0, 0, 0, 0));
        colorTablero.add(tableroBlanco);
        tableroBlanco.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
        tableroBlanco.setForeground(new java.awt.Color(187, 187, 187));
        tableroBlanco.setText("Blanco");
        tableroBlanco.setBorder(null);
        tableroBlanco.setContentAreaFilled(false);
        tableroBlanco.setFocusPainted(false);

        textoColorTablero.setBackground(new Color(0, 0, 0, 0));
        textoColorTablero.setEditable(false);
        textoColorTablero.setFont(new java.awt.Font("Times New Roman", 0, 18));
        textoColorTablero.setForeground(new java.awt.Color(187, 187, 187));
        textoColorTablero.setText("Color del tablero de juego:");
        textoColorTablero.setBorder(null);

        textoJugador1.setBackground(new Color(0, 0, 0, 0));
        textoJugador1.setEditable(false);
        textoJugador1.setFont(new java.awt.Font("Times New Roman", 0, 18));
        textoJugador1.setForeground(new java.awt.Color(187, 187, 187));
        textoJugador1.setText("Jugador 1:");
        textoJugador1.setBorder(null);

        persona1.setBackground(new Color(0, 0, 0, 0));
        nJugadores.add(persona1);
        persona1.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
        persona1.setForeground(new java.awt.Color(187, 187, 187));
        persona1.setText("Persona");
        persona1.setBorder(null);
        persona1.setContentAreaFilled(false);
        persona1.setFocusPainted(false);
        persona1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                persona1MouseClicked(evt);
            }
        });

        cpu1.setBackground(new Color(0, 0, 0, 0));
        nJugadores.add(cpu1);
        cpu1.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
        cpu1.setForeground(new java.awt.Color(187, 187, 187));
        cpu1.setText("CPU");
        cpu1.setBorder(null);
        cpu1.setContentAreaFilled(false);
        cpu1.setFocusPainted(false);
        cpu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cpu1MouseClicked(evt);
            }
        });

        textoNivel2.setBackground(new Color(0, 0, 0, 0));
        textoNivel2.setEditable(false);
        textoNivel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        textoNivel2.setForeground(new java.awt.Color(187, 187, 187));
        textoNivel2.setText("Nivel de dificultad:");
        textoNivel2.setBorder(null);

        nivel1.setBackground(new Color(0, 0, 0, 0));
        nivel.add(nivel1);
        nivel1.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivel1.setForeground(new java.awt.Color(187, 187, 187));
        nivel1.setText("Alfa-beta profundidad 1");
        nivel1.setBorder(null);
        nivel1.setContentAreaFilled(false);
        nivel1.setFocusPainted(false);

        nivel2.setBackground(new Color(0, 0, 0, 0));
        nivel.add(nivel2);
        nivel2.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivel2.setForeground(new java.awt.Color(187, 187, 187));
        nivel2.setText("Alfa-beta profundidad 2");
        nivel2.setBorder(null);
        nivel2.setContentAreaFilled(false);
        nivel2.setFocusPainted(false);

        botonCancelar.setFont(new java.awt.Font("Papyrus", 1, 18));
        botonCancelar.setText("Cancelar");
        botonCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonCancelarMouseClicked(evt);
            }
        });

        botonAceptar.setFont(new java.awt.Font("Papyrus", 1, 18));
        botonAceptar.setText("Aceptar");
        botonAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonAceptarMouseClicked(evt);
            }
        });

        nivelJ21.setBackground(new Color(0, 0, 0, 0));
        nivelJ2.add(nivelJ21);
        nivelJ21.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelJ21.setForeground(new java.awt.Color(187, 187, 187));
        nivelJ21.setText("Alfa-beta profundidad 1");
        nivelJ21.setBorder(null);
        nivelJ21.setContentAreaFilled(false);
        nivelJ21.setFocusPainted(false);

        nivelJ22.setBackground(new Color(0, 0, 0, 0));
        nivelJ2.add(nivelJ22);
        nivelJ22.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelJ22.setForeground(new java.awt.Color(187, 187, 187));
        nivelJ22.setText("Alfa-beta profundidad 2");
        nivelJ22.setBorder(null);
        nivelJ22.setContentAreaFilled(false);
        nivelJ22.setFocusPainted(false);

        textoNivel1.setBackground(new Color(0, 0, 0, 0));
        textoNivel1.setEditable(false);
        textoNivel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        textoNivel1.setForeground(new java.awt.Color(187, 187, 187));
        textoNivel1.setText("Nivel de dificultad:");
        textoNivel1.setBorder(null);

        persona2.setBackground(new Color(0, 0, 0, 0));
        nJugadores2.add(persona2);
        persona2.setFont(new java.awt.Font("Comic Sans MS", 0, 15));
        persona2.setForeground(new java.awt.Color(187, 187, 187));
        persona2.setText("Persona");
        persona2.setBorder(null);
        persona2.setContentAreaFilled(false);
        persona2.setFocusPainted(false);
        persona2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                persona2MouseClicked(evt);
            }
        });

        textoJugador2.setBackground(new Color(0, 0, 0, 0));
        textoJugador2.setEditable(false);
        textoJugador2.setFont(new java.awt.Font("Times New Roman", 0, 18));
        textoJugador2.setForeground(new java.awt.Color(187, 187, 187));
        textoJugador2.setText("Jugador 2:");
        textoJugador2.setBorder(null);

        cpu2.setBackground(new Color(0, 0, 0, 0));
        nJugadores2.add(cpu2);
        cpu2.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        cpu2.setForeground(new java.awt.Color(187, 187, 187));
        cpu2.setText("CPU");
        cpu2.setBorder(null);
        cpu2.setContentAreaFilled(false);
        cpu2.setFocusPainted(false);
        cpu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cpu2MouseClicked(evt);
            }
        });

        nivelHormigas1.setBackground(new Color(0, 0, 0, 0));
        nivel.add(nivelHormigas1);
        nivelHormigas1.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelHormigas1.setForeground(new java.awt.Color(187, 187, 187));
        nivelHormigas1.setText("Colonia de hormigas");
        nivelHormigas1.setBorder(null);
        nivelHormigas1.setContentAreaFilled(false);
        nivelHormigas1.setFocusPainted(false);

        nivelJ2Hormigas1.setBackground(new Color(0, 0, 0, 0));
        nivelJ2.add(nivelJ2Hormigas1);
        nivelJ2Hormigas1.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelJ2Hormigas1.setForeground(new java.awt.Color(187, 187, 187));
        nivelJ2Hormigas1.setText("Colonia de hormigas");
        nivelJ2Hormigas1.setBorder(null);
        nivelJ2Hormigas1.setContentAreaFilled(false);
        nivelJ2Hormigas1.setFocusPainted(false);

        nivelHormigas2.setBackground(new Color(0, 0, 0, 0));
        nivel.add(nivelHormigas2);
        nivelHormigas2.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelHormigas2.setForeground(new java.awt.Color(187, 187, 187));
        nivelHormigas2.setText("Colonia de hormigas mejorada");
        nivelHormigas2.setBorder(null);
        nivelHormigas2.setContentAreaFilled(false);
        nivelHormigas2.setFocusPainted(false);

        nivelHormigas3.setBackground(new Color(0, 0, 0, 0));
        nivel.add(nivelHormigas3);
        nivelHormigas3.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelHormigas3.setForeground(new java.awt.Color(187, 187, 187));
        nivelHormigas3.setText("Colonia de hormigas suprema");
        nivelHormigas3.setBorder(null);
        nivelHormigas3.setContentAreaFilled(false);
        nivelHormigas3.setFocusPainted(false);

        nivelJ2Hormigas2.setBackground(new Color(0, 0, 0, 0));
        nivelJ2.add(nivelJ2Hormigas2);
        nivelJ2Hormigas2.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelJ2Hormigas2.setForeground(new java.awt.Color(187, 187, 187));
        nivelJ2Hormigas2.setText("Colonia de hormigas mejorada");
        nivelJ2Hormigas2.setBorder(null);
        nivelJ2Hormigas2.setContentAreaFilled(false);
        nivelJ2Hormigas2.setFocusPainted(false);

        nivelJ2Hormigas3.setBackground(new Color(0, 0, 0, 0));
        nivelJ2.add(nivelJ2Hormigas3);
        nivelJ2Hormigas3.setFont(new java.awt.Font("Comic Sans MS", 0, 15)); // NOI18N
        nivelJ2Hormigas3.setForeground(new java.awt.Color(187, 187, 187));
        nivelJ2Hormigas3.setText("Colonia de hormigas suprema");
        nivelJ2Hormigas3.setBorder(null);
        nivelJ2Hormigas3.setContentAreaFilled(false);
        nivelJ2Hormigas3.setFocusPainted(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textoColorTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(tableroBlanco)
                        .addGap(30, 30, 30)
                        .addComponent(tableroNegro))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textoJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(persona1)
                        .addGap(18, 18, 18)
                        .addComponent(cpu1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(botonAceptar)
                        .addGap(161, 161, 161)
                        .addComponent(botonCancelar))
                    .addComponent(textoNivel2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textoNivel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nivelJ21)
                            .addComponent(nivelJ2Hormigas1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nivelJ2Hormigas2)
                            .addComponent(nivelJ22)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nivelHormigas1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(nivelHormigas2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nivel1)
                        .addGap(43, 43, 43)
                        .addComponent(nivel2))
                    .addComponent(nivelHormigas3)
                    .addComponent(nivelJ2Hormigas3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(textoJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(persona2)
                        .addGap(18, 18, 18)
                        .addComponent(cpu2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoColorTablero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tableroBlanco)
                    .addComponent(tableroNegro))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoJugador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpu1)
                    .addComponent(persona1))
                .addGap(18, 18, 18)
                .addComponent(textoNivel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nivel1)
                    .addComponent(nivel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nivelHormigas1)
                    .addComponent(nivelHormigas2))
                .addGap(10, 10, 10)
                .addComponent(nivelHormigas3)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textoJugador2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpu2)
                    .addComponent(persona2))
                .addGap(18, 18, 18)
                .addComponent(textoNivel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nivelJ21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nivelJ2Hormigas1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nivelJ22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nivelJ2Hormigas2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nivelJ2Hormigas3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonCancelarMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
}//GEN-LAST:event_botonCancelarMouseClicked

    private void persona1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_persona1MouseClicked
        // TODO add your handling code here:

        /**
         * 'Desabilita' las características de la CPU
         */
        nivel1.setEnabled(false);
        nivel2.setEnabled(false);
        nivelHormigas1.setEnabled(false);
        nivelHormigas2.setEnabled(false);
        nivelHormigas3.setEnabled(false);
//        nivel1.setForeground(new Color(187, 187, 187, 255)); (a 87 los 3 primeros parámetros)
//        nivel2.setForeground(new Color(187, 187, 187, 255));
//        nivel3.setForeground(new Color(187, 187, 187, 255));
//        nivel4.setForeground(new Color(187, 187, 187, 255));
//        nivel5.setForeground(new Color(187, 187, 187, 255));
//        nivel6.setForeground(new Color(187, 187, 187, 255));
//        nivel7.setForeground(new Color(187, 187, 187, 255));
//        nivel8.setForeground(new Color(187, 187, 187, 255));
//        nivel9.setForeground(new Color(187, 187, 187, 255));
//        nivel10.setForeground(new Color(187, 187, 187, 255));
        
    }//GEN-LAST:event_persona1MouseClicked

    private void cpu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cpu1MouseClicked
        // TODO add your handling code here:

        /**
         * 'Habilita' las características de la CPU
         */
        nivel1.setEnabled(true);
        nivel2.setEnabled(true);
        nivelHormigas1.setEnabled(true);
        nivelHormigas2.setEnabled(true);
        nivelHormigas3.setEnabled(true);
//        nivel1.setForeground(new Color(87, 87, 87, 255)); (a 187 los 3 primeros parámetros)
//        nivel2.setForeground(new Color(87, 87, 87, 255));
//        nivel3.setForeground(new Color(87, 87, 87, 255));
//        nivel4.setForeground(new Color(87, 87, 87, 255));
//        nivel5.setForeground(new Color(87, 87, 87, 255));
//        nivel6.setForeground(new Color(87, 87, 87, 255));
//        nivel7.setForeground(new Color(87, 87, 87, 255));
//        nivel8.setForeground(new Color(87, 87, 87, 255));
//        nivel9.setForeground(new Color(87, 87, 87, 255));
//        nivel10.setForeground(new Color(87, 87, 87, 255));
        
    }//GEN-LAST:event_cpu1MouseClicked



    private void botonAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonAceptarMouseClicked
        // TODO add your handling code here:
        /**
         * Guarda los valores seleccionados en las variables del padre
         * (frame principal) y después se oculta
         */

        // tableroNegro
        if (tableroBlanco.isSelected()) {
            padre.setTableroNegro(false);
        } else {
            padre.setTableroNegro(true);
        }

        // jugadores
        if (persona1.isSelected()) {
            padre.setJug1(Juego.PERSONA);
        } else {
            padre.setJug1(Juego.CPU);
        }
        
        if (persona2.isSelected()) {
            padre.setJug2(Juego.PERSONA);
        } else {
            padre.setJug2(Juego.CPU);
        }

        // nivel del jugador 1
        if (nivel1.isSelected()) {
            padre.setNivel1(1);
        }
        if (nivel2.isSelected()) {
            padre.setNivel1(2);
        }
        
        if (nivelHormigas1.isSelected()) {
            padre.setNivel1(Juego.C_HORMIGAS_GEN);
        }
        if (nivelHormigas2.isSelected()) {
            padre.setNivel1(Juego.C_HORMIGAS_FER_DIST_GEN);
        }
        if (nivelHormigas3.isSelected()) {
            padre.setNivel1(Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN);
        }

        // nivel del jugador 2
        if (nivelJ21.isSelected()) {
            padre.setNivel2(1);
        }
        if (nivelJ22.isSelected()) {
            padre.setNivel2(2);
        }
        
        if (nivelJ2Hormigas1.isSelected()) {
            padre.setNivel2(Juego.C_HORMIGAS_GEN);
        }
        if (nivelJ2Hormigas2.isSelected()) {
            padre.setNivel2(Juego.C_HORMIGAS_FER_DIST_GEN);
        }
        if (nivelJ2Hormigas3.isSelected()) {
            padre.setNivel2(Juego.C_HORMIGAS_FER_DIST_RIVAL_GEN);
        }
        
        // Se oculta
        this.setVisible(false);
    }//GEN-LAST:event_botonAceptarMouseClicked

    private void persona2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_persona2MouseClicked
        // TODO add your handling code here:

        /**
         * 'Desabilita' las características de la CPU
         */
        nivelJ21.setEnabled(false);
        nivelJ22.setEnabled(false);
        nivelJ2Hormigas1.setEnabled(false);
        nivelJ2Hormigas2.setEnabled(false);
        nivelJ2Hormigas3.setEnabled(false);
//        nivel11.setForeground(new Color(187, 187, 187, 255)); (a 87 los 3 primeros parámetros)
//        nivel12.setForeground(new Color(187, 187, 187, 255));
//        nivel13.setForeground(new Color(187, 187, 187, 255));
//        nivel14.setForeground(new Color(187, 187, 187, 255));
//        nivel15.setForeground(new Color(187, 187, 187, 255));
//        nivel16.setForeground(new Color(187, 187, 187, 255));
//        nivel17.setForeground(new Color(187, 187, 187, 255));
//        nivel18.setForeground(new Color(187, 187, 187, 255));
//        nivel19.setForeground(new Color(187, 187, 187, 255));
//        nivel20.setForeground(new Color(187, 187, 187, 255));
        
    }//GEN-LAST:event_persona2MouseClicked

    private void cpu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cpu2MouseClicked
        // TODO add your handling code here:

        /**
         * 'Habilita' las características de la CPU
         */
        nivelJ21.setEnabled(true);
        nivelJ22.setEnabled(true);
        nivelJ2Hormigas1.setEnabled(true);
        nivelJ2Hormigas2.setEnabled(true);
        nivelJ2Hormigas3.setEnabled(true);
//        nivel11.setForeground(new Color(87, 87, 87, 255)); (a 187 los 3 primeros parámetros)
//        nivel12.setForeground(new Color(87, 87, 87, 255));
//        nivel13.setForeground(new Color(87, 87, 87, 255));
//        nivel14.setForeground(new Color(87, 87, 87, 255));
//        nivel15.setForeground(new Color(87, 87, 87, 255));
//        nivel16.setForeground(new Color(87, 87, 87, 255));
//        nivel17.setForeground(new Color(87, 87, 87, 255));
//        nivel18.setForeground(new Color(87, 87, 87, 255));
//        nivel19.setForeground(new Color(87, 87, 87, 255));
//        nivel20.setForeground(new Color(87, 87, 87, 255));
        
    }//GEN-LAST:event_cpu2MouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.ButtonGroup colorTablero;
    private javax.swing.JRadioButton cpu1;
    private javax.swing.JRadioButton cpu2;
    private javax.swing.ButtonGroup nJugadores;
    private javax.swing.ButtonGroup nJugadores2;
    private javax.swing.ButtonGroup nivel;
    private javax.swing.JRadioButton nivel1;
    private javax.swing.JRadioButton nivel2;
    private javax.swing.JRadioButton nivelHormigas1;
    private javax.swing.JRadioButton nivelHormigas2;
    private javax.swing.JRadioButton nivelHormigas3;
    private javax.swing.ButtonGroup nivelJ2;
    private javax.swing.JRadioButton nivelJ21;
    private javax.swing.JRadioButton nivelJ22;
    private javax.swing.JRadioButton nivelJ2Hormigas1;
    private javax.swing.JRadioButton nivelJ2Hormigas2;
    private javax.swing.JRadioButton nivelJ2Hormigas3;
    private javax.swing.JRadioButton persona1;
    private javax.swing.JRadioButton persona2;
    private javax.swing.JRadioButton tableroBlanco;
    private javax.swing.JRadioButton tableroNegro;
    private javax.swing.JTextField textoColorTablero;
    private javax.swing.JTextField textoJugador1;
    private javax.swing.JTextField textoJugador2;
    private javax.swing.JTextField textoNivel1;
    private javax.swing.JTextField textoNivel2;
    // End of variables declaration//GEN-END:variables

}
