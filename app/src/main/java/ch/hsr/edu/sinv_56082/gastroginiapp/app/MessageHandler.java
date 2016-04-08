package ch.hsr.edu.sinv_56082.gastroginiapp.app;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageHandler implements Runnable{


    private Socket socket = null;
    private Handler handler;

    public MessageHandler(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    private InputStream iStream;
    private OutputStream oStream;

    @Override
    public void run() {
        try {
            iStream = socket.getInputStream();
            oStream = socket.getOutputStream();

            handler.obtainMessage(P2pHandler.SET_MESSAGE_HANDLER, this).sendToTarget();

            while (true) {
                Scanner s = new Scanner(iStream).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";
                handler.obtainMessage(P2pHandler.RECIEVED_MESSAGE, result).sendToTarget();
            }
        } catch (IOException e) {
            Log.e("", "Exception during reading", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(String message) {
        try {
            oStream.write(message.getBytes());
        } catch (IOException e) {
            Log.e("", "Exception during write", e);
        }
    }
}
