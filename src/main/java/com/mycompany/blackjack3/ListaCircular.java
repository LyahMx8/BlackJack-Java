
package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.List;

class ListaCircularSimple<T> {
    private Nodo<T> cabeza;
    private int tamano;
    
    public ListaCircularSimple() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            nuevoNodo.siguiente = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
            nuevoNodo.siguiente = cabeza;
        }
        tamano++;
    }
    
    public T obtener(int indice) {
        if (cabeza == null || indice < 0) {
            return null;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice % tamano; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    public void rotar(int pasos) {
        if (cabeza != null) {
            for (int i = 0; i < pasos % tamano; i++) {
                cabeza = cabeza.siguiente;
            }
        }
    }
    
    public List<T> recorrer(int limite) {
        List<T> elementos = new ArrayList<>();
        if (cabeza == null) return elementos;
        
        Nodo<T> actual = cabeza;
        int maxIter = (limite > 0) ? limite : tamano;
        
        for (int i = 0; i < maxIter; i++) {
            elementos.add(actual.dato);
            actual = actual.siguiente;
        }
        return elementos;
    }
    
    public int getTamano() {
        return tamano;
    }
}

