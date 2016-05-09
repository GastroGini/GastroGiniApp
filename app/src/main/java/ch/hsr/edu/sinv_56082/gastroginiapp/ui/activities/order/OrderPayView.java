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
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.Consumer;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.app.ConnectionController;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.OrderPayAdapter;

public class OrderPayView extends AppCompatActivity implements OrderPayAdapter.OrderItemClickListener {

    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.proceedButton) Button proceedButton;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderPayRecyclerView) RecyclerView orderPayRecyclerView;
    @Bind(R.id.order_pay_subtotal) TextView subtotalField;
    AppCompatActivity activity;

    EventTable eventTable;
    List<OrderPosition> tableOrderPositions = new ArrayList<>();
    List<OrderPosition> opToPayList = new ArrayList<>();
    private ViewController<OrderPosition> orderPositionController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_view);
        ButterKnife.bind(this);
        activity=this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderPositionController = new ViewController<>(OrderPosition.class);

        Bundle args = getIntent().getExtras();
        eventTable=getEventTableFromUUID(args);
        final ArrayList<String> test = args.getStringArrayList("tableOrderPositions");

        for(String opUUID : test){
            opToPayList.add(orderPositionController.get(UUID.fromString(opUUID)));
        }
        Log.d("ADSF", "onCreate: " + test);

        subtotalField.setText("Subtotal: " + calculateSubtotal(opToPayList));
        OrderPayAdapter adapter = new OrderPayAdapter(opToPayList, this);
        orderPayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderPayRecyclerView.setAdapter(adapter);
        orderPayRecyclerView.setHasFixedSize(true);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(OrderPosition op : opToPayList){
                    orderPositionController.update(op, new Consumer<OrderPosition>() {
                        @Override
                        public void consume(OrderPosition orderPosition) {
                            orderPosition.orderState = OrderState.STATE_PAYED;
                            orderPosition.payTime = new Date(System.currentTimeMillis());
                        }
                    });
                }

                ConnectionController.getInstance().sendPayed(opToPayList); // TODO Controller
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private String calculateSubtotal(List<OrderPosition> opToPayList) {
        double sum = 0;
        for(OrderPosition orderPosition : opToPayList){
            sum += orderPosition.product.price;
        }
        return sum + "";
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
    @Override
    public void onClick(OrderPosition orderPosition) {
        //No functionality
    }
    public EventTable getEventTableFromUUID (Bundle args){
        eventTable = new ViewController<>(EventTable.class).get(args.getString("eventTable-uuid"));
        return eventTable;
    }
}
