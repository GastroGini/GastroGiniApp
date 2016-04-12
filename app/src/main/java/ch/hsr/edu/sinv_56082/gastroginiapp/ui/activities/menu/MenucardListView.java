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
import android.widget.ImageView;

import com.activeandroid.Model;
import com.activeandroid.query.Select;

import java.util.List;

import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView.EditMenuEntity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView.MenuCardView;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.MenuProductListClickListener;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order.EventTablesAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.*;

public class MenucardListView extends AppCompatActivity implements MenuProductListClickListener {
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
        setContentView(R.layout.activity_menucard_list_view);
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


        ImageView editButton = (ImageView) findViewById(R.id.EditMenuCardList);

        productLists = new Select().from(ProductList.class).execute();

        FloatingActionButton addNew = (FloatingActionButton) findViewById(R.id.fab);

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),EditMenuEntity.class);
                intent.putExtra("comingFrom", "addNewMenuList" );
                intent.putExtra("page-name", "add-menu-list");
                startActivity(intent);
            }
        });



        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMenuCardListEditable = !isMenuCardListEditable;
                runRecyclerView(isMenuCardListEditable);
            }
        });


        runRecyclerView(isMenuCardListEditable);

    }


    public void runRecyclerView (boolean isMenuCardListEditable){
        RecyclerView eventTablesRecyclerView = (RecyclerView)findViewById(R.id.menuCardRecyclerView);
        eventTablesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventTablesRecyclerView.setAdapter(new MenuProductListAdapter(this, productLists, isMenuCardListEditable));
        eventTablesRecyclerView.setHasFixedSize(true);
    }




    @Override
    public void onClick(ProductList productListNumber) {
        Log.e("menu card list view", "goto menu card view" + productListNumber); // Erro
        Intent intent = new Intent(getBaseContext(),MenuCardView.class);
        intent.putExtra("product-list-number", productListNumber.toString());
        intent.putExtra("page-name", "edit-menu-list");
        startActivity(intent);
    }

    @Override
    public void editItem(ProductList productListNumber) {
        Log.e("menu list edit", "goto menu card view" + productListNumber.toString());
        Intent intent = new Intent(getBaseContext(),EditMenuEntity.class);
        intent.putExtra("product-list-number", productListNumber.toString());
        intent.putExtra("page-name", "edit-menu-list");
        startActivity(intent);
    }

    @Override
    public void deleteItem(ProductList name) {
        int index = productLists.indexOf(name);
        productLists.remove(index);
        runRecyclerView(isMenuCardListEditable);
        Log.e("menu list delete", "goto menu card view" + name.toString());
    }
}
