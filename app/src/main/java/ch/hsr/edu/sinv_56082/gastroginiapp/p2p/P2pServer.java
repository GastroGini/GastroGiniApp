package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;

public class P2pServer {

    private static final String TAG = "SERVER::";
    private final App app;
    private TransferEvent dto;
    private Event runningEvent;
    private Handler handler;

    public WifiP2pDnsSdServiceInfo wifiP2pService;



    private Map<String, ConnectedDevice> connectedDevices = new HashMap<>();

    public P2pServer(Event event){
        dto = new TransferEvent(event.getUuid(), event.name, event.startTime);
        app = App.getApp();
        runningEvent = event;
        messageHandlers = new HashMap<>();

        initGroup();
        removeLocalService();
        registerMessageHandlers();
        registerMessageHandler();
        try {
            new ServerSocketHandler(handler, app.p2p.macAddress).start();
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
                Log.d(TAG, "handleMessage: recieved"+msg+P2pHandler.SET_MESSAGE_HANDLER+P2pHandler.RECIEVED_MESSAGE);
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    ConnectedDevice clientDevice = (ConnectedDevice) msg.obj;

                    connectedDevices.put(clientDevice.device, clientDevice);
                    clientDevice.handler.write("Server hello");
                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage connectionMessage = (ConnectionMessage) msg.obj;

                    Log.d(TAG, "handleMessage: " + connectionMessage.content);
                    handleServerMessages(connectionMessage);

                    return true;
                }else if (P2pHandler.DISCONNECTED == msg.what){
                    connectedDevices.get(((ConnectionMessage) msg.obj).fromAddress).connectionState = ConnectionController.ConnectionState.RECONNECTING;
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
            record.put(app.p2p.TXTRECORD_PROP_AVAILABLE, "visible");
            record.put(app.p2p.EVENT_INFO+"name", eventName.name);
            record.put(app.p2p.EVENT_INFO+"uuid", eventName.uuid.toString());
            record.put(app.p2p.EVENT_INFO+"date", eventName.startDate.toString());

            app.p2p.wifiP2pManager.clearLocalServices(app.p2p.wifiP2pChannel, null);

            wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(app.p2p.SERVICE_INSTANCE + eventName.name, app.p2p.SERVICE_REG_TYPE, record);
            app.p2p.wifiP2pManager.addLocalService(app.p2p.wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: added Local Service: " + app.p2p.SERVICE_INSTANCE + eventName);
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to add Service" + app.p2p.SERVICE_INSTANCE + eventName);
                }
            });
        }
    }


    private Map<String, MessageObject> messageHandlers;


    private void handleServerMessages(ConnectionMessage message) { //TODO duplicated code
        DataMessage dataMessage = new Gson().fromJson(message.content, DataMessage.class);
        MessageObject messager = messageHandlers.get(dataMessage.action);
        if (messager != null) messager.handleMessage(new Gson().fromJson(message.content, messager.type), message.fromAddress);
    }

    private void sendMessage(String to, DataMessage message){
        connectedDevices.get(to).handler.write(new Gson().toJson(message));
    }

    private void registerMessageHandlers() {
        messageHandlers.put("get_initial_data", new MessageObject<Object>(Object.class){
            @Override
            public void handleMessage(Object object, String from) {
                List<Product> products = runningEvent.productList.products();
                InitialDataMessage msg = new InitialDataMessage(runningEvent, products);

                sendMessage(from, new DataMessage("InitialData", msg));
            }
        });


    }



    public void sendStop() {

    }
}
