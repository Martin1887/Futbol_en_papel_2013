package futbolEnPapel2013.jugador.controladores.programacionGenetica.evolucion;

import futbolEnPapel2013.Juego;
import futbolEnPapel2013.JuegoObscuro;
import futbolEnPapel2013.jugador.controladores.hormigas.genetico.EvaluacionGen;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.arbol.Arbol;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.CyclicBarrier;

/**
 * Clase que representa el proceso de generación de programas mediante
 * programación genética
 * @author Marcos Martín Pozo Delgado
 * @version 1.0
 */
public class ProgramacionGen {

    public static String inicio = "package futbolEnPapel2011.jugador.controladores.programacionGenetica;"
            + "\n"
            + "import futbolEnPapel2011.jugador.controladores.Controlador;\n"
            + "import futbolEnPapel2011.Juego;\n"
            + "import futbolEnPapel2011.estructura.*;\n\n"
            + "/**\n"
            + "* Clase que representa el controlador generado por la programación genética\n"
            + "* @author Marcos Martín Pozo Delgado\n"
            + "* @version 1.0\n"
            + "*/\n"
            + "public class ProgGen extends Controlador {\n\n"
            + "\t/**\n"
            + "\t* Constantes que definen los posibles objetivos de la distancia \n"
            + "\t*/\n"
            + "\tpublic static final int RIVAL = 0;\n"
            + "\tpublic static final int PROPIA = 1;\n"
            + "\tpublic static final int CENTRO = 2;\n"
            + "\tpublic static final int PAREDIZQ = 3;\n"
            + "\tpublic static final int PAREDDER = 4;\n\n\n"
            + "\tJuego padre;\n\n"
            + "\t/**\n"
            + "\t/**\n"
            + "\t* Constructor completo\n"
            + "\t* @param juego\n"
            + "\t*/\n"
            + "public ProgGen(Juego juego) {\n"
            + "\t\tpadre = juego;\n"
            + "\t}\n"
            + "\t/**\n"
            + "\t* Método mover del controlador de programación genética\n"
            + "\t* @param juego El padre\n"
            + "\t*/\n"
            + "\t@Override\n"
            + "\tpublic void mover(Juego juego) {\n"
            + "\t\tpadre = juego;\n\t}\n";
    public static String fin = "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia a la meta rival en int\n"
            + "\t* @return (int) distancia(RIVAL);\n"
            + "\t*/\n"
            + "\tpublic int distMeta() {\n"
            + "\t\treturn (int) distancia(RIVAL);\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia a la meta propia en int\n"
            + "\t* @return (int) distancia(PROPIA);\n"
            + "\t*/\n"
            + "\tpublic int distMiMeta() {\n"
            + "\t\treturn (int) distancia(PROPIA);\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia al centro del campo en int\n"
            + "\t* @return (int) distancia(CENTRO);\n"
            + "\t*/\n"
            + "\tpublic int distCentro() {\n"
            + "\t\treturn (int) distancia(CENTRO);\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia a la pared izquierda en la\n"
            + "v* misma y en int\n"
            + "\t* @return (int) distancia(PAREDIZQ);\n"
            + "\t*/\n"
            + "\tpublic int distParedIzq() {\n"
            + "\t\treturn (int) distancia(PAREDIZQ);\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia a la pared derecha en la\n"
            + "\t* misma y en int\n"
            + "\t* @return (int) distancia(PAREDDER);\n"
            + "\t*/\n"
            + "\tpublic int distParedDer() {\n"
            + "\t\treturn (int) distancia(PAREDDER);\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces del nodo actual marcados\n"
            + "\t* @return El número de enlaces del nodo actual marcados\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesM() {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < actual.getEnlaces().length; i++) {\n"
            + "\t\t\tif (actual.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces del nodo pasado por\n"
            + "\t* parámetro marcados\n"
            + "\t* @param nodo La posición del nodo del que obtener los enlaces marcados\n"
            + "\t* @return El número de enlaces del nodo pasado por parámetro marcados\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesM(Posicion nodo) {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[nodo.getX()][nodo.getY()];\n\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < actual.getEnlaces().length; i++) {\n"
            + "\t\t\tif (actual.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces marcados que hay por la\n"
            + "\t* izquierda (los de los enlaces de la izquierda y todos los enlaces de\n"
            + "\t* los nodos que conectan estos)\n"
            + "\t* @return marcados El número de enlaces marcados por la izquierda\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesMIzq() {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()];\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo conectado de alguno de los enlaces de la izquierda (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tNodo aux;\n\n"
            + "\t\t/**\n"
            + "\t\t* Posición del nodo anterior (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tPosicion pos;\n\n"
            + "\t\t/*\n"
            + "\t\t* Primero se miran los enlaces de la izquierda (0, 4 y 6)\n"
            + "\t\t*/\n"
            + "\t\tif (actual.getEnlaces()[0].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[4].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[6].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t}\n\n"
            + "\t\t/*\n"
            + "\t\t* Ahora se miran todos los enlaces de los nodos conectados por los\n"
            + "\t\t* enlaces anteriores\n"
            + "\t\t*/\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 0);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 4);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 6);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces marcados que hay por la\n"
            + "\t* derecha (los de los enlaces de la derecha y todos los enlaces de\n"
            + "\t* los nodos que conectan estos)\n"
            + "\t* @return marcados El número de enlaces marcados por la derecha\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesMDer() {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()];\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo conectado de alguno de los enlaces de la derecha (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tNodo aux;\n\n"
            + "\t\t/**\n"
            + "\t\t* Posición del nodo anterior (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tPosicion pos;\n\n"
            + "\t\t/*\n"
            + "\t\t* Primero se miran los enlaces de la derecha (1, 5 y 7)\n"
            + "\t\t*/\n"
            + "\t\tif (actual.getEnlaces()[1].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[5].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[7].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n\n"
            + "\t\t/*\n"
            + "\t\t* Ahora se miran todos los enlaces de los nodos conectados por los\n"
            + "\t\t* enlaces anteriores\n"
            + "\t\t*/\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 1);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 5);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 7);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces marcados que hay por\n"
            + "\t* arriba (los de los enlaces de arriba y todos los enlaces de\n"
            + "\t* los nodos que conectan estos)\n"
            + "\t* @return marcados El número de enlaces marcados por arriba\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesMArr() {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()];\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo conectado de alguno de los enlaces de la derecha (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tNodo aux;\n\n"
            + "\t\t/**\n"
            + "\t\t* Posición del nodo anterior (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tPosicion pos;\n\n"
            + "\t\t/*\n"
            + "\t\t* Primero se miran los enlaces de arriba (2, 4 y 5)\n"
            + "\t\t*/\n"
            + "\t\tif (actual.getEnlaces()[2].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[4].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[5].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n\n"
            + "\t\t/*\n"
            + "\t\t* Ahora se miran todos los enlaces de los nodos conectados por los\n"
            + "\t\t* enlaces anteriores\n"
            + "\t\t*/\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 2);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 4);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 5);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el número de enlaces marcados que hay por\n"
            + "\t* abajo (los de los enlaces de abajo y todos los enlaces de\n"
            + "\t* los nodos que conectan estos)\n"
            + "\t* @return marcados El número de enlaces marcados por abajo\n"
            + "\t*/\n"
            + "\tpublic int nEnlacesMDer() {\n"
            + "\t\t/**\n"
            + "\t\t* Número de enlaces del nodo actual marcados\n"
            + "\t\t*/\n"
            + "\t\tint marcados = 0;\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo de la posición actual\n"
            + "\t\t*/\n"
            + "\t\tNodo actual = padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()];\n\n"
            + "\t\t/**\n"
            + "\t\t* Nodo conectado de alguno de los enlaces de la derecha (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tNodo aux;\n\n"
            + "\t\t/**\n"
            + "\t\t* Posición del nodo anterior (auxiliar)\n"
            + "\t\t*/\n"
            + "\t\tPosicion pos;\n\n"
            + "\t\t/*\n"
            + "\t\t* Primero se miran los enlaces de la derecha (3, 6 y 7)\n"
            + "\t\t*/\n"
            + "\t\tif (actual.getEnlaces()[3].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[6].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n"
            + "\t\tif (actual.getEnlaces()[7].getMarcado()) {\n"
            + "\t\t\tmarcados++;\n"
            + "\t\t}\n\n"
            + "\t\t/*\n"
            + "\t\t* Ahora se miran todos los enlaces de los nodos conectados por los\n"
            + "\t\t* enlaces anteriores\n"
            + "\t\t*/\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 3);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 6);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\tpos = obtenerNodo(padre.getActual(), 7);\n"
            + "\t\taux = padre.getTablero().getNodos()[pos.getX()][pos.getY()];\n"
            + "\t\t// Por cada enlace marcado se aumenta marcados en 1\n"
            + "\t\tfor (int i = 0; i < aux.getEnlaces().length; i++) {\n"
            + "\t\t\tif (aux.getEnlaces()[i].getMarcado()) {\n"
            + "\t\t\t\tmarcados++;\n"
            + "\t\t\t}\n"
            + "\t\t}\n\n"
            + "\t\treturn marcados;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia la izquierda haciendo Math.ceil a 1 / el coste del nodo de la izquierda\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia la izquierda\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoIzq() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() - 1, actual.getY());\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia la derecha haciendo Math.ceil a 1 / el coste del nodo de la derecha\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia la derecha\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoDer() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() + 1, actual.getY());\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia arriba haciendo Math.ceil a 1 / el coste del nodo de arriba\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia arriba\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoArr() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX(), actual.getY() - 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia abajo haciendo Math.ceil a 1 / el coste del nodo de abajo\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia abajo\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoAba() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX(), actual.getY() + 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia arriba-izquierda haciendo Math.ceil a 1 / el coste del nodo de arriba-izquierda\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia arriba-izquierda\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoArI() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() - 1, actual.getY() - 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t\t\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia arriba-derecha haciendo Math.ceil a 1 / el coste del nodo de arriba-derecha\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia arriba-derecha\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoArD() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() + 1, actual.getY() - 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia abajo-izquierda haciendo Math.ceil a 1 / el coste del nodo de abajo-izquierda\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia abajo-izquierda\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoAbI() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() - 1, actual.getY() + 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la longitud del camino más largo que hay moviendo\n"
            + "\t* hacia abajo-derecha haciendo Math.ceil a 1 / el coste del nodo de abajo-derecha\n"
            + "\t* en un grafo que se crea en el método a partir del tablero actual\n"
            + "\t* @return El camino más largo que hay hacia abajo-derecha\n"
            + "\t*/\n"
            + "\tpublic int longCaminoMasLargoAbD() {\n"
            + "\t\tint longitud = 0;\n"
            + "\t\ttry {\n"
            + "\t\t\tPosicion actual = padre.getActual();\n"
            + "\t\t\tPosicion siguiente = new Posicion(actual.getX() + 1, actual.getY() + 1);\n"
            + "\t\t\tGrafo g = new Grafo(padre.getTablero());\n"
            + "\t\t\tdouble coste = g.obtenerEnlace(actual, siguiente)\n"
            + "\t\t\t        .getCoste(g.obtenerIndiceCoste(actual, siguiente));\n"
            + "\t\t\tdouble longitudDouble = 1D / coste;\n"
            + "\t\t\tlongitudDouble = Math.ceil(longitudDouble);\n"
            + "\t\t\tlongitud = (int) longitudDouble;\n"
            + "\t\t} catch (CloneNotSupportedException e) {\n"
            + "\t\t\te.printStackTrace();\n"
            + "\t\t}\n"
            + "\t\treturn longitud;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve el nodo al que conecta un enlace\n"
            + "\t* @param nodo Posición del nodo\n"
            + "\t* @param enlace Número que representa al enlace en el array del nodo\n"
            + "\t* @return La posición del nodo conectado\n"
            + "\t*/\n"
            + "\tpublic static Posicion obtenerNodo(Posicion nodo, int enlace) {\n"
            + "\t\tPosicion conectado = null;\n\n"
            + "\t\t// Se comprueban todas las posibilidades entre nodos adyacentes\n"
            + "\t\tswitch (enlace) {\n"
            + "\t\t\tcase 0:\n"
            + "\t\t\t    // Izquierda\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() - 1, nodo.getY());\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 1:\n"
            + "\t\t\t    // Derecha\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() + 1, nodo.getY());\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 2:\n"
            + "\t\t\t    // Arriba\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX(), nodo.getY() - 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 3:\n"
            + "\t\t\t    // Abajo\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX(), nodo.getY() + 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 4:\n"
            + "\t\t\t    // Arriba-Izquierda\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() - 1, nodo.getY() - 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 5:\n"
            + "\t\t\t    // Arriba-Derecha\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() + 1, nodo.getY() - 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 6:\n"
            + "\t\t\t    // Abajo-Izquierda\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() - 1, nodo.getY() + 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t\tcase 7:\n"
            + "\t\t\t    // Abajo-Derecha\n"
            + "\t\t\t    conectado = new Posicion(nodo.getX() + 1, nodo.getY() + 1);\n"
            + "\t\t\t    break;\n"
            + "\t\t}\n\n"
            + "\t\treturn conectado;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que devuelve la distancia de la posición actual\n"
            + "\t* a la portería rival o propia o el centro o pared izquierda o derecha\n"
            + "\t* (dependiendo del parámetro), donde la distancia es\n"
            + "\t* el máximo entre la distancia en X y la distancia en Y"
            + "\t* @params objetivo El objetivo (portería rival o propia o centro del campo o pared izquierda o derecha)\n"
            + "\t* @return La distancia al objetivo requerido\n"
            + "\t*/\n"
            + "\tpublic double distancia(int objetivo) {\n"
            + "\t\tdouble resultado = 0;\n\n"
            + "\t\t// Posición del estado actual\n"
            + "\t\tdouble x1 = padre.getActual().getX();\n"
            + "\t\tdouble y1 = padre.getActual().getY();\n\n"
            + "\t\t// Posición en la que está el objetivo\n"
            + "\t\tdouble x2 = 5, y2 = y1;\n\n"
            + "\t\t// Si el objetivo es alguna pared\n"
            + "\t\tif (objetivo == PAREDIZQ) {\n"
            + "\t\t\tx2 = 10;\n"
            + "\t\t} else if (objetivo == PAREDDER) {\n"
            + "\t\t\tx2 = 0;\n"
            + "\t\t}\n\n"
            + "\t\t// Si el objetivo es alguna portería o el centro\n"
            + "\t\tif (objetivo == RIVAL) {\n"
            + "\t\t\tif (padre.getTurnoJug1()) {\n"
            + "\t\t\t\ty2 = -1;\n"
            + "\t\t\t} else {\n"
            + "\t\t\t\ty2 = 17;\n"
            + "\t\t\t}\n"
            + "\t\t} else if (objetivo == PROPIA) {\n"
            + "\t\t\tif (padre.getTurnoJug1()) {\n"
            + "\t\t\t\ty2 = 17;\n"
            + "\t\t\t} else {\n"
            + "\t\t\t\ty2 = -1;\n"
            + "\t\t\t}\n"
            + "\t\t} else if (objetivo == CENTRO) {\n"
            + "\t\t\ty2 = 8;\n"
            + "\t\t}\n\n"
            + "\t\t// Se calcula la distancia \n"
            + "\t\tresultado = Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));\n\n"
            + "\t\treturn resultado;\n"
            + "\t}\n\n"
            + "\t/**\n"
            + "\t* Método que comprueba si el jugador no se puede mover en ninguna posición\n"
            + "\t* @return true si no hay movimiento posible y false en caso contrario\n"
            + "\t*/\n"
            + "\tpublic boolean bloqueo() {\n"
            + "\t\tif ((padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[0] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[0].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[1] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[1].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[2] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[2].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[3] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[3].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[4] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[4].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[5] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[5].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[6] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[6].getMarcado())\n"
            + "\t\t    && (padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[7] == null\n"
            + "\t\t    || padre.getTablero().getNodos()[padre.getActual().getX()]\n"
            + "\t\t    [padre.getActual().getY()].getEnlaces()[7].getMarcado())) {\n"
            + "\t\t\treturn true;\n"
            + "\t\t} else {\n"
            + "\t\t\treturn false;\n"
            + "\t\t}\n"
            + "\t}\n"
            + "}";

