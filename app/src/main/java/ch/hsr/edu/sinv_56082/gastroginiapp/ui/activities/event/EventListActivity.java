package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.DoIt;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.WarningMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.client.ServiceResponseHolder;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.p2p.iface.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.JoinEventActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.DateHelpers;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventViewHolder;

public class EventListActivity extends CommonActivity implements Serializable, CommonAdapter.Listener<Event> {
    private List<Event> myEventList = new ArrayList<>();
    private List<ServiceResponseHolder> foreignEventList;
    private static int MYEVENTLIST_IDENTIFIER = 1;
    private boolean myEventsCollapsedState = true;
    private AppCompatActivity activity;
    private ViewController<Event> eventController;

    @Bind(R.id.noAvailableEventsText) TextView noAvailableEventsText;
    @Bind(R.id.eventListMyEventsRecyclerView) RecyclerView eventListMyEventsRecyclerView;
    @Bind(R.id.eventListForeignEventsRecyclerView) ListView eventListForeignEventsRecyclerView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.myEventsExpandCollapseIcon) ImageView myEventsExpandCollapseIcon;
    @Bind(R.id.foreignEventsExpandCollapseIcon) ImageView foreignEventsExpandCollapseIcon;
    @Bind(R.id.myEventsEditModeIcon) ImageView myEventEditModeIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;
        eventController = new ViewController<>(Event.class);

        startMyEventRecyclerView();
        startForeignEventRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EventViewActivity.class);
                startActivityForResult(intent, MYEVENTLIST_IDENTIFIER);
            }
        });

        myEventEditModeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonAdapter adapter = ((CommonAdapter) eventListMyEventsRecyclerView.getAdapter());
                adapter.setEditMode(!adapter.isEditMode());
            }
        });
        myEventsExpandCollapseIcon.setAlpha(0.25f);
        myEventsExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myEventsCollapsedState) {
                    myEventsExpandCollapseIcon.setAlpha(0.25f);
                    eventListMyEventsRecyclerView.setVisibility(View.GONE);
                    noAvailableEventsText.setVisibility(View.GONE);
                } else {
                    eventListMyEventsRecyclerView.setVisibility(View.VISIBLE);
                    myEventsExpandCollapseIcon.setAlpha(1.00f);
                    checkIfEventListEmpty();
                }
                myEventsCollapsedState = !myEventsCollapsedState;
            }
        });

        foreignEventsExpandCollapseIcon.setAlpha(0.25f);
        foreignEventsExpandCollapseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventListForeignEventsRecyclerView.getVisibility() == View.VISIBLE) {
                    foreignEventsExpandCollapseIcon.setAlpha(0.25f);
                    eventListForeignEventsRecyclerView.setVisibility(View.GONE);
                } else {
                    eventListForeignEventsRecyclerView.setVisibility(View.VISIBLE);
                    foreignEventsExpandCollapseIcon.setAlpha(1.00f);
                }
            }
        });
    }

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

    private void startForeignEventRecyclerView() {
        foreignEventList = ConnectionController.getInstance().getServiceList();
        eventListForeignEventsRecyclerView.setAdapter(createForeignEventAdapter());
        eventListForeignEventsRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConnectionController.getInstance().connectTo(foreignEventList.get(position));
            }
        });
    }

    private void startMyEventRecyclerView() {
        myEventList.addAll(eventController.getModelList());
        eventListMyEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventListMyEventsRecyclerView.setAdapter(createMyEventAdapter());
        eventListMyEventsRecyclerView.setHasFixedSize(true);
    }

    @NonNull
    private ArrayAdapter<ServiceResponseHolder> createForeignEventAdapter() {
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foreignEventList);
    }

    @NonNull
    private CommonAdapter<Event, EventViewHolder> createMyEventAdapter() {
        return new CommonAdapter<Event, EventViewHolder>(R.layout.column_row_events, myEventList, this) {
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
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionController.getInstance().onConnectionEstablished(new DoIt() {
            @Override
            public void doIt() {
                Intent intent = new Intent(activity, JoinEventActivity.class);
                startActivity(intent);
            }
        });
        ConnectionController.getInstance().addServiceResponseCallback(new DoIt() {
            @Override
            public void doIt() {
                ((BaseAdapter) eventListForeignEventsRecyclerView.getAdapter()).notifyDataSetChanged();
            }
        });
        ConnectionController.getInstance().discoverServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ConnectionController.getInstance().removeConnectionEstablished();
        ConnectionController.getInstance().removeServiceResponseCallback();
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
    public void onDelete(final Event ownEvent) {
        new WarningMessage(activity, "Diesen Event wirklich löschen?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eventController.delete(ownEvent);
                myEventList.remove(ownEvent);
                eventListMyEventsRecyclerView.getAdapter().notifyDataSetChanged();
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
}
