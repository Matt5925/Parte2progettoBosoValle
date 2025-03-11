package GestioneBanca;
import java.util.Vector;
public class gestioneUtenti {

    Vector <Utente> tuttiGliUtenti=new Vector(10,5);

    public void push(Utente u) {
        tuttiGliUtenti.add(u);
    }


    public Utente remove(int i) {

        return tuttiGliUtenti.remove(i);

    }





    public boolean empty(){
        return tuttiGliUtenti.isEmpty();
    }

    public Utente pop() {
        if(!empty()) {
            return tuttiGliUtenti.remove(0);
        }else {
            return null;
        }
    }

    public void vediUtenti() {
        if(!empty()) {
            for (int i = 0; i < tuttiGliUtenti.size(); i++) {
                System.out.println("--------------------------------------------------------------------------------------");
                System.out.println("Veicolo "+(i+1));
                if(tuttiGliUtenti.elementAt(i)!=null)System.out.print(tuttiGliUtenti.elementAt(i));
            }
        }else System.out.println("Coda vuota");


    }
}




