package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common;


public class ConnectionMessage {
    public ConnectionMessage(String fromAddress, String content) {
        this.fromAddress = fromAddress;
        this.content = content;
    }

    public String fromAddress;
    public String content;

    @Override
    public String toString() {
        return fromAddress + " >::> " +content;
    }
}
