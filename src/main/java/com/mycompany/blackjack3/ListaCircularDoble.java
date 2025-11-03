
package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.List;

class ListaCircularDoble<T> {
    private NodoDoble<T> cabeza;
    private int tamano;
    
    public ListaCircularDoble() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    public void agregar(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            nuevoNodo.siguiente = nuevoNodo;
            nuevoNodo.anterior = nuevoNodo;
        } else {
            NodoDoble<T> cola = cabeza.anterior;
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            nuevoNodo.siguiente = cabeza;
            cabeza.anterior = nuevoNodo;
        }
        tamano++;
    }
    
    public T obtener(int indice) {
        if (cabeza == null || indice < 0) {
            return null;
        }
        NodoDoble<T> actual = cabeza;
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
    
    public void rotarInverso(int pasos) {
        if (cabeza != null) {
            for (int i = 0; i < pasos % tamano; i++) {
                cabeza = cabeza.anterior;
            }
        }
    }
    
    public List<T> recorrer(int limite) {
        List<T> elementos = new ArrayList<>();
        if (cabeza == null) return elementos;
        
        NodoDoble<T> actual = cabeza;
        int maxIter = (limite > 0) ? limite : tamano;
        
        for (int i = 0; i < maxIter; i++) {
            elementos.add(actual.dato);
            actual = actual.siguiente;
        }
        return elementos;
    }
    
    public List<T> recorrerInverso(int limite) {
        List<T> elementos = new ArrayList<>();
        if (cabeza == null) return elementos;
        
        NodoDoble<T> actual = cabeza.anterior;
        int maxIter = (limite > 0) ? limite : tamano;
        
        for (int i = 0; i < maxIter; i++) {
            elementos.add(actual.dato);
            actual = actual.anterior;
        }
        return elementos;
    }
    
    public int getTamano() {
        return tamano;
    }
}
