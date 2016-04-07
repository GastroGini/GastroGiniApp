package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

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

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductDescriptionAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListAdapter;

public class ProductDescriptionListActivity extends AppCompatActivity implements MenuProductDescriptionAdapter.OnClickListener {

    private static final int PRODUCT_DESCRIPTION_RESULT = 2987;
    private List<ProductDescription> productDescriptions = new ArrayList<>();
    private RecyclerView eventTablesRecyclerView;

    ProductDescriptionListActivity activity;

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
        setContentView(R.layout.activity_product_description_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity = this;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(activity, MenuProductDescriptionEditActivity.class), PRODUCT_DESCRIPTION_RESULT);
            }
        });

        eventTablesRecyclerView = (RecyclerView)findViewById(R.id.productDescriptionReciclerView);

        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new MenuProductDescriptionAdapter(this, productDescriptions));
        eventTablesRecyclerView.setHasFixedSize(true);

        loadProductDescriptions();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PRODUCT_DESCRIPTION_RESULT){
            loadProductDescriptions();
            Log.d("TEST", "onActivityResult: ReloadList");
        }
    }

    private void loadProductDescriptions() {
        productDescriptions.clear();
        productDescriptions.addAll(new Select().from(ProductDescription.class).<ProductDescription>execute());
        eventTablesRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(ProductDescription productList) {
        Intent intent = new Intent(this, MenuProductDescriptionEditActivity.class);
        intent.putExtra("productDescription-uuid", productList.uuid.toString());
        startActivityForResult(intent, PRODUCT_DESCRIPTION_RESULT);
    }
}
