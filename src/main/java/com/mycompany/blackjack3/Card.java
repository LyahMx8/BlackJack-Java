
package com.mycompany.blackjack3;

import java.util.HashMap;
import java.util.Map;

class Card {
    private String suit;
    private String name;
    private int points;
    
    public Card(int suitNum, int nameNum) {
        this.suit = getSuit(suitNum);
        this.name = getName(nameNum);
        this.points = getPoints(this.name);
    }
    
    private String getName(int i) {
        switch (i) {
            case 1: return "A";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: return String.valueOf(i);
        }
    }
    
    private int getPoints(String name) {
        if (name.equals("J") || name.equals("Q") || name.equals("K") || name.equals("10")) {
            return 10;
        } else if (name.equals("A")) {
            return 11;
        } else {
            return Integer.parseInt(name);
        }
    }
    
    private String getSuit(int i) {
        switch (i) {
            case 1: return "Diamonds";
            case 2: return "Clubs";
            case 3: return "Spades";
            case 4: return "Hearts";
            default: return "error";
        }
    }
    
    @Override
    public String toString() {
        Map<String, String> symbols = new HashMap<>();
        symbols.put("Diamonds", "♦");
        symbols.put("Clubs", "♣");
        symbols.put("Spades", "♠");
        symbols.put("Hearts", "♥");
        return name + " " + symbols.get(suit);
    }
    
    public int getPoints() {
        return points;
    }
    
    public String getName() {
        return name;
    }
}

