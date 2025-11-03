
package com.mycompany.blackjack3;


class NodoPrioridad<T> {
    T dato;
    int prioridad;
    NodoPrioridad<T> siguiente;
    
    public NodoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
        this.siguiente = null;
    }
}