package GestioneBanca;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

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
        loginButton.setBackground(Color.CYAN);
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(41, 128, 185)); // Blu più scuro
            }
        });
        JButton registerButton = new JButton("Registrati");
        registerButton.setBackground(Color.CYAN);

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
        JButton btnDeposita = new JButton("Depositare soldi in banca");
        JButton btnPreleva = new JButton("Prelevare soldi dalla banca");
        JButton btnInvesti = new JButton("Aggiungere un investimento");
        JButton btnStato = new JButton("Stato conto e investimenti");
        JButton btnEsci = new JButton("Esci");

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
