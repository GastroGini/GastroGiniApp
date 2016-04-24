package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ProductDescriptionListActivity activity;
    private List<ProductCategory> productCategories = new ArrayList<>();
    private CommonAdapter<ProductCategory,ProductCategoryViewHolder> adapter;
    private List<ProductDescriptionAdapter> productDescriptionAdapterList = new ArrayList<>();
    private boolean editMode = false;
    @Bind(R.id.superProductDescriptionRecyclerView) RecyclerView s_recycler;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.productDescriptionHintText) TextView hintText;

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

        checkIfHintTextNecessary();

        adapter = new CommonAdapter<ProductCategory, ProductCategoryViewHolder>(
                        R.layout.column_row_product_description_categories,productCategories ) {
                    @Override
                    public ProductCategoryViewHolder createItemViewHolder(View view) {
                        return new ProductCategoryViewHolder(view);
                    }

                    @Override
                    public void bindViewHolder(ProductCategoryViewHolder holder, ProductCategory item) {
                        holder.productRecycler.setVisibility(View.GONE);
                        holder.expandCollapseIcon.setAlpha(0.25f);

                        List<ProductDescription> productDescriptions = new Select().from(ProductDescription.class)
                                .where("productCategory = ?", item.getId()).execute();

                        if(productDescriptions.size() > 0){
                            holder.menuTitle.setVisibility(View.VISIBLE);
                            holder.editIcon.setVisibility(View.VISIBLE);
                            holder.expandCollapseIcon.setVisibility(View.VISIBLE);
                            holder.productRecycler.setVisibility(View.VISIBLE);
                            holder.expandCollapseIcon.setAlpha(1.0f);

                            final RecyclerView recycler = holder.productRecycler;
                            final ImageView expandCollapseIcon = holder.expandCollapseIcon;
                            expandCollapseIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(recycler.getVisibility() == View.VISIBLE){
                                        expandCollapseIcon.setAlpha(0.25f);
                                        recycler.setVisibility(View.GONE);
                                    }else{
                                        expandCollapseIcon.setAlpha(1.0f);
                                        recycler.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            final ProductDescriptionAdapter i_adapter = new ProductDescriptionAdapter(activity,productDescriptions);
                            productDescriptionAdapterList.add(i_adapter);

                            holder.menuTitle.setText(item.name);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                            linearLayoutManager.setAutoMeasureEnabled(true);
                            holder.productRecycler.setLayoutManager(linearLayoutManager);
                            holder.productRecycler.setAdapter(i_adapter);
                            holder.productRecycler.setHasFixedSize(false);
                            holder.editIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    i_adapter.setEditMode(!i_adapter.isEditMode());
                                }
                            });
                        }else{
                            holder.menuTitle.setVisibility(View.GONE);
                            holder.editIcon.setVisibility(View.GONE);
                            holder.productRecycler.setVisibility(View.GONE);
                            holder.expandCollapseIcon.setVisibility(View.GONE);
                        }
                    }
                };

        s_recycler.setItemAnimator(null);
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
            checkIfHintTextNecessary();
            refreshAdapters();
            Log.d("TEST", "onActivityResult: ReloadList");
        }
    }

    private void checkIfHintTextNecessary(){
        if(new Select().from(ProductDescription.class).execute().isEmpty()){
            hintText.setVisibility(View.VISIBLE);
            s_recycler.setVisibility(View.GONE);
        }else{
            hintText.setVisibility(View.GONE);
            s_recycler.setVisibility(View.VISIBLE);
        }
    }

    private void refreshSuperAdapter(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }
    private void refreshAdapters() {
        if(productDescriptionAdapterList != null){
            for(ProductDescriptionAdapter i_adapter : productDescriptionAdapterList){
                i_adapter.notifyDataSetChanged();
            }
        }
        refreshSuperAdapter();
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
        removeItemFromCorrespondingList(item);
        checkIfHintTextNecessary();
    }

    private void removeItemFromCorrespondingList(ProductDescription item) {
        ProductDescriptionAdapter i_adapter;
        for(int i = 0; i < productDescriptionAdapterList.size();i++){
            i_adapter = productDescriptionAdapterList.get(i);
            if(i_adapter.getList().contains(item)){
                i_adapter.getList().remove(item);
                i_adapter.notifyDataSetChanged();
            }
            if(i_adapter.getList().isEmpty()){
                adapter.notifyItemChanged(i);
            }
        }
    }

}
