package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.OrderPositionAdapter;

public class TableOrderView extends AppCompatActivity implements OrderPositionAdapter.OnClickListener{

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

    private List<OrderPosition> myOrderPositionList = new ArrayList<>();
    private RecyclerView myEventOrdersRecyclerView;
    private EventTable eventTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle args = getIntent().getExtras();
        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        Event event = eventTable.event;
        setTitle("GastroGini - Event: " + event.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AppCompatActivity activity = this;

        loadOrderPositions();

        FloatingActionButton fab_add_order = (FloatingActionButton) findViewById(R.id.fab_add_order);
        fab_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dinMueter", "onClick: "+"hallo");
                try {
                    Intent intent = new Intent(activity, NewOrderView.class);
                    intent.putExtra("eventTable-uuid", eventTable.uuid.toString());
                    startActivity(intent);
                }catch(ClassCastException ex){
                    ex.printStackTrace();
                }
            }
        });
/*
        FloatingActionButton fab_select_all = (FloatingActionButton) findViewById(R.id.fab_select_all);
        fab_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myEventOrdersRecyclerView = (RecyclerView)findViewById(R.id.eventOrderRecyclerView);
        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(this);
        myEventOrdersRecyclerView.setLayoutManager(myLinearLayoutManager);
        myEventOrdersRecyclerView.setAdapter(new OrderPositionAdapter(this, myOrderPositionList));
        myEventOrdersRecyclerView.setHasFixedSize(true);

        loadOrderPositions();
        myEventOrdersRecyclerView.getAdapter().notifyDataSetChanged();

    }

    private void loadOrderPositions() {
        myOrderPositionList.clear();
        for (EventOrder order:eventTable.orders()){
            myOrderPositionList.addAll(order.orderPositions());
        }
    }


    @Override
    public void onClick(OrderPosition myOrderPositionList) {
        //TODO OnClick
        Log.d("checkMate", "onClick: Hallo");
    }
}
