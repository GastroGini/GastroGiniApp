package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;

import android.content.Context;

import com.google.gson.Gson;

import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;

public class P2pClientMock extends P2pClient {
    String sent;

    public P2pClientMock(P2pHandler p2p, Context context, DoIt onConnectionState) {
        super(p2p, context, onConnectionState);
    }

    void sendMessage(DataMessage message){
        sent = new Gson().toJson(message);
    }
}
