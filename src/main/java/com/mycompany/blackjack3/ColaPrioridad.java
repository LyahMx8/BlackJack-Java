
package com.mycompany.blackjack3;

class ColaPrioridad<T> {
    private NodoPrioridad<T> cabeza;
    private int tamano;
    
    public ColaPrioridad() {
        this.cabeza = null;
        this.tamano = 0;
    }
    
    public void encolar(T dato, int prioridad) {
        NodoPrioridad<T> nuevoNodo = new NodoPrioridad<>(dato, prioridad);
        
        if (cabeza == null || prioridad < cabeza.prioridad) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
        } else {
            NodoPrioridad<T> actual = cabeza;
            while (actual.siguiente != null && actual.siguiente.prioridad <= prioridad) {
                actual = actual.siguiente;
            }
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
        tamano++;
    }
    
    public T desencolar() {
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
}
