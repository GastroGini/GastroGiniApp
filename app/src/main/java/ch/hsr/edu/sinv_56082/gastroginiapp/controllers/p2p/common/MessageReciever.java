package ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.common;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.messages.ConnectionMessage;

public class MessageReciever implements Runnable{

    private volatile boolean running = true;

    public static final String TAG = MessageReciever.class.toString();
    private final ConnectedDevice device;
    private Socket socket = null;
    private Handler handler;

    public MessageReciever(Socket socket, Handler handler, ConnectedDevice device) {
        this.device = device;
        this.socket = socket;
        this.handler = handler;
    }

    private PrintWriter oStream;

    @Override
    public void run() {
        try {
            BufferedReader iStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            oStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            device.handler = this;

            handler.obtainMessage(P2pHandler.SET_MESSAGE_HANDLER, device).sendToTarget();

            while (running) {
                String result = iStream.readLine();
                if (result != null) {
                    handler.obtainMessage(P2pHandler.RECIEVED_MESSAGE, new ConnectionMessage(device.device, result)).sendToTarget();
                }else{
                    handler.obtainMessage(P2pHandler.DISCONNECTED, new ConnectionMessage(device.device, "disconnect")).sendToTarget();
                    throw new IOException("Read invalid argument from stream... closing");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Exception during reading", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: ", e);
            }
        }
    }

    public void terminate(){
        this.running = false;
    }

    public void write(String message) {
        oStream.println(message);
        oStream.flush();
    }
}
