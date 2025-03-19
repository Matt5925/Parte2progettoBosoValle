package GestioneBanca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gui extends JFrame {
    private JFrame frame;
    private JTextArea textArea;
    private Utente utente;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Gui window = new Gui();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Gui() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Text area to display information
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Panel for Buttons (Login and Register)
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        panel.setLayout(new FlowLayout());

        // Accesso/Registrazione buttons
        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Register");
        panel.add(btnLogin);
        panel.add(btnRegister);

        btnLogin.addActionListener(e -> login());
        btnRegister.addActionListener(e -> register());

        // Button for investing
        JButton btnInvest = new JButton("Investimenti");
        btnInvest.setEnabled(false); // Will be enabled after login
        panel.add(btnInvest);

        btnInvest.addActionListener(e -> invest());

        // This section simulates logged-in user's options (Deposita, Preleva, etc.)
        JPanel menuPanel = new JPanel();
        JButton btnDeposit = new JButton("Deposita");
        JButton btnWithdraw = new JButton("Preleva");
        JButton btnShowStatus = new JButton("Stato conto");
        JButton btnViewTransactions = new JButton("Vedi Transazioni");
        JButton btnAdvanceMonth = new JButton("Avanzare di un mese");
        JButton btnLogout = new JButton("Logout");
        panel.add(btnLogout);  // Aggiungi il pulsante al pannello

        btnLogout.addActionListener(e -> logout());

        menuPanel.add(btnDeposit);
        menuPanel.add(btnWithdraw);
        menuPanel.add(btnShowStatus);
        menuPanel.add(btnViewTransactions);
        menuPanel.add(btnAdvanceMonth);
        panel.add(btnLogout);  // Aggiungi il pulsante al pannello



        frame.getContentPane().add(menuPanel, BorderLayout.NORTH);

        // Actions for these buttons after logging in
        btnDeposit.addActionListener(e -> deposit());
        btnWithdraw.addActionListener(e -> withdraw());
        btnShowStatus.addActionListener(e -> showStatus());
        btnViewTransactions.addActionListener(e -> viewTransactions());
        btnAdvanceMonth.addActionListener(e -> advanceMonth());
        btnLogout.addActionListener(e -> logout());

        // All menu buttons are initially disabled
        menuPanel.setVisible(false);

        // Store reference for dynamic enabling/disabling after login
        this.textArea.setText("Benvenuto nel sistema di gestione banca.");
    }

    private void login() {
        String username = JOptionPane.showInputDialog(frame, "Inserisci nome utente:");
        String password = JOptionPane.showInputDialog(frame, "Inserisci la tua password:");

        if (GestoreFile.verificaLogin(username, password)) {
            utente = GestoreFile.recuperaUtente(username);
            textArea.setText("Login riuscito! Benvenuto " + username);

            // Abilita i pulsanti del menu
            enableUserOptions(true);

            // Abilita il pulsante Investimenti
            ((JButton) ((JPanel) frame.getContentPane().getComponent(1)).getComponent(2)).setEnabled(true); // Abilita il pulsante Investimenti
        } else {
            textArea.setText("Login fallito! Verifica il nome utente o la password.");
        }
    }

    private void register() {
        String username = JOptionPane.showInputDialog(frame, "Inserisci un nuovo nome utente:");
        String password = JOptionPane.showInputDialog(frame, "Inserisci una nuova password:");

        if (GestoreFile.verificaLogin(username, password)) {
            textArea.setText("L'utente esiste già.");
        } else {
            utente = new Utente(username, password, 1, new Portafoglio(100.0), new ContoBancario(0.0), new ArrayList<>());
            GestoreFile.salvaNuovoUtente(utente);
            textArea.setText("Registrazione completata! Benvenuto " + username);
        }
    }

    private void invest() {
        // Show dialog for investment details
            // Mostra la finestra di dialogo per i dettagli dell'investimento
            String durata = JOptionPane.showInputDialog(frame, "Scegli durata (Basso, Medio, Alto):");
            String rischio = JOptionPane.showInputDialog(frame, "Scegli rischio (Basso, Medio, Alto):");

            // Verifica che durata e rischio siano stati inseriti
            if (durata == null || durata.trim().isEmpty() || rischio == null || rischio.trim().isEmpty()) {
                textArea.setText("Errore: Devi inserire sia la durata che il rischio dell'investimento.");
                return; // Termina la funzione in caso di errore
            }

            // Gestione dell'importo dell'investimento
            double importo = 0;
            try {
                importo = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi investire?"));
            } catch (NumberFormatException e) {
                textArea.setText("Importo non valido.");
                return; // Esci dalla funzione se l'importo non è valido
            }

            // Procedi con l'aggiunta dell'investimento
            if (utente.aggiungiInvestimento(importo, durata, rischio, 6, utente)) {
                textArea.setText("Investimento di " + importo + "€ aggiunto con durata " + durata + " e rischio " + rischio);
                GestoreFile.salvaAggiornamenti(utente);
            } else {
                textArea.setText("Investimento non riuscito.");
            }
        }



    private void deposit() {
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi depositare?"));
        if (utente.getPortafoglio().prelevaDenaro(amount)) {
            utente.getContoBancario().deposita(amount);
            textArea.setText("Depositato " + amount + "€.");
            GestoreFile.salvaAggiornamenti(utente);
        } else {
            textArea.setText("Soldi insufficienti nel portafoglio.");
        }
    }

    private void withdraw() {
        double amount = Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi prelevare?"));
        if (utente.getContoBancario().preleva(amount)) {
            utente.getPortafoglio().aggiungiDenaro(amount);
            textArea.setText("Prelevato " + amount + "€.");
            GestoreFile.salvaAggiornamenti(utente);
        } else {
            textArea.setText("Soldi insufficienti nel conto bancario.");
        }
    }

    private void showStatus() {
        String status = "Stato attuale del conto:\n";
        status += "Portafoglio: " + utente.getPortafoglio().getSaldo() + "€\n";
        status += "Conto Bancario: " + utente.getContoBancario().getSaldo() + "€\n";
        status += "Investimenti: \n";
        for (Investimento investimento : utente.getInvestimenti()) {
            status += investimento.toString() + "\n";
        }
        textArea.setText(status);
    }

    private void viewTransactions() {
        GestoreFile.stampaTransazioni(utente);
    }

    private void advanceMonth() {
        if (utente.avanzareMese(utente)) {
            textArea.setText("Mese avanzato! Investimenti completati.");
        } else {
            textArea.setText("Avanzamento mese non riuscito.");
        }
        GestoreFile.salvaAggiornamenti(utente);
    }

    private void logout() {
        System.exit(0);
    }

    private void enableUserOptions(boolean enabled) {
        frame.getContentPane().getComponent(1).setVisible(enabled);  // Menu panel
        frame.getContentPane().getComponent(2).setVisible(enabled);  // Text area
    }
}













