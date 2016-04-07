package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class TableOrderView extends AppCompatActivity {
    private List<EventOrder> myEventOrderList = new ArrayList<>();
    private List<OrderPosition> myOrderPositionList = new ArrayList<>();
    private RecyclerView myEventOrdersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_order_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle args = getIntent().getExtras();
        EventTable eventTable = new Select().from(EventTable.class).where("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        Event event = eventTable.event;
        setTitle("GastroGini - Event: " + event.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final AppCompatActivity activity = this;

        myEventOrderList = eventTable.orders();
        for (EventOrder order:myEventOrderList){
            myOrderPositionList.addAll(order.orderPositions());
        }

        FloatingActionButton fab_add_order = (FloatingActionButton) findViewById(R.id.fab_add_order);
        fab_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab_select_all = (FloatingActionButton) findViewById(R.id.fab_select_all);
        fab_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myEventOrdersRecyclerView = (RecyclerView)findViewById(R.id.eventOrderRecyclerView);
        LinearLayoutManager myLinearLayoutManager = new LinearLayoutManager(this);
        my
    }

}
