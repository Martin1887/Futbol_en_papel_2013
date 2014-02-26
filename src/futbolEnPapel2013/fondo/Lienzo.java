package futbolEnPapel2013.fondo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Clase para dibujar líneas
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Lienzo extends Canvas {

    /**
     * Lista con las líneas dibujadas en el tablero
     */
    private ArrayList<Linea> lineas;


    public Lienzo() {
        super();
        lineas = new ArrayList<Linea>();
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
     * Método que actualiza el tablero sin borrarlo
     * @param g
     */
    @Override
    public void update(Graphics g) {
        paint(g);
        for (int i = 0; i < lineas.size(); i++) {
            Linea l = lineas.get(i);
            g.setColor(l.getColor());
            g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        }
    }

    /**
     * Método que repinta dibujando las líneas que hay que pintar
     */
    @Override
    public void repaint() {
        paint(getGraphics());
        for (int i = 0; i < lineas.size(); i++) {
            Linea l = lineas.get(i);
            getGraphics().setColor(l.getColor());
            getGraphics().drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        }
    }
}
