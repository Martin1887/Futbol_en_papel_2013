package futbolEnPapel2013.fondo;

import futbolEnPapel2013.Juego;
import futbolEnPapel2013.jugador.Jugador;
import futbolEnPapel2013.jugador.controladores.Grafo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Clase que representa un panel donde se dibujan líneas
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class FondoLienzo extends javax.swing.JPanel {

    /**
     * Imagen que se desea poner como fondo
     */
    private Image imagen;
    
    /**
     * Imagen que se quiere poner sobre la anterior en la parte de arriba
     */
    private Image imagenArriba;
    
    /**
     * Imagen que se quiere poner sobre la anterior en la parte de abajo
     */
    private Image imagenAbajo;

    /**
     * Juego (para dibujar el balón en la posición actual)
     */
    private Juego juego;

    /**
     * Lista con las líneas dibujadas en el tablero
     */
    private ArrayList<Linea> lineas;


    /**
     * Constructor con sólo el juego
     * @param j El juego
     */
    public FondoLienzo(Juego j) {
        initComponents();
        lineas = new ArrayList<Linea>();
        juego = j;
        //setVisible(true);
    }

    /**
     * Constructor con la ruta de la imagen como parámetro
     * @param j El juego
     * @param nombreImagen
     */
    public FondoLienzo(Juego j, String nombreImagen) {
        initComponents();
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen)).
                    getImage();
        }
        lineas = new ArrayList<Linea>();
        juego = j;
        //setVisible(true);
    }

    /**
     * Constructor con la imagen como parámetro
     * @param j El juego
     * @param imagenInicial
     */
    public FondoLienzo(Juego j, Image imagenInicial) {
        initComponents();
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }
        lineas = new ArrayList<Linea>();
        juego = j;
        //setVisible(true);
    }

    /**
     * Método get del atributo panel
     * @return panel
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Método set con la ruta como parámetro
     * @param nombreImagen
     */
    public void setImagen(String nombreImagen) {
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen))
                    .getImage();
        } else {
            imagen = null;
        }

        repaint();
    }

    /**
     * Método set con la imagen como parámetro
     * @param nuevaImagen
     */
    public void setImagen(Image nuevaImagen) {
        imagen = nuevaImagen;

        repaint();
    }
    
    /**
     * Método que asigna la imagen de arriba
     * @param nombreImagen La ruta de la imagen
     */
    public void setImagenArriba(String nombreImagen) {
        if (nombreImagen != null) {
            imagenArriba = new ImageIcon(getClass().getResource(nombreImagen))
                    .getImage();
        } else {
            imagenArriba = null;
        }

        repaint();
    }
    
    
    public void setImagenAbajo(String nombreImagen) {
        if (nombreImagen != null) {
            imagenAbajo = new ImageIcon(getClass().getResource(nombreImagen))
                    .getImage();
        } else {
            imagenAbajo = null;
        }

        repaint();
    }

    /**
     * Método get del atributo lineas
     * @return lineas
     */
    public ArrayList<Linea> getLíneas() {
        return lineas;
    }

    /**
     * Método set del atributo lineas
     * @param lineas La nueva lista de lineas
     */
    public void setLíneas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }

    /**
     * Mëtodo que añade una línea a la lista de líneas
     * @param x1 Coordenada x del primer punto
     * @param y1 Coordenada y del primer punto
     * @param x2 Coordenada x del segundo punto
     * @param y2 Coordenada y del segundo punto
     * @param color Color de la línea
     */
    public void anyadeLinea(int x1, int y1, int x2, int y2, Color color) {
        lineas.add(new Linea(x1, y1, x2, y2, color));
    }

    /**
     * Método que pinta la imagen
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            //setOpaque(false);
        } else {
            setOpaque(true);
        }

        if (lineas != null) {
            for (int i = 0; i < lineas.size(); i++) {
                Linea l = lineas.get(i);
                g.setColor(l.getColor());

                /*
                 * Se dibujan tres líneas para que parezca una gruesa
                 */
                // Horizontal
                if (l.getY1() == l.getY2()) {
                    g.drawLine(l.getX1(), l.getY1() - 1, l.getX2(), l.getY2() - 1);
                    g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
                    g.drawLine(l.getX1(), l.getY1() + 1, l.getX2(), l.getY2() + 1);
                } else if (l.getX1() == l.getX2()) {
                    // Vertical
                    g.drawLine(l.getX1() - 1, l.getY1(), l.getX2() - 1, l.getY2());
                    g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
                    g.drawLine(l.getX1() + 1, l.getY1(), l.getX2() + 1, l.getY2());
                } else if (l.getX1() != l.getX2() && l.getY1() != l.getY2()) {
                    // Diagonal
                    g.drawLine(l.getX1() - 1, l.getY1() - 1, l.getX2() - 1, l.getY2() + 1);
                    g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
                    g.drawLine(l.getX1() + 1, l.getY1() - 1, l.getX2() + 1, l.getY2() + 1);
                }

            }
            //panel.repaint();
        }

        // Se dibuja el balón en la posición actual
        g.drawImage(new ImageIcon(getClass().getResource("/recursos/balon.png")).getImage(),
                juego.getActual().getX() * (Jugador.DIST_NODOS - 1) + Jugador.ANCHO_INI,
                juego.getActual().getY() * (Jugador.DIST_NODOS - 1) + Jugador.ALTO_INI, 10, 15, this);
        
        // Ahora se dibujan las imágenes de arriba y abajo si existen
        if (imagenArriba != null) {
            g.drawImage(imagenArriba, 40, 80, this);
        }
        if (imagenAbajo != null) {
            g.drawImage(imagenAbajo, 80, 220, this);
        }

        /*
         * Se dibuja la traza de la feromona
         */
