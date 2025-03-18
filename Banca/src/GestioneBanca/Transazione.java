package GestioneBanca;

public class Transazione {

    private String mittente;
    private String destinatario;
    private double importo;

    public Transazione(String mittente, String destinatario, double importo){
        this.mittente=mittente;
        this.destinatario=destinatario;
        this.importo=importo;
    }


    public String toString (){
        String s="";
        s+=this.mittente;
        s+="---"+this.importo+"$--->";
        s+=this.destinatario;
        return s;
    }

}
