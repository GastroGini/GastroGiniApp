package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.serialization.Serializer;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.messages.MessageAction;

public abstract class MessageHandler {

    protected Map<MessageAction, MessageObject<?>> messageHandlers;

    public MessageHandler(){
        messageHandlers = new HashMap<>();
    }

    public void handleMessages(ConnectionMessage message) {
        DataMessage dataMessage = new Gson().fromJson(message.content, DataMessage.class);
        MessageObject messager = messageHandlers.get(dataMessage.action);
        if (messager != null) messager.handleMessage(Serializer.get().fromJson(dataMessage.content, messager.type), message.fromAddress);
    }

    public abstract void registerMessages();


}
