
package com.mycompany.blackjack3;

class Jugador {
    private String nombre;
    private int prioridad;
    private Hand mano;
    private int victorias;
    private int derrotas;
    private int empates;
    private boolean activo;
    
    public Jugador(String nombre, int prioridad) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.mano = new Hand(nombre);
        this.victorias = 0;
        this.derrotas = 0;
        this.empates = 0;
        this.activo = true;
    }
    
    public String getEstadisticas() {
        int total = victorias + derrotas + empates;
        if (total == 0) {
            return nombre + ": Sin juegos";
        }
        double winRate = (victorias * 100.0) / total;
        return String.format("%s: %dV/%dD/%dE (%.1f%%)", 
            nombre, victorias, derrotas, empates, winRate);
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public Hand getMano() { return mano; }
    public void setMano(Hand mano) { this.mano = mano; }
    public int getVictorias() { return victorias; }
    public void incrementarVictorias() { victorias++; }
    public void incrementarDerrotas() { derrotas++; }
    public void incrementarEmpates() { empates++; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
