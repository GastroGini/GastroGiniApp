package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.ServiceHome;

public class StartEventActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button hostButton = (Button) findViewById(R.id.hostButton);
        Button localHostButton = (Button) findViewById(R.id.localHostButton);
        final EditText userNameLocalInput = (EditText) findViewById(R.id.startEventUserNameLocalInput);
        final EditText userNameInput = (EditText) findViewById(R.id.startEventUserNameInput);
        final EditText eventPasswordInput = (EditText) findViewById(R.id.startEventPasswordInput);
        final Event event = (Event) getIntent().getExtras().get("event");
        final StartEventActivity sea = this;

        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameInput.getText().toString();
                String eventPassword = eventPasswordInput.getText().toString();
                Intent intent = new Intent(sea,ServiceHome.class);
                intent.putExtra("event", event);
                intent.putExtra("userName",userName);
                intent.putExtra("eventPassword",eventPassword);
                startActivity(intent);
            }
        });

        localHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameLocal = userNameLocalInput.getText().toString();
                String eventPassword = "";
                Intent intent = new Intent(sea, ServiceHome.class);
                intent.putExtra("event", event);
                intent.putExtra("userName",userNameLocal);
                intent.putExtra("eventPassword",eventPassword);
                startActivity(intent);
            }
        });
    }

}
