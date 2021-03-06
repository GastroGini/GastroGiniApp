
package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.server;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.ConnectedDevice;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.MessageReciever;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common.P2pHandler;


public class ServerSocketHandler extends Thread {

    private final String macAddress;
    volatile ServerSocket socket = null;
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
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // A blocking operation. Initiate a MessageReciever instance when
                // there is a new connection
                pool.execute(new MessageReciever(socket.accept(), handler, new ConnectedDevice(macAddress)));
                Log.d(TAG, "Launching the I/O handler");

            } catch (IOException e) {
                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        Log.d(TAG, "closeSocket: closing");
                    }
                } catch (IOException ioe) {
                    Log.d(TAG, "closeSocket: allready closed or dead");
                }
                e.printStackTrace();
                pool.shutdownNow();
                break;
            }
        }
        Log.d(TAG, "run: shutting down");

    }

    public void terminate(){
        Log.d(TAG, "terminate: Stopping Server handler");
        pool.shutdownNow();
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                Log.d(TAG, "closeSocket: closing");
            }
        } catch (IOException ioe) {
            Log.d(TAG, "closeSocket: allready closed or dead");
        }
        Thread.currentThread().interrupt();
    }

}
