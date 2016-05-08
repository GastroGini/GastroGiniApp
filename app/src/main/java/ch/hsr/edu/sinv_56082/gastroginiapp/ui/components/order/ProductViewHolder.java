package ch.hsr.edu.sinv_56082.gastroginiapp.ui.components.order;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class ProductViewHolder extends RecyclerView.ViewHolder {
    View productItemView;
    TextView name;
    TextView size;
    TextView price;
    ImageView subtractAmount;
    TextView amountCounter;
    ImageView addAmount;
    private int count = 0;

    public ProductViewHolder(View productItemView, TextView name,TextView size,TextView price,
                             ImageView subtractAmount, TextView amountCounter, ImageView addAmount) {
        super(productItemView);
        this.productItemView = productItemView;
        this.name = name;
        this.size = size;
        this.price = price;
        this.subtractAmount = subtractAmount;
        this.amountCounter = amountCounter;
        this.addAmount = addAmount;
    }
    public View getEventTablesView(){
        return productItemView;
    }
    public TextView getNameTextView(){ return name; }
    public TextView getSizeTextView(){ return size;}
    public TextView getPriceTextView(){return price;}
    public ImageView getSubtractAmountView(){return subtractAmount;}
    public TextView getAmountCounterView(){return amountCounter;}
    public ImageView getAddAmountView(){return addAmount;}

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void increaseCount(){
        count++;
    }

    public void decreaseCount(){
        if(count > 0){
            count--;
        }
    }
}
