package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;


public class TableOrderView extends TestActivity implements TableRowAdapter.TableItemClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tableOrderRecyclerView) RecyclerView tableOrderRecyclerView;
    @Bind(R.id.payButton) Button payButton;
    @Bind(R.id.deleteButton) Button deleteButton;

    EventTable eventTable;
    List<EventOrder> tableOrderList = new ArrayList<>();
    static List<OrderPosition> tableOrderPositions = new ArrayList<>();
    private TableOrderView activity;


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

        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();

        tableOrderList = eventTable.orders();
        for(EventOrder order : tableOrderList){
            tableOrderPositions.addAll(order.orderPositions());
        }

        final TableRowAdapter adapter = new TableRowAdapter(tableOrderPositions, this);
        tableOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tableOrderRecyclerView.setAdapter(adapter);
        tableOrderRecyclerView.setHasFixedSize(true);

        /*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, OrderPayView.class);

                intent.putStringArrayListExtra("tableOrderPositions", adapter.getSelectedUUIDs());
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: delete orderPosition and refresh list
            }
        });
    }


    //@Override
    public void onClick(OrderPosition orderPosition) {
        //Intent intent = new Intent(this, ProductDescriptionListActivity.class);
        //startActivity(intent);
    }
}
