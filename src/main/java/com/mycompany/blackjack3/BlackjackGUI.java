package com.mycompany.blackjack3;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Interfaz gráfica para el juego de Blackjack utilizando las estructuras y modelos existentes.
 * - Soporta 1 a 3 jugadores.
 * - Muestra manos de jugadores y crupier con cartas dibujadas (y oculta la segunda carta del crupier hasta su turno).
 * - Flujo: nueva partida → repartir → turnos jugador → turno crupier → resultados → nueva ronda.
 * - Si existen imágenes de cartas en la carpeta de imágenes, intenta usarlas; de lo contrario, dibuja las cartas.
 */
public class BlackjackGUI extends JFrame {

    private static final Path[] IMAGE_BASES = new Path[] {
        Paths.get("src", "main", "java", "com", "mycompany", "blackjack3", "src", "images"),
        Paths.get("src", "images"),
        Paths.get("images"),
        Paths.get("D:", "Disco", "Cursos", "UCompensar Estructuras de datos", "BlackJack", "src", "main", "java", "com", "mycompany", "blackjack3", "src", "images")
    };

    // Navegación entre pantallas
    private final JPanel rootPanel = new BackgroundPanel(new BorderLayout());
    private final MainMenuPanel mainMenuPanel = new MainMenuPanel();
    private final DemoPanel demoPanel = new DemoPanel();
    private final NewGamePanel newGamePanel = new NewGamePanel();
    private final GamePanel gamePanel = new GamePanel();

