package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonSelectable;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;

public class TableOrderView extends AppCompatActivity implements TableRowAdapter.TableItemClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tableOrderRecyclerView) RecyclerView tableOrderRecyclerView;
    @Bind(R.id.payButton) Button payButton;
    @Bind(R.id.deleteButton) Button deleteButton;

    EventTable eventTable;
    List<CommonSelectable<OrderPosition>> tableOrderPositions = new ArrayList<>();

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
        setTitle("GastroGini - Tabelle Auftragssicht ");

        loadEventTableFromUUID(args);
        loadOrderPositions();

        adapter = new TableRowAdapter(tableOrderPositions, this);
        tableOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tableOrderRecyclerView.setAdapter(adapter);
        tableOrderRecyclerView.setHasFixedSize(true);
        Log.d("", "tableOrderPosition: " + tableOrderPositions.toString());

        FloatingActionButton fab_add_order = (FloatingActionButton) findViewById(R.id.fab_add_order);
        fab_add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TableOrderView:", "NewOrder");
                Intent intent = new Intent(activity, NewOrderView.class);
                intent.putExtra("eventTable-uuid", eventTable.getUuid().toString());
                startActivityForResult(intent, 1);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TableOrderView", "Paybutton");
                Intent intent = new Intent(activity, OrderPayView.class);
                intent.putStringArrayListExtra("tableOrderPositions", adapter.getSelectedUUIDs());
                intent.putExtra("eventTable-uuid", eventTable.getUuid().toString());
                startActivityForResult(intent, 1);
                updateRecyclerView();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Warnung")
                        .setMessage("Wollen sie diese Position(en) wirklich löschen?")
                        .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                List<OrderPosition> orderPositionsToDelete = adapter.getSelectedOrderPositions();
                                ConnectionController.getInstance().sendDelete(orderPositionsToDelete); // TODO Controller
                                for (OrderPosition op : orderPositionsToDelete) {
                                    Log.d("delete orderPosition", "onClick: deleting order pos");
                                    deleteOrderPosition(op);
                                }
                            }
                        })
                        .setNegativeButton("Abbrechen", null)
                        .show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        updateRecyclerView();
        Log.d("TableOrderView", "onActivityResult: returned");
    }

    public void deleteOrderPosition (OrderPosition op){
        new ViewController<>(OrderPosition.class).delete(op);
        updateRecyclerView();
    }
    public void updateRecyclerView(){
        Log.d("TableOrderView", "updateRecyclerView: update view");
        loadOrderPositions();
        adapter.notifyDataSetChanged();
        Log.d("LIST", "updateRecyclerView: "+tableOrderPositions);
    }
    public void onClick(OrderPosition orderPosition) {
        //implemented in TableRowAdapter
    }
    public void loadOrderPositions() {
        Log.d("TableOrderView", "loadOrderPositions");
        tableOrderPositions.clear();
        for(EventOrder order : eventTable.orders()){
            for (OrderPosition pos: order.orderPositions()){
                if(pos.orderState==OrderState.STATE_OPEN){
                    tableOrderPositions.add(new CommonSelectable<>(pos));
                }
            }
        }
    }
    public void loadEventTableFromUUID(Bundle args){
        eventTable = new ViewController<>(EventTable.class).get(args.getString("eventTable-uuid"));
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
}
