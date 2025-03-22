/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
public class Investimento {
  private final double importo;
  private int mesiRimanenti;
  private final double tassoGuadagno;
  private boolean completato;
  private final String rischio;
  private final String durata;

  public Investimento(double importo, String durata, String rischio, int mesiRimanenti) {
    this.importo = importo;
    this.mesiRimanenti = mesiRimanenti;
    this.tassoGuadagno = determinaGuadagno(durata, rischio);
    this.completato = false;
    this.rischio = rischio;
    this.durata = durata;
  }

  public boolean completato() {
    return completato;
  }

  private double determinaGuadagno(String durata, String rischio) {
    double guadagno;

    if (durata.equalsIgnoreCase("Basso")) {
      guadagno = (int) ((Math.random() * 40) - 20);
      guadagno /= 100;

    } else if (durata.equalsIgnoreCase("Medio")) {
      guadagno = (int) ((Math.random() * 100) - 50);
      guadagno /= 100;

    } else {
      guadagno = (int) ((Math.random() * 200) - 100);
      guadagno /= 100;
    }

    if (rischio.equalsIgnoreCase("Medio")) {
      guadagno *= 2;
    } else if (rischio.equalsIgnoreCase("Alto")) {
      guadagno *= 3;
    }
    return guadagno;
  }

  public int getMesiRimanenti() {
    return this.mesiRimanenti;
  }

  public double getImporto() {

    return this.importo;
  }

  public String getDurata() {
    return this.durata;
  }

  public String getRischio() {
    return this.rischio;
  }

  public void aggiornaInvestimento() {
    if (completato) {
      return;
    }

    mesiRimanenti--;
    if (mesiRimanenti <= 0) {
      completato = true;
    }
  }

  public double calcolaGuadagno() {
    if (completato) {
      return importo + (importo * tassoGuadagno);
    }
    return 0;
  }

  @Override
  public String toString() {
    return "Investimento di "
        + importo
        + "€, mesi rimanenti: "
        + mesiRimanenti
        + ", tasso di guadagno: "
        + (tassoGuadagno * 100)
        + "%";
  }
}
