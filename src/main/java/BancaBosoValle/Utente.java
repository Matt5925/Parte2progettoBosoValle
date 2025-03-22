/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
import java.util.List;

public class Utente {
  private final String userName;
  private final String password;
  private final Portafoglio portafoglio;
  private final ContoBancario contoBancario;
  private final List<Investimento> investimenti;

  private final double sommaMensile = 100;
  private int meseCorrente = 1;

  public Utente(
      String userName,
      String password,
      int meseCorrente,
      Portafoglio portafoglio,
      ContoBancario contoBancario,
      List investimenti) {
    this.userName = userName;
    this.password = password;
    this.portafoglio = portafoglio;
    this.contoBancario = contoBancario;
    this.investimenti = investimenti;
    this.meseCorrente = meseCorrente;
  }

  public List<Investimento> getInvestimenti() {
    return investimenti;
  }

  public Portafoglio getPortafoglio() {
    return portafoglio;
  }

  public ContoBancario getContoBancario() {
    return contoBancario;
  }

  public boolean avanzareMese(Utente utente) {
    boolean completati = false;
    portafoglio.aggiungiDenaro(sommaMensile);

    Transazione t = new Transazione("Banca", "Portafoglio", sommaMensile);

    GestoreFile.salvaTransazione(t, utente);

    meseCorrente += 1;

    for (int i = 0; i < investimenti.size(); i++) {
      Investimento investimento = investimenti.get(i);
      investimento.aggiornaInvestimento();
      if (investimento.completato()) {
        double guadagno = investimento.calcolaGuadagno();
        contoBancario.deposita(guadagno);

        Transazione t1 = new Transazione("BancaInvestimenti", "ContoBancario", guadagno);
        GestoreFile.salvaTransazione(t1, utente);
        investimenti.remove(i); // Rimuoviamo l'investimento completato
        i--;
        completati = true;
      }
    }
    return completati;
  }

  public boolean aggiungiInvestimento(
      double importo, String durata, String rischio, int mesi, Utente utente) {

    if (!contoBancario.preleva(importo)) {
      return false;
    }
    Transazione t = new Transazione("ContoBancario", "BancaInvestimenti", importo);
    GestoreFile.salvaTransazione(t, utente);
    Investimento nuovoInvestimento = new Investimento(importo, durata, rischio, mesi);
    investimenti.add(nuovoInvestimento);

    return true;
  }

  public String getStato() {
    String stato = "Portafoglio: " + portafoglio.getSaldo() + "€\n";
    stato += "Conto bancario: " + contoBancario.getSaldo() + "€\n";
    stato += "Investimenti:\n";

    for (int i = 0; i < investimenti.size(); i++) {
      stato += investimenti.get(i).toString() + "\n";
    }

    return stato;
  }

  public int getMeseCorrente() {
    return meseCorrente;
  }

  public String getUsername() {
    return this.userName;
  }

  public String getPassword() {
    return this.password;
  }

  public String stampaInvestimenti() {
    String s = "";
    for (int i = 0; i < investimenti.size(); i++) {
      if (investimenti.get(i) != null) {
        s += investimenti.get(i).toString();
      }
    }
    return s;
  }
}
