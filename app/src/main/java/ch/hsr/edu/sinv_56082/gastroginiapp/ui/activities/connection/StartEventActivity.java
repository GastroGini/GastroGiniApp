package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.ServiceHome;

public class StartEventActivity extends CommonActivity {

    @Bind(R.id.startEventUserNameLocalInput) EditText startEventUserNameLocalInput;
    @Bind(R.id.startEventUserNameInput) EditText startEventUserNameInput;
    @Bind(R.id.startEventPasswordInput) EditText startEventPasswordInput;
    @Bind(R.id.hostButton) Button hostButton;
    @Bind(R.id.localHostButton) Button localHostButton;

    @Bind(R.id.toolbar) Toolbar toolbar;

    private Event event;
    private StartEventActivity startEventActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        event = Event.get(UUID.fromString(getIntent().getExtras().getString("event-uuid")));
        startEventActivity = this;

        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = startEventUserNameInput.getText().toString();
                String eventPassword = startEventPasswordInput.getText().toString();
                Intent intent = new Intent(startEventActivity, ServiceHome.class);
                intent.putExtra("event-uuid", event.getUuid().toString());
                intent.putExtra("userName", userName);
                intent.putExtra("eventPassword", eventPassword);
                startActivity(intent);
            }
        });

        localHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameLocal = startEventUserNameLocalInput.getText().toString();
                String eventPassword = "";
                Intent intent = new Intent(startEventActivity, ServiceHome.class);
                intent.putExtra("event-uuid", event.getUuid().toString());
                intent.putExtra("userName", userNameLocal);
                intent.putExtra("eventPassword", eventPassword);
                startActivity(intent);
            }
        });
    }

}
