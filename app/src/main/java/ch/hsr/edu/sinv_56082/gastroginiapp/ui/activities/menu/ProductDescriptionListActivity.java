package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.activeandroid.query.Select;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductDescriptionAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListAdapter;

public class ProductDescriptionListActivity extends AppCompatActivity implements MenuProductDescriptionAdapter.OnClickListener {

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



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.productDescriptionReciclerView);

        List<ProductDescription> productDescriptions = new Select().from(ProductDescription.class).execute();

        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new MenuProductDescriptionAdapter(this, productDescriptions));
        eventTablesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(ProductDescription productList) {

    }
}
