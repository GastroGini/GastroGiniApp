package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.LocalData;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.ProductDescriptionListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;

public class ServiceHome extends AppCompatActivity implements EventTablesAdapter.EventTableClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_service_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        Event event = Event.get(UUID.fromString(args.getString("event-uuid")));
        String userName = args.get("userName").toString();
        String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Event: " + event.name);


        ((LocalData)getApplication()).p2p.setLocalService(event.name + " " + userName);


        //TODO: Remove password display, just for showcase
        getSupportActionBar().setSubtitle( "User: " + userName + " | Event Password: " + eventPassword);

        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.eventTablesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        EventTablesAdapter adapter = new EventTablesAdapter(this,event.eventTables());
        eventTablesRecyclerView.setLayoutManager(linearLayoutManager);
        eventTablesRecyclerView.setAdapter(adapter);
        eventTablesRecyclerView.setHasFixedSize(true);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public void onClick(EventTable eventTable) {

    }
}
