/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

public class ContoBancario {
	private double saldo;

	public ContoBancario() {
		this.saldo = 0.0;
	}

	public double getSaldo() {
		return saldo;
	}

	public void deposita(double importo) {
		saldo += importo;
	}

	public boolean preleva(double importo) {
		if (importo <= saldo) {
			saldo -= importo;
			return true;
		}
		return false;
	}
}
