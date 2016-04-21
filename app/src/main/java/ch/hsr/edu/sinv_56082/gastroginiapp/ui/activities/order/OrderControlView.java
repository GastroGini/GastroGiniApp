package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.Date;
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
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.ProductAdapter;

public class OrderControlView extends AppCompatActivity implements ProductAdapter.ProductItemClickListener{

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderControlRecyclerView) RecyclerView orderControlRecyclerView;
    @Bind(R.id.backButton) Button backButton;
    @Bind(R.id.finishButton) Button finishButton;

    private AppCompatActivity activity;
    ArrayList<String> newOrderPositionUUID = new ArrayList<>();
    EventTable eventTable = new EventTable();
    List<Product> productList = new ArrayList<>();
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_control_view);
        ButterKnife.bind(this);
        activity = this;
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        newOrderPositionUUID=getIntent().getStringArrayListExtra("newOrderPositionsUUID");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTable=getEventTableFromUUID(args);
        productList=loadProducts(newOrderPositionUUID);
        adapter=createAdapter(productList);
        startRecyclerView(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventOrder eventOrder=new EventOrder(eventTable, new Date(System.currentTimeMillis()));
                for(Product product : productList){
                    new OrderPosition(null, OrderState.STATE_OPEN, product, eventOrder);
                }
                eventTable.orders().add(eventOrder);
                //TODO: persistenz???
                /*
                Intent intent = new Intent(activity, OrderControlView.class);
                intent.putExtra("newOrderPositionsUUID", newOrderPositionUUID);
                startActivity(intent);
                */
            }
        });
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
    public EventTable getEventTableFromUUID (Bundle args){
        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        return eventTable;
    }
    public List<Product> loadProducts (ArrayList<String> newOrderPositionUUID){
        for(String product : newOrderPositionUUID){
            productList.add(Product.get(product));
        }
        return productList;
    }
    public void startRecyclerView(ProductAdapter adapter){
        orderControlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderControlRecyclerView.setAdapter(adapter);
        orderControlRecyclerView.setHasFixedSize(true);
    }
    public ProductAdapter createAdapter(List<Product> productList){
        adapter = new ProductAdapter(productList, this);
        return adapter;
    }

   // @Override
    public void onClick(Product product) {

    }

}
