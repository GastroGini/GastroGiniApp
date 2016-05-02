package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;

public class P2pHandler {

    private static final String TAG = "P2PHandler";



    public static final String SERVICE_INSTANCE = "_|gagi|_";
    public static final String SERVICE_REG_TYPE = "_presence._tcp";
    public static final String TXTRECORD_PROP_AVAILABLE = "available";
    public static final int RECIEVED_MESSAGE = 14532;
    public static final int SERVICE_SERVER_PORT = 5638;
    public static final int SET_MESSAGE_HANDLER = 14533;
    public static final String EVENT_INFO = "event-info";
    public static final int DISCONNECTED = 14534;


    Context application;
    private P2pHandler p2pHandler;
    public String macAddress;
    public P2pClient client;


    public P2pHandler(final App application){
        this.application = application;
        p2pHandler = this;
        registerWifiP2pManager();
        if(isWifiP2pEnabled()) {
            Log.d(TAG, "P2pHandler: Disconnect all running connections from previous runs");
            Log.d(TAG, "P2pHandler: MacAddress: "+macAddress);
            disconnect(new DoIt() {
                @Override
                public void doIt() {
                    client = new P2pClient();
                    new WifiBroadcastReciever(p2pHandler, application);
                }
            });
        }
    }

    boolean isWifiP2pEnabled=false;

    WifiP2pManager wifiP2pManager;
    WifiP2pManager.Channel wifiP2pChannel;



    public void disconnect(final DoIt doIt) {
        if(!isWifiP2pEnabled()) return;
        wifiP2pManager.cancelConnect(wifiP2pChannel, null);
        wifiP2pManager.clearLocalServices(wifiP2pChannel, null);
        wifiP2pManager.clearServiceRequests(wifiP2pChannel, null);
        wifiP2pManager.stopPeerDiscovery(wifiP2pChannel, null);
        wifiP2pManager.removeGroup(wifiP2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                doIt.doIt();
                Log.d(TAG, "onSuccess: Disconnected from P2P network");
            }

            @Override
            public void onFailure(int reason) {
                doIt.doIt();
                Log.d(TAG, "onFailure: Failed to disconnect from P2p network! ReasonCode: " + reason);
            }
        });
    }





























































    private void registerWifiP2pManager() {
        WifiManager manager = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
        setIsWifiP2pEnabled(manager != null);
        if (isWifiP2pEnabled()) {
            WifiInfo info = manager.getConnectionInfo();
            macAddress = info.getMacAddress();

            wifiP2pManager = (WifiP2pManager) application.getSystemService(application.WIFI_P2P_SERVICE);
            setIsWifiP2pEnabled(wifiP2pManager != null);
            if (isWifiP2pEnabled()) {
                wifiP2pChannel = wifiP2pManager.initialize(application, application.getMainLooper(), new WifiP2pManager.ChannelListener() {
                    @Override
                    public void onChannelDisconnected() {
                        Log.d(TAG, "onChannelDisconnected: Disconnected from Channel");
                    }
                });
            }
        }
    }


    public boolean isWifiP2pEnabled() {
        return isWifiP2pEnabled;
    }

    public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
        this.isWifiP2pEnabled = isWifiP2pEnabled;
    }


}
