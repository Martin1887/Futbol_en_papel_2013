/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package futbolEnPapel2013.jugador.controladores.programacionGenetica.evolucion;

import futbolEnPapel2013.jugador.controladores.programacionGenetica.Dinamico;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import futbolEnPapel2013.jugador.controladores.programacionGenetica.Prueba;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Martín
 */
public class PruebaCompila {

    private static String extractClasspath(ClassLoader cl) {
            StringBuffer buf = new StringBuffer();

            while (cl != null) {
                    if (cl instanceof URLClassLoader) {
                            URL urls[] = ((URLClassLoader) cl).getURLs();
                            for (int i = 0; i < urls.length; i++) {
                                    if (buf.length() > 0) {
                                            buf.append(File.pathSeparatorChar);
                                    }
                                    buf.append(urls[i].getFile().toString());
                            }
                    }
                    cl = cl.getParent();
            }

            return buf.toString();
    }

    public static void main(String[] args) throws IOException {
        Prueba prueba = new Prueba();
        prueba.imprime();

        String cod = "package futbolEnPapel2013.jugador.controladores.programacionGenetica;\npublic class Prueba implements Dinamico {\n@Override\npublic void imprime() {\nSystem.out.println(\"Nuevo\");\n}\n}";

//        ClassLoader c = Thread.currentThread().getContextClassLoader();
//        String classpath = extractClasspath(c) + File.pathSeparatorChar + new File("/buildfutbolEnPapel2013/jugador/controladores/programacionGenetica/Dinamico.class").toURI().toURL();
//        System.out.println(classpath);

        DynaCode dynamCode = new DynaCode();
	dynamCode.addSourceDir(new File("src_dinamico"));
        
        File f = new File("src_dinamico/futbolEnPapel2013/jugador/controladores/programacionGenetica/Prueba.java");
        f.delete();
        f.createNewFile();

        // Se crea un objeto escritor
        FileWriter fw = new FileWriter(f);
        fw.write(cod);
        fw.flush();
        fw.close();

        
        Dinamico din = (Dinamico) dynamCode.newProxyInstance(Dinamico.class, "futbolEnPapel2013.jugador.controladores.programacionGenetica.Prueba");

        din.imprime();

        cod = "package futbolEnPapel2013.jugador.controladores.programacionGenetica;\npublic class Prueba implements Dinamico {\n@Override\npublic void imprime() {\nSystem.out.println(\"Nuevo2\");\n}\n}";

        f = new File("src_dinamico/futbolEnPapel2013/jugador/controladores/programacionGenetica/Prueba.java");
        f.delete();
        f.createNewFile();

        // Se crea un objeto escritor
        fw = new FileWriter(f);
        fw.write(cod);
        fw.flush();
        fw.close();

        din.imprime();
    }
}
