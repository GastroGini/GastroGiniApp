package ch.hsr.edu.sinv_56082.gastroginiapp.p2p;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageHandler implements Runnable{


    public static final String TAG = "MESSAFE HANDLER";
    private Socket socket = null;
    private Handler handler;

    public MessageHandler(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    private BufferedReader iStream;
    private PrintWriter oStream;

    @Override
    public void run() {
        Log.d("MESSAGE::", "run: starting");
        try {
            iStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            oStream = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

            handler.obtainMessage(P2pHandler.SET_MESSAGE_HANDLER, this).sendToTarget();

            while (true) {
                //Scanner s = new Scanner(iStream).useDelimiter("\\A");
                //String result = s.hasNext() ? s.next() : "";
                String result = iStream.readLine();
                Log.d(TAG, "run: read: "+result);
                if (result != null) {
                    handler.obtainMessage(P2pHandler.RECIEVED_MESSAGE, result).sendToTarget();
                }else{
                    handler.obtainMessage(P2pHandler.DISCONNECTED).sendToTarget();
                    throw new IOException("Read invalid argument from stream... closing");
                }
            }
        } catch (IOException e) {
            Log.e("", "Exception during reading", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "run: ", e);
            }
        }
    }

    public void write(String message) {
        Log.d("MESSGA HAND", "write: " + message);
        oStream.println(message);
        oStream.flush();
    }
}
