package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;

public class P2pClient {


    private static final String TAG = "P2pClient";
    private final App app;

    private ConnectionController.ConnectionState connectionState;
    public String currentServerAddress;
    public boolean connectedToServer;
    public ArrayList<ServiceResponseHolder> serviceList;
    private Map<String, MessageObject> messageHandlers;


    public P2pClient(){
        this.app = App.getApp();

        serviceList = new ArrayList<>();
        messageHandlers = new HashMap<>();

        registerMessageHandlers();

        connectionState = ConnectionController.ConnectionState.DISCONNECTED;

        registerServiceDiscovery();
        registerConnectionInfoListener();
        registerMessageHandler();

    }




    WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private WifiP2pDnsSdServiceRequest wifiP2pServiceRequest;


    private MessageHandler messageHandler;

    public MessageHandler getMessageHandler(){
        return messageHandler;
    }

    private Handler handler;
    private List<DoIt> serviceResponseCallbacks = new ArrayList<>();

    public void addServiceResponseCallback(DoIt callback){
        serviceResponseCallbacks.add(callback);
    }

    public void removeServiceResponseCallback(DoIt callback){
        serviceResponseCallbacks.remove(callback);
    }


    //TODO extract to client
    public void discoverServices(){
        wifiP2pServiceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        app.p2p.wifiP2pManager.addServiceRequest(app.p2p.wifiP2pChannel, wifiP2pServiceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: added Service Request");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failled to add service req");
            }
        });
        app.p2p.wifiP2pManager.discoverServices(app.p2p.wifiP2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: started service discovery");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failled to start service discovery");
            }
        });
    }


    private Map<String, String> serviceInfo = new HashMap<>(); //TODO use service info for presentation
    private Map<String, String> serviceVisible = new HashMap();


    //TODO extract to client
    private void registerServiceDiscovery() {
        if (!app.p2p.isWifiP2pEnabled())return;

        Log.d(TAG, "registerServiceDiscovery: registering discovery listeners");

        app.p2p.wifiP2pManager.setDnsSdResponseListeners(app.p2p.wifiP2pChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {
                        Log.d(TAG, "onDnsSdServiceAvailable: "+instanceName+"--"+registrationType);
                        if (instanceName.startsWith(app.p2p.SERVICE_INSTANCE)) {
                            serviceVisible.put(srcDevice.deviceAddress, instanceName.replace(app.p2p.SERVICE_INSTANCE, ""));
                            onService(srcDevice);
                        }

                    }
                }, new WifiP2pManager.DnsSdTxtRecordListener() {
                    @Override
                    public void onDnsSdTxtRecordAvailable(
                            String fullDomainName, Map<String, String> record,
                            WifiP2pDevice device) {
                        Log.d("TEST",
                                device.deviceName + " is "+ record.get(app.p2p.TXTRECORD_PROP_AVAILABLE));

                        if(record.containsKey(app.p2p.EVENT_INFO+"name")){
                            TransferEvent ev = new TransferEvent(UUID.fromString(record.get(app.p2p.EVENT_INFO + "uuid")),record.get(app.p2p.EVENT_INFO+"name"), new Date());
                            String txt = new Gson().toJson(ev);

                            serviceInfo.put(device.deviceAddress, txt);
                            Log.d(TAG, "onDnsSdTxtRecordAvailable: Found event" + txt);
                            onService(device);
                        }
                    }
                });
    }

    private void onService(WifiP2pDevice srcDevice){
        if (serviceVisible.containsKey(srcDevice.deviceAddress)) {
            ServiceResponseHolder service = new ServiceResponseHolder(srcDevice, serviceVisible.get(srcDevice.deviceAddress));
            for (ServiceResponseHolder holder : serviceList ){
                if (holder.device.deviceAddress.equals(service.device.deviceAddress)){
                    serviceList.remove(holder);
                    break;
                }
            }
            serviceList.add(service);

            if (connectionState == ConnectionController.ConnectionState.DISCONNECTED) {
                for (DoIt callback : serviceResponseCallbacks) {
                    if (callback != null) {
                        callback.doIt();
                    }
                }
            } else if (connectionState == ConnectionController.ConnectionState.RECONNECTING){
                for (ServiceResponseHolder holder:serviceList){
                    if (holder.device.deviceAddress.equals(currentServerAddress)){
                        connectTo(holder);
                    }
                }
            }
        }
    }


    public void connectTo(final ServiceResponseHolder holder){
        if (!app.p2p.isWifiP2pEnabled()) return;
        app.p2p.disconnect(new DoNothing());
        app.p2p.wifiP2pManager.cancelConnect(app.p2p.wifiP2pChannel, null);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = holder.device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        config.groupOwnerIntent = 2;
        app.p2p.wifiP2pManager.connect(app.p2p.wifiP2pChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                currentServerAddress = holder.device.deviceAddress;
                Log.d(TAG, "onSuccess: Connected to device: " + holder);
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failed to connect to device: " + holder);
            }
        });
    }



    private void registerConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {

            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                Log.d(TAG, "onConnectionInfoAvailable: Connection info recieved"+info.toString());

                if (!info.isGroupOwner) {
                    Log.d(TAG, "onConnectionInfoAvailable: starting Client");
                    if(info.groupOwnerAddress != null) {
                        new ClientSocketHandler(handler, info.groupOwnerAddress, new ConnectedDevice(app.p2p.macAddress)).start();
                        connectedToServer = true;
                    }
                }
            }
        };
    }

    private void registerMessageHandler() { //TODO maybe one handler for server and client
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: recieved"+msg+app.p2p.SET_MESSAGE_HANDLER);
                if(app.p2p.SET_MESSAGE_HANDLER == msg.what){
                    connectionState = ConnectionController.ConnectionState.CONNECTED;
                    connectedToServer = true;
                    Log.d(TAG, "handleMessage: sethandler");
                    messageHandler = ((ConnectedDevice) msg.obj).handler;

                    sendMessage(new DataMessage("get_initial_data", null));

                    return true;
                }else if (app.p2p.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage message = (ConnectionMessage) msg.obj;
                    Log.d(TAG, "handleMessage: " + message.content);
                    handleServerMessages(message);
                    return true;
                } else if (app.p2p.DISCONNECTED == msg.what) {
                    connectionState = ConnectionController.ConnectionState.RECONNECTING;
                    discoverServices();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendMessage(DataMessage message){
        if (messageHandler == null) return;
        messageHandler.write(new Gson().toJson(message));
    }

    private void handleServerMessages(ConnectionMessage message) {
        DataMessage dataMessage = new Gson().fromJson(message.content, DataMessage.class);
        MessageObject messager = messageHandlers.get(dataMessage.action);
        if (messager != null) messager.handleMessage(new Gson().fromJson(message.content, messager.type), message.fromAddress);
    }


    private void registerMessageHandlers() {
        messageHandlers.put("InitialData", new MessageObject<InitialDataMessage>(InitialDataMessage.class){
            @Override
            public void handleMessage(InitialDataMessage object, String fromAddress) {
                Log.d(TAG, "handleMessage: recieved init data"+ object + object.event+ object.productList);
            }
        });
    }

}
