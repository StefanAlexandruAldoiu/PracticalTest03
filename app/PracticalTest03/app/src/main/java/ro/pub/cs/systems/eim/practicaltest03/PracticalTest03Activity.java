package ro.pub.cs.systems.eim.practicaltest03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest03Activity extends AppCompatActivity {


    // Server widgets
    private EditText serverPortEditText = null;
    private Button setButton = null;
    private Button resetButton = null;
    private Button pollButton = null;
    public EditText addAlarm = null;
    private Button startButton = null;
    private Button stopButton = null;

    private TextView ok = null;

    private Server server = null;
    private Client client = null;

    private StartButtonClickListener startButtonClickListener = new StartButtonClickListener();
    private class StartButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            String serverPort = serverPortEditText.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(getApplicationContext(), "[MAIN ACTIVITY] Server port should be filled!", Toast.LENGTH_SHORT).show();
                return;
            }
            server = new Server(Integer.parseInt(serverPort), addAlarm);
            if (server.serverSocket == null) {
                Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not create server thread!");
                return;
            }
            server.start();
        }

    }
    private SetButtonClickListener setButtonClickListener = new SetButtonClickListener();
    private class SetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            client = new Client("set", ok);
            client.start();
        }
    }
    private ResetButtonClickListener resetButtonClickListener = new ResetButtonClickListener();
    private class ResetButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            client = new Client("reset", ok);
            client.start();
        }
    }
    private PollButtonClickListener pollButtonClickListener = new PollButtonClickListener();
    private class PollButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {

            client = new Client("poll", ok);
            client.start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test03_main);
        serverPortEditText = (EditText)findViewById(R.id.server_port_edit_text);
        addAlarm = (EditText)findViewById(R.id.hour_edit_text);

        setButton = (Button)findViewById(R.id.set_button);
        resetButton = (Button)findViewById(R.id.reset_button);
        pollButton = (Button)findViewById(R.id.poll_button);
        startButton = (Button)findViewById(R.id.start_button);
        stopButton = (Button)findViewById(R.id.stop_button);

        startButton.setOnClickListener(startButtonClickListener);
        setButton.setOnClickListener(setButtonClickListener);
        resetButton.setOnClickListener(resetButtonClickListener);

    }

}
