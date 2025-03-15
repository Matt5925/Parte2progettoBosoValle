/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

public class Investimento {
	private double importo;
	private int mesiRimanenti;
	private double tassoGuadagno;
	private boolean completato;
	private String rischio;
	private String durata;

	public Investimento(double importo, String durata, String rischio) {
		this.importo = importo;
		this.mesiRimanenti = determinaDurata(durata);
		this.tassoGuadagno = determinaGuadagno(durata, rischio);
		this.completato = false;
		this.rischio=rischio;
		this.durata=durata;
	}

	private int determinaDurata(String durata) {
		int mesi = 0;
		if (durata.equalsIgnoreCase("Basso")) {
			mesi = 3;
		} else if (durata.equalsIgnoreCase("Medio")) {
			mesi = 6;
		} else {
			mesi = 12;
		}
		return mesi;
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

	public double getImporto(){
		return this.importo;
	}

	public String getDurata(){
		return this.durata;
	}
	public String getRischio(){
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

	public boolean completato() {
		return completato;
	}

	public double calcolaGuadagno() {
		if (completato) {
			return importo + (importo * tassoGuadagno);
		}
		return 0;
	}



	@Override
	public String toString() {
		return "Investimento di " + importo + "€, mesi rimanenti: " + mesiRimanenti + ", tasso di guadagno: "
				+ (tassoGuadagno * 100) + "%";
	}
}
