package com.mycompany.blackjack3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class BlackJack3 {

    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        menuPrincipal();
    }
    
    private static void menuPrincipal() {
        while (true) {
            System.out.println("\n" + "═".repeat(60));
            System.out.println("    SISTEMA DE BLACKJACK CON ESTRUCTURAS DE DATOS");
            System.out.println("═".repeat(60));
            System.out.println("\n1. Ver demostración de estructuras");
            System.out.println("2. Jugar Blackjack Multijugador");
            System.out.println("3. Salir");
            
            System.out.print("\nSelecciona una opción: ");
            String opcion = scanner.nextLine().trim();
            
            switch (opcion) {
                case "1" -> demoEstructuras();
                case "2" -> jugarBlackjack();
                case "3" -> {
                    System.out.println("\n¡Hasta pronto! 👋");
                    return;
                }
                default -> {
                    System.out.println("\n❌ Opción inválida");
                    pausa(1000);
                }
            }
        }
    }
    
    private static void demoEstructuras() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    DEMOSTRACIÓN DE ESTRUCTURAS DE DATOS");
        System.out.println("═".repeat(60));
        
        // 1. PILA (LIFO)
        System.out.println("\n1️⃣  PILA (LIFO - Last In First Out)");
        System.out.println("-".repeat(40));
        Pila<Integer> pila = new Pila<>();
        int[] elementos = {5, 2, 8, 1, 9};
        System.out.println("Agregando: " + Arrays.toString(elementos));
        for (int elem : elementos) {
            pila.push(elem);
        }
        
        System.out.println("Contenido (tope→base): " + pila.recorrer());
        System.out.println("Pop: " + pila.pop() + " (último en entrar, primero en salir)");
        System.out.println("Peek: " + pila.peek() + " (ver tope sin eliminar)");
        
        // 2. COLA (FIFO)
        System.out.println("\n\n2️⃣  COLA (FIFO - First In First Out)");
        System.out.println("-".repeat(40));
        Cola<String> cola = new Cola<>();
        String[] personas = {"Ana", "Bob", "Carlos"};
        System.out.println("Encolando: " + Arrays.toString(personas));
        for (String persona : personas) {
            cola.encolar(persona);
        }
        
        System.out.println("Desencolar: " + cola.desencolar() + " (primero en entrar, primero en salir)");
        System.out.println("Frente: " + cola.frente() + " (siguiente sin eliminar)");
        
        // 3. COLA DE PRIORIDAD
        System.out.println("\n\n3️⃣  COLA DE PRIORIDAD");
        System.out.println("-".repeat(40));
        ColaPrioridad<String> colaP = new ColaPrioridad<>();
        System.out.println("Agregando tareas (menor número = mayor prioridad):");
        colaP.encolar("Email", 3);
        System.out.println("  • Email (prioridad 3)");
        colaP.encolar("Urgente", 1);
        System.out.println("  • Urgente (prioridad 1)");
        colaP.encolar("Reunión", 2);
        System.out.println("  • Reunión (prioridad 2)");
        
        System.out.println("\nProcesando por prioridad:");
        while (!colaP.estaVacia()) {
            System.out.println("  → " + colaP.desencolar());
        }
        
        // 4. LISTA CIRCULAR SIMPLE
        System.out.println("\n\n4️⃣  LISTA CIRCULAR SIMPLE");
        System.out.println("-".repeat(40));
        ListaCircularSimple<String> circular = new ListaCircularSimple<>();
        String[] turnos = {"Jugador 1", "Jugador 2", "Jugador 3"};
        for (String turno : turnos) {
            circular.agregar(turno);
        }
        
        System.out.println("Turnos iniciales: " + circular.recorrer(circular.getTamano()));
        System.out.println("\nRotando 5 veces (simula pasar turnos):");
        for (int i = 0; i < 5; i++) {
            System.out.println("  Turno " + (i+1) + ": " + circular.obtener(0));
            circular.rotar(1);
        }
        
        // 5. LISTA CIRCULAR DOBLE
        System.out.println("\n\n5️⃣  LISTA CIRCULAR DOBLE");
        System.out.println("-".repeat(40));
        ListaCircularDoble<String> circularD = new ListaCircularDoble<>();
        String[] direcciones = {"Norte", "Este", "Sur", "Oeste"};
        for (String dir : direcciones) {
            circularD.agregar(dir);
        }
        
        System.out.println("Recorrido adelante: " + circularD.recorrer(circularD.getTamano()));
        System.out.println("Recorrido atrás: " + circularD.recorrerInverso(circularD.getTamano()));
        
        circularD.rotar(2);
        System.out.println("Después de rotar 2 posiciones: " + circularD.recorrer(circularD.getTamano()));
        
        // 6. ORDENAMIENTOS
        System.out.println("\n\n6️⃣  ORDENAMIENTOS");
        System.out.println("-".repeat(40));
        Integer[] numeros = {64, 34, 25, 12, 22, 11, 90};
        
        ListaSimple<Integer> listaSimple = new ListaSimple<>();
        for (int num : numeros) {
            listaSimple.agregar(num);
        }
        System.out.println("Lista original: " + listaSimple.recorrer());
        listaSimple.ordenarBurbuja(Integer::compareTo);
        System.out.println("Ordenada (burbuja): " + listaSimple.recorrer());
        
        ListaDoble<Integer> listaDoble = new ListaDoble<>();
        List<Integer> numerosShuffled = Arrays.asList(numeros);
        Collections.shuffle(numerosShuffled);
        for (int num : numerosShuffled) {
            listaDoble.agregar(num);
        }
        System.out.println("\nLista original: " + listaDoble.recorrer());
        listaDoble.ordenarQuicksort(Integer::compareTo);
        System.out.println("Ordenada (quicksort): " + listaDoble.recorrer());
        
        // 7. PROCESAMIENTO DE CADENAS
        System.out.println("\n\n7️⃣  PROCESAMIENTO DE CADENAS");
        System.out.println("-".repeat(40));
        String texto = "anita lava la tina";
        System.out.println("Texto: '" + texto + "'");
        System.out.println("Invertido: '" + ProcesadorCadenas.invertir(texto) + "'");
        System.out.println("¿Es palíndromo?: " + ProcesadorCadenas.esPalindromo(texto));
        System.out.println("Vocales: " + ProcesadorCadenas.contarVocales(texto));
        System.out.println("Capitalizado: '" + ProcesadorCadenas.capitalizarPalabras(texto) + "'");
        
        System.out.println("\n" + "═".repeat(60));
        System.out.print("\nPresiona Enter para volver al menú...");
        scanner.nextLine();
    }
    
    private static void jugarBlackjack() {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    ♠♥ BLACKJACK MULTIJUGADOR ♣♦");
        System.out.println("═".repeat(60));
        
        // Configurar jugadores
        int numJugadores = 0;
        while (numJugadores < 1 || numJugadores > 3) {
            try {
                System.out.print("\n¿Cuántos jugadores? (1-3): ");
                numJugadores = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un número válido.");
            }
        }
        
        // Lista circular para turnos
        ListaCircularDoble<Jugador> turnos = new ListaCircularDoble<>();
        List<Jugador> jugadores = new ArrayList<>();
        
        for (int i = 0; i < numJugadores; i++) {
            System.out.print("Nombre del Jugador " + (i+1) + ": ");
            String nombre = scanner.nextLine().trim();
            if (nombre.isEmpty()) {
                nombre = "Jugador " + (i+1);
            }
            nombre = ProcesadorCadenas.capitalizarPalabras(nombre);
            
            Jugador jugador = new Jugador(nombre, i+1);
            jugadores.add(jugador);
            turnos.agregar(jugador);
        }
        
        Jugador dealer = new Jugador("Crupier", 0);
        GameHistory history = new GameHistory();
        
        boolean jugarMas = true;
        int ronda = 0;
        
        while (jugarMas) {
            ronda++;
            System.out.println("\n" + "═".repeat(60));
            System.out.println("    RONDA " + ronda);
            System.out.println("═".repeat(60));
            
            // Nuevo mazo
            Deck deck = new Deck();
            deck.shuffle();
            
            // Reiniciar manos
            dealer.setMano(new Hand("Crupier"));
            for (Jugador jugador : jugadores) {
                if (jugador.isActivo()) {
                    jugador.setMano(new Hand(jugador.getNombre()));
                }
            }
            
            // Repartir cartas iniciales
            System.out.println("\n🎴 Repartiendo cartas...");
            for (int i = 0; i < 2; i++) {
                for (Jugador jugador : jugadores) {
                    if (jugador.isActivo()) {
                        jugador.getMano().addCard(deck.dealCard());
                    }
                }
                dealer.getMano().addCard(deck.dealCard());
            }
            
            pausa(1000);
            
            // Mostrar cartas iniciales
            System.out.println("\n" + "─".repeat(60));
            dealer.getMano().display(true);
            System.out.println("─".repeat(60));
            
            for (Jugador jugador : jugadores) {
                if (jugador.isActivo()) {
                    jugador.getMano().display(false);
                    System.out.println("   Puntos: " + jugador.getMano().getValue());
                }
            }
            
            // Turno de cada jugador
            for (Jugador jugador : turnos.recorrer(numJugadores)) {
                if (!jugador.isActivo()) {
                    continue;
                }
                
                System.out.println("\n" + "─".repeat(60));
                System.out.println("    Turno de " + jugador.getNombre());
                System.out.println("─".repeat(60));
                
                while (jugador.getMano().getValue() < 21) {
                    jugador.getMano().display(false);
                    System.out.println("Puntos: " + jugador.getMano().getValue());
                    
                    System.out.print("\n¿Pedir carta? (s/n): ");
                    String decision = scanner.nextLine().toLowerCase();
                    
                    if (decision.equals("s")) {
                        jugador.getMano().addCard(deck.dealCard());
                        System.out.println("\n" + jugador.getNombre() + " recibe una carta...");
                        
                        if (jugador.getMano().getValue() > 21) {
                            jugador.getMano().display(false);
                            System.out.println("💥 ¡" + jugador.getNombre() + " se pasó con " + 
                                jugador.getMano().getValue() + " puntos!");
                            jugador.setActivo(false);
                            break;
                        } else if (jugador.getMano().getValue() == 21) {
                            jugador.getMano().display(false);
                            System.out.println("🎯 ¡" + jugador.getNombre() + " tiene 21!");
                            break;
                        }
                    } else {
                        System.out.println(jugador.getNombre() + " se planta con " + 
                            jugador.getMano().getValue() + " puntos");
                        break;
                    }
                }
            }
            
            // Turno del crupier
            List<Jugador> jugadoresActivos = new ArrayList<>();
            for (Jugador j : jugadores) {
                if (j.isActivo() && j.getMano().getValue() <= 21) {
                    jugadoresActivos.add(j);
                }
            }
            
            if (!jugadoresActivos.isEmpty()) {
                System.out.println("\n" + "─".repeat(60));
                System.out.println("    Turno del Crupier");
                System.out.println("─".repeat(60));
                
                dealer.getMano().display(false);
                System.out.println("Puntos: " + dealer.getMano().getValue());
                
                while (dealer.getMano().getValue() < 17) {
                    pausa(1000);
                    System.out.println("\nEl crupier pide carta...");
                    dealer.getMano().addCard(deck.dealCard());
                    dealer.getMano().display(false);
                    System.out.println("Puntos: " + dealer.getMano().getValue());
                }
                
                if (dealer.getMano().getValue() > 21) {
                    System.out.println("\n💥 ¡El crupier se pasó con " + 
                        dealer.getMano().getValue() + " puntos!");
                }
            }
            
            // RESULTADOS
            System.out.println("\n" + "═".repeat(60));
            System.out.println("    RESULTADOS FINALES");
            System.out.println("═".repeat(60));
            
            int dealerValue = dealer.getMano().getValue();
            boolean dealerBusted = dealerValue > 21;
            
            // Cola de prioridad para resultados
            ColaPrioridad<ResultadoJuego> resultados = new ColaPrioridad<>();
            
            for (Jugador jugador : jugadores) {
                int jugadorValue = jugador.getMano().getValue();
                String resultado;
                int prioridad;
                
                if (jugadorValue > 21) {
                    resultado = "DERROTA";
                    jugador.incrementarDerrotas();
                    prioridad = 3;
                } else if (dealerBusted || jugadorValue > dealerValue) {
                    resultado = "VICTORIA";
                    jugador.incrementarVictorias();
                    prioridad = 1;
                } else if (jugadorValue == dealerValue) {
                    resultado = "EMPATE";
                    jugador.incrementarEmpates();
                    prioridad = 2;
                } else {
                    resultado = "DERROTA";
                    jugador.incrementarDerrotas();
                    prioridad = 3;
                }
                
                ResultadoJuego info = new ResultadoJuego(jugador.getNombre(), jugadorValue, resultado);
                resultados.encolar(info, prioridad);
            }
            
            // Mostrar resultados ordenados
            System.out.println("\nCrupier: " + dealerValue + " puntos");
            System.out.println("\n" + "─".repeat(60));
            
            int posicion = 1;
            while (!resultados.estaVacia()) {
                ResultadoJuego res = resultados.desencolar();
                String emoji;
                
                switch (res.resultado) {
                    case "VICTORIA":
                        emoji = "🏆";
                        break;
                    case "EMPATE":
                        emoji = "🤝";
                        break;
                    default:
                        emoji = "❌";
                }
                
                System.out.println(posicion + ". " + emoji + " " + res.nombre + ": " + 
                    res.puntos + " puntos - " + res.resultado);
                posicion++;
            }
            
            // Agregar al historial
            history.addGame("Ronda " + ronda + " - Crupier: " + dealerValue);
            
            // Mostrar estadísticas
            System.out.println("\n" + "═".repeat(60));
            System.out.println("    ESTADÍSTICAS GENERALES");
            System.out.println("═".repeat(60));
            
            // Ordenar jugadores por victorias
            ListaDoble<Jugador> listaStats = new ListaDoble<>();
            for (Jugador jugador : jugadores) {
                listaStats.agregar(jugador);
            }
            
            listaStats.ordenarQuicksort((j1, j2) -> Integer.compare(j2.getVictorias(), j1.getVictorias()));
            
            for (Jugador jugador : listaStats.recorrer()) {
                System.out.println("  " + jugador.getEstadisticas());
            }
            
            // Mostrar historial
            history.showHistory(3);
            
            // Reactivar jugadores para siguiente ronda
            for (Jugador jugador : jugadores) {
                jugador.setActivo(true);
            }
            
            // Preguntar si continuar
            System.out.println("\n" + "═".repeat(60));
            System.out.print("\n¿Jugar otra ronda? (s/n): ");
            String continuar = scanner.nextLine().toLowerCase();
            jugarMas = continuar.equals("s");
            
            if (jugarMas) {
                // Rotar turnos
                turnos.rotar(1);
                System.out.println("\n🔄 Rotando turnos para la próxima ronda...");
                pausa(1000);
            }
        }
        
        // Resumen final
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    RESUMEN FINAL");
        System.out.println("═".repeat(60));
        
        // Ordenar jugadores para podio
        ListaDoble<Jugador> listaFinal = new ListaDoble<>();
        for (Jugador jugador : jugadores) {
            listaFinal.agregar(jugador);
        }
        
        listaFinal.ordenarQuicksort((j1, j2) -> Integer.compare(j2.getVictorias(), j1.getVictorias()));
        
        System.out.println("\n🏆 PODIO FINAL 🏆\n");
        String[] medallas = {"🥇", "🥈", "🥉"};
        
        int pos = 0;
        for (Jugador jugador : listaFinal.recorrer()) {
            String medalla = (pos < 3) ? medallas[pos] : "  ";
            System.out.println(medalla + " " + jugador.getEstadisticas());
            pos++;
        }
        
        System.out.println("\n" + "═".repeat(60));
        System.out.println("    ¡Gracias por jugar!");
        System.out.println("═".repeat(60));
        
        System.out.print("\nPresiona Enter para volver al menú...");
        scanner.nextLine();
    }
    
    private static void pausa(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
        // Clase auxiliar para resultados
    static class ResultadoJuego {
        String nombre;
        int puntos;
        String resultado;
        
        public ResultadoJuego(String nombre, int puntos, String resultado) {
            this.nombre = nombre;
            this.puntos = puntos;
            this.resultado = resultado;
        }
    }
}
