package ro.pub.cs.systems.eim.practicaltest03;

import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;

import android.util.Log;

        import java.io.IOException;
        import java.net.ServerSocket;
        import java.net.Socket;
        import java.util.HashMap;

/**
 * Created by stefa on 5/20/2017.
 */

public class Server extends Thread {
    int port = 0;

    ServerSocket serverSocket = null;
    HashMap<String, String> data = new HashMap<String , String>();
    private EditText addAlarm = null;

    public Server(int port, EditText addAlarm ) {
        this.port = port;
        this.addAlarm = addAlarm;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
        this.data = new HashMap<>();
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i(Constants.TAG, "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i(Constants.TAG, "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());

                BufferedReader bufferedReader = Utilities.getReader(socket);
                PrintWriter printWriter = Utilities.getWriter(socket);

                String command = bufferedReader.readLine();
                String hour_and_minute = addAlarm.getText().toString();

                if (command.equals("set")) {
                    if (this.data.containsKey(hour_and_minute) == false){
                        this.data.put(hour_and_minute, " ");
                    }
                }else if (command.equals("reset") ){
                    this.data.remove(hour_and_minute);
                }



            }
        }
        catch (IOException ioException) {
            Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }


    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e(Constants.TAG, "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}