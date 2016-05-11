package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductViewHolder;

public class ProductListActivity extends AppCompatActivity implements CommonAdapter.Listener<Product> {

    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.editProduct) public ImageView editProduct;
    @Bind(R.id.productRecyclerView) public RecyclerView productRecyclerView;
    private ProductList productList;
    private ProductListActivity activity;
    private UUID menucardRowItem;
    private CommonAdapter<Product,ProductViewHolder> adapter;

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle extras = getIntent().getExtras();
        if(extras==null) finish();
        menucardRowItem = (UUID)extras.get("menucardRowItem-uuid");
        loadDataSet();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MenuProductEditActivity.class);
                intent.putExtra("menucardRowItem-uuid", productList.getUuid().toString());
                startActivityForResult(intent,1);
            }
        });

        adapter = new CommonAdapter<Product, ProductViewHolder>(
                R.layout.column_row_product_list, productList.products(), this) {
            @Override
            public ProductViewHolder createItemViewHolder(View view) {
                return new ProductViewHolder(view);
            }

            @Override
            public void bindViewHolder(ProductViewHolder holder, Product item) {
                String name = (item != null && item.productDescription != null && item.productDescription.name != null)
                        ? item.productDescription.name
                        : "";
                String description = (item != null && item.productDescription != null && item.productDescription.description != null)
                ?item.productDescription.description
                :"";

                holder.productTitle.setText(name);
                holder.productDescription.setText(description);
                holder.productVolume.setText(item.volume);
                holder.productPrice.setText(item.price+"");
            }
        };
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(adapter);
        productRecyclerView.setHasFixedSize(true);


        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEditMode(!adapter.isEditMode());
            }
        });

    }

    public void loadDataSet(){
       productList = new ViewController<>(ProductList.class).get(menucardRowItem);
    }

    public void refreshList(){
        adapter.setList(productList.products());
    }

    @Override
    public void onItemClick(Product item) {
        Intent intent = new Intent(this, MenuProductEditActivity.class);
        intent.putExtra("product-uuid", item.getUuid().toString());
        intent.putExtra("menucardRowItem-uuid", item.productList.getUuid().toString());
        startActivityForResult(intent, 1);
    }

    @Override
    public void onDelete(Product item) {
        new ViewController<>(Product.class).delete(item);
        adapter.getList().remove(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                loadDataSet();
                refreshList();
            }
        }
    }
}
