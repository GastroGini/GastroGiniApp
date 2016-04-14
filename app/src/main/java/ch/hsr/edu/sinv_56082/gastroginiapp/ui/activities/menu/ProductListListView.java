package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.activeandroid.query.Select;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView.EditMenuEntity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu.MenuCardView.MenuCardView;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductListViewHolder;

public class ProductListListView extends TestActivity implements TestAdapter.Listener<ProductList> {

    List<ProductList> productLists;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.EditMenuCardList)ImageView EditMenuCardList;
    @Bind(R.id.fab)FloatingActionButton fab;
    @Bind(R.id.menuCardRecyclerView)RecyclerView menuCardRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_list_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        productLists = new Select().from(ProductList.class).execute();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), EditMenuEntity.class);
                intent.putExtra("comingFrom", "addNewMenuList");
                intent.putExtra("page-name", "add-menu-list");
                startActivity(intent);
            }
        });


        menuCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuCardRecyclerView.setAdapter(new TestAdapter<ProductList, ProductListViewHolder>(R.layout.column_row_product_list,productLists, this) {
            @Override
            public ProductListViewHolder createItemViewHolder(View view) {
                return new ProductListViewHolder(view);
            }

            @Override
            public void bindViewHolder(ProductListViewHolder holder, ProductList item) {
                holder.productList.setText(item.name);
            }
        });
        menuCardRecyclerView.setHasFixedSize(true);


        EditMenuCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    isMenuCardListEditable = !isMenuCardListEditable;
                //      runRecyclerView(isMenuCardListEditable);
            }
        });


        //runRecyclerView(isMenuCardListEditable);

    }




/*
    @Override
    public void onClick(ProductList productListNumber) {

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

    }
*/

    @Override
    public void onItemClick(ProductList item) {
        Log.e("menu card list view", "goto menu card view" + item); // Erro
        Intent intent = new Intent(getBaseContext(),MenuCardView.class);
        intent.putExtra("product-list-number", item.toString());
        intent.putExtra("page-name", "edit-menu-list");
        startActivity(intent);
    }

    @Override
    public void onDelete(ProductList item) {
        int index = productLists.indexOf(item);
        productLists.remove(index);
        //runRecyclerView(isMenuCardListEditable);
        Log.e("menu list delete", "goto menu card view" + item.toString());
    }
}
