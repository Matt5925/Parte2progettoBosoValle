package GestioneBanca;
import javax.swing.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;




public class GestoreFile {
    private static final String PATH_CARTELLA_UTENTI = "fileUtenti/"; // Cartella dove vengono salvati i file utente
    private static File cartellaUtenti = new File(PATH_CARTELLA_UTENTI);


    public static void salvaAggiornamenti(Utente utente) {
        // Crea il file dell'utente con il nome "username.txt"
        File fileUtente = new File(PATH_CARTELLA_UTENTI + utente.getUsername() + ".txt");

        try {
            // Usa FileWriter per sovrascrivere il file
            FileWriter writer = new FileWriter(fileUtente);

            // Scrivi i dati dell'utente nel file, ogni dato su una riga separata
            writer.write(utente.getUsername() + "\n");
            writer.write(utente.getPassword() + "\n");
            writer.write(utente.getMeseCorrente() + "\n");
            writer.write(utente.getPortafoglio().tostring() + "\n");
            writer.write(utente.getContoBancario().tostring() + "\n");

            for (int i = 0; i < utente.getInvestimenti().size(); i++) {
                Investimento investimento = utente.getInvestimenti().get(i);
                writer.write(investimento.getImporto() + "," + investimento.getDurata() + "," + investimento.getRischio() + ";");
            }

            writer.write("\n");

            // Chiudi il file
            writer.close();


            System.out.println("Dati dell'utente aggiornati correttamente nel file.");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio dei dati dell'utente.");
            e.printStackTrace();
        }
    }




    public static void salvaNuovoUtente(Utente utente) {
        // Verifica se la cartella esiste, se non esiste la crea
        if (!cartellaUtenti.exists()) {
            cartellaUtenti.mkdirs(); // Crea la cartella se non esiste
        }

        try {
      // Crea il file dell'utente con il nome username.txt
      File fileUtente = new File(PATH_CARTELLA_UTENTI + utente.getUsername() + ".txt");
            // Se il file non esiste, lo crea e scrive i dati
            FileWriter writer = new FileWriter(fileUtente);
            writer.write(utente.getUsername() + "\n");
            writer.write(utente.getPassword()+ "\n");// Scrivi anche la password (nota: in un'applicazione reale, dovresti criptarla)
            writer.write(utente.getMeseCorrente()+ "\n");
            writer.write(utente.getPortafoglio().tostring() + "\n");
            writer.write(utente.getContoBancario().tostring() +"\n");
          writer.write(utente.stampaInvestimenti() +";");
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Errore nel salvataggio dei dati dell'utente.", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static boolean verificaLogin(String username, String password ) {

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

    public static Utente recuperaUtente(String username) {
        File fileUtente = new File(PATH_CARTELLA_UTENTI + username + ".txt");
        if (!fileUtente.exists()) {
            return null;  // L'utente non esiste
        }

        String usernameUtente = "";
        String passwordUtente = "";
        int meseCorrente = 1;
        Portafoglio portafoglioUtente = null;
        ContoBancario contoBancarioUtente = null;
        List<Investimento> investimentiUtente = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileUtente));

            // Leggi la prima riga e salva i dati dell'utente
            usernameUtente = reader.readLine();
            passwordUtente = reader.readLine();
            meseCorrente = Integer.parseInt(reader.readLine()); // Leggi meseCorrente
            portafoglioUtente = new Portafoglio(Double.parseDouble(reader.readLine()));
            contoBancarioUtente = new ContoBancario(Double.parseDouble(reader.readLine()));

            // Leggi gli investimenti
            String investimentiString = reader.readLine();
            //System.out.println("Investimenti letti dal file: " + investimentiString); // Aggiungi log per debugging
            if (investimentiString != null && !investimentiString.trim().isEmpty()) {
                // Separiamo gli investimenti usando un delimitatore ";"
                String[] investimentiArray = investimentiString.split(";");
                for (String investimentoStr : investimentiArray) {
                    if (!investimentoStr.trim().isEmpty()) {
                        String[] investimentoDetails = investimentoStr.split(",");
                        if (investimentoDetails.length == 3) {

                            try {
                                // Parsing dei dettagli dell'investimento
                                double importo = Double.parseDouble(investimentoDetails[0].trim());
                                String durata = investimentoDetails[1].trim();
                                String rischio = investimentoDetails[2].trim();

                                // Aggiungiamo l'investimento alla lista
                                Investimento investimento = new Investimento(importo, durata, rischio);
                                investimentiUtente.add(investimento);
                                System.out.println("Investimento ripreso: " + investimento.toString()); // Log per il singolo investimento
                            } catch (NumberFormatException e) {
                                System.out.println("Errore nel formato dell'investimento: " + investimentoStr);
                            }
                        } else {
                            System.out.println("Errore nei dettagli dell'investimento: " + investimentoStr);
                        }
                    }
                }
            }

            reader.close();

            // Crea l'oggetto Utente con i dati letti
            return new Utente(usernameUtente, passwordUtente, meseCorrente, portafoglioUtente, contoBancarioUtente, investimentiUtente);

        } catch (IOException e) {
            e.printStackTrace();
            return null;  // In caso di errore nella lettura del file
        }
    }


}