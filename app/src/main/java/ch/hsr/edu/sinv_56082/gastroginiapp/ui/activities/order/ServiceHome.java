package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;

public class ServiceHome extends AppCompatActivity implements EventTableClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        Event event = (Event) args.get("event");
        String userName = args.get("userName").toString();
        String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Event: " + event.getTitle());

        //TODO: Remove password display, just for showcase
        getSupportActionBar().setSubtitle( "User: " + userName + " | Event Password: " + eventPassword);

        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.eventTablesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        EventTablesAdapter adapter = new EventTablesAdapter(this,event.getTables());
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
    public void onClick(EventTable eventTable, int position) {

    }
}
