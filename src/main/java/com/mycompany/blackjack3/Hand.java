
package com.mycompany.blackjack3;

import java.util.List;

class Hand {
    private String nombre;
    private ListaSimple<Card> cards;
    
    public Hand(String nombre) {
        this.nombre = nombre;
        this.cards = new ListaSimple<>();
    }
    
    public void addCard(Card card) {
        if (card != null) {
            cards.agregar(card);
        }
    }
    
    public void display(boolean ocultarSegunda) {
        List<Card> cartas = cards.recorrer();
        StringBuilder sb = new StringBuilder(nombre + ": ");
        
        for (int i = 0; i < cartas.size(); i++) {
            if (ocultarSegunda && i == 1) {
                sb.append("[?] ");
            } else {
                sb.append(cartas.get(i).toString()).append(" | ");
            }
        }
        System.out.println(sb.toString());
    }
    
    public int getValue() {
        int total = 0;
        int aceCount = 0;
        
        for (Card card : cards.recorrer()) {
            total += card.getPoints();
            if (card.getName().equals("A")) {
                aceCount++;
            }
        }
        
        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        
        return total;
    }
    
    public int getCardCount() {
        return cards.getTamano();
    }
    
    public Card getCard(int index) {
        return cards.obtener(index);
    }
}
