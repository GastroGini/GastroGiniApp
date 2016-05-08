package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection;

import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.server.P2pServer;

public class ConnectionController extends Controller {

    private final P2pHandler p2p;

    enum ConnectionType {
        SERVER, CLIENT, DISCONNECTED
    }

    public static ConnectionController instance = new ConnectionController(App.getApp().getP2p());

    private static final String TAG = "ConnectionController";
    public P2pServer server;
    private ConnectionState connectionState;
    private ConnectionType connectionType;

    public void connectTo(ServiceResponseHolder serviceResponseHolder) {
        p2p.getClient().connectTo(serviceResponseHolder);
        connectionType = ConnectionType.CLIENT;
    }


    public void disconnect(){
        if (getConnectionState() == ConnectionState.DISCONNECTED) return;

        if (connectionType == ConnectionType.SERVER) {
            if (server == null) return;
            server.sendStop();
        }else if (connectionType == ConnectionType.CLIENT){
            p2p.getClient().disconnectClient();
        }
        connectionType = ConnectionType.DISCONNECTED;
        connectionState = ConnectionState.DISCONNECTED;
    }

    private ConnectionState getConnectionState() {
        if (connectionType == ConnectionType.CLIENT){
            return p2p.getClient().connectionState;
        }
        if (connectionType == ConnectionType.SERVER){
            return connectionState;
        }
        return ConnectionState.DISCONNECTED;
    }


    private ConnectionController(P2pHandler p2p){
        this.p2p = p2p;
        connectionType = ConnectionType.DISCONNECTED;
        connectionState = ConnectionState.DISCONNECTED;
    }

    public void startServer(Event event){
        if(server != null) {
            Log.d(TAG, "startServer: ");
            return;
        }
        server = new P2pServer(event, p2p);
        connectionState = ConnectionState.CONNECTED;
        connectionType = ConnectionType.SERVER;
    }





}
