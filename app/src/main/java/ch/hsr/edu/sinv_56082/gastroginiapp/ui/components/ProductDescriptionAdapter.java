package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import ch.hsr.edu.sinv_56082.gastroginiapp.R;
import ch.hsr.edu.sinv_56082.gastroginiapp.domain.models.ProductDescription;
import ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.menu.ProductDescriptionViewHolder;

public class ProductDescriptionAdapter extends RecyclerView.Adapter<ProductDescriptionViewHolder> {

    List<ProductDescription> productDescriptions = new ArrayList<>();
    private boolean editMode = false;

    public ProductDescriptionAdapter(List<ProductDescription> list){
        this.productDescriptions = list;
    }

    @Override
    public ProductDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.column_row_product_description,parent,false);
        return new ProductDescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductDescriptionViewHolder holder, int position) {
        holder.productDescriptionName.setText(productDescriptions.get(position).name);
        holder.productDescriptionDesc.setText(productDescriptions.get(position).description);
        if(isEditMode()){
            holder.deleteButton.setVisibility(View.VISIBLE);
        }else{
            holder.deleteButton.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return productDescriptions.size();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }
}
