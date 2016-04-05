package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;

public class ServiceHome extends AppCompatActivity implements EventTableClickListener {
    private static int MYEVENTTABLE_IDENTIFIER=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        Event event = new Select().from(Event.class).where("uuid = ?",UUID.fromString(args.getString("event-uuid"))).executeSingle();
        String userName = args.get("userName").toString();
        String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Event: " + event.name);

        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.eventTablesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        EventTablesAdapter adapter = new EventTablesAdapter(this,event.eventTables(), MYEVENTTABLE_IDENTIFIER);
        eventTablesRecyclerView.setLayoutManager(linearLayoutManager);
        eventTablesRecyclerView.setAdapter(adapter);
        eventTablesRecyclerView.setHasFixedSize(true);
    }

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
    public void onClick(EventTable eventTable, int identifier) {
        try {
            Intent intent = new Intent(this, TableOrderView.class);
            intent.putExtra("eventTable-uuid", eventTable.uuid.toString());
            startActivityForResult(intent, identifier);
        }catch(ClassCastException ex){
            ex.printStackTrace();
        }
    }
}
