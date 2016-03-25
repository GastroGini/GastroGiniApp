package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

public class Produkt {
    private int anzahl;
    private String beschreibung;
    private String groesse;
    private String preis;

    public Produkt(int anzahl, String beschreibung, String groesse, String preis){
        this.anzahl = anzahl;
        this.beschreibung = beschreibung;
        this.groesse = groesse;
        this.preis = preis;
    }

    public int getAnzahl(){
        return anzahl;
    }

    public String getBeschreibung(){
        return beschreibung;
    }

    public String getGroesse(){
        return groesse;
    }

    public String getPreis(){
        return preis;
    }
}
