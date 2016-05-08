package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;


import android.content.Context;
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
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectionState;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.ConnectionMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageReciever;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.TransferEvent;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.new_event_order.NewEventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.order_positions.OrderPositionsHolder;

public class P2pClient {


    private static final String TAG = P2pClient.class.toString();
    private final ClientMessageHandler messageHandler;


    private final P2pHandler p2p;

    public P2pHandler getP2p(){
        return p2p;
    }

    public ConnectionState connectionState;
    private String currentServerAddress;
    public boolean connectedToServer;
    private ArrayList<ServiceResponseHolder> serviceList;
    private boolean initialized;


    public P2pClient(P2pHandler p2p, Context context){
        this.p2p = p2p;
        serviceList = new ArrayList<>();

        messageHandler = new ClientMessageHandler(this);
        messageHandler.registerMessages();

        connectionState = ConnectionState.DISCONNECTED;

        registerServiceDiscovery();
        registerConnectionInfoListener();
        registerMessageReciever();

        new WifiBroadcastReciever(p2p, this, context);

    }

    public WifiP2pManager.ConnectionInfoListener connectionInfoListener;


    private MessageReciever messageReciever;

    private Handler reciever;

    private DoIt serviceResponseCallback = new DoNothing();

    public void setServiceResponseCallback(DoIt callback){
        serviceResponseCallback = callback;
    }

    public void removeServiceResponseCallback(){
        setServiceResponseCallback(new DoNothing());
    }

    private Consumer<String> onInitDataSuccess = new ConsumerDoNothing<>();

    public void setOnInitDataSuccess(Consumer<String> consumer){
        onInitDataSuccess = consumer;
    }

    public void removeOnInitDataSucces(){
        setOnInitDataSuccess(new ConsumerDoNothing<String>());
    }


    public void discoverServices(){
        if (!p2p.isWifiP2pEnabled())return;

        WifiP2pDnsSdServiceRequest wifiP2pServiceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        p2p.getWifiP2pManager().addServiceRequest(p2p.getWifiP2pChannel(), wifiP2pServiceRequest, null);
        p2p.getWifiP2pManager().discoverServices(p2p.getWifiP2pChannel(), null);
    }


    private Map<String, String> serviceInfo = new HashMap<>(); //TODO use service info for presentation
    private Map<String, String> serviceVisible = new HashMap();


    private void registerServiceDiscovery() {
        if (!p2p.isWifiP2pEnabled())return;

        p2p.getWifiP2pManager().setDnsSdResponseListeners(p2p.getWifiP2pChannel(),
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {

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
                        if (record.containsKey(P2pHandler.EVENT_INFO + "name")) {
                            TransferEvent ev = new TransferEvent(UUID.fromString(record.get(P2pHandler.EVENT_INFO + "uuid")), record.get(P2pHandler.EVENT_INFO + "name"), new Date());
                            String txt = new Gson().toJson(ev);
                            serviceInfo.put(device.deviceAddress, txt);
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
                serviceResponseCallback.doIt();
            } else if (connectionState == ConnectionState.RECONNECTING){
                for (ServiceResponseHolder holder:serviceList){
                    if (holder.device.deviceAddress.equals(currentServerAddress)){
                        connectTo(holder);
                    }
                }
            }
        }
    }


    public void connectTo(final ServiceResponseHolder holder){
        if (!p2p.isWifiP2pEnabled()) return;
        //p2p.disconnect(new DoNothing());
        //p2p.getWifiP2pManager().cancelConnect(p2p.getWifiP2pChannel(), null);
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = holder.device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        config.groupOwnerIntent = 2;
        p2p.getWifiP2pManager().connect(p2p.getWifiP2pChannel(), config, null);
    }



    private void registerConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {

            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if (!info.isGroupOwner) {
                    if(info.groupOwnerAddress != null) {
                        new ClientSocketHandler(reciever, info.groupOwnerAddress, new ConnectedDevice(p2p.getMacAddress())).start();
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
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    connectionState = ConnectionState.CONNECTED;
                    connectedToServer = true;
                    messageReciever = ((ConnectedDevice) msg.obj).handler;

                    sendMessage(new DataMessage(MessageAction.GET_INITIAL_DATA, null));

                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){
                    ConnectionMessage message = (ConnectionMessage) msg.obj;
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
        p2p.disconnect();
    }


    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public List<ServiceResponseHolder> getServiceList() {
        return serviceList;
    }

    public Consumer<String> getOnInitDataSuccess() {
        return onInitDataSuccess;
    }
}