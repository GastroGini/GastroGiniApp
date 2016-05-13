package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client.P2pClient;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server.P2pServer;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class ConnectionController {

    private P2pHandler p2p;
    private P2pClient client;
    private P2pServer server;

    public void addClientConnectionListener(DoIt doIt) {
        if (client==null)return;
        client.addClientConnectionListener(doIt);
    }

    public void removeClientConnectionListener() {
        if (client == null)return;
        client.removeClientConnectionListener();
    }



    public enum ConnectionType {
        SERVER, CLIENT, DISCONNECTED
    }

    private static ConnectionController instance = new ConnectionController(App.getApp());

    public static ConnectionController getInstance(){
        return instance;
    }

    private static final String TAG = "ConnectionController";

    private ConnectionState connectionState;

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    private ConnectionType connectionType;

    public void connectTo(ServiceResponseHolder serviceResponseHolder) {
        if (client == null)return;
        client.connectTo(serviceResponseHolder);
        connectionType = ConnectionType.CLIENT;
    }


    public void disconnect(){
        if (getConnectionState() == ConnectionState.DISCONNECTED) return;

        if (connectionType == ConnectionType.SERVER) {
            if (server == null) return;
            server.sendStop();
        }else if (connectionType == ConnectionType.CLIENT){
            if (client == null) return;
            client.disconnectClient();
        }
        connectionType = ConnectionType.DISCONNECTED;
        connectionState = ConnectionState.DISCONNECTED;
    }

    private ConnectionState getConnectionState() {
        if (connectionType == ConnectionType.CLIENT){
            if (client == null) return ConnectionState.DISCONNECTED;
            return client.connectionState;
        }
        if (connectionType == ConnectionType.SERVER){
            return connectionState;
        }
        return ConnectionState.DISCONNECTED;
    }


    private ConnectionController(final Context context){
        this.p2p = new P2pHandler(context, new DoIt() {
            @Override
            public void doIt() {
                client = new P2pClient(p2p, context);
            }
        });

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

    /*
            SERVER Forwarding
     */

    public void addOrderPositionListener(DoIt listener){
        if (server == null) return;

        server.addOrderPositionListener(listener);
    }

    public void removeOrderPositionListener(){
        if (server == null) return;
        server.removeOrderPositionListener();
    }

    /*
            CLIENT Forwarding
     */


    public List<ServiceResponseHolder> getServiceList() {
        if (client == null) return new ArrayList<>();

        return client.getServiceList();
    }

    public void addServiceResponseCallback(DoIt responseCallback) {
        if (client == null) return;
        client.setServiceResponseCallback(responseCallback);
    }

    public void discoverServices() {
        if (client == null) return;
        client.discoverServices();
    }

    public void removeServiceResponseCallback() {
        if (client == null) return;
        client.removeServiceResponseCallback();
    }

    public void setOnInitDataSuccess(Consumer<String> consumer) {
        if (client == null)return;
        client.setOnInitDataSuccess(consumer);
    }

    public void removeOnInitDataSuccess() {
        if (client == null)return;
        client.removeOnInitDataSucces();
    }

    public void sendNew(EventOrder eventOrder) {
        if (client == null) return;
        client.sendNew(eventOrder);
    }

    public void sendPayed(List<OrderPosition> opToPayList) {
        if (client == null)return;
        client.sendPayed(opToPayList);
    }

    public void sendDelete(List<OrderPosition> orderPositionsToDelete) {
        if (client == null) return;
        client.sendDelete(orderPositionsToDelete);
    }


}
