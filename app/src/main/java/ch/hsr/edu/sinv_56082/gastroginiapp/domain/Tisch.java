package ch.hsr.edu.sinv_56082.gastroginiapp.domain;

public class Tisch {
    private String tischDescription;
    public Tisch(String tischDescription){
        this.tischDescription = tischDescription;
    }

    public String getTischDescription(){
        return tischDescription;
    }
}
