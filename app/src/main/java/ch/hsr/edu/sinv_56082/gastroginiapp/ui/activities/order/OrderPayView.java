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

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderState;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;

public class OrderPayView extends AppCompatActivity implements TableRowAdapter.TableItemClickListener {

    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.proceedButton) Button proceedButton;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderPayRecyclerView) RecyclerView orderPayRecyclerView;
    AppCompatActivity activity;

    EventTable eventTable;
    List<OrderPosition> tableOrderPositions = new ArrayList<>();
    List<OrderPosition> opToPayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_view);
        ButterKnife.bind(this);
        activity=this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();
        eventTable=getEventTableFromUUID(args);
        final ArrayList<String> test = args.getStringArrayList("tableOrderPositions");

        for(String opUUID : test){
            opToPayList.add(OrderPosition.get(UUID.fromString(opUUID)));
        }
        Log.d("ADSF", "onCreate: "+test);

        TableRowAdapter adapter = new TableRowAdapter(opToPayList, this);
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
                Log.d("OrderPayView", "payment started");
                for(OrderPosition op : opToPayList){
                    op.orderState= OrderState.STATE_PAYED;
                    op.payTime=new Date(System.currentTimeMillis());
                    op.save();
                    Log.d("asdfasdgojad", "onClick: ");
                }
                setResult(RESULT_OK);
                Log.d("OrderPayView", "payment done");
                finish();
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
    @Override
    public void onClick(OrderPosition orderPosition) {
        //No functionality
    }
    public EventTable getEventTableFromUUID (Bundle args){
        eventTable = new Select().from(EventTable.class).where
                ("uuid = ?", UUID.fromString(args.getString("eventTable-uuid"))).executeSingle();
        return eventTable;
    }
}