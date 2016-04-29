package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pServer;

public class ConnectionController extends Controller {

    private P2pServer server;

    enum ConnectionState {
        CONNECTED, DISCONNECTED, RECONNECTING
    }

    public ConnectionController(){
    }

    public void startServer(Event event){
        app.p2p.server = new P2pServer(event);
    }



}
