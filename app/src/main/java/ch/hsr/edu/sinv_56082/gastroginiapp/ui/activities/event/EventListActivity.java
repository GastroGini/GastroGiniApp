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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.p2p.P2pHandler;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.DateHelpers;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventViewHolder;

public class EventListActivity extends CommonActivity implements Serializable, CommonAdapter.Listener<Event> {

    private List<Event> myEventList = new ArrayList<>();
    private List<P2pHandler.ServiceResponseHolder> foreignEventList = new ArrayList<>();


    private static int MYEVENTLIST_IDENTIFIER = 1;
    private static int FOREIGNEVENTLIST_IDENTIFIER = 2;


    private boolean myEventsCollapsedState = true;


    //private EventsAdapter myEventsAdapter;
    //private EventsAdapter foreignEventsAdapter;


    @Bind(R.id.noAvailableEventsText) TextView noAvailableEventsText;
    @Bind(R.id.eventListMyEventsRecyclerView) RecyclerView eventListMyEventsRecyclerView;
    @Bind(R.id.eventListForeignEventsRecyclerView) ListView eventListForeignEventsRecyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.myEventsExpandCollapseIcon) ImageView myEventsExpandCollapseIcon;
    @Bind(R.id.foreignEventsExpandCollapseIcon) ImageView foreignEventsExpandCollapseIcon;
    @Bind(R.id.myEventsEditModeIcon) ImageView myEventEditModeIcon;


    private AppCompatActivity activity;
    private ViewController<Event> eventController;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MYEVENTLIST_IDENTIFIER) {
            myEventList.clear();
            myEventList.addAll(eventController.getModelList());
            eventListMyEventsRecyclerView.getAdapter().notifyDataSetChanged();
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

        eventController = new ViewController<>(Event.class);

        myEventList.addAll(eventController.getModelList());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EventViewActivity.class);
                startActivityForResult(intent, MYEVENTLIST_IDENTIFIER);
            }
        });


        //myEventsAdapter = new EventsAdapter(this, myEventList,MYEVENTLIST_IDENTIFIER, activity);
        //foreignEventsAdapter = new EventsAdapter(this,foreignEventList,FOREIGNEVENTLIST_IDENTIFIER);

        eventListMyEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventListMyEventsRecyclerView.setAdapter(new CommonAdapter<Event, EventViewHolder>(R.layout.column_row_events, myEventList, this) {
            @Override
            public EventViewHolder createItemViewHolder(View view) {
                return new EventViewHolder(view);
            }

            @Override
            public void bindViewHolder(EventViewHolder holder, Event item) {
                holder.columnRowEventTitle.setText(item.name);
                holder.columnRowStartDate.setText(DateHelpers.dateToString(item.startTime));
                holder.columnRowAmountOfTables.setText(String.valueOf(item.eventTables().size()));
            }
        });


        eventListForeignEventsRecyclerView.setAdapter(new ArrayAdapter<P2pHandler.ServiceResponseHolder>(this, android.R.layout.simple_list_item_1, foreignEventList));
        eventListForeignEventsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO p2p not in activity
                ((App) getApplication()).p2p.connectTo(foreignEventList.get(position));
            }
        });
        eventListMyEventsRecyclerView.setHasFixedSize(true);
        //eventListForeignEventsRecyclerView.setHasFixedSize(true);


        myEventEditModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonAdapter adapter = ((CommonAdapter) eventListMyEventsRecyclerView.getAdapter());
                adapter.setEditMode(!adapter.isEditMode());
            }
        });

        myEventsExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myEventsCollapsedState) {
                    eventListMyEventsRecyclerView.setVisibility(View.GONE);
                    noAvailableEventsText.setVisibility(View.GONE);
                } else {
                    eventListMyEventsRecyclerView.setVisibility(View.VISIBLE);
                    checkIfEventListEmpty();
                }
                myEventsCollapsedState = !myEventsCollapsedState;
            }
        });

        foreignEventsExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListForeignEventsRecyclerView.getVisibility() == View.VISIBLE) {
                    eventListForeignEventsRecyclerView.setVisibility(View.GONE);
                } else {
                    eventListForeignEventsRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void checkIfEventListEmpty(){
        if(myEventList.isEmpty()){
            eventListMyEventsRecyclerView.setVisibility(View.GONE);
            noAvailableEventsText.setVisibility(View.VISIBLE);
        }else{
            eventListMyEventsRecyclerView.setVisibility(View.VISIBLE);
            noAvailableEventsText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO p2p handling should not be in activity

        ((App)getApplication()).p2p.removeLocalService();

       /* ((App)getApplication()).p2p.addServiceResponseCallback(new P2pHandler.ServiceResponseCallback() {
            @Override
            public void onNewServiceResponse(P2pHandler.ServiceResponseHolder service) {
                for (P2pHandler.ServiceResponseHolder holder : foreignEventList) {
                    if (holder.device.deviceAddress.equals(service.device.deviceAddress)) {
                        foreignEventList.remove(holder);
                        break;
                    }
                }

                foreignEventList.add(service);
                ((BaseAdapter) eventListForeignEventsRecyclerView.getAdapter()).notifyDataSetChanged();
            }
        });*/

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
        eventController.delete(ownEvent);
        myEventList.remove(ownEvent);
        eventListMyEventsRecyclerView.getAdapter().notifyDataSetChanged();
    }
}
