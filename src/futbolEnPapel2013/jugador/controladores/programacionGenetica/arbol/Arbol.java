package futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol;

import java.util.Random;
import java.util.LinkedList;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.flujo.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.aritmeticos.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operadores.logicos.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.finales.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.numeros.*;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.operandos.*;

/**
 * Clase que representa un árbol de programación genética, es decir, el
 * código de un controlador en forma de árbol
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class Arbol {

    /**
     * Nodo raíz del árbol (un bloque de código)
     */
    private Bloque raiz;

    /**
     * Componente aleatorio
     */
    private Random aleat;


    /**
     * Constructor por defecto. Construye el árbol aleatoriamente.
     */
    public Arbol() {
        aleat = new Random(System.currentTimeMillis());

        // Se escoge una cardinalidad aleatoria para la raíz entre 1 y 7
        // (para que el árbol no sea excesivamente grande)
        raiz.setCardinalidad(aleat.nextInt(7) + 1);

        // Array auxiliar para almacenar los hijos de la raíz
        NodoA[] hijos = new NodoA[raiz.getCardinalidad()];

        // Se rellena el bloque
        rellenaBloque(hijos);

        // Una vez asignados los hijos se asignan a raiz
        raiz.setHijos(hijos);

        // Ahora se asignan los hijos de todos los hijos de la raíz menos el
        // último (que no tiene hijos)
        for (int i = 0; i < raiz.getCardinalidad() - 1; i++) {
            // A partir de la cardinalidad se sabe si es If o IfElse
            Operador op = (Operador) raiz.getHijos()[i];

            if (op.getCardinalidad() == 2) {
                // If
                hijos = new NodoA[2];
                rellenaIf(hijos);

                If iff = (If) op;
                iff.setHijos(hijos);
            } else {
                // Else
                hijos = new NodoA[3];
                rellenaIfElse(hijos);

                IfElse ifElse = (IfElse) op;
                ifElse.setHijos(hijos);
            }
        }

    }

    /**
     * Método que rellena los hijos de una expresión
     * @param hijos
     */
    public void rellenaExpresion(NodoA[] hijos) {
        // Sólo un hijo que puede ser operando no final (10 más los números),
        // producto, suma o resta
        int n = aleat.nextInt(14);

        // Para si es un operador aritmético
        NodoA[] nodos;

        switch (n) {
            case 0:
                // DistCentro
                hijos[0] = new DistCentro();
                break;
            case 1:
                // DistMeta
                hijos[0] = new DistMeta();
                break;
            case 2:
                // DistMiMeta
                hijos[0] = new DistMiMeta();
                break;
            case 3:
                // DistParedDer
                hijos[0] = new DistParedDer();
                break;
            case 4:
                // DistParedIzq
                hijos[0] = new DistParedIzq();
                break;
            case 5:
                // NEnlacesM
                hijos[0] = new NEnlacesM();
                break;
            case 6:
                // NEnlacesMAba
                hijos[0] = new NEnlacesMAba();
                break;
            case 7:
                // NEnlacesMArr
                hijos[0] = new NEnlacesMArr();
                break;
            case 8:
                // NEnlacesMDer
                hijos[0] = new NEnlacesMDer();
                break;
            case 9:
                // NEnlacesMIzq
                hijos[0] = new NEnlacesMIzq();
                break;
            case 10:
                // Número (hay 9 posibles)
                int m = aleat.nextInt(9);
                switch (m) {
                    case 0:
                        // -4
                        hijos[0] = new NumeroM4();
                        break;
                    case 1:
                        // -3
                        hijos[0] = new NumeroM3();
                        break;
                    case 2:
                        // -2
                        hijos[0] = new NumeroM2();
                        break;
                    case 3:
                        // -1
                        hijos[0] = new NumeroM1();
                        break;
                    case 4:
                        // 0
                        hijos[0] = new Numero0();
                        break;
                    case 5:
                        // 1
                        hijos[0] = new Numero1();
                        break;
                    case 6:
                        // 2
                        hijos[0] = new Numero2();
                        break;
                    case 7:
                        // 3
                        hijos[0] = new Numero3();
                        break;
                    case 8:
                        // 4
                        hijos[0] = new Numero4();
                        break;
                }
                break;
            case 11:
                // Suma
                hijos[0] = new Suma();
                // Los op. aritméticos tienen cardinalidad 2
                nodos = new NodoA[2];
                rellenaOp(nodos);
                Suma s = (Suma) hijos[0];
                s.setHijos(nodos);
                break;
            case 12:
                // Resta
                hijos[0] = new Resta();
                // Los op. aritméticos tienen cardinalidad 2
                nodos = new NodoA[2];
                rellenaOp(nodos);
                Resta r = (Resta) hijos[0];
                r.setHijos(nodos);
                break;
            case 13:
                // Producto
                hijos[0] = new Producto();
                // Los op. aritméticos tienen cardinalidad 2
                nodos = new NodoA[2];
                rellenaOp(nodos);
                Producto p = (Producto) hijos[0];
                p.setHijos(nodos);
                break;
        }
    }

    /**
     * Método que rellena los hijos de un operador lógico o aritmético
     * @param hijos
     */
    public void rellenaOp(NodoA[] hijos) {
        // 2 expresiones
        NodoA[] nodos = new NodoA[1];
        rellenaExpresion(nodos);
        Operador op = (Operador) hijos[0];
        op.setHijos(nodos);

        nodos = new NodoA[1];
        rellenaExpresion(nodos);
        op = (Operador) hijos[1];
        op.setHijos(nodos);
    }

    /**
     * Método que rellena los hijos de un if
     * @param hijos El array en el que colocar los hijos
     */
    public void rellenaIf(NodoA[] hijos) {
        // El primer hijo es un op. lógico y el segundo un bloque

        // Del 0 al 3
        int hijo = aleat.nextInt(4);

        switch (hijo) {
            case 0:
                hijos[0] = new Igual();
                break;
            case 1:
                hijos[0] = new NoIgual();
                break;
            case 2:
                hijos[0] = new Menor();
                break;
            case 3:
                hijos[0] = new Mayor();
                break;
            default:
                hijos[0] = new Igual();
                break;
        }

        // Los lógicos tienen cardinalidad 2
        NodoA[] nodos = new NodoA[2];
        rellenaOp(nodos);
        Operador op = (Operador) hijos[0];
        op.setHijos(nodos);

        // Ahora el bloque. Primero se elige la cardinalidad y luego se
        // rellena. La cardinalidad máxima es 4 y la mínima 1
        int card = aleat.nextInt(4) + 1;
        nodos = new NodoA[card];
        Bloque b = (Bloque) raiz.getHijos()[1];
        b.setCardinalidad(card);
        rellenaBloque(nodos);
        b.setHijos(nodos);
    }

    /**
     * Método que rellena los hijos de un ifElse
     * @param hijos El array en el que colocar los hijos
     */
    public void rellenaIfElse(NodoA[] hijos) {
        // El primer hijos es un op. lógico y los otros un bloque

        // Del 0 al 3
        int hijo = aleat.nextInt(4);

        switch (hijo) {
            case 0:
                hijos[0] = new Igual();
                break;
            case 1:
                hijos[0] = new NoIgual();
                break;
            case 2:
                hijos[0] = new Menor();
                break;
            case 3:
                hijos[0] = new Mayor();
                break;
            default:
                hijos[0] = new Igual();
                break;
        }

        // Los lógicos tienen cardinalidad 2
        NodoA[] nodos = new NodoA[2];
        rellenaOp(nodos);
        Operador op = (Operador) hijos[0];
        op.setHijos(nodos);

        // Ahora los bloques. Primero se elige la cardinalidad y luego se
        // rellenan. La cardinalidad máxima es 4 y la mínima 1
        int card = aleat.nextInt(4) + 1;
        nodos = new NodoA[card];
        Bloque b = (Bloque) raiz.getHijos()[1];
        b.setCardinalidad(card);
        rellenaBloque(nodos);
        b.setHijos(nodos);

        // Ahora el segundo bloque
        card = aleat.nextInt(4) + 1;
        nodos = new NodoA[card];
        b = (Bloque) raiz.getHijos()[2];
        b.setCardinalidad(card);
        rellenaBloque(nodos);
        b.setHijos(nodos);
    }

    /**
     * Método que rellena los hijos de un bloque
     * @param hijos El array en el que colocar los hijos
     */
    public void rellenaBloque(NodoA[] hijos) {
        // Se da valor a los hijos, teniendo en cuenta que sólo el último debe
        // ser final y los demás deben ser if o if-else
        for (int i = 0; i < hijos.length; i++) {
            if (i < hijos.length - 1) {
                // O If o IfElse
                int hijo = aleat.nextInt(2);

                switch (hijo) {
                    case 0:
                        hijos[i] = new If();
                        break;
                    case 1:
                        hijos[i] = new IfElse();
                        break;
                }

            } else {
                // Del 0 al 7 (hay 8 clases finales)
                int hijo = aleat.nextInt(8);

                switch (hijo) {
                    case 0:
                        hijos[i] = new MoverIzq();
                        break;
                    case 1:
                        hijos[i] = new MoverDer();
                        break;
                    case 2:
                        hijos[i] = new MoverArr();
                        break;
                    case 3:
                        hijos[i] = new MoverAba();
                        break;
                    case 4:
                        hijos[i] = new MoverArI();
                        break;
                    case 5:
                        hijos[i] = new MoverArD();
                        break;
                    case 6:
                        hijos[i] = new MoverAbI();
                        break;
                    case 7:
                        hijos[i] = new MoverAbD();
                        break;
                }
            }
        }
    }

    /**
     * Método que devuelve el NodoA que está en la posición n según amplitud
     * @param n Posición del NodoA que se quiere obtener
     * @return nodo El NodoA que está en la posición n según amplitud o null si no se ha encontrado
     */
    public NodoA obtenerNodoEnAmplitud(int n) {
        NodoA nodo = null;

        // Si n es 0 se devuelve null
        if (n == 0) {
            return null;
        }

        // Variable usada para contar el nodo máximo actual
        int max = 0;

        LinkedList<NodoA> abierta = new LinkedList<NodoA>();
        abierta.addLast(raiz);
        max++;

        // Si es la raíz
        if (max == n) {
            return raiz;
        }

        boolean exito = false;

        while (abierta.isEmpty() || exito) {
            nodo = abierta.getFirst();
            abierta.removeFirst();

            // Si nodo tiene sucesores
            if (nodo instanceof Operador) {
                Operador op = (Operador) nodo;
                NodoA[] hijos = op.getHijos();
                max += hijos.length;
                // Si algún hijo es el buscado exito = true
                if (n <= max) {
                    exito = true;
                    nodo = hijos[hijos.length - 1 - (max - n)];
                } else {
                    // Si no se añaden los hijos al final de abierta
                    for (int i = 0; i < hijos.length; i++) {
                        abierta.addLast(hijos[i]);
                    }
                }
            }
        }

        // Si ha exito nodo ya contiene al nodoA buscado, si no se devuelve null
        if (!exito) {
            nodo = null;
        }

        return nodo;
    }

    /**
     * Método que devuelve un NodoA aleatorio del tipo especificado por parámetro
     * @param clase La clase de la cual se quiere obtener el NodoA
     * @return nodo Un NodoA aleatorio de la clase especificada o null si no se ha encontrado
     */
    public NodoA obtenerNodoAleatPorTipo(String clase) {
        NodoA nodo = null;

        // Se busca durante 87 iteraciones, y si en éstas no se ha obtenido
        // ninguno del tipo especificado se supone que no hay y se devuelve null
        for (int i = 0; i < 87; i++) {
            // La raíz es el nodo 1
            int n = aleat.nextInt(nNodos()) + 1;

            nodo = obtenerNodoEnAmplitud(n);

            // Si el nodo es de la clase buscada se termina el bucle, si no se
            // sigue buscando
            if (obtenerClase(nodo).equals(clase)) {
                break;
            }
        }

        return nodo;
    }

    /**
     * Método que devuelve el número de nodos del árbol.
     * Para ello primero se le suma a 1 la cardinalidad de la raíz y después
     * se le suma a eso la cardinalidad de cada uno de los hijos de la raíz
     * @return El número de nodos del árbol
     */
    public int nNodos() {
        int n = 1;

        n += raiz.getCardinalidad();

        // Hasta cardinalidad - 2 porque el último hijo es operando final
        for (int i = 0; i < raiz.getCardinalidad() - 1; i++) {
            Operador op = (Operador) raiz.getHijos()[i];
            n += op.getCardinalidad();
        }

        return n;
    }

    /**
     * Método que obtiene la clase final de un NodoA intentando hacer casting
     * a cada uno de los posibles tipos
     * @param nodo El NodoA cuya clase se desea saber
     * @return La clase final de nodo o null si no se encuentra
     */
    public String obtenerClase(NodoA nodo) {
        String clase = null;

        boolean fin = false;

        // Hay 38 clases posibles
        for (int i = 0; i < 38 && !fin; i++) {
            try {
                switch (i) {
                    case 0:
                        Expresion e = (Expresion) nodo;
                        clase = e.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 1:
                        Producto p = (Producto) nodo;
                        clase = p.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 2:
                        Resta r = (Resta) nodo;
                        clase = r.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 3:
                        Suma s = (Suma) nodo;
                        clase = s.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 4:
                        Bloque b = (Bloque) nodo;
                        clase = b.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 5:
                        If iff = (If) nodo;
                        clase = iff.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 6:
                        IfElse iE = (IfElse) nodo;
                        clase = iE.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 7:
                        Igual ig = (Igual) nodo;
                        clase = ig.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 8:
                        Mayor ma = (Mayor) nodo;
                        clase = ma.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 9:
                        Menor me = (Menor) nodo;
                        clase = me.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 10:
                        NoIgual nI = (NoIgual) nodo;
                        clase = nI.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 11:
                        MoverAbD mAD = (MoverAbD) nodo;
                        clase = mAD.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 12:
                        MoverAbI mAI = (MoverAbI) nodo;
                        clase = mAI.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 13:
                        MoverAba mAb = (MoverAba) nodo;
                        clase = mAb.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 14:
                        MoverArD mArD = (MoverArD) nodo;
                        clase = mArD.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 15:
                        MoverArI mArI = (MoverArI) nodo;
                        clase = mArI.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 16:
                        MoverArr mAr = (MoverArr) nodo;
                        clase = mAr.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 17:
                        MoverIzq mI = (MoverIzq) nodo;
                        clase = mI.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 18:
                        MoverDer mD = (MoverDer) nodo;
                        clase = mD.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 19:
                        Numero0 n0 = (Numero0) nodo;
                        clase = n0.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 20:
                        Numero1 n1 = (Numero1) nodo;
                        clase = n1.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 21:
                        Numero2 n2 = (Numero2) nodo;
                        clase = n2.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 22:
                        Numero3 n3 = (Numero3) nodo;
                        clase = n3.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 23:
                        Numero4 n4 = (Numero4) nodo;
                        clase = n4.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 24:
                        NumeroM1 nM1 = (NumeroM1) nodo;
                        clase = nM1.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 25:
                        NumeroM2 nM2 = (NumeroM2) nodo;
                        clase = nM2.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 26:
                        NumeroM3 nM3 = (NumeroM3) nodo;
                        clase = nM3.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 27:
                        NumeroM4 nM4 = (NumeroM4) nodo;
                        clase = nM4.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 28:
                        DistCentro dC = (DistCentro) nodo;
                        clase = dC.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 29:
                        DistMeta dM = (DistMeta) nodo;
                        clase = dM.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 30:
                        DistMiMeta dMM = (DistMiMeta) nodo;
                        clase = dMM.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 31:
                        DistParedDer dPD = (DistParedDer) nodo;
                        clase = dPD.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 32:
                        DistParedIzq dPI = (DistParedIzq) nodo;
                        clase = dPI.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 33:
                        NEnlacesM nEM = (NEnlacesM) nodo;
                        clase = nEM.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 34:
                        NEnlacesMAba nEMAb = (NEnlacesMAba) nodo;
                        clase = nEMAb.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 35:
                        NEnlacesMArr nEMAr = (NEnlacesMArr) nodo;
                        clase = nEMAr.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 36:
                        NEnlacesMDer nEMD = (NEnlacesMDer) nodo;
                        clase = nEMD.getClass().getSimpleName();
                        fin = true;
                        break;
                    case 37:
                        NEnlacesMIzq nEMI = (NEnlacesMIzq) nodo;
                        clase = nEMI.getClass().getSimpleName();
                        fin = true;
                        break;
                }
            } catch (ClassCastException e) {
                // Si el casting no ha funcionado es que no es la clase y
                // se continúa en el bucle
                continue;
            }
        }

        return clase;
    }


    /**
     * Método que muta el árbol por subárbol reemplazando un nodo entero
     * (con todos sus descendientes) por otro aleatorio del mismo tipo
     */
    public void mutar() {

        /**
         * Nodo a mutar (empezando en 1)
         */
        int n = aleat.nextInt(nNodos()) + 1;

        NodoA nodo = null;

        // Por si acaso
        while (nodo == null) {
            nodo = obtenerNodoEnAmplitud(n);
            n = aleat.nextInt(nNodos()) + 1;
        }

        // Ahora se reemplaza ese nodo por otro aleatorio del mismo tipo
        Class clase = null;
        try {
            clase = Class.forName(obtenerClase(nodo));
            nodo = (NodoA) clase.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si es un operador se le asignan los hijos
        Operador op = null;
        try {
            op = (Operador) nodo;

            // Se rellena el operador dependiendo de la clase a la que pertenezca
            String clase2 = obtenerClase(op);
            // Operador aritmético o lógico
            if (clase2.equals("Suma") || clase2.equals("Resta")
                    || clase2.equals("Producto") || clase2.equals("Igual")
                    || clase2.equals("Mayor") || clase2.equals("Menor")
                    || clase2.equals("NoIgual")) {
                NodoA[] hijos = new NodoA[2];
                rellenaOp(hijos);
                op.setHijos(hijos);
            }
                
            // Expresión
            if (clase2.equals("Expresion")) {
                NodoA[] hijo = new NodoA[1];
                rellenaExpresion(hijo);
                op.setHijos(hijo);
            }

            // If
            if (clase2.equals("If")) {
                NodoA[] hijos = new NodoA[2];
                rellenaIf(hijos);
                op.setHijos(hijos);
            }

            // IfElse
            if (clase2.equals("IfElse")) {
                NodoA[] hijos = new NodoA[3];
                rellenaIfElse(hijos);
                op.setHijos(hijos);
            }

            // Bloque
            if (clase2.equals("Bloque")) {
                NodoA[] hijos = new NodoA[op.getCardinalidad()];
                rellenaBloque(hijos);
                op.setHijos(hijos);
            }
        } catch (ClassCastException e) {
            // No se hace nada, el nodo no tiene hijos porque es un operando
            return;
        }

    }

    /**
     * Método que cruza un subárbol de este árbol con otro del mismo tipo del
     * pasado por parámetro
     * @param arbol El árbol a cruzar
     */
    public void cruzar(Arbol arbol) {

        /**
         * Nodo a cruzar (empezando en 1)
         */
        int n = aleat.nextInt(nNodos()) + 1;

        /**
         * El nodo de este subárbol
         */
        NodoA nodo = null;
        /**
         * El nodo del otro subárbol
         */
        NodoA nodo2 = null;

        // Por si en el otro subárbol no existe un nodo del tipo buscado
        while (nodo2 == null) {
            // Por si acaso
            while (nodo == null) {
                nodo = obtenerNodoEnAmplitud(n);
                n = aleat.nextInt(nNodos()) + 1;
            }

            // Ahora se intenta obtener otro del mismo tipo del otro subárbol
            String clase = obtenerClase(nodo);

            nodo2 = arbol.obtenerNodoAleatPorTipo(clase);
        }

        // Ahora que se tienen los nodos del mismo tipo se intercambian clonados

        NodoA aux = nodo.clone();
        nodo = nodo2.clone();
        nodo2 = aux.clone();
    }
}
