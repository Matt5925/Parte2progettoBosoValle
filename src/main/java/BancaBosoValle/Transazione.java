public class Transazione {

  private final String mittente;
  private final String destinatario;
  private final double importo;

  public Transazione(String mittente, String destinatario, double importo) {
    this.mittente = mittente;
    this.destinatario = destinatario;
    this.importo = importo;
  }

  public String toString() {
    String s = "";
    s += this.mittente;
    s += "---" + this.importo + "$--->";
    s += this.destinatario;
    return s;
  }
}
