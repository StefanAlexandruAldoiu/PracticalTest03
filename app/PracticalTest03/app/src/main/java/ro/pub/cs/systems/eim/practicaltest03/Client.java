package ro.pub.cs.systems.eim.practicaltest03;


import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by stefa on 5/20/2017.
 */

public class Client extends  Thread{
    private String command;
    private TextView okTextView;
    private Socket socket;

    public Client(String command, TextView ok) {
        this.command = command;
        this.okTextView = ok;
    }


    @Override
    public void run() {
        try {
            socket = new Socket("localhost", 7000);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            if (command.equals("set") || command.equals("reset")){
                printWriter.println(this.command);
                printWriter.flush();
            }
            else{
                printWriter.println(this.command);
                printWriter.flush();
                String weatherInformation;
                while ((weatherInformation = bufferedReader.readLine()) != null) {
                    final String finalizedWeateherInformation = weatherInformation;
                    okTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            okTextView.setText(finalizedWeateherInformation);
                        }
                    });
                }

            }


        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}