    public BlackjackGUI() {
        super("Blackjack - Estructuras de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 720));
        setLocationRelativeTo(null);

        rootPanel.add(mainMenuPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
    }

    private void showMainMenu() {
        rootPanel.removeAll();
        rootPanel.add(mainMenuPanel, BorderLayout.CENTER);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void showDemo() {
        rootPanel.removeAll();
        rootPanel.add(demoPanel, BorderLayout.CENTER);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void showNewGame() {
        rootPanel.removeAll();
        rootPanel.add(newGamePanel, BorderLayout.CENTER);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void showGame() {
        rootPanel.removeAll();
        rootPanel.add(gamePanel, BorderLayout.CENTER);
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    // Panel: Menú principal
    private class MainMenuPanel extends JPanel {
        MainMenuPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 24, 24, 24),
                BorderFactory.createLineBorder(new Color(30, 30, 30), 1)
            ));
            card.setBackground(new Color(245, 245, 245));

            JLabel title = new JLabel("Blackjack", SwingConstants.CENTER);
            title.setAlignmentX(CENTER_ALIGNMENT);
            title.setFont(new Font("SansSerif", Font.BOLD, 36));
            title.setBorder(BorderFactory.createEmptyBorder(8, 8, 16, 8));

            JButton btnDemo = new JButton("1. Ver demostración de estructuras");
            btnDemo.setAlignmentX(CENTER_ALIGNMENT);
            btnDemo.addActionListener(e -> showDemo());

            JButton btnNewGame = new JButton("2. Jugar Blackjack Multijugador (GUI)");
            btnNewGame.setAlignmentX(CENTER_ALIGNMENT);
            btnNewGame.addActionListener(e -> showNewGame());

            JButton btnExit = new JButton("Salir");
            btnExit.setAlignmentX(CENTER_ALIGNMENT);
            btnExit.addActionListener(e -> System.exit(0));

            card.add(title);
            card.add(Box.createRigidArea(new Dimension(0, 12)));
            card.add(btnDemo);
            card.add(Box.createRigidArea(new Dimension(0, 8)));
            card.add(btnNewGame);
            card.add(Box.createRigidArea(new Dimension(0, 8)));
            card.add(btnExit);

            add(card, gbc);
        }
    }

    // Panel: Demo (equivalente a demoEstructuras en consola)
    private class DemoPanel extends JPanel {
        private final javax.swing.JTextArea area = new javax.swing.JTextArea();

        DemoPanel() {
            setLayout(new BorderLayout(8, 8));
            area.setEditable(false);
            area.setFont(new Font("Consolas", Font.PLAIN, 14));

            JPanel top = new JPanel(new BorderLayout());
            JLabel title = new JLabel("Demostración de Estructuras de Datos", SwingConstants.CENTER);
            title.setFont(new Font("SansSerif", Font.BOLD, 20));
            top.add(title, BorderLayout.CENTER);

            JPanel actions = new JPanel();
            JButton btnRun = new JButton("Ejecutar Demo");
            JButton btnBack = new JButton("Volver al Menú");
            btnBack.addActionListener(e -> showMainMenu());
            btnRun.addActionListener(e -> runDemo());
            actions.add(btnRun);
            actions.add(btnBack);

            add(top, BorderLayout.NORTH);
            add(new JScrollPane(area), BorderLayout.CENTER);
            add(actions, BorderLayout.SOUTH);

            runDemo();
        }

        private void runDemo() {
            StringBuilder sb = new StringBuilder();
            sb.append("1) PILA (LIFO)\n");
            Pila<Integer> pila = new Pila<>();
            int[] elementos = {5, 2, 8, 1, 9};
            for (int e : elementos) pila.push(e);
            sb.append("   Contenido (tope→base): ").append(pila.recorrer()).append("\n");
            sb.append("   Pop: ").append(pila.pop()).append("\n");
            sb.append("   Peek: ").append(pila.peek()).append("\n\n");

            sb.append("2) COLA (FIFO)\n");
            Cola<String> cola = new Cola<>();
            for (String s : Arrays.asList("Ana", "Bob", "Carlos")) cola.encolar(s);
            sb.append("   Desencolar: ").append(cola.desencolar()).append("\n");
            sb.append("   Frente: ").append(cola.frente()).append("\n\n");

            sb.append("3) COLA DE PRIORIDAD\n");
            ColaPrioridad<String> cp = new ColaPrioridad<>();
            cp.encolar("Email", 3); cp.encolar("Urgente", 1); cp.encolar("Reunión", 2);
            sb.append("   Procesando por prioridad:\n");
            while (!cp.estaVacia()) sb.append("     → ").append(cp.desencolar()).append("\n");
            sb.append("\n");

            sb.append("4) LISTA CIRCULAR SIMPLE (rotación)\n");
            ListaCircularSimple<String> circ = new ListaCircularSimple<>();
            for (String t : Arrays.asList("J1","J2","J3")) circ.agregar(t);
            sb.append("   Inicial: ").append(circ.recorrer(circ.getTamano())).append("\n");
            circ.rotar(1);
            sb.append("   Tras rotar 1: ").append(circ.recorrer(circ.getTamano())).append("\n\n");

            sb.append("5) LISTA CIRCULAR DOBLE\n");
            ListaCircularDoble<String> circD = new ListaCircularDoble<>();
            for (String d : Arrays.asList("N","E","S","O")) circD.agregar(d);
            sb.append("   Adelante: ").append(circD.recorrer(circD.getTamano())).append("\n");
            sb.append("   Atrás: ").append(circD.recorrerInverso(circD.getTamano())).append("\n\n");

            sb.append("6) ORDENAMIENTOS\n");
            ListaSimple<Integer> ls = new ListaSimple<>();
            for (int n : Arrays.asList(64,34,25,12,22,11,90)) ls.agregar(n);
            sb.append("   ListaSimple original: ").append(ls.recorrer()).append("\n");
            ls.ordenarBurbuja(Integer::compareTo);
            sb.append("   Burbuja: ").append(ls.recorrer()).append("\n");
            ListaDoble<Integer> ld = new ListaDoble<>();
            for (int n : Arrays.asList(64,34,25,12,22,11,90)) ld.agregar(n);
            ld.ordenarQuicksort(Integer::compareTo);
            sb.append("   Quicksort: ").append(ld.recorrer()).append("\n\n");

            sb.append("7) PROCESAMIENTO DE CADENAS\n");
            String texto = "anita lava la tina";
            sb.append("   Invertir: ").append(ProcesadorCadenas.invertir(texto)).append("\n");
            sb.append("   Palíndromo: ").append(ProcesadorCadenas.esPalindromo(texto)).append("\n");
            sb.append("   Vocales: ").append(ProcesadorCadenas.contarVocales(texto)).append("\n");
            sb.append("   Capitalizar: ").append(ProcesadorCadenas.capitalizarPalabras(texto)).append("\n");

            area.setText(sb.toString());
            area.setCaretPosition(0);
        }
    }

    // Panel: Nueva partida (configurar jugadores)
    private class NewGamePanel extends JPanel {
        private final JComboBox<Integer> playersCombo = new JComboBox<>(new Integer[]{1, 2, 3});
        private final JLabel lbl1 = new JLabel("Jugador 1:");
        private final JLabel lbl2 = new JLabel("Jugador 2:");
        private final JLabel lbl3 = new JLabel("Jugador 3:");
        private final JTextField name1 = new JTextField("Jugador 1", 16);
        private final JTextField name2 = new JTextField("Jugador 2", 16);
        private final JTextField name3 = new JTextField("Jugador 3", 16);
        private final JPanel namesPanel = new JPanel(new GridLayout(3, 2, 8, 8));

        NewGamePanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.CENTER;

            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(24, 24, 24, 24),
                BorderFactory.createLineBorder(new Color(30, 30, 30), 1)
            ));
            card.setBackground(new Color(245, 245, 245));

            JLabel title = new JLabel("Nueva Partida", SwingConstants.CENTER);
            title.setAlignmentX(CENTER_ALIGNMENT);
            title.setFont(new Font("SansSerif", Font.BOLD, 28));

            JPanel playersRow = new JPanel();
            playersRow.add(new JLabel("Número de jugadores:"));
            playersRow.add(playersCombo);

            namesPanel.add(lbl1);
            namesPanel.add(name1);
            namesPanel.add(lbl2);
            namesPanel.add(name2);
            namesPanel.add(lbl3);
            namesPanel.add(name3);

            JButton btnBack = new JButton("Volver");
            btnBack.addActionListener(e -> showMainMenu());

            JButton btnStart = new JButton("Comenzar");
            btnStart.addActionListener(e -> {
                int n = Objects.requireNonNull((Integer) playersCombo.getSelectedItem());
                List<String> nombres = new ArrayList<>();
                if (n >= 1) nombres.add(ProcesadorCadenas.capitalizarPalabras(name1.getText().trim().isEmpty() ? "Jugador 1" : name1.getText()));
                if (n >= 2) nombres.add(ProcesadorCadenas.capitalizarPalabras(name2.getText().trim().isEmpty() ? "Jugador 2" : name2.getText()));
                if (n >= 3) nombres.add(ProcesadorCadenas.capitalizarPalabras(name3.getText().trim().isEmpty() ? "Jugador 3" : name3.getText()));

                gamePanel.controller.startNewGame(nombres);
                showGame();
            });

            JPanel actions = new JPanel();
            actions.add(btnBack);
            actions.add(btnStart);

            playersCombo.addActionListener(e -> updateNameFieldsVisibility());
            updateNameFieldsVisibility();

            card.add(title);
            card.add(Box.createRigidArea(new Dimension(0, 12)));
            card.add(playersRow);
            card.add(Box.createRigidArea(new Dimension(0, 12)));
            card.add(namesPanel);
            card.add(Box.createRigidArea(new Dimension(0, 12)));
            card.add(actions);

            add(card, gbc);
        }

