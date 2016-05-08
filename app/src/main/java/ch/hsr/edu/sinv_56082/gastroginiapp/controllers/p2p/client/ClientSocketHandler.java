
package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageReciever;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;

public class ClientSocketHandler extends Thread {

    private static final String TAG = ClientSocketHandler.class.toString();
    private final ConnectedDevice clientDevice;
    private Handler handler;
    private MessageReciever messageReciever;
    private InetAddress mAddress;

    public ClientSocketHandler(Handler handler, InetAddress groupOwnerAddress, ConnectedDevice clientDevice) {
        this.handler = handler;
        this.mAddress = groupOwnerAddress;
        this.clientDevice = clientDevice;
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(mAddress.getHostAddress(), P2pHandler.SERVICE_SERVER_PORT), 5000);

            messageReciever = new MessageReciever(socket, handler, clientDevice);
            new Thread(messageReciever).start();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "run: ", e);
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                Log.e(TAG, "run: ", e1);

            }
        }
    }
}
