package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class ProductViewHolder extends RecyclerView.ViewHolder {
    View productItemView;

    TextView name;
    TextView size;
    TextView price;

    public ProductViewHolder(View productItemView, TextView name,TextView size,TextView price) {
        super(productItemView);
        this.productItemView = productItemView;
        this.name = name;
        this.size = size;
        this.price = price;
    }
    public View getEventTablesView(){
        return productItemView;
    }
    public TextView getNameTextView(){  return name; }
    public TextView getSizeTextView(){
        return size;
    }
    public TextView getPriceTextView(){
        return price;
    }
}
