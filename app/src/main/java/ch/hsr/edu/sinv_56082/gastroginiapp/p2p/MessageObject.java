package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

public abstract class MessageObject<M> {
    public final Class<M> type;

    public MessageObject(Class<M> type){
        this.type = type;
    }

    public abstract void handleMessage(M object, String fromAddress);
}
