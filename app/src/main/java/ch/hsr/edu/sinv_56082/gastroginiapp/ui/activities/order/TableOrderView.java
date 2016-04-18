package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

/**
 * Created by Dogan on 08.04.16.
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;


public class TableOrderView extends TestActivity implements TableRowAdapter.TableItemClickListener {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.tableOrderRecyclerView) RecyclerView tableOrderRecyclerView;

    EventTable eventTable;
    List<EventOrder> tableOrderList = new ArrayList<>();
    List<OrderPosition> tableOrderPositions = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_table);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //setContentView(R.layout.activity_service_home);


        Bundle args = getIntent().getExtras();
        //Event event = Event.get(UUID.fromString(args.getString("event-uuid")));
        //String userName = args.get("userName").toString();
        //String eventPassword = args.get("eventPassword").toString();

        setTitle("GastroGini - Table order view");

        //TODO: Remove password display, just for showcase
        getSupportActionBar().setSubtitle("");

        List temp = new ArrayList<Product>();
        temp.add(new Product(new ProductDescription("cola",null, null),null,4,"5dl"));
        temp.add(new Product(new ProductDescription("fanta",null, null),null,3,"5dl"));
        temp.add(new Product(new ProductDescription("redbull",null, null),null,5,"5dl"));
        temp.add(new Product(new ProductDescription("tea",null, null),null,3,"5dl"));

        eventTable = EventTable.get(UUID.fromString(args.getString("eventTable-uuid")));
        tableOrderList = eventTable.orders();
        for(EventOrder order : tableOrderList){
            tableOrderPositions.addAll(order.orderPositions());
        }

        TableRowAdapter adapter = new TableRowAdapter(this,temp);
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
    }

    @Override
    public void onClick(EventTable eventTable) {
        //Intent intent = new Intent(this, ProductDescriptionListActivity.class);
        //startActivity(intent);
    }
}
