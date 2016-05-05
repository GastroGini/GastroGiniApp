package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
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
    private UserController userController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userController = new UserController();

        event = new ViewController<>(Event.class).get(getIntent().getExtras().getString("event-uuid"));
        startEventActivity = this;

        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = startEventUserNameInput.getText().toString();
                userController.saveUser(userName);
                String eventPassword = startEventPasswordInput.getText().toString();
                Intent intent = new Intent(startEventActivity, ServiceHome.class);
                intent.putExtra("event-uuid", event.getUuid().toString());
                intent.putExtra("userName", userName);
                intent.putExtra("eventPassword", eventPassword);

                ConnectionController.instance.startServer(event);

                startActivity(intent);
            }
        });

        localHostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userNameLocal = startEventUserNameLocalInput.getText().toString();
                userController.saveUser(userNameLocal);
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
