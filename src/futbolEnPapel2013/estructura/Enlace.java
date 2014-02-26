package futbolEnPapel2013.estructura;

/**
 * Clase que representa los enlaces del tablero, es decir, los lados
 * de los cuadrados.
 * @author Martín
 * @version 1.0
 */
public class Enlace {

    /**
     * Variable booleana que almacena si el enlace ha sido accedido por algún
     * jugador o si es línea central o pared (true, en caso contrario, false)
     */
    private boolean marcado;

    /**
     * Costes del enlace por cada extremo en turnos. Los extremos se asignan
     * de la siguiente forma:
     * Arriba, arriba-izq, izq, abajo-izq: 0
     * Abajo, arriba-der, der, abajo-der: 1
     */
    private double[] coste;

    /**
     * Variable que indica la cantidad de feromona en el enlace
     */
    private double feromona;

    /**
     * Variable que indica la cantidad de feromona a añadir al enlace tras
     * la disipación
     */
    private double ferNueva;

    /**
     * Constructor por defecto que pone marcado a false
     */
    public Enlace() {
        marcado = false;
        coste = new double[2];
        coste[0] = 1;
        coste[1] = 1;
        feromona = 0;
        ferNueva = 0;
    }

    /**
     * Constructor con valor de marcado
     * @param valor El valor que se asignará a marcado
     */
    public Enlace(boolean valor) {
        marcado = valor;
        coste = new double[2];
        coste[0] = 1;
        coste[1] = 1;
        feromona = 0;
        ferNueva = 0;
    }

    /**
     * Método get del atributo marcado
     * @return marcado
     */
    public boolean getMarcado() {
        return marcado;
    }

    /**
     * Método set del atributo marcado
     * @param nuevo el valor nuevo que se asignará a marcado
     */
    public void setMarcado(boolean nuevo) {
        marcado = nuevo;
    }

    /**
     * Método get del atributo coste
     * @param indice El indice del coste
     * @return coste
     */
    public double getCoste(int indice) {
        return coste[indice];
    }

    /**
     * Método set del atributo coste
     * @param indice El índice del coste
     * @param nuevo El nuevo valor para el coste del enlace
     */
    public void setCoste(int indice, double nuevo) {
        coste[indice] = nuevo;
    }

    /**
     * Método get del atributo feromona
     * @return feromona
     */
    public double getFeromona() {
        return feromona;
    }

    /**
     * Método set del atributo feromona
     * @param fer El nuevo valor para la feromona
     */
    public void setFeromona(double fer) {
        feromona = fer;
    }

    /**
     * Método get del atributo ferNueva
     * @return ferNueva
     */
    public double getFerNueva() {
        return ferNueva;
    }

    /**
     * Método set del atributo ferNueva
     * @param nueva El nuevo valor para ferNueva
     */
    public void setFerNueva(double nueva) {
        ferNueva = nueva;
    }

    /**
     * Método que suma el parámetro a ferNueva
     * @param mas La cantidad de feromona a añadir a ferNueva
     */
    public void sumaFerNueva(double mas) {
        ferNueva += mas;
    }

    /**
     * Método equals, que compara si otro objeto Nodo es igual a este
     * @param otro El otro nodo a comparar
     * @return true si son iguales y false en caso contrario
     */
    @Override
    public boolean equals(Object otro) {

        if (otro == null) {
            return false;
        }

        if (otro.getClass() != this.getClass()) {
            return false;
        }

        Enlace otroEnlace = (Enlace) otro;

        if (otroEnlace.getMarcado() == marcado && otroEnlace.getCoste(0) == coste[0]
                && otroEnlace.getCoste(1) == coste[1]
                && otroEnlace.getFeromona() == feromona
                && otroEnlace.getFerNueva() == ferNueva) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.marcado ? 1 : 0);
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.coste[0]) ^ (Double.doubleToLongBits(this.coste[0]) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.coste[1]) ^ (Double.doubleToLongBits(this.coste[1]) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.feromona) ^ (Double.doubleToLongBits(this.feromona) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.ferNueva) ^ (Double.doubleToLongBits(this.ferNueva) >>> 32));
        return hash;
    }

    /**
     * Método que devuelve una instancia igual a la actual
     * @return enlace Una instancia igual a la actual
     */
    @Override
    public Enlace clone() {
        Enlace enlace = new Enlace(marcado);

        enlace.setCoste(0, coste[0]);
        enlace.setCoste(1, coste[1]);
        enlace.setFerNueva(ferNueva);
        enlace.setFeromona(feromona);

        return enlace;
    }
}