        private void updateNameFieldsVisibility() {
            int n = Objects.requireNonNull((Integer) playersCombo.getSelectedItem());
            boolean show2 = n >= 2;
            boolean show3 = n >= 3;
            lbl2.setVisible(show2);
            name2.setVisible(show2);
            lbl3.setVisible(show3);
            name3.setVisible(show3);
            namesPanel.revalidate();
            namesPanel.repaint();
        }
    }

    // (Se removió el panel SinglePlayer para revertir a la versión previa)

    // Panel: Juego
    private class GamePanel extends JPanel {
        private final JLabel statusBar = new JLabel("Listo.");
        private final JPanel dealerPanel = new JPanel();
        private final JPanel playersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        private final JButton btnHit;
        private final JButton btnStand;
        private final JButton btnNextRound;
        private final JButton btnBackToMenu;

        private final GameController controller = new GameController(this);

        GamePanel() {
            setLayout(new BorderLayout(8, 8));
            setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            setOpaque(false);

            // Modern buttons
            btnHit = new RoundButton("Pedir (Hit)", new Color(76, 175, 80));           // green
            btnStand = new RoundButton("Plantarse (Stand)", new Color(244, 67, 54));   // red
            btnNextRound = new RoundButton("Nueva Ronda", new Color(33, 150, 243));    // blue
            btnBackToMenu = new RoundButton("Volver al Menú", new Color(96, 125, 139)); // blue-grey

            // Header
            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);
            JLabel title = new JLabel("Black Jack", SwingConstants.CENTER);
            title.setForeground(Color.WHITE);
            title.setFont(new Font("SansSerif", Font.BOLD, 28));
            title.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            header.add(title, BorderLayout.CENTER);

            // Dealer
            JPanel dealerCard = new JPanel(new BorderLayout());
            dealerCard.setOpaque(false);
            dealerCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            dealerPanel.setOpaque(false);
            dealerCard.add(dealerPanel, BorderLayout.CENTER);

            // Players
            JPanel playersCard = new JPanel(new BorderLayout());
            playersCard.setOpaque(false);
            playersCard.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            playersPanel.setOpaque(false);
            playersCard.add(playersPanel, BorderLayout.CENTER);

            // Controls
            JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
            controls.setOpaque(false);
            btnHit.addActionListener(e -> controller.playerHit());
            btnStand.addActionListener(e -> controller.playerStand());
            btnNextRound.addActionListener(e -> controller.nextRoundOrFinish());
            btnBackToMenu.addActionListener(e -> showMainMenu());
            btnHit.setToolTipText("Pedir una carta (Hit)");
            btnStand.setToolTipText("Plantarse (Stand)");
            btnNextRound.setToolTipText("Comenzar la siguiente ronda o terminar");
            btnBackToMenu.setToolTipText("Regresar al menú principal");
            controls.add(btnHit);
            controls.add(btnStand);
            controls.add(btnNextRound);
            controls.add(btnBackToMenu);

            // Bottom bar with status + controls
            JPanel bottomBar = new JPanel(new BorderLayout());
            bottomBar.setOpaque(false);
            statusBar.setForeground(Color.WHITE);
            bottomBar.add(statusBar, BorderLayout.NORTH);
            bottomBar.add(controls, BorderLayout.SOUTH);

            add(header, BorderLayout.PAGE_START);
            add(dealerCard, BorderLayout.BEFORE_FIRST_LINE);
            JScrollPane sp = new JScrollPane(playersCard);
            sp.setBorder(BorderFactory.createEmptyBorder());
            sp.setOpaque(false);
            sp.getViewport().setOpaque(false);
            add(sp, BorderLayout.CENTER);
            add(bottomBar, BorderLayout.PAGE_END);

            updateButtonsState(false, false, false);
        }

        void render() {
            dealerPanel.removeAll();
            playersPanel.removeAll();

            // Dealer hand
            HandView dealerView = new HandView(controller.dealer.getNombre(), controller.dealer.getMano(), controller.dealerHideSecond);
            dealerPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
            dealerPanel.add(dealerView);

            // Players hands
            for (int i = 0; i < controller.players.size(); i++) {
                Jugador jugador = controller.players.get(i);
                PlayerView pv = new PlayerView(jugador, i, controller);
                playersPanel.add(pv);
            }

            dealerPanel.revalidate();
            dealerPanel.repaint();
            playersPanel.revalidate();
            playersPanel.repaint();

            String turno = controller.isPlayersPhase()
                ? "Turno de: " + controller.players.get(controller.currentPlayerIndex).getNombre()
                : (controller.roundActive ? "Turno del crupier..." : "Ronda finalizada.");
            int restantes = controller.getDeckRemaining();
            int ronda = controller.getRonda();
            statusBar.setText("Ronda " + ronda + " | " + turno + " | Cartas restantes en mazo: " + restantes);
        }

        void updateButtonsState(boolean playing, boolean playersTurn, boolean canNextRound) {
            btnHit.setEnabled(playing && playersTurn);
            btnStand.setEnabled(playing && playersTurn);
            btnNextRound.setEnabled(canNextRound);
        }

        void backToMenu() {
            BlackjackGUI.this.showMainMenu();
        }
    }

    // Controlador de juego (estado y reglas para la GUI)
    private static class GameController {
        private final GamePanel ui;
        private List<Jugador> players = new ArrayList<>();
        private Jugador dealer = new Jugador("Crupier", 0);
        private Deck deck;
        private int currentPlayerIndex = 0;
        private boolean roundActive = false;
        private boolean dealerHideSecond = true;
        private final GameHistory history = new GameHistory();
        private int ronda = 0;

        GameController(GamePanel ui) {
            this.ui = ui;
        }

        int getDeckRemaining() {
            return (deck == null) ? 0 : deck.cardsRemaining();
        }

        int getRonda() {
            return ronda;
        }

        void startNewGame(List<String> nombres) {
            players = new ArrayList<>();
            int i = 1;
            for (String nombre : nombres) {
                players.add(new Jugador(nombre, i++));
            }
            ronda = 0;
            startRound();
        }

        void startRound() {
            if (players.isEmpty()) return;
            if (ronda >= 1 && players.size() > 1) {
                Collections.rotate(players, -1); // rotar orden de salida como en ListaCircularDoble
            }
            ronda++;
            deck = new Deck();
            deck.shuffle();
            dealer.setMano(new Hand("Crupier"));
            for (Jugador j : players) {
                j.setMano(new Hand(j.getNombre()));
                j.setActivo(true);
            }
            // deal two cards to each player and dealer
            for (int r = 0; r < 2; r++) {
                for (Jugador j : players) {
                    if (j.isActivo()) {
                        j.getMano().addCard(deck.dealCard());
                    }
                }
                dealer.getMano().addCard(deck.dealCard());
            }
            dealerHideSecond = true;
            roundActive = true;
            currentPlayerIndex = 0;
            advanceToNextActiveIfNeeded();
            ui.updateButtonsState(true, isPlayersPhase(), false);
            ui.render();
        }

        boolean isPlayersPhase() {
            return roundActive && currentPlayerIndex < players.size();
        }

        boolean isCurrentPlayerIndex(int idx) {
            return isPlayersPhase() && currentPlayerIndex == idx;
        }

        void playerHit() {
            if (!isPlayersPhase()) return;
            Jugador j = players.get(currentPlayerIndex);
            j.getMano().addCard(deck.dealCard());
            int v = j.getMano().getValue();
            if (v > 21) {
                j.setActivo(false);
                nextPlayer();
            } else if (v == 21) {
                nextPlayer();
            }
            ui.render();
        }

        void playerHitByIndex(int idx) {
            if (isCurrentPlayerIndex(idx)) {
                playerHit();
            }
        }

        void playerStand() {
            if (!isPlayersPhase()) return;
            nextPlayer();
            ui.render();
        }

        void playerStandByIndex(int idx) {
            if (isCurrentPlayerIndex(idx)) {
                playerStand();
            }
        }

        private void nextPlayer() {
            currentPlayerIndex++;
            advanceToNextActiveIfNeeded();
            if (!isPlayersPhase()) {
                // Dealer phase
                ui.updateButtonsState(true, false, false);
                dealerTurn();
            } else {
                ui.updateButtonsState(true, true, false);
            }
        }

        private void advanceToNextActiveIfNeeded() {
            while (currentPlayerIndex < players.size()
                && (!players.get(currentPlayerIndex).isActivo()
                    || players.get(currentPlayerIndex).getMano().getValue() > 21)) {
                currentPlayerIndex++;
            }
        }

        private void dealerTurn() {
            dealerHideSecond = false;
            ui.render();

            // Animar pequeñas pausas al pedir cartas del crupier
            Timer t = new Timer(700, e -> {
                if (dealer.getMano().getValue() < 17) {
                    dealer.getMano().addCard(deck.dealCard());
                    ui.render();
                } else {
                    ((Timer) e.getSource()).stop();
                    endRoundAndShowResults();
                }
            });
            t.setRepeats(true);
            t.start();
        }

        private void endRoundAndShowResults() {
            int dealerValue = dealer.getMano().getValue();
            boolean dealerBusted = dealerValue > 21;

            ColaPrioridad<Resultado> resultados = new ColaPrioridad<>();
            StringBuilder resumen = new StringBuilder("Crupier: " + dealerValue + " puntos\n");

            for (Jugador j : players) {
                int jugadorValue = j.getMano().getValue();
                String res;
                int prioridad;
                if (jugadorValue > 21) {
                    res = "DERROTA";
                    j.incrementarDerrotas();
                    prioridad = 3;
                } else if (dealerBusted || jugadorValue > dealerValue) {
                    res = "VICTORIA";
                    j.incrementarVictorias();
                    prioridad = 1;
                } else if (jugadorValue == dealerValue) {
                    res = "EMPATE";
                    j.incrementarEmpates();
                    prioridad = 2;
                } else {
                    res = "DERROTA";
                    j.incrementarDerrotas();
                    prioridad = 3;
                }
                resultados.encolar(new Resultado(j.getNombre(), jugadorValue, res), prioridad);
            }

            int pos = 1;
            while (!resultados.estaVacia()) {
                Resultado r = resultados.desencolar();
                resumen.append(pos++).append(". ").append(r.nombre)
                    .append(": ").append(r.puntos).append(" pts - ").append(r.resultado).append("\n");
            }

            history.addGame("Ronda " + ronda + " - Crupier: " + dealerValue);
            roundActive = false;
            ui.updateButtonsState(false, false, true);
            ui.render();

            // Construir estadísticas generales (orden por victorias desc)
            ListaDoble<Jugador> listaStats = new ListaDoble<>();
            for (Jugador j : players) listaStats.agregar(j);
            listaStats.ordenarQuicksort((j1, j2) -> Integer.compare(j2.getVictorias(), j1.getVictorias()));
            StringBuilder stats = new StringBuilder("ESTADÍSTICAS GENERALES\n");
            for (Jugador j : listaStats.recorrer()) {
                stats.append("  ").append(j.getEstadisticas()).append("\n");
            }

            // Historial (últimos 3)
            List<String> ultimos = history.getLast(3);
            StringBuilder hist = new StringBuilder("ÚLTIMOS RESULTADOS\n");
            if (ultimos.isEmpty()) {
                hist.append("  No hay historial.\n");
            } else {
                int i = 1;
                for (String h : ultimos) {
                    hist.append("  ").append(i++).append(". ").append(h).append("\n");
                }
            }

            String mensaje = resumen + "\n" + stats + "\n" + hist;
            JOptionPane.showMessageDialog(ui, mensaje, "Resultados de la Ronda", JOptionPane.INFORMATION_MESSAGE);
        }

        void nextRoundOrFinish() {
            int r = JOptionPane.showConfirmDialog(ui, "¿Jugar otra ronda?", "Continuar", JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) {
                startRound();
            } else {
                showFinalPodiumAndReturn();
            }
        }

        private void showFinalPodiumAndReturn() {
            ListaDoble<Jugador> listaFinal = new ListaDoble<>();
            for (Jugador j : players) listaFinal.agregar(j);
            listaFinal.ordenarQuicksort((j1, j2) -> Integer.compare(j2.getVictorias(), j1.getVictorias()));
            String[] medallas = {"🥇","🥈","🥉"};
            StringBuilder sb = new StringBuilder("🏆 PODIO FINAL 🏆\n\n");
            int pos = 0;
            for (Jugador j : listaFinal.recorrer()) {
                String med = (pos < 3) ? medallas[pos] : "  ";
                sb.append(med).append(" ").append(j.getEstadisticas()).append("\n");
                pos++;
            }
            JOptionPane.showMessageDialog(ui, sb.toString(), "Resumen Final", JOptionPane.INFORMATION_MESSAGE);
            ui.backToMenu();
        }

        static class Resultado {
            final String nombre;
            final int puntos;
            final String resultado;
            Resultado(String n, int p, String r) { this.nombre = n; this.puntos = p; this.resultado = r; }
        }
    }

    // Vista de jugador (panel con nombre, puntos y su mano)
    private static class PlayerView extends JPanel {
        PlayerView(Jugador jugador, int index, GameController controller) {
            setLayout(new BorderLayout());
            boolean isMyTurn = controller.isCurrentPlayerIndex(index);
            setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
            setOpaque(false);
            JLabel name = new JLabel(jugador.getNombre(), SwingConstants.CENTER);
            name.setFont(new Font("SansSerif", Font.BOLD, 16));
            name.setForeground(isMyTurn ? new Color(233, 243, 97) : Color.WHITE);
            JLabel points = new JLabel("Puntos: " + jugador.getMano().getValue(), SwingConstants.CENTER);
            points.setForeground(Color.WHITE);
            points.setOpaque(false);

            HandView hv = new HandView(jugador.getNombre(), jugador.getMano(), false);

            JPanel actions = new TransparentPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
            JButton btnHit = new RoundButton("Pedir", new Color(76, 175, 80));
            JButton btnStand = new RoundButton("Plantarse", new Color(244, 67, 54));
            btnHit.setToolTipText("Pedir carta para " + jugador.getNombre());
            btnStand.setToolTipText("Plantarse para " + jugador.getNombre());
            btnHit.addActionListener(e -> controller.playerHitByIndex(index));
            btnStand.addActionListener(e -> controller.playerStandByIndex(index));
            btnHit.setEnabled(isMyTurn);
            btnStand.setEnabled(isMyTurn);
            actions.add(btnHit);
            actions.add(btnStand);

            add(name, BorderLayout.NORTH);
            add(hv, BorderLayout.CENTER);
            JPanel south = new TransparentPanel(new BorderLayout());
            south.add(points, BorderLayout.NORTH);
            south.add(actions, BorderLayout.SOUTH);
            add(south, BorderLayout.SOUTH);
        }
    }

    // Vista de mano: muestra las cartas como componentes independientes
    private static class HandView extends JPanel {
        HandView(String owner, Hand hand, boolean hideSecond) {
            setOpaque(false);
            setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 8));
            for (int i = 0; i < hand.getCardCount(); i++) {
                Card c = hand.getCard(i);
                boolean faceDown = hideSecond && i == 1;
                add(new CardView(c, faceDown));
            }
        }

        @Override
        public Dimension getPreferredSize() {
            // Calcular tamaño preferido sumando los preferidos de las cartas + gaps
            int count = getComponentCount();
            if (count == 0) {
                return new Dimension(0, 0);
            }
            int w = 0;
            int h = 0;
            for (int i = 0; i < count; i++) {
                Dimension d = getComponent(i).getPreferredSize();
                w += d.width;
                h = Math.max(h, d.height);
            }
            // gaps del FlowLayout (aprox 8 px entre cartas)
            w += Math.max(0, (count - 1)) * 8;
            // algo de margen
            w += 8;
            h += 8;
            return new Dimension(w, h);
        }
    }

    // Panel totalmente transparente (no pinta fondo aunque el LAF lo intente)
    private static class TransparentPanel extends JPanel {
        TransparentPanel(java.awt.LayoutManager layout) {
            super(layout);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder());
        }
        @Override
        protected void paintComponent(Graphics g) {
            // Intencionalmente vacío para no pintar fondo
        }
    }

    // Componente de carta (dibuja o intenta cargar imagen)
    private static class CardView extends JComponent {
        private static final int WIDTH = 110;
        private static final int HEIGHT = 154;
        private static final Color CARD_RED = new Color(200, 0, 0);
        private static final Color CARD_BLACK = new Color(20, 20, 20);
        private static java.awt.Image BACK_IMAGE = null;
        private static boolean BACK_TRIED = false;
        private final Card card;
        private final boolean faceDown;
        private java.awt.Image cachedImage;

        CardView(Card card, boolean faceDown) {
            this.card = card;
            this.faceDown = faceDown;
            setPreferredSize(new Dimension(WIDTH, HEIGHT));
            setMinimumSize(new Dimension(WIDTH, HEIGHT));
            setMaximumSize(new Dimension(WIDTH, HEIGHT));
            tryLoadImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();

            // Fondo
            // Sombra ligera
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(4, 6, w - 4, h - 4, 12, 12);
            g2.setColor(new Color(255, 255, 255));
            g2.fillRoundRect(2, 2, w - 4, h - 4, 12, 12);
            g2.setColor(new Color(180, 180, 180));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(2, 2, w - 4, h - 4, 12, 12);

            if (faceDown) {
                // Dorso de la carta
                java.awt.Image back = tryLoadBackImage();
                if (back != null) {
                    int iw = back.getWidth(null);
                    int ih = back.getHeight(null);
                    float scale = Math.min((w - 16f) / iw, (h - 16f) / ih);
                    int dw = (int) (iw * scale);
                    int dh = (int) (ih * scale);
                    int dx = (w - dw) / 2;
                    int dy = (h - dh) / 2;
                    g2.drawImage(back, dx, dy, dw, dh, null);
                } else {
                    GradientPaint gp = new GradientPaint(4, 4, new Color(10, 80, 180), w - 4, h - 4, new Color(20, 20, 120));
                    g2.setPaint(gp);
                    g2.fillRoundRect(6, 6, w - 12, h - 12, 10, 10);
                    g2.setColor(Color.WHITE);
                    g2.drawRoundRect(6, 6, w - 12, h - 12, 10, 10);
                }
                g2.dispose();
                return;
            }

            // Si hay imagen, dibujarla centrada
            if (cachedImage != null) {
                int iw = cachedImage.getWidth(null);
                int ih = cachedImage.getHeight(null);
                float scale = Math.min((w - 16f) / iw, (h - 16f) / ih);
                int dw = (int) (iw * scale);
                int dh = (int) (ih * scale);
                int dx = (w - dw) / 2;
                int dy = (h - dh) / 2;
                g2.drawImage(cachedImage, dx, dy, dw, dh, null);
                g2.dispose();
                return;
            }

            // Dibujo programático
            String[] rankAndSymbol = parseRankAndSymbol(card);
            String rank = rankAndSymbol[0];
            String symbol = rankAndSymbol[1];
            boolean isRed = "♥".equals(symbol) || "♦".equals(symbol);
            Color fg = isRed ? CARD_RED : CARD_BLACK;

            g2.setColor(fg);
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2.drawString(rank, 10, 22);
            g2.drawString(symbol, 10, 40);

            g2.setFont(new Font("SansSerif", Font.BOLD, 46));
            g2.drawString(symbol, w / 2 - 14, h / 2 + 16);

            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            String br = new StringBuilder(rank).reverse().toString();
            g2.drawString(br, w - 10 - g2.getFontMetrics().stringWidth(br), h - 10);

            g2.dispose();
        }

        private static java.awt.Image tryLoadBackImage() {
            if (BACK_TRIED) return BACK_IMAGE;
            BACK_TRIED = true;
            String[] candidates = new String[] {"dorso.png"};
            for (Path base : IMAGE_BASES) {
                if (!Files.isDirectory(base)) continue;
                for (String name : candidates) {
                    Path p = base.resolve(name);
                    if (Files.exists(p)) {
                        BACK_IMAGE = new javax.swing.ImageIcon(p.toString()).getImage();
                        return BACK_IMAGE;
                    }
                }
            }
            return null;
        }

        private void tryLoadImage() {
            // Intenta cargar una imagen desde la carpeta de imágenes del proyecto si existe.
            // Se busca por coincidencia difusa usando rango y palo (en inglés).
            try {
                String[] rankAndSymbol = parseRankAndSymbol(card);
                String rank = rankAndSymbol[0]; // e.g. "A", "10", "K"
                String suitName = normalizeSuit(rankAndSymbol[1]); // hearts, diamonds, clubs, spades

                Optional<Path> found = Optional.empty();
                for (Path base : IMAGE_BASES) {
                    if (Files.isDirectory(base)) {
                        try (var stream = Files.list(base)) {
                            Optional<Path> match = stream
                                .filter(p -> {
                                    String fn = p.getFileName().toString().toLowerCase();
                                    return (fn.endsWith(".png") || fn.endsWith(".jpg") || fn.endsWith(".jpeg"))
                                        && fn.contains(rank.toLowerCase())
                                        && fn.contains(suitName.toLowerCase());
                                })
                                .findFirst();
                            if (match.isPresent()) {
                                found = match;
                                break;
                            }
                        }
                    }
                }
                if (found.isPresent()) {
                    cachedImage = javax.imageio.ImageIO.read(found.get().toFile());
                } else {
                    cachedImage = null;
                }
            } catch (IOException ex) {
                cachedImage = null;
            }
        }

        private static String[] parseRankAndSymbol(Card c) {
            // Card.toString() -> "A ♥"
            String s = c.toString().trim();
            String[] parts = s.split("\\s+");
            String rank = parts.length > 0 ? parts[0] : "?";
            String symbol = parts.length > 1 ? parts[1] : "♠";
            return new String[]{rank, symbol};
        }

        private static String normalizeSuit(String symbol) {
            return switch (symbol) {
                case "♥" -> "hearts";
                case "♦" -> "diamonds";
                case "♣" -> "clubs";
                case "♠" -> "spades";
                default -> "spades";
            };
        }
    }

    // Botón redondeado con estilo moderno
    private static class RoundButton extends JButton {
        private final Color base;
        private final Color hover;
        private final Color press;
        private Color current;
        private final int radius = 14;

        RoundButton(String text, Color base) {
            super(text);
            this.base = base;
            this.hover = base.brighter();
            this.press = base.darker();
            this.current = base;
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

            addChangeListener(e -> {
                if (getModel().isArmed() || getModel().isPressed()) {
                    current = press;
                } else if (getModel().isRollover()) {
                    current = hover;
                } else {
                    current = base;
                }
                repaint();
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 40));
            g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, radius + 2, radius + 2);
            g2.setColor(current);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Panel de fondo con imagen (se pinta escalada a toda el área)
    private static class BackgroundPanel extends JPanel {
        private static java.awt.Image BG_IMAGE = null;
        private static boolean BG_TRIED = false;

        BackgroundPanel(BorderLayout layout) {
            super(layout);
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            java.awt.Image img = tryLoadBackgroundImage();
            if (img != null) {
                int w = getWidth();
                int h = getHeight();
                int iw = img.getWidth(null);
                int ih = img.getHeight(null);
                if (iw > 0 && ih > 0) {
                    // Cover
                    double scale = Math.max((double) w / iw, (double) h / ih);
                    int dw = (int) (iw * scale);
                    int dh = (int) (ih * scale);
                    int dx = (w - dw) / 2;
                    int dy = (h - dh) / 2;
                    g.drawImage(img, dx, dy, dw, dh, null);
                } else {
                    g.drawImage(img, 0, 0, w, h, null);
                }
            } else {
                // fallback liso
                g.setColor(new Color(34, 80, 56));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }

        private static java.awt.Image tryLoadBackgroundImage() {
            if (BG_TRIED) return BG_IMAGE;
            BG_TRIED = true;
            String[] candidates = new String[] {"background.png"};
            for (Path base : IMAGE_BASES) {
                if (!Files.isDirectory(base)) continue;
                for (String name : candidates) {
                    Path p = base.resolve(name);
                    if (Files.exists(p)) {
                        BG_IMAGE = new javax.swing.ImageIcon(p.toString()).getImage();
                        return BG_IMAGE;
                    }
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}


