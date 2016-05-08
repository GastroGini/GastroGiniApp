package ch.hsr.edu.sinv_56082.gastroginiapp.p2p.client;


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

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.ConsumerDoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.MessageReciever;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.TransferEvent;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.WifiBroadcastReciever;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.new_event_order.NewEventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.order_positions.OrderPositionsHolder;

public class P2pClient {


    private static final String TAG = "P2pClient";
    private final App app;
    private final ClientMessageHandler messageHandler;

    public ConnectionState connectionState;
    private String currentServerAddress;
    public boolean connectedToServer;
    public ArrayList<ServiceResponseHolder> serviceList;
    private boolean initialized;


    public P2pClient(){
        this.app = App.getApp();

        serviceList = new ArrayList<>();

        messageHandler = new ClientMessageHandler(this);
        messageHandler.registerMessages();

        connectionState = ConnectionState.DISCONNECTED;

        registerServiceDiscovery();
        registerConnectionInfoListener();
        registerMessageReciever();

        new WifiBroadcastReciever(app.p2p, app);

    }

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener;


    private MessageReciever messageReciever;

    public MessageReciever getMessageReciever(){
        return messageReciever;
    }

    private Handler reciever;
    private List<DoIt> serviceResponseCallbacks = new ArrayList<>();

    public void addServiceResponseCallback(DoIt callback){
        serviceResponseCallbacks.add(callback);
    }

    public void removeServiceResponseCallback(DoIt callback){
        serviceResponseCallbacks.remove(callback);
    }


    public void discoverServices(){
        if (!app.p2p.isWifiP2pEnabled())return;

        WifiP2pDnsSdServiceRequest wifiP2pServiceRequest = WifiP2pDnsSdServiceRequest.newInstance();
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


    private void registerServiceDiscovery() {
        if (!app.p2p.isWifiP2pEnabled())return;

        Log.d(TAG, "registerServiceDiscovery: registering discovery listeners");

        app.p2p.wifiP2pManager.setDnsSdResponseListeners(app.p2p.wifiP2pChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {
                        Log.d(TAG, "onDnsSdServiceAvailable: "+instanceName+"--"+registrationType);
                        if (instanceName.startsWith(P2pHandler.SERVICE_INSTANCE)) {
                            serviceVisible.put(srcDevice.deviceAddress, instanceName.replace(P2pHandler.SERVICE_INSTANCE, ""));
                            onService(srcDevice);
                        }

                    }
                }, new WifiP2pManager.DnsSdTxtRecordListener() {
                    @Override
                    public void onDnsSdTxtRecordAvailable(
                            String fullDomainName, Map<String, String> record,
                            WifiP2pDevice device) {
                        Log.d("TEST",
                                device.deviceName + " is "+ record.get(P2pHandler.TXTRECORD_PROP_AVAILABLE));

                        if(record.containsKey(P2pHandler.EVENT_INFO +"name")){
                            TransferEvent ev = new TransferEvent(UUID.fromString(record.get(P2pHandler.EVENT_INFO + "uuid")),record.get(P2pHandler.EVENT_INFO +"name"), new Date());
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

            if (connectionState == ConnectionState.DISCONNECTED) {
                for (DoIt callback : serviceResponseCallbacks) {
                    if (callback != null) {
                        callback.doIt();
                    }
                }
            } else if (connectionState == ConnectionState.RECONNECTING){
                for (ServiceResponseHolder holder:serviceList){
                    if (holder.device.deviceAddress.equals(currentServerAddress)){
                        Log.d(TAG, "onService: RECONNECTING to server");
                        connectTo(holder);
                    }
                }
            }
        }
    }


    public void connectTo(final ServiceResponseHolder holder){
        if (!app.p2p.isWifiP2pEnabled()) return;
        //app.p2p.disconnect(new DoNothing());
        //app.p2p.wifiP2pManager.cancelConnect(app.p2p.wifiP2pChannel, null);
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
                        new ClientSocketHandler(reciever, info.groupOwnerAddress, new ConnectedDevice(app.p2p.macAddress)).start();
                    }
                }
            }
        };
    }

    private Map<String, MessageObject> messageHandlers;

    private void registerMessageReciever() {
        reciever = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: recieved"+msg+ P2pHandler.SET_MESSAGE_HANDLER);
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    connectionState = ConnectionState.CONNECTED;
                    connectedToServer = true;
                    Log.d(TAG, "handleMessage: sethandler");
                    messageReciever = ((ConnectedDevice) msg.obj).handler;

                    sendMessage(new DataMessage(MessageAction.GET_INITIAL_DATA, null));

                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage message = (ConnectionMessage) msg.obj;
                    Log.d(TAG, "handleMessage: " + message.content);
                    messageHandler.handleMessages(message);
                    return true;
                } else if (P2pHandler.DISCONNECTED == msg.what) {
                    connectionState = ConnectionState.RECONNECTING;
                    discoverServices();
                    return true;
                }
                return false;
            }
        });
    }

    private void sendMessage(DataMessage message){
        if (messageReciever == null) return;
        messageReciever.write(new Gson().toJson(message));
    }



    public Consumer<String> onInitDataSuccess = new ConsumerDoNothing<>(); //TODO find something better


    public void sendNew(EventOrder order){
        if (!isInitialized()) return;

        EventOrder newOrder = new ViewController<>(EventOrder.class).get(order.getUuid());
        NewEventOrder neo = new NewEventOrder(newOrder, newOrder.orderPositions());
        sendMessage(new DataMessage(MessageAction.NEW_ORDER, Serializer.get().toJson(neo)));
    }

    public void sendDelete(List<OrderPosition> orderPositions){
        if (!isInitialized()) return;

        List<OrderPosition> newOrderPositions = getNewOrderPositions(orderPositions);
        sendMessage(new DataMessage(MessageAction.DELETE_ORDER_POSITIONS, Serializer.get().toJson(new OrderPositionsHolder(newOrderPositions))));
    }

    public void sendPayed(List<OrderPosition> orderPositions){
        if (!isInitialized()) return;

        List<OrderPosition> newOrderPositions = getNewOrderPositions(orderPositions);
        sendMessage(new DataMessage(MessageAction.SET_ORDER_POSITIONS_PAYED, Serializer.get().toJson(new OrderPositionsHolder(newOrderPositions))));
    }

    public List<OrderPosition> getNewOrderPositions(List<OrderPosition> orderPositions){
        List<OrderPosition> newOrderPositions = new ArrayList<>();
        ViewController<OrderPosition> controller = new ViewController<>(OrderPosition.class);
        for (OrderPosition orderPosition: orderPositions){
            newOrderPositions.add(controller.get(orderPosition.getUuid()));
        }
        return newOrderPositions;
    }

    public void disconnectClient(){
        sendMessage(new DataMessage(MessageAction.DISCONNECT_CLIENT, new Object()));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                disconnect();
            }
        }, 3000);
    }

    public void disconnect(){
        connectionState = ConnectionState.DISCONNECTED;
        connectedToServer = false;
        messageReciever.terminate();
        setInitialized(false);
        app.p2p.disconnect();
    }


    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }
}
