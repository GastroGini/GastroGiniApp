package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.ConnectionActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order.ServiceHome;

public class JoinEventActivity extends ConnectionActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    @Bind(R.id.join) Button join;
    @Bind(R.id.startEventUserNameInput) EditText userName;
    @Bind(R.id.startEventPasswordInput) EditText pw;
    private JoinEventActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.activity = this;

        setContentView(R.layout.activity_join_event);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserController().saveUser(userName.getText().toString());
                ConnectionController.getInstance().authenticate(pw.getText().toString());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionController.getInstance().setOnInitDataSuccess(new Consumer<String>() {
            @Override
            public void consume(String s) {
                Intent intent = new Intent(activity, ServiceHome.class);
                intent.putExtra("event-uuid", s);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectionController.getInstance().removeOnInitDataSuccess();
    }
}
