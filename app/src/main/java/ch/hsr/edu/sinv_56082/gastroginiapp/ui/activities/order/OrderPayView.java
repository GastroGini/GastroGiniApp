package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.table.TableRowAdapter;

public class OrderPayView extends TestActivity {

    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.proceedButton) Button proceedButton;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.orderPayRecyclerView) RecyclerView orderPayRecyclerView;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();

        ArrayList<String> test = args.getStringArrayList("tableOrderPositions");

        Log.d("ADSF", "onCreate: "+test);

        //TableRowAdapter adapter = new TableRowAdapter(this,tableOrderPositions);
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
                //TODO: clear table order view list
            }
        });
    }

}
