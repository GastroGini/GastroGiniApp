package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.TransferEvent;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;

public class P2pServer {

    private static final String TAG = "SERVER::";
    private final P2pHandler p2p;
    private ServerSocketHandler serverService;
    protected Event runningEvent;

    protected String currentPassword = "";

    private Handler handler;
    private WifiP2pDnsSdServiceInfo wifiP2pService;
    DoIt orderPositionListener = new DoNothing();


    protected Map<String, ConnectedDevice> connectedDevices = new HashMap<>();
    private final ServerMessageHandler messageHandler;

    public P2pServer(Event event,  String pw, P2pHandler p2p) throws IOException {
        this.p2p = p2p;
        TransferEvent dto = new TransferEvent(event.getUuid(), event.name, event.startTime);
        runningEvent = event;
        currentPassword = pw;

        initGroup();
        removeLocalService();
        messageHandler = new ServerMessageHandler(this, runningEvent);
        messageHandler.registerMessages();
        registerMessageHandler();
        serverService = new ServerSocketHandler(handler, p2p.getMacAddress());
        serverService.start();
        setLocalService(dto);
    }

    public void initGroup() {
        if(!p2p.isWifiP2pEnabled()) return;
        p2p.getWifiP2pManager().cancelConnect(p2p.getWifiP2pChannel(), null);

        p2p.getWifiP2pManager().createGroup(p2p.getWifiP2pChannel(), new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: created group");
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
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    ConnectedDevice clientDevice = (ConnectedDevice) msg.obj;
                    connectedDevices.put(clientDevice.device, clientDevice);
                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage connectionMessage = (ConnectionMessage) msg.obj;
                    handleMessages(connectionMessage);
                    return true;
                }else if (P2pHandler.DISCONNECTED == msg.what){
                    connectedDevices.get(((ConnectionMessage) msg.obj).fromAddress).connectionState = ConnectionState.RECONNECTING;
                }
                return false;
            }
        });
    }

    void handleMessages(ConnectionMessage message){
        messageHandler.handleMessages(message);
    }

    public void removeLocalService(){
        if (p2p.isWifiP2pEnabled())
            p2p.getWifiP2pManager().clearLocalServices(p2p.getWifiP2pChannel(), new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: remove local service");
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: remove local service");
                }
            });
    }

    public void setLocalService(final TransferEvent eventName) {
        if (p2p.isWifiP2pEnabled()) {
            final Map<String, String> record = new HashMap<>();
            record.put(P2pHandler.TXTRECORD_PROP_AVAILABLE, "visible");
            //record.put(p2p.EVENT_INFO+"name", eventName.name);
            //record.put(p2p.EVENT_INFO+"uuid", eventName.uuid.toString());
            //record.put(p2p.EVENT_INFO+"date", eventName.startDate.toString());

            p2p.getWifiP2pManager().clearLocalServices(p2p.getWifiP2pChannel(), null);

            wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(P2pHandler.SERVICE_INSTANCE + eventName.name, P2pHandler.SERVICE_REG_TYPE, record);
            p2p.getWifiP2pManager().addLocalService(p2p.getWifiP2pChannel(), wifiP2pService, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: local service hosted"+eventName.name+record.toString());
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to host local service");
                }
            });
        }
    }

    public void sendMessage(String to, DataMessage message){
        connectedDevices.get(to).handler.write(new Gson().toJson(message));
    }

    public void sendStop(final DoIt doIt) {
        for (ConnectedDevice device : connectedDevices.values()){
            sendMessage(device.device, new DataMessage(MessageAction.STOP_SERVER, new Object()));
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stop(doIt);
            }
        }, 3000);
    }

    public void stop(DoIt doIt){
        for (ConnectedDevice device : connectedDevices.values()) {
            device.handler.terminate();
        }
        if (serverService!= null) {
            serverService.terminate();
        }
        p2p.disconnect();
        removeLocalService();
        doIt.doIt();
    }

    public void addOrderPositionListener(DoIt listener) {
        orderPositionListener = listener;
    }

    public void removeOrderPositionListener(){
        orderPositionListener = new DoNothing();
    }
}
