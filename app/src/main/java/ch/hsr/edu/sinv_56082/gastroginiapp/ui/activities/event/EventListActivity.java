package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.DateHelpers;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestViewHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventViewHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventsAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventClickListener;

public class EventListActivity extends TestActivity implements Serializable, TestAdapter.Listener<Event> {

    private List<Event> myEventList = new ArrayList<>();
    private List<P2pHandler.ServiceResponseHolder> foreignEventList = new ArrayList<>();


    private static int MYEVENTLIST_IDENTIFIER = 1;
    private static int FOREIGNEVENTLIST_IDENTIFIER = 2;


    private boolean myEventsCollapsedState = true;


    //private EventsAdapter myEventsAdapter;
    //private EventsAdapter foreignEventsAdapter;


    @Bind(R.id.noAvailableEventsText) TextView noAvailableEventsText;
    @Bind(R.id.eventListMyEventsRecyclerView) RecyclerView myEventsRecyclerView;
    @Bind(R.id.eventListForeignEventsRecyclerView) ListView foreignEventsRecyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.myEventsExpandCollapseIcon) ImageView myEventExpandCollapseIcon;
    @Bind(R.id.foreignEventsExpandCollapseIcon) ImageView foreignEventExpandCollapseIcon;
    @Bind(R.id.myEventsEditModeIcon) ImageView myEventEditModeIcon;


    private AppCompatActivity activity;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MYEVENTLIST_IDENTIFIER) {
            myEventList.clear();
            myEventList.addAll(new Select().from(Event.class).<Event>execute());
            myEventsRecyclerView.getAdapter().notifyDataSetChanged();
            Log.d("hj", "onActivityResult: reloaded list");
        }
        checkIfEventListEmpty();
    }

    /*

    @Override
    public void onClick(Event event, int identifier) {
        try {
            Intent intent = new Intent(this, EventViewActivity.class);
            intent.putExtra("event-uuid", event.getUuid().toString());
            startActivityForResult(intent, identifier);
        }catch(ClassCastException ex){
            ex.printStackTrace();
        }
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        activity = this;

        myEventList.addAll(new Select().from(Event.class).<Event>execute());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EventViewActivity.class);
                startActivityForResult(intent, MYEVENTLIST_IDENTIFIER);
            }
        });


        //myEventsAdapter = new EventsAdapter(this, myEventList,MYEVENTLIST_IDENTIFIER, activity);
        //foreignEventsAdapter = new EventsAdapter(this,foreignEventList,FOREIGNEVENTLIST_IDENTIFIER);

        myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //foreignEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myEventsRecyclerView.setAdapter(new TestAdapter<Event, EventViewHolder>(R.layout.column_row_events, myEventList, this) {
            @Override
            public EventViewHolder createItemViewHolder(View view) {
                return new EventViewHolder(view);
            }

            @Override
            public void bindViewHolder(EventViewHolder holder, Event item) {
                holder.eventTitle.setText(item.name);
                holder.startDate.setText(DateHelpers.dateToString(item.startTime));
                holder.tableCount.setText(String.valueOf(item.eventTables().size()));
            }
        });


        foreignEventsRecyclerView.setAdapter(new ArrayAdapter<P2pHandler.ServiceResponseHolder>(this, android.R.layout.simple_list_item_1, foreignEventList));
        foreignEventsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO p2p not in activity
                ((App) getApplication()).p2p.connectTo(foreignEventList.get(position));
            }
        });

        myEventsRecyclerView.setHasFixedSize(true);
        //foreignEventsRecyclerView.setHasFixedSize(true);


        myEventEditModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestAdapter adapter = ((TestAdapter) myEventsRecyclerView.getAdapter());
                adapter.setEditMode(!adapter.isEditMode());
            }
        });

        myEventExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myEventsCollapsedState) {
                    myEventsRecyclerView.setVisibility(View.GONE);
                    noAvailableEventsText.setVisibility(View.GONE);
                } else {
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
            noAvailableEventsText.setVisibility(View.VISIBLE);
        }else{
            myEventsRecyclerView.setVisibility(View.VISIBLE);
            noAvailableEventsText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO p2p handling should not be in activity
        ((App)getApplication()).p2p.startBroadcastReciever();

        ((App)getApplication()).p2p.removeLocalServie();

        ((App)getApplication()).p2p.addServiceResponseCallback(new P2pHandler.ServiceResponseCallback() {
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

        ((App)getApplication()).p2p.discoverServices();


    }

    public static int getMyeventlistIdentifier(){
        return MYEVENTLIST_IDENTIFIER;
    }

    public static int getForeigneventlistIdentifier(){
        return FOREIGNEVENTLIST_IDENTIFIER;
    }

    @Override
    public void onItemClick(Event ownEvent) {
        try {
            Intent intent = new Intent(this, EventViewActivity.class);
            intent.putExtra("event-uuid", ownEvent.getUuid().toString());
            startActivityForResult(intent, MYEVENTLIST_IDENTIFIER);
        }catch(ClassCastException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onDelete(Event ownEvent) {
        ownEvent.delete();
        myEventList.remove(ownEvent);
        myEventsRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
