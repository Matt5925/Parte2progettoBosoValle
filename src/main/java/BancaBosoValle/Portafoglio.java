/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
public class Portafoglio {
  private double saldo;

  public Portafoglio(Double saldo) {
    this.saldo = saldo;
  }

  public String tostring() {
    String s = "";
    s += this.saldo;
    return s;
  }

  public double getSaldo() {
    return saldo;
  }

  public void aggiungiDenaro(double importo) {
    saldo += importo;
  }

  public boolean prelevaDenaro(double importo) {
    if (importo <= saldo) {
      saldo -= importo;
      return true;
    }
    return false;
  }
}
