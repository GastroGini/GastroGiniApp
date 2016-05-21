package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.DataMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageAction;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageObject;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.authenticate.AuthenticateResult;

public class ClientMessageHandler extends MessageHandler {

    private final P2pClient client;

    public ClientMessageHandler(P2pClient client){
        this.client = client;
    }

    @Override
    public void registerMessages() {
        messageHandlers.put(MessageAction.INITIAL_DATA, new InitialDataReader(client));

        messageHandlers.put(MessageAction.AUTHENTICATE_RESULT, new MessageObject<AuthenticateResult>(AuthenticateResult.class){
            @Override
            public void handleMessage(AuthenticateResult object, String fromAddress) {
                if (object.statuscode == 1){
                    client.sendMessage(new DataMessage(MessageAction.GET_INITIAL_DATA, null));
                } else {
                    client.disconnect();
                }
            }
        });

        messageHandlers.put(MessageAction.STOP_SERVER, new MessageObject(Object.class) {
            @Override
            public void handleMessage(Object object, String fromAddress) {
                client.disconnect();
            }
        });
    }
}
