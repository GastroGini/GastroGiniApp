package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableViewHolder;

public class ServiceHome extends AppCompatActivity implements CommonAdapter.Listener<EventTable> {


    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.eventTablesRecyclerView)RecyclerView eventTablesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        Event event = new ViewController<>(Event.class).get(args.getString("event-uuid"));
        //String userName = args.get("userName").toString();

        String userName = new UserController().getUser().firstName;

        String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Event: " + event.name);

        //ModelHolder<Event> eventJson = new ModelHolder<>(Event.class);
        //eventJson.setModel(event);


        //TODO: Remove password display, just for showcase
        getSupportActionBar().setSubtitle("User: " + userName + " | Event Password: " + eventPassword);

        //EventTablesAdapter adapter = new EventTablesAdapter(this,event.eventTables());
        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new CommonAdapter<EventTable, EventTableViewHolder>(R.layout.column_row_event_tables, event.eventTables(), this) {
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
    }

    @Override
    public void onItemClick(EventTable item) {
        Log.e("ServiceHome:", "Table selected");
        Intent intent = new Intent(this, TableOrderView.class);
        intent.putExtra("eventTable-uuid", item.getUuid().toString());
        startActivity(intent);
    }

    @Override
    public void onDelete(EventTable item) {

    }
}
