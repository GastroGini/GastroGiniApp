package ch.hsr.edu.sinv_56082.gastroginiapp.p2p.server;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.TransferEvent;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.MessageAction;

public class P2pServer {

    private static final String TAG = "SERVER::";
    private final App app;
    private ServerSocketHandler serverService;
    protected Event runningEvent;
    private Handler handler;
    private WifiP2pDnsSdServiceInfo wifiP2pService;

    protected Map<String, ConnectedDevice> connectedDevices = new HashMap<>();
    private final ServerMessageHandler messageHandler;

    public P2pServer(Event event){
        TransferEvent dto = new TransferEvent(event.getUuid(), event.name, event.startTime);
        app = App.getApp();
        runningEvent = event;

        initGroup();
        removeLocalService();
        messageHandler = new ServerMessageHandler(this);
        messageHandler.registerMessages();
        registerMessageHandler();
        try {
            serverService = new ServerSocketHandler(handler, app.p2p.macAddress);
            serverService.start();
        } catch (IOException e) {
            Log.e(TAG, "P2pServer: failed to create server socket", e);
        }
        setLocalService(dto);


    }

    public void initGroup() {
        if(!app.p2p.isWifiP2pEnabled()) return;
        app.p2p.wifiP2pManager.cancelConnect(app.p2p.wifiP2pChannel, null);

        app.p2p.wifiP2pManager.createGroup(app.p2p.wifiP2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Created Group");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failed to create group");
            }
        });
    }

    private void registerMessageHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: recieved"+msg+ P2pHandler.SET_MESSAGE_HANDLER+P2pHandler.RECIEVED_MESSAGE);
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    ConnectedDevice clientDevice = (ConnectedDevice) msg.obj;

                    connectedDevices.put(clientDevice.device, clientDevice);
                    //clientDevice.handler.write("Server hello");
                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage connectionMessage = (ConnectionMessage) msg.obj;

                    Log.d(TAG, "handleMessage: " + connectionMessage.content);
                    messageHandler.handleMessages(connectionMessage);

                    return true;
                }else if (P2pHandler.DISCONNECTED == msg.what){
                    connectedDevices.get(((ConnectionMessage) msg.obj).fromAddress).connectionState = ConnectionState.RECONNECTING;
                }
                return false;
            }
        });
    }

    public void removeLocalService(){
        if (app.p2p.isWifiP2pEnabled())
            app.p2p.wifiP2pManager.clearLocalServices(app.p2p.wifiP2pChannel, null);
    }

    public void setLocalService(final TransferEvent eventName) {
        if (app.p2p.isWifiP2pEnabled()) {
            Map<String, String> record = new HashMap<>();
            record.put(P2pHandler.TXTRECORD_PROP_AVAILABLE, "visible");
            //record.put(app.p2p.EVENT_INFO+"name", eventName.name);
            //record.put(app.p2p.EVENT_INFO+"uuid", eventName.uuid.toString());
            //record.put(app.p2p.EVENT_INFO+"date", eventName.startDate.toString());

            app.p2p.wifiP2pManager.clearLocalServices(app.p2p.wifiP2pChannel, null);

            wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(P2pHandler.SERVICE_INSTANCE + eventName.name, P2pHandler.SERVICE_REG_TYPE, record);
            app.p2p.wifiP2pManager.addLocalService(app.p2p.wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: added Local Service: " + P2pHandler.SERVICE_INSTANCE + eventName);
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to add Service" + P2pHandler.SERVICE_INSTANCE + eventName);
                }
            });
        }
    }

    public void sendMessage(String to, DataMessage message){
        connectedDevices.get(to).handler.write(new Gson().toJson(message));
    }

    public void sendStop() {
        for (ConnectedDevice device : connectedDevices.values()){
            sendMessage(device.device, new DataMessage(MessageAction.STOP_SERVER, new Object()));
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop();
            }
        }, 3000);
    }

    public void stop(){
        for (ConnectedDevice device : connectedDevices.values()) {
            device.handler.terminate();
        }
        serverService.terminate();
        app.p2p.disconnect(new DoNothing());
        removeLocalService();
    }
}
