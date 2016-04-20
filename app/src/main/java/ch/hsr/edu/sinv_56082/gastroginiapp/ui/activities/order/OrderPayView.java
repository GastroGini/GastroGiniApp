package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class OrderPayView extends AppCompatActivity {

    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.proceedButton) Button proceedButton;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderPayRecyclerView) RecyclerView orderPayRecyclerView;

    List<OrderPosition> tableOrderPositions = new ArrayList<>();
    List<OrderPosition> opToPayList = new ArrayList<>();

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();

        final ArrayList<String> test = args.getStringArrayList("tableOrderPositions");
        for(String opUUID : test){
            //TODO: make type UUID from type string
            //opToPayList.add(OrderPosition.get(opUUID));
        }
        Log.d("ADSF", "onCreate: "+test);

        //TableRowAdapter adapter = new TableRowAdapter(this,OrderPositionsUUID);
        orderPayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //orderPayRecyclerView.setAdapter(adapter);
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
                Log.d("Bezahlen", "onClick: payment done");
                for(OrderPosition op : opToPayList){
                    //TODO:set orderState to payed
                }
                onBackPressed();
            }
        });
    }

}
