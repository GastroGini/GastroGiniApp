package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.LocalData;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventsAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventClickListener;

public class EventListActivity extends AppCompatActivity implements EventClickListener, Serializable{

    private List<Event> myEventList = new ArrayList<>();
    private List<P2pHandler.ServiceResponseHolder> foreignEventList = new ArrayList<>();


    private static int MYEVENTLIST_IDENTIFIER = 1;
    private static int FOREIGNEVENTLIST_IDENTIFIER = 2;


    private boolean myEventsCollapsedState = true;
    private EventsAdapter myEventsAdapter;
    private EventsAdapter foreignEventsAdapter;
    private TextView infoText;
    private RecyclerView myEventsRecyclerView;
    private AppCompatActivity activity;
    private ListView foreignEventsRecyclerView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == MYEVENTLIST_IDENTIFIER) {
            myEventList.clear();
            myEventList.addAll(new Select().from(Event.class).<Event>execute());
            myEventsAdapter.notifyDataSetChanged();
        }
        checkIfEventListEmpty();
    }

    @Override
    public void onClick(Event event, int identifier) {
        try {
            Intent intent = new Intent(this, EventViewActivity.class);
            intent.putExtra("event-uuid", event.getUuid().toString());
            startActivityForResult(intent, identifier);
        }catch(ClassCastException ex){
            ex.printStackTrace();
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        myEventList.addAll(new Select().from(Event.class).<Event>execute());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEventsAdapter.changeEditMode();
                Intent intent = new Intent(activity, EventViewActivity.class);
                startActivityForResult(intent,MYEVENTLIST_IDENTIFIER);
            }
        });

        myEventsRecyclerView = (RecyclerView)findViewById(R.id.eventListMyEventsRecyclerView);
        foreignEventsRecyclerView = (ListView) findViewById(R.id.eventListForeignEventsRecyclerView);

        myEventsAdapter = new EventsAdapter(this, myEventList,MYEVENTLIST_IDENTIFIER, activity);
        //foreignEventsAdapter = new EventsAdapter(this,foreignEventList,FOREIGNEVENTLIST_IDENTIFIER);

        myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //foreignEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myEventsRecyclerView.setAdapter(myEventsAdapter);
        foreignEventsRecyclerView.setAdapter(new ArrayAdapter<P2pHandler.ServiceResponseHolder>(this, android.R.layout.simple_list_item_1, foreignEventList));
        foreignEventsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((LocalData)getApplication()).p2p.connectTo(foreignEventList.get(position));
            }
        });

        myEventsRecyclerView.setHasFixedSize(true);
        //foreignEventsRecyclerView.setHasFixedSize(true);

        final ImageView myEventExpandCollapseIcon = (ImageView) findViewById(R.id.myEventsExpandCollapseIcon);
        ImageView foreignEventExpandCollapseIcon = (ImageView) findViewById(R.id.foreignEventsExpandCollapseIcon);
        ImageView myEventEditModeIcon = (ImageView) findViewById(R.id.myEventsEditModeIcon);
        infoText = (TextView) findViewById(R.id.noAvailableEventsText);

        myEventEditModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myEventsAdapter.changeEditMode();
                myEventsAdapter.notifyDataSetChanged();
            }
        });

        myEventExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!myEventsCollapsedState){
                    myEventsRecyclerView.setVisibility(View.GONE);
                    infoText.setVisibility(View.GONE);
                }else{
                    myEventsRecyclerView.setVisibility(View.VISIBLE);
                    checkIfEventListEmpty();
                }
                myEventsCollapsedState = !myEventsCollapsedState;
            }
        });

        foreignEventExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foreignEventsRecyclerView.getVisibility() == View.VISIBLE) {
                    foreignEventsRecyclerView.setVisibility(View.GONE);
                } else {
                    foreignEventsRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void checkIfEventListEmpty(){
        if(myEventList.isEmpty()){
            myEventsRecyclerView.setVisibility(View.GONE);
            infoText.setVisibility(View.VISIBLE);
        }else{
            myEventsRecyclerView.setVisibility(View.VISIBLE);
            infoText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((LocalData)getApplication()).p2p.startBroadcastReciever(activity);

        ((LocalData)getApplication()).p2p.removeLocalServie();

        ((LocalData)getApplication()).p2p.addServiceResponseCallback(new P2pHandler.ServiceResponseCallback() {
            @Override
            public void onNewServiceResponse(P2pHandler.ServiceResponseHolder service) {
                for (P2pHandler.ServiceResponseHolder holder : foreignEventList) {
                    if (holder.device.deviceAddress.equals(service.device.deviceAddress)) {
                        foreignEventList.remove(holder);
                        break;
                    }
                }

                foreignEventList.add(service);
                ((BaseAdapter) foreignEventsRecyclerView.getAdapter()).notifyDataSetChanged();
            }
        });

        ((LocalData)getApplication()).p2p.discoverServices();


    }

    public static int getMyeventlistIdentifier(){
        return MYEVENTLIST_IDENTIFIER;
    }

    public static int getForeigneventlistIdentifier(){
        return FOREIGNEVENTLIST_IDENTIFIER;
    }
}
