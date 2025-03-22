import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class Gui {
  private static JFrame frame;
  private static CardLayout cardLayout;
  private static JPanel cardPanel;
  private static Utente utente;
  private static JLabel accountStatusLabel; // Etichetta per lo stato dell'account
  private static JTextArea
      investmentsDetailsArea; // Area di testo per mostrare i dettagli degli investimenti

  public static void main(String[] args) {
    // Avvio della GUI
    SwingUtilities.invokeLater(
        () -> {
          createAndShowGUI();
        });
  }

  private static void createAndShowGUI() {
    frame = new JFrame("Gestione Banca");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 400);

    // CardLayout per gestire la navigazione tra le schermate
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Aggiungi il pannello di login
    cardPanel.add(createLoginPanel(), "Login");

    // Aggiungi il pannello di registrazione
    cardPanel.add(createRegistrationPanel(), "Registration");

    // Aggiungi il pannello principale dopo il login
    cardPanel.add(createMainMenuPanel(), "MainMenu");

    frame.getContentPane().add(cardPanel);
    frame.setVisible(true);
  }

  // Pannello di login
  private static JPanel createLoginPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));

    JLabel labelUsername = new JLabel("Username:");
    JTextField usernameField = new JTextField();
    JLabel labelPassword = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Registrati");

    loginButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (GestoreFile.verificaLogin(username, password)) {
              utente = GestoreFile.recuperaUtente(username);
              updateAccountStatus(); // Aggiorna lo stato dell'account
              updateInvestmentsDetails(); // Aggiorna la lista degli investimenti
              cardLayout.show(cardPanel, "MainMenu");
            } else {
              JOptionPane.showMessageDialog(frame, "Nome utente o password errati.");
            }
          }
        });

    registerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Registration");
          }
        });

    panel.add(labelUsername);
    panel.add(usernameField);
    panel.add(labelPassword);
    panel.add(passwordField);
    panel.add(loginButton);
    panel.add(registerButton);

    return panel;
  }

  // Pannello di registrazione
  private static JPanel createRegistrationPanel() {

    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(3, 2));

    JLabel labelUsername = new JLabel("Username:");
    JTextField usernameField = new JTextField();
    JLabel labelPassword = new JLabel("Password:");
    JPasswordField passwordField = new JPasswordField();

    JButton registerButton = new JButton("Registrati");

    registerButton.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Aggiunta la verifica dei campi vuoti
            if (username.trim().isEmpty() || password.trim().isEmpty()) {
              JOptionPane.showMessageDialog(
                  frame, "Errore: Devi inserire sia il nome utente che la password.");
              return; // Termina l'operazione se i campi sono vuoti
            }

            if (GestoreFile.verificaLogin(username, password)) {
              JOptionPane.showMessageDialog(frame, "L'utente esiste già!");
            } else {
              utente =
                  new Utente(
                      username,
                      password,
                      1,
                      new Portafoglio(100.0),
                      new ContoBancario(0.0),
                      new ArrayList<>());
              GestoreFile.salvaNuovoUtente(utente);
              updateAccountStatus(); // Aggiorna lo stato dell'account
              updateInvestmentsDetails(); // Aggiorna la lista degli investimenti
              cardLayout.show(cardPanel, "MainMenu");
            }
          }
        });

    panel.add(labelUsername);
    panel.add(usernameField);
    panel.add(labelPassword);
    panel.add(passwordField);
    panel.add(registerButton);

    return panel;
  }

  // Pannello principale dopo il login
  private static JPanel createMainMenuPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    // Creazione del pannello per lo stato dell'account
    accountStatusLabel = new JLabel();
    accountStatusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
    accountStatusLabel.setForeground(Color.BLACK);
    accountStatusLabel.setHorizontalAlignment(JLabel.CENTER);
    updateAccountStatus(); // Inizializza lo stato dell'account
    panel.add(accountStatusLabel, BorderLayout.NORTH);

    // Pannello per i dettagli degli investimenti
    investmentsDetailsArea = new JTextArea();
    investmentsDetailsArea.setEditable(false);
    investmentsDetailsArea.setFont(new Font("Arial", Font.PLAIN, 20));
    JScrollPane scrollPane = new JScrollPane(investmentsDetailsArea);
    panel.add(scrollPane, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(6, 1)); // Aggiunto spazio per il pulsante Logout

    JButton btnAvanzareMese = new JButton("Avanzare di un mese");
    JButton btnDepositare = new JButton("Depositare soldi in banca");
    JButton btnPrelevare = new JButton("Prelevare soldi dalla banca");
    JButton btnInvestire = new JButton("Aggiungere un nuovo investimento");
    JButton btnTransazioni = new JButton("Vedi Transazioni");
    JButton btnLogout = new JButton("Logout"); // Nuovo pulsante Logout

    btnLogout.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String[] options = {"Esci", "Annulla"}; // Personalizza i pulsanti
            int conferma =
                JOptionPane.showOptionDialog(
                    frame,
                    "Sei sicuro di voler uscire?",
                    "Conferma Logout",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    options,
                    options[1] // Imposta "Annulla" come opzione predefinita
                    );

            if (conferma == 0) { // "Esci" è il primo elemento dell'array (indice 0)
              frame.dispose(); // Chiude la finestra
              System.exit(0); // Termina il programma
            }
          }
        });

    btnAvanzareMese.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (utente.avanzareMese(utente)) {
              JOptionPane.showMessageDialog(frame, "Investimenti completati.");
            }
            GestoreFile.salvaAggiornamenti(utente);
            updateAccountStatus(); // Aggiorna lo stato dell'account
            updateInvestmentsDetails(); // Aggiorna la lista degli investimenti
          }
        });

    btnDepositare.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            double amount =
                Double.parseDouble(JOptionPane.showInputDialog("Quanto vuoi depositare?"));
            if (utente.getPortafoglio().prelevaDenaro(amount)) {
              utente.getContoBancario().deposita(amount);
              GestoreFile.salvaAggiornamenti(utente);
              updateAccountStatus(); // Aggiorna lo stato dell'account
            } else {
              JOptionPane.showMessageDialog(frame, "Soldi insufficienti nel portafoglio.");
            }
          }
        });

    btnPrelevare.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            double amount =
                Double.parseDouble(JOptionPane.showInputDialog("Quanto vuoi prelevare?"));
            if (utente.getContoBancario().preleva(amount)) {
              utente.getPortafoglio().aggiungiDenaro(amount);
              GestoreFile.salvaAggiornamenti(utente);
              updateAccountStatus(); // Aggiorna lo stato dell'account
            } else {
              JOptionPane.showMessageDialog(frame, "Soldi insufficienti nel conto bancario.");
            }
          }
        });

    btnInvestire.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            String durata =
                JOptionPane.showInputDialog(frame, "Scegli durata (Basso, Medio, Alto):");
            String rischio =
                JOptionPane.showInputDialog(frame, "Scegli rischio (Basso, Medio, Alto):");
            int mesi = 0;

            if (durata == null
                || durata.trim().isEmpty()
                || rischio == null
                || rischio.trim().isEmpty()) {
              investmentsDetailsArea.setText(
                  "Errore: Devi inserire sia la durata che il rischio dell'investimento.");
              return;
            }

            double importo = 0;

            if (durata.equalsIgnoreCase("basso")) {
              mesi = 3;
            }
            if (durata.equalsIgnoreCase("medio")) {
              mesi = 6;
            }
            if (durata.equalsIgnoreCase("alto")) {
              mesi = 12;
            }

            try {
              importo =
                  Double.parseDouble(JOptionPane.showInputDialog(frame, "Quanto vuoi investire?"));
            } catch (NumberFormatException exception) {
              investmentsDetailsArea.setText("Importo non valido.");
              return;
            }

            if (utente.aggiungiInvestimento(importo, durata, rischio, mesi, utente)) {
              investmentsDetailsArea.setText(
                  "Investimento di "
                      + importo
                      + "€ aggiunto con durata "
                      + durata
                      + " e rischio "
                      + rischio);
              GestoreFile.salvaAggiornamenti(utente);
            } else {
              investmentsDetailsArea.setText("Investimento non riuscito.");
            }
          }
        });

    btnTransazioni.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            GestoreFile.stampaTransazioni(utente);
          }
        });

    buttonPanel.add(btnAvanzareMese);
    buttonPanel.add(btnDepositare);
    buttonPanel.add(btnPrelevare);
    buttonPanel.add(btnInvestire);
    buttonPanel.add(btnTransazioni);
    buttonPanel.add(btnLogout); // Aggiunto pulsante Logout

    panel.add(buttonPanel, BorderLayout.EAST);

    return panel;
  }

  // Metodo per aggiornare la JLabel con lo stato dell'account
  private static void updateAccountStatus() {
    if (utente != null) {
      String stato =
          "<html><b>Conto Bancario:</b> "
              + utente.getContoBancario().getSaldo()
              + " €<br><b>Portafoglio:</b> "
              + utente.getPortafoglio().getSaldo()
              + " €<br><b>Investimenti:</b> "
              + utente.getInvestimenti().size()
              + " attivi.</html>";
      accountStatusLabel.setText(stato);
    }
  }

  // Metodo per aggiornare la sezione degli investimenti
  private static void updateInvestmentsDetails() {
    if (utente != null && !utente.getInvestimenti().isEmpty()) {
      StringBuilder investmentsText = new StringBuilder();
      for (Investimento investimento : utente.getInvestimenti()) {
        investmentsText.append("Investimento: ").append(investimento.getImporto()).append(" €\n");
        investmentsText.append("Durata: ").append(investimento.getDurata()).append("\n");
        investmentsText.append("Rischio: ").append(investimento.getRischio()).append("\n\n");
      }
      investmentsDetailsArea.setText(investmentsText.toString());
    } else {
      investmentsDetailsArea.setText("Non ci sono investimenti attivi.");
    }
  }
}
