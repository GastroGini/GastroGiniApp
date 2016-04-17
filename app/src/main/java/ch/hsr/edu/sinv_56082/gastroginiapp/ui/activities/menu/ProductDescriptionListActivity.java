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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.Product;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.TestActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.TestAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductDescriptionViewHolder;

public class ProductDescriptionListActivity extends TestActivity implements TestAdapter.Listener<ProductDescription> {

    private static final int PRODUCT_DESCRIPTION_RESULT = 2987;
    private List<ProductDescription> productDescriptions = new ArrayList<>();
    @Bind(R.id.productDescriptionRecyclerView) RecyclerView productDescriptionRecyclerView;
    @Bind(R.id.menuCardProductListHeaderEditMenuIcon) ImageView editIcon;

    ProductDescriptionListActivity activity;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        activity = this;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(activity, MenuProductDescriptionEditActivity.class), PRODUCT_DESCRIPTION_RESULT);
            }
        });

        productDescriptionRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TestAdapter<ProductDescription, ProductDescriptionViewHolder> adapter =
                new TestAdapter<ProductDescription, ProductDescriptionViewHolder>(
                        R.layout.column_row_product_description, productDescriptions, this) {
            @Override
            public ProductDescriptionViewHolder createItemViewHolder(View view) {
                return new ProductDescriptionViewHolder(view);
            }

            @Override
            public void bindViewHolder(ProductDescriptionViewHolder holder, ProductDescription item) {
                holder.productDescriptionName.setText(item.name);
                holder.productDescriptionDesc.setText(item.description);
            }
        };

        productDescriptionRecyclerView.setAdapter(adapter);

        productDescriptionRecyclerView.setHasFixedSize(true);

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setEditMode(!adapter.isEditMode());
            }
        });

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
        productDescriptionRecyclerView.getAdapter().notifyDataSetChanged();
    }

    /*@Override
    public void onClick(ProductDescription menucardRowItem) {
        Intent intent = new Intent(this, MenuProductDescriptionEditActivity.class);
        intent.putExtra("productDescriptionSelect-uuid", menucardRowItem.getUuid().toString());
        startActivityForResult(intent, PRODUCT_DESCRIPTION_RESULT);
    }*/

    @Override
    public void onItemClick(ProductDescription item) {
        Intent intent = new Intent(this, MenuProductDescriptionEditActivity.class);
        intent.putExtra("productDescriptionSelect-uuid", item.getUuid().toString());
        startActivityForResult(intent, PRODUCT_DESCRIPTION_RESULT);
    }

    @Override
    public void onDelete(ProductDescription item) {
        item.delete();
        loadProductDescriptions();
    }
}
