
package com.mycompany.blackjack3;

import java.util.List;

class Pila<T> {
    private final ListaSimple<T> items;
    
    public Pila() {
        this.items = new ListaSimple<>();
    }
    
    public void push(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        nuevoNodo.siguiente = items.getCabeza();
        items.setCabeza(nuevoNodo);
        items.setTamano(items.getTamano() + 1);
    }
    
    public T pop() {
        if (estaVacia()) {
            return null;
        }
        return items.eliminarPrimero();
    }
    
    public T peek() {
        if (estaVacia()) {
            return null;
        }
        return items.getCabeza().dato;
    }
    
    public boolean estaVacia() {
        return items.estaVacia();
    }
    
    public int getTamano() {
        return items.getTamano();
    }
    
    public List<T> recorrer() {
        return items.recorrer();
    }
}