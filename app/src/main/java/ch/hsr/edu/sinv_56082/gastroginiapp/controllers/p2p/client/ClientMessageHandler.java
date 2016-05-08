package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.MessageAction;

public class ClientMessageHandler extends MessageHandler {

    private final P2pClient client;

    public ClientMessageHandler(P2pClient client){
        this.client = client;
    }

    @Override
    public void registerMessages() {
        messageHandlers.put(MessageAction.INITIAL_DATA, new InitialDataReader(client));

        messageHandlers.put(MessageAction.STOP_SERVER, new MessageObject(Object.class) {
            @Override
            public void handleMessage(Object object, String fromAddress) {
                client.disconnect();
            }
        });
    }
}
