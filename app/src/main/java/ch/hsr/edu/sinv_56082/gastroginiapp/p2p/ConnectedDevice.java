package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;

public class ConnectedDevice {
    public MessageHandler handler;

    public ConnectedDevice(String device) {
        this.device = device;
    }

    public String device;

    public ConnectionController.ConnectionState connectionState = ConnectionController.ConnectionState.CONNECTED;
}
