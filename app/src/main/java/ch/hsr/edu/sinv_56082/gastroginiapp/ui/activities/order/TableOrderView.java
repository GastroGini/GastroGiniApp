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
import android.widget.Button;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;

public class TableOrderView extends AppCompatActivity implements TableRowAdapter.TableItemClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tableOrderRecyclerView) RecyclerView tableOrderRecyclerView;
    @Bind(R.id.payButton) Button payButton;
    @Bind(R.id.deleteButton) Button deleteButton;

    EventTable eventTable;
    List<EventOrder> tableOrderList = new ArrayList<>();
    static List<OrderPosition> tableOrderPositions = new ArrayList<>();
    List<OrderPosition> selectedOrderPositionList = new ArrayList<>();
    ArrayList<String> OrderPositionsUUID = new ArrayList<>();
    private AppCompatActivity activity;
    TableRowAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table);
        ButterKnife.bind(this);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle args = getIntent().getExtras();
        setTitle("GastroGini - Table order view");

        eventTable=getEventTableFromUUID(args);
        loadOrderPositions(eventTable);
        updateRecyclerView();

        FloatingActionButton fab_add_order = (FloatingActionButton) findViewById(R.id.fab_add_order);
        fab_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("fab_add_order", "onClick: button pressed");
                Intent intent = new Intent(activity, NewOrderView.class);
                intent.putExtra("eventTable-uuid", eventTable.getUuid());
                startActivity(intent);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, OrderPayView.class);
                intent.putStringArrayListExtra("tableOrderPositions", adapter.getSelectedUUIDs());
                intent.putExtra("OrderPositionsUUID", OrderPositionsUUID);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("delete order positions", "onClick: button pressed");
                for(OrderPosition op : selectedOrderPositionList){
                    if(tableOrderPositions.contains(op)){
                        deleteOrderPosition(op);
                    }else{
                        Log.d("delete order position", "onClick: element to delete not in orderPositionList");
                    }
                }
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        updateRecyclerView();
    }

    public void deleteOrderPosition (OrderPosition op){
        tableOrderPositions.remove(op);
        selectedOrderPositionList.remove(op);
        updateRecyclerView();
    }
    public void updateRecyclerView(){
        tableOrderPositions=loadOrderPositions(eventTable);
        OrderPositionsUUID=loadOrderPositionsUUID(tableOrderPositions);
        adapter=createAdapter(tableOrderPositions);
        startRecyclerView(adapter);
    }
    //@Override
    public void onClick(OrderPosition orderPosition) {
        //TODO: select object and add UUID to OrderPositionUUID
    }
    public List<OrderPosition> loadOrderPositions (EventTable eventTable){
        tableOrderList = eventTable.orders();
        tableOrderPositions.clear();

        for(EventOrder order : tableOrderList){
            for (OrderPosition pos: order.orderPositions()){
                if(pos.orderState== OrderState.STATE_OPEN){
                    tableOrderPositions.add(pos);
                }
            }
        }
        return tableOrderPositions;
    }
    public ArrayList<String> loadOrderPositionsUUID(List<OrderPosition> tableOrderPositions){
        OrderPositionsUUID.clear();
        for(OrderPosition op : tableOrderPositions){
            OrderPositionsUUID.add(op.getUuid().toString());
        }
        return OrderPositionsUUID;
    }
    public EventTable getEventTableFromUUID (Bundle args){
        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        return eventTable;
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
    public void startRecyclerView(TableRowAdapter adapter){
        tableOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tableOrderRecyclerView.setAdapter(adapter);
        tableOrderRecyclerView.setHasFixedSize(true);
    }
    public TableRowAdapter createAdapter(List<OrderPosition> tableOrderPositions){
        adapter = new TableRowAdapter(tableOrderPositions, this);
        return adapter;
    }
}
