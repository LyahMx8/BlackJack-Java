package com.mycompany.blackjack3;

import java.util.Collections;
import java.util.List;

class Deck {
    private ListaDoble<Card> cards;
    private Pila<Card> cardsDealt;
    
    public Deck() {
        this.cards = new ListaDoble<>();
        this.cardsDealt = new Pila<>();
        
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 13; j++) {
                cards.agregar(new Card(i, j));
            }
        }
    }
    
    public void shuffle() {
        List<Card> tempList = cards.recorrer();
        Collections.shuffle(tempList);
        
        cards = new ListaDoble<>();
        for (Card card : tempList) {
            cards.agregar(card);
        }
    }
    
    public Card dealCard() {
        if (cards.getCabeza() == null) {
            return null;
        }
        
        Card card = cards.getCabeza().dato;
        cards.setCabeza(cards.getCabeza().siguiente);
        if (cards.getCabeza() != null) {
            cards.getCabeza().anterior = null;
        } else {
            cards.setCola(null);
        }
        cards.setTamano(cards.getTamano() - 1);
        
        cardsDealt.push(card);
        return card;
    }
    
    public int cardsRemaining() {
        return cards.getTamano();
    }
}

