/**
 * @author cristian katia , francesco secco
 * @version 1.0
 */
package GestioneBanca;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		int scelta;


		Utente utente=null;
	boolean accessoRiuscito =false;
do{
	System.out.print("1) Accedi\n");
	System.out.print("2) Registrati\n");
	int sceltaAccesso;
	sceltaAccesso = inserisciIntero();


	while (sceltaAccesso != 2 && sceltaAccesso != 1) {
		System.out.println("\nERRORE! ");
		System.out.print("SCEGLI --> ");
		sceltaAccesso = inserisciIntero();
	}



	switch(sceltaAccesso){


//accedi
		case 1:{

			// Logica per il login utente
			System.out.print("Inserisci nome utente: ");
			String userName = scanner.nextLine();
			System.out.print("\nInserisci Password: ");
			String password = scanner.nextLine();

			// Verifica il login
			if (GestoreFile.verificaLogin(userName, password)) {

				System.out.println("Login riuscito! Benvenuto, " + userName + "!");
				accessoRiuscito=true;
				utente=GestoreFile.recuperaUtente(userName);
			} else {
				System.out.println("Nome utente o password errati.");
			}
			break;
		}

//registra
		case 2:{

			System.out.print("Inserisci nome utente: ");
			String userName=scanner.next();
			System.out.print("\nInserisci Password: ");
			String password=scanner.next();
			accessoRiuscito=true;

			utente=new Utente(userName,password, new Portafoglio(100.0),new ContoBancario(0.0)/*new Investimento[]{}*/);
			GestoreFile.salvaNuovoUtente(utente);


			break;
		}
	}
}while(!accessoRiuscito);




		do {

			menu(utente.getMeseCorrente());
			System.out.print("\nSCEGLI --> ");
			scelta = inserisciIntero();

			while (scelta > 5 || scelta < 0) {
				System.out.println("\nERRORE! ");
				System.out.print("SCEGLI --> ");
				scelta = inserisciIntero();
			}

			switch (scelta) {
			case 1: {
				if (utente.avanzareMese()) {
					System.out.println("\nATTENZIONE degli Investimenti sono stati completati");
				}
				break;
			}

			case 2: {
				System.out.print("Soldi nel Portafoglio: " + utente.getPortafoglio().getSaldo() + "\n");
				System.out.print("Quanto vuoi depositare? ");
				double depositaImporto = inserisciDouble();
				if (utente.getPortafoglio().prelevaDenaro(depositaImporto)) {
					utente.getContoBancario().deposita(depositaImporto);
					System.out.println("Hai depositato " + depositaImporto + "€.");
				} else {
					System.out.println("Soldi insufficienti nel portafoglio.");
				}
				GestoreFile.salvaAggiornamenti(utente);

				break;
			}

			case 3: {
				System.out.print("Soldi nel Conto Bancario: " + utente.getContoBancario().getSaldo() + "\n");
				System.out.print("Quanto vuoi prelevare? ");

				double prelievaImporto = inserisciDouble();
				if (utente.getContoBancario().preleva(prelievaImporto)) {
					utente.getPortafoglio().aggiungiDenaro(prelievaImporto);
					System.out.println("Hai prelevato " + prelievaImporto + "€.");
				} else {
					System.out.println("Soldi insufficienti nel conto bancario.");
				}
				GestoreFile.salvaAggiornamenti(utente);
				break;
			}

			case 4: {
				String durata = "";
				String rischio = "";
				double importo = 0;
				boolean ok;

				do {
					ok = true;
					System.out.println("Scegli una durata (Basso, Medio, Alto): ");
					durata = scanner.next().trim();
					if (durata.equalsIgnoreCase("Basso") || durata.equalsIgnoreCase("Medio")
							|| durata.equalsIgnoreCase("Alto")) {
					} else {
						System.out.println("Durata non valida. Inserisci 'Basso', 'Medio' o 'Alto'.");
						ok = false;
					}

				} while (!ok);

				do {
					ok = true;
					System.out.println("Scegli un rischio (Basso, Medio, Alto): ");
					rischio = scanner.next().trim();
					if (rischio.equalsIgnoreCase("Basso") || rischio.equalsIgnoreCase("Medio")
							|| rischio.equalsIgnoreCase("Alto")) {
					} else {
						System.out.println("Rischio non valido. Inserisci 'Basso', 'Medio' o 'Alto'.");
						ok = false;
					}

				} while (!ok);

				System.out.print("Soldi nel Conto Bancario: " + utente.getContoBancario().getSaldo() + "\n");
				do {
					ok = true;
					System.out.print("Quanto vuoi investire? ");
					importo = inserisciDouble();
					if (importo <= 0) {
						System.out.println("L'importo deve essere maggiore di zero.");
						ok = false;
					}

				} while (!ok);

				if (utente.aggiungiInvestimento(importo, durata, rischio)) {
					System.out.println("Investimento aggiunto");
				} else {
					System.out.println("Non e' possibile aggiungere L'Investimento");
				}

				break;
			}

			case 5: {
				System.out.println(utente.getStato());
				break;
			}

			case 0: {
				System.out.println("Uscita dal programma.");
				return;
			}

			default: {
				System.out.println("Scelta non valida. Inserisci un numero da 1 a 6.");
				break;
			}
			}

		} while (scelta != 0);
		scanner.close();
	}

	private static void menu(int meseCorrente) {
		System.out.println("\n--- Menu ---");
		System.out.println("Mese: " + meseCorrente);
		System.out.println("1. Avanzare di un mese");
		System.out.println("2. Depositare soldi in banca");
		System.out.println("3. Prelevare soldi dalla banca");
		System.out.println("4. Aggiungere un nuovo investimento");
		System.out.println("5. Stato conto, portafoglio e investimenti");
		System.out.println("\n0. Uscire");
	}

	public static int inserisciIntero() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		int intero = 0;
		boolean ok;

		do {

			ok = true;
			try {

				String input = scanner.nextLine().trim();
				intero = Integer.parseInt(input);

			} catch (NumberFormatException e) {
				System.out.println("Errore inserisci un numero valido.");
				ok = false;
			}
		} while (!ok);

		return intero;
	}

	public static double inserisciDouble() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);

		double double1 = 0;
		boolean ok;

		do {

			ok = true;
			try {

				String input = scanner.nextLine().trim();
				double1 = Double.parseDouble(input);

			} catch (NumberFormatException e) {
				System.out.println("Errore inserisci un numero valido.");
				ok = false;
			}
		} while (!ok);

		return double1;
	}

}
