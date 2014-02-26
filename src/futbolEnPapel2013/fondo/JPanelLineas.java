package futbolEnPapel2013.fondo;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Panel con líneas que se dibujan sobre el mismo
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class JPanelLineas extends javax.swing.JPanel {

    /** Creates new form JPanelLineas */
    public JPanelLineas() {
        initComponents();
    }

    /**
     * Método que pinta el panel y las líneas sobre el mismo
     * @param g
     * @param lineas
     */
    public void paint(Graphics g, ArrayList<Linea> lineas) {
        super.paint(g);

        for (int i = 0; i < lineas.size(); i++) {
                Linea l = lineas.get(i);
                g.setColor(l.getColor());
                //g.clearRect(l.getX1() - 1, l.getY1() - 1, l.getX2() + 1, l.getY2() + 1);
                g.drawLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(480, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
