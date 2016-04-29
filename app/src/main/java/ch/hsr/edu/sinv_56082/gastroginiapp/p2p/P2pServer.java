package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;

public class P2pServer {

    private static final String TAG = "SERVER::";
    private final App app;
    private TransferEvent dto;
    private Event runningEvent;
    private Handler handler;
    private MessageHandler messageHandler;


    public P2pServer(Event event){
        dto = new TransferEvent(event.getUuid(), event.name, event.startTime);
        app = App.getApp();
        runningEvent = event;
        app.p2p.initGroup();
        app.p2p.removeLocalService();
        registerMessageHandler();
        try {
            new ServerSocketHandler(handler).start();
        } catch (IOException e) {
            Log.e(TAG, "P2pServer: failed to create server socket", e);
        }
        app.p2p.setLocalService(dto);

    }





    private void registerMessageHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Log.d(TAG, "handleMessage: recieved"+msg+P2pHandler.SET_MESSAGE_HANDLER+P2pHandler.RECIEVED_MESSAGE);
                if(P2pHandler.SET_MESSAGE_HANDLER == msg.what){
                    messageHandler = (MessageHandler) msg.obj;
                    messageHandler.write("Server hello");
                    return true;
                }else if (P2pHandler.RECIEVED_MESSAGE == msg.what){

                    Log.d(TAG, "handleMessage: " + ((String) msg.obj));
                    return true;
                }
                return false;
            }
        });
    }

}
