package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;

import android.net.wifi.p2p.WifiP2pDevice;


public class ServiceResponseHolder {
    public String name;
    public WifiP2pDevice device;

    public ServiceResponseHolder(WifiP2pDevice device, String name) {
        this.device = device;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
