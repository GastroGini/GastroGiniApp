package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;

import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductCategory;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.ProductDescriptionAdapter;

public class ProductDescriptionListActivity extends CommonActivity implements ProductDescriptionAdapter.Listener {
    private static final int PRODUCT_DESCRIPTION_RESULT = 2987;
    ProductDescriptionListActivity activity;
    List<ProductCategory> productCategories = new ArrayList<>();
    @Bind(R.id.superProductDescriptionRecyclerView) RecyclerView s_recycler;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_description_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(activity, MenuProductDescriptionEditActivity.class), PRODUCT_DESCRIPTION_RESULT);
            }
        });

        activity = this;
        productCategories = new Select().from(ProductCategory.class).execute();

        final CommonAdapter<ProductCategory,ProductCategoryViewHolder> adapter =
                new CommonAdapter<ProductCategory, ProductCategoryViewHolder>(
                        R.layout.column_row_product_description_categories,productCategories
                ) {
                    @Override
                    public ProductCategoryViewHolder createItemViewHolder(View view) {
                        return new ProductCategoryViewHolder(view);
                    }

                    @Override
                    public void bindViewHolder(ProductCategoryViewHolder holder, ProductCategory item) {
                        List<ProductDescription> productDescriptions = new Select().from(ProductDescription.class)
                                .where("productCategory = ?", item.getId()).execute();
                        final ProductDescriptionAdapter i_adapter = new ProductDescriptionAdapter(activity,productDescriptions);
                        holder.menuTitle.setText(item.name);
                        holder.editIcon.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                i_adapter.setEditMode(!isEditMode());
                            }
                        });
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                        linearLayoutManager.setAutoMeasureEnabled(true);
                        holder.productRecycler.setLayoutManager(linearLayoutManager);
                        holder.productRecycler.setAdapter(i_adapter);
                        holder.productRecycler.setHasFixedSize(false);
                    }
                };
        s_recycler.setLayoutManager(new LinearLayoutManager(activity));
        s_recycler.setClickable(false);
        s_recycler.setFocusable(false);
        s_recycler.setAdapter(adapter);
        s_recycler.setHasFixedSize(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PRODUCT_DESCRIPTION_RESULT){
            //loadProductDescriptions();
            Log.d("TEST", "onActivityResult: ReloadList");
        }
    }

    @Override
    public void onItemClick(ProductDescription item) {
        Intent intent = new Intent(this, MenuProductDescriptionEditActivity.class);
        intent.putExtra("productDescriptionSelect-uuid", item.getUuid().toString());
        startActivityForResult(intent, PRODUCT_DESCRIPTION_RESULT);
    }

    @Override
    public void onDelete(ProductDescription item) {
        item.delete();
        //loadProductDescriptions();
    }

   /*private void loadProductDescriptions() {
        productDescriptions.clear();
        productDescriptions.addAll(new Select().from(ProductDescription.class).<ProductDescription>execute());
        productDescriptionRecyclerView.getAdapter().notifyDataSetChanged();
    }*/

}
