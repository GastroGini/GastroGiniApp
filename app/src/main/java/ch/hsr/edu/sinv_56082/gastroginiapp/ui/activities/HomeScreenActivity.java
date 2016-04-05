package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.DummyData;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event.EventListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuMain;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        new DummyData();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AppCompatActivity activity = this;

        Button eventButton = (Button) findViewById(R.id.eventButton);
        Button menuButton = (Button) findViewById(R.id.menuButton);

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(activity,EventListActivity.class);
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

}
