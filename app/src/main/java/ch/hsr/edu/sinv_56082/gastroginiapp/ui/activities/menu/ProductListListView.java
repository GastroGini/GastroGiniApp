package ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.menu;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ch.hsr.edu.sinv_56082.gastroginiapp.Helpers.WarningMessage;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.controllers.view.ViewController;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductList;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.activities.CommonActivity;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.common.CommonAdapter;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductListViewHolder;

public class ProductListListView extends CommonActivity implements CommonAdapter.Listener<ProductList> {

    public static final int REQUEST_CODE_NEW_PRODUCT_LIST = 1;
    List<ProductList> productLists;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.EditMenuCardList)ImageView EditMenuCardList;
    @Bind(R.id.fab)FloatingActionButton fab;
    @Bind(R.id.menuCardRecyclerView)RecyclerView menuCardRecyclerView;
    private ProductListListView activity;
    private CommonAdapter<ProductList,ProductListViewHolder> adapter;
    private ViewController<ProductList> productListController;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_list_view);
        this.activity = this;
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productListController = new ViewController<>(ProductList.class);

        productLists = productListController.getModelList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductListListEditView.class);
                startActivityForResult(intent, REQUEST_CODE_NEW_PRODUCT_LIST);
            }
        });

        adapter = new CommonAdapter<ProductList, ProductListViewHolder>(
                R.layout.column_row_product_description,productLists, this) {
            @Override
            public ProductListViewHolder createItemViewHolder(View view) {
                return new ProductListViewHolder(view);
            }

            @Override
            public void bindViewHolder(ProductListViewHolder holder, ProductList item) {
                holder.menucardRowItemDescriptionTitle.setText(item.name);
                /* Because we use the same Layout file, we need to adjust the invisible
                 * containers textSize, for proper display of in-between spaces*/
                holder.menucardRowItemDescription.setVisibility(View.INVISIBLE);
                holder.menucardRowItemDescription.setTextSize(1.00f);
            }
        };

        menuCardRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        menuCardRecyclerView.setAdapter(adapter);
        menuCardRecyclerView.setHasFixedSize(true);


        EditMenuCardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonAdapter adap = ((CommonAdapter) menuCardRecyclerView.getAdapter());
                adap.setEditMode(!adap.isEditMode());
                //    isMenuCardListEditable = !isMenuCardListEditable;
                //      runRecyclerView(isMenuCardListEditable);
            }
        });


        //runRecyclerView(isMenuCardListEditable);

    }

    public void loadDataSet(){
        productLists = productListController.getModelList();
    }

    public void refreshList(){
        if(adapter != null){
            adapter.setList(productLists);
        }
    }

    @Override
    public void onItemClick(ProductList item) {
        Log.e("menu card list view", "goto menu card view" + item); // Erro
        Intent intent = new Intent(this,ProductListActivity.class);
        intent.putExtra("menucardRowItem-uuid", item.getUuid());
        startActivity(intent);
    }

    @Override
    public void onDelete(final ProductList item) {
        new WarningMessage(activity, "Wollen sie diese Position(en) wirklich löschen?", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                try {
                    productListController.delete(item);
                    productLists.remove(item);
                    adapter.notifyDataSetChanged();
                }catch (SQLiteConstraintException e){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    builder1.setMessage(activity.getResources().getString(R.string.cannot_delete_productList));
                    builder1.setCancelable(true);
                    builder1.setNegativeButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

                //runRecyclerView(isMenuCardListEditable);
                Log.e("menu list delete", "goto menu card view" + item.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_PRODUCT_LIST) {
            if (resultCode == RESULT_OK) {
                loadDataSet();
                refreshList();
            }
        }
    }
}
