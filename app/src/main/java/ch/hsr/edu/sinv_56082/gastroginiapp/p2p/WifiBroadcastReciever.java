package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by tobias on 18.04.2016.
 */
public class WifiBroadcastReciever {

    BroadcastReceiver wifiBroadcatsReciver;
    Context application;
    P2pHandler p2pHandler;
    IntentFilter intentFilter;


    WifiBroadcastReciever(Context context, P2pHandler p2pHandler){
        application = context;
        this.p2pHandler = p2pHandler;
        intentFilter = new IntentFilter();
        registerWifiBroadcastReciver();
        startBroadcastReciever();
    }


    private void startBroadcastReciever(){
        if (p2pHandler.isWifiP2pEnabled())
            application.registerReceiver(wifiBroadcatsReciver, intentFilter);
    }

    private void registerWifiBroadcastReciver() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        final String TAG ="WIFI_BroadcastReciever";
        wifiBroadcatsReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(TAG, "onReceive: " + action);
                if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
                    int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                        p2pHandler.setIsWifiP2pEnabled(true);
                    } else {
                        p2pHandler.setIsWifiP2pEnabled(false);
                    }
                } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

                } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                    if (p2pHandler.wifiP2pManager == null) return;

                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                    if (networkInfo.isConnected()) {
                        p2pHandler.wifiP2pManager.requestConnectionInfo(p2pHandler.wifiP2pChannel, p2pHandler.connectionInfoListener);
                    }
                } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {}
            }
        };
    }

}
