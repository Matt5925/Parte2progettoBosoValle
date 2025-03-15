/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;
import java.util.ArrayList;
import java.util.List;

public class Utente {
	private String userName;
	private String password;
	private Portafoglio portafoglio;
	private ContoBancario contoBancario;
	private List<Investimento> investimenti;

	private final double sommaMensile = 100;
	private int meseCorrente = 1;

	public Utente(String userName,String password,int meseCorrente,Portafoglio portafoglio,ContoBancario contoBancario,Investimento[] investimenti) {
		this.userName=userName;
		this.password=password;
		this.portafoglio = portafoglio;
		this.contoBancario = contoBancario;
		this.investimenti = new ArrayList<>();
		this.meseCorrente=meseCorrente;
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

	public boolean avanzareMese() {
		boolean completati = false;
		portafoglio.aggiungiDenaro(sommaMensile);
		meseCorrente += 1;

		for (int i = 0; i < investimenti.size(); i++) {
			Investimento investimento = investimenti.get(i);
			investimento.aggiornaInvestimento();
			if (investimento.completato()) {
				double guadagno = investimento.calcolaGuadagno();
				contoBancario.deposita(guadagno);
				investimenti.remove(i); // Rimuoviamo l'investimento completato
				i--;
				completati = true;

			}
		}
		return completati;
	}

	public boolean aggiungiInvestimento(double importo, String durata, String rischio) {


		if (!contoBancario.preleva(importo)) {
			return false;
		}

		Investimento nuovoInvestimento = new Investimento(importo, durata, rischio);
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
	public String getUsername(){
		return this.userName;
	}

	public String getPassword(){
		return this.password;
	}
	public String stampaInvestimenti(){
		String s="";
		for (int i=0;i<investimenti.size();i++){
			if(investimenti.get(i)!=null){
				s+=investimenti.get(i).toString();
			}
		}
		return s;
	}


}