    /**
     * Método que compila y controla el proceso de evolución de la programación
     * genética
     * @param args
     */
    public static void main(String[] args) {

        /**
         * Población de individuos. Tamaño 24
         */
        Arbol[] poblacion = new Arbol[164];

        /**
         * Evaluación de cada uno de los individuos de la población
         */
        double[] evaluaciones = new double[poblacion.length];

        /**
         * Estadísticas de cada uno de los individuos de la población
         */
        double[][] estadisticas = new double[poblacion.length][7];

        /**
         * Generación intermedia. De igual tamaño que la población
         */
        Arbol[] intermedia = new Arbol[poblacion.length];

        /**
         * Evaluación de cada uno de los individuos de la intermedia
         */
        double[] evIntermedia = new double[intermedia.length];

        /**
         * Estadísticas de cada uno de los individuos de la intermedia
         */
        double[][] estIntermedia = new double[intermedia.length][7];

        /**
         * Generación actual
         */
        int generacion = 0;

        /**
         * Barrera que sirve para esperar a que se ejecuten todas las evaluaciones
         */
        CyclicBarrier barrera;

        /**
         * Número de hilos que ejecutarán la función de evaluación
         */
        int nHilos = 16;

        /**
         * Variables para imprimir en el fichero cuya ruta se introduzca
         * por pantalla la evaluación del indMejor individuo de
         * cada generación, para así después poder hacer una gráfica
         */
        File f;
        FileOutputStream f1;
        OutputStreamWriter f2;
        BufferedWriter linea;
//        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        String ruta = "evMejor.txt";

        try {
            f = new File(ruta);
            f1 = new FileOutputStream(f);
            f2 = new OutputStreamWriter(f1);
            linea = new BufferedWriter(f2);

            linea.write("GanaJ1 (1/0)\tminDistMetaJ1\tminDistMetaJ2\t"
                    + "turnosCampoJ1\tturnosCampoJ2\tturnos totales\tevaluación"
                    + " (600*gana+40*minDistMetaJ1-40*minDistMetaJ2+2*porcentajeCampoJ2"
                    + "-5*turnosTotales si gana y +5*turnosTotales si pierde)\tevaluación)");


            /**
             * Variables para imprimir en el fichero cuya ruta se introduzca
             * por pantalla los valores del indMejor individuo de
             * cada generación, para sacar conclusiones
             */
            File fM;
            FileOutputStream fM1;
            OutputStreamWriter fM2;
            BufferedWriter lineaM;
//        teclado = new BufferedReader(new InputStreamReader(System.in));


//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar los parámetros del mejor");

//        ruta = teclado.readLine();

            ruta = "mejor.txt";

            fM = new File(ruta);
            fM1 = new FileOutputStream(fM);
            fM2 = new OutputStreamWriter(fM1);
            lineaM = new BufferedWriter(fM2);

            /**
             * Variables para imprimir en el fichero cuya ruta se introduzca
             * por pantalla la evaluación de todos los individuos de
             * cada generación, para así después poder hacer una gráfica
             */
            File fT;
            FileOutputStream f1T;
            OutputStreamWriter f2T;
            BufferedWriter lineaT;
//        teclado = new BufferedReader(new InputStreamReader(System.in));

//        System.out.println("Introduzca la ruta del fichero donde quiere almacenar las evaluaciones de todos");

//        ruta = teclado.readLine();

            ruta = "evTodos.txt";

            fT = new File(ruta);
            f1T = new FileOutputStream(fT);
            f2T = new OutputStreamWriter(f1T);
            lineaT = new BufferedWriter(f2T);

            lineaT.write("GanaJ1 (1/0)\tminDistMetaJ1\tminDistMetaJ2\t"
                    + "turnosCampoJ1\tturnosCampoJ2\tturnos totales\tevaluación"
                    + " (600*gana+40*minDistMetaJ1-40*minDistMetaJ2+2*porcentajeCampoJ2"
                    + "-5*turnosTotales si gana y +5*turnosTotales si pierde)\tevaluación)");




            /*
             * Primero se generan todos los individuos y se calcula su evaluación
             */
            for (int i = 0; i < poblacion.length; i++) {
                poblacion[i] = new Arbol();
                //evaluaciones[i] = fEvaluacion(poblacion[i], i, generacion, estadisticas[i], lineaT);
            }

            // El tamaño de la barrera es el número de hilos a ejecutar más este hilo
            
                barrera = new CyclicBarrier(nHilos + 1);
                int tamTrozo = poblacion.length / nHilos;
                int primerElemento = 0;
                for (int i = 0; i < nHilos; i++) {
                    Arbol[] individuos = new Arbol[tamTrozo];
                    System.arraycopy(poblacion, primerElemento, individuos, 0, tamTrozo);
                    Evaluacion evaluador = new Evaluacion(individuos, evaluaciones,
                            primerElemento, generacion, estadisticas, lineaT, barrera);
                    evaluador.start();
                    primerElemento += tamTrozo;
                }
                barrera.await();
            

            /*
             * Después se ejecuta el proceso de evolución durante 2000 generaciones
             */
            while (generacion < 2000) {
                // Primero se meten en la intermedia los 1/2 mejores, para lo cual
                // se ordenan de peor a mejor
                OperadoresGen.quickSort(poblacion, evaluaciones, 0, poblacion.length - 1);
                // Se copian los últimos al principio de intermedia
                System.arraycopy(poblacion, poblacion.length * 1 / 2, intermedia, 0, poblacion.length / 2);
                // También se copian las evaluaciones
                System.arraycopy(evaluaciones, evaluaciones.length * 1 / 2, evIntermedia, 0, evaluaciones.length / 2);
                // También se copian las estadísticas
                System.arraycopy(estadisticas, estadisticas.length * 1 / 2, estIntermedia, 0, estadisticas.length / 2);

                // Después se seleccionan individuos hasta llenar la intermedia
                // y cada par se muta o se cruza con equiprobabilidad
                for (int i = intermedia.length / 2; i < intermedia.length; i += 2) {
                    if (Math.random() < 0.5) {
                        // Se mutan los dos
                        Arbol[] auxs = OperadoresGen.seleccionar(poblacion, evaluaciones, 2, OperadoresGen.TORNEOS, 3);
                        auxs[0].mutar();
                        auxs[1].mutar();
                        System.arraycopy(auxs, 0, intermedia, i, 2);
                    } else {
                        // Se cruzan entre ellos

                        Arbol[] auxs = OperadoresGen.seleccionar(poblacion, evaluaciones, 2, OperadoresGen.TORNEOS, 3);
                        // Da igual en cual de ellos se haga el cruce, se van a
                        // modificar los dos
                        auxs[0].cruzar(auxs[1]);
                        System.arraycopy(auxs, 0, intermedia, i, 2);
                    }
                }

                // Ahora hay que calcular las evaluaciones de la intermedia
                // (sólo de la segunda mitad, de la otra ya están)
//                for (int i = intermedia.length / 2; i < intermedia.length; i++) {
//                    evIntermedia[i] = fEvaluacion(intermedia[i], i, generacion, estadisticas[i], lineaT);
//                }

                // El tamaño de la barrera es el número de hilos a ejecutar más este hilo
                barrera = new CyclicBarrier(poblacion.length / 2 + 1);
                tamTrozo = 1;
                primerElemento = 0;
                for (int i = poblacion.length / 2; i < poblacion.length; i++) {
                    Arbol[] individuos = new Arbol[tamTrozo];
                    System.arraycopy(intermedia, primerElemento, individuos, 0, tamTrozo);
                    Evaluacion evaluador = new Evaluacion(individuos, evIntermedia,
                            primerElemento, generacion, estIntermedia, lineaT, barrera);
                    evaluador.start();
                    primerElemento += tamTrozo;
                }
                barrera.await();


                /*
                 * Por último la población es reemplazada por la intermedia
                 */
                poblacion = OperadoresGen.reemplazar(poblacion, evaluaciones, intermedia, evIntermedia, OperadoresGen.ESTACIONARIO);

                generacion++;

                // Se calcula cuál es el mejor
                int mejor = 0;
                double evMejor = evaluaciones[0];
                for (int i = 0; i < poblacion.length; i++) {
                    if (evaluaciones[i] > evMejor) {
                        mejor = i;
                        evMejor = evaluaciones[mejor];
                    }
                }

                /* impresión del indMejor y de su puntuación */
                for (int i = 0; i < 10; i++) {
                    System.out.println("********************");
                }

                System.out.println("Mejor puntuación: " + evMejor);
                System.out.println();
                System.out.println("Mejor individuo: ");
                
                System.out.println(poblacion[mejor].toString());
                


                /*
                 * Se imprimen las estadísticas del indMejor y un salto de línea en el fichero
                 */
                linea.newLine();
                for (int i = 0; i < estadisticas[mejor].length; i++) {
                    linea.write(estadisticas[mejor][i] + "\t");
                }
                linea.write(String.valueOf(evMejor));
                linea.flush();

                /*
                 * Se imprimen los valores del indMejor y un salto de línea en el fichero
                 */             
                lineaM.write(poblacion[mejor].toString());
                
                lineaM.newLine();
                lineaM.flush();

                lineaT.newLine();
                lineaT.newLine();
                lineaT.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que proporciona la evaluación de un individuo en una generación
     * determinada. La generación se usa para elegir el rival.
     * La evaluación consiste en la media de la evaluación de 20 partidos
     * @param individuo Individuo a evaluar
     * @param i Número de individuo (para tener varios códigos)
     * @param generacion Generación de la población. Se usa para elegir el rival
     * @param estadisticas Estadísticas del individuo
     * @param fSalida Fichero donde se escriben las estadísticas de todas las evaluaciones
     * @return evaluacion
     */
    public static double fEvaluacion(Arbol individuo, int i, int generacion,
            double[] estadisticas, BufferedWriter fSalida) {
        double evaluacion = 0;

        // Primero se construye el código, añadiendo al nombre de la clase y al
        // constructor el número de individuo
        String ini = String.copyValueOf(inicio.toCharArray());
        ini.replaceFirst("ProgGen", "ProgGen" + i);
        ini.replaceFirst("ProgGen", "ProgGen" + i);

        String cod = ini + individuo.toString() + fin;

        try {
            // Se crea el archivo .java eliminando su contenido si ya existía
            File f = new File("src/src_dinamico/ProgGen" + i);
            f.delete();
            f.createNewFile();

            // Se crea un objeto escritor
            FileWriter fw = new FileWriter(f);
            fw.write(cod);
            fw.flush();
            fw.close();


            // Ahora se crea una instancia del controlador creado, y se enfrenta con
            // un alfa-beta cuyo nivel depende del número de generación (2 si es
            // mayor que 200 y 1 alias)
            // Se ejecutan los 20 partidos copiando a la vez las estadísticas medias
            // en estadisticas
            int tipoJ1 = Juego.CPU;
            int tipoJ2 = Juego.CPU;
            int niv1 = Juego.PROG_GEN_EVAL;   
            int niv2 = generacion > 200 ? 2 : 1;


            JuegoObscuro juego = null;
            try {
                juego = new JuegoObscuro(tipoJ1, tipoJ2, niv1, niv2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            double[][] res = juego.iniciar_partidos(20, fSalida, null, i, false);
            double[] est = res[res.length - 1];
            System.arraycopy(est, 0, estadisticas,
                    0, est.length);

            // Se asigna la evaluación como la evaluación media y se procede a restar
            // por tiempo si el tiempo medio de cada movimiento ha sido mayor a 2 segundos
            evaluacion = estadisticas[estadisticas.length - 1];

        } catch (Exception e) {
            e.printStackTrace();
        }

        return evaluacion;
    }
}
