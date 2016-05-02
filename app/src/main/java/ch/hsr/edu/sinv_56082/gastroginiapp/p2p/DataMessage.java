package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.Serializer;

public class DataMessage {

    public DataMessage(String action, String conntent) {
        this.action = action;
        this.conntent = conntent;
    }

    public DataMessage(String action, Object conntent){
        this.action = action;
        this.conntent = Serializer.get().toJson(conntent);
    }

    public String action;
    public String conntent;
}
