
package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ListaDoble<T> {
    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola;
    private int tamano;
    
    public ListaDoble() {
        this.cabeza = null;
        this.cola = null;
        this.tamano = 0;
    }
    
    public void agregar(T dato) {
        NodoDoble<T> nuevoNodo = new NodoDoble<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
        tamano++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            return null;
        }
        
        NodoDoble<T> actual;
        if (indice < tamano / 2) {
            actual = cabeza;
            for (int i = 0; i < indice; i++) {
                actual = actual.siguiente;
            }
        } else {
            actual = cola;
            for (int i = tamano - 1; i > indice; i--) {
                actual = actual.anterior;
            }
        }
        return actual.dato;
    }
    
    public List<T> recorrer() {
        List<T> elementos = new ArrayList<>();
        NodoDoble<T> actual = cabeza;
        while (actual != null) {
            elementos.add(actual.dato);
            actual = actual.siguiente;
        }
        return elementos;
    }
    
    public List<T> recorrerInverso() {
        List<T> elementos = new ArrayList<>();
        NodoDoble<T> actual = cola;
        while (actual != null) {
            elementos.add(actual.dato);
            actual = actual.anterior;
        }
        return elementos;
    }
    
    public void ordenarQuicksort(Comparator<T> comparador) {
        List<T> elementos = recorrer();
        quicksort(elementos, 0, elementos.size() - 1, comparador);
        
        cabeza = null;
        cola = null;
        tamano = 0;
        for (T elem : elementos) {
            agregar(elem);
        }
    }
    
    private void quicksort(List<T> arr, int bajo, int alto, Comparator<T> comparador) {
        if (bajo < alto) {
            int pi = partition(arr, bajo, alto, comparador);
            quicksort(arr, bajo, pi - 1, comparador);
            quicksort(arr, pi + 1, alto, comparador);
        }
    }
    
    private int partition(List<T> arr, int bajo, int alto, Comparator<T> comparador) {
        T pivot = arr.get(alto);
        int i = bajo - 1;
        
        for (int j = bajo; j < alto; j++) {
            if (comparador.compare(arr.get(j), pivot) <= 0) {
                i++;
                T temp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, temp);
            }
        }
        
        T temp = arr.get(i + 1);
        arr.set(i + 1, arr.get(alto));
        arr.set(alto, temp);
        return i + 1;
    }
    
    public int getTamano() {
        return tamano;
    }
    
    public NodoDoble<T> getCabeza() {
        return cabeza;
    }
    
    public void setCabeza(NodoDoble<T> cabeza) {
        this.cabeza = cabeza;
    }
    
    public void setCola(NodoDoble<T> cola) {
        this.cola = cola;
    }
    
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
}
