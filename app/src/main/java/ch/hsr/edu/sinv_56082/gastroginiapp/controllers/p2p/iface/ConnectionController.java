package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.iface;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client.P2pClient;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionType;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server.P2pServer;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class ConnectionController {

    private P2pHandler p2p;
    private P2pClient client;
    private P2pServer server;


    private DoIt connectionStateListener = new DoNothing();
    private volatile boolean tryReconnecting = false;

    public void addClientConnectionListener(DoIt doIt) {
        if (client == null) return;
        client.addClientConnectionListener(doIt);
    }

    public void removeClientConnectionListener() {
        if (client == null) return;
        client.removeClientConnectionListener();
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
        if (client == null) return;
        client.connectTo(serviceResponseHolder);
        connectionType = ConnectionType.CLIENT;
    }


    public void disconnect(){
        if (getConnectionState() == ConnectionState.DISCONNECTED) return;

        if (connectionType == ConnectionType.SERVER) {
            if (server == null) return;
            server.sendStop(new DoIt() {
                @Override
                public void doIt() {
                    server = null;
                }
            });
        }else if (connectionType == ConnectionType.CLIENT){
            if (client == null) return;
            client.disconnectClient();
        }
        connectionType = ConnectionType.DISCONNECTED;
        setConnectionState(ConnectionState.DISCONNECTED);
    }

    public ConnectionState getConnectionState() {
        if (connectionType == ConnectionType.CLIENT){
            if (client == null) return ConnectionState.DISCONNECTED;
            return client.getConnectionState();
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
                client = new P2pClient(p2p, context, new DoIt() {
                    @Override
                    public void doIt() {
                        setConnectionState(client.getConnectionState());
                        if (client.getConnectionState() == ConnectionState.RECONNECTING){
                            tryReconnecting = true;
                        }else {
                            tryReconnecting = false;
                        }
                    }
                });
            }
        });

        connectionType = ConnectionType.DISCONNECTED;
        setConnectionState(ConnectionState.DISCONNECTED);


        final Handler h = new Handler();
        final int delay = 30000;
        h.postDelayed(new Runnable() {
            public void run() {
                if (tryReconnecting){
                    if(connectionType == ConnectionType.CLIENT)
                        client.discoverServices();
                }
                h.postDelayed(this, delay);
            }
        }, delay);

    }

    public boolean startServer(Event event, String pw){
        if(server != null) {
            Log.d(TAG, "startServer: allready running");
            return false;
        }
        try {
            server = new P2pServer(event, pw, p2p);
            setConnectionState(ConnectionState.CONNECTED);
            connectionType = ConnectionType.SERVER;
        }catch (IOException e){
            Log.d(TAG, "startServer: failed to start server");
            return false;
        }
        return true;
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

    public void authenticate(String pw){
        if (client == null) return;
        client.authenticate(pw);
    }

    public void onConnectionEstablished(DoIt doIt){
        if (client == null) return;
        client.addConnectionEstablishedListener(doIt);
    }

    public void removeConnectionEstablished(){
        if (client == null) return;
        client.removeConnectionEstablishedListener();
    }

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
        if (client == null) return;
        client.setOnInitDataSuccess(consumer);
    }

    public void removeOnInitDataSuccess() {
        if (client == null) return;
        client.removeOnInitDataSucces();
    }

    public void sendNew(EventOrder eventOrder) {
        if (client == null) return;
        client.sendNew(eventOrder);
    }

    public void sendPayed(List<OrderPosition> opToPayList) {
        if (client == null) return;
        client.sendPayed(opToPayList);
    }

    public void sendDelete(List<OrderPosition> orderPositionsToDelete) {
        if (client == null) return;
        client.sendDelete(orderPositionsToDelete);
    }


    public void addConnectionStateListener(DoIt doIt) {
        connectionStateListener = doIt;
    }

    public void removeConnectionStateListener(){
        connectionStateListener = new DoNothing();
    }

    public void setConnectionState(ConnectionState connectionState) {
        this.connectionState = connectionState;
        connectionStateListener.doIt();
    }
}
