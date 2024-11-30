/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

public class Portafoglio {
	private double saldo;

	public Portafoglio() {
		this.saldo = 100;
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
