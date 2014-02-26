package futbolEnPapel2013.fondo;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Clase que consiste en un Fondo con un Lienzo para poder dibujar líneas
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class FondoConLienzo2 extends Fondo {

    private Lienzo lienzo;

    public FondoConLienzo2(String nombreImagen) {
        super(nombreImagen);
        lienzo = new Lienzo();
        lienzo.setSize(getSize());
    }

    public FondoConLienzo2(Image imagenInicial) {
        super(imagenInicial);
        lienzo = new Lienzo();
        lienzo.setSize(getSize());
    }

    /**
     * Método get del atributo lienzo
     * @return lienzo
     */
    public Lienzo getLienzo() {
        return lienzo;
    }

    /**
     * Método set del atributo lienzo
     * @param nuevo El nuevo lienzo
     */
    public void setLienzo(Lienzo nuevo) {
        lienzo = nuevo;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }
}
