package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages;


import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization.Serializer;

public class DataMessage {

    public DataMessage(MessageAction action, String conntent) {
        this.action = action;
        this.content = conntent;
    }

    public DataMessage(MessageAction action, Object conntent){
        this.action = action;
        this.content = Serializer.get().toJson(conntent);
    }

    public MessageAction action;
    public String content;
}
