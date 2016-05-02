package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection;

import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pServer;

public class ConnectionController extends Controller {

    private static final String TAG = "ConnectionController";
    public P2pServer server;

    public enum ConnectionState {
        CONNECTED, DISCONNECTED, RECONNECTING
    }


    public void stopServer(){
        if(server == null) return;
        server.sendStop();

    }


    public ConnectionController(){
    }

    public void startServer(Event event){
        if(server != null) {
            Log.d(TAG, "startServer: ");
            return;
        }
            server = new P2pServer(event);


    }



}
