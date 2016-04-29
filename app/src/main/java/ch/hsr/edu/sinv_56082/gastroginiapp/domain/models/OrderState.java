package ch.hsr.edu.sinv_56082.gastroginiapp.domain.models;

public enum OrderState {

    STATE_OPEN("Open", "Open"), STATE_PAYED("Payed", "Payed");

    OrderState(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String name;
    public String description;
}
