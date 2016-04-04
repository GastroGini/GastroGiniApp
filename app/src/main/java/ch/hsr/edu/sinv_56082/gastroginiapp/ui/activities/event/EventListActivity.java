package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.event;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.EventsAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.event.ItemClickListener;

public class EventListActivity extends AppCompatActivity implements ItemClickListener, Serializable{
    final private List<Event> myEventList = new ArrayList<>();
    final private List<Event> foreignEventList = new ArrayList<>();
    final private int MYEVENTLIST_IDENTIFIER = 1;
    final private int FOREIGNEVENTLIST_IDENTIFIER = 2;
    private EventsAdapter myEventsAdapter;
    private EventsAdapter foreignEventsAdapter;
    private TextView infoText;
    private RecyclerView myEventsRecyclerView;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        switch(requestCode){
            case MYEVENTLIST_IDENTIFIER:{
                if(resultCode == Activity.RESULT_OK){
                    Bundle args = data.getExtras();
                    String newTitle = args.getString("changedTitle");
                    int position = args.getInt("position");
                    if(position == myEventList.size()){
                        myEventList.add(new Event(newTitle));
                    }else{
                        myEventList.get(position).setTitle(newTitle);
                    }
                    myEventsAdapter.notifyDataSetChanged();
                }
            }
            break;
            case FOREIGNEVENTLIST_IDENTIFIER:{
                if(resultCode == Activity.RESULT_OK){
                    Bundle args = data.getExtras();
                    String newTitle = args.getString("changedTitle");
                    int position = args.getInt("position");
                    foreignEventList.get(position).setTitle(newTitle);
                    foreignEventsAdapter.notifyDataSetChanged();
                }
            }
        }
        checkIfEventListEmpty();
    }

    @Override
    public void onClick(String title, int position, int identifier) {
        try {
            Intent intent = new Intent(this, EventViewActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("pos",position);
            startActivityForResult(intent, identifier);
        }catch(ClassCastException ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final AppCompatActivity activity = this;

        /* Dummy data, replace with persistency */
        myEventList.add(new Event("My Fest 1"));
        myEventList.add(new Event("My Fest 2"));
        myEventList.add(new Event("My Fest 3"));
        myEventList.add(new Event("My Fest 4"));
        myEventList.add(new Event("My Fest 5"));

        foreignEventList.add(new Event("Foreign Fest 1"));
        foreignEventList.add(new Event("Foreign Fest 2"));
        foreignEventList.add(new Event("Foreign Fest 3"));
        foreignEventList.add(new Event("Foreign Fest 4"));
        foreignEventList.add(new Event("Foreign Fest 5"));

        /* Dummy data */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EventViewActivity.class);
                intent.putExtra("title","");
                intent.putExtra("pos",myEventList.size());
                startActivityForResult(intent,MYEVENTLIST_IDENTIFIER);
            }
        });

        myEventsRecyclerView = (RecyclerView)findViewById(R.id.eventListMyEventsRecyclerView);
        final RecyclerView foreignEventsRecyclerView = (RecyclerView) findViewById(R.id.eventListForeignEventsRecyclerView);

        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager foreignLinearLayoutManager = new LinearLayoutManager(this);

        myEventsAdapter = new EventsAdapter(this, myEventList,MYEVENTLIST_IDENTIFIER);
        foreignEventsAdapter = new EventsAdapter(this,foreignEventList,FOREIGNEVENTLIST_IDENTIFIER);

        myEventsRecyclerView.setLayoutManager(myLinearLayoutManager);
        foreignEventsRecyclerView.setLayoutManager(foreignLinearLayoutManager);

        myEventsRecyclerView.setAdapter(myEventsAdapter);
        foreignEventsRecyclerView.setAdapter(foreignEventsAdapter);

        myEventsRecyclerView.setHasFixedSize(true);
        foreignEventsRecyclerView.setHasFixedSize(true);

        ImageView myEventExpandCollapseIcon = (ImageView) findViewById(R.id.myEventsExpandCollapseIcon);
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
                if(myEventsRecyclerView.getVisibility() == View.VISIBLE){
                    myEventsRecyclerView.setVisibility(View.GONE);
                }else{
                    myEventsRecyclerView.setVisibility(View.VISIBLE);
                }
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
}
