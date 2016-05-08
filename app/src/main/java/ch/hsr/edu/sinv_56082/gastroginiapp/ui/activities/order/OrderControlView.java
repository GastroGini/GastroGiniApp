package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.app.App;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.ProductAdapter;

public class OrderControlView extends AppCompatActivity implements ProductAdapter.ProductItemClickListener{

    public final static int ORDERCONTROLVIEW_ABORT = 1453;
    public final static int ORDERCONTROLVIEW_CONFIRM = 1071;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderControlRecyclerView) RecyclerView orderControlRecyclerView;
    @Bind(R.id.backButton) Button backButton;
    @Bind(R.id.finishButton) Button finishButton;

    ArrayList<String> newOrderPositionUUID = new ArrayList<>();
    EventTable eventTable = new EventTable();
    List<Product> productList = new ArrayList<>();
    ProductAdapter adapter;
    private ViewController<OrderPosition> orderPositionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_control_view);
        ButterKnife.bind(this);
        AppCompatActivity activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderPositionController = new ViewController<>(OrderPosition.class);

        Bundle args = getIntent().getExtras();
        newOrderPositionUUID=getIntent().getStringArrayListExtra("newOrderPositionsUUID");

        eventTable=getEventTableFromUUID(args);
        productList=loadProducts(newOrderPositionUUID);
        adapter=createAdapter(productList);
        startRecyclerView(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(ORDERCONTROLVIEW_ABORT);
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EventOrder eventOrder = new ViewController<>(EventOrder.class).create(new Supplier<EventOrder>() {
                    @Override
                    public EventOrder supply() {
                        return new EventOrder(eventTable, new Date(), new UserController().getUser());
                    }
                });

                for(final Product product : productList){
                    orderPositionController.create(new Supplier<OrderPosition>() {
                        @Override
                        public OrderPosition supply() {
                            return new OrderPosition(null, OrderState.STATE_OPEN, product, eventOrder);
                        }
                    });
                }

                Log.d("adding order", "onClick: "+new ViewController<>(EventOrder.class).get(eventOrder.getUuid()).orderPositions());

                App.getApp().p2p.client.sendNew(eventOrder); // TODO Controller

                setResult(ORDERCONTROLVIEW_CONFIRM);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed(){
        setResult(ORDERCONTROLVIEW_ABORT);
        finish();
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
        eventTable = new ViewController<>(EventTable.class).get(args.getString("eventTable-uuid"));
        return eventTable;
    }
    public List<Product> loadProducts (ArrayList<String> newOrderPositionUUID){
        for(String product : newOrderPositionUUID){
            productList.add(new ViewController<>(Product.class).get(product));
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

    @Override
    public void onClick(Product product) {
        //ignore click
    }
}
