package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductViewHolder;

public class ProductListActivity extends AppCompatActivity implements TestAdapter.Listener<Product> {

    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.editProduct) public ImageView editProduct;
    @Bind(R.id.productRecyclerView) public RecyclerView productRecyclerView;
    private ProductList productList;
    private ProductListActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        Bundle extras = getIntent().getExtras();
        if(extras==null) finish();
        productList = ProductList.get((UUID)extras.get("menucardRowItem-uuid"));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MenuProductEditActivity.class);
                intent.putExtra("menucardRowItem-uuid", productList.getUuid().toString());
                startActivity(intent);
            }
        });


        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productRecyclerView.setAdapter(new TestAdapter<Product, ProductViewHolder>(R.layout.column_row_product, productList.products(), this) {
            @Override
            public ProductViewHolder createItemViewHolder(View view) {
                return new ProductViewHolder(view);
            }

            @Override
            public void bindViewHolder(ProductViewHolder holder, Product item) {
                holder.columnRowProductDescription.setText(item.productDescription.name);
                holder.columnRowProductPrice.setText(String.valueOf(item.price));
                holder.columnRowProductVolume.setText(item.volume);
            }
        });
        productRecyclerView.setHasFixedSize(true);


        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public void onItemClick(Product item) {
        Intent intent = new Intent(this, MenuProductEditActivity.class);
        intent.putExtra("product-uuid", item.getUuid().toString());
        intent.putExtra("menucardRowItem-uuid", item.productList.getUuid().toString());
        startActivity(intent);
    }

    @Override
    public void onDelete(Product item) {

    }
}
