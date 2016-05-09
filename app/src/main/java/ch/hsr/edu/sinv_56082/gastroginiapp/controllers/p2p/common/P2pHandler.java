package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoNothing;

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

    private Context context;
    private String macAddress;
    private boolean isWifiP2pEnabled=false;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel wifiP2pChannel;

    public String getMacAddress() {
        return macAddress;
    }

    public WifiP2pManager.Channel getWifiP2pChannel() {
        return wifiP2pChannel;
    }

    public WifiP2pManager getWifiP2pManager() {
        return wifiP2pManager;
    }

    public P2pHandler(final Context context, DoIt doIt){
        this.context = context;
        registerWifiP2pManager();
        if(isWifiP2pEnabled()) {
            disconnect(doIt);
        }
    }

    public void disconnect(){
        disconnect(new DoNothing());
    }

    public void disconnect(final DoIt doIt) {
        if(!isWifiP2pEnabled()) return;

        wifiP2pManager.removeGroup(wifiP2pChannel, null);
    }


    private void registerWifiP2pManager() {
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        setIsWifiP2pEnabled(manager != null);
        if (isWifiP2pEnabled()) {
            WifiInfo info = manager.getConnectionInfo();
            macAddress = info.getMacAddress();

            wifiP2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
            setIsWifiP2pEnabled(wifiP2pManager != null);
            if (isWifiP2pEnabled()) {
                wifiP2pChannel = wifiP2pManager.initialize(context, context.getMainLooper(), new WifiP2pManager.ChannelListener() {
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
