package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionState;

public class ConnectedDevice {
    public MessageReciever handler;

    public ConnectedDevice(String device) {
        this.device = device;
    }

    public String device;

    public ConnectionState connectionState = ConnectionState.CONNECTED;
}
