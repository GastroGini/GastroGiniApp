package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common;


public class ConnectedDevice {
    public MessageReciever handler;

    public ConnectedDevice(String device) {
        this.device = device;
    }

    public String device;

    public ConnectionState connectionState = ConnectionState.CONNECTED;
}
