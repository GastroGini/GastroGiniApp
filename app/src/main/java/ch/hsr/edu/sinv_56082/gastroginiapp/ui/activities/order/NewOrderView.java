package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.Bind;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.OrderPosition;

public class NewOrderView extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.newOrderRecyclerView) RecyclerView newOrderRecyclerView;
    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.finishButton) Button finishButton;

    private AppCompatActivity activity;
    ArrayList<String> newOrderPositionUUID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, OrderPayView.class);
                //intent.putExtra("OrderPositionsUUID", OrderPositionsUUID);
                startActivity(intent);
            }
        });
    }

    protected void onItemClicked (OrderPosition orderPosition){
        newOrderPositionUUID.contains(orderPosition.getUuid().toString());

    }

}
