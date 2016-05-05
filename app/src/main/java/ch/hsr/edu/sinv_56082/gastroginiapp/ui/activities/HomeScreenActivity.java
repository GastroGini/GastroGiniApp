package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.connection.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.DummyData;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuMain;

public class HomeScreenActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)Toolbar toolbar;
    private AppCompatActivity activity;
    @Bind(R.id.eventButton) Button eventButton;
    @Bind(R.id.menuButton) Button menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);

        /* To clean the database for a fresh restart */
        //deleteDatabase("Data.db");
        new DummyData();
        setSupportActionBar(toolbar);
        activity = this;

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EventListActivity.class);
                startActivity(intent);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MenuMain.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionController.instance.disconnect();
    }
}