//        boolean baseNegra = false;
//        if (juego.getPadre().getTableroNegro()) {
//            baseNegra = true;
//        }
//
//        // Se obtiene el grafo del jugador actual en caso de que tenga controlador
//        Grafo grafo = null;
//        if (juego.getTurnoJug1()) {
//            if (juego.getJug1().getControlador() != null) {
//                grafo = juego.getJug1().getControlador().grafo;
//            }
//        } else {
//            if (juego.getJug2().getControlador() != null) {
//                grafo = juego.getJug2().getControlador().grafo;
//            }
//        }
//
//        // Si el grafo es null no se hace nada más
//        if (grafo == null) {
//            super.paint(g);
//            return;
//        }
//
//        // Se recorren todos los enlaces del grafo y se pinta la feromona en el tablero
//        for (int i = 0; i < grafo.getGrafo().getEnlaces().length; i++) {
//            for (int j = 0; j < grafo.getGrafo().getEnlaces()[i].length; j++) {
//                for (int k = 0; k < grafo.getGrafo().getEnlaces()[i][j].length; k++) {
//                    // Si el enlace no existe se pasa al siguiente
//                    if (grafo.getGrafo().getEnlaces()[i][j][k] == null) {
//                        continue;
//                    }
//
//                    // Máxima cantidad de feromona en un enlace estimada
//                    int max = 255;
//
//                    double fer = grafo.getGrafo().getEnlaces()[i][j][k].getFerNueva()
//                            + grafo.getGrafo().getEnlaces()[i][j][k].getFeromona();
//
//                    // Color del rectángulo que se pintará en el enlace
//                    Color color = null;
//                    int verde = (int) (fer * 255 / max);
//                    if (verde > 255) {
//                        verde = 255;
//                    }
//                    if (baseNegra)  {
//                        color = new Color(0, verde, 0);
//                    } else {
//                        color = new Color(255 - verde, 255, 255 - (int) verde);
//                    }
//
//                    g.setColor(color);
//
//                    // Se calculan las coordenadas del rectángulo en base al enlace
//                    int x = 0;
//                    int y = 0;
//                    int[] pX = new int[4];
//                    int[] pY = new int[4];
//                    int grosor = 4;
//                    // La k indica la dirección del mismo
//                    switch (k) {
//                        case 0:
//                            // Izquierda
//                            x = Jugador.ANCHO_INI - Jugador.DIST_NODOS + i * Jugador.DIST_NODOS;
//                            y = Jugador.ALTO_INI + j * Jugador.DIST_NODOS - grosor / 2;
//                            g.drawRect(x, y, Jugador.DIST_NODOS, grosor);
//                            break;
//                        case 1:
//                            // Arriba
//                            x = Jugador.ANCHO_INI + i * Jugador.DIST_NODOS - grosor / 2;
//                            y = Jugador.ALTO_INI - Jugador.DIST_NODOS + j * Jugador.DIST_NODOS;
//                            g.drawRect(x, y, grosor, Jugador.DIST_NODOS);
//                            break;
//                        case 2:
//                            // Arriba-izquierda
//                            pX[0] = Jugador.ANCHO_INI - Jugador.DIST_NODOS + i * Jugador.DIST_NODOS - grosor / 2;
//                            pX[1] = Jugador.ANCHO_INI - Jugador.DIST_NODOS + i * Jugador.DIST_NODOS + grosor / 2;
//                            pX[2] = Jugador.ANCHO_INI + i * Jugador.DIST_NODOS + grosor / 2;
//                            pX[3] = Jugador.ANCHO_INI + i * Jugador.DIST_NODOS - grosor / 2;
//                            pY[0] = Jugador.ALTO_INI - Jugador.DIST_NODOS + j * Jugador.DIST_NODOS;
//                            pY[1] = Jugador.ALTO_INI - Jugador.DIST_NODOS + j * Jugador.DIST_NODOS;
//                            pY[2] = Jugador.ALTO_INI + j * Jugador.DIST_NODOS;
//                            pY[3] = Jugador.ALTO_INI + j * Jugador.DIST_NODOS;
//                            g.drawPolygon(pX, pY, 4);
//                            break;
//                        case 3:
//                            // Arriba-derecha
//                            pX[0] = Jugador.ANCHO_INI + i * Jugador.DIST_NODOS - grosor / 2;
//                            pX[1] = Jugador.ANCHO_INI + i * Jugador.DIST_NODOS + grosor / 2;
//                            pX[2] = Jugador.ANCHO_INI + Jugador.DIST_NODOS + i * Jugador.DIST_NODOS + grosor / 2;
//                            pX[3] = Jugador.ANCHO_INI + Jugador.DIST_NODOS + i * Jugador.DIST_NODOS - grosor / 2;
//                            pY[3] = Jugador.ALTO_INI - Jugador.DIST_NODOS + j * Jugador.DIST_NODOS;
//                            pY[2] = Jugador.ALTO_INI - Jugador.DIST_NODOS + j * Jugador.DIST_NODOS;
//                            pY[1] = Jugador.ALTO_INI + j * Jugador.DIST_NODOS;
//                            pY[0] = Jugador.ALTO_INI + j * Jugador.DIST_NODOS;
//                            g.drawPolygon(pX, pY, 4);
//                            break;
//                    }
//                }
//            }
//        }

        super.paint(g);
    }

