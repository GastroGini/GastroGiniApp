package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.EventTable;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.connection.ConnectionActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.ProductAdapter;

public class NewOrderView extends ConnectionActivity implements ProductAdapter.ProductItemClickListener{
    private final int NEWORDERVIEW_REQUESTCODE = 1989;
    private AppCompatActivity activity;
    private ArrayList<String> newOrderPositionUUID = new ArrayList<>();
    private EventTable eventTable = new EventTable();
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter adapter;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.newOrderRecyclerView) RecyclerView newOrderRecyclerView;
    @Bind(R.id.cancelButton) Button cancelButton;
    @Bind(R.id.finishButton) Button finishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_view);
        ButterKnife.bind(this);
        activity = this;
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventTable = getEventTableFromUUID(args);
        productList = loadProducts(eventTable);
        adapter = createAdapter(productList);
        startRecyclerView(adapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrderPositionUUID.clear();
                onBackPressed();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("NewOrderView", "go to orderControlView");
                Intent intent = new Intent(activity, OrderControlView.class);
                intent.putStringArrayListExtra("newOrderPositionsUUID", newOrderPositionUUID);
                intent.putExtra("eventTable-uuid", eventTable.getUuid().toString());
                startActivityForResult(intent, NEWORDERVIEW_REQUESTCODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEWORDERVIEW_REQUESTCODE){
            if(resultCode == OrderControlView.ORDERCONTROLVIEW_ABORT){
                Log.d("NewOrderView", "Order was aborted");
            }

            if(resultCode == OrderControlView.ORDERCONTROLVIEW_CONFIRM){
                Log.d("NewOrderView", "Order was confirmed");
                setResult(RESULT_OK);
                finish();
            }
        }

    }

    @Override
    public void onClick(Product product) {
        Log.d("NewOrderView", "product added to new order");
        newOrderPositionUUID.add(product.getUuid().toString());
    }

    @Override
    public void onDelete(Product product) {
        newOrderPositionUUID.remove(product.getUuid().toString());
    }

    private EventTable getEventTableFromUUID (Bundle args){
        eventTable = new ViewController<>(EventTable.class).get(args.getString("eventTable-uuid"));
        return eventTable;
    }
    private List<Product> loadProducts (EventTable eventTable){
        productList.addAll(eventTable.event.productList.products());
        return productList;
    }
    private void startRecyclerView(ProductAdapter adapter){
        newOrderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newOrderRecyclerView.setAdapter(adapter);
        newOrderRecyclerView.setHasFixedSize(true);
    }

    private ProductAdapter createAdapter(List<Product> productList){
        adapter = new ProductAdapter(productList, this);
        return adapter;
    }

    /*    public List<Product> reloadProducts (ArrayList<String> newOrderPositionUUID){
        for(String product : newOrderPositionUUID){
            productList.add(new ViewController<>(Product.class).get(product));
        }
        return productList;
    }*/

    @Override
    public ConnectionActivity getActivity() {
        return this;
    }
}
