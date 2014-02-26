package futbolEnPapel2013.fondo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * Clase que contiene una imagen y un lienzo para pintar sobre él
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class FondoConLienzo extends javax.swing.JPanel {

    /**
     * Imagen que se desea poner como fondo
     */
    private Image imagen;


    /** Creates new form FondoConLienzo */
    public FondoConLienzo() {
        initComponents();
    }

    /**
     * Constructor con la ruta de la imagen como parámetro
     * @param nombreImagen
     */
    public FondoConLienzo(String nombreImagen) {
        initComponents();
        if (nombreImagen != null) {
            imagen = new ImageIcon(getClass().getResource(nombreImagen)).
                    getImage();
        }
    }

    /**
     * Constructor con la imagen como parámetro
     * @param imagenInicial
     */
    public FondoConLienzo(Image imagenInicial) {
        initComponents();
        if (imagenInicial != null) {
            imagen = imagenInicial;
        }
    }

    /**
     * Método get del atributo lienzo
     * @return lienzo
     */
    public Lienzo getLienzo() {
        return lienzo;
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
     * Método que pinta la imagen
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

            setOpaque(false);
        } else {
            setOpaque(true);
        }

        super.paint(g);
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lienzo = new futbolEnPapel2013.fondo.Lienzo();

        setPreferredSize(new java.awt.Dimension(480, 600));

        lienzo.setBackground(new Color(0, 0, 0, 0));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lienzo, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lienzo, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private futbolEnPapel2013.fondo.Lienzo lienzo;
    // End of variables declaration//GEN-END:variables

}
