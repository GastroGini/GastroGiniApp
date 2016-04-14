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
import android.widget.ImageView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuCardAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListClickListener;

public class MenuCardView extends AppCompatActivity implements MenuProductListClickListener {
    boolean isMenuCardListEditable;
    List<ProductList> productLists;
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
        isMenuCardListEditable = false;
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


        ImageView editButton = (ImageView) findViewById(R.id.editMenuItem);


        productLists = new Select().from(ProductList.class).execute();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMenuCardListEditable = !isMenuCardListEditable;
                runRecyclerView(isMenuCardListEditable);
            }
        });
        runRecyclerView(isMenuCardListEditable);



        FloatingActionButton addNew = (FloatingActionButton) findViewById(R.id.addMenuItem);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),EditMenuEntity.class);
                intent.putExtra("comingFrom", "addNewMenuItem" );
                intent.putExtra("page-name", "add-new-menu-item");
                startActivity(intent);
            }
        });



    }

    public void runRecyclerView (boolean isMenuCardListEditable){
        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.menuCardItemReciclerView);

        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new MenuCardAdapter(this, productLists,isMenuCardListEditable));
        eventTablesRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(ProductList productListNumber) {
        Intent intent = new Intent(getBaseContext(),EditMenuEntity.class);
        intent.putExtra("product-list-number", productListNumber.toString());
        intent.putExtra("page-name", "edit-new-menu-item");

        startActivity(intent);
        Log.e("menu card onclick view","productlist"+productListNumber.toString());
    }

    @Override
    public void editItem(ProductList productListNumber) {
        Intent intent = new Intent(getBaseContext(),EditMenuEntity.class);
        intent.putExtra("product-list-number", productListNumber.toString());
        intent.putExtra("page-name", "edit-new-menu-item");
        startActivity(intent);
        Log.e("menu card edit view","productlist"+productListNumber.toString());
    }

    @Override
    public void deleteItem(ProductList name) {
        int index = productLists.indexOf(name);
        productLists.remove(index);
        runRecyclerView(isMenuCardListEditable);
        Log.e("menu card delete view","productlist"+name.toString());
    }
}
