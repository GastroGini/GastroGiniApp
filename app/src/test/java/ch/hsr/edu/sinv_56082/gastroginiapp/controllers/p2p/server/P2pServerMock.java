package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server;

import com.google.gson.Gson;

import java.io.IOException;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;

public class P2pServerMock extends P2pServer {

    String sent = "";

    public P2pServerMock(Event event, String pw, P2pHandler p2p) throws IOException {
        super(event, pw, p2p);
    }

    public void sendMessage(String to, DataMessage message){
        sent = new Gson().toJson(message);
    }
}
