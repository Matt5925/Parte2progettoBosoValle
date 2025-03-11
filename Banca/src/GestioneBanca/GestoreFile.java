package GestioneBanca;
import javax.swing.*;
import java.io.*;





public class GestoreFile {
    private static final String PATH_CARTELLA_UTENTI = "fileUtenti/"; // Cartella dove vengono salvati i file utente
    private static File cartellaUtenti = new File(PATH_CARTELLA_UTENTI);

    //private static final String PATH_CARTELLA_UTENTI = "fileUtenti/";
    //private static final String PATH_CARTELLA_REGISTRO = "registroTransazioni/";
    //private static final String PATH_REGISTRO = PATH_CARTELLA_REGISTRO + "registro.txt";
    //private static File registroTransazioni = new File(PATH_REGISTRO);
   // private static File cartellaUtenti = new File(PATH_CARTELLA_UTENTI);
/*
    public static void salvaTransazione(String username, String data, String transazione) {

        File cartella = new File(PATH_CARTELLA_REGISTRO);
        if (!cartella.exists()) {
            cartella.mkdirs();
        }

        if (!registroTransazioni.exists()) {
            try {
                registroTransazioni.createNewFile();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Errore nella creazione del registro!", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        try {
            FileWriter writer = new FileWriter(registroTransazioni, true);
            writer.write(username + " | " + data + "\n");
            writer.write(transazione);
            writer.write("\n\n");
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio della transazione", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }*/

    public static void salvaUtente(String username, String password) {
        // Verifica se la cartella esiste, se non esiste la crea
        if (!cartellaUtenti.exists()) {
            cartellaUtenti.mkdirs(); // Crea la cartella se non esiste
        }

        try {
            // Crea il file dell'utente con il nome username.txt
            File fileUtente = new File(PATH_CARTELLA_UTENTI + username + ".txt");
            // Se il file non esiste, lo crea e scrive i dati
            FileWriter writer = new FileWriter(fileUtente);
            writer.write(username + "\n");
            writer.write(password + "\n"); // Scrivi anche la password (nota: in un'applicazione reale, dovresti criptarla)
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio dei dati dell'utente.", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean verificaLogin(String username, String password) {
        File fileUtente = new File(PATH_CARTELLA_UTENTI + username + ".txt");

        // Verifica se il file dell'utente esiste
        if (!fileUtente.exists()) {
            return false; // Utente non trovato
        }

        try {
            // Leggi i dati dal file
            BufferedReader reader = new BufferedReader(new FileReader(fileUtente));
            String nome = reader.readLine(); // Legge il nome utente
            String storedPassword = reader.readLine(); // Legge la password salvata

            reader.close();

            // Verifica se la password inserita corrisponde a quella salvata
            return password.equals(storedPassword);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore nel caricamento dei dati dell'utente.", "Errore", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}