package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView;

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

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuCardAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;

public class MenuCardView extends AppCompatActivity implements MenuCardAdapter.OnClickListener {

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
        setContentView(R.layout.activity_menucard_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String menuListId ;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            menuListId = extras.getString("product-list-number");
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addMenuItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.menuCardItemReciclerView);

        List<ProductList> productLists = new Select().from(ProductList.class).execute();

        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new MenuCardAdapter(this, productLists));
        eventTablesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(ProductList productListNumber) {
        Log.e("menu card view","productlist"+productListNumber.toString());
    }
}
