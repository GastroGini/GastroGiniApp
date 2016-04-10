package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

/**
 * Created by Dogan on 08.04.16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Event;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.ProductDescriptionListActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTableClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableItemClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;


public class TableOrderView extends AppCompatActivity implements TableItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_service_home);
        setContentView(R.layout.activity_order_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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



        RecyclerView tableOrderRecycleView = (RecyclerView)findViewById(R.id.tableOrderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        TableRowAdapter adapter = new TableRowAdapter(this,temp);
        tableOrderRecycleView.setLayoutManager(linearLayoutManager);
        tableOrderRecycleView.setAdapter(adapter);
        tableOrderRecycleView.setHasFixedSize(true);




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