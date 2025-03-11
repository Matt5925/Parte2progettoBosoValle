/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

public class Utente {
	private String userName;
	private String password;
	private Portafoglio portafoglio;
	private ContoBancario contoBancario;
	private Investimento[] investimenti;
	private int numeroInvestimenti;
	private final double sommaMensile = 100;
	private int meseCorrente = 1;

	public Utente(String userName,String password) {
		this.userName=userName;
		this.password=password;
		this.portafoglio = new Portafoglio();
		this.contoBancario = new ContoBancario();
		this.investimenti = new Investimento[10];
		this.numeroInvestimenti = 0;
	}

	public Portafoglio getPortafoglio() {
		return portafoglio;
	}

	public ContoBancario getContoBancario() {
		return contoBancario;
	}

	public boolean avanzareMese() {
		boolean completati = false;
		portafoglio.aggiungiDenaro(sommaMensile);
		meseCorrente += 1;

		for (int i = 0; i < numeroInvestimenti; i++) {
			investimenti[i].aggiornaInvestimento();
			if (investimenti[i].completato()) {
				double guadagno = investimenti[i].calcolaGuadagno();
				contoBancario.deposita(guadagno);
				rimuoviInvestimento(i);
				i--;
				completati = true;
			}
		}
		return completati;
	}

	public boolean aggiungiInvestimento(double importo, String durata, String rischio) {
		if (numeroInvestimenti >= investimenti.length) {
			return false;
		}

		if (!contoBancario.preleva(importo)) {
			return false;
		}

		Investimento nuovoInvestimento = new Investimento(importo, durata, rischio);
		investimenti[numeroInvestimenti] = nuovoInvestimento;
		numeroInvestimenti++;
		return true;
	}

	private void rimuoviInvestimento(int index) {
		for (int i = index; i < numeroInvestimenti - 1; i++) {
			investimenti[i] = investimenti[i + 1];
		}
		investimenti[numeroInvestimenti - 1] = null;
		numeroInvestimenti--;

	}

	public String getStato() {
		String stato = "Portafoglio: " + portafoglio.getSaldo() + "€\n";
		stato += "Conto bancario: " + contoBancario.getSaldo() + "€\n";
		stato += "Investimenti:\n";

		for (int i = 0; i < numeroInvestimenti; i++) {
			stato += investimenti[i].toString() + "\n";
		}

		return stato;
	}

	public int getMeseCorrente() {
		return meseCorrente;
	}
}
