package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection;

import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.Controller;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.client.P2pClient;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.server.P2pServer;

public class ConnectionController extends Controller {

    enum ConnectionType {
        SERVER, CLIENT, DISCONNECTED
    }

    public static ConnectionController instance = new ConnectionController();

    private static final String TAG = "ConnectionController";
    public P2pServer server;
    private ConnectionState connectionState;
    private ConnectionType connectionType;

    public void connectTo(ServiceResponseHolder serviceResponseHolder) {
        App.getApp().p2p.client.connectTo(serviceResponseHolder);
        connectionType = ConnectionType.CLIENT;
    }


    public void disconnect(){
        if (getConnectionState() == ConnectionState.DISCONNECTED) return;

        if (connectionType == ConnectionType.SERVER) {
            if (server == null) return;
            server.sendStop();
        }else if (connectionType == ConnectionType.CLIENT){
            App.getApp().p2p.client.disconnectClient();
        }
        connectionType = ConnectionType.DISCONNECTED;
        connectionState = ConnectionState.DISCONNECTED;
    }

    private ConnectionState getConnectionState() {
        if (connectionType == ConnectionType.CLIENT){
            return App.getApp().p2p.client.connectionState;
        }
        if (connectionType == ConnectionType.SERVER){
            return connectionState;
        }
        return ConnectionState.DISCONNECTED;
    }


    private ConnectionController(){
        connectionType = ConnectionType.DISCONNECTED;
        connectionState = ConnectionState.DISCONNECTED;
    }

    public void startServer(Event event){
        if(server != null) {
            Log.d(TAG, "startServer: ");
            return;
        }
        server = new P2pServer(event);
        connectionState = ConnectionState.CONNECTED;
        connectionType = ConnectionType.SERVER;
    }





}
