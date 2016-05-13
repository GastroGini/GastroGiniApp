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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Supplier;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.UserController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventOrder;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.OrderControlAdapter;

public class OrderControlView extends AppCompatActivity implements OrderControlAdapter.ProductItemClickListener{

    public final static int ORDERCONTROLVIEW_ABORT = 1453;
    public final static int ORDERCONTROLVIEW_CONFIRM = 1071;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderControlRecyclerView) RecyclerView orderControlRecyclerView;
    @Bind(R.id.backButton) Button backButton;
    @Bind(R.id.finishButton) Button finishButton;
    @Bind(R.id.order_control_subtotal) TextView subTotal;

    ArrayList<String> newOrderPositionUUID = new ArrayList<>();
    EventTable eventTable = new EventTable();
    List<Product> productList = new ArrayList<>();
    OrderControlAdapter adapter;
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
        updateSubTotalField();
        adapter = createAdapter(productList);
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

                ConnectionController.getInstance().sendNew(eventOrder); // TODO Controller

                setResult(ORDERCONTROLVIEW_CONFIRM);
                finish();

            }
        });
    }

    private String calculateSubTotal(List<Product> productList) {
        double sum = 0;
        for(Product item : productList){
            sum += item.price;
        }
        return sum + "";
    }

    public void updateSubTotalField(){
        subTotal.setText("Zwischensumme: " + calculateSubTotal(productList));
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
    public void startRecyclerView(OrderControlAdapter adapter){
        orderControlRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderControlRecyclerView.setAdapter(adapter);
        orderControlRecyclerView.setHasFixedSize(true);
    }
    public OrderControlAdapter createAdapter(List<Product> productList){
        adapter = new OrderControlAdapter(productList, this);
        return adapter;
    }

    @Override
    public void onClick(Product product) {
        newOrderPositionUUID.add(product.getUuid().toString());
        productList.add(product);
    }

    @Override
    public void onDelete(Product product) {
        newOrderPositionUUID.remove(product.getUuid().toString());
        productList.remove(product);
    }
}
