package ch.hsr.edu.sinv_56082.gastroginiapp.app;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class P2pHandler {

    private static final String SERVICE_INSTANCE = "_wifi_p2p_gastrogini_";
    private static final String SERVICE_REG_TYPE = "_presence._tcp";
    private static final String TXTRECORD_PROP_AVAILABLE = "available";
    private static final String TAG = "GastroGiniApplication";
    public static final int RECIEVED_MESSAGE = 14532;
    public static final int SERVICE_SERVER_PORT = 5638;
    public static final int SET_MESSAGE_HANDLER = 14533;


    BroadcastReceiver wifiBroadcatsReciver;
    LocalData application;


    public P2pHandler(LocalData application){
        this.application = application;
        registerIntentFilter();
        registerWifiP2pManager();
        registerServiceDiscovery();
        registerConnectionInfoListener();
        registerWifiBroadcastReciver();
        registerMessageHandler();
    }


    boolean isWifiP2pEnabled=false;

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel wifiP2pChannel;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private WifiP2pDnsSdServiceRequest wifiP2pServiceRequest;
    private WifiP2pDnsSdServiceInfo wifiP2pService;

    private MessageHandler messageHandler;

    public MessageHandler getMessageHandler(){
        return messageHandler;
    }

    private Handler handler;
    private List<ServiceResponseCallback> serviceResponseCallbacks = new ArrayList<>();



    public interface MessageReciever{
        boolean onRecieve(String messageType, String messageJSON);
    }



    private void registerMessageHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(SET_MESSAGE_HANDLER == msg.what){
                    messageHandler = (MessageHandler) msg.obj;
                    return true;
                }else if (RECIEVED_MESSAGE == msg.what){

                    Log.d(TAG, "handleMessage: " + ((String) msg.obj));
                    return true;
                }
                return false;
            }
        });
    }

    public void removeLocalServie(){
        wifiP2pManager.clearLocalServices(wifiP2pChannel, null);
    }

    public void setLocalService(String eventName) {
        Map<String, String> record = new HashMap<>();
        record.put(TXTRECORD_PROP_AVAILABLE, "visible");

        wifiP2pManager.clearLocalServices(wifiP2pChannel, null);

        wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_INSTANCE + eventName, SERVICE_REG_TYPE, record);
        wifiP2pManager.addLocalService(wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: added Local Service: " + SERVICE_INSTANCE);
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failed to add Service");
            }
        });
    }

    public class ServiceResponseHolder {
        public String name;
        public WifiP2pDevice device;

        public ServiceResponseHolder(String name, WifiP2pDevice device) {
            this.name = name;
            this.device = device;
        }
    }

    public interface ServiceResponseCallback{
        void onNewServiceResponse(ServiceResponseHolder service);
    }

    public void addServiceResponseCallback(ServiceResponseCallback callback){
        serviceResponseCallbacks.add(callback);
    }

    public void removeServiceResponseCallback(ServiceResponseCallback callback){
        serviceResponseCallbacks.remove(callback);
    }


    public void discoverServices(){
        wifiP2pServiceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        wifiP2pManager.addServiceRequest(wifiP2pChannel, wifiP2pServiceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: added Service Request");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failled to add service req");
            }
        });
        wifiP2pManager.discoverServices(wifiP2pChannel, new WifiP2pManager.ActionListener() {
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


    private void registerServiceDiscovery() {

        wifiP2pManager.setDnsSdResponseListeners(wifiP2pChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {

                        if (instanceName.startsWith(SERVICE_INSTANCE)) {
                            for (ServiceResponseCallback callback : serviceResponseCallbacks) {
                                if (callback != null) {
                                    callback.onNewServiceResponse(new ServiceResponseHolder(
                                            instanceName.replace(SERVICE_INSTANCE, ""),
                                            srcDevice
                                    ));
                                }
                            }
                        }

                    }
                }, new WifiP2pManager.DnsSdTxtRecordListener() {
                    @Override
                    public void onDnsSdTxtRecordAvailable(
                            String fullDomainName, Map<String, String> record,
                            WifiP2pDevice device) {
                        Log.d("TEST",
                                device.deviceName + " is available");
                    }
                });



    }

    private void registerConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if (info.isGroupOwner) {
                    try {
                        new ServerSocketHandler(handler).start();
                    } catch (IOException e) {
                        Log.e(TAG, "onConnectionInfoAvailable: ", e);
                    }
                } else {
                    new ClientSocketHandler(handler, info.groupOwnerAddress).start();
                }
            }
        };
    }

    private void registerWifiP2pManager() {
        wifiP2pManager = (WifiP2pManager) application.getSystemService(application.WIFI_P2P_SERVICE);
        wifiP2pChannel = wifiP2pManager.initialize(application, application.getMainLooper(), new WifiP2pManager.ChannelListener() {
            @Override
            public void onChannelDisconnected() {
                Log.d(TAG, "onChannelDisconnected: Disconnected from Channel");
            }
        });
    }

    private void registerWifiBroadcastReciver() {
        wifiBroadcatsReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                    int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                        setIsWifiP2pEnabled(true);
                    } else {
                        setIsWifiP2pEnabled(false);
                    }
                } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                    if (wifiP2pManager == null) return;

                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                    if (networkInfo.isConnected()) {
                        wifiP2pManager.requestConnectionInfo(wifiP2pChannel, connectionInfoListener);
                    }
                } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {}
            }
        };
    }



    private void registerIntentFilter() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }


    public boolean isWifiP2pEnabled() {
        return isWifiP2pEnabled;
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }
}
