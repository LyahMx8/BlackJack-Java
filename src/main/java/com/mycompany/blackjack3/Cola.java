
package com.mycompany.blackjack3;

import java.util.List;

class Cola<T> {
    private ListaDoble<T> items;
    
    public Cola() {
        this.items = new ListaDoble<>();
    }
    
    public void encolar(T dato) {
        items.agregar(dato);
    }
    
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T dato = items.getCabeza().dato;
        items.setCabeza(items.getCabeza().siguiente);
        if (items.getCabeza() != null) {
            items.getCabeza().anterior = null;
        } else {
            items.setCola(null);
        }
        items.setTamano(items.getTamano() - 1);
        return dato;
    }
    
    public T frente() {
        if (estaVacia()) {
            return null;
        }
        return items.getCabeza().dato;
    }
    
    public boolean estaVacia() {
        return items.getCabeza() == null;
    }
    
    public int getTamano() {
        return items.getTamano();
    }
    
    public List<T> recorrer() {
        return items.recorrer();
    }
}

