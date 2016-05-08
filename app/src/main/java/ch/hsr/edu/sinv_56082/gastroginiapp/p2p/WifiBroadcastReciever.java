package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;



public class WifiBroadcastReciever {

    private BroadcastReceiver wifiBroadcatsReciver;
    private Context application;
    private P2pHandler p2pHandler;
    private IntentFilter intentFilter;

    public WifiBroadcastReciever(P2pHandler p2pHandler, Context application){
        this.application = application;
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
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        wifiBroadcatsReciver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                    if (p2pHandler.getWifiP2pManager() == null) return;

                    NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

                    if (networkInfo.isConnected()) {
                        p2pHandler.getWifiP2pManager().requestConnectionInfo(p2pHandler.getWifiP2pChannel(), p2pHandler.getClient().connectionInfoListener);
                    }
                }
            }
        };
    }

}
