/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

public class ContoBancario {
	private double saldo;

	public ContoBancario(double saldo) {
		this.saldo = saldo;
	}


	public String tostring(){
		String s="";
		s+=this.saldo;
		return s;
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