//
//    /**
//     * Método que actualiza el tablero sin borrarlo
//     * @param g
//     */
//    @Override
//    public void update(Graphics g) {
//        paint(g);
//
//        for (int i = 0; i < lineas.size(); i++) {
//            Linea l = lineas.get(i);
//                g.setColor(l.getColor());
//
//                // Se dibujan tres líneas juntas para que parezca una línea gruesa
//                g.drawLine(l.getX1() - 1, l.getY1() - 1, l.getX2() - 1, l.getY2() - 1);
//                g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
//                g.drawLine(l.getX1() + 1, l.getY1() + 1, l.getX2() + 1, l.getY2() + 1);
//        }
//    }
//
//    /**
//     * Método que repinta dibujando las líneas que hay que pintar
//     */
//    @Override
//    public void repaint() {
//        Graphics g = getGraphics();
//        paint(g);
//        // Para evitar excepción en el caso de que se llame antes de terminar
//        // de construirlo
//        if (lineas != null) {
//            for (int i = 0; i < lineas.size(); i++) {
//                Linea l = lineas.get(i);
//                g.setColor(l.getColor());
//
//                // Se dibujan tres líneas juntas para que parezca una línea gruesa
//                g.drawLine(l.getX1() - 1, l.getY1() - 1, l.getX2() - 1, l.getY2() - 1);
//                g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
//                g.drawLine(l.getX1() + 1, l.getY1() + 1, l.getX2() + 1, l.getY2() + 1);
//            }
//            //panel.repaint();
//        }
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();

        setBackground(new Color(0, 0, 0, 0));

        panel.setBackground(new Color(0, 0, 0, 0));
        panel.setPreferredSize(new java.awt.Dimension(480, 600));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables

}
