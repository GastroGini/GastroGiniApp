package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.MessageAction;

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
