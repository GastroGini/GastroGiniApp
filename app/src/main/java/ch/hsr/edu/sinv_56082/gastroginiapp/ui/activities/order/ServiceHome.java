package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableViewHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;

public class ServiceHome extends AppCompatActivity implements TestAdapter.Listener<EventTable> {


    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.eventTablesRecyclerView)RecyclerView eventTablesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);


        Bundle args = getIntent().getExtras();
        //TODO Extract to controller
        Event event = Event.get(UUID.fromString(args.getString("event-uuid")));
        String userName = args.get("userName").toString();
        String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Event: " + event.name);


        //TODO p2p handling should not be in activity (ApplicationController)
        ((App)getApplication()).p2p.setLocalService(event.name + " " + userName);


        //TODO: Remove password display, just for showcase
        getSupportActionBar().setSubtitle( "User: " + userName + " | Event Password: " + eventPassword);

        //EventTablesAdapter adapter = new EventTablesAdapter(this,event.eventTables());
        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new TestAdapter<EventTable, EventTableViewHolder>(R.layout.column_row_event_tables, event.eventTables(), this) {
            @Override
            public EventTableViewHolder createItemViewHolder(View view) {
                return new EventTableViewHolder(view);
            }

            @Override
            public void bindViewHolder(EventTableViewHolder holder, EventTable item) {
                holder.eventTableTitleText.setText(item.name);
            }
        });
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
    public void onItemClick(EventTable item) {
        Log.e("MyTagGoesHere", "Hacim buraya geldim");
        Intent intent = new Intent(this, TableOrderView.class);
        startActivity(intent);
    }

    @Override
    public void onDelete(EventTable item) {

    }
}
