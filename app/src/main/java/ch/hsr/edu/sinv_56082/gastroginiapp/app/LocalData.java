package ch.hsr.edu.sinv_56082.gastroginiapp.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Looper;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Person;

public class LocalData extends Application {

    private static final String SERVICE_INSTANCE = "_wifi_p2p_gastrogini_";
    private static final String SERVICE_REG_TYPE = "_presence._tcp";
    private static final String TXTRECORD_PROP_AVAILABLE = "available";
    private static final String TAG = "GastroGiniApplication";


    private UUID localUser;
    private SharedPreferences preferences;

    BroadcastReceiver wifiBroadcatsReciver;

    LocalData application;



    boolean isWifiP2pEnabled=false;

    private final IntentFilter intentFilter = new IntentFilter();
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel wifiP2pChannel;
    private WifiP2pManager.PeerListListener peerListListener;
    private List<WifiP2pDevice> peerList;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;
    private WifiP2pDnsSdServiceRequest wifiP2pServiceRequest;
    private WifiP2pDnsSdServiceInfo wifiP2pService;


    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ActiveAndroid.initialize(this);
        initLocalUser();
        registerIntentFilter();
        registerWifiP2pManager();
        addLocalService();
        discoverServices();
        registerConnectionInfoListener();
        registerWifiBroadcastReciver();
    }

    private void addLocalService() {
        Map<String, String> record = new HashMap<>();
        record.put(TXTRECORD_PROP_AVAILABLE, "visible");

        wifiP2pService = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_INSTANCE, SERVICE_REG_TYPE, record);
        wifiP2pManager.addLocalService(wifiP2pChannel, wifiP2pService, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: added Local Service: "+SERVICE_INSTANCE);
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "onFailure: failed to add Service");
            }
        });
    }

    private void discoverServices() {

        wifiP2pManager.setDnsSdResponseListeners(wifiP2pChannel,
                new WifiP2pManager.DnsSdServiceResponseListener() {
                    
                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, final WifiP2pDevice srcDevice) {

                        if (instanceName.equalsIgnoreCase(SERVICE_INSTANCE)) {
                            WifiP2pConfig conf = new WifiP2pConfig();
                            conf.deviceAddress = srcDevice.deviceAddress;
                            conf.wps.setup = WpsInfo.PBC;
                            wifiP2pManager.connect(wifiP2pChannel, conf, new WifiP2pManager.ActionListener() {
                                @Override
                                public void onSuccess() {
                                    Log.d(TAG, "onSuccess: connected to "+srcDevice.deviceName);
                                }

                                @Override
                                public void onFailure(int reason) {
                                    Log.d(TAG, "onFailure: failled to connect");
                                }
                            });
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

    private void registerConnectionInfoListener() {
        connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if (info.isGroupOwner) {
                    // TODO Start Server
                } else {
                    // Todo Start Client
                }
            }
        };
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterReceiver(wifiBroadcatsReciver);
    }

    private void registerWifiP2pManager() {
        wifiP2pManager = (WifiP2pManager) getSystemService(WIFI_P2P_SERVICE);
        wifiP2pChannel = wifiP2pManager.initialize(this, getMainLooper(), new WifiP2pManager.ChannelListener() {
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
                        application.setIsWifiP2pEnabled(true);
                    } else {
                        application.setIsWifiP2pEnabled(false);
                    }
                } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

                    if (wifiP2pManager == null) return;

                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                    if (networkInfo.isConnected()) {
                        wifiP2pManager.requestConnectionInfo(wifiP2pChannel, connectionInfoListener);
                    }
                } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {


                }
            }
        };
        registerReceiver(wifiBroadcatsReciver, intentFilter);
    }

    private void initLocalUser() {
        preferences = getSharedPreferences("LocalUserData", MODE_PRIVATE);
        if (preferences.getString("local-user-uuid", null) != null){
            localUser = UUID.fromString(preferences.getString("local-user-uuid", null));
            Log.d("LocalData", "onCreate: loaded User: " + localUser.toString());
        }else{
            Person localUserPerson = new Person("local", "user");
            localUserPerson.save();
            setLocalUser(localUserPerson.uuid);
        }
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


    public void setLocalUser(UUID localUser) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("local-user-uuid", localUser.toString());
        editor.commit();
        this.localUser = localUser;
    }

    public UUID getLocalUser() {
        return localUser;
    }
}
