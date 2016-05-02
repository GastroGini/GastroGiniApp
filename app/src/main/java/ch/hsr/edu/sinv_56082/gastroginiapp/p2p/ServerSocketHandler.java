
package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ServerSocketHandler extends Thread {

    private final String macAddress;
    ServerSocket socket = null;
    private final int THREAD_COUNT = 30;
    private Handler handler;
    private static final String TAG = "ServerSocketHandler";

    public ServerSocketHandler(Handler handler, String macAddress) throws IOException {
        this.macAddress = macAddress;
        try {
            socket = new ServerSocket(P2pHandler.SERVICE_SERVER_PORT);
            this.handler = handler;
            Log.d("GroupOwnerSocketHandler", "Socket Started");
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdownNow();
            throw e;
        }
    }

    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            THREAD_COUNT-10, THREAD_COUNT, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    @Override
    public void run() {
        while (true) {
            try {
                // A blocking operation. Initiate a ChatManager instance when
                // there is a new connection
                pool.execute(new MessageHandler(socket.accept(), handler, new ConnectedDevice(macAddress)));
                Log.d(TAG, "Launching the I/O handler");

            } catch (IOException e) {
                try {
                    if (socket != null && !socket.isClosed())
                        socket.close();
                } catch (IOException ioe) {

                }
                e.printStackTrace();
                pool.shutdownNow();
                break;
            }
        }
    }

}
