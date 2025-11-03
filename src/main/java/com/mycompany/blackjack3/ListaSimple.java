
package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ListaSimple<T> {
    private Nodo<T> cabeza;
    private int tamano;
    
    public ListaSimple() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    public void agregar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (cabeza == null) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
        tamano++;
    }
    
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamano) {
            return null;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }
    
    public T eliminarPrimero() {
        if (cabeza == null) {
            return null;
        }
        T dato = cabeza.dato;
        cabeza = cabeza.siguiente;
        tamano--;
        return dato;
    }
    
    public boolean estaVacia() {
        return cabeza == null;
    }
    
    public int getTamano() {
        return tamano;
    }
    
    public List<T> recorrer() {
        List<T> elementos = new ArrayList<>();
        Nodo<T> actual = cabeza;
        while (actual != null) {
            elementos.add(actual.dato);
            actual = actual.siguiente;
        }
        return elementos;
    }
    
    public void ordenarBurbuja(Comparator<T> comparador) {
        if (tamano < 2) return;
        
        for (int i = 0; i < tamano - 1; i++) {
            Nodo<T> actual = cabeza;
            for (int j = 0; j < tamano - i - 1; j++) {
                if (actual.siguiente != null) {
                    if (comparador.compare(actual.dato, actual.siguiente.dato) > 0) {
                        T temp = actual.dato;
                        actual.dato = actual.siguiente.dato;
                        actual.siguiente.dato = temp;
                    }
                    actual = actual.siguiente;
                }
            }
        }
    }
    
    public Nodo<T> getCabeza() {
        return cabeza;
    }
    
    public void setCabeza(Nodo<T> cabeza) {
        this.cabeza = cabeza;
    }
    
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
}
