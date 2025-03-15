package GestioneBanca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Gui() {
        frame = new JFrame("Gestione Banca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(creaPannelloAccesso(), "Login");
        mainPanel.add(creaPannelloMenu(), "Menu");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel creaPannelloAccesso() {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("             Nome utente:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("             Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Accedi");
        loginButton.setBackground(Color.WHITE);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton registerButton = new JButton("Registrati");
        registerButton.setBackground(Color.WHITE);
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(Color.WHITE); // Torna al colore originale
            }
        });

        loginButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if (GestoreFile.verificaLogin(user, pass)) {
                JOptionPane.showMessageDialog(frame, "Accesso riuscito! \uD83D\uDE01");
                cardLayout.show(mainPanel, "Menu");
            } else {
                JOptionPane.showMessageDialog(frame, "Nome utente o password errati.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            GestoreFile.salvaUtente(user, pass);
            JOptionPane.showMessageDialog(frame, "Registrazione completata!");
        });

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);
        panel.add(loginButton);
        panel.add(registerButton);

        return panel;
    }

    private JPanel creaPannelloMenu() {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JButton btnAvanzaMese = new JButton("Avanzare di un mese");
        btnAvanzaMese.setBackground(Color.WHITE);
        btnAvanzaMese.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAvanzaMese.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAvanzaMese.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton btnDeposita = new JButton("Depositare soldi in banca");
        btnDeposita.setBackground(Color.WHITE);
        btnDeposita.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnDeposita.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnDeposita.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton btnPreleva = new JButton("Prelevare soldi dalla banca");
        btnPreleva.setBackground(Color.WHITE);
        btnPreleva.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnPreleva.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPreleva.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton btnInvesti = new JButton("Aggiungere un investimento");
        btnInvesti.setBackground(Color.WHITE);
        btnInvesti.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnInvesti.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnInvesti.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton btnStato = new JButton("Stato conto e investimenti");
        btnStato.setBackground(Color.WHITE);
        btnStato.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnStato.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnStato.setBackground(Color.WHITE); // Torna al colore originale
            }
        });
        JButton btnEsci = new JButton("Esci");
        btnEsci.setBackground(Color.WHITE);
        btnEsci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnEsci.setBackground(new Color(239, 239, 239)); // Blu più chiaro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnEsci.setBackground(Color.WHITE); // Torna al colore originale
            }
        });

        btnEsci.addActionListener(e -> frame.dispose());

        panel.add(btnAvanzaMese);
        panel.add(btnDeposita);
        panel.add(btnPreleva);
        panel.add(btnInvesti);
        panel.add(btnStato);
        panel.add(btnEsci);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Gui::new);
    }
}
