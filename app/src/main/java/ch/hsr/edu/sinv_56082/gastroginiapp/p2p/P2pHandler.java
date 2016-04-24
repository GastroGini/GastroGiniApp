package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import android.content.Context;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
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

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Functions;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;

public class P2pHandler {

    private static final String SERVICE_INSTANCE = "_wifi_p2p_gastrogini_";
    private static final String SERVICE_REG_TYPE = "_presence._tcp";
    private static final String TXTRECORD_PROP_AVAILABLE = "available";
    private static final String TAG = "GastroGiniApplication";
    public static final int RECIEVED_MESSAGE = 14532;
    public static final int SERVICE_SERVER_PORT = 5638;
    public static final int SET_MESSAGE_HANDLER = 14533;


    Context application;
    private P2pHandler p2pHandler;


    public P2pHandler(final App application){
        this.application = application;
        p2pHandler = this;
        registerWifiP2pManager();
        if(isWifiP2pEnabled()) {
            Log.d(TAG, "P2pHandler: Disconnect all running connections from previous runs");
            disconnect(new Functions.DoIt() {
                @Override
                public void doIt() {
                    registerServiceDiscovery();
                    registerConnectionInfoListener();
                    registerMessageHandler();
                    new WifiBroadcastReciever(p2pHandler, application);
                }
            });
        }
    }

    boolean isWifiP2pEnabled=false;

    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel wifiP2pChannel;
    WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private WifiP2pDnsSdServiceRequest wifiP2pServiceRequest;
    private WifiP2pDnsSdServiceInfo wifiP2pService;

    private MessageHandler messageHandler;

    public MessageHandler getMessageHandler(){
        return messageHandler;
    }

    private Handler handler;
    private List<Functions.BiConsumer<String, WifiP2pDevice>> serviceResponseCallbacks = new ArrayList<>();

    public void disconnect(final Functions.DoIt doIt) {
        wifiP2pManager.removeGroup(wifiP2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                if(doIt!=null)doIt.doIt();
                Log.d(TAG, "onSuccess: Disconnected from P2P network");
            }

            @Override
            public void onFailure(int reason) {
                if(doIt!=null)doIt.doIt();
                Log.d(TAG, "onFailure: Failed to disconnect from P2p network! ReasonCode: "+reason);
            }
        });
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

    public void removeLocalService(){
        if (isWifiP2pEnabled())
            wifiP2pManager.clearLocalServices(wifiP2pChannel, null);
    }

    //TODO extract to server
    public void setLocalService(final String eventName) {
        if (isWifiP2pEnabled()) {
            Map<String, String> record = new HashMap<>();
            record.put(TXTRECORD_PROP_AVAILABLE, "visible");

            wifiP2pManager.clearLocalServices(wifiP2pChannel, null);

            wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_INSTANCE + eventName, SERVICE_REG_TYPE, record);
            wifiP2pManager.addLocalService(wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: added Local Service: " + SERVICE_INSTANCE + eventName);
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to add Service");
                }
            });
        }
    }

    public class ServiceResponseHolder {
        public String name;
        public WifiP2pDevice device;

        public ServiceResponseHolder(String name, WifiP2pDevice device) {
            this.name = name;
            this.device = device;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public void addServiceResponseCallback(Functions.BiConsumer<String, WifiP2pDevice> callback){
        serviceResponseCallbacks.add(callback);
    }

    public void removeServiceResponseCallback(Functions.BiConsumer<String, WifiP2pDevice> callback){
        serviceResponseCallbacks.remove(callback);
    }


    //TODO extract to client
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


    //TODO extract to client
    private void registerServiceDiscovery() {

        wifiP2pManager.setDnsSdResponseListeners(wifiP2pChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {

                        if (instanceName.startsWith(SERVICE_INSTANCE)) {
                            for (Functions.BiConsumer<String, WifiP2pDevice> callback : serviceResponseCallbacks) {
                                if (callback != null) {
                                    callback.consume(instanceName.replace(SERVICE_INSTANCE, ""), srcDevice);
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

    public void connectTo(final ServiceResponseHolder holder){
        if (isWifiP2pEnabled()) {
            WifiP2pConfig config = new WifiP2pConfig();
            config.deviceAddress = holder.device.deviceAddress;
            config.wps.setup = WpsInfo.PBC;
            wifiP2pManager.connect(wifiP2pChannel, config, new WifiP2pManager.ActionListener() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: Connected to device: " + holder);
                }

                @Override
                public void onFailure(int reason) {
                    Log.d(TAG, "onFailure: failed to connect to device: " + holder);
                }
            });
        }
    }


    private void registerConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                Log.d(TAG, "onConnectionInfoAvailable: Connection info recieved"+info.toString());

                if (info.isGroupOwner) {
                    try {
                        Log.d(TAG, "onConnectionInfoAvailable: Starting Server");
                        new ServerSocketHandler(handler).start();
                    } catch (IOException e) {
                        Log.e(TAG, "onConnectionInfoAvailable: ", e);
                    }
                } else {
                    Log.d(TAG, "onConnectionInfoAvailable: starting Client");
                    new ClientSocketHandler(handler, info.groupOwnerAddress).start();
                }
            }
        };
    }

    private void registerWifiP2pManager() {
        wifiP2pManager = (WifiP2pManager) application.getSystemService(application.WIFI_P2P_SERVICE);
        setIsWifiP2pEnabled(wifiP2pManager != null);
        if(isWifiP2pEnabled()) {
            wifiP2pChannel = wifiP2pManager.initialize(application, application.getMainLooper(), new WifiP2pManager.ChannelListener() {
                @Override
                public void onChannelDisconnected() {
                    Log.d(TAG, "onChannelDisconnected: Disconnected from Channel");
                }
            });
        }
    }




    public boolean isWifiP2pEnabled() {
        return isWifiP2pEnabled;
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }
}
