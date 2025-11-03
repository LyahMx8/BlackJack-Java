
package com.mycompany.blackjack3;

class ProcesadorCadenas {
    public static String invertir(String cadena) {
        Pila<Character> pila = new Pila<>();
        for (char c : cadena.toCharArray()) {
            pila.push(c);
        }
        
        StringBuilder resultado = new StringBuilder();
        while (!pila.estaVacia()) {
            resultado.append(pila.pop());
        }
        return resultado.toString();
    }
    
    public static boolean esPalindromo(String cadena) {
        String limpia = cadena.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return limpia.equals(new StringBuilder(limpia).reverse().toString());
    }
    
    public static int contarVocales(String cadena) {
        int contador = 0;
        String vocales = "aeiouAEIOU";
        for (char c : cadena.toCharArray()) {
            if (vocales.indexOf(c) != -1) {
                contador++;
            }
        }
        return contador;
    }
    
    public static String capitalizarPalabras(String cadena) {
        String[] palabras = cadena.split("\\s+");
        StringBuilder resultado = new StringBuilder();
        for (String palabra : palabras) {
            if (palabra.length() > 0) {
                resultado.append(Character.toUpperCase(palabra.charAt(0)));
                if (palabra.length() > 1) {
                    resultado.append(palabra.substring(1).toLowerCase());
                }
                resultado.append(" ");
            }
        }
        return resultado.toString().trim();
    }
}
