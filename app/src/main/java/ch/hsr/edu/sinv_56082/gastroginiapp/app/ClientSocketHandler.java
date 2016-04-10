
package ch.hsr.edu.sinv_56082.gastroginiapp.app;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientSocketHandler extends Thread {

    private static final String TAG = "ClientSocketHandler";
    private Handler handler;
    private MessageHandler messageHandler;
    private InetAddress mAddress;

    public ClientSocketHandler(Handler handler, InetAddress groupOwnerAddress) {
        this.handler = handler;
        this.mAddress = groupOwnerAddress;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(mAddress.getHostAddress(), P2pHandler.SERVICE_SERVER_PORT), 5000);
            Log.d(TAG, "Launching the I/O handler");
            messageHandler = new MessageHandler(socket, handler);
            new Thread(messageHandler).start();

            messageHandler.write("client Hello");
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

}
