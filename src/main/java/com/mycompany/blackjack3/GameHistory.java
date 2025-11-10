
package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.List;

class GameHistory {
    private Cola<String> history;
    
    public GameHistory() {
        this.history = new Cola<>();
    }
    
    public void addGame(String resultado) {
        history.encolar(resultado);
    }
    
    public void showHistory(int limite) {
        if (history.estaVacia()) {
            System.out.println("No hay historial.");
            return;
        }
        
        System.out.println("\n=== ÚLTIMOS RESULTADOS ===");
        List<String> tempLista = new ArrayList<>();
        
        while (!history.estaVacia()) {
            tempLista.add(history.desencolar());
        }
        
        int inicio = Math.max(0, tempLista.size() - limite);
        for (int i = inicio; i < tempLista.size(); i++) {
            System.out.println((i + 1) + ". " + tempLista.get(i));
        }
        
        for (String resultado : tempLista) {
            history.encolar(resultado);
        }
    }

    public List<String> getLast(int limite) {
        List<String> result = new ArrayList<>();
        if (history.estaVacia()) {
            return result;
        }
        List<String> tempLista = new ArrayList<>();
        while (!history.estaVacia()) {
            tempLista.add(history.desencolar());
        }
        int inicio = Math.max(0, tempLista.size() - limite);
        for (int i = inicio; i < tempLista.size(); i++) {
            result.add(tempLista.get(i));
        }
        for (String r : tempLista) {
            history.encolar(r);
        }
        return result;
    }
}