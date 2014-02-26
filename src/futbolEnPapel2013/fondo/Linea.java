package futbolEnPapel2013.fondo;

import java.awt.Color;

/**
 * Clae que representa una línea del movimiento de un jugador
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Linea {

    /**
     * Coordenada x del primer punto
     */
    private int x1;

    /**
     * Coordenada y del primer punto
     */
    private int y1;

    /**
     * Coordenada x del segundo punto
     */
    private int x2;

    /**
     * Coordenada y del segundo punto
     */
    private int y2;

    /**
     * Color de la línea
     */
    private Color color;


    /**
     * Constructor completo
     * @param xP1 Coordenada x del primer punto
     * @param yP1 Coordenada y del primer punto
     * @param xP2 Coordenada x del segundo punto
     * @param yP2 Coordenada y del segundo punto
     * @param c Color de la línea
     */
    public Linea(int xP1, int yP1, int xP2, int yP2, Color c) {
        x1 = xP1;
        y1 = yP1;
        x2 = xP2;
        y2 = yP2;
        color = c;
    }

    /**
     * Método get del atributo color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Método get del atributo x1
     * @return x1
     */
    public int getX1() {
        return x1;
    }

    /**
     * Método get del atributo x2
     * @return x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * Método get del atributo y1
     * @return y1
     */
    public int getY1() {
        return y1;
    }

    /**
     * Método get del atributo y2
     * @return y2
     */
    public int getY2() {
        return y2;
    }

    /**
     * Método set del atributo color
     * @param color El nuevo color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Método set del atributo x1
     * @param x1 El nuevo x1
     */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * Método set del atributo x2
     * @param x2 El nuevo x2
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * Método set del atributo y1
     * @param y1 El nuevo y1
     */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * Método set del atributo y2
     * @param y2 El nuevo y2
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }


}
