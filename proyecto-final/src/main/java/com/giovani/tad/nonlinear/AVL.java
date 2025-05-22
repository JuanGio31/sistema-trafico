package com.giovani.tad.nonlinear;

import com.giovani.Interseccion;

import java.io.FileWriter;
import java.io.PrintWriter;

public class AVL {
    private NodoAVL raiz;

    public AVL() {
        this.raiz = null;
    }

    public NodoAVL buscar(Interseccion dato) {
        return buscar(this.raiz, dato);
    }

    public NodoAVL buscar(NodoAVL actual, Interseccion dato) {
        if (actual == null) {
            return null;
        }
        if (actual.getDato().getId().equals(dato.getId())) {
            return actual;
        }
        NodoAVL izq = buscar(actual.getHijoIzquierdo(), dato);
        if (izq != null) {
            return izq;
        }
        return buscar(actual.getHijoDerecho(), dato);
    }

    private int obtenerFE(NodoAVL nodo) {
        if (nodo == null) return -1;
        return nodo.getFe();
    }

    private NodoAVL rotacionIzquierda(NodoAVL nodo) {
        NodoAVL aux = nodo.getHijoIzquierdo();
        nodo.setHijoIzquierdo(aux.getHijoDerecho());
        aux.setHijoDerecho(nodo);
        nodo.setFe(Math.max(obtenerFE(nodo.getHijoIzquierdo()), obtenerFE(nodo.getHijoDerecho())) + 1);
        aux.setFe(Math.max(obtenerFE(aux.getHijoIzquierdo()), obtenerFE(aux.getHijoDerecho())) + 1);
        return aux;
    }

    private NodoAVL rotacionDerecha(NodoAVL nodo) {
        NodoAVL aux = nodo.getHijoDerecho();
        nodo.setHijoDerecho(aux.getHijoIzquierdo());
        aux.setHijoIzquierdo(nodo);
        nodo.setFe(Math.max(obtenerFE(nodo.getHijoIzquierdo()), obtenerFE(nodo.getHijoDerecho())) + 1);
        aux.setFe(Math.max(obtenerFE(aux.getHijoIzquierdo()), obtenerFE(aux.getHijoDerecho())) + 1);
        return aux;
    }

    private NodoAVL rotacionDobleIzquierda(NodoAVL nodo) {
        NodoAVL temp;
        nodo.setHijoIzquierdo(rotacionDerecha(nodo.getHijoIzquierdo()));
        temp = rotacionIzquierda(nodo);
        return temp;
    }

    private NodoAVL rotacionDobleDerecha(NodoAVL nodo) {
        NodoAVL temp;
        nodo.setHijoDerecho(rotacionIzquierda(nodo.getHijoDerecho()));
        temp = rotacionDerecha(nodo);
        return temp;
    }

    private NodoAVL insertar(NodoAVL nuevo, NodoAVL sub) {
        var nuevoPadre = sub;
        if (nuevo.getComplejidad() < sub.getComplejidad()) {
            if (sub.getHijoIzquierdo() == null) {
                sub.setHijoIzquierdo(nuevo);
            } else {
                sub.setHijoIzquierdo(insertar(nuevo, sub.getHijoIzquierdo()));
                if (obtenerFE(sub.getHijoIzquierdo()) - obtenerFE(sub.getHijoDerecho()) == 2) {
                    if (nuevo.getComplejidad() < sub.getHijoIzquierdo().getComplejidad()) {
                        nuevoPadre = rotacionIzquierda(sub);
                    } else {
                        nuevoPadre = rotacionDobleIzquierda(sub);
                    }
                }
            }
        } else if (nuevo.getComplejidad() > sub.getComplejidad()) {
            if (sub.getHijoDerecho() == null) {
                sub.setHijoDerecho(nuevo);
            } else {
                sub.setHijoDerecho(insertar(nuevo, sub.getHijoDerecho()));
                if (obtenerFE(sub.getHijoDerecho()) - obtenerFE(sub.getHijoIzquierdo()) == 2) {
                    if (nuevo.getComplejidad() > sub.getHijoDerecho().getComplejidad()) {
                        nuevoPadre = rotacionDerecha(sub);
                    } else {
                        nuevoPadre = rotacionDobleDerecha(sub);
                    }
                }
            }
        }
//        else {
//            System.out.println("Nodo duplicado!");
//        }
        if (sub.getHijoIzquierdo() == null && sub.getHijoDerecho() != null) {
            sub.setFe(sub.getHijoDerecho().getFe() + 1);
        } else if (sub.getHijoDerecho() == null && sub.getHijoIzquierdo() != null) {
            sub.setFe(sub.getHijoIzquierdo().getFe() + 1);
        } else {
            sub.setFe(Math.max(obtenerFE(sub.getHijoIzquierdo()), obtenerFE(sub.getHijoDerecho())) + 1);
        }
        return nuevoPadre;
    }

    public void insertar(Interseccion dato) {
        var nuevo = new NodoAVL(dato);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            raiz = insertar(nuevo, raiz);
        }
    }

    public void preOrder() {
        System.out.print("- Pre-Orden { ");
        this.preOrder(this.raiz);
        System.out.println("}");
    }

    public void inOrder() {
        System.out.print("- In-Orden { ");
        this.inOrder(this.raiz);
        System.out.println("}");
    }

    public void postOrder() {
        System.out.print("- Post-Orden { ");
        this.postOrder(this.raiz);
        System.out.println("}");
    }

    private void inOrder(NodoAVL node) {
        if (node != null) {
            //subarbol izquierdo, nodo raiz, subarbol derecho
            inOrder(node.getHijoIzquierdo());
            System.out.print(node.getDato().getId() + "[" + node.getDato().getCola().getNumProcesos() + "] - ");
            inOrder(node.getHijoDerecho());
        }
    }

    private void postOrder(NodoAVL node) {
        if (node != null) {
            //subarbol izquierdo, subarbol derecho, nodo raiz
            postOrder(node.getHijoIzquierdo());
            postOrder(node.getHijoDerecho());
            System.out.print(node.getDato().getId() + "[" + node.getDato().getCola().getNumProcesos() + "] - ");
        }
    }

    private void preOrder(NodoAVL node) {
        if (node != null) {
            //nodo raiz, subarbol izquierdo, subarbol derecho
            System.out.print(node.getDato().getId() + "[" + node.getDato().getCola().getNumProcesos() + "] - ");
            preOrder(node.getHijoIzquierdo());
            preOrder(node.getHijoDerecho());
        }
    }

    public String obtenerGraphviz() {
        String texto = "digraph G {\n"
                + "   node[shape = square, style = filled, fillcolor = \"#57B8FF\", color = \"#2176AE\"];\n"
                + "   edge[color = \"#31CEFO\"];\n";
        if (raiz != null) {
            texto += raiz.textoGraphviz();
        }
        texto += "\n}";
        return texto;
    }

    public void escribirArchivo(String path, String contenido) {
        FileWriter fichero;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(path);
            pw = new PrintWriter(fichero);
            pw.write(contenido);
            pw.close();
            fichero.close();
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo " + path);
        } finally {
            if (pw != null) pw.close();
        }
    }

    public void dibujar() {
        try {
            escribirArchivo("archivo.dot", obtenerGraphviz());
            ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tjpg", "archivo.dot", "-o", "archivo.jpg");
            processBuilder.redirectErrorStream(true);
            processBuilder.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
